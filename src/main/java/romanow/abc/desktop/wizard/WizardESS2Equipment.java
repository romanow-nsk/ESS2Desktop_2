/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.subject2area.ESS2Entity;
import romanow.abc.core.entity.subject2area.ESS2Equipment;
import romanow.abc.core.entity.subject2area.ESS2MetaFile;
import romanow.abc.core.entity.subject2area.ESS2Node;
import romanow.abc.desktop.ESSMetaPanel;

/**
 *
 * @author romanow0
 */
public class WizardESS2Equipment extends WizardBaseViewDB {

    private ESS2Node node;
    private ESS2Equipment equipment;
    private void selectChoice(){
        if (equipment.getMetaFile().getOid()==0){
            MetaFile.select(0);
            return;
            }
        long oid = equipment.getMetaFile().getRef().getOid();
        for(int i=0;i<panel.getMetaData().size();i++){
            if (panel.getMetaData().get(i).getOid()==oid){
                MetaFile.select(i+1);
                return;
                }
            MetaFile.select(0);
            }
        }
    public WizardESS2Equipment(ESSMetaPanel frame0, ESS2Entity entity0, I_Wizard back0) {
        super(frame0,entity0,back0);
        initComponents();
        setSize(750,220);
        equipment = (ESS2Equipment)entity0;
        MultiUnit.setSelected(equipment.isMultiUnit());
        MetaFile.removeAll();
        MetaFile.add("...");
        for(ESS2MetaFile metaFile : panel.getMetaData())
            MetaFile.add(metaFile.toString());
        selectChoice();
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
        MetaFile = new java.awt.Choice();
        SetMetaFile = new javax.swing.JButton();
        MultiUnit = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 120, 460, 2);
        getContentPane().add(MetaFile);
        MetaFile.setBounds(10, 90, 400, 20);

        SetMetaFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetMetaFile.setBorderPainted(false);
        SetMetaFile.setContentAreaFilled(false);
        SetMetaFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetMetaFileActionPerformed(evt);
            }
        });
        getContentPane().add(SetMetaFile);
        SetMetaFile.setBounds(420, 80, 30, 30);

        MultiUnit.setText("Мульти-Unit");
        MultiUnit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MultiUnitItemStateChanged(evt);
            }
        });
        getContentPane().add(MultiUnit);
        MultiUnit.setBounds(10, 130, 120, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SetMetaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetMetaFileActionPerformed
        if (MetaFile.getSelectedIndex()==0){
            main.popup("Не выбран мета-файл");
            return;
            }
        ESS2MetaFile metaFile = panel.getMetaData().get(MetaFile.getSelectedIndex()-1);
        if (!Values.isEquipmentType(metaFile.getMetaType())) {
            main.popup("Недопустимый тип для мета-файла оборудования");
            return;
            }
        equipment.getMetaFile().setOid(metaFile.getOid());
        oneUpdate("Изменено metaFile: "+metaFile.toString());
    }//GEN-LAST:event_SetMetaFileActionPerformed

    private void MultiUnitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MultiUnitItemStateChanged
        equipment.setMultiUnit(MultiUnit.isSelected());
        oneUpdate("Изменено multiUnit: "+MultiUnit.isSelected());
    }//GEN-LAST:event_MultiUnitItemStateChanged

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
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private java.awt.Choice MetaFile;
    private javax.swing.JCheckBox MultiUnit;
    private javax.swing.JButton SetMetaFile;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
