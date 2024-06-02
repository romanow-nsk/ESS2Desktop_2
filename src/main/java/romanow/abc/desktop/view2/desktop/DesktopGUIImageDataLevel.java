package romanow.abc.desktop.view2.desktop;

import lombok.Getter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2RegLink;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIImageDataLevel;
import romanow.abc.core.entity.metadata.view.Meta2GUIImageScriptLevel;
import romanow.abc.core.entity.metadata.view.Meta2GUIRegW2;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2ScriptFile;
import romanow.abc.core.script.CallContext;
import romanow.abc.core.script.ScriptException;
import romanow.abc.core.types.TypeFace;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;

import static romanow.abc.core.Utils.httpError;

public class DesktopGUIImageDataLevel extends View2BaseDesktop{
    private Image image = null;
    private JPanel imagePanel=null;
    private Meta2GUIImageDataLevel element;
    private double scriptValue=0;
    private boolean valueValid=false;
    public DesktopGUIImageDataLevel(){
        setType(Values.GUIImageDataLevel);
        }
    ImageFilter filter = new RGBImageFilter() {
        int transparentColor = Color.white.getRGB() | 0xFF000000;
        public final int filterRGB(int x, int y, int rgb) {
            if ((rgb | 0xFF000000) == transparentColor)
                return 0x00FFFFFF & rgb;
            else
                return rgb;
            }
        };
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUIImageDataLevel) getElement();
        imagePanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                try {                       // 89.05  Задержка для снижения частоты вызова (Idle ?????)
                    Thread.sleep(Values.PicturesEventDelayMs);
                    } catch (InterruptedException e) {}
                int yy=0;
                if (valueValid){
                    if (scriptValue > element.getHighLevel())
                        yy = imagePanel.getHeight();
                    else
                    if (scriptValue < element.getLowLevel())
                        yy=0;
                    else{
                        yy = (int)((scriptValue-element.getLowLevel())/(element.getHighLevel()-element.getLowLevel())*imagePanel.getHeight());
                        }
                    }
                Color res = Color.green;
                if (scriptValue<element.getFailLevel())
                    res  = Color.red;
                else
                    if (scriptValue<element.getWarnLevel())
                        res = Color.yellow;
                g.setColor(new Color(getLabelColor().getRGB() | 0xFF000000));
                g.fillRect(0,0,context.dx(imagePanel.getWidth()),imagePanel.getHeight()-yy);
                g.setColor(new Color(res.getRGB() | 0xFF000000));
                g.fillRect(0,imagePanel.getHeight()-yy,imagePanel.getWidth(),yy);
                if (image != null) {
                    g.drawImage(image, 0, 0, null);
                    imagePanel.setBorder(BorderFactory.createLineBorder(getElemBackColor(),2));
                    }
                else{
                    imagePanel.setBorder(BorderFactory.createLineBorder(getElemBackColor(),1));
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
        String formName = element.getFormName();
        if (formName!=null && formName.length()!=0){
            imagePanel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1){
                        if (element.isOwnUnit() && element.getUnitLevel()!=0){
                            context.setIndex(element.getUnitLevel(),element.getUnitIdx());
                            }
                        if (!element.isOnlyIndex())
                            context.openForm(element.getFormName(),FormContext2.ModeNext);
                        else
                            context.repaintView();
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
        Meta2Register register = getRegister();
        scriptValue = register.regValueToFloat(getUnitIdx(),(int)vv);
        valueValid=true;
        imagePanel.repaint();
        }


    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2GUIImageDataLevel element = (Meta2GUIImageDataLevel) getElement();
        try {
            Call<ResponseBody> call2 = context0.getService().downLoad(context0.getToken(),element.getPicture().getOid());
            Response<ResponseBody> response = call2.execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                BufferedImage originalImage = ImageIO.read(body.byteStream());
                image = originalImage.getScaledInstance(context0.x(element.getImageW()), context0.y(element.getImageH()), Image.SCALE_DEFAULT);
                }
            else{
                return httpError(response);
                }
            } catch (Exception ee){
                return "Ошибка загрузки: "+element.getPicture().getTitle()+"\n"+ee.toString();
                }
        Meta2Register register = getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2DataRegister || register instanceof Meta2SettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
    /*
    @Override
    public void repaintBefore(){
        if (scriptFile==null || !scriptFile.isValid() || element.isNoCalc()){
            calculaded=false;
            imagePanel.repaint();
            return;
            }
        try {
            CallContext context = scriptFile.getScriptCode();
            context.reset();
            context.call(false);
            TypeFace result = scriptFile.getScriptCode().getVariables().get(Values.ScriptResultVariable);
            if (result==null) {
                calculaded=false;
                imagePanel.repaint();
                new Message(300, 300, "Ошибка исполнения скрипта\nОтстутствует результат", Values.PopupMessageDelay);
                }
            else{
                calculaded=true;
                scriptValue = result.getRealValue();
                imagePanel.repaint();
                }
            } catch (ScriptException e) {
                calculaded=false;
                imagePanel.repaint();
                new Message(300,300,"Ошибка исполнения скрипта\n"+e.toString(),Values.PopupMessageDelay);
                }
            }
     */

}
