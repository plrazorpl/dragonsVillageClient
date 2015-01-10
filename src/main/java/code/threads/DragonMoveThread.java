package code.threads;

import code.Utils.UtilConnection;
import code.Utils.UtilData;
import code.daos.basic.DaoProvider;
import code.enums.EDragonStatus;
import code.helpedClass.composition.DragonMoveSideComposition;
import dragonsVillage.Datagrams.requestDatagram.RequestMoveDragonDatagram;
import dragonsVillage.Enums.EMapPointType;
import dragonsVillage.Enums.EMoveSide;
import dragonsVillage.dtos.DragonDTO;
import dragonsVillage.dtos.LoginUserDTO;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class DragonMoveThread extends AThread {

    private DragonDTO dragonDTO;
    private final LoginUserDTO loginUserDTO = DaoProvider.getLoggedUserDAO().getLoginUserDTO();
    private int preferedMaxDistance = 2;
    private Queue<DragonMoveSideComposition> bfsQueue;

    private EDragonStatus status = EDragonStatus.STAY;

    public DragonMoveThread(DragonDTO dragonDTO) {
        super(1000);
        this.dragonDTO = dragonDTO;
    }

    @Override
    public void run() {
        if(status == EDragonStatus.STAY) {
            stayOperation();
        }
    }

    private void stayOperation() {
        double distanceDragon = calculateDistance(dragonDTO.getPositionX(),loginUserDTO.getPositionX(), dragonDTO.getPositionY(), loginUserDTO.getPositionY());
        if(preferedMaxDistance < distanceDragon) {
//            System.out.println("Smok: " + dragonDTO.getName() + " distance: " + distanceDragon);
            EMoveSide eMoveSide = dragonBFS();
//            System.out.println("Move to: " + eMoveSide);
            if(eMoveSide != null) {
                RequestMoveDragonDatagram requestMoveDragonDatagram = new RequestMoveDragonDatagram(DaoProvider.getMapDAO().getMap().getId(),dragonDTO.getId(),eMoveSide);
                try {
                    UtilConnection.sendObject(requestMoveDragonDatagram);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private EMoveSide dragonBFS() {
        int dragonDTOPositionX = dragonDTO.getPositionX();
        int dragonDTOPositionY = dragonDTO.getPositionY();
        bfsQueue = new ArrayDeque<>();

        if(UtilData.isAllowedToStandPositionForDragon(dragonDTOPositionX + 1, dragonDTOPositionY, dragonDTO)) {
            bfsQueue.add(new DragonMoveSideComposition(dragonDTOPositionX+1,dragonDTOPositionY,EMoveSide.RIGHT));
        }

        if(UtilData.isAllowedToStandPositionForDragon(dragonDTOPositionX - 1, dragonDTOPositionY, dragonDTO)) {
            bfsQueue.add(new DragonMoveSideComposition(dragonDTOPositionX-1,dragonDTOPositionY,EMoveSide.LEFT));
        }

        if(UtilData.isAllowedToStandPositionForDragon(dragonDTOPositionX, dragonDTOPositionY + 1, dragonDTO)) {
            bfsQueue.add(new DragonMoveSideComposition(dragonDTOPositionX,dragonDTOPositionY+1,EMoveSide.DOWN));
        }

        if(UtilData.isAllowedToStandPositionForDragon(dragonDTOPositionX, dragonDTOPositionY-1, dragonDTO)) {
            bfsQueue.add(new DragonMoveSideComposition(dragonDTOPositionX,dragonDTOPositionY-1,EMoveSide.UP));
        }

        while(!bfsQueue.isEmpty()) {
            DragonMoveSideComposition element = bfsQueue.poll();
            double distanceDragon = calculateDistance(element.getX(),loginUserDTO.getPositionX(), element.getY(), loginUserDTO.getPositionY());

            if(distanceDragon <= preferedMaxDistance) {
                bfsQueue = null;
                return element.getMoveSide();
            }

            if(UtilData.isAllowedToStandPositionForDragon(element.getX() + 1, element.getY(), dragonDTO)) {
                bfsQueue.add(new DragonMoveSideComposition(element.getX()+1,element.getY(),element.getMoveSide()));
            }

            if(UtilData.isAllowedToStandPositionForDragon(element.getX() - 1, element.getY(), dragonDTO)) {
                bfsQueue.add(new DragonMoveSideComposition(element.getX()-1,element.getY(),element.getMoveSide()));
            }

            if(UtilData.isAllowedToStandPositionForDragon(element.getX(), element.getY() + 1, dragonDTO)) {
                bfsQueue.add(new DragonMoveSideComposition(element.getX(),element.getY()+1,element.getMoveSide()));
            }

            if(UtilData.isAllowedToStandPositionForDragon(element.getX(), element.getY()-1, dragonDTO)) {
                bfsQueue.add(new DragonMoveSideComposition(element.getX(),element.getY()-1,element.getMoveSide()));
            }
        }

        return null;
    }

    private double calculateDistance(int fromX, int toX, int fromY, int toY) {
        double difX = fromX - toX;
        double difY = fromY - toY;

        return Math.sqrt(difX * difX + difY * difY);
    }

    public EDragonStatus getStatus() {
        return status;
    }

    public void setStatus(EDragonStatus status) {
        this.status = status;
    }
}
