/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUIBit2Commands;
import romanow.abc.core.entity.metadata.view.Meta2GUILevelIndicator;

import javax.swing.*;

/**
 *
 * @author romanow
 */
public class WizardMeta2GUIBit2Commands extends WizardMeta2GUIRegW2 {

    /**
     * Creates new form WizardMeta2GUILevelIndicator2
     */
    private Meta2GUIBit2Commands elem;
    public WizardMeta2GUIBit2Commands() {
        initComponents();
        }
    @Override
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHight(350);
        elem = (Meta2GUIBit2Commands)   entity;
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(235,120,"CmdReg",elem.getCmdRegLink(),this);
        add(linkPanel);
        CmdOn.setText(""+elem.getCmdOn());
        CmdOff.setText(""+elem.getCmdOff());
        BitNum.setText(""+elem.getBitNum());
        ButtonSize.setText(""+elem.getButtonSize());
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
        BitNum = new javax.swing.JTextField();
        CmdOn = new javax.swing.JTextField();
        CmdOff = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ButtonSize = new javax.swing.JTextField();

        jLabel1.setText("Разряд");
        jLabel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel1KeyPressed(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 230, 100, 16);

        jLabel2.setText("команда ВКЛ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(250, 180, 100, 16);

        BitNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BitNumKeyPressed(evt);
            }
        });
        getContentPane().add(BitNum);
        BitNum.setBounds(100, 230, 40, 25);

        CmdOn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmdOnKeyPressed(evt);
            }
        });
        getContentPane().add(CmdOn);
        CmdOn.setBounds(360, 180, 60, 25);

        CmdOff.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmdOffKeyPressed(evt);
            }
        });
        getContentPane().add(CmdOff);
        CmdOff.setBounds(360, 210, 60, 25);

        jLabel3.setText("кнопка (size)");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(250, 240, 100, 16);

        jLabel4.setText("команда ВЫКЛ");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(250, 210, 100, 16);

        ButtonSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonSizeKeyPressed(evt);
            }
        });
        getContentPane().add(ButtonSize);
        ButtonSize.setBounds(360, 240, 60, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CmdOnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmdOnKeyPressed
        onKeyPressed("CmdOn", CmdOn, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setCmdOn(value);
            }
        });
    }//GEN-LAST:event_CmdOnKeyPressed

    private void BitNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BitNumKeyPressed
        onKeyPressed("CmdOff", BitNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setCmdOff(value);
            }
        });
    }//GEN-LAST:event_BitNumKeyPressed

    private void CmdOffKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmdOffKeyPressed
        onKeyPressed("CmdOff", CmdOff, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setCmdOff(value);
            }
        });
    }//GEN-LAST:event_CmdOffKeyPressed

    private void jLabel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1KeyPressed

    private void ButtonSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonSizeKeyPressed
        onKeyPressed("ButtonSize", ButtonSize, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                        elem.setButtonSize(value);
                    }
        });
    }//GEN-LAST:event_ButtonSizeKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BitNum;
    private javax.swing.JTextField ButtonSize;
    private javax.swing.JTextField CmdOff;
    private javax.swing.JTextField CmdOn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
