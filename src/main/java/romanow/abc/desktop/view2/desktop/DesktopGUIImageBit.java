package romanow.abc.desktop.view2.desktop;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUI2StateBox;
import romanow.abc.core.entity.metadata.view.Meta2GUIImage;
import romanow.abc.core.entity.metadata.view.Meta2GUIImageBit;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.ESSMainBase;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.OK;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static romanow.abc.core.Utils.httpError;
import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIImageBit extends View2BaseDesktop {
    protected JComponent textField;
    private int bitNum=0;
    private int lastBitValue=0;
    private Image image0 = null;
    private Image image1 = null;
    private JPanel imagePanel=null;
    private Meta2GUIImageBit element;
    public DesktopGUIImageBit(){
        setType(Values.GUIImageBit);
        }
    //-----------------------------------------------------------------------
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        element = (Meta2GUIImageBit) getElement();
        int hh = element.getH();
        if (hh==0) hh=25;
        if (element.getDx()!=0){
            setLabel(panel);
            }
        FormContext2 context= getContext();
        imagePanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                g.setColor(new Color(0xfff0f0f0));
                g.fillRect(0,0,imagePanel.getWidth(),imagePanel.getHeight());
                Image image = lastBitValue==0 ? image0 : image1;
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
        imagePanel.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(element.getImageW()),
                context.dy(element.getImageH()));
        panel.add(imagePanel);
        imagePanel.repaint();
        imagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLUE,1));
        setInfoClick(imagePanel);
        bitNum = element.getBitNum();
        }
    public void showInfoMessage() {
        Meta2BitRegister set = (Meta2BitRegister) getRegister();
        Meta2Bit bit = set.getBits().getByCode(bitNum);
        String ss = "Разряд регистра "+toHex(set.getRegNum()+getRegOffset())+" ["+toHex(set.getRegNum())+"]("+bitNum+") "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+=bit==null ? " не найден " : bit.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        lastBitValue = (int)(vv>>bitNum) & 01;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                imagePanel.repaint();
                }
            });
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = (Meta2Register) getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2BitRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        Meta2GUIImageBit element = (Meta2GUIImageBit) getElement();
        Pair<String,Image> one = loadPicture(context0,element,element.getPictureFor0().getRef());
        if (one.o1!=null)
            return one.o1;
        image0 = one.o2;
        one = loadPicture(context0,element,element.getPictureFor1().getRef());
        if (one.o1!=null)
            return one.o1;
        image1 = one.o2;
        return null;
        }
    public Pair<String,Image> loadPicture(FormContext2 context0, Meta2GUIImageBit element0,Artifact art){
        try {
            Call<ResponseBody> call2 = context0.getService().downLoad(context0.getToken(),art.getOid());
            Response<ResponseBody> response = call2.execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                BufferedImage originalImage = ImageIO.read(body.byteStream());
                Image image = originalImage.getScaledInstance(context0.dx(element0.getImageW()), context0.dy(element0.getImageH()), Image.SCALE_DEFAULT);
                return new Pair<>(null,image);
                }
            else{
                return new Pair<>(httpError(response),null);
            }
        } catch (Exception ee){
            return new Pair<>("Ошибка загрузки: "+art.getTitle()+"\n"+ee.toString(),null);
        }
    }


}
