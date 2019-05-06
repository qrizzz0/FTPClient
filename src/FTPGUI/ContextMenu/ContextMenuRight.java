package FTPGUI.ContextMenu;

import FTPGUI.RemoteFile;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
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
        remoteTree.updateUI();
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
