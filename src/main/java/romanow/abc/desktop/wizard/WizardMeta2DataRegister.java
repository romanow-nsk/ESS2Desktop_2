/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Entity;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author romanow0
 */
public class WizardMeta2DataRegister extends WizardMeta2Register {
    private ArrayList<ConstValue> streamTypes;
    private Meta2DataRegister register;
    public WizardMeta2DataRegister() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(800,200);
        register = (Meta2DataRegister)entity;
        Unit.setText(register.getUnit());
        Power.setText(""+register.getPower());
        EnvValue.setText(""+register.getEnvVar());
        streamTypes = Values.constMap().getGroupList("DataStream");
        StreamType.removeAll();
        for(ConstValue vv : streamTypes)
            StreamType.add(vv.title());
        for(int i=0; i<streamTypes.size(); i++)
            if(streamTypes.get(i).value()==register.getStreamType())
                StreamType.select(i);
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Power = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Unit = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        StreamType = new java.awt.Choice();
        jLabel1 = new javax.swing.JLabel();
        SaveStreamType = new javax.swing.JButton();
        EnvValue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        Power.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PowerKeyPressed(evt);
            }
        });
        getContentPane().add(Power);
        Power.setBounds(240, 130, 40, 25);

        jLabel2.setText("????.??????.");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 135, 50, 14);

        Unit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UnitKeyPressed(evt);
            }
        });
        getContentPane().add(Unit);
        Unit.setBounds(70, 130, 60, 25);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 120, 720, 2);

        jLabel3.setText("?????????????? ????????????");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(140, 135, 100, 14);
        getContentPane().add(StreamType);
        StreamType.setBounds(530, 130, 160, 25);

        jLabel1.setText("?????? ????");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(480, 135, 60, 14);

        SaveStreamType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveStreamType.setBorderPainted(false);
        SaveStreamType.setContentAreaFilled(false);
        SaveStreamType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveStreamTypeActionPerformed(evt);
            }
        });
        getContentPane().add(SaveStreamType);
        SaveStreamType.setBounds(700, 130, 30, 30);

        EnvValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EnvValueKeyPressed(evt);
            }
        });
        getContentPane().add(EnvValue);
        EnvValue.setBounds(390, 130, 80, 25);

        jLabel4.setText("??????????.??????????.");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(300, 135, 90, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void UnitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UnitKeyPressed
        if(evt.getKeyCode()!=10) return;
        register.setUnit(Unit.getText());
        if (evt!=null)
            main.viewUpdate(evt,true);
        back.onEnter("???????????????? unit: "+register.getUnit());
    }//GEN-LAST:event_UnitKeyPressed

    private void PowerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PowerKeyPressed
        if(evt.getKeyCode()!=10) return;
        int xx=0;
        try {
            xx = Integer.parseInt(Power.getText());
            register.setPower(xx);
            if (evt!=null)
                main.viewUpdate(evt,true);
            back.onEnter("???????????????? power: "+register.getPower());
        } catch (Exception ee){
            System.out.println("???????????? ????????????: "+Power.getText());
            if (evt!=null)
                main.viewUpdate(evt,false);
        }
    }//GEN-LAST:event_PowerKeyPressed

    private void SaveStreamTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveStreamTypeActionPerformed
        ConstValue value = streamTypes.get(StreamType.getSelectedIndex());
        register.setStreamType(value.value());
        back.onEnter("???????????????? streamType: "+value.title());
    }//GEN-LAST:event_SaveStreamTypeActionPerformed

    private void EnvValueKeyPressed(KeyEvent evt) {//GEN-FIRST:event_EnvValueKeyPressed
        onStringKeyPressed("evnValue", EnvValue, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                register.setEnvVar(value);
            }
        });
    }//GEN-LAST:event_EnvValueKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EnvValue;
    private javax.swing.JTextField Power;
    private javax.swing.JButton SaveStreamType;
    private java.awt.Choice StreamType;
    private javax.swing.JTextField Unit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
