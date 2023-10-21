package romanow.abc.desktop.console;

import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.UniException;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.baseentityes.JString;
import okhttp3.MultipartBody;
import retrofit2.Call;

import java.util.ArrayList;

public class ConsoleImportMeta extends ConsoleCommand {
    public ConsoleImportMeta() {
        super("imp");
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        if (command.size()!=2)
            return "Не хватает параметров\n";
        String fname = command.get(1);
        String fullName = fname+".xls";
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fullName,"xls");
        Artifact art = new APICallC<Artifact>(){
            @Override
            public Call<Artifact> apiFun() {
                return client.service.upload(client.debugToken,"Meta-Data import",fname,body);
            }}.call();
        JString ss = new APICallC<JString>(){
            @Override
            public Call<JString> apiFun() {
                return client.service2.importMetaDataXls(client.debugToken,client.sysPassword,art.getOid());
                }}.call();
        String zz = ss.getValue();
        zz += "Импорт метаданных  выполнен\n";
        zz += new ConsoleSystemsList().exec(client,null);
        return zz;
        }
    @Override
    public String help() {
        return cmd+" file\t- импорт метаданных (имя без .xls)\n";
    }
}
