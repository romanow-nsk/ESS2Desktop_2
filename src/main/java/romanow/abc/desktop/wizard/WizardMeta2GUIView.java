/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import lombok.Getter;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.OK;

import java.awt.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIView extends WizardBaseView {

    /**
     * Creates new form WizardMeta2Equipment
     */
    public WizardMeta2GUIView() {
        initComponents();
        setBounds(200,200,800,250);
        }
   private Meta2GUIView view;
   @Getter private Meta2EntityList<Meta2GUIForm> list;
   private WizardMetaEntitySelector selector;
   @Override
   public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
       super.openForm(parentView0,entity0);
       view = (Meta2GUIView) entity0;
       BackColor.setText(""+String.format("%6x",view.getBackColor()));
       BackColorButton.setBackground(new Color(view.getBackColor()));
       MenuOnColor.setText(""+String.format("%6x",view.getMenuButtonOnColor()));
       MenuOnColorButton.setBackground(new Color(view.getMenuButtonOnColor()));
       MenuOffColor.setText(""+String.format("%6x",view.getMenuButtonOffColor()));
       MenuOffColorButton.setBackground(new Color(view.getMenuButtonOffColor()));
       TextColor.setText(""+String.format("%6x",view.getTextColor()));
       TextColorButton.setBackground(new Color(view.getTextColor()));
       LabelColor.setText(""+String.format("%6x",view.getLabelBackColor()));
       LabelColorButton.setBackground(new Color(view.getLabelBackColor()));
       list = view.getForms();
       selector = new WizardMetaEntitySelector("Формы", Values.MEGUIForm,callBack);
       selector.setBounds(10,80,750,40);
       getContentPane().add(selector);
       refreshList();
       }
    public void refreshList(){
            Choice choice = selector.getList();
            choice.removeAll();
            for(Meta2GUIForm form : list.getList())
                choice.add(form.getFullTitle());
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
                    list.getList().remove(idx);
                    refreshList();
                    back.onEnter("Удален: "+title);
                }
            });
        }
        @Override
        public void onAdd(int type) {
            try {
                Meta2GUIForm entity = (Meta2GUIForm) Values.createEntityByType("MetaElem",type,"romanow.abc.core.entity.metadata");
                list.add(entity);
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
        jLabel9 = new javax.swing.JLabel();
        BackColor = new javax.swing.JTextField();
        BackColorButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        MenuOnColor = new javax.swing.JTextField();
        MenuOnColorButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        MenuOffColor = new javax.swing.JTextField();
        MenuOffColorButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        TextColor = new javax.swing.JTextField();
        TextColorButton = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        LabelColor = new javax.swing.JTextField();
        LabelColorButton = new javax.swing.JButton();

        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 130, 480, 10);

        jLabel9.setText("Фон экрана");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 150, 80, 14);

        BackColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BackColorKeyPressed(evt);
            }
        });
        getContentPane().add(BackColor);
        BackColor.setBounds(110, 140, 60, 25);
        getContentPane().add(BackColorButton);
        BackColorButton.setBounds(170, 140, 25, 25);

        jLabel10.setText("Меню+");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(210, 150, 50, 14);

        MenuOnColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuOnColorKeyPressed(evt);
            }
        });
        getContentPane().add(MenuOnColor);
        MenuOnColor.setBounds(260, 140, 60, 25);
        getContentPane().add(MenuOnColorButton);
        MenuOnColorButton.setBounds(320, 140, 25, 25);

        jLabel11.setText("Меню-");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(210, 180, 50, 14);

        MenuOffColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuOffColorKeyPressed(evt);
            }
        });
        getContentPane().add(MenuOffColor);
        MenuOffColor.setBounds(260, 170, 60, 25);
        getContentPane().add(MenuOffColorButton);
        MenuOffColorButton.setBounds(320, 170, 25, 25);

        jLabel12.setText("Текст");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(360, 150, 50, 14);

        TextColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextColorKeyPressed(evt);
            }
        });
        getContentPane().add(TextColor);
        TextColor.setBounds(410, 140, 60, 25);
        getContentPane().add(TextColorButton);
        TextColorButton.setBounds(470, 140, 25, 25);

        jLabel13.setText("Фон надписи");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(10, 170, 90, 14);

        LabelColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabelColorKeyPressed(evt);
            }
        });
        getContentPane().add(LabelColor);
        LabelColor.setBounds(110, 170, 60, 25);
        getContentPane().add(LabelColorButton);
        LabelColorButton.setBounds(170, 170, 25, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BackColorKeyPressed
        onColorKeyPressed("backColor", BackColor, BackColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setBackColor(value);
            }
        });
    }//GEN-LAST:event_BackColorKeyPressed

    private void MenuOnColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuOnColorKeyPressed
        onColorKeyPressed("menuOnColor", MenuOnColor, MenuOnColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setMenuButtonOnColor(value);
            }
        });
    }//GEN-LAST:event_MenuOnColorKeyPressed

    private void MenuOffColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuOffColorKeyPressed
        onColorKeyPressed("menuOffColor", MenuOffColor, MenuOffColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setMenuButtonOffColor(value);
            }
        });

    }//GEN-LAST:event_MenuOffColorKeyPressed

    private void TextColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextColorKeyPressed
        onColorKeyPressed("textColor", TextColor, TextColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setTextColor(value);
            }
        });

    }//GEN-LAST:event_TextColorKeyPressed

    private void LabelColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabelColorKeyPressed
        onColorKeyPressed("labelBackColor", LabelColor, LabelColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setLabelBackColor(value);
            }
        });

    }//GEN-LAST:event_LabelColorKeyPressed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BackColor;
    private javax.swing.JButton BackColorButton;
    private javax.swing.JTextField LabelColor;
    private javax.swing.JButton LabelColorButton;
    private javax.swing.JTextField MenuOffColor;
    private javax.swing.JButton MenuOffColorButton;
    private javax.swing.JTextField MenuOnColor;
    private javax.swing.JButton MenuOnColorButton;
    private javax.swing.JTextField TextColor;
    private javax.swing.JButton TextColorButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
