package ftpclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class FTPDownloadHandler implements Runnable {
Socket dataSocket;
String fileName;
    
    public FTPDownloadHandler(Socket dataSocket, String fileName) throws IOException {
        this.dataSocket = dataSocket;
        this.fileName = fileName;
    }
    
    @Override
    public void run() {
        try {
        FTPDownloader(dataSocket, fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void FTPDownloader(Socket datasocket, String filename) throws IOException {
        byte[] readBytes = readFromDataSocket(datasocket);

        try ( FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(readBytes);
        }
    }
    
    public byte[] readFromDataSocket(Socket dataSocket) throws IOException {
        InputStream dataStream = dataSocket.getInputStream();
        byte[] buffer = new byte[1];
        int read = 1;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        //Lav til for loop for at tage downloads i bidder, ellers dør det hele når filer bliver for store
        while ((read = dataStream.read(buffer)) > 0) {
            byteStream.write(buffer);
        }

        byte[] outBytes = byteStream.toByteArray();

        dataStream.close();
        dataSocket.close();

        return outBytes;
    }
    

    
}
