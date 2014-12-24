package code.daos;


import code.view.component.GamePanel;
import code.view.component.GameWindow;
import code.view.component.LoginPanel;
import code.view.component.LoginWindow;

public class WindowDAO {
    private static final int DEFAULT_LOGIN_WINDOW_WIDTH = 1024;
    private static final int DEFAULT_LOGIN_WINDOW_HEIGHT = 768;

    private static final int DEFAULT_GAME_WINDOW_WIDTH = 1024;
    private static final int DEFAULT_GAME_WINDOW_HEIGHT = 768;

    private int loginWindowWidth = DEFAULT_LOGIN_WINDOW_WIDTH;
    private int loginWindowHeight = DEFAULT_LOGIN_WINDOW_HEIGHT;

    private int gameWindowWidth = DEFAULT_GAME_WINDOW_WIDTH;
    private int gameWindowHeight = DEFAULT_GAME_WINDOW_HEIGHT;

    private LoginWindow loginWindow;
    private LoginPanel loginPanel;

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    public int getLoginWindowWidth() {
        return loginWindowWidth;
    }

    public void setLoginWindowWidth(int loginWindowWidth) {
        this.loginWindowWidth = loginWindowWidth;
    }

    public int getLoginWindowHeight() {
        return loginWindowHeight;
    }

    public void setLoginWindowHeight(int loginWindowHeight) {
        this.loginWindowHeight = loginWindowHeight;
    }

    public LoginWindow getLoginWindow() {
        return loginWindow;
    }

    public void setLoginWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public int getGameWindowWidth() {
        return gameWindowWidth;
    }

    public void setGameWindowWidth(int gameWindowWidth) {
        this.gameWindowWidth = gameWindowWidth;
    }

    public int getGameWindowHeight() {
        return gameWindowHeight;
    }

    public void setGameWindowHeight(int gameWindowHeight) {
        this.gameWindowHeight = gameWindowHeight;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}
