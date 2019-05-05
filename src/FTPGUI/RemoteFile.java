package FTPGUI;

import ftpclient.FTPNavigationHandler;
import java.io.IOException;

public class RemoteFile {
    FTPNavigationHandler navigator;
    String path = "/";
    String elementInfo = "d--------- 1 ftp ftp           0 Jan 01 00:00 /"; //If there is no new elementinfo we assume "/"
    String[] listElements = null;
    boolean elementsExplored = false;
    
    public RemoteFile(FTPNavigationHandler navigator) {
        this.navigator = navigator;
    }
    
    public RemoteFile(FTPNavigationHandler navigator, String elementInfo) {
        this(navigator);
        this.elementInfo = elementInfo;
    }
    
    public RemoteFile(FTPNavigationHandler navigator, String path, String elementInfo) {
        this(navigator, elementInfo);
        this.path = path + parseNameFromElement(elementInfo);
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

    @Override
    public String toString() {
        return getName();
    }
    
    
    
}