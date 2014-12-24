package code.Utils;

import code.daos.basic.DaoProvider;
import dragonsVillage.Datagrams.sendDatagram.FullCurrentMap;

public class UtilCommand {
    public static void executeCommand(Object object){
        if(object instanceof String){
            printOnConsole((String) object);
        } else if(object instanceof FullCurrentMap){
            printOnConsole("fullMap");
            loadMap((FullCurrentMap) object);
        } else {
            //TODO: add no commend operation
            printOnConsole("Unknow command");
        }
    }

    private static void loadMap(FullCurrentMap map) {
        DaoProvider.getMapDAO().setMap(map.getMapDTO());
    }

    private static void printOnConsole(String object) {
        System.out.println(object);
    }
}
