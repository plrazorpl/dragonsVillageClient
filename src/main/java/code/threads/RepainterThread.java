package code.threads;

import code.daos.EngineDAO;
import code.daos.basic.DaoProvider;
import code.view.component.GamePanel;

@Deprecated
public class RepainterThread extends Thread {

    private EngineDAO engineDAO;
    private GamePanel gamePanel;

    @Override
    public void run() {
        super.run();

        engineDAO = DaoProvider.getEngineDAO();
        gamePanel = DaoProvider.getWindowDAO().getGamePanel();

        long actualMilis = 0;
        long timeSharp;
        long middleMilisec = 1000 / engineDAO.FRAME_RATE;
        int frameCount = 0;
        long currentTimeMillis = System.currentTimeMillis();
        long tmpCurrentTimeMillis;
        long differenceMillis;
        while (engineDAO.isNotEnd()){
            frameCount++;
            gamePanel.repaint();
            tmpCurrentTimeMillis = System.currentTimeMillis();
            differenceMillis = tmpCurrentTimeMillis - currentTimeMillis;
            actualMilis+=differenceMillis;
            timeSharp = middleMilisec - differenceMillis;

            if(actualMilis>1000){
                //TODO: remove println
                System.out.println(frameCount);
                actualMilis-=1000;
                engineDAO.setActualFrameRate(frameCount);
                frameCount = 0;
            }

            try {
                Thread.sleep(middleMilisec-differenceMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
