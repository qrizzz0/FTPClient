package ftpclient;

import ftpclient.transfersession.FTPDownloadHandler;
import ftpclient.transfersession.FTPUploadHandler;
import java.io.IOException;

public class FTPClient {
    public static void main(String[] args) {
        try {
        //Connect to ftp server
        FTPSessionManager FTP = new FTPSessionManager("emby.waii.dk", 21, "idunno", "47");
        FTPSession FTPSession1 = new FTPSession(FTP);
        //FTPSession FTP1 = new FTPSession("127.0.0.1", 21, "idunno", "47");
        //FTPSession FTP2 = new FTPSession("mirror.internode.on.net", 21, "anonymous", "anonymous@domain.com");
        
        
        //Send and print HELP
        String s1;
        s1 = FTPSession1.send("HELP");
        System.out.println(s1);
        
        //s1 = FTP2.send("HELP");
        System.out.println(FTPSession1.getAvailableText());
        
        FTPNavigationHandler FTP1CMD = new FTPNavigationHandler(FTP);
        System.out.println(FTP1CMD.listCurrentFolder());

        
        //FTP1CMD.cd("Test Directory/Hej/Med/Dig");
        //System.out.println(FTP1CMD.listCurrentFolder());
        
        //FTP1CMD.cd("..");
        //System.out.println(FTP1CMD.listCurrentFolder());
        
        //FTP1CMD.cd("..");
        //System.out.println(FTP1CMD.listCurrentFolder());
        //FTP1CMD.cd("..");
        //System.out.println(FTP1CMD.listCurrentFolder());
        //FTP1CMD.cd("..");
        
        
        
        //Get a file */
        //FTP1.getFile("fil.txt");
        System.out.println(FTPSession1.getAvailableText());
        
        //Get a file bigger than 1KB
        //FTP1.getFile("dotNetFx45.exe");
        
        FTPDownloadHandler test = new FTPDownloadHandler(FTP, "FlixGrab+_v1.5.8.323_Cracked_By_DFoX.rar");
                
        new FTPUploadHandler(FTP,"Courage.the.Cowardly.Dog.S04E22.DANiSH.480p.WEBRip.H.265-WhyNot.mp4");
        //FTPDownloadHandler test = FTP1.getFile("gerudo-atlantis.the.lost.empire.bd.r00");
        //FTPSession FTP2 = new FTPSession("emby.waii.dk", 21, "idunno", "47");
        FTPDownloadHandler lubuntu = new FTPDownloadHandler(FTP, "lubuntu-18.04-desktop-amd64.iso");
        //FTP1.getFile("ahem.png");
        
        while (1 != 2) {
            System.out.println(FTP.sessionInfo());
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
        }
        
        
        
        } 
        catch (IOException ex) {
            System.out.println("Something went wrong, maybe the server does not accept connections?");
            ex.printStackTrace();
        } 
    }
    
}
