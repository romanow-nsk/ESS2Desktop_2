/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;

import java.util.ArrayList;

/**
 *
 * @author romanow0
 */
public class WizardMeta2SettingRegister extends WizardMeta2Register {

    private ArrayList<ConstValue> failTypes;
    private Meta2SettingRegister register;
    public WizardMeta2SettingRegister() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(850,450);
        register = (Meta2SettingRegister)entity;
        MaxValueFormula.setText(register.getMaxValueFormula());
        DefValueFormula.setText(register.getDefValueFormula());
        MinValueFormula.setText(register.getMinValueFormula());
        Power.setText(""+register.getPower());
        Unit.setText(register.getUnit());
        DataRegNum.setText(getHEX().isSelected() ? "0x"+Integer.toString(register.getDataRegNum(),16) : ""+register.getDataRegNum());
        OverLimit.setSelected(register.isOverLimit());
        RemoteControl.setSelected(register.isRemoteEnable());
        DefValue.setText(""+register.getDefValue());
        MinValue.setText(""+register.getMinValue());
        MaxValue.setText(""+register.getMaxValue());
        EnvValue.setText(""+register.getEnvVar());
        failTypes = Values.constMap().getGroupList("SettingOver");
        FailType.removeAll();
        for(ConstValue vv : failTypes)
            FailType.add(vv.title());
        FailType.select(register.getFailType());   
        }
    @Override
    public void setRegNum(){
        super.setRegNum();
        DataRegNum.setText(getHEX().isSelected() ? "0x"+Integer.toString(register.getDataRegNum(),16) : ""+register.getDataRegNum());
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
        Power = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        MaxValueFormula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        DefValueFormula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Unit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        MinValueFormula = new javax.swing.JTextField();
        OverLimit = new javax.swing.JCheckBox();
        RemoteControl = new javax.swing.JCheckBox();
        FailType = new java.awt.Choice();
        jLabel7 = new javax.swing.JLabel();
        DataRegNum = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        SaveFailType = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        MaxValue = new javax.swing.JTextField();
        DefValue = new javax.swing.JTextField();
        MinValue = new javax.swing.JTextField();
        EnvValue = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Power.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PowerKeyPressed(evt);
            }
        });
        getContentPane().add(Power);
        Power.setBounds(580, 160, 80, 25);

        jLabel1.setText("Регистр данных аварии");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(190, 270, 140, 20);

        jLabel2.setText("Ограничение по максимуму");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 210, 160, 16);

        MaxValueFormula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MaxValueFormulaKeyPressed(evt);
            }
        });
        getContentPane().add(MaxValueFormula);
        MaxValueFormula.setBounds(190, 200, 150, 25);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Формулы");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 130, 60, 14);

        DefValueFormula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DefValueFormulaKeyPressed(evt);
            }
        });
        getContentPane().add(DefValueFormula);
        DefValueFormula.setBounds(190, 140, 150, 25);

        jLabel4.setText("Ед.измерения");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(460, 140, 110, 16);

        Unit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UnitKeyPressed(evt);
            }
        });
        getContentPane().add(Unit);
        Unit.setBounds(580, 130, 80, 25);

        jLabel5.setText("Значение по умолчанию");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 150, 160, 16);

        jLabel6.setText("Ограничение по минимуму");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 180, 160, 16);

        MinValueFormula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MinValueFormulaKeyPressed(evt);
            }
        });
        getContentPane().add(MinValueFormula);
        MinValueFormula.setBounds(190, 170, 150, 25);

        OverLimit.setText("Авария по превышению");
        OverLimit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OverLimitItemStateChanged(evt);
            }
        });
        getContentPane().add(OverLimit);
        OverLimit.setBounds(20, 240, 170, 20);

        RemoteControl.setText("Удаленное управление");
        RemoteControl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RemoteControlItemStateChanged(evt);
            }
        });
        getContentPane().add(RemoteControl);
        RemoteControl.setBounds(20, 270, 180, 20);
        getContentPane().add(FailType);
        FailType.setBounds(340, 240, 230, 25);

        jLabel7.setText("Перем.окружения");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(460, 200, 110, 16);

        DataRegNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DataRegNumKeyPressed(evt);
            }
        });
        getContentPane().add(DataRegNum);
        DataRegNum.setBounds(340, 270, 80, 25);

        jLabel8.setText("Тип аварии");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(190, 240, 110, 20);

        SaveFailType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveFailType.setBorderPainted(false);
        SaveFailType.setContentAreaFilled(false);
        SaveFailType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveFailTypeActionPerformed(evt);
            }
        });
        getContentPane().add(SaveFailType);
        SaveFailType.setBounds(580, 230, 30, 30);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(10, 120, 560, 3);

        MaxValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MaxValueKeyPressed(evt);
            }
        });
        getContentPane().add(MaxValue);
        MaxValue.setBounds(350, 200, 80, 25);

        DefValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DefValueKeyPressed(evt);
            }
        });
        getContentPane().add(DefValue);
        DefValue.setBounds(350, 140, 80, 25);

        MinValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MinValueKeyPressed(evt);
            }
        });
        getContentPane().add(MinValue);
        MinValue.setBounds(350, 170, 80, 25);

        EnvValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EnvValueKeyPressed(evt);
            }
        });
        getContentPane().add(EnvValue);
        EnvValue.setBounds(579, 190, 80, 25);

        jLabel9.setText("Масштаб целого");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(460, 170, 110, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void DefValueFormulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DefValueFormulaKeyPressed
        onStringKeyPressed("defValueFormula", DefValueFormula, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                register.setDefValueFormula(value);
            }
        });
    }//GEN-LAST:event_DefValueFormulaKeyPressed

    private void MaxValueFormulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MaxValueFormulaKeyPressed
        onStringKeyPressed("maxValueFormula", MaxValueFormula, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                register.setMaxValueFormula(value);
            }
        });
    }//GEN-LAST:event_MaxValueFormulaKeyPressed

    private void UnitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UnitKeyPressed
        onStringKeyPressed("unit", Unit, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                register.setUnit(value);
            }
        });

    }//GEN-LAST:event_UnitKeyPressed

    private void PowerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PowerKeyPressed
        onKeyPressed("power", Power, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                register.setPower(value);
            }
        });
    }//GEN-LAST:event_PowerKeyPressed

    private void MinValueFormulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MinValueFormulaKeyPressed
        onStringKeyPressed("minValueFormula", MinValueFormula, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                register.setMinValueFormula(value);
            }
        });
    }//GEN-LAST:event_MinValueFormulaKeyPressed

    private void OverLimitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OverLimitItemStateChanged
        register.setOverLimit(OverLimit.isSelected());
        back.onEnter("Изменено overLimit: "+register.isOverLimit());
    }//GEN-LAST:event_OverLimitItemStateChanged

    private void RemoteControlItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RemoteControlItemStateChanged
        register.setRemoteEnable(RemoteControl.isSelected());
        back.onEnter("Изменено remoteEnable: "+register.isRemoteEnable());
    }//GEN-LAST:event_RemoteControlItemStateChanged

    private void SaveFailTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveFailTypeActionPerformed
        ConstValue value = failTypes.get(FailType.getSelectedIndex());
        register.setFailType(value.value());
        back.onEnter("Изменено failType: "+value.title());
    }//GEN-LAST:event_SaveFailTypeActionPerformed

    private void DataRegNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DataRegNumKeyPressed
        onKeyPressed("regNum", getHEX(), DataRegNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                register.setDataRegNum(value);
            }
        });
    }//GEN-LAST:event_DataRegNumKeyPressed

    private void MaxValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MaxValueKeyPressed
        onKeyPressed("maxValue", MaxValue, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                register.setMaxValue(value);
            }
        });
    }//GEN-LAST:event_MaxValueKeyPressed

    private void DefValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DefValueKeyPressed
        onKeyPressed("defValue", DefValue, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                register.setDefValue(value);
            }
        });

    }//GEN-LAST:event_DefValueKeyPressed

    private void MinValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MinValueKeyPressed
        onKeyPressed("defValue", DefValue, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                register.setDefValue(value);
            }
        });
    }//GEN-LAST:event_MinValueKeyPressed

    private void EnvValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EnvValueKeyPressed
        onStringKeyPressed("evnValue", EnvValue, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                register.setEnvVar(value);
            }
        });
    }//GEN-LAST:event_EnvValueKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DataRegNum;
    private javax.swing.JTextField DefValue;
    private javax.swing.JTextField DefValueFormula;
    private javax.swing.JTextField EnvValue;
    private java.awt.Choice FailType;
    private javax.swing.JTextField MaxValue;
    private javax.swing.JTextField MaxValueFormula;
    private javax.swing.JTextField MinValue;
    private javax.swing.JTextField MinValueFormula;
    private javax.swing.JCheckBox OverLimit;
    private javax.swing.JTextField Power;
    private javax.swing.JCheckBox RemoteControl;
    private javax.swing.JButton SaveFailType;
    private javax.swing.JTextField Unit;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
