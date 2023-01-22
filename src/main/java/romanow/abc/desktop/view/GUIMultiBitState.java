package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GUIMultiBitState extends GUIElement {
    private int regNums[];
    private JTextField textField;
    public GUIMultiBitState(){
        type = Values.GUIMultiBitState;
        }
    @Override
    public int getRegStep(){
        return 0;
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        textField = new JTextField();
        int dd=element.getW2();
        if (dd==0) dd=100;
        textField.setBounds(
                context.x(element.getX()+dxOffset+element.getDx()+5),
                context.y(element.getY()+dyOffset),
                context.x(dd),
                context.y(25));
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        setInfoClick(textField);
        }
    public void createStateText(int regNum, int bitMask, ArrayList<MetaBit> out, long regVal){
        if (regNum==0)
            return;
        MetaRegister register = meta.getRegisterByNum(regNum);
        if (register==null){
            new Message(300,300,"Группа состояний: не найден регистр "+regNum,Values.PopupMessageDelay);
            return;
            }
        if (register.getType()!=Values.RegBitSet){
            new Message(300,300,"Группа состояний: тип регистра "+regNum,Values.PopupMessageDelay);
            return;
            }
        MetaBitRegister bitRegister = (MetaBitRegister)register;
        for(MetaBit bit : bitRegister.getBits()){
            int mask = 1<< bit.getBitNum();
            if ((mask & bitMask)!=0 && (mask & regVal)!=0){  // В маске установлен бит из описателя разряда
                out.add(bit);
                }
            }
        }
    private void sortByPrior(ArrayList<MetaBit> list){
        list.sort(new Comparator<MetaBit>() {
            @Override
            public int compare(MetaBit o1, MetaBit o2) {
                return o2.getPrior()-o1.getPrior();
                }
            });
        }
    public String createRegInfo(int regNum, int bitMask){
        return regNum==0 ? "" : " "+regNum+String.format("[%x] ",bitMask);
        }
    public void showInfoMessage() {
        String ss = "Группа состояний "+createRegInfo(element.getRegNum(),element.getBitMask())+
            createRegInfo(element.getRegNum2(),element.getColorYes())+createRegInfo(element.getRegNum3(),element.getColorNo());
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        ArrayList<MetaBit> out = new ArrayList();
        createStateText(element.getRegNum(),element.getBitMask(),out,vv);
        if (out.size()==0)
            textField.setText("");
        else{
            sortByPrior(out);
            textField.setText(out.get(0).getTitle());
            }
        }
    @Override
    public void putValue(int zz[]) throws UniException {
        boolean multiList = element.getBitNum()!=0;
        ArrayList<MetaBit> out = new ArrayList<>();
        createStateText(element.getRegNum(),element.getBitMask(),out,zz[0]);
        if (zz.length>1)
            createStateText(element.getRegNum2(),element.getColorYes(),out,zz[1]);
        if (zz.length>2)
            createStateText(element.getRegNum3(),element.getColorNo(),out,zz[2]);
        if (out.size()==0)
            textField.setText("");
        else{
            sortByPrior(out);
            textField.setText(out.get(0).getTitle());
            }
        }
    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta,register0, element0,plm0,onEvent0);
        if (!(register instanceof MetaBitRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        if (element.getRegNum2()==0){
            regNums = new int[1];
            regNums[0]=element.getRegNum();
            }
        else
        if (element.getRegNum3()==0){
            regNums = new int[2];
            regNums[0]=element.getRegNum();
            regNums[1]=element.getRegNum2();
            }
        else{
            regNums = new int[3];
            regNums[0]=element.getRegNum();
            regNums[1]=element.getRegNum2();
            regNums[2]=element.getRegNum3();
            }
        return null;
        }
    @Override
    public int[] getRegNum(){
        return regNums;
        }
    }

