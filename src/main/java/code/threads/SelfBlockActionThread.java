package code.threads;

import code.daos.basic.DaoProvider;

public class SelfBlockActionThread extends Thread {

    private long timeSleep;

    public SelfBlockActionThread(long timeSleep) {
        this.timeSleep = timeSleep;
    }

    @Override
    public void run() {
        super.run();
        DaoProvider.getEngineDAO().setSelfNotBlockCommand(false);
        try {
            Thread.currentThread().sleep(timeSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DaoProvider.getEngineDAO().setSelfNotBlockCommand(true);
    }
}
