package FTPGUI;

import ftpclient.FTPNavigationHandler;
import java.io.IOException;

public class RemoteFile {
    private FTPNavigationHandler navigator;
    private String path = "/";
    private String elementInfo = "d--------- 1 ftp ftp           0 Jan 01 00:00 /"; //If there is no new elementinfo we assume "/"
    private String[] listElements = null;
    private RemoteFile parentFolder = null;
    private boolean elementsExplored = false;
    
    public RemoteFile(FTPNavigationHandler navigator) {
        this.navigator = navigator;
    }
    
    public RemoteFile(FTPNavigationHandler navigator, String elementInfo) {
        this(navigator);
        this.elementInfo = elementInfo;
    }
    
    public RemoteFile(FTPNavigationHandler navigator, RemoteFile parent, String elementInfo) {
        this(navigator, elementInfo);
        this.parentFolder = parent;
        this.path = parent.getPath() + parseNameFromElement(elementInfo);
        if (!this.isFile()) {
            this.path += "/";
        }
    }
    
    public String[] list() throws IOException {
        if (!elementsExplored) {
            elementsExplored = true;
            String list = navigator.listFolder(path);
            if (list.isBlank()) {
                return null;
            }
            
            listElements = list.split("\r\n");
            for (int i = 0; i < listElements.length; i++) {
                System.out.println(listElements[i]);
            }
        }

        return listElements; 
    }
    
    private String parseNameFromElement(String element) {
        //String element = elementList[index];
        String[] splitBySpace = element.split("(:)\\d\\d "); //Regex matcher  : og tal, tal og space. Dette er ikke et mappe/filnavn pga. :
        return splitBySpace[splitBySpace.length - 1];
    }
    
    public String getName() {
        String[] splitBySpace = elementInfo.split("(:)\\d\\d "); //Regex matcher  : og tal, tal og space. Dette er ikke et mappe/filnavn pga. :
        return splitBySpace[splitBySpace.length - 1];
    }
    
    public String getPath() {
        return path;
    }
    
    public boolean isFile() {
        if (elementInfo.charAt(0) == 'd') {
            return false;
        }
        return true;
    }
    
    public void mkDir(String dirName) throws IOException {
        String path = this.path;
        if (isFile()) {
            path = this.path.replace(getName(), "");
        }
        elementsExplored = false;
        navigator.send("MKD " + path + dirName);
        setParentExploredStatus(false);
    }
    
    public void mkFile(String fileName) throws IOException {
        String path = this.path;
        if (isFile()) {
            path = this.path.replace(getName(), "");
        }
        elementsExplored = false;
        
        java.net.Socket socket = navigator.initDataConnection();
        navigator.send("STOR " + path + fileName);
        socket.getOutputStream().flush();
        socket.close();
        setParentExploredStatus(false);
    }
    
    public void rename(String newName) throws IOException {
        navigator.send("RNFR " + path);
        String path = this.path.replace(getName(), "");
        navigator.send("RNTO " + path + newName);
        setParentExploredStatus(false);
    }
    
    public void setElementsExplored(boolean setTo) {
        elementsExplored = setTo;
    }
    
    public void deleteMe() throws IOException {
        if (isFile()) {
            navigator.send("DELE " + this.path);
        } else {
            String s1 = navigator.send("RMDA " + path);
            //Hvis serveren ikke understøtter RMDA, slet alle filer & mapper rekursivt..
            if (s1.contains("500 ")) {
                String[] contents = list();
                for (String content : contents) {
                    RemoteFile fileForDeletion = new RemoteFile(navigator, this, content);
                    fileForDeletion.deleteMe();
                }
                navigator.send("RMD " + path);
            }
        }
        setParentExploredStatus(false);
    }

    public RemoteFile getParentFolder() {
        return parentFolder;
    }
    
    private void setParentExploredStatus(boolean setTo) {
        parentFolder.setElementsExplored(setTo);
    }
    
    public FTPNavigationHandler getNavigator() {
        return navigator;
    }
    
    @Override
    public String toString() {
        return getName(); //JTree bruger toString til at fange navnet på et objekt..
    }
    
}
