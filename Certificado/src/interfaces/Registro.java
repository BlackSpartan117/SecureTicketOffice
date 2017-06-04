/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import DAO.CertificadoDAO;
import DAO.CuentaDAO;
import certificado.Certificado;
import certificado.CifradorRSA;
import configuracion.ConexionMySQL;
import configuracion.PropiedadConexion;
import entidades.Cuenta;
import java.awt.Image;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.JOptionPane;
//import java.awt.ToolKit;
/**
 *
 * @author kars
 */
public class Registro extends javax.swing.JFrame {

    private final CuentaDAO cdao;
    private final ConexionMySQL ConexionBD;
    /**
     * Creates new form Interfaz
     */
    public Registro() {
        initComponents();
        this.setLocationRelativeTo(null);
        String ruta = System.getProperty("user.dir")+ "/src/configuracion/config.properties";
        PropiedadConexion connProp = new PropiedadConexion(ruta);
        ConexionBD = new ConexionMySQL(connProp);
        ConexionBD.getConexion();
        cdao = new CuentaDAO(ConexionBD.getConn());
        cdao.actualizarParametros();
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
        jLabelAM = new javax.swing.JLabel();
        jTextFieldAP = new javax.swing.JTextField();
        jButtonRegistrarse = new javax.swing.JButton();
        jTextFieldAM = new javax.swing.JTextField();
        jLabelAP = new javax.swing.JLabel();
        jTextFieldSaldoInicial = new javax.swing.JTextField();
        jLabelREgistro = new javax.swing.JLabel();
        jLabelNacimiento = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jLabelNommbres1 = new javax.swing.JLabel();
        jLabelNacimiento1 = new javax.swing.JLabel();
        jLabelSalir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(600, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 80, -1));

        jLabelAM.setBackground(java.awt.Color.white);
        jLabelAM.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelAM.setForeground(java.awt.Color.white);
        jLabelAM.setText("Apellido materno:");
        getContentPane().add(jLabelAM, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));
        getContentPane().add(jTextFieldAP, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 210, -1));

        jButtonRegistrarse.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonRegistrarse.setText("Registrarse");
        jButtonRegistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRegistrarseMouseClicked(evt);
            }
        });
        getContentPane().add(jButtonRegistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));
        getContentPane().add(jTextFieldAM, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 210, -1));

        jLabelAP.setBackground(java.awt.Color.white);
        jLabelAP.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelAP.setForeground(java.awt.Color.white);
        jLabelAP.setText("Apellido paterno:");
        getContentPane().add(jLabelAP, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));
        getContentPane().add(jTextFieldSaldoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 210, -1));

        jLabelREgistro.setBackground(java.awt.Color.white);
        jLabelREgistro.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabelREgistro.setForeground(java.awt.Color.white);
        jLabelREgistro.setText("Registro");
        getContentPane().add(jLabelREgistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        jLabelNacimiento.setBackground(java.awt.Color.white);
        jLabelNacimiento.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelNacimiento.setForeground(java.awt.Color.white);
        jLabelNacimiento.setText("$");
        getContentPane().add(jLabelNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, -1, -1));
        getContentPane().add(jTextFieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 210, -1));
        jTextFieldNombre.getAccessibleContext().setAccessibleDescription("");

        jLabelNommbres1.setBackground(java.awt.Color.white);
        jLabelNommbres1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelNommbres1.setForeground(java.awt.Color.white);
        jLabelNommbres1.setText("Nombre(s):");
        getContentPane().add(jLabelNommbres1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jLabelNacimiento1.setBackground(java.awt.Color.white);
        jLabelNacimiento1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelNacimiento1.setForeground(java.awt.Color.white);
        jLabelNacimiento1.setText("Saldo inicial:");
        getContentPane().add(jLabelNacimiento1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        jLabelSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondoPrincipal.jpg"))); // NOI18N
        getContentPane().add(jLabelSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonRegistrarseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRegistrarseMouseClicked
        String nombre = jTextFieldNombre.getText();
        String apellidoP = jTextFieldAP.getText();
        String apellidoM = jTextFieldAM.getText();
        String saldo = jTextFieldSaldoInicial.getText();
        if("".equals(nombre) || "".equals(apellidoP) || "".equals(apellidoM) || "".equals(saldo)){
            JOptionPane.showMessageDialog(null,
                "Todos los campos son obligatorios.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        double saldoInicial = 0;
        try{
            saldoInicial = Double.parseDouble(saldo);
        }catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null,
                "Los datos ingresados en el saldo inicial son incorrectos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Calendar c = Calendar.getInstance(); 
        c.setTime(sqlDate); 
        c.add(Calendar.YEAR, 5);
        java.sql.Date vigencia = new java.sql.Date(c.getTimeInMillis());
        Cuenta m = new Cuenta (0, nombre, apellidoP + " " + apellidoM, "0", "0", saldoInicial, vigencia);
        
        /********* Generar certificado **********/
        CifradorRSA cifrador = new CifradorRSA();
        KeyPair llavesCliente = cifrador.generarLlaves(2048);
        Certificado cer;
        
        cer = new Certificado("" + CuentaDAO.idActual, nombre + " " + apellidoP + " " + apellidoM, vigencia.toString(), llavesCliente.getPublic(), "Banxico", vigencia.toString());
        PrivateKey llavePrivadaBanco = (PrivateKey) cifrador.leerLlave("llaves/private.key", CifradorRSA.TipoLlave.PRIVADA);
        cer.firmar(llavePrivadaBanco);
        cifrador.guardarLlave("certificados/" + cer.getId() + ".key", llavesCliente.getPrivate(), CifradorRSA.TipoLlave.PRIVADA);
        System.out.println("Certificado generado");
        System.out.println(cer);
        
        entidades.Certificado elCertificado = new entidades.Certificado(CuentaDAO.idActual, vigencia, cer.getNombre(), vigencia, cer.getLlave().getEncoded(), cer.getFirma());
        CertificadoDAO certDao = new CertificadoDAO(ConexionBD.getConn());
        cdao.insertar(m);
        certDao.insertar(elCertificado);
        
        
        JOptionPane.showMessageDialog(null,
                "Se realizo el registro correctamente.\nNúmero de tarjeta: " + m.getNoTarjetaCredito() +
                        "\nCVV: " + m.getCvv() +"\nVigencia: " + m.getVigencia(),
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);
        jTextFieldNombre.setText("");
        jTextFieldAP.setText("");
        jTextFieldAM.setText("");
        jTextFieldSaldoInicial.setText("");
        
    }//GEN-LAST:event_jButtonRegistrarseMouseClicked

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
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonRegistrarse;
    private javax.swing.JLabel jLabelAM;
    private javax.swing.JLabel jLabelAP;
    private javax.swing.JLabel jLabelNacimiento;
    private javax.swing.JLabel jLabelNacimiento1;
    private javax.swing.JLabel jLabelNommbres1;
    private javax.swing.JLabel jLabelREgistro;
    private javax.swing.JLabel jLabelSalir;
    private javax.swing.JTextField jTextFieldAM;
    private javax.swing.JTextField jTextFieldAP;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldSaldoInicial;
    // End of variables declaration//GEN-END:variables
}
