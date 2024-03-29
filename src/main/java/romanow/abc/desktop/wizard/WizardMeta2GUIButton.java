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
    private boolean start=false;
    public WizardMeta2GUIButton() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(240);
        elem = (Meta2GUIButton) entity;
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(0,120,"",elem.getRegLink(),this);
        add(linkPanel);
        start=true;
        CmdCode.setText(""+elem.getCmdCode());
        ButtonSize.setText(""+elem.getButtonSize());
        RemoteEnable.setSelected(elem.isRemoteEnable());
        ButtonText.setText(elem.getButtonText());
        start=false;
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
        jLabel2 = new javax.swing.JLabel();
        ButtonSize = new javax.swing.JTextField();
        RemoteEnable = new javax.swing.JCheckBox();
        ButtonText = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel13.setText("Надпись");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(450, 130, 60, 16);

        CmdCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmdCodeKeyPressed(evt);
            }
        });
        getContentPane().add(CmdCode);
        CmdCode.setBounds(250, 150, 50, 25);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Кнопка");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(340, 130, 60, 16);

        ButtonSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonSizeKeyPressed(evt);
            }
        });
        getContentPane().add(ButtonSize);
        ButtonSize.setBounds(400, 150, 40, 25);

        RemoteEnable.setText("Удаленно");
        RemoteEnable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RemoteEnableItemStateChanged(evt);
            }
        });
        getContentPane().add(RemoteEnable);
        RemoteEnable.setBounds(310, 155, 90, 20);

        ButtonText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonTextKeyPressed(evt);
            }
        });
        getContentPane().add(ButtonText);
        ButtonText.setBounds(450, 150, 140, 25);

        jLabel14.setText("Команда");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(250, 130, 60, 16);

        jLabel3.setText("Размер");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(400, 130, 50, 16);

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

    private void ButtonSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonSizeKeyPressed
        onKeyPressed("buttonSize", ButtonSize, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setButtonSize(value);
            }
        });
    }//GEN-LAST:event_ButtonSizeKeyPressed

    private void RemoteEnableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RemoteEnableItemStateChanged
        if (start)
            return;
        elem.setRemoteEnable(RemoteEnable.isSelected());
        back.onEnter("Изменено remoteEnable"+": "+elem.isRemoteEnable());
    }//GEN-LAST:event_RemoteEnableItemStateChanged

    private void ButtonTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonTextKeyPressed
        onStringKeyPressed("buttonText", ButtonText, evt, new I_WizardActionString(){
            @Override
            public void onAction(String value) {
                elem.setButtonText(value);
            }
        });

    }//GEN-LAST:event_ButtonTextKeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ButtonSize;
    private javax.swing.JTextField ButtonText;
    private javax.swing.JTextField CmdCode;
    private javax.swing.JCheckBox RemoteEnable;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
