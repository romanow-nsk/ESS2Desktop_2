/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.subject2area.ESS2Entity;
import romanow.abc.core.entity.subject2area.ESS2EquipEmulator;
import romanow.abc.core.entity.subject2area.ESS2Node;
import romanow.abc.desktop.ESSMetaPanel;
import romanow.abc.desktop.MainBaseFrame;

/**
 *
 * @author romanow0
 */
public class WizardESS2EquipEmulator extends WizardBaseViewDB {

    private ESS2EquipEmulator emulator;
    public WizardESS2EquipEmulator(ESSMetaPanel frame0, ESS2Entity entity0, I_Wizard back0) {
        super(frame0,entity0,back0);
        initComponents();
        emulator = (ESS2EquipEmulator) entity0;
        DeviceName.setText(emulator.getClassName());
        Port.setText(""+emulator.getPort());
        Enabled.setSelected(emulator.isEnable());
        Trace.setSelected(emulator.isTrace());
        RTU.setSelected(emulator.isRTU());
        BaudRate.setText(""+emulator.getBaudRate());
        DeviceName.setText(emulator.getLineName());
        setSize(750,210);
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
        Port = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        DeviceName = new javax.swing.JTextField();
        Enabled = new javax.swing.JCheckBox();
        Trace = new javax.swing.JCheckBox();
        RTU = new javax.swing.JCheckBox();
        BaudRate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ClassName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 83, 560, 10);

        Port.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PortKeyPressed(evt);
            }
        });
        getContentPane().add(Port);
        Port.setBounds(340, 130, 60, 25);

        jLabel1.setText("Порт/номер");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(250, 135, 80, 16);

        jLabel2.setText("BaudRate");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(580, 135, 80, 16);

        DeviceName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DeviceNameKeyPressed(evt);
            }
        });
        getContentPane().add(DeviceName);
        DeviceName.setBounds(100, 130, 140, 25);

        Enabled.setText("Включено");
        Enabled.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EnabledItemStateChanged(evt);
            }
        });
        getContentPane().add(Enabled);
        Enabled.setBounds(300, 95, 80, 20);

        Trace.setText("Трассировка");
        Trace.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TraceItemStateChanged(evt);
            }
        });
        getContentPane().add(Trace);
        Trace.setBounds(410, 95, 130, 20);

        RTU.setText("RS-485");
        RTU.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RTUItemStateChanged(evt);
            }
        });
        getContentPane().add(RTU);
        RTU.setBounds(410, 135, 80, 20);

        BaudRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BaudRateKeyPressed(evt);
            }
        });
        getContentPane().add(BaudRate);
        BaudRate.setBounds(490, 130, 80, 25);

        jLabel3.setText("Имя");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 135, 80, 16);

        ClassName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ClassNameKeyPressed(evt);
            }
        });
        getContentPane().add(ClassName);
        ClassName.setBounds(100, 90, 180, 25);

        jLabel4.setText("Класс (Java)");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 95, 80, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PortKeyPressed
        onKeyPressed("port", Port, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                emulator.setPort(value);
                }
            });
    }//GEN-LAST:event_PortKeyPressed

    private void DeviceNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DeviceNameKeyPressed
        onStringKeyPressed("DeviceName", DeviceName, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                emulator.setLineName(value);
            }
        });
    }//GEN-LAST:event_DeviceNameKeyPressed

    private void EnabledItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EnabledItemStateChanged
        emulator.setEnable(Enabled.isSelected());
        oneUpdate("Изменено enable="+Enabled.isSelected());
    }//GEN-LAST:event_EnabledItemStateChanged

    private void TraceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TraceItemStateChanged
        emulator.setTrace(Trace.isSelected());
        oneUpdate("Изменено trace="+Trace.isSelected());

    }//GEN-LAST:event_TraceItemStateChanged

    private void RTUItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RTUItemStateChanged
        emulator.setRTU(RTU.isSelected());
        oneUpdate("Изменено RTU="+RTU.isSelected());
    }//GEN-LAST:event_RTUItemStateChanged

    private void BaudRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BaudRateKeyPressed
        onKeyPressed("baudRate", BaudRate, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                emulator.setBaudRate(value);
            }
        });

    }//GEN-LAST:event_BaudRateKeyPressed

    private void ClassNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClassNameKeyPressed
        onStringKeyPressed("ClassName", ClassName, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                emulator.setClassName(value);
            }
        });

    }//GEN-LAST:event_ClassNameKeyPressed

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
            java.util.logging.Logger.getLogger(WizardESS2EquipEmulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardESS2EquipEmulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardESS2EquipEmulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardESS2EquipEmulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JTextField ClassName;
    private javax.swing.JTextField DeviceName;
    private javax.swing.JCheckBox Enabled;
    private javax.swing.JTextField Port;
    private javax.swing.JCheckBox RTU;
    private javax.swing.JCheckBox Trace;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
