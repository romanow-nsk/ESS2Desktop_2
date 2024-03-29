package romanow.abc.desktop.view2.desktop;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIData;
import romanow.abc.core.entity.metadata.view.Meta2GUIFormButton;
import romanow.abc.core.entity.metadata.view.Meta2GUIImage;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.APICall;
import romanow.abc.desktop.BasePanel;
import romanow.abc.desktop.I_DownLoad;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;
import romanow.abc.desktop.wizard.WizardBaseView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static romanow.abc.core.Utils.httpError;
import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIImage extends View2BaseDesktop {
    private Image image = null;
    private JPanel imagePanel=null;
    private Meta2GUIImage element;
    private int dx,dy;
    public DesktopGUIImage(){
        setType(Values.GUIImage);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUIImage) getElement();
        imagePanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                g.setColor(new Color(0xfff0f0f0));
                g.fillRect(0,0,dx,dy);
                if (image != null) {
                    g.drawImage(image, 0, 0, null);
                    imagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLUE,0));
                    }
                else{
                    imagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLUE,1));
                    }
                super.paintChildren(g);
                super.paintBorder(g);
                }
            };
        dx = context.dx(element.getImageW());
        dy = context.dy(element.getImageH());
        imagePanel.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                dx,dy);
        String formName = element.getFormName();
        if (formName!=null && formName.length()!=0){
            imagePanel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1){
                        if (element.isOwnUnit() && element.getUnitLevel()!=0){
                            context.setIndex(element.getUnitLevel(),element.getUnitIdx());
                            }
                        context.openForm(element.getFormName(),FormContext2.ModeNext);
                        }
                    }
                });
            }
        panel.add(imagePanel);
        imagePanel.repaint();
        setInfoClick(imagePanel);
        }
    public void showInfoMessage() {
        Meta2Register set =  getRegister();
        String ss = "Картинка "+element.getPicture().getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2GUIImage element = (Meta2GUIImage) getElement();
        try {
            Call<ResponseBody> call2 = context0.getService().downLoad(context0.getToken(),element.getPicture().getOid());
            Response<ResponseBody> response = call2.execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                BufferedImage originalImage = ImageIO.read(body.byteStream());
                image = originalImage.getScaledInstance(context0.dx(element.getImageW()), context0.dy(element.getImageH()), Image.SCALE_DEFAULT);
                return null;
                }
            else{
                return httpError(response);
                }
            } catch (Exception ee){
                return "Ошибка загрузки: "+element.getPicture().getTitle()+"\n"+ee.toString();
                }
        }
}
