/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUI2StateBox;

import java.awt.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUI2Null extends WizardMeta2GUI {

    private Meta2GUI2StateBox elem;
    public WizardMeta2GUI2Null() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(750,200);
        elem = (Meta2GUI2StateBox) entity;
        RegNum.setText(""+elem.getRegNum());
        BitNum.setText(""+elem.getBitNum());
        ColorYes.setText(""+String.format("%6x",elem.getColorYes()));
        ColorYesButton.setBackground(new Color(elem.getColorYes()));
        ColorNo.setText(""+String.format("%6x",elem.getColorNo()));
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
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        ColorYes = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        RegNum = new javax.swing.JTextField();
        BitNum = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        ColorYesButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        ColorNo = new javax.swing.JTextField();
        ColorNoButton = new javax.swing.JButton();

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
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(10, 120, 560, 2);

        jLabel11.setText("Цвет \"1\"");
        jLabel11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel11KeyPressed(evt);
            }
        });
        getContentPane().add(jLabel11);
        jLabel11.setBounds(250, 135, 60, 14);

        ColorYes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColorYesKeyPressed(evt);
            }
        });
        getContentPane().add(ColorYes);
        ColorYes.setBounds(310, 130, 80, 25);

        jLabel12.setText("Регистр");
        jLabel12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel12KeyPressed(evt);
            }
        });
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 135, 70, 14);

        RegNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegNumKeyPressed(evt);
            }
        });
        getContentPane().add(RegNum);
        RegNum.setBounds(80, 130, 50, 25);

        BitNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BitNumKeyPressed(evt);
            }
        });
        getContentPane().add(BitNum);
        BitNum.setBounds(190, 130, 50, 25);

        jLabel13.setText("Бит");
        jLabel13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel13KeyPressed(evt);
            }
        });
        getContentPane().add(jLabel13);
        jLabel13.setBounds(150, 135, 40, 14);
        getContentPane().add(ColorYesButton);
        ColorYesButton.setBounds(400, 125, 30, 30);

        jLabel14.setText("Цвет \"0\"");
        jLabel14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel14KeyPressed(evt);
            }
        });
        getContentPane().add(jLabel14);
        jLabel14.setBounds(450, 135, 60, 14);

        ColorNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColorNoKeyPressed(evt);
            }
        });
        getContentPane().add(ColorNo);
        ColorNo.setBounds(510, 130, 80, 25);
        getContentPane().add(ColorNoButton);
        ColorNoButton.setBounds(600, 125, 30, 30);

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

    private void jLabel11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel11KeyPressed
    }//GEN-LAST:event_jLabel11KeyPressed

    private void jLabel12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel12KeyPressed
    }//GEN-LAST:event_jLabel12KeyPressed

    private void RegNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RegNumKeyPressed
        onKeyPressed("regNum", RegNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setRegNum(value);
            }
        });
    }//GEN-LAST:event_RegNumKeyPressed

    private void BitNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BitNumKeyPressed
        onKeyPressed("bitNum", BitNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setBitNum(value);
            }
        });
    }//GEN-LAST:event_BitNumKeyPressed

    private void jLabel13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel13KeyPressed

    }//GEN-LAST:event_jLabel13KeyPressed

    private void jLabel14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel14KeyPressed

    }//GEN-LAST:event_jLabel14KeyPressed

    private void ColorNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColorNoKeyPressed
        onColorKeyPressed("colorNo", ColorNo, ColorNoButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setColorNo(value);
            }
        });
    }//GEN-LAST:event_ColorNoKeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BitNum;
    private javax.swing.JTextField ColorNo;
    private javax.swing.JButton ColorNoButton;
    private javax.swing.JTextField ColorYes;
    private javax.swing.JButton ColorYesButton;
    private javax.swing.JTextField RegNum;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
