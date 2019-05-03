package ftpclient;

import java.io.IOException;
import java.util.StringTokenizer;

public class FTPNavigationHandler extends FTPSession {
    private final FTPSessionManager sessionManager;
    private String lastDirectory = "/";
    
    public FTPNavigationHandler(FTPSessionManager sessionManager) throws IOException {
        super(sessionManager);
        this.sessionManager = sessionManager;
    }
    
    public FTPNavigationHandler(FTPSessionManager sessionManager, String startDirectory) throws IOException {
        super(sessionManager);
        this.sessionManager = sessionManager;
        cd(startDirectory); 
    }
    
    public String listCurrentFolder() throws IOException {
        //Initialize passive data connection (for LIST)
        var dataSocket = initDataConnection();
        //List root folder
        String response = send("LIST");
        String list = getTextFromDataStream(dataSocket);
        
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
        
        StringTokenizer directoryTrim = new StringTokenizer(response, "\"");
        if (directoryTrim.countTokens() < 3) {
            if (response.contains("421 ")) {
                System.out.println("Control connection closed - restarting session!");
                restartSession();
                return getCurrentFolder();
            } else {
            System.out.println("Something went wrong with the datastream!");
            System.out.println("PWD gave response: " + response);
            closeSession();
            }
        }
        directoryTrim.nextToken();
        return (directoryTrim.nextToken());
    }
    
    public String getLastDirectory() {
        return lastDirectory;
    }
    
    @Override
    public void restartSession() throws IOException {
        super.restartSession();
        cd(lastDirectory);
    }
    
    @Override
    public String logString() {
        try { 
            return "Navigation Session current folder: " + getCurrentFolder(); 
        } catch (IOException ex) {
            return "Session dead";
        }
    }
}
