package romanow.abc.desktop.view2.desktop;

import romanow.abc.dataserver.ESSConsoleServer;
import romanow.abc.desktop.ESSKioskClient;

public class ESSMonitorClient {
        public static void main(String ss[]){
            ESSKioskClient.main(new String[]{"port:4567","conf:KLIAB_DSK","host:10.200.200.70"});
        }
}
