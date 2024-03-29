/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.EntityRefList;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2Device;
import romanow.abc.core.entity.subject2area.ESS2Entity;
import romanow.abc.core.entity.subject2area.ESS2LogUnit;
import romanow.abc.desktop.ESSMetaPanel;
import romanow.abc.desktop.MainBaseFrame;

/**
 *
 * @author romanow0
 */
public class WizardESS2LogUnit extends WizardBaseViewDB {

    private ESS2LogUnit logUnit;
    private EntityRefList<ESS2Device> devices;
    public WizardESS2LogUnit(ESSMetaPanel frame0, ESS2Entity entity0, EntityRefList<ESS2Device> devices0, I_Wizard back0) {
        super("Лог.устройство",frame0,entity0,back0);
        initComponents();
        logUnit = (ESS2LogUnit) entity0;
        DebugLogUnit.setText(""+logUnit.getDebugUnit());
        MainLogUnit.setText(""+logUnit.getMainUnit());
        SnapShotDisable.setSelected(logUnit.isSnapShotDisable());
        setSize(750,250);
        devices = devices0;
        DebugDevices.removeAll();
        MainDevices.removeAll();
        DebugDevices.add("...");
        MainDevices.add("...");
        for(ESS2Device device : devices){
            DebugDevices.add(device.getTitle());
            MainDevices.add(device.getTitle());
            }
        DebugDevices.select(0);
        MainDevices.select(0);
        int i=1;
        for(ESS2Device device : devices){
            if (device.getOid()==logUnit.getDebugDevice().getOid()){
                DebugDevices.select(i);
                break;
                }
            i++;
            }
        i=1;
        for(ESS2Device device : devices){
            if (device.getOid()==logUnit.getMainDevice().getOid()){
                MainDevices.select(i);
                break;
                }
            i++;
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

        jSeparator1 = new javax.swing.JSeparator();
        DebugLogUnit = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        DebugDevices = new java.awt.Choice();
        SetDebugDevice = new javax.swing.JButton();
        SnapShotDisable = new javax.swing.JCheckBox();
        MainDevices = new java.awt.Choice();
        SetMainDevice = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        MainLogUnit = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 120, 460, 3);

        DebugLogUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DebugLogUnitKeyPressed(evt);
            }
        });
        getContentPane().add(DebugLogUnit);
        DebugLogUnit.setBounds(430, 160, 40, 25);

        jLabel1.setText("Unit");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(400, 160, 40, 16);
        getContentPane().add(DebugDevices);
        DebugDevices.setBounds(10, 160, 330, 20);

        SetDebugDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetDebugDevice.setBorderPainted(false);
        SetDebugDevice.setContentAreaFilled(false);
        SetDebugDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetDebugDeviceActionPerformed(evt);
            }
        });
        getContentPane().add(SetDebugDevice);
        SetDebugDevice.setBounds(350, 160, 40, 30);

        SnapShotDisable.setText("Запрещение  SnapShot");
        SnapShotDisable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SnapShotDisableItemStateChanged(evt);
            }
        });
        getContentPane().add(SnapShotDisable);
        SnapShotDisable.setBounds(560, 130, 180, 20);
        getContentPane().add(MainDevices);
        MainDevices.setBounds(10, 130, 330, 20);

        SetMainDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetMainDevice.setBorderPainted(false);
        SetMainDevice.setContentAreaFilled(false);
        SetMainDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetMainDeviceActionPerformed(evt);
            }
        });
        getContentPane().add(SetMainDevice);
        SetMainDevice.setBounds(350, 125, 40, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Отладка");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(480, 160, 70, 16);

        MainLogUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MainLogUnitKeyPressed(evt);
            }
        });
        getContentPane().add(MainLogUnit);
        MainLogUnit.setBounds(430, 130, 40, 25);

        jLabel3.setText("Unit");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(400, 135, 40, 16);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Работа");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(480, 135, 70, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DebugLogUnitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DebugLogUnitKeyPressed
        onKeyPressed("debugLogUnit", DebugLogUnit, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                logUnit.setDebugUnit(value);
                }
            });
    }//GEN-LAST:event_DebugLogUnitKeyPressed

    private void SetDebugDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetDebugDeviceActionPerformed
        int idx = DebugDevices.getSelectedIndex();
        if (idx==0){
            logUnit.getDebugDevice().setOid(0);
            oneUpdate("Удалена ссылка на драйвер");
            }
        else{
            logUnit.getDebugDevice().setOidRef(devices.get(idx-1));
            oneUpdate("Изменено debugDevice: "+devices.get(idx-1).toString());
            }
    }//GEN-LAST:event_SetDebugDeviceActionPerformed

    private void SnapShotDisableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SnapShotDisableItemStateChanged
        logUnit.setSnapShotDisable(SnapShotDisable.isSelected());
        oneUpdate("Изменено snapShotEnable: "+logUnit.isSnapShotDisable());
    }//GEN-LAST:event_SnapShotDisableItemStateChanged

    private void SetMainDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetMainDeviceActionPerformed
        int idx = MainDevices.getSelectedIndex();
        if (idx==0){
            logUnit.getMainDevice().setOid(0);
            oneUpdate("Удалена ссылка на драйвер");
        }
        else{
            logUnit.getMainDevice().setOidRef(devices.get(idx-1));
            oneUpdate("Изменено mainDevice: "+devices.get(idx-1).toString());
        }

    }//GEN-LAST:event_SetMainDeviceActionPerformed

    private void MainLogUnitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MainLogUnitKeyPressed
        onKeyPressed("mainLogUnit", MainLogUnit, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                logUnit.setMainUnit(value);
            }
        });

    }//GEN-LAST:event_MainLogUnitKeyPressed

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
            java.util.logging.Logger.getLogger(WizardESS2LogUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardESS2LogUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardESS2LogUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardESS2LogUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private java.awt.Choice DebugDevices;
    private javax.swing.JTextField DebugLogUnit;
    private java.awt.Choice MainDevices;
    private javax.swing.JTextField MainLogUnit;
    private javax.swing.JButton SetDebugDevice;
    private javax.swing.JButton SetMainDevice;
    private javax.swing.JCheckBox SnapShotDisable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
