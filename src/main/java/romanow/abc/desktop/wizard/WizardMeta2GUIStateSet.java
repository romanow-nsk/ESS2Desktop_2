/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.Meta2State;
import romanow.abc.core.entity.metadata.Meta2StateRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUIStateSet;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.OK;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class WizardMeta2GUIStateSet extends WizardMeta2GUIRegW2 {

    /**
     * Creates new form WizardMeta2GUIStateSet
     */
    public WizardMeta2GUIStateSet() {
        initComponents();
        }
    private  Meta2GUIStateSet elem;
    private Meta2StateRegister register;
    private WizardMetaEntitySelector selector;
    private Choice choice;
    private ArrayList<Meta2State> states;
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(320);
        elem = (Meta2GUIStateSet) entity;
        register = (Meta2StateRegister) elem.getRegLink().getRegister();
        selector = new WizardMetaEntitySelector("Биты", Values.MEBit,callBack);
        selector.setBounds(10,230,750,40);
        choice = selector.getList();
        getContentPane().add(selector);
        states= register.getStates().getList();
        refreshList();
        }

    public void refreshList(){
        choice.removeAll();
        for(Meta2Entity entity : states){
            Meta2State state = (Meta2State)entity;
            choice.add(state.getTitle()+"("+state.getCode()+")");
            }
        }
    private void SaveBitsTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBitsTypeActionPerformed
        //ConstValue value = bitsTypes.get(BitsType.getSelectedIndex());
        //register.setBitRegType(value.value());
        //back.onEnter("Изменено bitRegType: "+value.title());
        }//GEN-LAST:event_SaveBitsTypeActionPerformed

    private I_WizardEntitySelector callBack = new I_WizardEntitySelector() {
        @Override
        public void onEdit(int type, int idx) {
            String ss = openWizardByType(states.get(idx));
            if (ss!=null)
                System.out.println(ss);
            }
        @Override
        public void onRemove(int type, int idx) {
            final String title = states.get(idx).getTitle();
            new OK(300, 300, "Удалить " + title, new I_Button() {
                @Override
                public void onPush() {
                    states.remove(idx);
                    refreshList();
                    back.onEnter("Удален: "+title);
                    }
                });
            }
        @Override
        public void onAdd(int type) {
            Meta2State entity = new Meta2State();
            states.add(entity);
            refreshList();
            String ss = openWizardByType(entity);
            if (ss!=null)
                System.out.println(ss);
            }
        @Override
        public void onSelect(int type, int idx) {}
    };
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BitsType = new java.awt.Choice();
        jLabel1 = new javax.swing.JLabel();
        SaveBitsType = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(BitsType);
        BitsType.setBounds(360, 180, 160, 25);

        jLabel1.setText("Тип битов");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(260, 190, 90, 16);

        SaveBitsType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveBitsType.setBorderPainted(false);
        SaveBitsType.setContentAreaFilled(false);
        SaveBitsType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBitsTypeActionPerformed(evt);
            }
        });
        getContentPane().add(SaveBitsType);
        SaveBitsType.setBounds(530, 180, 30, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(WizardMeta2GUIStateSet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUIStateSet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUIStateSet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUIStateSet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WizardMeta2GUIStateSet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice BitsType;
    private javax.swing.JButton SaveBitsType;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
