package FTPGUI.ContextMenu;

import FTPGUI.RemoteFile;
import java.awt.event.MouseEvent;
import javax.swing.JTree;

public class ContextMenuRight extends ContextMenu{
    JTree remoteTree;
    
    public ContextMenuRight(RemoteFile remoteFile, JTree remoteTree) {
        super(remoteFile);
        file = remoteFile;
        this.remoteTree = remoteTree;
    }

    @Override
    void makeDirMouseReleased(MouseEvent evt) {
        System.out.println("Make dir!");
        System.out.println(((RemoteFile)file).getPath());
        try { ((RemoteFile)file).mkDir("TestFolder2"); }
        catch (Exception ex) { ex.printStackTrace(); }
        
        
    }

    @Override
    void makeFileMouseReleased(MouseEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void refreshMouseReleased(MouseEvent evt) {
        ((RemoteFile)file).setElementsExplored(false);
        remoteTree.updateUI();
    }

    @Override
    void renameMouseReleased(MouseEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void deleteMouseReleased(MouseEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
