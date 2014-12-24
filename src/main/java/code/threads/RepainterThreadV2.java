package code.threads;

import code.daos.basic.DaoProvider;
import code.view.component.GamePanel;

public class RepainterThreadV2 implements Runnable {

    private static GamePanel gamePanel = DaoProvider.getWindowDAO().getGamePanel();
    private int frame = 0;
    private long currentTime = 0;
    private Long previousValue;


    @Override
    public void run() {
        //TODO: create framerate
        gamePanel.repaint();
        frame++;
        long stop = System.currentTimeMillis();
        if(previousValue != null){
            currentTime+=stop-previousValue;
        }
        previousValue = stop;
        if(frame >= DaoProvider.getEngineDAO().FRAME_RATE){
//            System.out.println(currentTime);
            frame = 0;
            currentTime -= 1000;
        }
    }
}
