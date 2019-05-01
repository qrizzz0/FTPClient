package ftpclient.transfer;

import java.io.*;
import java.net.*;
import java.util.*;

public class FTPDownloadHandler extends FTPTransferInterface {
    
    public FTPDownloadHandler(Socket dataSocket, String fileName, long size) throws IOException {
        this.dataSocket = dataSocket;
        this.fileName = fileName;
        this.size = size;
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
        int bufferSize = 8096;
        InputStream dataStream = dataSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream(filename);
        byte[] buffer = new byte[bufferSize];
        int read = 1;
        long WriteIterations = this.size / bufferSize + 1;
        
        while ((read = dataStream.read(buffer)) > 0) {
        //for (int i = 0; i <= WriteIterations; i++) {
            //read = dataStream.read(buffer);
            fos.write(buffer, 0, read);
            fos.flush();
            this.processedBytes += read;
            try { Thread.sleep(5); } catch (InterruptedException ex) {}
        }
        this.Finished = true;
        
        fos.close();
        dataStream.close();
        dataSocket.close();
    }
   
    
}
