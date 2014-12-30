package code.Utils;

import code.daos.basic.DaoProvider;
import dragonsVillage.dtos.DragonDTO;
import dragonsVillage.dtos.LoginUserDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class UtilData {
    public static Object deserializable(byte[] bytes, int length) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(getBytes(bytes,length)));
        return objectInputStream.readObject();
    }

    private static byte[] getBytes(byte[] bytes, int length) {
        byte[] result = new byte[length];
        for(int i=0;i<length;i++){
            result[i] = bytes[i];
        }
        return result;
    }

    public static LoginUserDTO getUserById(long userID) {
        for (LoginUserDTO loginUserDTO : DaoProvider.getMapDAO().getMap().getUsersOnMap()) {
            if(loginUserDTO.getId() == userID) {
                return loginUserDTO;
            }
        }

        return null;
    }

    public static ArrayList<LoginUserDTO> getUsersMapPoint(int x, int y) {
        if(DaoProvider.getMapDAO().getMap().getUsersMap()[x][y] == null) {
            DaoProvider.getMapDAO().getMap().getUsersMap()[x][y] = new ArrayList<>();
        }

        return DaoProvider.getMapDAO().getMap().getUsersMap()[x][y];
    }

    public static ArrayList<DragonDTO> getDragonMap(int x, int y) {
        ArrayList<DragonDTO>[][] dragonsMap = DaoProvider.getMapDAO().getMap().getDragonsMap();
        if(dragonsMap[x][y] == null) {
            dragonsMap[x][y] = new ArrayList<>();
        }
        return dragonsMap[x][y];
    }

    public static void addDragonIfNotExist(int x, int y, DragonDTO dragon) {
        if(!UtilData.getDragonMap(x, y).contains(dragon)) {
            UtilData.getDragonMap(x, y).add(dragon);
        }
    }
}
