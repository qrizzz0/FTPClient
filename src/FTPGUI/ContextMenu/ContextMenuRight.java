package FTPGUI.ContextMenu;

import FTPGUI.JTreeRemoteExpanded;
import FTPGUI.RemoteFile;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

public class ContextMenuRight extends ContextMenu{
    JTreeRemoteExpanded jTree;
    
    public ContextMenuRight(TreePath treePath, JTreeRemoteExpanded jTree) {
        super(treePath.getLastPathComponent());
        this.treePath = treePath;
        this.jTree = jTree;
        file = (RemoteFile)treePath.getLastPathComponent();
    }

    @Override
    void makeDirMouseReleased(MouseEvent evt) {
        String folderName = JOptionPane.showInputDialog("Please enter a name for the folder:");
        try {
            if (folderName == null) throw new Exception();
            ((RemoteFile)file).mkDir(folderName); 
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    void makeFileMouseReleased(MouseEvent evt) {
        String fileName = JOptionPane.showInputDialog("Please enter a name for the file:");
        try { 
            if (fileName == null) throw new Exception();
            ((RemoteFile)file).mkFile(fileName); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    void refreshMouseReleased(MouseEvent evt) {
        ((RemoteFile)file).setElementsExplored(false);
        jTree.refreshTree();
    }

    @Override
    void renameMouseReleased(MouseEvent evt) {
        String newName = JOptionPane.showInputDialog("Please enter a new name:");
        try { 
            if (newName != null) ((RemoteFile)file).rename(newName); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    void deleteMouseReleased(MouseEvent evt) {
    int confirmdialog = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete" + ((RemoteFile)file).getName(),"Warning",JOptionPane.YES_NO_OPTION);
    if(confirmdialog == JOptionPane.YES_OPTION){
        try { ((RemoteFile)file).deleteMe(); }
        catch (Exception ex) { ex.printStackTrace(); }
    }
    
    
    }
    
}
