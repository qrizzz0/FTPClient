package FTPGUI.ContextMenu;

import FTPGUI.RemoteFile;
import java.awt.event.MouseEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ContextMenuRight extends ContextMenu{
    
    public ContextMenuRight(RemoteFile remoteFile) {
        super(remoteFile);
    }

    @Override
    void makeDirMouseReleased(MouseEvent evt) {
        System.out.println("Make dir!");
    }

    @Override
    void makeFileMouseReleased(MouseEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void refreshMouseReleased(MouseEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
