package romanow.abc.drivers;

import retrofit2.Call;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.ErrorList;
import romanow.abc.core.UniException;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JInt;
import romanow.abc.core.entity.metadata.CallResult;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.desktop.APICall2;
import romanow.abc.desktop.ESSClient;

import java.util.ArrayList;
import java.util.HashMap;

public class ModBusClientProxyDriver implements I_ModbusGroupDriver {
    private RestAPIBase service;
    private RestAPIESS2 service2;
    private boolean ready=false;
    private long oid=0;  // =0, не включает соединение с ПЛК, иначе включает и выбирает мета-данные
    private String token;
    private ESSClient main;
    @Override
    public ErrorList openConnection(Object needed[], HashMap<String, String> paramList){
        ready=false;
        int ii=0;
        ErrorList errors = new ErrorList();
        try {
            service = (RestAPIBase)needed[0];
            ii=1;
            service2 = (RestAPIESS2)needed[1];
            ii=2;
            main = (ESSClient)needed[2];
            } catch (Exception ee){
                errors.addError("Недопустимый класс драйвера "+needed[ii].getClass().getSimpleName());
                }
        String size = paramList.get("token");
        int vv;
        if (size==null)
            errors.addError("Нет параметра token");
        token = paramList.get("token");
        /*
        String id = paramList.get("id");
        if (id==null){
            oid=0;          // Нет id - использование текущего состояния соединения сервер-ПЛК
            ready = new APICall2<JBoolean>(){
                @Override
                public Call<JBoolean> apiFun() {
                    return service2.isPLMReady(token);
                    }
                }.call(main).value();
            }
        try {
            oid = Long.parseLong(id);
            } catch (Exception ee){
                throw UniException.user("Ошибка формата id");
                }
        final long xx = oid;
        new APICall2<CallResult>(){
            @Override
            public Call<CallResult> apiFun() {
                return service2.connectToEquipment(token);
                }
            }.call(main);
         */
        if (errors.valid())
            ready=true;
        return errors;
        }
    @Override
    public void closeConnection(){
        try {
            new APICall2<CallResult>() {
                @Override
                public Call<CallResult> apiFun() {
                    return service2.disconnectFromEquipment(token);
                }
            }.call(main);
            } catch (Exception ee){}
            ready=false;
        }
    @Override
    public void reopenConnection(ErrorList errorList) {
        }
    @Override
    public int getState() {
        return ready ? ModBusBase.StateOn : ModBusBase.StateOff;
        }

    @Override
    public boolean isReady() {
        return ready;
        }
    @Override
    public int readRegister(String devName,int unit, int regNum) throws UniException {
        if (!ready)
            throw UniException.user("Устройство не готово");
        JInt xx = new APICall2<JInt>(){
            @Override
            public Call apiFun() {
                return service2.readESS2RegisterValue(token,devName,unit,regNum);
            }
        }.call(main);
        return xx.getValue();
        }
    @Override
    public void writeRegister(String devName,int unit, int regNum, int value) throws UniException {
        if (!ready)
            throw UniException.user("Устройство не готово");
        new APICall2<JEmpty>(){
            @Override
            public Call apiFun() {
                return service2.writeESS2RegisterValue(token,devName,unit,regNum,value);
            }
        }.call(main);
    }

    @Override
    public ArrayList<Integer> readRegisters(String devName,int unit, int regNum, int size) throws UniException {
        throw UniException.bug("Функция readRegisters(int unit, int regNum, int size) не поддерживанися в API");
        }


    @Override
    public void writeRegisters(String devName,int unit, int regNum, ArrayList<Integer> values) throws UniException {
        throw UniException.bug("Функция writeRegisters(int unit, int regNum, ArrayList<Integer> values) не поддерживанися в API");
        }
}
