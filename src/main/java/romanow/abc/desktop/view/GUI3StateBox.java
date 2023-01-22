package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.Message;


import javax.swing.*;

public class GUI3StateBox extends GUIElement {
    //private JTextField textField;
    protected JComponent textField;
    protected int bitNum=0;
    public GUI3StateBox(){
        type = Values.GUI3StateBox;
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
                context.x(element.getX()+element.getDx()+dxOffset+dd-5+offset),
                context.y(element.getY()+dyOffset+(hh-15)/2-5+offset),
                context.x(sz),
                context.y(sz));
        viewComponent();
        setInfoClick(textField);
        panel.add(textField);
        }
    public void showInfoMessage() {
        int bitNumElem = element.getBitNum();
        MetaBitRegister set = (MetaBitRegister)register;
        MetaBit bit = set.getBits().getByNumber(bitNumElem);
        String ss = "Разряды регистра "+(set.getRegNum()+regOffset)+" ["+set.getRegNum()+"]("+bitNum+"/"+(bitNum+1)+") "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+=bit.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public int getRegStep(){
        if (register instanceof MetaBitRegister)
            return ((MetaBitRegister)register).getStep();
        else
            return 0;
        }
    @Override
    public void putValue(long vv) throws UniException {
        int pair = (int)(vv>>bitNum) & 03;
        JButton bb = (JButton)textField;
        String ss = element.getFormLevel()==0 ? iconsWarning[pair] : iconsWorking[pair];
        bb.setIcon(new ImageIcon(getClass().getResource(ss)));
        }
    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta,register0, element0,plm0,onEvent0);
        if (!(register instanceof MetaBitRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
    @Override
    public void setBitNum(int nbit) {
        bitNum = nbit;
        }
}
