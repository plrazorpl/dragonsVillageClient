package code.view.component;

import code.daos.WindowDAO;
import code.daos.basic.DaoProvider;
import code.listeners.GameButtonsListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GameWindow extends JFrame implements ComponentListener{

    private WindowDAO windowDAO = DaoProvider.getWindowDAO();
    private GamePanel gamePanel = new GamePanel();

    public GameWindow(){
        setPreferredSize(new Dimension(windowDAO.getGameWindowWidth(), windowDAO.getGameWindowHeight()));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(gamePanel);
        addKeyListener(new GameButtonsListener());
        addComponentListener(this);
        pack();

        windowDAO.setGameWindow(this);
        windowDAO.setGamePanel(gamePanel);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        gamePanel.setSizeComponent(getWidth(),getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
