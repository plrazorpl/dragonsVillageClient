package code.daos;


import code.threads.RepainterThreadV2;

import java.util.concurrent.ScheduledFuture;

public class EngineDAO {

    public final int FRAME_RATE = 50;

    private boolean notEnd = true;
    private int actualFrameRate;
    private RepainterThreadV2 repainterThreadV2;
    private ScheduledFuture<?> graphicThread;
    private boolean actionAvaliable = true;
    private boolean selfNotBlockCommand = true;

    public boolean isNotEnd() {
        return notEnd;
    }

    public void setNotEnd(boolean notEnd) {
        this.notEnd = notEnd;
    }

    public int getActualFrameRate() {
        return actualFrameRate;
    }

    public void setActualFrameRate(int actualFrameRate) {
        this.actualFrameRate = actualFrameRate;
    }

    public RepainterThreadV2 getRepainterThreadV2() {
        return repainterThreadV2;
    }

    public void setRepainterThreadV2(RepainterThreadV2 repainterThreadV2) {
        this.repainterThreadV2 = repainterThreadV2;
    }

    public ScheduledFuture<?> getGraphicThread() {
        return graphicThread;
    }

    public void setGraphicThread(ScheduledFuture<?> graphicThread) {
        this.graphicThread = graphicThread;
    }

    public boolean isActionAvaliable() {
        return actionAvaliable;
    }

    public void setActionAvaliable(boolean actionAvaliable) {
        this.actionAvaliable = actionAvaliable;
    }

    public boolean isSelfNotBlockCommand() {
        return selfNotBlockCommand;
    }

    public void setSelfNotBlockCommand(boolean selfNotBlockCommand) {
        this.selfNotBlockCommand = selfNotBlockCommand;
    }
}
