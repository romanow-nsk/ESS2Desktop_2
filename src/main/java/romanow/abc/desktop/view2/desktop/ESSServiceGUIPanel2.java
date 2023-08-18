package romanow.abc.desktop.view2.desktop;

import romanow.abc.desktop.ESSServiceGUIPanel;
import romanow.abc.desktop.ScreenMode;

public class ESSServiceGUIPanel2 extends ESSServiceGUIPanel {
    public ESSServiceGUIPanel2() {
        setSecondPanel(true);
        }
    public ESSServiceGUIPanel2(ScreenMode screenMode) {
        super(screenMode);
        setSecondPanel(true);
        }
}
