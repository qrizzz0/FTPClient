package ftpclient;

import java.io.IOException;

public class FTPClient {

    public static void main(String[] args) {
        try {
        FTPIO FTP = new FTPIO("emby.waii.dk", "idunno", "47");
        String s1;
        s1 = FTP.send("HELP");
        System.out.println(s1);
        
        var datasocket = FTP.initDataConnection();
        
        //Vi viser indhold i rodmappen
        FTP.send("LIST");
        
        s1 = FTP.readFromDataSocket(datasocket, false);
        System.out.println(s1);
        
        FTP.getFile("fil.txt");
        
        
        
        }
        catch (IOException ex) {
            System.out.println("Something went wrong, maybe the server does not accept connections?");
            ex.printStackTrace();
        }
    }
    
}
