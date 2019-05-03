package ftpclient.transfersession;

import ftpclient.FTPSessionManager;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class FTPDownloadHandler extends FTPTransferInterface implements Runnable {
    
    public FTPDownloadHandler(FTPSessionManager sessionManager, String fileName) throws IOException {
        super(sessionManager);
        this.fileName = fileName;
        this.size = requestSize();
        this.dataSocket = initDataConnection();
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        try {
        writeFileFromSocket(dataSocket, fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void writeFileFromSocket(Socket datasocket, String filename) throws IOException {
        send("RETR " + fileName);
        InputStream dataStream = dataSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream(filename);
        byte[] buffer = new byte[bufferSize];
        
        while (!this.finished) {
            int read = dataStream.read(buffer);
            if (read <= 0) {
                if (dataSocket.isClosed()) {
                    throw new IOException();
                } else {
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
    public String logString() {
        return "Download: " + super.logString();
    }
    
}
