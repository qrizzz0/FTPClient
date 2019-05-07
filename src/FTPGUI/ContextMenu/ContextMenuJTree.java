package FTPGUI.ContextMenu;

import FTPGUI.RemoteFile;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

public abstract class ContextMenuJTree extends JPopupMenu {
    protected JMenuItem makeDir = new JMenuItem("Create Directory...");
    protected JMenuItem makeFile = new JMenuItem("Create File...");
    protected JMenuItem refresh = new JMenuItem("Refresh");
    protected JMenuItem rename = new JMenuItem("Rename...");
    protected JMenuItem delete = new JMenuItem("Delete");
    protected Object file;
    protected TreePath treePath;
    
    public ContextMenuJTree() {
        add(makeDir);
        add(makeFile);
        add(refresh);
        add(rename);
        add(delete);
        //makeDir.setEnabled(false);
        
        System.out.println((RemoteFile)file);
        
        makeDir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                makeDirMouseReleased(evt);
            }
        });
        
        makeFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                makeFileMouseReleased(evt);
            }
        });
        
        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                refreshMouseReleased(evt);
            }
        });
        
        rename.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                renameMouseReleased(evt);
            }
        });
        
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                deleteMouseReleased(evt);
            }
        });
        
    }
    
    abstract void makeDirMouseReleased(java.awt.event.MouseEvent evt);
    abstract void makeFileMouseReleased(java.awt.event.MouseEvent evt);
    abstract void refreshMouseReleased(java.awt.event.MouseEvent evt);
    abstract void renameMouseReleased(java.awt.event.MouseEvent evt);
    abstract void deleteMouseReleased(java.awt.event.MouseEvent evt);

}