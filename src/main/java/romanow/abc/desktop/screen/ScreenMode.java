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
    private boolean resizeXY=true;  // Выравнивание пропорций
    private boolean original=true;  // Разворачивание до размера экрана
    public ScreenMode(){
        this(false,true,ScreenDesktopWidth,ScreenDesktopHeight,ScreenDesktopWidth,ScreenDesktopHeight);
        }
    public int ScreenW(){
        return (int)(screenX*kX);
        }
    public int ScreenH(){
        return (int)(screenY*kY);
        }
    public ScreenMode(boolean original0, boolean resize, int screenX0, int screenY0,int realX0, int realY0){
        resizeXY = resize;
        original = original0;
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
        if (original){
            kX=1;
            kY=1;
            }
        else{
            kX = ((double)realX)/screenX;
            kY = ((double)realY)/screenY;
            if (resizeXY) {
                if (kY > kX)
                    kY=kX;
                else
                    kX=kY;
                }
            }
        }
    public int x(int x){
        return screenX/2+(int)((x-screenX/2)*kX);
        }
    public int y(int y){
        return screenY/2+(int)((y-screenY/2)*kY);
        }
    public int dx(int dx){
        return (int)(dx*kX);
        }
    public int dy(int dy){
        return (int)(dy*kY);
        }
}
