package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUI2StateBox;
import romanow.abc.core.entity.metadata.view.Meta2GUI3StateBox;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUI3StateBox extends View2BaseDesktop {
    //private JTextField textField;
    protected JComponent textField;
    protected boolean failMode=false;
    protected int bitNum=0;
    public DesktopGUI3StateBox(){
        setType(Values.GUI3StateBox);
        }
    private String iconsWarning[]={
        "/balllightgray.png","/ballyellow.png","/ballred.png","/ballwhite.png"
        };
    private String iconsWorking[]={
         "/balllightgray.png","/ballgreen.png","/ballred.png","/ballwhite.png"
        };
    //----------------------------------------------------------------------
    protected JComponent createComponent(){
        return new JButton();
        }
    protected void viewComponent(){
        JButton bb = (JButton)textField;
        bb.setIcon(new ImageIcon(getClass().getResource("/ballwhite.png"))); // NOI18N
        bb.setBorderPainted(false);
        bb.setContentAreaFilled(false);
        }
    protected int getSize(){
        return 24;
        }
    protected int getOffset(){
        return 0;
    }
    @Override
    public void addToPanel(JPanel panel) {
        failMode=true;
        FormContext2 context= getContext();
        Meta2GUI2StateBox element = (Meta2GUI2StateBox) getElement();
        int hh = element.getH();
        if (hh==0) hh=25;
        if (element.getDx()!=0){
            setLabel(panel);
            }
        //textField = new JTextField();
        textField = createComponent();
        int dd = element.getW2();
        if (dd==0) dd=50;
        int sz = getSize();
        int offset = (25-sz)/2;
        textField.setBounds(
                context.x(element.getX()+element.getDx()+getDxOffset()+dd-5+offset),
                context.y(element.getY()+getDyOffset()+(hh-15)/2-5+offset),
                context.dx(sz),
                context.dy(sz));
        viewComponent();
        setInfoClick(textField);
        panel.add(textField);
        }
    public void showInfoMessage() {
        FormContext2 context= getContext();
        Meta2GUI3StateBox element = (Meta2GUI3StateBox) getElement();
        int bitNumElem = element.getBitNum();
        Meta2BitRegister set = (Meta2BitRegister)getRegister();
        Meta2Bit bit = set.getBits().getByCode(bitNumElem);
        String ss = "Разряды регистра "+toHex(set.getRegNum()+getRegOffset())+" ["+toHex(set.getRegNum())+"]("+bitNum+"/"+(bitNum+1)+") "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+=bit.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        int pair = (int)(vv>>bitNum) & 03;
        JButton bb = (JButton)textField;
        Meta2GUI3StateBox element = (Meta2GUI3StateBox) getElement();
        String ss = element.isFailMode() ? iconsWorking[pair] : iconsWarning[pair];
        bb.setIcon(new ImageIcon(getClass().getResource(ss)));
        }

    @Override
    public String setParams(FormContext2 context, ESS2Architecture meta, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context,meta, element0,onEvent0);
        if (!(getRegister() instanceof Meta2BitRegister))
            return "Недопустимый "+getRegister().getTypeName()+" для "+getTypeName();
        return null;
        }
    @Override
    public void setBitNum(int nbit) {
        bitNum = nbit;
        }
}
