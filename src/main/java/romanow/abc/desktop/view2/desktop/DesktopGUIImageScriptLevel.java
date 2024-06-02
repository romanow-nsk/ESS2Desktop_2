package romanow.abc.desktop.view2.desktop;

import lombok.Getter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIImageScriptLevel;
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
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;

import static romanow.abc.core.Utils.httpError;

public class DesktopGUIImageScriptLevel extends View2BaseDesktop {
    private final static boolean async=true;
    private Image image = null;
    private JPanel imagePanel=null;
    private Meta2GUIImageScriptLevel element;
    private double scriptValue=0;
    private boolean calculaded=false;
    @Getter private ESS2ScriptFile scriptFile=null;
    public DesktopGUIImageScriptLevel(){
        setType(Values.GUIImageScriptLevel);
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
        element = (Meta2GUIImageScriptLevel) getElement();
        imagePanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                try {                       // 89.05  Задержка для снижения частоты вызова (Idle ?????)
                    Thread.sleep(Values.PicturesEventDelayMs);
                    } catch (InterruptedException e) {}
                int yy=0;
                if (scriptFile.isValid()){
                    if (scriptValue > element.getHighLevel())
                        yy = imagePanel.getHeight();
                    else
                    if (scriptValue < element.getLowLevel())
                        yy=0;
                    else{
                        yy = (int)((scriptValue-element.getLowLevel())/(element.getHighLevel()-element.getLowLevel())*imagePanel.getHeight());
                        }
                    }
                g.setColor(new Color(element.getColorBack() | 0xFF000000));
                g.fillRect(0,0,imagePanel.getWidth(),imagePanel.getHeight()-yy);
                g.setColor(new Color(element.getColorLevel() | 0xFF000000));
                g.fillRect(0,imagePanel.getHeight()-yy,imagePanel.getWidth(),yy);
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
        Meta2GUIImageScriptLevel element = (Meta2GUIImageScriptLevel) getElement();
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
        scriptFile = meta0.getScripts().getByName(element.getScripName());
        if (scriptFile==null)
            return "Не найден скрипт "+element.getScripName();
        if (scriptFile.isServerScript())
            return "Cкрипт "+element.getScripName()+" серверный";
        if (scriptFile.getScriptType()!=Values.STCalcClient)
            return "Cкрипт "+element.getScripName()+" - недопустимый тип "+scriptFile.getScriptType();
        if (!scriptFile.isPreCompiled())
            return "Cкрипт "+element.getScripName()+" не компилируется предварительно";
        return null;
        }
    @Override
    public void repaintBefore(){
        if (scriptFile==null || !scriptFile.isValid() || element.isNoCalc()){
            calculaded=false;
            imagePanel.repaint();
            return;
            }
        final CallContext context = scriptFile.getScriptCode();
        context.reset();
        context.setScriptName(scriptFile.getTitle());
        new AsyncSyncRunV(async) {
            @Override
            public void runCode() throws Exception {
                context.call(false);
                }
            @Override
            public void onExeption(Exception e) {
                calculaded=false;
                imagePanel.repaint();
                new Message(300,300,"Ошибка исполнения скрипта\n"+e.toString(),Values.PopupMessageDelay);
                }
            @Override
                public void onSuccess() {
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
                }
            };
        }
            /*
            try {
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
