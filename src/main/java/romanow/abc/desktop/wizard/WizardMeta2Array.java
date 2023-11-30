/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Array;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.Meta2Face;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.OK;

import java.util.ArrayList;

/**
 *
 * @author romanow0
 */
public class WizardMeta2Array extends WizardBaseView {
    /**
     * Creates new form xxx
     */
    private boolean busy = false;
    private Meta2Array array;
    private ArrayList<ConstValue> types;
    public WizardMeta2Array() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(850,250);
        busy = true;
        array = (Meta2Array) entity;
        if (array.getElem()==null)
            Elem.setText("???");
        else
            Elem.setText(""+array.getElem().getFullTitle());
        Inline61850.setSelected(array.isInline61860());
        Step.setText(""+array.getStep());
        Size.setText(""+array.getSize());
        Types.removeAll();
        types = createEquipElemList();
        for(ConstValue cc : types)
            Types.add(cc.title());
        for(int i=0;i<types.size();i++)
            if (types.get(i).value()==array.getElem().getMetaType()) {
                Types.select(i);
                break;
                }
        busy = false;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Elem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Size = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Step = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        ChangeElem = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        Types = new java.awt.Choice();
        Inline61850 = new javax.swing.JCheckBox();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        Elem.setEnabled(false);
        getContentPane().add(Elem);
        Elem.setBounds(110, 130, 410, 25);

        jLabel1.setText("Элемент");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 135, 90, 16);

        jLabel3.setText("Размерность");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 95, 100, 16);

        Size.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SizeKeyPressed(evt);
            }
        });
        getContentPane().add(Size);
        Size.setBounds(110, 90, 50, 25);

        jLabel4.setText("Шаг");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(180, 95, 60, 16);

        Step.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StepKeyPressed(evt);
            }
        });
        getContentPane().add(Step);
        Step.setBounds(220, 90, 50, 25);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 82, 560, 3);

        ChangeElem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/up.PNG"))); // NOI18N
        ChangeElem.setBorderPainted(false);
        ChangeElem.setContentAreaFilled(false);
        ChangeElem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeElemActionPerformed(evt);
            }
        });
        getContentPane().add(ChangeElem);
        ChangeElem.setBounds(480, 160, 40, 40);

        Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        Edit.setBorderPainted(false);
        Edit.setContentAreaFilled(false);
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });
        getContentPane().add(Edit);
        Edit.setBounds(530, 130, 30, 30);
        getContentPane().add(Types);
        Types.setBounds(270, 170, 210, 20);

        Inline61850.setText(" 61850 inline");
        Inline61850.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Inline61850ItemStateChanged(evt);
            }
        });
        getContentPane().add(Inline61850);
        Inline61850.setBounds(650, 90, 120, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void SizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SizeKeyPressed
        onKeyPressed("size", Size, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                array.setSize(value);
            }
        });
    }//GEN-LAST:event_SizeKeyPressed

    private void StepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StepKeyPressed
        onKeyPressed("step", Step, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                array.setStep(value);
            }
        });
    }//GEN-LAST:event_StepKeyPressed

    private void ChangeElemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeElemActionPerformed
        new OK(300, 300, "Изменить тип элемента масива", new I_Button() {
            @Override
            public void onPush() {
                try {
                    ConstValue cc = types.get(Types.getSelectedIndex());
                    Meta2Face vv = (Meta2Face) Values.createEntityByType("MetaElem",
                            cc.value(),"romanow.abc.core.entity.metadata");
                    array.setElem(vv);
                    Elem.setText(vv.getFullTitle());
                    back.onEnter("Изменен тип элемента массива: "+cc.title());
                    } catch (Exception ee){
                        System.out.println("Ошибка: "+ee.toString());
                        array.setElem(null);
                }
            }
        });
    }//GEN-LAST:event_ChangeElemActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        if (array.getElem()==null)
            return;
        openWizardByType(array.getElem());
    }//GEN-LAST:event_EditActionPerformed

    private void Inline61850ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Inline61850ItemStateChanged
        if (busy)
            return;
        array.setInline61860(Inline61850.isSelected());
        back.onEnter("Изменено 61850 Inline="+Inline61850.isSelected());
    }//GEN-LAST:event_Inline61850ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChangeElem;
    private javax.swing.JButton Edit;
    private javax.swing.JTextField Elem;
    private javax.swing.JCheckBox Inline61850;
    private javax.swing.JTextField Size;
    private javax.swing.JTextField Step;
    private java.awt.Choice Types;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
