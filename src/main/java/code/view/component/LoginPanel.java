package code.view.component;

import code.daos.basic.DaoProvider;
import code.listeners.LoginButtonListener;
import code.daos.WindowDAO;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel{

    private static final int DEFAULT_WIDTH_TEXT_FIELD = 200;
    private static final int DEFAULT_HEIGHT_TEXT_FIELD = 24;

    private JTextField loginTextField = new JTextField();
    private JTextField passwordTextField = new JTextField();

    private JLabel loginLabel = new JLabel("Login");
    private JLabel passwordLabel = new JLabel("Password");

    private JButton button = new JButton("Login");

    private JLabel errorMessage = new JLabel();

    public LoginPanel() {
        setLayout(null);
        addAllComponents();
        initLoginFields();
        initErrorLabel();
        initButtons();
    }

    private void initButtons() {
        Point location = getLocationByCentral(-(DEFAULT_WIDTH_TEXT_FIELD / 2), 30);
        button.setBounds(location.x, location.y, DEFAULT_WIDTH_TEXT_FIELD, DEFAULT_HEIGHT_TEXT_FIELD);
        button.addMouseListener(new LoginButtonListener());
    }

    public void initErrorLabel() {
        errorMessage.setForeground(Color.RED);
        int textWidth = getTextWidth(errorMessage.getText(),errorMessage.getFont());
        Point location = getLocationByCentral(-textWidth/2, -100);
        errorMessage.setBounds(location.x, location.y, textWidth, DEFAULT_HEIGHT_TEXT_FIELD*2);
    }

    private int getTextWidth(String text, Font font) {
        FontMetrics fontMetrics = this.getFontMetrics(font);
        return fontMetrics.stringWidth(text);
    }

    private void addAllComponents() {
        add(loginTextField);
        add(passwordTextField);
        add(loginLabel);
        add(passwordLabel);
        add(button);
        add(errorMessage);
    }

    private void initLoginFields() {
        Point loginPoint = getLoginLocation();
        Point passwordPoint = getPasswordLocation();
        loginTextField.setBounds(loginPoint.x, loginPoint.y, DEFAULT_WIDTH_TEXT_FIELD, DEFAULT_HEIGHT_TEXT_FIELD);
        passwordTextField.setBounds(passwordPoint.x, passwordPoint.y, DEFAULT_WIDTH_TEXT_FIELD, DEFAULT_HEIGHT_TEXT_FIELD);
        loginLabel.setBounds(loginPoint.x - 100, loginPoint.y, DEFAULT_WIDTH_TEXT_FIELD, DEFAULT_HEIGHT_TEXT_FIELD);
        passwordLabel.setBounds(passwordPoint.x - 100, passwordPoint.y, DEFAULT_WIDTH_TEXT_FIELD, DEFAULT_HEIGHT_TEXT_FIELD);

        //TODO: MOCK
        loginTextField.setText("test01");
        getPasswordTextField().setText("123");
    }

    private Point getLoginLocation() {
        Point p = getLocationByCentral(-(DEFAULT_WIDTH_TEXT_FIELD / 2), -20 - 24);
        return p;
    }

    private Point getLocationByCentral(int xSharp, int ySharp) {
        WindowDAO windowDAO = DaoProvider.getWindowDAO();
        int width = windowDAO.getLoginWindowWidth() / 2 ;
        int height = windowDAO.getLoginWindowHeight() / 2;

        return new Point(width+xSharp, height+ySharp);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(100,100,100,100);
    }

    public JTextField getLoginTextField() {
        return loginTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public Point getPasswordLocation() {
        return getLocationByCentral(-(DEFAULT_WIDTH_TEXT_FIELD / 2), 20 - 24);
    }

    public JLabel getLoginLabel() {
        return loginLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public JButton getButton() {
        return button;
    }

    public JLabel getErrorMessage() {
        return errorMessage;
    }
}
