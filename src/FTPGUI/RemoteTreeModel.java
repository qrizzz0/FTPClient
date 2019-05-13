package FTPGUI;

import ftpclient.FTPNavigationSession;
import java.io.IOException;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class RemoteTreeModel implements TreeModel {
    protected RemoteFile root;
    private FTPNavigationSession navigator;
    
    public RemoteTreeModel(FTPNavigationSession navigator) {
        this.navigator = navigator;
        root = new RemoteFile(navigator);
    }
    
    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object folder, int index) {
        String[] contents = null;
        try { contents = ((RemoteFile)folder).list(); }
        catch (IOException ex) { ex.printStackTrace(); }
        if (contents == null) {
            return null;
        } else if (index >= contents.length) {
            return -1;
        }
        return new RemoteFile(navigator, (RemoteFile)folder, contents[index]);
    }

    @Override
    public int getChildCount(Object folder) {
        String[] contents = null;
        try { contents = ((RemoteFile)folder).list(); }
        catch (IOException ex) { ex.printStackTrace(); }
        if (contents == null) return 0;
        return contents.length;
    }

    @Override
    public boolean isLeaf(Object file) {
        return ((RemoteFile)file).isFile();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        String[] contents = null;
        try { contents = ((RemoteFile)parent).list(); }
        catch (IOException ex) { ex.printStackTrace(); }
        if (contents == null) {
            return -1;
        }
        
        String childname = ((RemoteFile)child).getName();
        
        for(int i = 0; i < contents.length; i++) {
            if (childname.equals(contents[i])) {
                return i;
            }
        }
        
        return -1;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        //Træ kan ikke ændres, så ligegyldig..
    }
    
    @Override
    public void addTreeModelListener(TreeModelListener l) {
        //Det skal gå den anden vej, træet skal opdateres af downloads, men resten af programmet er ligeglad med træet.
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        //Samme som add..
    }
    
}
