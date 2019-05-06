package ftpclient;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class FTPSession {

    private Socket socket;
    private PrintStream ud;
    private BufferedReader ind;
    private int pingTime = 20; //Vi tilføjer en smule delay til ping der giver serveren tid til at processere.
    private final FTPSessionManager sessionManager;

    public FTPSession(FTPSessionManager sessionManager) throws IOException {
        this.sessionManager = sessionManager;
        initConnection();
    }
    
    private void initConnection() throws IOException {
        socket = new Socket(sessionManager.getHostname(), sessionManager.getPort());
        ud = new PrintStream(socket.getOutputStream());
        ind = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.pingTime += this.pingTime += pingHost(sessionManager.getHostname()); 
        sessionManager.logIn(this);
    }
    
    private int pingHost(String host) throws IOException {
        long starttime = System.currentTimeMillis();
        java.net.InetAddress.getByName(host).isReachable(1000);
        long endtime = System.currentTimeMillis();
        return (int) (endtime - starttime); 
    }
        
    private String readLines(BufferedReader ind) throws IOException {
        String response = "";
        String s1;
        int retries;
        int tryCount = 1;
        while (true) {
            if (ind.ready()) {
                tryCount = 1;
                s1 = ind.readLine() + "\r\n";
                response += s1;
            } else {
                //Hvis vi har fået tekst ud skal serveren ikke processere noget, derfor kun ping-afhængig
                if (response.isBlank()) {
                    retries = 8;
                } else {
                    retries = 2;
                }
                //Venter på mere data, faldende ventetid for hvert try for ikke at overdrive
                if (tryCount <= retries) {
                    try { Thread.sleep(pingTime); } catch (InterruptedException ex) {}
                    tryCount++;
                } else {
                    break;
                }
            }
        }
        return response;
    }

    public String getAvailableText() throws IOException{
        return readLines(ind);
    }
    
    public final String send(String message) throws IOException {
        System.out.println("Send: \"" + message + "\"");
        ud.println(message);
        ud.flush();
        return readLines(ind);
    }
    
    public final String sendForceFeedback(String message) throws IOException {
        String response = send(message);
        if (response.isBlank()) {
            return forceResponse();
        }
        
        return response;
    }

    public final Socket initDataConnection() throws IOException {
        
        boolean properResponse = false;
        StringTokenizer datasocket = null;
        int retryCount = 1;
        while (!properResponse) {
            //Ask server for passive data connection
            String response = sendForceFeedback("PASV");
            //Tokenize result and throw exception if not a proper data port
            datasocket = new StringTokenizer(response, "(,)");
            if (datasocket.countTokens() < 7) {
                System.out.println("Something went wrong with the datastream!");
                System.out.println("PASV gave response: " + response);
                if (retryCount <= 5) {
                    System.out.println("Clearing text from console and retrying.. Try: " + retryCount + "/5");
                    try { Thread.sleep(500); } catch (InterruptedException ex) {}
                    this.getAvailableText();
                    retryCount++;
                } else {
                    System.out.println("Retries failed! Aborting with exception!");
                    throw new IOException();
                }
            } else {
                properResponse = true;
            }
        }
        //First token unimportant
        datasocket.nextToken();
        //Next 4 is IP
        String IP = (datasocket.nextToken() + "." + datasocket.nextToken() + "." + datasocket.nextToken() + "." + datasocket.nextToken());
        //Next 2 is the passive port
        int dataport = (Integer.parseInt(datasocket.nextToken()) * 256) + Integer.parseInt(datasocket.nextToken());
        
        System.out.println("Datastream on port: " + dataport);
        
        return new Socket(IP, dataport);
    }
    
    public String getTextFromDataStream(Socket dataSocket) throws IOException {
        BufferedReader textBuffer = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
        return readLines(textBuffer);
    }
    
    public String forceResponse() throws IOException {
        String response = "";
        int retryCount = 1;
        while (response.isEmpty() && retryCount < 10) {
            try { Thread.sleep(500); } catch (InterruptedException ex) {}
            response = readLines(ind);
            System.out.println("Server not responding.. Retying: " + retryCount + "/10");
            retryCount++;
        }
        if (response.isEmpty()) {
            System.out.println("Forced response never came back. Killing session!");
            throw new IOException();
        }
        
        return response;
    }
    
    public void closeSession() throws IOException {
        ud.close();
        ind.close();
        socket.close();
        sessionManager.logOut(this);
    }
    
    public void restartSession() throws IOException {
        closeSession();
        initConnection();
    }
    
    public boolean socketAlive() throws IOException {
        boolean alive = true;
        String response = send("NOOP");
        alive = response.matches(".*200.*");
        alive = !socket.isClosed();
        return alive;
    }
    
    public String logString() {
        return "Plain Session";
    }

}
