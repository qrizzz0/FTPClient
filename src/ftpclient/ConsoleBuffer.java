package ftpclient;

public class ConsoleBuffer {
    private String buffer = "";
    
    public void println(String message) {
        buffer += message + "\r\n";
    }
    
    public String getBuffer() {
        String output = buffer;
        buffer = "";
        return output;
    }
    
    public boolean messageInBuffer() {
        return !buffer.isBlank();
    }
    
}
