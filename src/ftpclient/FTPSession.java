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
    private int pingTime = 2;

    public FTPSession(String host, int port, String user, String kode) throws IOException {
        long starttime = System.currentTimeMillis();
        java.net.InetAddress.getByName(host).isReachable(1000);
        long endtime = System.currentTimeMillis();
        this.pingTime += (int) (endtime - starttime); //Pingtid 2 højere end reelt.
        //Løser situtationer hvor serveren skal processere ting.

        socket = new Socket(host, port);
        ud = new PrintStream(socket.getOutputStream());
        ind = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Log ind
        String s1 = send("");
        System.out.println(s1);
        s1 = send("USER " + user);
        System.out.println(s1);
        s1 = send("PASS " + kode);
        System.out.println(s1);
    }

    private String readLines(BufferedReader ind) throws IOException {
        String svar = "";
        String s1;
        int retries = 0;
        while (true) {
            if (ind.ready()) {
                retries = 0;
                s1 = ind.readLine() + "\r\n";
                svar += s1;
            } else {
                if (retries < 3) {
                    try {
                        Thread.sleep(pingTime);
                    } catch (InterruptedException ex) {
                    }
                    retries++;
                } else {
                    break;
                }
            }
        }
        return svar;
    }

    public String send(String com) throws IOException {
        System.out.println("send: \"" + com + "\"");
        ud.println(com);
        ud.flush();
        return readLines(ind);
    }

    public Socket initDataConnection() throws IOException {
        //Ask server for passive data connection
        String response = send("PASV");

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
        var datasocket = initDataConnection();
        send("RETR " + filename);
        
        //Start download tråd her
        
    }

    public void createFile(String filename, byte[] content) throws IOException {
        var dataSocket = initDataConnection();
        send("STOR " + filename);
        //Start up-tråd her
    }

}
