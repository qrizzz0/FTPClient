package ftpclient;

import java.io.IOException;
import java.util.StringTokenizer;

public class FTPCMDHandler {
    FTPSession session;
    
    public FTPCMDHandler(FTPSession session) {
        this.session = session;
    }
    
    public String listCurrentFolder() throws IOException {
        //Initialize passive data connection (for LIST)
        var dataSocket = session.initDataConnection();
        //List root folder
        session.send("LIST");
        String list = session.getTextFromDataStream(dataSocket);
        
        System.out.println(session.getAvailableText());
        
        return list;
    }
    
    public void cd(String folder) throws IOException {
        session.send("PWD");
        String response = session.getAvailableText();
        
        StringTokenizer directoryTrim = new StringTokenizer(response, "\"");
        if (directoryTrim.countTokens() < 3) {
            System.out.println("Something went wrong with the datastream!");
            System.out.println("PWD gave response: " + response);
            throw new IOException();
        }
        directoryTrim.nextToken();
        String currentDirectory = (directoryTrim.nextToken());

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
                session.send("CWD \"" + previousFolder + "\"");
                break;
            case ".":
                //Do nothing :p
                break;
            default:
                session.send("CWD \"" + folder + "\"");
                System.out.println(session.getAvailableText());
                break;
        }
    }
        
}
