/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.desktop.I_Value;

import java.io.IOException;

/**
 *
 * @author romanow
 */
public class OKName extends javax.swing.JFrame {
    private I_Value ok;
    /**
     * Creates new form OK
     */

    public OKName(int x,int y,String title, I_Value ok0) {
        initComponents();
        ok = ok0;
        OK.setText(title);
        setBounds(x+20,y+20,390,180);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OK = new javax.swing.JButton();
        Name = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        OK.setText("jButton1");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });
        getContentPane().add(OK);
        OK.setBounds(10, 10, 350, 30);
        getContentPane().add(Name);
        Name.setBounds(10, 50, 350, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKActionPerformed
        dispose();
        if (ok!=null)
            ok.onEnter(Name.getText());
    }//GEN-LAST:event_OKActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Name;
    private javax.swing.JButton OK;
    // End of variables declaration//GEN-END:variables
}
