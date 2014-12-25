package code.Utils;

import code.daos.MapDAO;
import code.daos.basic.DaoProvider;
import dragonsVillage.Datagrams.sendDatagram.AddNewPlayerDatagram;
import dragonsVillage.Datagrams.sendDatagram.FullCurrentMap;
import dragonsVillage.dtos.LoginUserDTO;
import dragonsVillage.dtos.MapDTO;

import java.util.ArrayList;
import java.util.List;

public class UtilCommand {
    public static void executeCommand(Object object){
        if(object instanceof String){
            printOnConsole((String) object);
        } else if(object instanceof FullCurrentMap) {
            printOnConsole("getted full map");
            loadMap((FullCurrentMap) object);
        } else if(object instanceof AddNewPlayerDatagram) {
            printOnConsole("add new player");
            printOnConsole("Actual user: " + DaoProvider.getLoggedUserDAO().getLoginUserDTO().getLogin());
            addNewPlayer((AddNewPlayerDatagram) object);
        } else {
            //TODO: add no commend operation
            printOnConsole("Unknow command");
        }
    }

    private static void addNewPlayer(AddNewPlayerDatagram newPlayer) {
        MapDTO mapDTO = DaoProvider.getMapDAO().getMap();
        LoginUserDTO loginUserDTO = newPlayer.getLoginUserDTO();
        mapDTO.getUsersOnMap().add(loginUserDTO);

        mapDTO.getUsersMap()[loginUserDTO.getPositionX()][loginUserDTO.getPositionY()].add(loginUserDTO);

    }

    private static void initEmptyValue() {
        MapDTO map = DaoProvider.getMapDAO().getMap();
        //TODO: refactor name
        int length = map.getMapPointTypes().length;
        List<LoginUserDTO>[][] usersMap = new ArrayList[length][];
        for (int i=0;i<length;i++){
            int y = map.getMapPointTypes()[i].length;
            usersMap[i] = new ArrayList[y];
            for(int j=0;j<y;j++){
                usersMap[i][j] = new ArrayList<>();
            }
        }

        map.setUsersMap(usersMap);
        map.setUsersOnMap(new ArrayList<>());
    }

    private static void loadMap(FullCurrentMap map) {
        DaoProvider.getMapDAO().setMap(map.getMapDTO());
        initEmptyValue();
    }

    private static void printOnConsole(String object) {
        System.out.println(object);
    }
}
