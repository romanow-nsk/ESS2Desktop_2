package romanow.abc.desktop.console;

import com.google.gson.Gson;
import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.entity.baseentityes.JEmpty;import retrofit2.Call;
import romanow.abc.core.entity.subjectarea.WorkSettings;

import java.util.ArrayList;

public class ConsoleWS extends ConsoleCommand {
    public ConsoleWS() {
        super("set");
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        client.getWorkSettings();
        if (command.size()==1)
            return showSettings(client,command);
        else
        if (command.size()==3)
            return updateSettings(client,command);
        else
            return "Количество параметров ???\n";
        }
    private void updateWS(ConsoleClient client) throws UniException {
        new APICallC<JEmpty>(){
            @Override
            public Call<JEmpty> apiFun() {
                return client.service.updateWorkSettings(client.debugToken,new DBRequest(client.workSettings,new Gson()));
            }
        }.call();
    }
    private void updateField(ConsoleClient client, String fName) throws UniException {
        new APICallC<JEmpty>(){
            @Override
            public Call<JEmpty> apiFun() {
                return client.service.updateEntityField(client.debugToken,fName,new DBRequest(client.workSettings,client.gson));
                }
            }.call();
        }
    private String showSettings(ConsoleClient client, ArrayList<String> command){
        WorkSettings ws = client.workSettings;
        String out = "";
        out += "Подключение к ПЛК (да/нет):             \t\t\t"+(ws.isPlmConnected() ? "да" : "нет")+"\n";
        out += "Идентификатор мета-системы:             \t\t\t"+ws.getMetaSystemId()+"\n";
        out += "Глубина архива в днях:                  \tSDA  \t"+ws.getArchiveDepthInDay()+"\n";
        out += "Цикл опроса потоковых данных (сек):     \tSDP  \t"+ws.getStreamDataPeriod()+"\n";
        out += "Длинный цикл потоковых данных (сек):    \tSDLP\t"+ws.getStreamDataLongPeriod()+"\n";
        out += "Период обнаружения аварий (сек):        \tFP  \t"+ws.getFailureTestPeriod()+"\n";
        out += "Период обновления форм GUI (сек):       \tGUIP\t"+ws.getGUIrefreshPeriod()+"\n";
        out += "Период опроса дискретных событий (сек): \tDEP  \t"+ws.getEventTestPeriod()+"\n";
        out += "Период устаревания кэша регистров (мс): \tCRAP\t"+ws.getMaxRegisterAge()+"\n";
        out += "IP главного сервер                      \tHIP  \t"+ws.getMainServerIP()+"\n";
        out += "Порт главного сервера                   \tHP  \t"+ws.getMainServerPort()+"\n";
        out += "EMail IP сервера:                       \tEMH  \t"+ws.getMailHost()+"\n";
        out += "EMail почт.ящик:                        \tEMB  \t"+ws.getMailBox()+"\n";
        out += "EMail пароль:                           \tEMPW\t"+ws.getMailPass()+"\n";
        out += "EMail безопасность:                     \tEMS  \t"+ws.getMailSecur()+"\n";
        out += "EMail порт:                             \tEMP  \t"+ws.getMailPort()+"\n";
        out += "EMail адрес:                            \tEMA  \t"+ws.getMailToSend()+"\n";
        out += "Уведомление EMail (вкл/выкл):           \tEMON\t"+(ws.isMailNotifycation() ? "вкл" : "выкл")+"\n";
        return out;
        }
    private String cmdList[]={"SDA","SDP","SDLP","SDPS","FP","GUIP","DEP","CRAP","HIP","HP","EMH","EMB","EMPW","EMS","EMP","EMA","EMON"};
    private String value="";
    private int intVal=0;
    private boolean boolVal=false;
    private boolean parseInt(){
        try {
            intVal = Integer.parseInt(value);
            return true;
            } catch (Exception ee){ return false; }
        }
    private boolean parseBoolean(){
        try {
            boolVal = (Integer.parseInt(value,2) ==1);
            return true;
            } catch (Exception ee){ return false; }
        }
    private String updateSettings(ConsoleClient client, ArrayList<String> command) throws UniException {
        String out = "";
        String cmd = command.get(1);
        value = command.get(2);
        int idx=0;
        for(idx=0; idx<cmdList.length && !cmd.equals(cmdList[idx]);idx++);
        if (idx==cmdList.length)
            return  "Нет команды "+cmd+"\n";
        switch (idx){
            case 0:
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setArchiveDepthInDay(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 1:
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setStreamDataPeriod(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 2: //SDLP
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setStreamDataLongPeriod(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 3: //SDPS
                //if (!parseInt()) return "Формат целого числа: "+value+"\n";
                //client.workSettings.setStreamDataPeriodLimit(intVal);
                //updateWS(client);
                return "Настройки обновлены\n";
            case 4: //FP
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setFailureTestPeriod(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 5: //GUIP
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setGUIrefreshPeriod(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 6: //DEP
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setEventTestPeriod(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 7: //CRAP
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setMaxRegisterAge(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 8: //HIP
                client.workSettings.setMainServerIP(value);
                updateWS(client);
                return "Настройки обновлены\n";
            case 9: //HP
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setMainServerPort(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 10: //EMH
                client.workSettings.setMailHost(value);
                updateWS(client);
                return "Настройки обновлены\n";
            case 11: //EMB
                client.workSettings.setMailBox(value);
                updateWS(client);
                return "Настройки обновлены\n";
            case 12: //EMPW
                client.workSettings.setMailPass(value);
                updateWS(client);
                return "Настройки обновлены\n";
            case 13: //EMS
                client.workSettings.setMailSecur(value);
                updateWS(client);
                return "Настройки обновлены\n";
            case 14: //EMP
                if (!parseInt()) return "Формат целого числа: "+value+"\n";
                client.workSettings.setMailPort(intVal);
                updateWS(client);
                return "Настройки обновлены\n";
            case 15: //EMA
                client.workSettings.setMailToSend(value);
                updateWS(client);
                return "Настройки обновлены\n";
            case 16: //EMON
                if (!parseBoolean()) return "Формат логического: "+value+"\n";
                client.workSettings.setMailNotifycation(boolVal);
                updateWS(client);
                return "Настройки обновлены\n";
            default:
                return "Команда "+cmd+" не реализована\n";
            }
        }
    @Override
    public String help() {
        return cmd+"\t- получение настроек\n"+cmd+"\tparam value - запись настроек\n";
    }
}
