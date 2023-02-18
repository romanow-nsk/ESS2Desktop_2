package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.Meta2Entity;
import romanow.abc.core.entity.metadata.view.Meta2GUI3StateBox;
import romanow.abc.core.entity.metadata.view.Meta2GUI3StateBoxSmall;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.wizard.WizardBaseView;

import javax.swing.*;
import java.awt.*;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUI3StateBoxSmall extends DesktopGUI3StateBox {
    public DesktopGUI3StateBoxSmall() {
        setType(Values.GUI3StateBoxSmall);
        }
    private int iconsWarning[]={ 0xFFC8C8C8,0xFF00FFFF,0xFFFF0000,0xFFFFFFFF};
    private int iconsWorking[]={ 0xFFC8C8C8,0xFF00FF00,0xFFFF0000,0xFFFFFFFF};
    @Override
    protected JComponent createComponent() {
        return new JTextField();
        }
    @Override
    protected void viewComponent() {
        JTextField bb = (JTextField) textField;
        bb.setEditable(false);
        bb.setText("");
        }
    public void showInfoMessage() {
        FormContext2 context= getContext();
        Meta2GUI3StateBoxSmall element = (Meta2GUI3StateBoxSmall) getElement();
        int bitNumElem = element.getBitNum();
        Meta2BitRegister set = (Meta2BitRegister)getRegister();
        Meta2Bit bit = set.getBits().getByCode(bitNumElem);
        String ss = "Разряды регистра "+toHex(set.getRegNum()+getRegOffset())+" ["+toHex(set.getRegNum())+"]("+bitNum+"/"+(bitNum+1)+") "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+=bit.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) {
        JTextField bb = (JTextField) textField;
        int pair = (int)(vv>>bitNum) & 03;
        int color = getContext().getForm().getFormLevel()==0 ? iconsWarning[pair] : iconsWorking[pair];
        bb.setBackground(new Color(color));
        }
    @Override
    protected int getSize(){
        return 15;
        }

}
