package romanow.abc.desktop;

import org.openmuc.openiec61850.clientgui.ClientGui;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.dataserver.iec61850.IEC61850Client;

import javax.swing.*;

public class ESSMainBase extends MainBase{
    public ESSMainBase(){
        super();
        JButton ClientIEC61850 = new JButton();
        ClientIEC61850.setText("IEC61850");
        ClientIEC61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            System.setProperty("file.encoding","UTF-8");
                            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                            ClientGui gui = new ClientGui();
                            gui.setVisible(true);
                            gui.setBounds(300,200,800,600);
                        } catch (Exception ee) {}
                        }
                    });
                }
            });
        getContentPane().add(ClientIEC61850);
        ClientIEC61850.setBounds(40, 70, 110, 23);
        setBounds(200,200,180,150);
        }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            System.out.println(javax.swing.UIManager.getLookAndFeel().getName());
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName()+" "+info.getClassName());
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Values.init();
                new ESSMainBase().setVisible(true);
            }
        });
    }
}
