package ftpclient.transfer;

import ftpclient.FTPSessionManager;
import java.io.IOException;

public class FTPUploadHandler extends FTPTransferInterface implements Runnable {

    public FTPUploadHandler(FTPSessionManager sessionManager) throws IOException {
        super(sessionManager);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*public FTPUploadHandler(FTPSessionManager sessionManager, String fileName, byte[] content) throws IOException {

    }
    
    public FTPUploadHandler(FTPSessionManager sessionManager, String localFileLocation) throws IOException {
        this.fileName = fileName;
        this.session = sessionManager.startNewSession();
        this.dataSocket = session.initDataConnection();
        session.send("STOR " + fileName);
        new Thread(this).start();        
    }
    
    @Override
    public void run() {
        uploadFile();
    }
    
    private void uploadFile() {
        //PrintStream dataStream = new PrintStream(dataSocket.getOutputStream());
        //dataStream.write(content);
        //dataStream.close();
        //dataSocket.close();
    }
    
    @Override
    public String logString() {
        return "Upload: " + super.logString();
    } */
    
}
