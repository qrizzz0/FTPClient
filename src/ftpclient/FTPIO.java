package ftpclient;
import java.io.*;
import java.net.*;
import java.util.*;

public final class FTPIO {
    private final Socket socket;
    private final PrintStream ud;
    private final BufferedReader ind;
    
public FTPIO(String host, String user, String kode) throws IOException{
    socket = new Socket(host,21);
    ud = new PrintStream(socket.getOutputStream());
    ind = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String s1 = send("");
    System.out.println(s1);
    s1 = send("USER " + user);
    System.out.println(s1);
    s1 = send("PASS " + kode);
    System.out.println(s1);
    }

//Implementer med binary-read hvis muligt..
private String readLines(BufferedReader ind) throws IOException {
     String s1 = "";
     while (!ind.ready()) { } 
     while (true) {
        if (s1.isEmpty()) {
            s1 = ind.readLine(); 
            if (s1.charAt(0) + s1.charAt(1) + s1.charAt(2) >= 100 && s1.charAt(3) != '-')
                break;
        } else {
            String s2 = ind.readLine();
            s1 = s1 + "\n" + s2;
            if (s2.charAt(0) + s2.charAt(1) + s2.charAt(2) >= 100 && s2.charAt(3) != '-')
                break;
        }
    } 
    return s1;
}
 
 public String send(String com) throws IOException {
     System.out.println("send: " + com);
     ud.println(com);
     ud.flush();
     //Ryd op ---
     if(com.contains("LIST") || com.contains("RETR")) {
         String s1 = readLines(ind);
         s1 = s1 + readLines(ind);
         return s1;
     }
     //---
     return readLines(ind);

 }
 
 public Socket initDataConnection() throws IOException {
    String response = send("PASV");
    
    StringTokenizer datasocket = new StringTokenizer(response, "(,)");
    if (datasocket.countTokens() < 7) {
        System.out.println("Something went wrong with the datastream!");
        System.out.println("PASV gave response: " + response);
        throw new IOException();
    }
    datasocket.nextToken();
    String IP = (datasocket.nextToken() + "." + datasocket.nextToken() + "." + datasocket.nextToken() + "." + datasocket.nextToken());
    
    int dataport = (Integer.parseInt(datasocket.nextToken())*256) + Integer.parseInt(datasocket.nextToken()); 
    
    return new Socket (IP, dataport);
 }
 
 public String readFromDataSocket(Socket datasocket, boolean printFirstKB) throws IOException {
    var dataStream = new BufferedReader(new InputStreamReader(datasocket.getInputStream()));
    String s1 = readLines(dataStream);
    //Konverterer string til bytes og putter efterfølgende de første 1024 bytes ind i et andet bytearray og konverterer tilbage til string.
    if (printFirstKB) {
        byte[] byteArray = s1.getBytes();
        byte[] firstKilobyte = new byte[1024];
        for (int i = 0; i < 1024; i++) {
            if (i <= byteArray.length - 1) {
                firstKilobyte[i] = byteArray[i];
            }
        }
        String s2 = new String (firstKilobyte);
        System.out.println("First kilobyte: \n-------------------\n" + s2 + "\n-------------------\n");
    }
    return s1;
 }
 
 public void getFile(String filename) throws IOException {
     var datasocket = initDataConnection();
     send("RETR " + filename);
     String s1 = readFromDataSocket(datasocket, true);
     //System.out.println(s1);
     
 }
}