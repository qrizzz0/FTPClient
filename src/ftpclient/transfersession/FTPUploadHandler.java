package ftpclient.transfersession;

import ftpclient.FTPSessionManager;
import java.io.*;

public class FTPUploadHandler extends FTPTransferInterface implements Runnable {
    private File fileToUpload;

    public FTPUploadHandler(FTPSessionManager sessionManager, File fileToUpload) throws IOException {
        super(sessionManager);
        this.fileToUpload = fileToUpload;
        this.dataSocket = initDataConnection();
        this.size = fileToUpload.length();
        this.fileName = fileToUpload.getName();
        new Thread(this).start();
    }

    public FTPUploadHandler(FTPSessionManager sessionManager, String filePath) throws IOException {
        this(sessionManager, new File(filePath));
    }
    
    @Override
    public void run() {   
        try {
            //PrintStream dataStream = new PrintStream(dataSocket.getOutputStream());
            uploadFromFile(fileToUpload);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void uploadFromFile(File file) throws IOException {
        OutputStream outStream = dataSocket.getOutputStream();
        BufferedOutputStream dataStream = new BufferedOutputStream(outStream);
        InputStream fileStream = new FileInputStream(file);
        byte uploadBuffer[] = new byte[bufferSize];
            
        while (!finished) {
            int uploaded = fileStream.read(uploadBuffer);
            if (uploaded <= 0) {
                if (dataSocket.isClosed()) {
                    throw new IOException();
                } else {
                    try { Thread.sleep(5); } catch (InterruptedException ex) {}
                }
            } else {           
                dataStream.write(uploadBuffer, 0, uploaded);
                this.processedBytes += uploaded;
            }
            this.finished = this.size == this.processedBytes;
            speedCalculator(bufferSize);
        }
        dataStream.flush();
        dataStream.close();
        dataSocket.close();
    }
        
    @Override
    public String logString() {
        return "Upload: " + super.logString();
    }
    
}
