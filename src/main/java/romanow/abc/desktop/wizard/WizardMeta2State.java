/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.*;


/**
 *
 * @author romanow0
 */
public class WizardMeta2State extends WizardBaseView {
    /**
     * Creates new form xxx
     */
    Meta2State state;
    public WizardMeta2State() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(850,250);
        state = (Meta2State)  entity;
        RemoteEnable.setSelected(state.isRemoteEnable());
        Code.setText(""+state.getCode());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        Code = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        RemoteEnable = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel3.setText("Код команды");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 95, 100, 14);

        Code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CodeKeyPressed(evt);
            }
        });
        getContentPane().add(Code);
        Code.setBounds(110, 90, 80, 25);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 82, 560, 2);

        RemoteEnable.setText("Удаленное управление");
        RemoteEnable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RemoteEnableItemStateChanged(evt);
            }
        });
        getContentPane().add(RemoteEnable);
        RemoteEnable.setBounds(210, 90, 190, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void CodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CodeKeyPressed
        if(evt.getKeyCode()!=10) return;
        int xx=0;
        try {
            xx = Integer.parseInt(Code.getText());
            state.setCode(xx);
            if (evt!=null)
                main.viewUpdate(evt,true);
            back.onEnter("Изменено code: "+state.getCode());
            } catch (Exception ee){
                System.out.println("Ошибка целого: "+Code.getText());
                if (evt!=null)
                    main.viewUpdate(evt,false);
                }
    }//GEN-LAST:event_CodeKeyPressed

    private void RemoteEnableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RemoteEnableItemStateChanged
        state.setRemoteEnable(RemoteEnable.isSelected());
        back.onEnter("Изменено remoteEnable: "+state.isRemoteEnable());
    }//GEN-LAST:event_RemoteEnableItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Code;
    private javax.swing.JCheckBox RemoteEnable;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
