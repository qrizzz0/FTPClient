package ftpclient;
import ftpclient.transfer.*;
import java.io.IOException;

public class FTPClient {
    public static void main(String[] args) {
        try {
        //Connect to ftp server
        FTPSession FTP1 = new FTPSession("emby.waii.dk", 21, "idunno", "47");
        //FTPSession FTP1 = new FTPSession("127.0.0.1", 21, "idunno", "47");
        //FTPSession FTP2 = new FTPSession("mirror.internode.on.net", 21, "anonymous", "anonymous@domain.com");
        
        
        //Send and print HELP
        String s1;
        s1 = FTP1.send("HELP");
        System.out.println(s1);
        
        //s1 = FTP2.send("HELP");
        System.out.println(FTP1.getAvailableText());
        
        FTPCMDHandler FTP1CMD = new FTPCMDHandler(FTP1);
        System.out.println(FTP1CMD.listCurrentFolder());

        
        FTP1CMD.cd("Test Directory/Hej/Med/Dig");
        System.out.println(FTP1CMD.listCurrentFolder());
        
        FTP1CMD.cd("..");
        System.out.println(FTP1CMD.listCurrentFolder());
        
        FTP1CMD.cd("..");
        System.out.println(FTP1CMD.listCurrentFolder());
        FTP1CMD.cd("..");
        System.out.println(FTP1CMD.listCurrentFolder());
        FTP1CMD.cd("..");
        
        
        
        //Get a file */
        //FTP1.getFile("fil.txt");
        System.out.println(FTP1.getAvailableText());
        
        //Get a file bigger than 1KB
        //FTP1.getFile("dotNetFx45.exe");
        //var test = FTP1.getFile("FlixGrab+_v1.5.8.323_Cracked_By_DFoX.rar");
        
        //FTPUploadHandler test = FTP1.uploadFile("Courage.the.Cowardly.Dog.S04E22.DANiSH.480p.WEBRip.H.265-WhyNot.mp4");
        FTPDownloadHandler test = FTP1.getFile("gerudo-atlantis.the.lost.empire.bd.r00");
        FTPDownloadHandler lubuntu = FTP1.getFile("lubuntu-18.04-desktop-amd64.iso");
        //FTP1.getFile("ahem.png");
        
        while (true) {
            System.out.println("Upload: " + test.getFileName() + "   -   " + test.getProcessedBytes() + "/" + test.getSize());
            System.out.println("Speed: " + test.getCurrentSpeed() + " Kb/s");
            System.out.println("Finished?: " + test.isFinished());
            System.out.println("Download: " + lubuntu.getFileName() + "   -   " + lubuntu.getProcessedBytes() + "/" + lubuntu.getSize());
            System.out.println("Speed: " + lubuntu.getCurrentSpeed() + " Kb/s");
            System.out.println("Finished?: " + lubuntu.isFinished());
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            
            if (44 == 43) {
                break;
            }
        }
        
        } 
        catch (IOException ex) {
            System.out.println("Something went wrong, maybe the server does not accept connections?");
            ex.printStackTrace();
        } 
    }
    
}
