/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUIMultiBitState;

import java.awt.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIMultiBitState extends WizardMeta2GUI {

    private Meta2GUIMultiBitState elem;
    public WizardMeta2GUIMultiBitState() {
        initComponents();
        }   
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(250);
        elem = (Meta2GUIMultiBitState) entity;
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(10,120,"",elem.getRegLink(),this);
        add(linkPanel);        
        W2.setText(""+elem.getW2());
        BitMask.setText(""+String.format("%06x",elem.getBitMask()));
        ColorYes.setText(""+String.format("%06x",elem.getColorYes()));
        ColorYesButton.setBackground(new Color(elem.getColorYes()));
        ColorNo.setText(""+String.format("%06x",elem.getColorNo()));
        ColorNoButton.setBackground(new Color(elem.getColorNo()));
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
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        ColorYes = new javax.swing.JTextField();
        BitMask = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        ColorYesButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        ColorNo = new javax.swing.JTextField();
        ColorNoButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        W2 = new javax.swing.JTextField();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 82, 560, 2);

        jLabel11.setText("Цвет \"1\"");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(290, 130, 60, 14);

        ColorYes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColorYesKeyPressed(evt);
            }
        });
        getContentPane().add(ColorYes);
        ColorYes.setBounds(290, 150, 80, 25);

        BitMask.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BitMaskKeyPressed(evt);
            }
        });
        getContentPane().add(BitMask);
        BitMask.setBounds(550, 150, 90, 25);

        jLabel13.setText("Маска");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(550, 130, 40, 14);
        getContentPane().add(ColorYesButton);
        ColorYesButton.setBounds(380, 145, 30, 30);

        jLabel14.setText("Цвет \"0\"");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(420, 130, 60, 14);

        ColorNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColorNoKeyPressed(evt);
            }
        });
        getContentPane().add(ColorNo);
        ColorNo.setBounds(420, 150, 80, 25);
        getContentPane().add(ColorNoButton);
        ColorNoButton.setBounds(510, 145, 30, 30);

        jLabel15.setText("W2");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(230, 130, 40, 14);

        W2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                W2KeyPressed(evt);
            }
        });
        getContentPane().add(W2);
        W2.setBounds(230, 150, 50, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void ColorYesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColorYesKeyPressed
        onColorKeyPressed("colorYes", ColorYes, ColorYesButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setColorYes(value);
            }
        });
    }//GEN-LAST:event_ColorYesKeyPressed

    private void BitMaskKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BitMaskKeyPressed
        onKey16Pressed("bitMask", BitMask, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setBitMask(value);
            }
        });
    }//GEN-LAST:event_BitMaskKeyPressed

    private void ColorNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColorNoKeyPressed
        onColorKeyPressed("colorNo", ColorNo, ColorNoButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setColorNo(value);
            }
        });
    }//GEN-LAST:event_ColorNoKeyPressed

    private void W2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_W2KeyPressed
        onKeyPressed("W2", W2, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setW2(value);
            }
        });
    }//GEN-LAST:event_W2KeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BitMask;
    private javax.swing.JTextField ColorNo;
    private javax.swing.JButton ColorNoButton;
    private javax.swing.JTextField ColorYes;
    private javax.swing.JButton ColorYesButton;
    private javax.swing.JTextField W2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
