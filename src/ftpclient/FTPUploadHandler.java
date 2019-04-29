/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Kristofer
 */
public class FTPUploadHandler {
    public void FTPUploader(Socket dataSocket, String filename, byte[] content) throws IOException {
        PrintStream dataStream = new PrintStream(dataSocket.getOutputStream());
        dataStream.write(content);
        dataStream.close();
        dataSocket.close();
        System.out.println("File: " + filename + " delivered to server!");
    }
}
