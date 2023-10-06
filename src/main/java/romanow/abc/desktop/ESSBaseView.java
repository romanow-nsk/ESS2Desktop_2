package romanow.abc.desktop;

import lombok.Setter;
import romanow.abc.core.constants.ValuesBase;

import javax.swing.*;
import java.awt.*;

public class ESSBaseView extends javax.swing.JFrame {
    protected int winHigh=250;
    protected int winWidth=280;
    @Setter private boolean silenceMode=false;
    private boolean wasEvent=false;
    private boolean finish=false;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(2,2,winWidth-5,winHigh-5);
        }
    public ESSBaseView(){
        winWidth=0;
        winHigh=0;
        setUndecorated(false);
        }
    public ESSBaseView(int ww, int hh){
        winWidth=ww;
        winHigh=hh;
        setUndecorated(true);
        }
    public void setWH(int ww, int hh){
        winWidth=ww;
        winHigh=hh;
        }
    public void positionOn(int x0, int y0){
        setBounds(x0,y0,winWidth,winHigh);
        setVisible(true);
        }
    private Thread thread=null;
    public void delayIt(){
        delayIt(ValuesBase.PopupMessageDelay);
        }
    public void noSilence(){
        wasEvent = true;
        }
    public void delayIt(final  int delay){
        wasEvent=false;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay*1000);
                } catch (InterruptedException e) {
                    return;
                    }
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        if (finish)
                            return;
                        if (!silenceMode | !wasEvent)
                            dispose();
                        else{
                            delayIt(delay);
                            }
                        }
                    });
                }
            });
            thread.start();
        }
    public void closeView(){
        if(thread!=null){
            thread.interrupt();
            finish=true;
            thread=null;
            }
        dispose();
        }
    public void retryLongDelay(){
        if(thread!=null){
            thread.interrupt();
            thread=null;
            }
        delayIt(ValuesBase.PopupLongDelay);
        }
}
