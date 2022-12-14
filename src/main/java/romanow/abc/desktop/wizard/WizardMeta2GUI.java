/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUI;

import java.awt.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUI extends WizardBaseView {

    private Meta2GUI elem;
    public WizardMeta2GUI() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(750,200);
        elem = (Meta2GUI) entity;
        X.setText(""+elem.getX());
        Y.setText(""+elem.getY());
        W.setText(""+elem.getDx());
        H.setText(""+elem.getH());
        CommonColor.setSelected(elem.isCommonColor());
        FontSize.setText(""+elem.getFontSize());
        StringSize.setText(""+elem.getStringSize());
        ColorVal.setText(""+String.format("%6x",elem.getColor()));
        ElemColor.setBackground(new Color(elem.getColor()));
        Bold.setSelected(elem.isBold());
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
        jLabel9 = new javax.swing.JLabel();
        ColorVal = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        X = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Y = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        W = new javax.swing.JTextField();
        H = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        ElemColor = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        StringSize = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        FontSize = new javax.swing.JTextField();
        CommonColor = new javax.swing.JCheckBox();
        Bold = new javax.swing.JCheckBox();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 82, 700, 2);

        jLabel9.setText("????????");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(440, 90, 40, 14);
        jLabel9.getAccessibleContext().setAccessibleName("Y");

        ColorVal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColorValKeyPressed(evt);
            }
        });
        getContentPane().add(ColorVal);
        ColorVal.setBounds(480, 90, 70, 25);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(10, 120, 700, 10);

        jLabel11.setText("X");
        jLabel11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel11KeyPressed(evt);
            }
        });
        getContentPane().add(jLabel11);
        jLabel11.setBounds(10, 90, 30, 14);

        X.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                XKeyPressed(evt);
            }
        });
        getContentPane().add(X);
        X.setBounds(25, 90, 40, 25);

        jLabel12.setText("Y");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(70, 90, 30, 14);

        Y.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YKeyPressed(evt);
            }
        });
        getContentPane().add(Y);
        Y.setBounds(80, 90, 40, 25);

        jLabel13.setText("W");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(130, 90, 30, 14);

        W.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WKeyPressed(evt);
            }
        });
        getContentPane().add(W);
        W.setBounds(150, 90, 40, 25);

        H.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HKeyPressed(evt);
            }
        });
        getContentPane().add(H);
        H.setBounds(220, 90, 40, 25);

        jLabel10.setText("H");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(200, 90, 30, 14);
        getContentPane().add(ElemColor);
        ElemColor.setBounds(555, 90, 25, 25);
        getContentPane().add(jLabel14);
        jLabel14.setBounds(420, 90, 40, 0);

        StringSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StringSizeKeyPressed(evt);
            }
        });
        getContentPane().add(StringSize);
        StringSize.setBounds(400, 90, 30, 25);

        jLabel15.setText("?? ????????????");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(340, 100, 70, 14);

        jLabel16.setText("Font");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(270, 90, 40, 14);

        jLabel17.setText("???????????????? ");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(340, 90, 70, 14);

        FontSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FontSizeKeyPressed(evt);
            }
        });
        getContentPane().add(FontSize);
        FontSize.setBounds(300, 90, 30, 25);

        CommonColor.setText("??????????");
        CommonColor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CommonColorItemStateChanged(evt);
            }
        });
        getContentPane().add(CommonColor);
        CommonColor.setBounds(590, 90, 61, 23);

        Bold.setText("bold");
        Bold.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BoldItemStateChanged(evt);
            }
        });
        getContentPane().add(Bold);
        Bold.setBounds(660, 90, 50, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void ColorValKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColorValKeyPressed
        onColorKeyPressed("color", ColorVal, ElemColor, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setColor(value);
            }
        });
    }//GEN-LAST:event_ColorValKeyPressed

    private void XKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_XKeyPressed
        onKeyPressed("X", X, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setX(value);
            }
        });
    }//GEN-LAST:event_XKeyPressed

    private void YKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YKeyPressed
        onKeyPressed("Y", Y, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setY(value);
            }
        });
    }//GEN-LAST:event_YKeyPressed

    private void WKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WKeyPressed
        onKeyPressed("W", W, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setDx(value);
            }
        });
    }//GEN-LAST:event_WKeyPressed

    private void HKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HKeyPressed
        onKeyPressed("H", H, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setH(value);
            }
        });
    }//GEN-LAST:event_HKeyPressed

    private void jLabel11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel11KeyPressed
    }//GEN-LAST:event_jLabel11KeyPressed

    private void StringSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StringSizeKeyPressed
        onKeyPressed("StringSize", StringSize, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setStringSize(value);
            }
        });
    }//GEN-LAST:event_StringSizeKeyPressed

    private void FontSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FontSizeKeyPressed
        onKeyPressed("FontSize", FontSize, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setFontSize(value);
            }
        });
    }//GEN-LAST:event_FontSizeKeyPressed

    private void CommonColorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CommonColorItemStateChanged
        elem.setCommonColor(CommonColor.isSelected());
        back.onEnter("???????????????? commonColor: "+elem.isCommonColor());
    }//GEN-LAST:event_CommonColorItemStateChanged

    private void BoldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BoldItemStateChanged
        elem.setBold(Bold.isSelected());
        back.onEnter("???????????????? bold: "+elem.isBold());
    }//GEN-LAST:event_BoldItemStateChanged

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox Bold;
    private javax.swing.JTextField ColorVal;
    private javax.swing.JCheckBox CommonColor;
    private javax.swing.JButton ElemColor;
    private javax.swing.JTextField FontSize;
    private javax.swing.JTextField H;
    private javax.swing.JTextField StringSize;
    private javax.swing.JTextField W;
    private javax.swing.JTextField X;
    private javax.swing.JTextField Y;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
