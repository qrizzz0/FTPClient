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

//Erstat evt. med binaryread
private String readLines(BufferedReader ind) throws IOException {
     String s1 = "";
     while (!ind.ready()) { } 
     while (true) {
        if (s1.isEmpty()) {
            s1 = ind.readLine(); 
            if (s1.length() >= 3 && Character.isDigit(s1.charAt(0)) && Character.isDigit(s1.charAt(1)) && Character.isDigit(s1.charAt(2)) && s1.charAt(3) != '-')
                break;
        } else {
            String s2 = ind.readLine();
            if (s2 == null)
                break;
            if (s2.isEmpty() || s2.length() >= 4 && s2.charAt(3) != '-') {
                s1 = s1 + "\n";
            }
            s1 = s1 + s2;
            if (s2.length() >= 4 && Character.isDigit(s2.charAt(0)) && Character.isDigit(s2.charAt(1)) && Character.isDigit(s2.charAt(2)) && s2.charAt(3) != '-')
                break;
        }
    } 
    return s1;
}
 
 public String send(String com) throws IOException{
     System.out.println("send: " + com);
     ud.println(com);
     ud.flush();
     //Fix:
     if(com.contains("LIST") || com.contains("RETR")) {
         String s1 = readLines(ind);
         s1 = s1 + readLines(ind);
         return s1;
     }
     //---
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
    int dataport = (Integer.parseInt(datasocket.nextToken())*256) + Integer.parseInt(datasocket.nextToken()); 
    
    return new Socket (IP, dataport);
 }
 
 public byte[] readFromDataSocket(Socket dataSocket) throws IOException {
    var dataStream = dataSocket.getInputStream();
    byte[] buffer = new byte[1];
    int read = 1;
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    
    //Lav til for loop for at tage downloads i bidder, ellers dør det hele når filer bliver for store
    while ((read = dataStream.read(buffer)) > 0) {
        byteStream.write(buffer);
    }
    
    byte[] outBytes = byteStream.toByteArray();
    
    dataStream.close();
    dataSocket.close();
    
    return outBytes;
 }
 
 public void getFile(String filename) throws IOException {
    var datasocket = initDataConnection();
    send("RETR " + filename);
    byte[] readBytes = readFromDataSocket(datasocket);
     
    try (FileOutputStream fos = new FileOutputStream(filename)) {
        fos.write(readBytes);   
    }
 }
 
 public void createFile(String filename, byte[] content) throws IOException {
     var dataSocket = initDataConnection();
     send("STOR " + filename);
     PrintStream dataStream = new PrintStream(dataSocket.getOutputStream());
     dataStream.write(content);
     dataStream.close();
     dataSocket.close();
     System.out.println("File: " + filename + " delivered to server!");
 }
}