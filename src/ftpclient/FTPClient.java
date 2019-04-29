package ftpclient;
import java.io.IOException;

public class FTPClient {
    public static void main(String[] args) {
        try {
        //Connect to ftp server
        FTPSession FTP1 = new FTPSession("emby.waii.dk", 21, "idunno", "47");
        //FTPSession FTP2 = new FTPSession("mirror.internode.on.net", 21, "anonymous", "anonymous@domain.com");
        
        
        //Send and print HELP
        String s1;
        FTP1.send("HELP");
        System.out.println(FTP1.getAvailabeText());
        
        //s1 = FTP2.send("HELP");
        System.out.println(FTP1.getAvailabeText());
        
        
        //Initialize passive data connection (for LIST)
        var dataSocket = FTP1.initDataConnection();
        //List root folder
        FTP1.send("LIST");
        System.out.println(FTP1.getTextFromDataStream(dataSocket));
        System.out.println(FTP1.getAvailabeText());
        
        //Get a file */
        FTP1.getFile("fil.txt");
        System.out.println(FTP1.getAvailabeText());
        /*
        //Get a file bigger than 1KB
        FTP.getFile("NOR-gate.png");
        
        //Create a file
        String data = "Hello, how are you doing? :D";
        byte[] content = data.getBytes();
        FTP.createFile("NewFile.txt", content);
    */
        } 
        catch (IOException ex) {
            System.out.println("Something went wrong, maybe the server does not accept connections?");
            ex.printStackTrace();
        } 
    }
    
}
