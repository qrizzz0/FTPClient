package ftpclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class FTPIO implements Runnable {

    @Override
    public void run() {

    }

    public byte[] readFromDataSocket(Socket dataSocket) throws IOException {
        var dataStream = dataSocket.getInputStream();
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

    public void FTPDownloader(Socket datasocket, String filename) throws IOException {
        byte[] readBytes = readFromDataSocket(datasocket);

        try ( FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(readBytes);
        }
    }
    
    public void FTPUploader(Socket dataSocket, String filename, byte[] content) throws IOException {
        PrintStream dataStream = new PrintStream(dataSocket.getOutputStream());
        dataStream.write(content);
        dataStream.close();
        dataSocket.close();
        System.out.println("File: " + filename + " delivered to server!");
    }
    
}
