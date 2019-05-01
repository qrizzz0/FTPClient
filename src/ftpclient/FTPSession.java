package ftpclient;

import ftpclient.transfer.FTPDownloadHandler;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class FTPSession {

    private Socket socket;
    private PrintStream ud;
    private BufferedReader ind;
    private int pingTime = 20; //Vi tilføjer en smule delay til ping der giver serveren tid til at processere.

    public FTPSession(String host, int port, String user, String pass) throws IOException {
        socket = new Socket(host, port);
        ud = new PrintStream(socket.getOutputStream());
        ind = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        this.pingTime += this.pingTime += pingHost(host); 

        logIn(user, pass);
    }
        
    //Denne constructor står for de anonyme forbindelser:
    public FTPSession(String host, int port) throws IOException {
        socket = new Socket(host, port);
        ud = new PrintStream(socket.getOutputStream());
        ind = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.pingTime += pingHost(host);
        
        logIn("anonymous", "anonymous@domain.com");
    }

    private void logIn(String user, String pass) throws IOException {
        String s1 = send("");
        System.out.println(s1);
        s1 = send("USER " + user);
        System.out.println(s1);
        s1 = send("PASS " + pass);
        System.out.println(s1);
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
        int retries = 0;
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
                    try {
                        Thread.sleep(pingTime);
                    } catch (InterruptedException ex) {
                    }
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
    
    public String send(String message) throws IOException {
        System.out.println("Send: \"" + message + "\"");
        ud.println(message);
        ud.flush();
        return readLines(ind);
    }

    public Socket initDataConnection() throws IOException {
        int retryCount = 1;
        boolean properResponse = false;
        StringTokenizer datasocket = null;
        while (!properResponse) {
            //Ask server for passive data connection
            String response = send("PASV");
            //Tokenize result and throw exception if not a proper data port
            datasocket = new StringTokenizer(response, "(,)");
            if (datasocket.countTokens() < 7) {
                System.out.println("Something went wrong with the datastream!");
                System.out.println("PASV gave response: " + response);
                if (retryCount <= 5) {
                    System.out.println("Clearing text from console and retrying.. Try: " + retryCount + "/5");
                    try { Thread.sleep(500); } catch (InterruptedException ex) {}
                    this.getAvailableText();
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

        return new Socket(IP, dataport);
    }
    
    public FTPDownloadHandler getFile(String filename) throws IOException {
        String response = send("SIZE " + filename);
        //Size returnerer XXX XXXXXX, hvor først er responskode, anden er size i bytes.
        StringTokenizer sizetokenizer = new StringTokenizer(response, " \n\r");
        
        //Håndter hvis der ikke kommer rigtigt svar
        int retries = 1;
        while (sizetokenizer.countTokens() != 2 && retries <= 5) {
            System.out.println("Something went wrong, SIZE received: " + response);
            System.out.println("Retrying: " + retries + "/5");
            response = send("SIZE " + filename);
            sizetokenizer = new StringTokenizer(response, " \n\r");
            retries++;
        }
        
        sizetokenizer.nextToken();
        int size = Integer.parseInt(sizetokenizer.nextToken());
        
        var dataSocket = initDataConnection();
        send("RETR " + filename);
        
        //Start download tråd her
        FTPDownloadHandler FTPDownloader = new FTPDownloadHandler(dataSocket, filename, size);
        new Thread(FTPDownloader).start();
        return FTPDownloader;
    }
    
    public String getTextFromDataStream(Socket dataSocket) throws IOException {
        BufferedReader textBuffer = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
        dataSocket.close();
        return readLines(textBuffer);
    }

    public void createFile(String filename, byte[] content) throws IOException {
        var dataSocket = initDataConnection();
        send("STOR " + filename);
        //Start up-tråd her
    }

}
