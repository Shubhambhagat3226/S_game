package gameElement;

import javax.naming.BinaryRefAddr;
import java.awt.*;

public class Bird {
    private int x;
    private int y;
    private Image img;
    private int width;
    private int height;

    public Bird(int boardWidth, int boardHeight, Image img){
        this.x = boardWidth/8;
        this.y = boardHeight/2;
        this.img = img;
        width = 34;
        height = 24;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setY(int velocity){
        y += velocity;
        y = Math.max(y, 0);
    }

    public void resetY(int y) {
        this.y = y;
    }

    public void resetX(int x) {
        this.x = x;
    }
}
