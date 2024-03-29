/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.OK;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author romanow0
 */
public class WizardMeta2StateRegister extends WizardMeta2Register {
    private boolean busy = false;
    private Meta2StateRegister register;
    private WizardMetaEntitySelector selector;
    private Choice choice;
    private ArrayList<Meta2State> states;
    public WizardMeta2StateRegister() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(850,230);
        busy=true;
        register = (Meta2StateRegister)entity;
        selector = new WizardMetaEntitySelector("Состояния", Values.MEState,callBack);
        selector.setBounds(10,130,750,40);
        choice = selector.getList();
        getContentPane().add(selector);
        states = register.getStates().getList();
        refreshList();
        DEvent.setSelected(register.isDEvent());
        busy=false;
        }

    public void refreshList(){
        choice.removeAll();
        for(Meta2Entity entity : states){
            choice.add(entity.getFullTitle());
            }
        }

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
            try {
                Meta2State entity = (Meta2State) Values.createEntityByType("MetaElem",type,"romanow.abc.core.entity.metadata");
                states.add(entity);
                refreshList();
                String ss = openWizardByType(entity);
                if (ss!=null)
                    System.out.println(ss);
                } catch (UniException e) {
                    System.out.println(e.toString());
                    }
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

        jSeparator1 = new javax.swing.JSeparator();
        DEvent = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 120, 560, 2);

        DEvent.setText("Дискретное событие");
        DEvent.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DEventItemStateChanged(evt);
            }
        });
        getContentPane().add(DEvent);
        DEvent.setBounds(10, 170, 170, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void DEventItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DEventItemStateChanged
        if (busy) return;
        register.setDEvent(DEvent.isSelected());
        back.onEnter("Изменено dEvent: "+register.isDEvent());
    }//GEN-LAST:event_DEventItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox DEvent;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
