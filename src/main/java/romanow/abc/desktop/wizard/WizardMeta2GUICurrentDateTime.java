/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUICurrentDateTime;
import romanow.abc.core.entity.metadata.view.Meta2GUIDateTime;

/**
 *
 * @author Admin
 */
public class WizardMeta2GUICurrentDateTime extends WizardMeta2GUILabel {

    /**
     * Creates new form WizardMeta2GUICurrentDateTime
     */
    private boolean onStart=false;
    private Meta2GUICurrentDateTime elem;
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        elem = (Meta2GUICurrentDateTime)  entity;
        onStart=true;
        OnlyTime.setSelected(elem.isOnlyTime());
        onStart=false;
        resizeHeight(250);
        }
    public WizardMeta2GUICurrentDateTime() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OnlyTime = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        OnlyTime.setText("Только время");
        OnlyTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OnlyTimeItemStateChanged(evt);
            }
        });
        getContentPane().add(OnlyTime);
        OnlyTime.setBounds(20, 130, 140, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OnlyTimeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OnlyTimeItemStateChanged
        if (onStart)
            return;
        elem.setOnlyTime(OnlyTime.isSelected());
        back.onEnter("Изменено onlyTime"+": "+OnlyTime.isSelected());
    }//GEN-LAST:event_OnlyTimeItemStateChanged

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
            java.util.logging.Logger.getLogger(WizardMeta2GUICurrentDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUICurrentDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUICurrentDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUICurrentDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WizardMeta2GUICurrentDateTime().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox OnlyTime;
    // End of variables declaration//GEN-END:variables
}
