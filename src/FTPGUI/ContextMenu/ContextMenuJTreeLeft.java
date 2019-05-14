/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTPGUI.ContextMenu;

import FTPGUI.TransferHandler;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

public class ContextMenuJTreeLeft extends ContextMenuJTree {
    JTree jTree;
    protected JMenuItem transfer = new JMenuItem("Upload");
    
    
    public ContextMenuJTreeLeft(TreePath treePath, JTree jTree, TransferHandler transferHandler) {
        super();
        add(transfer, 0);
        this.jTree = jTree;
        this.file = (File)treePath.getLastPathComponent();
        this.treePath = treePath;
        this.transferHandler = transferHandler;

        String winSysDrive = System.getenv("SystemDrive") + "\\";
        String fileName = ((File)file).getPath();
        if ( fileName.equals(winSysDrive) || ((File)file).equals("/") ) {
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
            transferHandler.startUpload(((File)file)); 
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    void makeDirMouseReleased(MouseEvent evt) {
        String folderName = JOptionPane.showInputDialog("Please enter a name for the folder:");
        try {
            if (folderName == null) throw new Exception();
            new File(((File)file).getPath() + folderName).mkdir(); 
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    void makeFileMouseReleased(MouseEvent evt) {
        String fileName = JOptionPane.showInputDialog("Please enter a name for the file:");
        try { 
            if (fileName == null) throw new Exception();
            new File(((File)file).getPath() + fileName).createNewFile(); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    void refreshMouseReleased(MouseEvent evt) {
        jTree.updateUI();
    }

    @Override
    void renameMouseReleased(MouseEvent evt) {
        String newName = JOptionPane.showInputDialog("Please enter a new name:");
        File newFile = new File(((File)file).getParent() + newName);
        try { 
            if (newName != null) ((File)file).renameTo(newFile); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    void deleteMouseReleased(MouseEvent evt) {
        int confirmdialog = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete" + ((File)file).getName(),"Warning",JOptionPane.YES_NO_OPTION);
        if(confirmdialog == JOptionPane.YES_OPTION){
            deleteFile(((File)file));
        }
    }
    
    //Sletter mapper rekursivt...
    public void deleteFile(File file) {
        if (!((File)file).isFile()) {
            File[] filelist = file.listFiles();
            for (File fileToRemove : filelist) {
                deleteFile(fileToRemove);
            }
        }
        file.delete();
    }
    
    private void disableFileOptions() {
        transfer.setEnabled(false);
        rename.setEnabled(false);
        delete.setEnabled(false);
    }
    
}
