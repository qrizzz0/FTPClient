package ftpclient;

import ftpclient.transfersession.FTPDownloadHandler;
import ftpclient.transfersession.FTPUploadHandler;
import java.io.IOException;
import java.util.ArrayList;

public class FTPSessionManager {
    private String hostname;
    private int port;
    private String username;
    private String password;
    private ArrayList<FTPSession> sessionList;
    private ConsoleBuffer consoleBuffer = new ConsoleBuffer();
    
    public FTPSessionManager(String hostname, int port, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        sessionList = new ArrayList<>();
    }
    
    public FTPSessionManager(String hostname, int port) {
        this(hostname, port, "anonymous", "anonymous@domain.com");
    }
    
    public void logIn(FTPSession session) throws IOException {
        String s1 = session.send("");
        buffer_println(s1);
        s1 = session.sendForceFeedback("USER " + username);
        buffer_println(s1);
        s1 = session.sendForceFeedback("PASS " + password);
        if (s1.contains("530 ")) {
            throw new IOException();
        }
        buffer_println(s1);
        sessionList.add(session);
    }
    
    public void logOut(FTPSession session) throws IOException {
        sessionList.remove(session);
    }
    
    public String getHostname() {
        return hostname;
    }
    
    public int getPort() {
        return port;
    }
    
    public FTPSession newSession() throws IOException {
        return new FTPSession(this);
    }
    
    public FTPNavigationSession newNavigationSession() throws IOException {
        return new FTPNavigationSession(this);
    }
        
    public FTPDownloadHandler newDownloadSession(String path, String destination) throws IOException {
        var downloadSession = new FTPDownloadHandler(this, path, destination);
        return downloadSession;
    }
    
    public FTPUploadHandler newUploadSession(String file, String destination) throws IOException {
        FTPUploadHandler uploadSession;
        if (destination == null) {
            uploadSession = new FTPUploadHandler(this, file);
        } else {
            return new FTPUploadHandler(this, file, destination);
        }
        return uploadSession;
    }
    
    public String sessionInfo() {
        String result = "";
        //for (FTPSession session : sessionList) { //En tråd kan tilføje/fjerne sig selv her mens det køres, det dræber foreach	
        for (int i = 1; i <= sessionList.size(); i++) {
            result += i + ": " + sessionList.get(i-1).sessionString() + "\n";
        }   		
        return result;
    }
    
    public void buffer_println(String message) {
        consoleBuffer.println(message);
    }
    
    public String getBufferMessages() {
        return consoleBuffer.getBuffer();
    }
    
    public boolean messageExists() {
        return consoleBuffer.messageInBuffer();
    }
}
