/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import java.io.IOException;

/**
 *
 * @author Nikolaj Landberg
 */
public class FTPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
        //Connect to ftp server
        FTPIO FTP = new FTPIO("emby.waii.dk", "idunno", "47");
        
        //Send and print HELP
        String s1;
        s1 = FTP.send("HELP");
        System.out.println(s1);
        
        //Initialize passive data connection (for LIST)
        var datasocket = FTP.initDataConnection();
        
        //List root folder
        FTP.send("LIST");
        byte[] bytes = FTP.readFromDataSocket(datasocket, false);
        s1 = new String (bytes);
        System.out.println(s1);
        
        //Get a file
        FTP.getFile("fil.txt");
        
        //Get a file bigger than 1KB
        FTP.getFile("NOR-gate.png");
        
        //Create a file
        String data = "Hello, how are you doing? :D";
        byte[] content = data.getBytes();
        FTP.createFile("NewFile.txt", content);
        
        }
        catch (IOException ex) {
            System.out.println("Something went wrong, maybe the server does not accept connections?");
            ex.printStackTrace();
        }
    }
    
}
