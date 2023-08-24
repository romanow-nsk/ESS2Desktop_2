/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUILevelWB;

/**
 *
 * @author Admin
 */
public class WizardMeta2GUILevelWB extends WizardMeta2GUIRegW2 {

    /**
     * Creates new form WizardESS2GUILevelWB
     */
    private Meta2GUILevelWB elem;
    public WizardMeta2GUILevelWB() {
        initComponents();
    }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        elem =(Meta2GUILevelWB)entity0;
        LevelHigh.setText(""+elem.getHighLevel());
        LevelLow.setText(""+elem.getLowLevel());
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LevelLow = new javax.swing.JTextField();
        LevelHigh = new javax.swing.JTextField();

        jLabel1.setText("Уровень 0");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(250, 215, 70, 16);

        jLabel2.setText("Уровень 1");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(250, 185, 70, 16);

        LevelLow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                LevelLowKeyReleased(evt);
            }
        });
        getContentPane().add(LevelLow);
        LevelLow.setBounds(330, 210, 64, 25);

        LevelHigh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LevelHighKeyPressed(evt);
            }
        });
        getContentPane().add(LevelHigh);
        LevelHigh.setBounds(330, 180, 64, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LevelHighKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LevelHighKeyPressed
        onKeyPressedFloat("levelHigh", LevelHigh, evt, new I_WizardActionFloat() {
            @Override
            public void onAction(float value) {
                elem.setHighLevel(value);
            }
        });
    }//GEN-LAST:event_LevelHighKeyPressed

    private void LevelLowKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LevelLowKeyReleased
        onKeyPressedFloat("levelLow", LevelLow, evt, new I_WizardActionFloat() {
            @Override
            public void onAction(float value) {
                elem.setLowLevel(value);
            }
        });
    }//GEN-LAST:event_LevelLowKeyReleased

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
            java.util.logging.Logger.getLogger(WizardMeta2GUILevelWB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUILevelWB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUILevelWB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUILevelWB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WizardMeta2GUILevelWB().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField LevelHigh;
    private javax.swing.JTextField LevelLow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
