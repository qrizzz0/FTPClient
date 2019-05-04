package FTPGUI;

public class FTPMainGUI extends javax.swing.JFrame {

    private LocalTreeModel localFileTree = new LocalTreeModel();

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
        jScrollPaneTreeRight = new javax.swing.JScrollPane();
        jTreeRight = new javax.swing.JTree();

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

        jTextAreaConsole.setColumns(20);
        jTextAreaConsole.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        jTextAreaConsole.setRows(5);
        jTextAreaConsole.setAlignmentX(0.0F);
        jTextAreaConsole.setAlignmentY(0.0F);
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
        jScrollPaneTreeLeft.setViewportView(jTreeLeft);

        jTreeRight.setPreferredSize(null);
        jScrollPaneTreeRight.setViewportView(jTreeRight);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
            .addComponent(jScrollPaneConsole)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPaneTreeLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTreeRight))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelConnect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPaneConsole, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneTreeLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneTreeRight, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(419, 419, 419))
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FTPMainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabelHost;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPort;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JPanel jPanelConnect;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JScrollPane jScrollPaneConsole;
    private javax.swing.JScrollPane jScrollPaneTreeLeft;
    private javax.swing.JScrollPane jScrollPaneTreeRight;
    private javax.swing.JTextArea jTextAreaConsole;
    private javax.swing.JTextField jTextFieldHost;
    private javax.swing.JTextField jTextFieldPort;
    private javax.swing.JTextField jTextFieldUser;
    private javax.swing.JTree jTreeLeft;
    private javax.swing.JTree jTreeRight;
    // End of variables declaration//GEN-END:variables
}
