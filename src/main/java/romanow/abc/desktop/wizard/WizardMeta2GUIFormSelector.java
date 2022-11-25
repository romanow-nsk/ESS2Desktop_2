/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIFormSelector extends WizardMeta2GUI {

    private Meta2GUIFormSelector elem;
    public WizardMeta2GUIFormSelector() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(750,200);
        elem = (Meta2GUIFormSelector) entity;
        FormName.setText(""+elem.getFormName());
        Level.setText(""+elem.getFormLevel());
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        LevelLabel = new javax.swing.JLabel();
        FormName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        Level = new javax.swing.JTextField();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        LevelLabel.setText("Уровень");
        getContentPane().add(LevelLabel);
        LevelLabel.setBounds(260, 135, 70, 14);

        FormName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FormNameKeyPressed(evt);
            }
        });
        getContentPane().add(FormName);
        FormName.setBounds(90, 130, 150, 25);

        jLabel13.setText("Форма");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 135, 70, 14);

        Level.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LevelKeyPressed(evt);
            }
        });
        getContentPane().add(Level);
        Level.setBounds(320, 130, 50, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void FormNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormNameKeyPressed
        onStringKeyPressed("formName", FormName, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                elem.setFormName(value);
            }
        });
    }//GEN-LAST:event_FormNameKeyPressed

    private void LevelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LevelKeyPressed
        onKeyPressed("formLevel", Level, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setFormLevel(value);
            }
        });
    }//GEN-LAST:event_LevelKeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FormName;
    private javax.swing.JTextField Level;
    private javax.swing.JLabel LevelLabel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel13;
    // End of variables declaration//GEN-END:variables
}
