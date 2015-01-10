package code.view.component;

import code.Utils.UtilData;
import code.daos.LoggedUserDAO;
import code.daos.MapDAO;
import code.daos.WindowDAO;
import code.daos.basic.DaoProvider;
import dragonsVillage.Enums.*;
import dragonsVillage.dtos.DragonDTO;
import dragonsVillage.dtos.LoginUserDTO;
import dragonsVillage.dtos.MapDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    private int width;
    private int height;

    private WindowDAO windowDAO = DaoProvider.getWindowDAO();
    private MapDAO mapDAO = DaoProvider.getMapDAO();
    private LoggedUserDAO userDAO = DaoProvider.getLoggedUserDAO();
    private LoginUserDTO user;
    private MapDTO map;


    private Map<EMapPointType,Image> mapImages = new HashMap<EMapPointType,Image>();

    private long timeStamp = 0;
    private int frameCount = 0;

    public GamePanel(){
        width = windowDAO.getGameWindowWidth();
        height = windowDAO.getGameWindowHeight();
        setBackground(Color.BLACK);
    }

    public void setSizeComponent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(timeStamp == 0) {
            timeStamp = System.currentTimeMillis();
        }
        if((user = userDAO.getLoginUserDTO()) !=null){
            Point playerPosition = getCentralPoint(user);
            if((map = mapDAO.getMap())!=null){
                //TODO: Mocked draw map;
                drawMap(g,playerPosition);

            }
            try {
                g.drawImage(getPlayerSkin(user),playerPosition.x,playerPosition.y,null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            drawElements(g,playerPosition);
//            g.setColor(Color.GREEN);
//            g.fillOval(playerPosition.x,playerPosition.y, WIDTH, HEIGHT);
        }

        frameCount++;
        if(System.currentTimeMillis() - timeStamp > 1000) {
            System.out.println("FPS: " + frameCount);
            frameCount = 0;
            timeStamp = 0;

        }
    }

    private void drawElements(Graphics g, Point playerPosition) {
        int x = user.getPositionX();
        int y = user.getPositionY();


        int minX = 0;
        int minY = 0;

        g.setColor(Color.BLUE);

        for(;playerPosition.x-minX*WIDTH>0;minX++){}
        for(;playerPosition.y-minY*HEIGHT>0;minY++){}

        for(int actualX = playerPosition.x-minX*WIDTH;actualX<width+WIDTH;actualX+=WIDTH){
            for(int actualY = playerPosition.y-minY*HEIGHT;actualY<height+HEIGHT;actualY+=HEIGHT) {
                if(UtilData.isCorrectPosition(x-minX,y-minY)) {

                    try {
                        if(map.getMapPointTypes()[x-minX][y-minY] != EMapPointType.GROUND) {
                            g.drawImage(getMapImage(x - minX, y - minY), actualX + user.getActualSharpX(), actualY + user.getActualSharpY(), null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                y++;
            }
            y=user.getPositionY();
            x++;
        }
    }

    private void drawMap(Graphics g, Point playerPosition) {
        int x = user.getPositionX();
        int y = user.getPositionY();


        int minX = 0;
        int minY = 0;

        g.setColor(Color.BLUE);

        for(;playerPosition.x-minX*WIDTH>0;minX++){}
        for(;playerPosition.y-minY*HEIGHT>0;minY++){}

        for(int actualX = playerPosition.x-minX*WIDTH;actualX<width+WIDTH;actualX+=WIDTH){
            for(int actualY = playerPosition.y-minY*HEIGHT;actualY<height+HEIGHT;actualY+=HEIGHT) {
                if(UtilData.isCorrectPosition(x-minX,y-minY)) {
                    try {
                        g.drawImage(getMapImage(EMapPointType.GROUND), actualX+user.getActualSharpX(), actualY+user.getActualSharpY(), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                y++;
            }
            y=user.getPositionY();
            x++;
        }

        x = user.getPositionX();
        y = user.getPositionY();


        g.setColor(Color.BLUE);

        for(int actualX = playerPosition.x-minX*WIDTH;actualX<width+WIDTH;actualX+=WIDTH) {
            for (int actualY = playerPosition.y - minY * HEIGHT; actualY < height + HEIGHT; actualY += HEIGHT) {
                if (UtilData.isCorrectPosition(x - minX, y - minY) && !UtilData.getDragonMap(x - minX,y - minY).isEmpty()) {
                    try {
                        for (DragonDTO dragonDTO : map.getDragonsMap()[x - minX][y - minY]) {
                            g.drawImage(getDragonSkin(dragonDTO), actualX + user.getActualSharpX() - dragonDTO.getActualSharpX(), actualY + user.getActualSharpY() - dragonDTO.getActualSharpY(),null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                y++;
            }
            y=user.getPositionY();
            x++;
        }


        x = user.getPositionX();
        y = user.getPositionY();


        g.setColor(Color.BLUE);


        for(int actualX = playerPosition.x-minX*WIDTH;actualX<width+WIDTH;actualX+=WIDTH){
            for(int actualY = playerPosition.y-minY*HEIGHT;actualY<height+HEIGHT;actualY+=HEIGHT) {
                if(UtilData.isCorrectPosition(x - minX, y - minY) && !map.getUsersMap()[x-minX][y-minY].isEmpty()) {
                    try {
                        for (LoginUserDTO otherPlayer : map.getUsersMap()[x - minX][y - minY]) {
                            if(otherPlayer.getId() != user.getId()) {
                                g.drawImage(getPlayerSkin(otherPlayer), actualX + user.getActualSharpX() - otherPlayer.getActualSharpX(), actualY + user.getActualSharpY() - otherPlayer.getActualSharpY(), null);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                y++;
            }
            y=user.getPositionY();
            x++;
        }
    }

    private Image getDragonSkin(DragonDTO dragonDTO) throws IOException {
        if(dragonDTO.getDragonSkin() == null){
            File skinFile = null;
            if(dragonDTO.getMoveSide() == null || dragonDTO.getMoveSide() == EMoveSide.LEFT) {
                skinFile = new File(dragonDTO.getDragonType().getLeft());
            } else if(dragonDTO.getMoveSide() == EMoveSide.RIGHT) {
                skinFile = new File(dragonDTO.getDragonType().getRight());
            }
            if(!skinFile.exists()){
                ClassLoader classLoader = getClass().getClassLoader();
                if(dragonDTO.getMoveSide() == null || dragonDTO.getMoveSide() == EMoveSide.LEFT) {
                    skinFile = new File(classLoader.getResource(dragonDTO.getDragonType().getLeft()).getFile());
                } else if(dragonDTO.getMoveSide() == EMoveSide.RIGHT) {
                    skinFile = new File(classLoader.getResource(dragonDTO.getDragonType().getRight()).getFile());
                }
            }
            BufferedImage readed = ImageIO.read(skinFile);
            dragonDTO.setDragonSkin(readed.getScaledInstance(WIDTH,HEIGHT,Image.SCALE_SMOOTH));
        }
        return dragonDTO.getDragonSkin();
    }

    private Image getMapImage(int x, int y) throws IOException {
        EMapPointType mapPointType = map.getMapPointTypes()[x][y];
        if(mapPointType == null) {
            return getMapImage(EMapPointType.GROUND);
        }
        return getMapImage(mapPointType);
    }

    private Image getMapImage(EMapPointType mapPointType) throws IOException {
        Image result;
        if(mapImages.get(mapPointType) == null){
            File mapFile = new File(map.getMapType().getPath());
            if(!mapFile.exists()){
                ClassLoader classLoader = getClass().getClassLoader();
                mapFile = new File(classLoader.getResource(EMapType.DEFAULT.getPath() + mapPointType.getPath()).getFile());
            }
            BufferedImage readed = ImageIO.read(mapFile);
            result = readed.getScaledInstance(WIDTH,HEIGHT,Image.SCALE_SMOOTH);
            mapImages.put(mapPointType,result);
            return result;
        }
        return mapImages.get(mapPointType);
    }



    private Point getCentralPoint(LoginUserDTO user) {
        int middleWidth = width/2 - WIDTH/2;
        int middleHeight = height/2 - HEIGHT/2;
        //TODO: expand method
        return new Point(middleWidth,middleHeight);
    }

    public Image getPlayerSkin(LoginUserDTO otherPlayer) throws IOException {
        if(otherPlayer.getPlayerSkin() == null){
            File skinFile = null;
            if(otherPlayer.getMoveSide()==null || otherPlayer.getMoveSide() == EMoveSide.DOWN) {
                skinFile = new File(user.getSkin().getDown());
            } else if(otherPlayer.getMoveSide() == EMoveSide.UP) {
                skinFile = new File(user.getSkin().getUp());
            } else if(otherPlayer.getMoveSide() == EMoveSide.LEFT) {
                skinFile = new File(user.getSkin().getLeft());
            } else if(otherPlayer.getMoveSide() == EMoveSide.RIGHT) {
                skinFile = new File(user.getSkin().getRight());
            }
            if(!skinFile.exists()){
                ClassLoader classLoader = getClass().getClassLoader();
                if(otherPlayer.getMoveSide()==null || otherPlayer.getMoveSide() == EMoveSide.DOWN) {
                    skinFile = new File(classLoader.getResource(otherPlayer.getSkin().getDown()).getFile());
                } else if(otherPlayer.getMoveSide() == EMoveSide.UP) {
                    skinFile = new File(classLoader.getResource(otherPlayer.getSkin().getUp()).getFile());
                } else if(otherPlayer.getMoveSide() == EMoveSide.LEFT) {
                    skinFile = new File(classLoader.getResource(otherPlayer.getSkin().getLeft()).getFile());
                } else if(otherPlayer.getMoveSide() == EMoveSide.RIGHT) {
                    skinFile = new File(classLoader.getResource(otherPlayer.getSkin().getRight()).getFile());
                }
            }
            BufferedImage readed = ImageIO.read(skinFile);
            otherPlayer.setPlayerSkin(readed.getScaledInstance(WIDTH,HEIGHT,Image.SCALE_SMOOTH));
        }
        return otherPlayer.getPlayerSkin();
    }

    public MapDTO getMap() {
        return map;
    }

    public void setMap(MapDTO map) {
        this.map = map;
    }

    public Map<EMapPointType, Image> getMapImages() {
        return mapImages;
    }

    public void setMapImages(Map<EMapPointType, Image> mapImages) {
        this.mapImages = mapImages;
    }
}
