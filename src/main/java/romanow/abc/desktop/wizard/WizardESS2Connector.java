/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.subject2area.ESS2Device;
import romanow.abc.core.entity.subject2area.ESS2Entity;
import romanow.abc.desktop.ESSMetaPanel;

/**
 *
 * @author romanow0
 */
public class WizardESS2Connector extends WizardBaseViewDB {

    private ESS2Device connector;
    private boolean onStart=false;
    public WizardESS2Connector(ESSMetaPanel frame0, ESS2Entity entity0, I_Wizard back0) {
        super("Контроллер",frame0,entity0,back0);
        initComponents();
        onStart=true;
        connector = (ESS2Device)entity0;
        IP.setText(connector.getIP());
        Port.setText(""+connector.getPort());
        DBEmulator.setSelected(connector.isDbEmulator());
        Trace.setSelected(connector.isTrace());
        RTU.setSelected(connector.isRTU());
        RegInBlock.setText(""+connector.getRegsInBlock());
        TimeOut.setText(""+connector.getTimeOut());
        BaudRate.setText(""+connector.getBaudRate());
        UniqueModule.setSelected(connector.isUniqueModule());
        UniqueModuleClass.setText(connector.getUniqueModuleClass());
        setSize(750,230);
        onStart=false;
        UnitsNum.setText(""+connector.getUnitsNum());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        Port = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        IP = new javax.swing.JTextField();
        DBEmulator = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        RegInBlock = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TimeOut = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        UnitsNum = new javax.swing.JTextField();
        RTU = new javax.swing.JCheckBox();
        BaudRate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Trace = new javax.swing.JCheckBox();
        UniqueModule = new javax.swing.JCheckBox();
        UniqueModuleClass = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 120, 600, 3);

        Port.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PortKeyPressed(evt);
            }
        });
        getContentPane().add(Port);
        Port.setBounds(310, 130, 50, 25);

        jLabel1.setText("Порт/номер");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(220, 135, 80, 16);

        jLabel2.setText("Регистров в блоке");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 165, 120, 16);

        IP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IPKeyPressed(evt);
            }
        });
        getContentPane().add(IP);
        IP.setBounds(70, 130, 140, 25);

        DBEmulator.setText("БД-эмулятор");
        DBEmulator.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DBEmulatorItemStateChanged(evt);
            }
        });
        DBEmulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBEmulatorActionPerformed(evt);
            }
        });
        getContentPane().add(DBEmulator);
        DBEmulator.setBounds(460, 135, 110, 20);

        jLabel3.setText("IP/Имя");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 135, 50, 16);

        RegInBlock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegInBlockKeyPressed(evt);
            }
        });
        getContentPane().add(RegInBlock);
        RegInBlock.setBounds(170, 160, 40, 25);

        jLabel4.setText("Тайм-аут(с)");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(220, 165, 80, 16);

        TimeOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TimeOutKeyPressed(evt);
            }
        });
        getContentPane().add(TimeOut);
        TimeOut.setBounds(310, 160, 50, 25);

        jLabel5.setText("Бит/с");
        jLabel5.setInheritsPopupMenu(false);
        getContentPane().add(jLabel5);
        jLabel5.setBounds(460, 165, 60, 16);

        UnitsNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UnitsNumKeyPressed(evt);
            }
        });
        getContentPane().add(UnitsNum);
        UnitsNum.setBounds(420, 130, 30, 25);

        RTU.setText("RS-485");
        RTU.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RTUItemStateChanged(evt);
            }
        });
        getContentPane().add(RTU);
        RTU.setBounds(370, 165, 80, 20);

        BaudRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BaudRateKeyPressed(evt);
            }
        });
        getContentPane().add(BaudRate);
        BaudRate.setBounds(500, 160, 64, 25);

        jLabel6.setText("Unit-ов");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(370, 135, 50, 16);

        Trace.setText("Трассировка");
        Trace.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TraceItemStateChanged(evt);
            }
        });
        getContentPane().add(Trace);
        Trace.setBounds(570, 135, 110, 20);

        UniqueModule.setText("Уникальный модуль");
        UniqueModule.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                UniqueModuleItemStateChanged(evt);
            }
        });
        getContentPane().add(UniqueModule);
        UniqueModule.setBounds(10, 90, 170, 20);

        UniqueModuleClass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UniqueModuleClassKeyPressed(evt);
            }
        });
        getContentPane().add(UniqueModuleClass);
        UniqueModuleClass.setBounds(170, 90, 170, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PortKeyPressed
        onKeyPressed("port", Port, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                connector.setPort(value);
                }
            });
    }//GEN-LAST:event_PortKeyPressed

    private void IPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IPKeyPressed
        onStringKeyPressed("IP", IP, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                connector.setIP(value);
            }
        });
    }//GEN-LAST:event_IPKeyPressed

    private void DBEmulatorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DBEmulatorItemStateChanged
        if(onStart)
            return;
        connector.setDbEmulator(DBEmulator.isSelected());
        oneUpdate("Изменено dbEmulator: "+DBEmulator.isSelected());
    }//GEN-LAST:event_DBEmulatorItemStateChanged

    private void RegInBlockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RegInBlockKeyPressed
        onKeyPressed("regsInBlock", RegInBlock, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                connector.setRegsInBlock(value);
            }
        });
    }//GEN-LAST:event_RegInBlockKeyPressed

    private void TimeOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TimeOutKeyPressed
        onKeyPressed("timeOut", TimeOut, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                connector.setTimeOut(value);
            }
        });
    }//GEN-LAST:event_TimeOutKeyPressed

    private void UnitsNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UnitsNumKeyPressed
        onKeyPressed("unitsNum", UnitsNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                connector.setUnitsNum(value);
            }
        });

    }//GEN-LAST:event_UnitsNumKeyPressed

    private void RTUItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RTUItemStateChanged
        if(onStart)
            return;
        connector.setRTU(RTU.isSelected());
        oneUpdate("Изменено RTU: "+RTU.isSelected());
    }//GEN-LAST:event_RTUItemStateChanged

    private void BaudRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BaudRateKeyPressed
        onKeyPressed("baudRate", BaudRate, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                connector.setBaudRate(value);
            }
        });

    }//GEN-LAST:event_BaudRateKeyPressed

    private void DBEmulatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBEmulatorActionPerformed
        if(onStart)
            return;
        connector.setDbEmulator(DBEmulator.isSelected());
        oneUpdate("Изменено DBEmulator: "+DBEmulator.isSelected());

    }//GEN-LAST:event_DBEmulatorActionPerformed

    private void TraceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TraceItemStateChanged
        if(onStart)
            return;
        connector.setTrace(Trace.isSelected());
        oneUpdate("Изменено Trace: "+Trace.isSelected());
    }//GEN-LAST:event_TraceItemStateChanged

    private void UniqueModuleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_UniqueModuleItemStateChanged
        if (onStart)
            return;
        connector.setUniqueModule(UniqueModule.isSelected());
        oneUpdate("Изменено uniqueModule: "+UniqueModule.isSelected());
    }//GEN-LAST:event_UniqueModuleItemStateChanged

    private void UniqueModuleClassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UniqueModuleClassKeyPressed
        if(evt.getKeyCode()!=10) return;
        connector.setUniqueModuleClass(UniqueModuleClass.getText());
        if (evt!=null)
            main.viewUpdate(evt,true);
        back.onEnter("Изменено : uniqueModuleClass: "+UniqueModuleClass.getText());
    }//GEN-LAST:event_UniqueModuleClassKeyPressed

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
            java.util.logging.Logger.getLogger(WizardESS2Connector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Connector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Connector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Connector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new WizardESS2Connector().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BaudRate;
    private javax.swing.JCheckBox DBEmulator;
    private javax.swing.JTextField IP;
    private javax.swing.JTextField Port;
    private javax.swing.JCheckBox RTU;
    private javax.swing.JTextField RegInBlock;
    private javax.swing.JTextField TimeOut;
    private javax.swing.JCheckBox Trace;
    private javax.swing.JCheckBox UniqueModule;
    private javax.swing.JTextField UniqueModuleClass;
    private javax.swing.JTextField UnitsNum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
