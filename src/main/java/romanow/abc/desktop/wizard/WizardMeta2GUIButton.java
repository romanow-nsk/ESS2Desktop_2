/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUIButton;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIButton extends WizardMeta2GUI {

    private Meta2GUIButton elem;
    private Meta2CommandRegister register;
    private Meta2Command command;
    private int newCode=0;
    public WizardMeta2GUIButton() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(240);
        elem = (Meta2GUIButton) entity;
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(0,120,"",elem.getRegLink(),this);
        add(linkPanel);
        CmdCode.setText(""+elem.getCmdCode());
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
        jLabel13 = new javax.swing.JLabel();
        CmdCode = new javax.swing.JTextField();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel13.setText("Команда");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(250, 130, 100, 14);

        CmdCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmdCodeKeyPressed(evt);
            }
        });
        getContentPane().add(CmdCode);
        CmdCode.setBounds(250, 150, 50, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void CmdCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmdCodeKeyPressed
       onKeyPressed("cmdCode", CmdCode, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setCmdCode(value);
            }
        });
    }//GEN-LAST:event_CmdCodeKeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CmdCode;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel13;
    // End of variables declaration//GEN-END:variables
}
