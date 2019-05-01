package ftpclient.transfer;

import java.net.Socket;

public abstract class FTPTransferInterface implements Runnable {
    protected Socket dataSocket;
    protected String fileName;
    protected long processedBytes;
    protected long size;
    protected boolean Finished = false;
    
    
    public long getProcessedBytes() {
        return this.processedBytes;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
}
