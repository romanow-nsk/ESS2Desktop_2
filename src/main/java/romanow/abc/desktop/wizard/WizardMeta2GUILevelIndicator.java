/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUILevelIndicator;

/**
 *
 * @author romanow
 */
public class WizardMeta2GUILevelIndicator extends WizardMeta2GUIRegW2 {

    /**
     * Creates new form WizardMeta2GUILevelIndicator2
     */
    private Meta2GUILevelIndicator elem;
    public WizardMeta2GUILevelIndicator() {
        initComponents();
        }
    @Override
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(350);
        elem = (Meta2GUILevelIndicator)   entity;
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(235,120,"MaxFail",elem.getMaxFail(),this);
        add(linkPanel);
        linkPanel = new WizardRegLinkPanel(235,175,"MinFail",elem.getMinFail(),this);
        add(linkPanel);
        linkPanel = new WizardRegLinkPanel(470,120,"MaxWarn",elem.getMaxWarn(),this);
        add(linkPanel);
        linkPanel = new WizardRegLinkPanel(470,175,"MinWarn",elem.getMinWarn(),this);
        add(linkPanel);
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Предупреждение");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(480, 235, 140, 14);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Макс");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(710, 130, 70, 14);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Мин");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(710, 185, 70, 14);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Авария");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(240, 235, 100, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    // End of variables declaration//GEN-END:variables
}
