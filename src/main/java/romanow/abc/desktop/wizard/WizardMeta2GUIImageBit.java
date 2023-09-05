/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import okhttp3.MultipartBody;
import retrofit2.Call;
import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.artifacts.ArtifactList;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUIImageBit;
import romanow.abc.core.utils.FileNameExt;
import romanow.abc.desktop.APICall;

import java.awt.*;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIImageBit extends WizardMeta2GUI {
    private ArtifactList artifacts = new ArtifactList();
    private Meta2GUIImageBit elem;
    public WizardMeta2GUIImageBit() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(400);
        elem = (Meta2GUIImageBit) entity;
        WizardRegLinkPanel linkPanel = new WizardRegLinkPanel(10,120,"",elem.getRegLink(),this);
        add(linkPanel);
        BitNum.setText(""+elem.getBitNum());
        RemoteEnable.setSelected(elem.isRemoteEnable());
        ImageH.setText(""+elem.getImageH());
        ImageW.setText(""+elem.getImageW());
        refreshImageList();
        selectCurrentImage(ImageList0,elem.getPictureFor0().getOid());
        selectCurrentImage(ImageList1,elem.getPictureFor1().getOid());
        }
    private void selectCurrentImage(Choice cc, long ownOid){
        if (ownOid==0){
            cc.select(0);
            return;
            }
        int idx2=1;
        for(Artifact artifact : artifacts){
            if (artifact.getOid()==ownOid){
                cc.select(idx2);
                break;
                }
            idx2++;
            }
        }
    private void refreshImageList(){
        new APICall<ArtifactList>(main) {
            @Override
            public Call<ArtifactList> apiFun() {
                return main.getService().getArtifactConditionList(main.getDebugToken(), Values.ArtifactImageType,"",
                        Values.ESSImageFileDescription,"",0,0,0,0);
                }
            @Override
            public void onSucess(ArtifactList oo) {
                artifacts = oo;
                ImageList0.removeAll();
                ImageList1.removeAll();
                artifacts.sortById();
                ImageList0.add("...");
                ImageList1.add("...");
                for(Artifact ctr : artifacts){
                    ImageList0.add(ctr.getTitle());
                    ImageList1.add(ctr.getTitle());
                    }
                }
            };
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
        BitNum = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        RemoteEnable = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        ImageH = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        ImageW = new javax.swing.JTextField();
        ImageList0 = new java.awt.Choice();
        SetImage0 = new javax.swing.JButton();
        UploadImage = new javax.swing.JButton();
        ImageAlias = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        ImageList1 = new java.awt.Choice();
        SetImage1 = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        jCheckBox2.setText("jCheckBox2");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 84, 670, 0);

        BitNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BitNumKeyPressed(evt);
            }
        });
        getContentPane().add(BitNum);
        BitNum.setBounds(240, 150, 40, 25);

        jLabel13.setText("Бит");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(240, 130, 40, 16);

        RemoteEnable.setText("Удаленно");
        RemoteEnable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RemoteEnableItemStateChanged(evt);
            }
        });
        getContentPane().add(RemoteEnable);
        RemoteEnable.setBounds(450, 150, 90, 20);

        jLabel16.setText("H image");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(20, 180, 50, 16);

        ImageH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageHKeyPressed(evt);
            }
        });
        getContentPane().add(ImageH);
        ImageH.setBounds(20, 200, 50, 25);

        jLabel17.setText("Alias");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(290, 130, 110, 16);

        ImageW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageWKeyPressed(evt);
            }
        });
        getContentPane().add(ImageW);
        ImageW.setBounds(80, 200, 50, 25);
        getContentPane().add(ImageList0);
        ImageList0.setBounds(150, 200, 270, 20);

        SetImage0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetImage0.setBorderPainted(false);
        SetImage0.setContentAreaFilled(false);
        SetImage0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetImage0ActionPerformed(evt);
            }
        });
        getContentPane().add(SetImage0);
        SetImage0.setBounds(430, 200, 30, 30);

        UploadImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UploadImage.setBorderPainted(false);
        UploadImage.setContentAreaFilled(false);
        UploadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadImageActionPerformed(evt);
            }
        });
        getContentPane().add(UploadImage);
        UploadImage.setBounds(410, 150, 30, 30);
        getContentPane().add(ImageAlias);
        ImageAlias.setBounds(290, 150, 110, 25);

        jLabel18.setText("W image");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(80, 180, 60, 16);
        getContentPane().add(ImageList1);
        ImageList1.setBounds(150, 240, 270, 20);

        SetImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetImage1.setBorderPainted(false);
        SetImage1.setContentAreaFilled(false);
        SetImage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetImage1ActionPerformed(evt);
            }
        });
        getContentPane().add(SetImage1);
        SetImage1.setBounds(430, 240, 30, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void BitNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BitNumKeyPressed
        onKeyPressed("bitNum", BitNum, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setBitNum(value);
            }
        });
    }//GEN-LAST:event_BitNumKeyPressed

    private void RemoteEnableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RemoteEnableItemStateChanged
        elem.setRemoteEnable(RemoteEnable.isSelected());
        back.onEnter("Изменено remoteEnable"+": "+elem.isRemoteEnable());
    }//GEN-LAST:event_RemoteEnableItemStateChanged

    private void ImageHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageHKeyPressed
        onKeyPressed("imageH", ImageH, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setImageH(value);
            }
        });
    }//GEN-LAST:event_ImageHKeyPressed

    private void ImageWKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageWKeyPressed
        onKeyPressed("imageW", ImageW, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                elem.setImageW(value);
            }
        });
    }//GEN-LAST:event_ImageWKeyPressed

    private void SetImage0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetImage0ActionPerformed
        if (artifacts.size()==0)
        return;
        int idx = ImageList0.getSelectedIndex();
        Artifact art = idx==0 ? new Artifact() : artifacts.get(idx-1);
        elem.getPictureFor0().setOidRef(art);
        back.onEnter("Выбрана картинка "+art.getTitle());
    }//GEN-LAST:event_SetImage0ActionPerformed

    private void UploadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadImageActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт картинок 2.0", "*.*", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), Values.ESSImageFileDescription+" "+ImageAlias.getText(), fname.fileName(), body);
            }
            @Override
            public void onSucess(final Artifact art) {
                refreshImageList();
                }
        };
    }//GEN-LAST:event_UploadImageActionPerformed

    private void SetImage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetImage1ActionPerformed
        if (artifacts.size()==0)
            return;
        int idx = ImageList1.getSelectedIndex();
        Artifact art = idx==0 ? new Artifact() : artifacts.get(idx-1);
        elem.getPictureFor1().setOidRef(art);
        back.onEnter("Выбрана картинка "+art.getTitle());
    }//GEN-LAST:event_SetImage1ActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BitNum;
    private javax.swing.JTextField ImageAlias;
    private javax.swing.JTextField ImageH;
    private java.awt.Choice ImageList0;
    private java.awt.Choice ImageList1;
    private javax.swing.JTextField ImageW;
    private javax.swing.JCheckBox RemoteEnable;
    private javax.swing.JButton SetImage0;
    private javax.swing.JButton SetImage1;
    private javax.swing.JButton UploadImage;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
