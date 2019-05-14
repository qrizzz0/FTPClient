
import ftpclient.FTPSessionManager;
import java.io.File;
import java.io.IOException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestBackend {
    
    public TestBackend() {
    }
    
    
    @Test
    public void testSessionManagerBasicSession() throws IOException {
        String host = "emby.waii.dk";
        int port = 21;
        FTPSessionManager instance = new FTPSessionManager(host, port, "idunno", "47");
        assertEquals(instance.getHostname(), host);
        assertEquals(instance.getPort(), port);
        
        var newsession = instance.newSession();
        String NOOPResponse = newsession.send("NOOP");
        Assert.assertThat(NOOPResponse, CoreMatchers.containsString("200"));
        
        assertEquals(newsession.socketAlive(), true);
    }
    
    @Test
    public void testNavigationSession() throws IOException {
        String host = "emby.waii.dk";
        int port = 21;
        FTPSessionManager instance = new FTPSessionManager(host, port, "idunno", "47");
                
        var newsession = instance.newNavigationSession();
        String NOOPResponse = newsession.send("NOOP");
        Assert.assertThat(NOOPResponse, CoreMatchers.containsString("200"));
        
        assertEquals(newsession.getCurrentFolder(), "/");
        newsession.cd("Test Directory");
        assertEquals(newsession.getCurrentFolder(), "/Test Directory");
        newsession.cd("TestFolder4");
        assertEquals(newsession.getCurrentFolder(), "/Test Directory/TestFolder4");
        newsession.cd("..");
        assertEquals(newsession.getCurrentFolder(), "/Test Directory");
        
        newsession.mkDir("Directory998877");
        newsession.cd("Directory998877");
        newsession.mkFile("TestFile");
        newsession.deleteFile("TestFile");
        newsession.cd("/Test Directory");
        newsession.deleteDirectory("Directory998877");
        
        String list = newsession.listCurrentFolder();
        Assert.assertThat(list, not(CoreMatchers.containsString("Directory998877")));
    }
    
    @Test
    public void testTransferSessions() throws IOException {
        String host = "emby.waii.dk";
        int port = 21;
        FTPSessionManager instance = new FTPSessionManager(host, port, "idunno", "47");
                
        
        var newuploadsession = instance.newUploadSession("manifest.mf", "/");
        newuploadsession.run();
        var newdownloadsession = instance.newDownloadSession("/manifest.mf", "DL.");
        newdownloadsession.run();
        File file = new File("DL.manifest.mf");
        File file2 = new File("manifest.mf");
        assertTrue(file.exists());
        
        assertEquals(file.length(), file2.length());
        
        file.delete();
        var newsession = instance.newNavigationSession();
        newsession.deleteFile("/manifest.mf");
        
        
    }
    
}
