package FTPGUI.ContextMenu;

import FTPGUI.JTreeRemote;
import FTPGUI.RemoteFile;
import FTPGUI.TransferHandler;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

public class ContextMenuJTreeRight extends ContextMenuJTree{
    JTreeRemote jTree;
    protected JMenuItem transfer = new JMenuItem("Download");
    
    
    public ContextMenuJTreeRight(TreePath treePath, JTreeRemote jTree, TransferHandler transferHandler) {
        super();
        add(transfer, 0);
        this.jTree = jTree;
        this.file = (RemoteFile)treePath.getLastPathComponent();
        this.treePath = treePath;
        this.transferHandler = transferHandler;
        
        if (((RemoteFile)file).isRoot()) {
            disableFileOptions();
        } 
        
        transfer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (transfer.isEnabled()) {
                    transferMouseReleased(evt);
                }
            }
        });
        
    }

    public void transferMouseReleased(MouseEvent evt) {
        try {
            transferHandler.startDownload(((RemoteFile)file)); 
        } catch (Exception ex) { ex.printStackTrace(); }
    }
            
    @Override
    public void makeDirMouseReleased(MouseEvent evt) {
        String folderName = JOptionPane.showInputDialog("Please enter a name for the folder:");
        try {
            if (folderName == null) throw new Exception();
            ((RemoteFile)file).mkDir(folderName); 
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public void makeFileMouseReleased(MouseEvent evt) {
        String fileName = JOptionPane.showInputDialog("Please enter a name for the file:");
        try { 
            if (fileName == null) throw new Exception();
            ((RemoteFile)file).mkFile(fileName); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public void refreshMouseReleased(MouseEvent evt) {
        if (file != null) {
            ((RemoteFile)file).setElementsExplored(false);
        }
        jTree.refreshTree();
    }

    @Override
    public void renameMouseReleased(MouseEvent evt) {
        String newName = JOptionPane.showInputDialog("Please enter a new name:");
        try { 
            if (newName != null) ((RemoteFile)file).rename(newName); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public void deleteMouseReleased(MouseEvent evt) {
        int confirmdialog = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete" + ((RemoteFile)file).getName(),"Warning",JOptionPane.YES_NO_OPTION);
        if(confirmdialog == JOptionPane.YES_OPTION){
            try { ((RemoteFile)file).deleteMe(); }
            catch (Exception ex) { ex.printStackTrace(); }
        }
    }
    
    private void disableFileOptions() {
            transfer.setEnabled(false);
            rename.setEnabled(false);
            delete.setEnabled(false);
    }
    
}
