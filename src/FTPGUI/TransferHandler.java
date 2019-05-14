package FTPGUI;

import ftpclient.FTPSessionManager;
import java.io.File;
import javax.swing.JTree;

public class TransferHandler {
    private FTPSessionManager sessionManager;
    private JTree jTreeRight;
    private JTree jTreeLeft;
    
    public TransferHandler(FTPSessionManager sessionManager, JTree jTreeLeft, JTree jTreeRight) {
        this.sessionManager = sessionManager;
        this.jTreeRight = jTreeRight;
        this.jTreeLeft = jTreeLeft;
    }
    
    public void startUpload(File file) {
        String destination = "/"; 
        //Sæt destination til hvorend der er valgt hvis der er valgt noget.
        if (jTreeRight.getSelectionPath() != null) {
            var remoteFile = (RemoteFile)jTreeRight.getSelectionPath().getLastPathComponent();
            destination = remoteFile.getPath();
            if (remoteFile.isFile()) {
                destination = destination.replace(remoteFile.getName(), "");
            }
        }
        
        try {
            var uploadSession = sessionManager.newUploadSession(file.getPath(), destination);
            new Thread(uploadSession).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void startDownload(RemoteFile remoteFile) {
        String destination;
        if (jTreeLeft.getSelectionPath() == null) {
            destination = ((File)jTreeLeft.getPathForRow(0).getLastPathComponent()).getPath();
        } else {
            File file = (File)jTreeLeft.getSelectionPath().getLastPathComponent();
            destination = file.getPath();
            if (file.isDirectory()) {
                if (System.getProperty("os.name").contains("Windows")) {
                    destination += "\\";
                } else {
                    destination += "/";
                }
            } else {
                destination = destination.replace(file.getName(), "");
            }
        }
        
        try {
            var downloadSession = sessionManager.newDownloadSession(remoteFile.getPath(), destination);
            new Thread(downloadSession).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
}
