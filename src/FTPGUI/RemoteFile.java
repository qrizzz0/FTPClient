package FTPGUI;

import ftpclient.FTPNavigationSession;
import java.io.IOException;

public class RemoteFile {
    private FTPNavigationSession navigator;
    private String path = "/";
    private String elementInfo = "d--------- 1 ftp ftp           0 Jan 01 00:00 /";  //If there is no new elementinfo we assume we are root.
    private String[] listElements = null;
    private RemoteFile parentFolder = null;
    private boolean elementsExplored = false;
    
    public RemoteFile(FTPNavigationSession navigator) {
        this.navigator = navigator;
    }
    
    public RemoteFile(FTPNavigationSession navigator, String elementInfo) {
        this(navigator);
        this.elementInfo = elementInfo;
    }
    
    public RemoteFile(FTPNavigationSession navigator, RemoteFile parent, String elementInfo) {
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
        if (this.isFile()) {
            path = this.path.replace(getName(), "");
            setParentExploredStatus(false);
        }
        this.elementsExplored = false;
        navigator.mkDir(path + dirName);
    }
    
    public void mkFile(String fileName) throws IOException {
        String path = this.path;
        if (this.isFile()) {
            path = this.path.replace(getName(), "");
            setParentExploredStatus(false);
        }
        this.elementsExplored = false;
        navigator.mkFile(path + fileName);
    }
    
    public void rename(String newName) throws IOException {
        String newPath = this.path.replace(getName(), newName);
        navigator.renameItem(path, newPath);
        setParentExploredStatus(false);
    }
    
    public void setElementsExplored(boolean setTo) {
        elementsExplored = setTo;
    }
    
    public void deleteMe() throws IOException {
        if (isFile()) {
            navigator.deleteFile(this.path);
        } else {
            //Slet mappe rekursivt!
            String[] contents = list();
            for (String content : contents) {
                RemoteFile fileForDeletion = new RemoteFile(navigator, this, content);
                fileForDeletion.deleteMe();
            }
            navigator.deleteDirectory(this.path);
        }
        setParentExploredStatus(false);
    }

    public RemoteFile getParentFolder() {
        return parentFolder;
    }
    
    private void setParentExploredStatus(boolean setTo) {
        parentFolder.setElementsExplored(setTo);
    }
    
    public FTPNavigationSession getNavigator() {
        return navigator;
    }
    
    @Override
    public String toString() {
        return getName(); //JTree bruger toString til at fange navnet på et objekt..
    }
    
    public boolean isRoot() {
        return path.equals("/");
    }
    
}
