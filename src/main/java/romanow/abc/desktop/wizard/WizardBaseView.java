/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.artifacts.ArtifactList;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.Meta2Face;
import romanow.abc.desktop.I_Value;
import romanow.abc.desktop.MainBaseFrame;
import romanow.abc.desktop.view2.FormContext2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import static romanow.abc.core.constants.Values.WizardX0;
import static romanow.abc.core.constants.Values.WizardY0;

/**
 *
 * @author romanow0
 */
public class WizardBaseView extends javax.swing.JFrame {
    private boolean isClosed=false;
    protected I_Value<String> onClose=null;
    protected MainBaseFrame main;
    protected WizardBaseView parentView;
    protected Meta2Entity entity;
    protected int level=0;
    protected I_Value<String> back;
    protected FormContext2 context;
    protected boolean busy=false;
    public static String openWizard(MainBaseFrame main0, Meta2Entity entity,I_Value<String> back){
        String name = "Wizard"+entity.getClass().getSimpleName();
        try {
            Class  cls = Class.forName("romanow.abc.desktop.wizard."+name);
            WizardBaseView view = (WizardBaseView)cls.newInstance();
            view.back = back;
            view.main = main0;
            view.openForm(null,entity);
            view.revalidate();
            view.repaint(1000);
            return null;
            } catch (Exception ee){
                return "Ошибка создания формы "+name+": "+ee.toString();
                }
            }
    public static void setArtifactsChoice(ArtifactList artifacts, Choice ImageList, long oid){
        ImageList.removeAll();
        artifacts.sortById();
        ImageList.add("...");
        for(Artifact ctr : artifacts)
            ImageList.add(ctr.getTitle());
        if (oid==0)
            return;
        int idx=0;
        for(idx=0;idx<artifacts.size();idx++)
            if (artifacts.get(idx).getOid()==oid){
                ImageList.select(idx+1);
                break;
            }
        }
    public String openWizardByType(Meta2Entity entity){
        return openWizardByType(entity,this,null,null,null);
        }
    public static String openWizardByType(Meta2Entity entity, WizardBaseView parent, I_Value<String> closeCode, I_Value<String> back0, FormContext2 context2){
        String name = "Wizard"+entity.getClass().getSimpleName();
        try {
            Class  cls = Class.forName("romanow.abc.desktop.wizard."+name);
            WizardBaseView view = (WizardBaseView)cls.newInstance();
            view.main = parent!=null ?parent.main : context2.getMain();
            view.onClose = closeCode;
            view.back = back0;
            view.context = context2;
            view.openForm(parent,entity);
            view.revalidate();
            view.repaint(1000);
            return null;
            } catch (Exception ee){
                return "Ошибка создания формы "+name+": "+ee.toString();
                }
        }
    public void resizeHeight(){
        resizeHeight(getSize().height);
        }
    public void resizeHeight(int high){
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        Dimension dim = getSize();
        Rectangle rec = getBounds();
        rec.height = high;
        if (rec.y + high > sSize.height - 50)
            rec.y -= rec.y + high - sSize.height + 50;
        setBounds(rec);
        }
    public void openForm(WizardBaseView parentView0,Meta2Entity entity0) {
        busy = true;
        level = parentView0==null ? 0 : level+1;
        parentView = parentView0;
        if (parentView !=null){
            main = parentView.main;
            back = parentView.back;
            parentView.setVisible(false);
            }
        entity = entity0;
        setVisible(true);
        setLocation(WizardX0+level*10,WizardY0+level*10);
        setSize(800,170);
        resizeHeight(170);
        setTitle("Уровень "+(level+1)+"  "+entity.getFullTitle());
        Title.setText(entity.getTitle());
        Comment.setText(entity.getComment());
        ShortName.setText(entity.getShortName());
        if (entity instanceof Meta2Face){
            DOType.setText(((Meta2Face)entity).getDOType());
            Not61850.setSelected(((Meta2Face)entity).isOut61850Model());
            In60870.setSelected(((Meta2Face)entity).isIn60870Model());
            }
        else{
            DOType.setVisible(false);
            Not61850.setVisible(false);
            In60870.setVisible(false);
            }
        busy=false;
        }

    public WizardBaseView(){
        initComponents();
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

        ShortName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Comment = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Title = new javax.swing.JTextField();
        DOType = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Not61850 = new javax.swing.JCheckBox();
        In60870 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        ShortName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ShortNameKeyPressed(evt);
            }
        });
        getContentPane().add(ShortName);
        ShortName.setBounds(80, 10, 80, 25);

        jLabel1.setText("Название");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(180, 10, 70, 16);

        Comment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CommentKeyPressed(evt);
            }
        });
        getContentPane().add(Comment);
        Comment.setBounds(120, 50, 450, 25);

        jLabel2.setText("Комментарий");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 50, 100, 20);

        jLabel3.setText("DOType");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(590, 50, 70, 20);

        Title.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TitleKeyPressed(evt);
            }
        });
        getContentPane().add(Title);
        Title.setBounds(270, 10, 360, 25);

        DOType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DOTypeKeyPressed(evt);
            }
        });
        getContentPane().add(DOType);
        DOType.setBounds(650, 50, 80, 25);

        jLabel4.setText("Имя ");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 20, 40, 16);

        Not61850.setText("МЭК 61850 исключен");
        Not61850.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Not61850ItemStateChanged(evt);
            }
        });
        getContentPane().add(Not61850);
        Not61850.setBounds(650, 10, 150, 20);

        In60870.setText("МЭК 60870 включен");
        In60870.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                In60870ItemStateChanged(evt);
            }
        });
        getContentPane().add(In60870);
        In60870.setBounds(650, 30, 140, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void closeForm(){
        if (isClosed)
            return;
        if (parentView!=null)
            parentView.setVisible(true);
        if (onClose!=null)
            onClose.onEnter(entity.getTitle());
        isClosed=true;
        dispose();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void ShortNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ShortNameKeyPressed
        onStringKeyPressed("shortName", ShortName, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                entity.setShortName(value);
            }
        });
    }//GEN-LAST:event_ShortNameKeyPressed

    private void TitleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TitleKeyPressed
        onStringKeyPressed("title", Title, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                entity.setTitle(value);
            }
        });
    }//GEN-LAST:event_TitleKeyPressed

    private void CommentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CommentKeyPressed
        onStringKeyPressed("comment", Comment, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                entity.setComment(value);
            }
        });
    }//GEN-LAST:event_CommentKeyPressed

    private void DOTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DOTypeKeyPressed
        onStringKeyPressed("logDevName", DOType, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                ((Meta2Face)entity).setDOType(value);
            }
        });
    }//GEN-LAST:event_DOTypeKeyPressed

    private void Not61850ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Not61850ItemStateChanged
        if (busy)
            return;
        ((Meta2Face)entity).setOut61850Model(Not61850.isSelected());
        back.onEnter("Изменено 61850 исключен="+Not61850.isSelected());
    }//GEN-LAST:event_Not61850ItemStateChanged

    private void In60870ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_In60870ItemStateChanged
        if (busy)
            return;
        ((Meta2Face)entity).setIn60870Model(In60870.isSelected());
        back.onEnter("Изменено 61850 включен="+In60870.isSelected());
    }//GEN-LAST:event_In60870ItemStateChanged

    public void onKeyPressedFloat(String name, JTextField fld, KeyEvent evt, I_WizardActionFloat action){
        if(evt.getKeyCode()!=10) return;
        float xx=0;
        try {
            xx = Float.parseFloat(fld.getText());
            action.onAction(xx);
            if (evt!=null)
                main.viewUpdate(evt,true);
            back.onEnter("Изменено "+name+": "+fld.getText());
        } catch (Exception ee){
            System.out.println("Ошибка вещественного: "+fld.getText());
            if (evt!=null)
                main.viewUpdate(evt,false);
        }
    }

    public void onKeyPressed(String name, JTextField fld, KeyEvent evt, I_WizardAction action){
        if(evt.getKeyCode()!=10) return;
        int xx=0;
        try {
            xx = Integer.parseInt(fld.getText());
            action.onAction(xx);
            if (evt!=null)
                main.viewUpdate(evt,true);
            back.onEnter("Изменено "+name+": "+fld.getText());
        } catch (Exception ee){
            System.out.println("Ошибка целого: "+fld.getText());
            if (evt!=null)
                main.viewUpdate(evt,false);
        }
    }
    public void onKeyPressed(String name, JCheckBox hex, JTextField fld, KeyEvent evt, I_WizardAction action){
        if(evt.getKeyCode()!=10) return;
        int xx=0;
        String ss = fld.getText();
        try {
            if (!hex.isSelected())
                xx = Integer.parseInt(ss);
            else{
                xx = Integer.parseInt(ss.startsWith("0x") ? ss.substring(2) : ss,16);
            }
            action.onAction(xx);
            if (evt!=null)
                main.viewUpdate(evt,true);
            back.onEnter("Изменено "+name+": "+fld.getText());
        } catch (Exception ee){
            System.out.println("Ошибка целого: "+fld.getText());
            if (evt!=null)
                main.viewUpdate(evt,false);
        }
    }
    public void onColorKeyPressed(String name,JTextField fld, JButton button, KeyEvent evt, I_WizardAction action){
        if(evt.getKeyCode()!=10) return;
        long xx=0;
        try {
            xx = Long.parseLong(fld.getText(),16);
            action.onAction((int)xx);
            if (evt!=null)
                main.viewUpdate(evt,true);
            button.setBackground(new Color((int)xx));
            back.onEnter("Изменено "+name+": "+fld.getText());
        } catch (Exception ee){
            System.out.println("Ошибка целого(16): "+fld.getText());
            if (evt!=null)
                main.viewUpdate(evt,false);
        }
    }
    public void onStringKeyPressed(String name,JTextField fld, KeyEvent evt, I_WizardActionString action){
        if(evt.getKeyCode()!=10) return;
        action.onAction(fld.getText());
        if (evt!=null)
            main.viewUpdate(evt,true);
        back.onEnter("Изменено "+name+": "+fld.getText());
    }
    public void onKey16Pressed(String name,JTextField fld, KeyEvent evt, I_WizardAction action){
        if(evt.getKeyCode()!=10) return;
        long xx=0;
        try {
            xx = Long.parseLong(fld.getText(),16);
            action.onAction((int)xx);
            if (evt!=null)
                main.viewUpdate(evt,true);
            back.onEnter("Изменено "+name+": "+fld.getText());
        } catch (Exception ee){
            System.out.println("Ошибка целого(16): "+fld.getText());
            if (evt!=null)
                main.viewUpdate(evt,false);
        }
    }

    public static ArrayList<ConstValue> createEquipElemList(){
        ArrayList<ConstValue> out = new ArrayList<>();
        HashMap<Integer,ConstValue> map = Values.constMap().getGroupMapByValue("MetaElem");
        out.add(map.get(Values.MEDataRegister));
        out.add(map.get(Values.MESetting));
        out.add(map.get(Values.MEBitRegister));
        out.add(map.get(Values.MECommandRegister));
        out.add(map.get(Values.MEStateRegister));
        out.add(map.get(Values.MEArray));
        out.add(map.get(Values.MECollection));
        out.add(map.get(Values.MEBitArray));
        return out;
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Comment;
    private javax.swing.JTextField DOType;
    private javax.swing.JCheckBox In60870;
    private javax.swing.JCheckBox Not61850;
    private javax.swing.JTextField ShortName;
    private javax.swing.JTextField Title;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
