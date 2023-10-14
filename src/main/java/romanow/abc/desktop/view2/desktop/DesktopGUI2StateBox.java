package romanow.abc.desktop.view2.desktop;

import retrofit2.http.Url;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUI2StateBox;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.OK;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUI2StateBox extends View2BaseDesktop {
    protected JComponent textField;
    private int bitNum=0;
    private JButton cmdButton=null;     // Кнопка
    private long lastBitValue=-1;       // Последнее значение разряда
    private long lastValue=0;           //
    private Meta2GUI2StateBox element;
    private boolean remoteDisable;
    public DesktopGUI2StateBox(){
        setType(Values.GUI2StateBox);
        }
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
    protected void putValueOwn(int cc){
        JButton bb = (JButton)textField;
        URL url = getClass().getResource(getColorIconName(cc));
        bb.setIcon(new ImageIcon(url));
        }
    protected int getSize(){
        return 24;
        }
    protected int getOffset(){
        return 0;
    }
    //-----------------------------------------------------------------------
    private String getColorIconName(int color){
        switch (color){
            case 0x00C0C0C0: return "/balllightgray.png";
            case 0x00FF0000: return "/ballred.png";
            case 0x0000FF00: return "/ballgreen.png";
            case 0x00F0A000: return "/balldarkyellow.png";
            case 0x0000A000: return "/balldarkgreen.png";
            case 0x00FFFF00: return "/ballyellow.png";
            case 0x000000FF: return "/ballblue.png";
            }
        return "/ballwhite.png";
        }
    @Override
    public void addToPanel(JPanel panel) {
        element = (Meta2GUI2StateBox) getElement();
        int hh = element.getH();
        if (hh==0) hh=25;
        setLabel(panel);
        textField = createComponent();
        int dd = element.getW2();
        if (dd==0) dd=10;
        int sz = getSize();
        int offset = (25-sz)/2;
        FormContext2 context = getContext();
        int xx = element.getX()+element.getDx()+getDxOffset()+dd-5+offset;
        int yy = element.getY()+getDyOffset()+(hh-15)/2-5+offset;
        textField.setBounds(
                context.x(xx),
                context.y(yy),
                context.dx(sz),
                context.dy(sz));
        viewComponent();
        setInfoClick(textField);
        panel.add(textField);
        bitNum = element.getBitNum();
        int bSize = element.getButtonSize();
        if (bSize==0)
            return;
        remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !element.isRemoteEnable();
        cmdButton = new JButton();
        cmdButton.setBounds(
                context.x(xx+sz+5),
                context.y(yy-(hh-sz)/2),
                context.dx(bSize),
                context.dy(hh));
        setButtonParams(cmdButton,true);
        if (remoteDisable)
            cmdButton.setBackground(new Color(Values.AccessDisableColor));
        cmdButton.setText("");
        //cmdButton.addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //
        //    }});
        setInfoClick(cmdButton,true);
        panel.add(cmdButton);
        }
    @Override
    public void onFullClick(){
            FormContext2 context = getContext();
            if (context.isInfoMode()){
                showInfoMessage();
                return;
                }
            if (remoteDisable){
                new Message(300,300,"Запрет удаленного управления", Values.PopupMessageDelay);
                return;
                }
            if (!context.isActionEnable()){
                new Message(300,300,"Недостаточен уровень доступа",Values.PopupMessageDelay);
                return;
                }
            if (lastBitValue==-1){
                new Message(300,300,"Разряд еще не прочитан",Values.PopupMessageDelay);
                return;
                }
            new OK(200, 200, element.getTitle()+" "+(lastBitValue!=0 ? "ОТКЛ" : "ВКЛ"), new I_Button() {
                @Override
                public void onPush() {
                    try {
                        long vv = lastValue ^ (1<<bitNum);       // Инвертировать разряд
                        if (element.isTwoUnits())
                            writeMainRegisterTwo((int)vv);
                        else
                            writeMainRegister((int)vv);
                        context.getBack().forceRepaint();
                    } catch (UniException ex) {
                        String ss = "Ошибка изменения разряда: "+ex.toString();
                        context.popup(ss);
                        System.out.println(ss);
                    }
                }
            });
        }
    public void showInfoMessage() {
        Meta2GUI2StateBox element = (Meta2GUI2StateBox) getElement();
        int bitNumElem = element.getBitNum();
        Meta2BitRegister set = (Meta2BitRegister) getRegister();
        Meta2Bit bit = set.getBits().getByCode(bitNumElem);
        String ss = "Разряд регистра "+toHex(set.getRegNum()+getRegOffset())+" ["+toHex(set.getRegNum())+"]("+bitNum+") "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+=bit==null ? " не найден " : bit.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        Meta2GUI2StateBox element = (Meta2GUI2StateBox) getElement();
        lastValue = vv;
        lastBitValue = (vv>>bitNum) & 01;
        if (cmdButton!=null)
            cmdButton.setText(lastBitValue!=0 ? "ОТКЛ" : "ВКЛ");
        int cc = (lastBitValue!=0 ? element.getColorYes() : element.getColorNo()) & 0x00FFFFFF;
        putValueOwn(cc);
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = (Meta2Register) getRegister();
        if (!(register instanceof Meta2BitRegister || ((Meta2GUI2StateBox) getElement()).isMixedRegister() && register instanceof Meta2DataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
