/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Image;
//import java.awt.ToolKit;

package Ventanas;


/**
 *
 * @author kars
 */
public class Interfaz_1 extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz
     */
    public Interfaz_1() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /*
    public Image getIconImage() {
        Image retValue = ToolKit.getDefaultToolKit().getImage(ClassLoader.getSystemResource("imagenes/bancologo.png"));
        return retValue;
    }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabelUsuarios = new javax.swing.JLabel();
        jLabelUsuario = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jPasswordPassword = new javax.swing.JPasswordField();
        jButtonISesion = new javax.swing.JButton();
        jButtonRegistrarse = new javax.swing.JButton();
        jLabelSalir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 390, 80, -1));

        jLabelUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuarios.png"))); // NOI18N
        getContentPane().add(jLabelUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 190, 180));

        jLabelUsuario.setBackground(java.awt.Color.white);
        jLabelUsuario.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelUsuario.setForeground(java.awt.Color.white);
        jLabelUsuario.setText("Usuario: ");
        getContentPane().add(jLabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, -1, -1));

        jLabelPassword.setBackground(java.awt.Color.white);
        jLabelPassword.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelPassword.setForeground(java.awt.Color.white);
        jLabelPassword.setText("Contraseña:");
        getContentPane().add(jLabelPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));
        getContentPane().add(jTextFieldUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 180, -1));
        getContentPane().add(jPasswordPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 180, -1));

        jButtonISesion.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonISesion.setText("Iniciar Sesión");
        getContentPane().add(jButtonISesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, -1, -1));

        jButtonRegistrarse.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonRegistrarse.setText("Registrarse");
        jButtonRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarseActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRegistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, -1, -1));

        jLabelSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoPrincipal.jpg"))); // NOI18N
        getContentPane().add(jLabelSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 541, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarseActionPerformed
        Registro a = new Registro();
        a.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonRegistrarseActionPerformed

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
            java.util.logging.Logger.getLogger(Interfaz_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz_1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonISesion;
    private javax.swing.JButton jButtonRegistrarse;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelSalir;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLabel jLabelUsuarios;
    private javax.swing.JPasswordField jPasswordPassword;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables
}
