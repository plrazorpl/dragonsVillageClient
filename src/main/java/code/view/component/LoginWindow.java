package code.view.component;

import code.daos.basic.DaoProvider;
import code.daos.WindowDAO;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame{

    private LoginPanel loginPanel = new LoginPanel();

    public LoginWindow(){
        WindowDAO windowDAO = DaoProvider.getWindowDAO();
        windowDAO.setLoginWindow(this);
        windowDAO.setLoginPanel(loginPanel);
        setPreferredSize(new Dimension(windowDAO.getLoginWindowWidth(), windowDAO.getLoginWindowHeight()));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(loginPanel);
        pack();
        setVisible(true);
    }
}
