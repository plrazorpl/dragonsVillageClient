package code.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public abstract class AThread implements Runnable {
    private ScheduledExecutorService scheduler;

    public AThread(long rate) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this, 10, rate, MILLISECONDS);
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
