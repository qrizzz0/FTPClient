package ftpclient.transfersession;

import ftpclient.FTPSessionManager;
import java.io.*;
import java.net.Socket;

public class FTPUploadHandler extends FTPTransferSession implements Runnable {
    private File fileToUpload;
    private String destination = "/";

    public FTPUploadHandler(FTPSessionManager sessionManager, File fileToUpload) throws IOException {
        super(sessionManager);
        this.fileToUpload = fileToUpload;
        this.dataSocket = initDataConnection();
        this.size = fileToUpload.length();
        this.fileName = fileToUpload.getName();
    }

    public FTPUploadHandler(FTPSessionManager sessionManager, String filePath) throws IOException {
        this(sessionManager, new File(filePath));
    }
    
    public FTPUploadHandler(FTPSessionManager sessionManager, String filePath, String destination) throws IOException {
        this(sessionManager, new File(filePath));
        this.destination = destination;
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
        send("STOR " + destination + fileName);
        sessionManager.buffer_println("Starting upload: " + fileName + " Destination: " + destination);
        OutputStream outStream = dataSocket.getOutputStream();
        BufferedOutputStream dataStream = new BufferedOutputStream(outStream);
        
        InputStream fileStream = null;
        try { fileStream = new BufferedInputStream(new FileInputStream(file)); }
        catch (FileNotFoundException ex) { 
            sessionManager.buffer_println("File to upload not found! Cleaning up.");
            closeUploadSocket(dataStream, dataSocket);
            send("DELE " + fileName);
            throw new IOException();
        }
        
        
        byte uploadBuffer[] = new byte[bufferSize];
            
        while (!finished) {
            int uploaded = fileStream.read(uploadBuffer);
            
            dataStream.write(uploadBuffer, 0, uploaded);
            this.processedBytes += uploaded;
            
            this.finished = this.size == this.processedBytes;
            speedCalculator(bufferSize);
        }
        closeUploadSocket(dataStream, dataSocket);
    }
        
    private void closeUploadSocket(BufferedOutputStream dataStream, Socket dataSocket) throws IOException {
            dataStream.flush();
            try {Thread.sleep(500); } catch (Exception ex) {}
            dataStream.close();
            dataSocket.close();
            closeSession();
    }
    
    @Override
    public String sessionString() {
        return "Upload: " + super.sessionString();
    }
    
}
