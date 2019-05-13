package ftpclient.transfersession;

import ftpclient.FTPSessionManager;
import java.io.*;
import java.net.Socket;

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
        send("STOR " + fileName);
        OutputStream outStream = dataSocket.getOutputStream();
        BufferedOutputStream dataStream = new BufferedOutputStream(outStream);
        InputStream fileStream = null;
        try { fileStream = new FileInputStream(file); }
        catch (FileNotFoundException ex) { 
            System.out.println("File to upload not found! Cleaning up.");
            closeUploadSocket(dataStream, dataSocket);
            send("DELE " + fileName);
            throw new IOException();
        }

        byte uploadBuffer[] = new byte[bufferSize];
            
        while (!finished) {
            int uploaded = fileStream.read(uploadBuffer);
            if (uploaded <= 0) {
                if (dataSocket.isClosed()) {
                    throw new IOException();
                } else {
                    //Hvis vi ikke har uploadet noget så vent og prøv igen..
                    try { Thread.sleep(5); } catch (InterruptedException ex) {}
                }
            } else {    
                dataStream.write(uploadBuffer, 0, uploaded);
                this.processedBytes += uploaded;
            }
            this.finished = this.size == this.processedBytes;
            speedCalculator(bufferSize);
        }
        closeUploadSocket(dataStream, dataSocket);
    }
        
    private void closeUploadSocket(BufferedOutputStream dataStream, Socket dataSocket) throws IOException {
            dataStream.flush();
            dataStream.close();
            dataSocket.close();
            closeSession();
    }
    
    @Override
    public String logString() {
        return "Upload: " + super.logString();
    }
    
}
