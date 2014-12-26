package code.Utils;

import code.daos.basic.DaoProvider;
import code.threads.MoveUserThread;
import dragonsVillage.Datagrams.responseDatagram.*;
import dragonsVillage.Datagrams.sendDatagram.*;
import dragonsVillage.Datagrams.sendDatagram.FullCurrentMap;
import dragonsVillage.dtos.LoginUserDTO;
import dragonsVillage.dtos.MapDTO;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class UtilCommand {
    private static BlockingQueue mutex = new ArrayBlockingQueue(1);
    private static Object avaliableAction = new Object();

    static {
        try {
            mutex.put(avaliableAction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void executeCommand(Object object) throws InterruptedException {
        mutex.take();
        if(object instanceof String){
            printOnConsole((String) object);
        } else if(object instanceof FullCurrentMap) {
            loadMap((FullCurrentMap) object);
        } else if(object instanceof AddNewPlayerToUserDatagram) {
            addNewPlayer((AddNewPlayerToUserDatagram) object);
        } else if(object instanceof PlayerLoggedOutToUserDatagram) {
            removeOtherPlayer((PlayerLoggedOutToUserDatagram) object);
        } else if(object instanceof ResponseUserMoveToUserDatagram) {
            moveUser((ResponseUserMoveToUserDatagram) object);
        } else if(object instanceof ArrayList) {
            //TODO: why serialization in datagram not working by net?
            executeArrayListCommand((ArrayList) object);
        } else {
            //TODO: add no commend operation
            printOnConsole("Unknow command");
        }
        mutex.put(avaliableAction);
    }

    private static void moveUser(ResponseUserMoveToUserDatagram userMoveDatagram) {
        LoginUserDTO movedObject = null;

        if(userMoveDatagram.getUserID() == DaoProvider.getLoggedUserDAO().getLoginUserDTO().getId()) {
            movedObject = DaoProvider.getLoggedUserDAO().getLoginUserDTO();
            DaoProvider.getEngineDAO().setActionAvaliable(false);
        } else {
            movedObject = UtilData.getUserById(userMoveDatagram.getUserID());

        }

        new MoveUserThread(movedObject, userMoveDatagram.getX(),userMoveDatagram.getY());

//        ArrayList<LoginUserDTO> usersMapPoint = UtilData.getUsersMapPoint(movedObject.getPositionX(), movedObject.getPositionY());
//
//
//        movedObject.setPositionX(userMoveDatagram.getX());
//        movedObject.setPositionY(userMoveDatagram.getY());
//
//        usersMapPoint.remove(movedObject);
//
//        usersMapPoint = UtilData.getUsersMapPoint(movedObject.getPositionX(), movedObject.getPositionY());
//
//        usersMapPoint.add(movedObject);

    }

    private static void removeOtherPlayer(PlayerLoggedOutToUserDatagram player) {
        MapDTO map = DaoProvider.getMapDAO().getMap();
        LoginUserDTO user = player.getUser();
        map.getUsersMap()[user.getPositionX()][user.getPositionY()].remove(user);
        map.getUsersOnMap().remove(user);
    }

    private static void executeArrayListCommand(ArrayList object) {
        if(object.size()>0){
            if(object.get(0) instanceof LoginUserDTO) {
                initOtherPlayers(object);
            } else {
                printOnConsole("Unknow simple data");
            }
        } else {
            printOnConsole("Empty simple data");
        }
    }

    private static void addNewPlayer(AddNewPlayerToUserDatagram newPlayer) {
        MapDTO mapDTO = DaoProvider.getMapDAO().getMap();
        LoginUserDTO loginUserDTO = newPlayer.getLoginUserDTO();
        mapDTO.getUsersOnMap().add(loginUserDTO);

        mapDTO.getUsersMap()[loginUserDTO.getPositionX()][loginUserDTO.getPositionY()].add(loginUserDTO);

    }

    private static void initEmptyValue() {
        MapDTO map = DaoProvider.getMapDAO().getMap();
        //TODO: refactor name
        int length = map.getMapPointTypes().length;
        ArrayList<LoginUserDTO>[][] usersMap = new ArrayList[length][];
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

    private static void initOtherPlayers(ArrayList<LoginUserDTO> usersOnMap) {
        MapDTO map = DaoProvider.getMapDAO().getMap();
        for (LoginUserDTO otherPlayer : usersOnMap) {
            map.getUsersMap()[otherPlayer.getPositionX()][otherPlayer.getPositionY()].add(otherPlayer);
            map.getUsersOnMap().add(otherPlayer);
        }

    }

    private static void printOnConsole(String object) {
        System.out.println(object);
    }
}
