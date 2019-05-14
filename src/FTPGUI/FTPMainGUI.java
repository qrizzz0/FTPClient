package FTPGUI;

import FTPGUI.ContextMenu.ContextMenuJTree;
import FTPGUI.ContextMenu.ContextMenuJTreeLeft;
import FTPGUI.ContextMenu.ContextMenuJTreeRight;
import ftpclient.FTPNavigationSession;
import ftpclient.FTPSession;
import ftpclient.FTPSessionManager;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class FTPMainGUI extends javax.swing.JFrame {

    private LocalTreeModel localFileTree = new LocalTreeModel();
    private RemoteTreeModel remoteTreeModel;
    private TransferHandler transferHandler;

    public FTPMainGUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelConnect = new javax.swing.JPanel();
        jLabelHost = new javax.swing.JLabel();
        jTextFieldHost = new javax.swing.JTextField();
        jLabelUser = new javax.swing.JLabel();
        jTextFieldUser = new javax.swing.JTextField();
        jLabelPassword = new javax.swing.JLabel();
        jPasswordField = new javax.swing.JPasswordField();
        jLabelPort = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPaneConsole = new javax.swing.JScrollPane();
        jTextAreaConsole = new javax.swing.JTextArea();
        jScrollPaneTreeLeft = new javax.swing.JScrollPane();
        jTreeLeft = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeRight = new FTPGUI.JTreeRemote();
        jLabel1 = new javax.swing.JLabel();
        jScrollPaneSessions = new javax.swing.JScrollPane();
        jTextAreaSessions = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(800, 600));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanelConnect.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelConnect.setPreferredSize(new java.awt.Dimension(800, 30));

        jLabelHost.setText("Host:");

        jLabelUser.setText("Username:");

        jTextFieldUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUserActionPerformed(evt);
            }
        });

        jLabelPassword.setText("Password:");

        jPasswordField.setToolTipText("");

        jLabelPort.setText("Port:");

        jTextFieldPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPortActionPerformed(evt);
            }
        });

        jButton1.setText("Connect");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelConnectLayout = new javax.swing.GroupLayout(jPanelConnect);
        jPanelConnect.setLayout(jPanelConnectLayout);
        jPanelConnectLayout.setHorizontalGroup(
            jPanelConnectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConnectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelHost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldHost, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelPort)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 88, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanelConnectLayout.setVerticalGroup(
            jPanelConnectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConnectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabelPort)
                .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1)
                .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelConnectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextFieldHost, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabelHost)
                .addComponent(jLabelUser)
                .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabelPassword))
        );

        jScrollPaneConsole.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneConsole.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPaneConsole.setAutoscrolls(true);

        jTextAreaConsole.setColumns(20);
        jTextAreaConsole.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextAreaConsole.setRows(5);
        jTextAreaConsole.setAlignmentX(0.0F);
        jTextAreaConsole.setAlignmentY(0.0F);
        jTextAreaConsole.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jTextAreaConsoleComponentAdded(evt);
            }
        });
        jScrollPaneConsole.setViewportView(jTextAreaConsole);

        jScrollPaneTreeLeft.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneTreeLeft.setToolTipText("");
        jScrollPaneTreeLeft.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPaneTreeLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPaneTreeLeftMouseClicked(evt);
            }
        });
        jScrollPaneTreeLeft.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jScrollPaneTreeLeftPropertyChange(evt);
            }
        });

        jTreeLeft.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTreeLeft.setModel(localFileTree);
        jTreeLeft.setPreferredSize(null);
        jTreeLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTreeLeftMouseReleased(evt);
            }
        });
        jScrollPaneTreeLeft.setViewportView(jTreeLeft);

        jTreeRight.setModel(remoteTreeModel);
        jTreeRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTreeRightMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeRight);

        jLabel1.setText("Current Sessions:");

        jScrollPaneSessions.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneSessions.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTextAreaSessions.setColumns(20);
        jTextAreaSessions.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        jTextAreaSessions.setRows(5);
        jTextAreaSessions.setAlignmentX(0.0F);
        jTextAreaSessions.setAlignmentY(0.0F);
        jScrollPaneSessions.setViewportView(jTextAreaSessions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
            .addComponent(jScrollPaneConsole)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPaneTreeLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPaneSessions)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelConnect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPaneConsole, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jScrollPaneTreeLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPaneSessions, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUserActionPerformed

    private void jTextFieldPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPortActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void jScrollPaneTreeLeftPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jScrollPaneTreeLeftPropertyChange

    }//GEN-LAST:event_jScrollPaneTreeLeftPropertyChange

    private void jScrollPaneTreeLeftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneTreeLeftMouseClicked

    }//GEN-LAST:event_jScrollPaneTreeLeftMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        boolean connect = true;
        String host = jTextFieldHost.getText();
        int port = 21;
        String username = jTextFieldUser.getText();
        String password = new String(jPasswordField.getPassword());
        
        if (host.isBlank()) {
                JOptionPane.showMessageDialog(null, "You need to enter a host to connect to!", "Enter host!", JOptionPane.WARNING_MESSAGE);
                connect = false;
        }
        
        if (!jTextFieldPort.getText().isBlank()) {
            try { 
                port = Integer.parseInt(jTextFieldPort.getText()); 
                if (port < 1 || port > 65535) {
                    throw new Exception();
                }
            } catch (Exception ex) { 
                connect = false;
                JOptionPane.showMessageDialog(null, "The port needs to be an integer value between 1-65535!", "Wrong port!", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        if (connect) {
        try {
                FTPSessionManager FTPLogin;
                if (username.isBlank() || password.isBlank()) {
                    FTPLogin = new FTPSessionManager(host, port);
                } else {
                    FTPLogin = new FTPSessionManager(host, port, username, password);
                }
                
                startConsoleUpdater(FTPLogin);
                
                setRemoteTree(FTPLogin);
                jTreeRight.setModel(remoteTreeModel);
                this.transferHandler = new TransferHandler(FTPLogin, jTreeLeft, jTreeRight);
                
            } catch (IOException ex) {
                ex.printStackTrace();
                connect = false;
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTreeLeftMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeLeftMouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            int selectedRow = jTreeLeft.getRowForLocation(evt.getX(), evt.getY());
            jTreeLeft.setSelectionRow(selectedRow);
            
            var treePath = jTreeLeft.getPathForRow(selectedRow);
            if (treePath == null) {  
                    if (jTreeLeft.getPathForRow(0) == null) {
                        //Vis ikke menu!
                    } else {
                        treePath = jTreeLeft.getPathForRow(0);
                    }
            }
            
            ContextMenuJTreeLeft menu = new ContextMenuJTreeLeft(treePath, jTreeLeft, transferHandler);
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTreeLeftMouseReleased

    private void jTreeRightMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeRightMouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            int selectedRow = jTreeRight.getRowForLocation(evt.getX(), evt.getY());
            jTreeRight.setSelectionRow(selectedRow);
           
            var treePath = jTreeRight.getPathForRow(selectedRow);
            if (treePath == null) {  
                    if (jTreeRight.getPathForRow(0) == null) {
                        //Vis ikke menu!
                    } else {
                        treePath = jTreeRight.getPathForRow(0);
                    }
            }
            
            ContextMenuJTreeRight menu = new ContextMenuJTreeRight(treePath, jTreeRight, transferHandler);
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTreeRightMouseReleased

    private void jTextAreaConsoleComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jTextAreaConsoleComponentAdded
        
    }//GEN-LAST:event_jTextAreaConsoleComponentAdded

    public void startConsoleUpdater(FTPSessionManager sessionManager) {
        Thread consoleUpdater = new Thread() {
            public void run() {
                try { Thread.sleep(25); } catch (Exception ex) {}
                while (1 != 2) { //Kør for evigt..
                    if (sessionManager.messageExists()) {
                        jTextAreaConsole.append(sessionManager.getBufferMessages());
                        jTextAreaConsole.revalidate();
                        jTextAreaConsole.setCaretPosition(jTextAreaConsole.getDocument().getLength() - 1);
                    }
                    try { Thread.sleep(25); } catch (Exception ex) {}
                }
            }
        };
        Thread sessionUpdater = new Thread() {
            public void run() {
                while (1 != 2) { //Kør for evigt..
                    jTextAreaSessions.setText(sessionManager.sessionInfo());
                    jTextAreaSessions.revalidate();
                    try { Thread.sleep(250); } catch (Exception ex) {}
                }
            }
        };
        
        consoleUpdater.start();
        sessionUpdater.start();
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    //javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FTPMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FTPMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FTPMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FTPMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FTPMainGUI().setVisible(true);
            }
        });
    }

    public void setRemoteTree(FTPSessionManager sessionManager) throws IOException {
        remoteTreeModel = new RemoteTreeModel(sessionManager.newNavigationSession());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelHost;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPort;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JPanel jPanelConnect;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneConsole;
    private javax.swing.JScrollPane jScrollPaneSessions;
    private javax.swing.JScrollPane jScrollPaneTreeLeft;
    private javax.swing.JTextArea jTextAreaConsole;
    private javax.swing.JTextArea jTextAreaSessions;
    private javax.swing.JTextField jTextFieldHost;
    private javax.swing.JTextField jTextFieldPort;
    private javax.swing.JTextField jTextFieldUser;
    private javax.swing.JTree jTreeLeft;
    private FTPGUI.JTreeRemote jTreeRight;
    // End of variables declaration//GEN-END:variables
}
