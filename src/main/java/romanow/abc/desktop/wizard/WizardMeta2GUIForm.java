/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUICollection;
import romanow.abc.desktop.I_Button;

import romanow.abc.desktop.OK;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;


import static romanow.abc.core.entity.metadata.view.Meta2GUI.createByType;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIForm extends WizardBaseView {

    /**
     * Creates new form WizardMeta2Equipment
     */
    public WizardMeta2GUIForm() {
        initComponents();
        }
    private ArrayList<ConstValue> types = new ArrayList<>();
    private ArrayList<ConstValue> access = new ArrayList<>();
    private Meta2GUIForm view;
    private Meta2GUICollection list;
    private WizardMetaEntitySelector selector;
    private Meta2EntityList<Meta2GUIForm> forms;
    @Override
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(400);
        view = (Meta2GUIForm) entity0;
        list = view.getControls();
        selector = new WizardMetaEntitySelector("Элементы ЧМИ", Values.MEGUIElement,callBack);
        selector.setBounds(10,80,750,40);
        getContentPane().add(selector);
        access = Values.constMap().getGroupList("AccessLevel");
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
        AccessLevel.removeAll();
        WriteLevel.removeAll();
        for(ConstValue cc : access){
            AccessLevel.add(cc.title());
            WriteLevel.add(cc.title());
            }
        Parents.removeAll();
        WizardBaseView root = parentView;
        if (root!=null){
            while(root.parentView!=null)
                root = root.parentView;
            if (!(root instanceof WizardMeta2GUIView))
                System.out.println("Ошибка поиска предка");
            else{
                forms = ((WizardMeta2GUIView)root).getList();
                Parents.add("...");
                for(Meta2GUIForm ff : forms.getList())
                    Parents.add(ff.getTitle());
                Parents.select(0);
                for(int i=0;i<forms.getList().size();i++){
                    if (view.getParentName().equals(forms.getList().get(i).getTitle())){
                        Parents.select(i+1);
                        break;
                        }
                    }
                }
            }
        refreshList();
        }
    public void selectCoice(int value, Choice cc){
        for(int i=0;i<access.size();i++)
            if (access.get(i).value()==value){
                cc.select(i);
                break;
            }
        }
    public void refreshList(){
            Choice choice = selector.getList();
            choice.removeAll();
            for(Meta2GUI element : list.getList()){
                String ss = element.getFullTitle();
                choice.add(ss);
                }
            FormLevel.setText(""+view.getFormLevel());
            Level.setText(""+view.getLevel());
            ElemCount.setText(""+view.getElementsCount());
            Module.setText(view.getModuleName());
            NoMenu.setSelected(view.isNoMenu());
            Empty.setSelected(view.isEmpty());
            Image.setText(view.getBackImage());
            selectCoice(view.getWriteLevel(),WriteLevel);
            selectCoice(view.getAccessLevel(),AccessLevel);
            GroupIndex.setText(""+view.getBaseFormIndex());
            LinkForm.setSelected(view.isLinkForm());
            BaseForm.setSelected(view.isBaseForm());
            ButtonSize.setText(""+view.getButtonSize());
            SnapShot.setSelected(view.isSnapShot());
            }
    private I_WizardEntitySelector callBack = new I_WizardEntitySelector() {
        @Override
        public void onEdit(int type, int idx) {
            String ss = openWizardByType(list.getList().get(idx));
            if (ss!=null)
                System.out.println(ss);
            }
        @Override
        public void onRemove(int type, int idx) {
            final String title = list.getList().get(idx).getTitle();
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
        public void onAdd(int type0) {
            int type = types.get(Types.getSelectedIndex()).value();
            Meta2GUI entity = (Meta2GUI) createByType(type);
            if (entity.getErrors().getErrCount()!=0){
                System.out.println(entity.getErrors().toString());
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

    @Override
    public void closeForm(){
        super.closeForm();
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
        label111 = new javax.swing.JLabel();
        FormLevel = new javax.swing.JTextField();
        label112 = new javax.swing.JLabel();
        Parents = new java.awt.Choice();
        Module = new javax.swing.JTextField();
        label113 = new javax.swing.JLabel();
        Empty = new javax.swing.JCheckBox();
        NoMenu = new javax.swing.JCheckBox();
        SaveParent = new javax.swing.JButton();
        AccessLevel = new java.awt.Choice();
        label114 = new javax.swing.JLabel();
        SaveAccessLevel = new javax.swing.JButton();
        WriteLevel = new java.awt.Choice();
        label115 = new javax.swing.JLabel();
        SaveWriteLevel = new javax.swing.JButton();
        label116 = new javax.swing.JLabel();
        Image = new javax.swing.JTextField();
        label117 = new javax.swing.JLabel();
        ElemCount = new javax.swing.JTextField();
        label118 = new javax.swing.JLabel();
        label119 = new javax.swing.JLabel();
        Level = new javax.swing.JTextField();
        label120 = new javax.swing.JLabel();
        label121 = new javax.swing.JLabel();
        label122 = new javax.swing.JLabel();
        GroupIndex = new javax.swing.JTextField();
        label123 = new javax.swing.JLabel();
        BaseForm = new javax.swing.JCheckBox();
        LinkForm = new javax.swing.JCheckBox();
        ButtonSize = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        SnapShot = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(Types);
        Types.setBounds(610, 140, 140, 25);

        jLabel1.setText("Элемент ЧМИ+");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(500, 150, 100, 14);

        label111.setText("Модуль");
        getContentPane().add(label111);
        label111.setBounds(10, 190, 60, 14);

        FormLevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FormLevelKeyPressed(evt);
            }
        });
        getContentPane().add(FormLevel);
        FormLevel.setBounds(70, 230, 40, 25);

        label112.setText("группы");
        getContentPane().add(label112);
        label112.setBounds(10, 245, 60, 10);
        getContentPane().add(Parents);
        Parents.setBounds(310, 150, 140, 20);

        Module.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ModuleKeyPressed(evt);
            }
        });
        getContentPane().add(Module);
        Module.setBounds(70, 190, 170, 25);

        label113.setText("Предок");
        getContentPane().add(label113);
        label113.setBounds(250, 150, 60, 14);

        Empty.setText("пустая");
        Empty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EmptyItemStateChanged(evt);
            }
        });
        getContentPane().add(Empty);
        Empty.setBounds(690, 260, 61, 23);

        NoMenu.setText("без меню");
        NoMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NoMenuItemStateChanged(evt);
            }
        });
        getContentPane().add(NoMenu);
        NoMenu.setBounds(610, 260, 90, 23);

        SaveParent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveParent.setBorderPainted(false);
        SaveParent.setContentAreaFilled(false);
        SaveParent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveParentActionPerformed(evt);
            }
        });
        getContentPane().add(SaveParent);
        SaveParent.setBounds(460, 140, 30, 30);
        getContentPane().add(AccessLevel);
        AccessLevel.setBounds(310, 190, 140, 20);

        label114.setText("Доступ");
        getContentPane().add(label114);
        label114.setBounds(250, 190, 60, 14);

        SaveAccessLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveAccessLevel.setBorderPainted(false);
        SaveAccessLevel.setContentAreaFilled(false);
        SaveAccessLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAccessLevelActionPerformed(evt);
            }
        });
        getContentPane().add(SaveAccessLevel);
        SaveAccessLevel.setBounds(460, 180, 30, 30);
        getContentPane().add(WriteLevel);
        WriteLevel.setBounds(310, 230, 140, 20);

        label115.setText("Запись");
        getContentPane().add(label115);
        label115.setBounds(250, 230, 60, 14);

        SaveWriteLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveWriteLevel.setBorderPainted(false);
        SaveWriteLevel.setContentAreaFilled(false);
        SaveWriteLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveWriteLevelActionPerformed(evt);
            }
        });
        getContentPane().add(SaveWriteLevel);
        SaveWriteLevel.setBounds(460, 220, 30, 30);

        label116.setText("Фон (файл)");
        getContentPane().add(label116);
        label116.setBounds(500, 185, 80, 14);

        Image.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageKeyPressed(evt);
            }
        });
        getContentPane().add(Image);
        Image.setBounds(610, 180, 140, 25);

        label117.setText(" элементов");
        getContentPane().add(label117);
        label117.setBounds(130, 165, 70, 14);

        ElemCount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ElemCountKeyPressed(evt);
            }
        });
        getContentPane().add(ElemCount);
        ElemCount.setBounds(200, 150, 40, 25);

        label118.setText("Уровень");
        getContentPane().add(label118);
        label118.setBounds(10, 155, 60, 14);

        label119.setText("Уровень");
        getContentPane().add(label119);
        label119.setBounds(10, 230, 60, 14);

        Level.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LevelKeyPressed(evt);
            }
        });
        getContentPane().add(Level);
        Level.setBounds(70, 150, 40, 25);

        label120.setText("Групповых");
        getContentPane().add(label120);
        label120.setBounds(130, 150, 70, 14);

        label121.setText("Индекс");
        getContentPane().add(label121);
        label121.setBounds(140, 220, 70, 14);

        label122.setText("формы");
        getContentPane().add(label122);
        label122.setBounds(140, 250, 70, 14);

        GroupIndex.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GroupIndexKeyPressed(evt);
            }
        });
        getContentPane().add(GroupIndex);
        GroupIndex.setBounds(200, 230, 40, 25);

        label123.setText("базовой");
        getContentPane().add(label123);
        label123.setBounds(140, 235, 70, 14);

        BaseForm.setText("Базовая");
        BaseForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BaseFormItemStateChanged(evt);
            }
        });
        getContentPane().add(BaseForm);
        BaseForm.setBounds(310, 260, 81, 23);

        LinkForm.setText("Ссылка на базовую");
        LinkForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LinkFormItemStateChanged(evt);
            }
        });
        getContentPane().add(LinkForm);
        LinkForm.setBounds(410, 260, 150, 23);

        ButtonSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonSizeKeyPressed(evt);
            }
        });
        getContentPane().add(ButtonSize);
        ButtonSize.setBounds(610, 220, 40, 25);

        jLabel2.setText("Размер кнопки (%)");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(500, 230, 110, 14);

        SnapShot.setText("SnapShot");
        SnapShot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SnapShotItemStateChanged(evt);
            }
        });
        getContentPane().add(SnapShot);
        SnapShot.setBounds(690, 220, 80, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EmptyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EmptyItemStateChanged
        view.setEmpty(Empty.isSelected());
        back.onEnter("Изменено empty"+view.isEmpty());
    }//GEN-LAST:event_EmptyItemStateChanged

    private void NoMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NoMenuItemStateChanged
        view.setNoMenu(NoMenu.isSelected());
        back.onEnter("Изменено noMenu"+view.isNoMenu());
    }//GEN-LAST:event_NoMenuItemStateChanged

    private void SaveParentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveParentActionPerformed
        view.setParentName(Parents.getSelectedIndex()==0 ? "" : Parents.getSelectedItem());
        back.onEnter("Изменено parentName"+view.getParentName());
    }//GEN-LAST:event_SaveParentActionPerformed

    private void FormLevelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormLevelKeyPressed
        onKeyPressed("formLevel", FormLevel, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setFormLevel(value);
            }
        });
    }//GEN-LAST:event_FormLevelKeyPressed

    private void SaveAccessLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAccessLevelActionPerformed
        view.setAccessLevel(access.get(AccessLevel.getSelectedIndex()).value());
        back.onEnter("Изменено accessLevel"+AccessLevel.getSelectedItem());
    }//GEN-LAST:event_SaveAccessLevelActionPerformed

    private void SaveWriteLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveWriteLevelActionPerformed
        view.setWriteLevel(access.get(WriteLevel.getSelectedIndex()).value());
        back.onEnter("Изменено accessLevel"+WriteLevel.getSelectedItem());
    }//GEN-LAST:event_SaveWriteLevelActionPerformed

    private void ModuleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModuleKeyPressed
        onStringKeyPressed("moduleName", Module, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                view.setModuleName(value);
            }
        });
    }//GEN-LAST:event_ModuleKeyPressed

    private void ImageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageKeyPressed
        onStringKeyPressed("image", Image, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                view.setBackImage(value);
            }
        });
    }//GEN-LAST:event_ImageKeyPressed

    private void ElemCountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ElemCountKeyPressed
        onKeyPressed("elementsCount", ElemCount, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setElementsCount(value);
            }
        });
    }//GEN-LAST:event_ElemCountKeyPressed

    private void LevelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LevelKeyPressed
        onKeyPressed("level", Level, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setLevel(value);
            }
        });
    }//GEN-LAST:event_LevelKeyPressed

    private void GroupIndexKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GroupIndexKeyPressed
        onKeyPressed("baseFormIndex", GroupIndex, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setBaseFormIndex(value);
            }
        });
    }//GEN-LAST:event_GroupIndexKeyPressed

    private void BaseFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BaseFormItemStateChanged
        view.setBaseForm(BaseForm.isSelected());
        back.onEnter("Изменено baseForm"+view.isBaseForm());
    }//GEN-LAST:event_BaseFormItemStateChanged

    private void LinkFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LinkFormItemStateChanged
        view.setLinkForm(LinkForm.isSelected());
        back.onEnter("Изменено linkForm"+view.isLinkForm());
    }//GEN-LAST:event_LinkFormItemStateChanged

    private void ButtonSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonSizeKeyPressed
        onKeyPressed("buttonSize", ButtonSize, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setButtonSize(value);
            }
        });
    }//GEN-LAST:event_ButtonSizeKeyPressed

    private void SnapShotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SnapShotItemStateChanged
        view.setSnapShot(SnapShot.isSelected());
        back.onEnter("Изменено snapShot"+SnapShot.isSelected());

    }//GEN-LAST:event_SnapShotItemStateChanged



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice AccessLevel;
    private javax.swing.JCheckBox BaseForm;
    private javax.swing.JTextField ButtonSize;
    private javax.swing.JTextField ElemCount;
    private javax.swing.JCheckBox Empty;
    private javax.swing.JTextField FormLevel;
    private javax.swing.JTextField GroupIndex;
    private javax.swing.JTextField Image;
    private javax.swing.JTextField Level;
    private javax.swing.JCheckBox LinkForm;
    private javax.swing.JTextField Module;
    private javax.swing.JCheckBox NoMenu;
    private java.awt.Choice Parents;
    private javax.swing.JButton SaveAccessLevel;
    private javax.swing.JButton SaveParent;
    private javax.swing.JButton SaveWriteLevel;
    private javax.swing.JCheckBox SnapShot;
    private java.awt.Choice Types;
    private java.awt.Choice WriteLevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel label111;
    private javax.swing.JLabel label112;
    private javax.swing.JLabel label113;
    private javax.swing.JLabel label114;
    private javax.swing.JLabel label115;
    private javax.swing.JLabel label116;
    private javax.swing.JLabel label117;
    private javax.swing.JLabel label118;
    private javax.swing.JLabel label119;
    private javax.swing.JLabel label120;
    private javax.swing.JLabel label121;
    private javax.swing.JLabel label122;
    private javax.swing.JLabel label123;
    // End of variables declaration//GEN-END:variables
}
