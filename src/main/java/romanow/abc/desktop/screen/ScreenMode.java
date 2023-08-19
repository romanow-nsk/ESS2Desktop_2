package romanow.abc.desktop.screen;

import romanow.abc.core.constants.Values;

import static romanow.abc.core.constants.Values.ScreenDesktopHeight;
import static romanow.abc.core.constants.Values.ScreenDesktopWidth;

public class ScreenMode {
    private int screenY;            // Размер панели по высоте (из метаданных)
    private int screenX;            // Размер панели по ширине (из метаданных)
    private int realY;              // Размер монитора по высоте
    private int realX;              // Размер монитора по ширине
    private double kX=1;
    private double kY=1;
    private double kW=1;
    private boolean origHW=false;    // Оригинальные пропорции
    public ScreenMode(){
        this(true,ScreenDesktopWidth,ScreenDesktopHeight,ScreenDesktopWidth,ScreenDesktopHeight);
        }
    public int ScreenW(){
        return (int)(ScreenDesktopWidth*kW);
        }
    public int ScreenH(){
        return (int)(ScreenDesktopHeight*kY);
        }
    public ScreenMode(int realX0, int realY0){
        this(false,0,0,realX0,realY0);
        }
    public ScreenMode(boolean origHW0, int screenX0, int screenY0,int realX0, int realY0){
        origHW = origHW0;
        if (realX0==0 || realY0==0){
            realX0 = ScreenDesktopWidth;
            realY0 = ScreenDesktopHeight;
            }
        if (screenX0==0 || screenY0==0){
            screenX0 = ScreenDesktopWidth;
            screenY0 = ScreenDesktopHeight;
            }
        screenX = screenX0;
        screenY = screenY0;
        realX = realX0;
        realY = realY0;
        kX = ((double)realX)/ScreenDesktopWidth;
        kY = ((double)realY)/ScreenDesktopHeight;
        kW=kX;
        if (origHW) {
            double k1 = ((double) ScreenDesktopHeight)/ScreenDesktopWidth;
            double k2 = ((double) screenY)/screenX;
            if (k1 !=k2){
                if (k2<k1)
                    kY *= k2/k1;
                else{
                    kW = kX*k1/k2;
                    }
                }
            }
        }
    public int x(int x){
        return (int)(x*kX);
        }
    public int y(int y){
        return (int)(y*kY);
        }
    public int dx(int dx){
        return (int)(dx*kX);
        }
    public int dy(int dy){
        return (int)(dy*kY);
        }
}
