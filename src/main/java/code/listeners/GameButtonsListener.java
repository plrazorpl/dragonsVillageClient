package code.listeners;

import code.Utils.UtilConnection;
import code.daos.basic.DaoProvider;
import dragonsVillage.Datagrams.requestDatagram.RequestMoveDatagram;
import dragonsVillage.Enums.EMoveSide;
import dragonsVillage.dtos.LoginUserDTO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static dragonsVillage.Enums.EMoveSide.RIGHT;

public class GameButtonsListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        LoginUserDTO loginUserDTO = DaoProvider.getLoggedUserDAO().getLoginUserDTO();
        try {
            if(e.getKeyChar() == 'D' || e.getKeyChar() == 'd'){
                    UtilConnection.sendObject(new RequestMoveDatagram(loginUserDTO, EMoveSide.RIGHT));
            } else if(e.getKeyChar() == 'A' || e.getKeyChar() == 'a'){
                UtilConnection.sendObject(new RequestMoveDatagram(loginUserDTO, EMoveSide.LEFT));
            } else if(e.getKeyChar() == 'S' || e.getKeyChar() == 's'){
                UtilConnection.sendObject(new RequestMoveDatagram(loginUserDTO, EMoveSide.DOWN));
            } else if(e.getKeyChar() == 'W' || e.getKeyChar() == 'w'){
                UtilConnection.sendObject(new RequestMoveDatagram(loginUserDTO, EMoveSide.UP));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
