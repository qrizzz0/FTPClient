package ftpclient;

import FTPGUI.RemoteFile;
import ftpclient.transfersession.FTPDownloadHandler;
import ftpclient.transfersession.FTPUploadHandler;
import java.io.IOException;

public class FTPClient {
    public static void main(String[] args) {
        try {
        //Connect to ftp server
        FTPSessionManager FTP = new FTPSessionManager("emby.waii.dk", 21, "idunno", "47");
        FTPSession FTPSession1 = FTP.newSession();
        
        
        //Send and print HELP
        String s1;
        s1 = FTPSession1.send("HELP");
        System.out.println(s1);
        
        System.out.println(FTPSession1.getAvailableText());
        
        FTPNavigationSession FTP1Nav = FTP.newNavigationSession();

        
        RemoteFile tester = new RemoteFile(FTP1Nav);
        var elementlist = tester.list();
        System.out.println("E2 Name: " + tester.getName());
        
        
        //Prøv lidt forskellige upload/downloads
        FTPDownloadHandler test = new FTPDownloadHandler(FTP, "FlixGrab+_v1.5.8.323_Cracked_By_DFoX.rar"); 
        new FTPUploadHandler(FTP, "Courage.the.Cowardly.Dog.S04E22.DANiSH.480p.WEBRip.H.265-WhyNot.mp4");
        FTPDownloadHandler lubuntu = new FTPDownloadHandler(FTP, "lubuntu-18.04-desktop-amd64.iso");
        
        //Opdater for evigt..
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
