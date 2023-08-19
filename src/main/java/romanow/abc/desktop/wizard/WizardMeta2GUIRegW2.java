/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIRegW2 extends WizardMeta2GUI {
    private boolean onStart=false;
    private Meta2GUIRegW2 elem;
    public WizardMeta2GUIRegW2() {
        initComponents();
        }
    public void onHex(){}
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(280);
        elem = (Meta2GUIRegW2)  entity;
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(0,120,"",elem.getLink(),this);
        add(linkPanel);
        W2.setText(""+elem.getW2());
        onStart=true;
        IntValue.setSelected(elem.isIntValue());
        ByteSize.setSelected(elem.isByteSize());
        AfterPoint.setText(""+elem.getAfterPoint());
        onStart=false;
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
        W2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        IntValue = new javax.swing.JCheckBox();
        ByteSize = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        AfterPoint = new javax.swing.JTextField();

        jCheckBox1.setText("jCheckBox1");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        W2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                W2KeyPressed(evt);
            }
        });
        getContentPane().add(W2);
        W2.setBounds(50, 180, 40, 25);

        jLabel13.setText("цифр после точки ");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(105, 220, 120, 16);

        IntValue.setText("Формат целого");
        IntValue.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                IntValueItemStateChanged(evt);
            }
        });
        getContentPane().add(IntValue);
        IntValue.setBounds(100, 180, 130, 20);

        ByteSize.setText("Размерность байт");
        ByteSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ByteSizeItemStateChanged(evt);
            }
        });
        getContentPane().add(ByteSize);
        ByteSize.setBounds(100, 200, 140, 20);

        jLabel14.setText("W2");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(10, 180, 30, 16);

        AfterPoint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AfterPointKeyPressed(evt);
            }
        });
        getContentPane().add(AfterPoint);
        AfterPoint.setBounds(50, 210, 40, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void W2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_W2KeyPressed
        onKeyPressed("W2", W2, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setW2(value);
            }
        });
    }//GEN-LAST:event_W2KeyPressed

    private void IntValueItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_IntValueItemStateChanged
        if (onStart)
            return;
        elem.setIntValue(IntValue.isSelected());
        back.onEnter("Изменено IntValue"+": "+IntValue.isSelected());
    }//GEN-LAST:event_IntValueItemStateChanged

    private void ByteSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ByteSizeItemStateChanged
        if (onStart)
        return;
        elem.setByteSize(ByteSize.isSelected());
        back.onEnter("Изменено ByteSize"+": "+ByteSize.isSelected());
    }//GEN-LAST:event_ByteSizeItemStateChanged

    private void AfterPointKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AfterPointKeyPressed
        onKeyPressed("AfterPoint", AfterPoint, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setAfterPoint(value);
            }
        });
    }//GEN-LAST:event_AfterPointKeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AfterPoint;
    private javax.swing.JCheckBox ByteSize;
    private javax.swing.JCheckBox IntValue;
    private javax.swing.JTextField W2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    // End of variables declaration//GEN-END:variables
}
