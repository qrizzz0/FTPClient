package FTPGUI;

import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class JTreeRemoteExpanded extends JTree {
    
    public void expandRemoteFilePath(TreePath path) {
        // Only expand if not leaf!
        TreeModel          model = getModel();

        if(path != null && model != null &&
           !model.isLeaf(path.getLastPathComponent())) {
            
                for (int i = 0; i < getRowCount(); i++) {
                    if (((RemoteFile)path.getLastPathComponent()).getName().equals(((RemoteFile)getPathForRow(i).getLastPathComponent()).getName())) {
                        path = getPathForRow(i);
                    }
                }
            
            setExpandedState(path, true);
        }
    }
    
    public void refreshTree() {
        int rows = getRowCount();
        ArrayList<TreePath> expanded = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            if (isExpanded(i)) {
                System.out.println("Adding to arraylist!");
                expanded.add(getPathForRow(i));
            }
        }
        updateUI();
        
        expanded.forEach((path) -> expandRemoteFilePath(path));
    }
    
}
