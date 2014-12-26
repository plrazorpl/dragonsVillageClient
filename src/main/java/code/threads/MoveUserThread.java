package code.threads;

import code.Utils.UtilData;
import code.daos.basic.DaoProvider;
import dragonsVillage.dtos.LoginUserDTO;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MoveUserThread implements Runnable {

    private static final double STEPS_COUNT = 100;

    private LoginUserDTO userDTO;
    private int toX;
    private int toY;
    private final double incrementX;
    private final double incrementY;
    private double currentX;
    private double currentY;
    private int actualStep = 0;
    private ScheduledExecutorService scheduler;

    public MoveUserThread(LoginUserDTO userDTO, int toX, int toY) {
        this.userDTO = userDTO;
        this.toX = toX;
        this.toY = toY;
        incrementX = ((double)(userDTO.getPositionX()-toX))*DaoProvider.getWindowDAO().getGamePanel().WIDTH/STEPS_COUNT;
        incrementY = ((double)(userDTO.getPositionY()-toY))*DaoProvider.getWindowDAO().getGamePanel().HEIGHT/STEPS_COUNT;
        currentX = 0;
        currentY = 0;

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this, 0, (long) (900/STEPS_COUNT), MILLISECONDS);
    }

    @Override
    public void run() {



        currentX += incrementX;
        currentY += incrementY;
        userDTO.setActualSharpX((int)currentX);
        userDTO.setActualSharpY((int)currentY);

        actualStep++;
        if(actualStep == STEPS_COUNT) {

            scheduler.shutdown();
            userDTO.setActualSharpX(0);
            userDTO.setActualSharpY(0);

            ArrayList<LoginUserDTO> usersMapPoint = UtilData.getUsersMapPoint(userDTO.getPositionX(), userDTO.getPositionY());

            userDTO.setPositionX(toX);
            userDTO.setPositionY(toY);

            usersMapPoint.remove(userDTO);

            usersMapPoint = UtilData.getUsersMapPoint(userDTO.getPositionX(), userDTO.getPositionY());

            usersMapPoint.add(userDTO);

            if (!DaoProvider.getEngineDAO().isActionAvaliable()) {
                DaoProvider.getEngineDAO().setActionAvaliable(true);
            }
        }

    }
}
