/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient.transfer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Kristofer
 */
public class FTPUploadHandler extends FTPTransferInterface {
    
    public void FTPUploader(Socket dataSocket, String filename, byte[] content) throws IOException {
        this.dataSocket = dataSocket;
        System.out.println("File: " + filename + " delivered to server!");
    }
    
    public void FTPUploader(Socket dataSocket, String filename, String localLocation) throws IOException {
        this.dataSocket = dataSocket;
        System.out.println("File: " + filename + " delivered to server!");
    }
    
    private void uploadFromFileStream() {
        PrintStream dataStream = new PrintStream(dataSocket.getOutputStream());
        dataStream.write(content);
        dataStream.close();
        dataSocket.close();
    }

    @Override
    public void run() {
        //Start upload!
    }
    
}
