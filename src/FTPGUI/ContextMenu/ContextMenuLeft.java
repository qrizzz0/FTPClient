/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTPGUI.ContextMenu;

import FTPGUI.RemoteFile;
import java.awt.event.MouseEvent;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Kristofer
 */
public class ContextMenuLeft extends ContextMenu {

    public ContextMenuLeft(RemoteFile remoteFile) {
        super(remoteFile);
    }

    @Override
    void makeDirMouseReleased(MouseEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
