package code.threads;

import code.Utils.UtilData;
import code.daos.basic.DaoProvider;
import code.enums.EDragonStatus;
import dragonsVillage.Datagrams.requestDatagram.RequestMoveDragonDatagram;
import dragonsVillage.Datagrams.responseDatagram.ResponseMoveDragonDatagram;
import dragonsVillage.Enums.EMoveSide;
import dragonsVillage.dtos.DragonDTO;

import java.util.ArrayList;

public class DragonMoveAnimationTherad extends AThread {
    private DragonDTO dragonDTO;
    private double xSharp;
    private double ySharp;
    private int frameWidth;

    private double actualSharpX;
    private double actualSharpY;
    private ResponseMoveDragonDatagram dragonDatagram;

    public DragonMoveAnimationTherad(ResponseMoveDragonDatagram dragonDatagram) {
        super(900/DaoProvider.getEngineDAO().FRAME_RATE);
        this.dragonDatagram = dragonDatagram;
        this.dragonDTO = DaoProvider.getMapDAO().getMap().getDragonsDTO().get(dragonDatagram.getDragonID());
        this.frameWidth = DaoProvider.getWindowDAO().getGamePanel().WIDTH;
        xSharp = (double)(dragonDTO.getPositionX()-dragonDatagram.getPositionX()) / DaoProvider.getEngineDAO().FRAME_RATE;
        ySharp = (double)(dragonDTO.getPositionY()-dragonDatagram.getPositionY()) / DaoProvider.getEngineDAO().FRAME_RATE;
        if(DaoProvider.getEngineDAO().getDragonThreadMap().get(dragonDatagram.getDragonID())!=null) {
            DaoProvider.getEngineDAO().getDragonThreadMap().get(dragonDatagram.getDragonID()).setStatus(EDragonStatus.MOVE);
        }

        dragonDTO.setMoveSide(dragonDatagram.getMoveSide());
        if(dragonDatagram.getMoveSide() == EMoveSide.LEFT || dragonDatagram.getMoveSide() == EMoveSide.RIGHT) {
            dragonDTO.setDragonSkin(null);
        }
    }

    @Override
    public void run() {
        actualSharpX+=xSharp;
        actualSharpY+=ySharp;
        if((xSharp != 0 && Math.abs(actualSharpX)>=1) || (ySharp != 0 && Math.abs(actualSharpY)>=1)) {
            changeDragonPosition(dragonDTO, dragonDatagram);
            dragonDTO.setPositionX(dragonDatagram.getPositionX());
            dragonDTO.setPositionY(dragonDatagram.getPositionY());
            dragonDTO.setActualSharpX(0);
            dragonDTO.setActualSharpY(0);
            if(DaoProvider.getEngineDAO().getDragonThreadMap().get(dragonDatagram.getDragonID())!=null) {
                DaoProvider.getEngineDAO().getDragonThreadMap().get(dragonDatagram.getDragonID()).setStatus(EDragonStatus.STAY);
            }
            getScheduler().shutdown();
        } else {
            dragonDTO.setActualSharpX((int) (actualSharpX*frameWidth));
            dragonDTO.setActualSharpY((int) (actualSharpY*frameWidth));
        }
    }

    private void changeDragonPosition(DragonDTO dragonDTO, ResponseMoveDragonDatagram dragonDatagram) {
        ArrayList<DragonDTO>[][] dragonsMap = DaoProvider.getMapDAO().getMap().getDragonsMap();
        for(int i=0;i<dragonDTO.getDragonType().getSize();i++) {
            for(int j=0;j<dragonDTO.getDragonType().getSize();j++) {
                UtilData.getDragonMap(dragonDTO.getPositionX() + i, dragonDTO.getPositionY() + j).remove(dragonDTO);
            }
        }

        for(int i=0;i<dragonDTO.getDragonType().getSize();i++) {
            for(int j=0;j<dragonDTO.getDragonType().getSize();j++) {
                UtilData.getDragonMap(dragonDatagram.getPositionX() + i, dragonDatagram.getPositionY() + j).add(dragonDTO);
            }
        }
    }
}
