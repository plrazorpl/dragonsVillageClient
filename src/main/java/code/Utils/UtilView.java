package code.Utils;

import code.daos.basic.DaoProvider;
import code.threads.RepainterThreadV2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class UtilView {

    public static void setErrorMessage(String message){
        DaoProvider.getWindowDAO().getLoginPanel().getErrorMessage().setText(message);
        DaoProvider.getWindowDAO().getLoginPanel().initErrorLabel();
    }

    public static void startGameEngines() {
        RepainterThreadV2 repainterThreadV2 = DaoProvider.getEngineDAO().getRepainterThreadV2();
        int middleTime = 1000 / DaoProvider.getEngineDAO().FRAME_RATE;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        DaoProvider.getEngineDAO().setGraphicThread(scheduler.scheduleAtFixedRate(repainterThreadV2, 0, middleTime, MILLISECONDS));
    }
}
