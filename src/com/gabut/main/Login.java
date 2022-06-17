/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.gabut.main;


import java.sql.*;
import javax.swing.*;
import com.gabut.controller.Koneksi;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;

/**
 *
 * @author Anjayani
 */
public class Login extends javax.swing.JFrame {
        Connection conn;
	ResultSet rs = null;
	PreparedStatement pst = null;
       
    /**
     * Creates new form Login
     */
        
public Login() throws SQLException, ClassNotFoundException {
	this.conn = Koneksi.getKoneksi();
	initComponents();

      
    }

public void login(){
           String sql = "select * from tb_user where username=? and password=?";
	   
	   try {
	       pst= conn.prepareStatement(sql);
	       pst.setString(1, txt_user.getText());
	       pst.setString(2, String.valueOf(txt_pass.getPassword()));
	       rs=pst.executeQuery();
	       if(rs.next()){
		   JOptionPane.showMessageDialog(null,"Berhasil Login");
		   this.setVisible(false);
		   new Dashboard(rs.getString(1)).setVisible(true);
	       }else
		   JOptionPane.showMessageDialog(null,"Username dan Password Yang Anda Masukkan Salah");
               txt_pass.setText("");
               txt_user.setText("");
	   }catch (Exception e){
	       JOptionPane.showMessageDialog(null, e);
	   }

    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_user = new javax.swing.JTextField();
        txt_pass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 250, 252));
        jPanel1.setToolTipText("test aja");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/png/2.PNG"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/png/3.PNG"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 160, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/png/4.PNG"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 290, -1));

        txt_user.setBackground(new java.awt.Color(249, 250, 252));
        txt_user.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        txt_user.setForeground(new java.awt.Color(102, 102, 102));
        txt_user.setBorder(null);
        txt_user.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_userFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_userFocusLost(evt);
            }
        });
        jPanel1.add(txt_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, 300, 30));

        txt_pass.setBackground(new java.awt.Color(249, 250, 252));
        txt_pass.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        txt_pass.setForeground(new java.awt.Color(102, 102, 102));
        txt_pass.setBorder(null);
        txt_pass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_passFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_passFocusLost(evt);
            }
        });
        jPanel1.add(txt_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 300, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/png/1.PNG"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, -1, -1));

        jPanel3.setBackground(new java.awt.Color(116, 198, 94));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("LOGIN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel4)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/png/5.PNG"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        jPanel3.setBackground(new Color(90,172,68));
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        jPanel3.setBackground(new Color(116,198,94));
    }//GEN-LAST:event_jPanel3MouseExited

    private void txt_userFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_userFocusGained
        
    }//GEN-LAST:event_txt_userFocusGained

    private void txt_userFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_userFocusLost
       
    }//GEN-LAST:event_txt_userFocusLost

    private void txt_passFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passFocusGained
        
    }//GEN-LAST:event_txt_passFocusGained

    private void txt_passFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passFocusLost
       
    }//GEN-LAST:event_txt_passFocusLost

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
login();       
	
    }//GEN-LAST:event_jPanel3MouseClicked

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
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField txt_pass;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}