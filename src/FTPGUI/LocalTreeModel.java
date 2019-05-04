/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTPGUI;

import java.io.File;
import javax.swing.event.TreeModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Kristofer
 */
public class LocalTreeModel implements TreeModel {
    protected File root;
    
    public LocalTreeModel() {
        if (System.getProperty("os.name").contains("Windows")) {
            this.root = new File("C:/");
        } else {
            this.root = new File("/");
        }
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object folder, int index) {
        String[] contents = ((File)folder).list();
        if (contents == null) {
            return null;
        } else if (index >= contents.length) {
            return -1;
        }
        
        return new File(((File)folder).getPath(), contents[index]);
    }

    @Override
    public int getChildCount(Object folder) {
        String[] contents = ((File)folder).list();
        return contents.length;
    }

    @Override
    public boolean isLeaf(Object file) {
        return ((File)file).isFile();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        String[] contents = ((File)parent).list();
        if (contents == null) {
            return -1;
        }
        
        String childname = ((File)child).getName();
        
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
