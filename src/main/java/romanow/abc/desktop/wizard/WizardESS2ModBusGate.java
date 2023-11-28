/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.EntityRefList;
import romanow.abc.core.entity.subject2area.*;
import romanow.abc.desktop.ESSMetaPanel;
import romanow.abc.desktop.MainBaseFrame;

/**
 *
 * @author romanow0
 */
public class WizardESS2ModBusGate extends WizardBaseViewDB {
    private boolean start=false;
    private ESS2ModBusGate gate;
    private EntityRefList<ESS2Device> devices;
    public WizardESS2ModBusGate(ESSMetaPanel frame0, ESS2Entity entity0, final EntityRefList<ESS2Device> devices0, I_Wizard back0) {
        super("Шлюз Modbus/TCP",frame0,entity0,back0);
        initComponents();
        start = true;
        devices = devices0;
        gate = (ESS2ModBusGate) entity0;
        Port.setText(""+gate.getPort());
        Enabled.setSelected(gate.isEnable());
        Trace.setSelected(gate.isTrace());
        Devices.removeAll();
        Devices.add("...");
        for(ESS2Device device : devices)
            Devices.add(device.getTitle());
        selectChoice();
        setSize(750,280);
        start = false;
        }
    private void selectChoice(){
        long oid = gate.getDevice().getOid();
        if (oid==0){
            Devices.select(0);
            return;
            }
        for(int i=0;i<devices.size();i++){
            if (devices.get(i).getOid()==oid){
                Devices.select(i+1);
                return;
            }
        Devices.select(0);
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

        jCheckBox1 = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        Port = new javax.swing.JTextField();
        Enabled = new javax.swing.JCheckBox();
        Trace = new javax.swing.JCheckBox();
        Devices = new java.awt.Choice();
        SetMetaFile = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

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
        Port.setBounds(260, 100, 60, 25);

        Enabled.setText("Включено");
        Enabled.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EnabledItemStateChanged(evt);
            }
        });
        getContentPane().add(Enabled);
        Enabled.setBounds(10, 100, 80, 20);

        Trace.setText("Трассировка");
        Trace.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TraceItemStateChanged(evt);
            }
        });
        getContentPane().add(Trace);
        Trace.setBounds(100, 100, 130, 20);
        getContentPane().add(Devices);
        Devices.setBounds(10, 130, 310, 20);

        SetMetaFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetMetaFile.setBorderPainted(false);
        SetMetaFile.setContentAreaFilled(false);
        SetMetaFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetMetaFileActionPerformed(evt);
            }
        });
        getContentPane().add(SetMetaFile);
        SetMetaFile.setBounds(330, 120, 30, 30);

        jLabel5.setText("Порт");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(210, 105, 50, 16);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Контроллер");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(370, 130, 110, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PortKeyPressed
        onKeyPressed("port", Port, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                gate.setPort(value);
                }
            });
    }//GEN-LAST:event_PortKeyPressed

    private void EnabledItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EnabledItemStateChanged
        if (start)
            return;
        gate.setEnable(Enabled.isSelected());
        oneUpdate("Изменено enable="+Enabled.isSelected());
    }//GEN-LAST:event_EnabledItemStateChanged

    private void TraceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TraceItemStateChanged
        if (start)
            return;
        gate.setTrace(Trace.isSelected());
        oneUpdate("Изменено trace="+Trace.isSelected());

    }//GEN-LAST:event_TraceItemStateChanged

    private void SetMetaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetMetaFileActionPerformed
        if (Devices.getSelectedIndex()==0){
            gate.getDevice().setOid(0);
            main.popup("Контроллер отключен от шлюза "+gate.getTitle());
            return;
            }
        gate.getDevice().setOidRef(devices.get(Devices.getSelectedIndex()-1));
        oneUpdate("Контроллер навзначен на шлюз "+ gate.getTitle());
    }//GEN-LAST:event_SetMetaFileActionPerformed

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
            java.util.logging.Logger.getLogger(WizardESS2ModBusGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardESS2ModBusGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardESS2ModBusGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardESS2ModBusGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private java.awt.Choice Devices;
    private javax.swing.JCheckBox Enabled;
    private javax.swing.JTextField Port;
    private javax.swing.JButton SetMetaFile;
    private javax.swing.JCheckBox Trace;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
