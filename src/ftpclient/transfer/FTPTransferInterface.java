package ftpclient.transfer;

import java.net.Socket;

public abstract class FTPTransferInterface implements Runnable {
    protected Socket dataSocket;
    protected String fileName;
    protected long processedBytes;
    protected long size;
    protected boolean finished = false;
    protected int bufferSize = 8096;
    
    //Til hastighedsudregning.
    private int speed; //Last calculated speed in kb/s
    private long currentTime;
    private long lastTime;
    private long lastProcessedBytes;
    
    public long getProcessedBytes() {
        return this.processedBytes;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    protected void speedCalculator(int bufferSize) {
        this.currentTime = System.currentTimeMillis();

        //Calculate speed every ~5 ms.
        long timeDifference = this.currentTime - this.lastTime;
        if (timeDifference > 25) {
            long byteDifference =  this.processedBytes - this.lastProcessedBytes;
            long BytesPerMilliSecond = (byteDifference / timeDifference);
            this.speed = (int) ((BytesPerMilliSecond * 1000) / 1024); //Milliseconds * 1000 = bits/s / 1024 = kbit/s / 8 = kb/s
            this.lastProcessedBytes = this.processedBytes;        
            this.lastTime = System.currentTimeMillis();
        }
        
    }
    
    public int getCurrentSpeed() {
        return this.speed;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
}
