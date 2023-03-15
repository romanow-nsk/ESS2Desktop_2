/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.metadata.Meta2Collection;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.Meta2Equipment;
import romanow.abc.desktop.I_Value;

/**
 *
 * @author romanow0
 */
public class WizardMeta2Equipment extends WizardBaseView {

    /**
     * Creates new form WizardMeta2Equipment
     */
    public WizardMeta2Equipment() {
        initComponents();
    }
        private Meta2Collection collection;
        @Override
        public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
            super.openForm(parentView0,entity0);
            collection = ((Meta2Equipment)entity0).getRegisters();
            setSize(800,160);
            }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Registers = new javax.swing.JButton();

        Registers.setText("Элементы");
        Registers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistersActionPerformed(evt);
            }
        });
        getContentPane().add(Registers);
        Registers.setBounds(20, 90, 100, 22);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegistersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistersActionPerformed
        String ss = openWizardByType(collection);
        if (ss!=null)
            System.out.println(ss);
    }//GEN-LAST:event_RegistersActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Registers;
    // End of variables declaration//GEN-END:variables
}
