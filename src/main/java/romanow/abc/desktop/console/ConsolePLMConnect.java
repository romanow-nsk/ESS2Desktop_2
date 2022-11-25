package romanow.abc.desktop.console;

import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import retrofit2.Call;

import java.util.ArrayList;

public class ConsolePLMConnect extends ConsoleCommand {
    public ConsolePLMConnect() {
        super("conn");
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        return "";
        /*
        client.getWorkSettings();
        int idx=0;
        if (command.size()>1){
            try {
                idx = Integer.parseInt(command.get(1));
                if (idx<0 || idx>=client.systems.size())
                    return "Недопустимый индекс конфигурации\n";
                } catch (Exception ee){ return "Формат индекса конфигурации: "+command.get(1)+"\n"; }
            }
        long oid = client.systems.get(idx).getOid();
        DBRequest res = new APICallC<DBRequest>() {             // Получить мета-данные для себя
            @Override
            public Call<DBRequest> apiFun() {
                return client.service.getEntity(client.debugToken, "MetaExternalSystem",
                        oid, 4);
                }
            }.call();
        client.meta = (MetaExternalSystem) res.get(client.gson);
        client.meta.createMap();
        client.openPLMDriver();
        final long xx = oid;
        new APICallC<JEmpty>(){
            @Override
            public Call<JEmpty> apiFun() {
                return client.service2.connectToPLM(client.debugToken,xx);
                }
            }.call();
        int plmVersion = client.plm.readRegister("",0,client.meta.getVersionRegNum());
        if (plmVersion!=client.meta.getVersionRegValue())
            return  "Несовпадение версий ПЛК="+plmVersion+" мета-данные="+client.meta.getVersionRegValue()+"\n";
        client.connected = true;
        return "Соединение с ПЛК установлено\n";
         */
    }

    @Override
    public String help() {
        return cmd+" <индекс конфигурации>\t- соединение с ПЛК\n";
    }
}
