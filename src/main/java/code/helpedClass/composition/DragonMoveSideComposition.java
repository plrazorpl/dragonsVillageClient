package code.helpedClass.composition;

import dragonsVillage.Enums.EMoveSide;

public class DragonMoveSideComposition {
    private int x;
    private int y;
    private EMoveSide moveSide;

    public DragonMoveSideComposition(int x, int y, EMoveSide moveSide) {
        this.x = x;
        this.y = y;
        this.moveSide = moveSide;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public EMoveSide getMoveSide() {
        return moveSide;
    }

    public void setMoveSide(EMoveSide moveSide) {
        this.moveSide = moveSide;
    }
}
