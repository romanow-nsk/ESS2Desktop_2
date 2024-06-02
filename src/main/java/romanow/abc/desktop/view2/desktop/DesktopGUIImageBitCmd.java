package romanow.abc.desktop.view2.desktop;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIBit2Commands;
import romanow.abc.core.entity.metadata.view.Meta2GUIImageBit;
import romanow.abc.core.entity.metadata.view.Meta2GUIImageBitCmd;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2Device;
import romanow.abc.core.utils.Pair;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import static romanow.abc.core.Utils.httpError;
import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIImageBitCmd extends View2BaseDesktop {
    protected JComponent textField;
    private int bitNum=0;
    private int lastBitValue=0;
    private Image image0 = null;
    private Image image1 = null;
    private JPanel imagePanel=null;
    private Meta2GUIImageBitCmd element;
    private long lastValue=0;            //
    public DesktopGUIImageBitCmd(){
        setType(Values.GUIImageBitCmd);
        }
    //-----------------------------------------------------------------------
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        element = (Meta2GUIImageBitCmd) getElement();
        int hh = element.getH();
        if (hh==0) hh=25;
        if (element.getDx()!=0){
            setLabel(panel);
            }
        FormContext2 context= getContext();
        imagePanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                try {                       // 89.05  Задержка для снижения частоты вызова (Idle ?????)
                    Thread.sleep(Values.PicturesEventDelayMs);
                    } catch (InterruptedException e) {}
                g.setColor(new Color(0xfff0f0f0));
                g.fillRect(0,0,imagePanel.getWidth(),imagePanel.getHeight());
                Image image = lastBitValue==0 ? image0 : image1;
                if (image != null) {
                    g.drawImage(image, 0, 0, null);
                    imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,0));
                    }
                else{
                    imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
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
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
        setInfoClick(imagePanel);
        bitNum = element.getBitNum();
        setInfoClick(imagePanel,true);
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
        lastValue = vv;
        lastBitValue = (int)(vv>>bitNum) & 01;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                imagePanel.repaint();
                }
            });
        }

    @Override
    public void onFullClick(){
        FormContext2 context = getContext();
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !element.isRemoteEnable();
        if (context.isInfoMode()){
            showInfoMessage();
            return;
            }
        if (isNoEditThereMes())
            return;
        if (remoteDisable){
            new Message(300,300,"Запрет удаленного управления", Values.PopupMessageDelay);
            return;
            }
        if (!context.isActionEnable()){
            new Message(300,300,"Недостаточен уровень доступа",Values.PopupMessageDelay);
            return;
            }
        if (lastBitValue==-1){
            new Message(300,300,"Разряд еще не прочитан",Values.PopupMessageDelay);
            return;
            }
        new OK(200, 200, element.getTitle()+" "+(lastBitValue!=0 ? "ОТКЛ" : "ВКЛ"), new I_Button() {
            @Override
            public void onPush() {
                int cmd=0;
                Meta2RegLink link = null;
                try {
                    link = element.getCmdRegLink();
                    ESS2Device device = getDeviceTwo();
                    cmd = lastBitValue !=0 ? element.getCmdOff() : element.getCmdOn();
                    device.getDriver().writeRegister(device.getShortName(),getDevUnitTwo(),link.getRegNum(), cmd);
                    context.getBack().forceRepaint();
                } catch (UniException ex) {
                    String ss = "Ошибка записи команды "+cmd+" в регистр: "+ link.getTitle() + ": "+ ex.toString();
                    context.popup(ss);
                    System.out.println(ss);
                }
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
        register = ((Meta2GUIImageBitCmd)getElement()).getCmdRegLink().getRegister();
        if (!(register instanceof Meta2CommandRegister))
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
