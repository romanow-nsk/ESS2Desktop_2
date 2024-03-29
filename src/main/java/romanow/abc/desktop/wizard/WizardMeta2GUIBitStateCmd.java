/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.Meta2RegLink;
import romanow.abc.core.entity.metadata.view.Meta2GUIBitStateCmd;

import java.awt.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIBitStateCmd extends WizardMeta2GUI {

    private Meta2GUIBitStateCmd elem;
    public WizardMeta2GUIBitStateCmd() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        elem = (Meta2GUIBitStateCmd) entity;
        if (elem.getCmdReg()==null)
            elem.setCmdReg(new Meta2RegLink());
        resizeHeight(300);
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(10,120,"BitReg",elem.getRegLink(),this);
        add(linkPanel);
        linkPanel = new WizardRegLinkPanel(10,170,"CmdReg",elem.getCmdReg(),this);
        add(linkPanel);
        BitNum.setText(""+elem.getBitNum());
        CmdOn.setText(""+elem.getCmdOn());
        CmdOff.setText(""+elem.getCmdOff());
        ColorYes.setText(""+String.format("%06x",elem.getColorYes()));
        ColorYesButton.setBackground(new Color(elem.getColorYes()));
        ColorNo.setText(""+String.format("%06x",elem.getColorNo()));
        ColorNoButton.setBackground(new Color(elem.getColorNo()));
        W2.setText(""+elem.getW2());
        ButtonSize.setText(""+elem.getButtonSize());
        RemoteEnable.setSelected(elem.isRemoteEnable());
        DisableIndexIn.setText(""+elem.getDisableIndexIn());
        DisableIndexOut.setText(""+elem.getDisableIndexOut());        
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
        jCheckBox2 = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        ColorYes = new javax.swing.JTextField();
        CmdOff = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        ColorYesButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        ColorNo = new javax.swing.JTextField();
        ColorNoButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        W2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ButtonSize = new javax.swing.JTextField();
        RemoteEnable = new javax.swing.JCheckBox();
        BitNum = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        CmdOn = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        DisableIndexIn = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        DisableIndexOut = new javax.swing.JTextField();

        jCheckBox1.setText("jCheckBox1");

        jCheckBox2.setText("jCheckBox2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 84, 670, 0);

        jLabel11.setText("Цвет \"1\"");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(290, 130, 60, 16);

        ColorYes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColorYesKeyPressed(evt);
            }
        });
        getContentPane().add(ColorYes);
        ColorYes.setBounds(290, 150, 80, 25);

        CmdOff.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmdOffKeyPressed(evt);
            }
        });
        getContentPane().add(CmdOff);
        CmdOff.setBounds(420, 180, 70, 25);

        jLabel13.setText("Выкл");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(380, 185, 40, 16);
        getContentPane().add(ColorYesButton);
        ColorYesButton.setBounds(380, 150, 25, 25);

        jLabel14.setText("Цвет \"0\"");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(420, 130, 60, 16);

        ColorNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColorNoKeyPressed(evt);
            }
        });
        getContentPane().add(ColorNo);
        ColorNo.setBounds(420, 150, 80, 25);
        getContentPane().add(ColorNoButton);
        ColorNoButton.setBounds(510, 150, 25, 25);

        jLabel1.setText("W2");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(540, 130, 30, 16);

        W2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                W2KeyPressed(evt);
            }
        });
        getContentPane().add(W2);
        W2.setBounds(540, 150, 40, 25);

        jLabel2.setText("Кнопка (size)");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(590, 130, 80, 16);

        ButtonSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonSizeKeyPressed(evt);
            }
        });
        getContentPane().add(ButtonSize);
        ButtonSize.setBounds(590, 150, 40, 25);

        RemoteEnable.setText("Удаленно");
        RemoteEnable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RemoteEnableItemStateChanged(evt);
            }
        });
        getContentPane().add(RemoteEnable);
        RemoteEnable.setBounds(640, 150, 90, 20);

        BitNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BitNumKeyPressed(evt);
            }
        });
        getContentPane().add(BitNum);
        BitNum.setBounds(240, 150, 40, 25);

        jLabel16.setText("Бит");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(240, 130, 40, 16);

        CmdOn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmdOnKeyPressed(evt);
            }
        });
        getContentPane().add(CmdOn);
        CmdOn.setBounds(290, 180, 70, 25);

        jLabel17.setText("Вкл");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(240, 185, 40, 16);

        jLabel4.setText("Событие ");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(510, 180, 70, 16);

        jLabel5.setText("запрещения");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(510, 190, 80, 16);

        DisableIndexIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DisableIndexInKeyPressed(evt);
            }
        });
        getContentPane().add(DisableIndexIn);
        DisableIndexIn.setBounds(590, 180, 38, 25);

        jLabel3.setText("реакция / генерация");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(580, 200, 120, 16);

        DisableIndexOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DisableIndexOutKeyPressed(evt);
            }
        });
        getContentPane().add(DisableIndexOut);
        DisableIndexOut.setBounds(630, 180, 38, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void ColorYesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColorYesKeyPressed
        onColorKeyPressed("colorYes", ColorYes, ColorYesButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setColorYes(value);
            }
        });
    }//GEN-LAST:event_ColorYesKeyPressed

    private void CmdOffKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmdOffKeyPressed
        onKeyPressed("cmdOff", CmdOff, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setCmdOff(value);
            }
        });
    }//GEN-LAST:event_CmdOffKeyPressed

    private void ColorNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColorNoKeyPressed
        onColorKeyPressed("colorNo", ColorNo, ColorNoButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setColorNo(value);
            }
        });
    }//GEN-LAST:event_ColorNoKeyPressed

    private void W2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_W2KeyPressed
        onKeyPressed("W2", W2, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setW2(value);
            }
        });
    }//GEN-LAST:event_W2KeyPressed

    private void ButtonSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonSizeKeyPressed
        onKeyPressed("buttonSize", ButtonSize, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setButtonSize(value);
            }
        });
    }//GEN-LAST:event_ButtonSizeKeyPressed

    private void RemoteEnableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RemoteEnableItemStateChanged
        elem.setRemoteEnable(RemoteEnable.isSelected());
        back.onEnter("Изменено remoteEnable"+": "+elem.isRemoteEnable());
    }//GEN-LAST:event_RemoteEnableItemStateChanged

    private void BitNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BitNumKeyPressed
        onKeyPressed("bitNum", BitNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setBitNum(value);
            }
        });
    }//GEN-LAST:event_BitNumKeyPressed

    private void CmdOnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmdOnKeyPressed
        onKeyPressed("cmdOn", CmdOn, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setCmdOn(value);
            }
        });
    }//GEN-LAST:event_CmdOnKeyPressed

    private void DisableIndexInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DisableIndexInKeyPressed
        onKeyPressed("disableIndexIn", DisableIndexIn, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setDisableIndexIn(value);
            }
        });
    }//GEN-LAST:event_DisableIndexInKeyPressed

    private void DisableIndexOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DisableIndexOutKeyPressed
        onKeyPressed("disableIndexOut", DisableIndexOut, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setDisableIndexOut(value);
            }
        });
    }//GEN-LAST:event_DisableIndexOutKeyPressed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BitNum;
    private javax.swing.JTextField ButtonSize;
    private javax.swing.JTextField CmdOff;
    private javax.swing.JTextField CmdOn;
    private javax.swing.JTextField ColorNo;
    private javax.swing.JButton ColorNoButton;
    private javax.swing.JTextField ColorYes;
    private javax.swing.JButton ColorYesButton;
    private javax.swing.JTextField DisableIndexIn;
    private javax.swing.JTextField DisableIndexOut;
    private javax.swing.JCheckBox RemoteEnable;
    private javax.swing.JTextField W2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
