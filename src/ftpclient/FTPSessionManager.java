package ftpclient;

import ftpclient.transfer.FTPDownloadHandler;
import ftpclient.transfer.FTPUploadHandler;
import java.io.IOException;
import java.util.ArrayList;

public class FTPSessionManager {
    private String hostname;
    private int port;
    private String username;
    private String password;
    private ArrayList<FTPSession> sessionList;
    
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
        System.out.println(s1);
        s1 = session.send("USER " + username);
        System.out.println(s1);
        s1 = session.send("PASS " + password);
        System.out.println(s1);
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
    
    public String getUsername() {
        return hostname;
    }
    
    public String getPassword() {
        return hostname;
    }
    
    public String sessionInfo() {
        String result = "";
        for (FTPSession session : sessionList) { 		      
            result += session.logString() + "\n";
        }   		
        return result;
    }
}
