package ftpclient;

import java.io.IOException;
import java.util.StringTokenizer;

public class FTPNavigationSession extends FTPSession {
    private final FTPSessionManager sessionManager;
    private String lastDirectory = "/";
    
    public FTPNavigationSession(FTPSessionManager sessionManager) throws IOException {
        super(sessionManager);
        this.sessionManager = sessionManager;
    }
    
    public FTPNavigationSession(FTPSessionManager sessionManager, String startDirectory) throws IOException {
        super(sessionManager);
        this.sessionManager = sessionManager;
        cd(startDirectory); 
    }
    
    public String listCurrentFolder() throws IOException {
        //Initialize passive data connection (for LIST)
        var dataSocket = initDataConnection();
        //List root folder
        String response = send("LIST");
        String list = getTextFromSocket(dataSocket);
        
        System.out.println(response);
        
        return list;
    }
    
        public String listFolder(String path) throws IOException {
        //Initialize passive data connection (for LIST)
        var dataSocket = initDataConnection();
        //List root folder
        String response = send("LIST " + path);
        String list = getTextFromSocket(dataSocket);
        
        System.out.println(response);
        
        return list;
    }
    
    public final void cd(String folder) throws IOException {
        String currentDirectory = this.getCurrentFolder();
        switch (folder) {
            case "..":
                if (currentDirectory.equals("/")) {
                    //Do nothing!
                    break;
                }
                StringTokenizer folderTrim = new StringTokenizer(currentDirectory, "/");
                String previousFolder = "";
                //Vi springer første token over fordi den er tom (derfor <=)
                if (folderTrim.countTokens() == 1) {
                    previousFolder = "/";
                } else {
                    for (int i = 1; i < folderTrim.countTokens(); i++) {
                        previousFolder += "/" + folderTrim.nextToken();
                    }   
                }
                send("CWD \"" + previousFolder + "\"");
                System.out.println(getAvailableText());
                lastDirectory = previousFolder;
                break;
            case ".":
                //Do nothing :p
                break;
            default:
                send("CWD \"" + folder + "\"");
                System.out.println(getAvailableText());
                lastDirectory = folder;
                break;
        }
    }
    
    public String getCurrentFolder() throws IOException {
        String response = send("PWD");
        
        //I mangel på bedre, en smule farligt, men det kan ske at PWD ikke får svaret tilbage :p
        if (response.isBlank()) {
            return getCurrentFolder();
        }
        
        StringTokenizer directoryTrim = new StringTokenizer(response, "\"");
        if (directoryTrim.countTokens() < 3) {
            if (response.contains("421 ")) {
                System.out.println("Control connection for navigator closed - restarting session!");
                restartSession();
                return getCurrentFolder();
            } else {
            System.out.println("Something went wrong with the datastream!");
            System.out.println("PWD gave response: " + response);
            closeSession();
            return null;
            }
        }
        directoryTrim.nextToken();
        return (directoryTrim.nextToken());
    }
    
    public String getLastDirectory() {
        return lastDirectory;
    }
    
    public void mkFile(String path) throws IOException {
        java.net.Socket socket = initDataConnection();
        send("STOR " + path);
        socket.getOutputStream().flush();
        socket.close();
    }
    
    public void mkDir(String path) throws IOException {
        send("MKD " + path);
    }
    
    public void renameItem(String path, String newPath) throws IOException {
        send("RNFR " + path);
        send("RNTO " + newPath);
    }
    
    public void deleteFile(String path) throws IOException {
        send("DELE " + path);
    }
    
    public void deleteDirectory(String path) throws IOException {
        send("RMD " + path);
    }
    
    @Override
    public void restartSession() throws IOException {
        super.restartSession();
        cd(lastDirectory);
    }
    
    @Override
    public String sessionString() {
        try { 
            return "Navigation Session current folder: " + getCurrentFolder(); 
        } catch (IOException ex) {
            return "Session dead";
        }
    }
}
