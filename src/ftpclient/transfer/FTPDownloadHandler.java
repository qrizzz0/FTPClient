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
        InputStream dataStream = dataSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream(filename);
        byte[] buffer = new byte[bufferSize];
        int read = 1;
        int noReads = 0;
        
        while ((read = dataStream.read(buffer)) > 0 && !this.finished) {
            fos.write(buffer, 0, read);
            fos.flush();
            this.processedBytes += read;
            
            if (read <= 0) {
                noReads++;
                try { Thread.sleep(10); } catch (InterruptedException ex) {}
            }

            this.finished = this.size == this.processedBytes; 
            this.finished = noReads >= 100;
            this.speedCalculator(bufferSize);
        }
        if (!this.finished) {
            //Something must be wrong, has the size changed on the server?
            this.finished = true;
        }
        
        fos.close();
        dataStream.close();
        dataSocket.close();
    }
   
    
}
