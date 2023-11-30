/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.Meta2Register;

import javax.swing.*;
import java.util.ArrayList;

/**
 *
 * @author romanow0
 */
public class WizardMeta2Register extends WizardBaseView {
    private boolean busy = false;
    private ArrayList<ConstValue> dataTypes;
    private Meta2Register register;
    public WizardMeta2Register() {
        initComponents();
        }
    public void setRegNum(){
        RegNum.setText(HEX.isSelected() ? "0x"+Integer.toString(register.getRegNum(),16) : ""+register.getRegNum());
        }
    public JCheckBox getHEX() {
        return HEX;
        }

    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(800,200);
        busy = true;
        register = (Meta2Register)entity;
        RegNum.setText(HEX.isSelected() ? "0x"+Integer.toString(register.getRegNum(),16) : ""+register.getRegNum());
        dataTypes = Values.constMap().getGroupList("ValueType");
        DataFormat.removeAll();
        for(ConstValue vv : dataTypes)
            DataFormat.add(vv.title());
        DataFormat.select(register.getFormat());
        SnapShot.setSelected(register.isSnapShot());
        IEC60870RegNum.setText(""+register.getIEC60870RegNum());
        busy = false;
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
        SaveDataFormat = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        RegNum = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        DataFormat = new java.awt.Choice();
        jSeparator2 = new javax.swing.JSeparator();
        HEX = new javax.swing.JCheckBox();
        SnapShot = new javax.swing.JCheckBox();
        Регистр1 = new javax.swing.JLabel();
        Регистр = new javax.swing.JLabel();
        IEC60870RegNum = new javax.swing.JTextField();

        jCheckBox1.setText("jCheckBox1");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 82, 560, 3);

        SaveDataFormat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveDataFormat.setBorderPainted(false);
        SaveDataFormat.setContentAreaFilled(false);
        SaveDataFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveDataFormatActionPerformed(evt);
            }
        });
        getContentPane().add(SaveDataFormat);
        SaveDataFormat.setBounds(470, 90, 30, 30);

        jLabel9.setText("Номер");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(70, 95, 50, 16);

        RegNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegNumKeyPressed(evt);
            }
        });
        getContentPane().add(RegNum);
        RegNum.setBounds(120, 90, 80, 25);

        jLabel10.setText("Формат ");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(280, 95, 60, 16);
        getContentPane().add(DataFormat);
        DataFormat.setBounds(340, 90, 120, 25);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(10, 120, 560, 3);

        HEX.setText("hex");
        HEX.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HEXItemStateChanged(evt);
            }
        });
        getContentPane().add(HEX);
        HEX.setBounds(20, 95, 60, 20);

        SnapShot.setText("SnapShot");
        SnapShot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SnapShotItemStateChanged(evt);
            }
        });
        getContentPane().add(SnapShot);
        SnapShot.setBounds(200, 95, 100, 20);

        Регистр1.setText("Регистр");
        getContentPane().add(Регистр1);
        Регистр1.setBounds(510, 90, 60, 16);

        Регистр.setText("МЭК 60870");
        getContentPane().add(Регистр);
        Регистр.setBounds(510, 105, 80, 16);

        IEC60870RegNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IEC60870RegNumKeyPressed(evt);
            }
        });
        getContentPane().add(IEC60870RegNum);
        IEC60870RegNum.setBounds(590, 90, 50, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void SaveDataFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveDataFormatActionPerformed
        ConstValue value = dataTypes.get(DataFormat.getSelectedIndex());
        register.setFormat(value.value());
        back.onEnter("Изменено format: "+value.title());
    }//GEN-LAST:event_SaveDataFormatActionPerformed

    private void RegNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RegNumKeyPressed
        onKeyPressed("regNum", HEX, RegNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                register.setRegNum(value);
                }
            });
        }//GEN-LAST:event_RegNumKeyPressed

    private void HEXItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HEXItemStateChanged
        if (busy) return;
        setRegNum();
    }//GEN-LAST:event_HEXItemStateChanged

    private void SnapShotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SnapShotItemStateChanged
        if (busy) return;
        register.setSnapShot(SnapShot.isSelected());
        back.onEnter("Изменено snapShot: "+register.isSnapShot());
    }//GEN-LAST:event_SnapShotItemStateChanged

    private void IEC60870RegNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IEC60870RegNumKeyPressed
        onKeyPressed("IEC60870RegNum", IEC60870RegNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                register.setIEC60870RegNum(value);
            }
        });
    }//GEN-LAST:event_IEC60870RegNumKeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice DataFormat;
    private javax.swing.JCheckBox HEX;
    private javax.swing.JTextField IEC60870RegNum;
    private javax.swing.JTextField RegNum;
    private javax.swing.JButton SaveDataFormat;
    private javax.swing.JCheckBox SnapShot;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel Регистр;
    private javax.swing.JLabel Регистр1;
    // End of variables declaration//GEN-END:variables
}
