/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;


import okhttp3.MultipartBody;
import org.apache.tools.ant.types.selectors.TypeSelector;
import retrofit2.Call;
import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.artifacts.ArtifactList;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUIImage;
import romanow.abc.core.utils.FileNameExt;
import romanow.abc.desktop.APICall;

import java.util.ArrayList;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIImage extends WizardMeta2GUI {
    private Meta2GUIImage elem;
    private ArtifactList artifacts = new ArtifactList();
    public WizardMeta2GUIImage() {
        initComponents();
        }
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHight(250);
        elem = (Meta2GUIImage)  entity;
        ImageH.setText(""+elem.getImageH());
        ImageW.setText(""+elem.getImageW());
        refreshImageList();
        selectCurrentImage();
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
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        ImageH = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ImageW = new javax.swing.JTextField();
        ImageList = new java.awt.Choice();
        SetImage = new javax.swing.JButton();
        UploadImage = new javax.swing.JButton();
        ImageAlias = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 82, 560, 2);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(10, 120, 560, 2);

        jLabel11.setText("H image");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(30, 130, 50, 14);

        ImageH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageHKeyPressed(evt);
            }
        });
        getContentPane().add(ImageH);
        ImageH.setBounds(30, 150, 50, 25);

        jLabel14.setText("Alias");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(480, 130, 110, 14);

        ImageW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageWKeyPressed(evt);
            }
        });
        getContentPane().add(ImageW);
        ImageW.setBounds(90, 150, 50, 25);
        getContentPane().add(ImageList);
        ImageList.setBounds(160, 150, 270, 20);

        SetImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetImage.setBorderPainted(false);
        SetImage.setContentAreaFilled(false);
        SetImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetImageActionPerformed(evt);
            }
        });
        getContentPane().add(SetImage);
        SetImage.setBounds(440, 145, 30, 30);

        UploadImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UploadImage.setBorderPainted(false);
        UploadImage.setContentAreaFilled(false);
        UploadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadImageActionPerformed(evt);
            }
        });
        getContentPane().add(UploadImage);
        UploadImage.setBounds(600, 145, 30, 30);
        getContentPane().add(ImageAlias);
        ImageAlias.setBounds(480, 150, 110, 25);

        jLabel15.setText("W image");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(90, 130, 60, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void selectCurrentImage(){
        long ownOid = elem.getPicture().getOid();
        if (ownOid==0){
            ImageList.select(0);
            return;
            }
        int idx2=1;
        for(Artifact artifact : artifacts){
            if (artifact.getOid()==ownOid){
                ImageList.select(idx2);
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
                ImageList.removeAll();
                artifacts.sortById();
                ImageList.add("...");
                for(Artifact ctr : artifacts)
                    ImageList.add(ctr.getTitle());
                }
            };
        }

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

    private void SetImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetImageActionPerformed
        if (artifacts.size()==0)
            return;
        int idx = ImageList.getSelectedIndex();
        Artifact art = idx==0 ? new Artifact() : artifacts.get(idx-1);
        elem.getPicture().setOidRef(art);
        back.onEnter("Выбрана картинка "+art.getTitle());
    }//GEN-LAST:event_SetImageActionPerformed

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
                elem.getPicture().setOidRef(art);
                back.onEnter("Выбрана картинка "+art.getTitle());
                refreshImageList();
                }
            };
    }//GEN-LAST:event_UploadImageActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ImageAlias;
    private javax.swing.JTextField ImageH;
    private java.awt.Choice ImageList;
    private javax.swing.JTextField ImageW;
    private javax.swing.JButton SetImage;
    private javax.swing.JButton UploadImage;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
