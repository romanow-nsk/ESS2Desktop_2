/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import romanow.abc.core.entity.subjectarea.MetaSettingRegister;

/**
 *
 * @author romanow
 */
public class ESSSettingsRegister extends javax.swing.JFrame {
    private double minValue=0;
    private double maxValue=0;
    private boolean minFormulaValid=false;
    private boolean maxFormulaValid=false;
    private I_Success back=null;
    private ESSSettingsCalculator calculator = new ESSSettingsCalculator();

    /**
     * Creates new form ESSSettingsRegister
     */
    public ESSSettingsRegister(MetaExternalSystem meta, MetaSettingRegister register, I_ModbusGroupDriver plm, I_Success back0) {
        back = back0;
        initComponents();
        setVisible(true);
        setTitle("Уставка");
        setBounds(200,200,400,280);
        String name = register.getTitle();
        UtilsDesktop.setLabelText(SettingsName,name,50);
        DefValueFormula.setText(register.getDefValueFormula());
        MinValueFormula.setText(register.getMinValueFormula());
        MaxValueFormula.setText(register.getMaxValueFormula());
        SettingShortName.setText(register.getShortName());
        SettingUnit.setText(register.getUnit());
        SettingRegNum.setText(""+register.getRegNum());
        try {
            calculator.calculate(meta,register,plm);
            SettingValue.setText(""+calculator.getValue());
            MinValue.setText(calculator.isMinFormulaValid() ? ""+calculator.getMinValue() : "");
            MaxValue.setText(calculator.isMaxFormulaValid() ? ""+calculator.getMaxValue() : "");
            DefValue.setText(calculator.isDefFormulaValid() ? ""+calculator.getDefValue() : "");
            } catch (UniException e) {
                System.out.println("Калькулятор уставок: "+e.toString());
                }
            }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SettingValue = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        DefValueFormula = new javax.swing.JTextField();
        MinValueFormula = new javax.swing.JTextField();
        MaxValueFormula = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        DefValue = new javax.swing.JTextField();
        MinValue = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        MaxValue = new javax.swing.JTextField();
        WriteSetting = new javax.swing.JButton();
        SettingRegNum = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        SettingShortName = new javax.swing.JTextField();
        SettingUnit = new javax.swing.JTextField();
        CheckLimits = new javax.swing.JCheckBox();
        SettingsName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        SettingValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SettingValueKeyPressed(evt);
            }
        });
        getContentPane().add(SettingValue);
        SettingValue.setBounds(300, 210, 70, 25);

        jLabel11.setText("Номер");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(10, 190, 50, 14);

        jLabel12.setText("Значение");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(280, 70, 90, 14);

        DefValueFormula.setEnabled(false);
        getContentPane().add(DefValueFormula);
        DefValueFormula.setBounds(130, 90, 160, 25);

        MinValueFormula.setEnabled(false);
        getContentPane().add(MinValueFormula);
        MinValueFormula.setBounds(130, 120, 160, 25);

        MaxValueFormula.setEnabled(false);
        getContentPane().add(MaxValueFormula);
        MaxValueFormula.setBounds(130, 150, 160, 25);

        jLabel13.setText("Умолчание");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(10, 90, 90, 14);

        jLabel14.setText("Формула");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(130, 70, 90, 14);

        DefValue.setEnabled(false);
        getContentPane().add(DefValue);
        DefValue.setBounds(300, 90, 70, 25);

        MinValue.setEnabled(false);
        getContentPane().add(MinValue);
        MinValue.setBounds(300, 120, 70, 25);

        jLabel15.setText("Минимум");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(10, 120, 90, 14);

        MaxValue.setEnabled(false);
        getContentPane().add(MaxValue);
        MaxValue.setBounds(300, 150, 70, 25);

        WriteSetting.setText("Запись");
        WriteSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WriteSettingActionPerformed(evt);
            }
        });
        getContentPane().add(WriteSetting);
        WriteSetting.setBounds(210, 210, 80, 23);

        SettingRegNum.setEnabled(false);
        getContentPane().add(SettingRegNum);
        SettingRegNum.setBounds(80, 180, 40, 25);

        jLabel16.setText("Максимум");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(10, 150, 90, 14);

        jLabel17.setText("Имя");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(130, 190, 30, 14);

        SettingShortName.setEnabled(false);
        getContentPane().add(SettingShortName);
        SettingShortName.setBounds(170, 180, 120, 25);

        SettingUnit.setEnabled(false);
        getContentPane().add(SettingUnit);
        SettingUnit.setBounds(300, 180, 70, 25);

        CheckLimits.setText("Проверка ограничений");
        getContentPane().add(CheckLimits);
        CheckLimits.setBounds(10, 210, 170, 23);

        SettingsName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(SettingsName);
        SettingsName.setBounds(10, 10, 360, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void writeSettings(){
        try {
            calculator.parseAndWrite(SettingValue.getText(),CheckLimits.isSelected());
            if (back!=null)
                back.onSuccess();
        } catch (UniException e) {
            System.out.println("Калькулятор уставок: "+e.toString());
        }
        dispose();
    }

    private void WriteSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WriteSettingActionPerformed
        writeSettings();
    }//GEN-LAST:event_WriteSettingActionPerformed

    private void SettingValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SettingValueKeyPressed
        if(evt.getKeyCode()!=10) return;
        writeSettings();
        MainBaseFrame.viewUpdate(evt,true);
    }//GEN-LAST:event_SettingValueKeyPressed

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
            java.util.logging.Logger.getLogger(ESSSettingsRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ESSSettingsRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ESSSettingsRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ESSSettingsRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ESSSettingsRegister(null,null,null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckLimits;
    private javax.swing.JTextField DefValue;
    private javax.swing.JTextField DefValueFormula;
    private javax.swing.JTextField MaxValue;
    private javax.swing.JTextField MaxValueFormula;
    private javax.swing.JTextField MinValue;
    private javax.swing.JTextField MinValueFormula;
    private javax.swing.JTextField SettingRegNum;
    private javax.swing.JTextField SettingShortName;
    private javax.swing.JTextField SettingUnit;
    private javax.swing.JTextField SettingValue;
    private javax.swing.JLabel SettingsName;
    private javax.swing.JButton WriteSetting;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    // End of variables declaration//GEN-END:variables
}
