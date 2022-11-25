package romanow.abc.desktop.console;

import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;

import retrofit2.Call;

import java.util.ArrayList;

public class ConsoleSystemsList extends ConsoleCommand {
    public ConsoleSystemsList() {
        super("systems");
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        return "";
        /*
        ArrayList<DBRequest> list = new APICallC<ArrayList<DBRequest>>(){
            @Override
            public Call<ArrayList<DBRequest>> apiFun() {
                return client.service.getEntityList(client.debugToken,"MetaExternalSystem", Values.GetAllModeActual,0);
                }
        }.call();
    client.systems.clear();
    String out = "Конфигурации: "+list.size()+"\n";
    for(DBRequest vv : list){
        MetaExternalSystem meta = (MetaExternalSystem) vv.get(client.gson);
        client.systems.add(meta);
        out+=meta.getTitle()+"\n";
        }
    return out;
         */
    }

    @Override
    public String help() {
        return cmd+"\t- список конфигураций (подсистем)\n";
    }
}
