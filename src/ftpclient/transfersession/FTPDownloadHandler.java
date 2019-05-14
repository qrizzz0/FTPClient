package ftpclient.transfersession;

import ftpclient.FTPSessionManager;
import java.io.*;
import java.util.StringTokenizer;

public class FTPDownloadHandler extends FTPTransferSession implements Runnable {
    private String destination = "";
    
    public FTPDownloadHandler(FTPSessionManager sessionManager, String filePath) throws IOException {
        super(sessionManager);
        this.fileName = filePath;
        this.size = requestSize();
        this.dataSocket = initDataConnection();
    }
    
    public FTPDownloadHandler(FTPSessionManager sessionManager, String filePath, String destination) throws IOException {
        this(sessionManager, filePath);
        this.destination = destination + trimPathToFileName(fileName);
    }
    
    @Override
    public void run() {
        try {
        writeFileFromSocket(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void writeFileFromSocket(String fileName) throws IOException {
        send("RETR " + fileName);
        sessionManager.buffer_println("Starting download: " + fileName + " Destination: " + destination);
        InputStream dataStream = dataSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream(destination);
        byte[] buffer = new byte[bufferSize];
        
        while (!this.finished) {
            int read = dataStream.read(buffer);
            if (read <= 0) {
                if (dataSocket.isClosed()) {
                    throw new IOException();
                } else {
                    //Hvis vi ikke har downloadet noget så vent og prøv igen..
                    try { Thread.sleep(5); } catch (InterruptedException ex) {}
                }
            } else {           
                fos.write(buffer, 0, read);
                fos.flush();
                this.processedBytes += read;
            }
            
            this.finished = this.size == this.processedBytes;
            this.speedCalculator(bufferSize);
        }
        
        closeUploadSocket(fos, dataStream);
    }
    
    private void closeUploadSocket(FileOutputStream fos, InputStream dataStream) throws IOException {
        fos.flush();
        fos.close();
        dataStream.close();
        dataSocket.close();
        closeSession();
    }
    
   
    private long requestSize() throws IOException {
        int retries = 0;
        while (retries <= 5) {
            String response = send("SIZE " + this.fileName);
            StringTokenizer sizetokenizer = new StringTokenizer(response, " \n\r");
            if (sizetokenizer.countTokens() == 2) {
                sizetokenizer.nextToken();
                long size = Integer.parseInt(sizetokenizer.nextToken());
                return size;
            }
            retries++;
        }
        throw new IOException();
    }
    
    @Override
    public String sessionString() {
        return "Download: " + super.sessionString();
    }

    private String trimPathToFileName(String filePath) {
        String[] brokenUpPath = filePath.split("/");
        return brokenUpPath[brokenUpPath.length - 1];
    }
    
}
