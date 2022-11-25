package romanow.abc.desktop.view;

import romanow.abc.core.UniException;

public class GUIElementsFactory {
    private GUIElement list[] = { new GUILabel(), new GUIData(),
        new GUI2StateBox(), new GUIStateSet(), new GUISetting(), new GUICommand(),
        new GUITimeSetting(), new GUIFormButton(), new GUIFormSelector(), new GUILevelIndicator3(), new GUILevelMultiIndicator(), new GUI2StateBoxSmall(),
        new GUIDataLabel(), new GUI3StateBox(), new GUI3StateBoxSmall(),
        new GUIESSWorkSettingInt(), new GUIESSWorkSettingString(), new GUIESSWorkSettingBoolean(),
        new GUIMultiBitState() };
    public GUIElement getByType(int type) throws UniException {
        for(GUIElement xx : list)
            if (xx.getType()==type){
                try {
                    return xx.getClass().newInstance();
                    } catch (Exception ee) {
                        throw UniException.bug("Ошибка создания элемента ЧМИ "+xx.getClass().getSimpleName());
                        }
                }
        throw UniException.bug("Недопустимый тип при создании элемента ЧМИ "+type);
        }
}
