package code;

import code.daos.basic.DaoProvider;
import code.threads.RepainterThreadV2;
import code.view.component.GameWindow;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import code.view.component.LoginWindow;

public class Start {
    private BlockingQueue queue = new ArrayBlockingQueue(1);
    public static void main(String[] arg) throws IOException, InterruptedException {

        System.out.println("test");

        LoginWindow loginWindow = new LoginWindow();
        GameWindow gameWindow = new GameWindow();

        RepainterThreadV2 repainterThreadV2 = new RepainterThreadV2();
        DaoProvider.getEngineDAO().setRepainterThreadV2(repainterThreadV2);
    }
}
