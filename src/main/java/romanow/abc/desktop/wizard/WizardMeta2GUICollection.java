/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUICollection;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.OK;

import java.util.ArrayList;
import java.util.Comparator;

import static romanow.abc.core.entity.metadata.view.Meta2GUI.createByType;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUICollection extends WizardBaseView {
    private ArrayList<ConstValue> types = new ArrayList<>();
    private ArrayList<Meta2GUI> list;
    private WizardMetaEntitySelector choice;
    private int cnts[] = new int[20];
    /**
     * Creates new form xxx
     */
    public WizardMeta2GUICollection() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Types = new java.awt.Choice();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(Types);
        Types.setBounds(610, 140, 140, 25);

        jLabel1.setText("Элемент ЧМИ+");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(500, 150, 100, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        setSize(800,250);
        list = ((Meta2GUICollection)entity).getList();
        choice=new WizardMetaEntitySelector("Элементы ЧМИ", Values.MEGUIElement,callBack);
        getContentPane().add(choice);
        choice.setBounds(10,80,750,40);
        refreshList();
        types = Values.constMap().getGroupList("GUIType");
        types.sort(new Comparator<ConstValue>() {
            @Override
            public int compare(ConstValue o1, ConstValue o2) {
                return o1.title().compareTo(o2.title());
            }
        });
        Types.removeAll();
        for(ConstValue cc : types)
            Types.add(cc.title());
        refreshList();
        }
    public void refreshList(){
        choice.getList().removeAll();
        for(int i=0; i<list.size();i++){
            Meta2GUI entity = list.get(i);
            choice.getList().add(entity.getFullTitle());
            }
        }
    private I_WizardEntitySelector callBack = new I_WizardEntitySelector() {
        @Override
        public void onEdit(int type, int idx) {
            String ss = openWizardByType(list.get(idx));
            if (ss!=null)
                System.out.println(ss);
            }
        @Override
        public void onRemove(int type, int idx) {
            final String title = list.get(idx).getTitle();
            new OK(300, 300, "Удалить " + title, new I_Button() {
                @Override
                public void onPush() {
                    list.remove(idx);
                    refreshList();
                    back.onEnter("Удален: "+title);
                    }
                });
            }
        @Override
        public void onAdd(int type0) {
            int type = types.get(Types.getSelectedIndex()).value();
            Meta2GUI entity = (Meta2GUI) createByType(type);
            if (entity.getErrors().getErrCount()!=0){
                System.out.println(entity.getErrors().getInfo());
                main.popup("Ошибка создания элемента ЧМИ");
                return;
            }
            list.add(entity);
            refreshList();
            String ss = openWizardByType(entity);
            if (ss!=null)
                System.out.println(ss);
            }
        @Override
        public void onSelect(int type, int idx) {}
        };

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
            java.util.logging.Logger.getLogger(WizardMeta2GUICollection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUICollection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUICollection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardMeta2GUICollection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new WizardMeta2GUICollection().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice Types;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
