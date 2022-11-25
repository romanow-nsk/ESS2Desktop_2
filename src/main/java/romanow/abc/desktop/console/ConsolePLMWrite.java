package romanow.abc.desktop.console;

import romanow.abc.core.UniException;
import romanow.abc.core.entity.subjectarea.MetaRegister;


import java.util.ArrayList;

public class ConsolePLMWrite extends ConsoleCommand {
    private boolean hex;
    public ConsolePLMWrite(boolean hex0) {
        super(hex0 ? "wrx" : "wr");
        hex = hex0;
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        return "";
        /*
        try {
            if (!client.plm.isReady()){
                return "Устройство не готово\n";
                }
            int sz = command.size();
            if (sz<3)
                return "Не хватает параметров\n";
            int regNum = Integer.parseInt(command.get(1));
            int regOffset = 0;
            if (sz==4)
                regOffset = Integer.parseInt(command.get(2));
            MetaRegister register = client.meta.getRegisters().get(regNum);
            if (register==null){
                return  "Регистр "+regNum+" не найден\n";
                }
            int valIdx =  sz==3 ? 2 : 3;
            String value = command.get(valIdx);
            int regValue;
            regValue = hex ? Integer.parseInt(value,16) : Integer.parseInt(value);
            client.plm.writeRegister("",0,regNum+regOffset,regValue);
            return  "Регистр изменен "+(regNum+regOffset)+":"+value+"\n";
            }
        catch(UniException e1){
            return "Ошибка ПЛК: "+e1.toString()+"\n";
            }
        catch(Exception ee){
            return "Недопустимое значение параметра\n";
            }
        */
        }
    @Override
    public String help() {
        return cmd+" reg <offset> value\t- запись регистра ПЛК "+(hex ? "(hex)" : "")+ "\n";
        }
}
