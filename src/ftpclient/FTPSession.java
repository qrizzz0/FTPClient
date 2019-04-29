package ftpclient;

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
    private int pingTime = 2; //Vi tilføjer en smule delay til ping der giver serveren tid til at processere.

    public FTPSession(String host, int port, String user, String pass) throws IOException {
        socket = new Socket(host, port);
        ud = new PrintStream(socket.getOutputStream());
        ind = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        this.pingTime += this.pingTime += pingHost(host);; 

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
        send("");
        System.out.println(readLines());
        send("USER " + user);
        System.out.println(readLines());
        send("PASS " + pass);
        System.out.println(readLines());
    }
    
    private int pingHost(String host) throws IOException {
        long starttime = System.currentTimeMillis();
        java.net.InetAddress.getByName(host).isReachable(1000);
        long endtime = System.currentTimeMillis();
        return (int) (endtime - starttime); 
    }
        
    public String readLines() throws IOException {
        String svar = "";
        String s1;
        int retries = 0;
        int trycount = 0;
        while (true) {
            if (ind.ready()) {
                trycount = 0;
                s1 = ind.readLine() + "\r\n";
                svar += s1;
            } else {
                if (svar.isBlank()) {
                    retries = 5;
                } else {
                    retries = 2;
                }
                if (trycount < retries) {
                    try {
                        Thread.sleep(pingTime);
                    } catch (InterruptedException ex) {
                    }
                    trycount++;
                } else {
                    break;
                }
            }
        }
        return svar;
    }

    public void send(String com) throws IOException {
        System.out.println("send: \"" + com + "\"");
        ud.println(com);
        ud.flush();
    }

    public Socket initDataConnection() throws IOException {
        //Ask server for passive data connection
        send("PASV");
        String response = readLines();
        //Tokenize result and throw exception if not a proper data port
        StringTokenizer datasocket = new StringTokenizer(response, "(,)");
        if (datasocket.countTokens() < 7) {
            System.out.println("Something went wrong with the datastream!");
            System.out.println("PASV gave response: " + response);
            throw new IOException();
        }
        //First token unimportant
        datasocket.nextToken();
        //Next 4 is IP
        String IP = (datasocket.nextToken() + "." + datasocket.nextToken() + "." + datasocket.nextToken() + "." + datasocket.nextToken());
        //Next 2 is the passive port
        int dataport = (Integer.parseInt(datasocket.nextToken()) * 256) + Integer.parseInt(datasocket.nextToken());

        return new Socket(IP, dataport);
    }
    
    public void getFile(String filename) throws IOException {
        var dataSocket = initDataConnection();
        send("RETR " + filename);
        
        //Start download tråd her
        FTPDownloadHandler FTPDownloader = new FTPDownloadHandler(dataSocket, filename);
        new Thread(FTPDownloader).start();
    }

    public void createFile(String filename, byte[] content) throws IOException {
        var dataSocket = initDataConnection();
        send("STOR " + filename);
        //Start up-tråd her
    }

}
