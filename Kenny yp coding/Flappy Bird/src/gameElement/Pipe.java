package gameElement;

import java.awt.*;

public class Pipe {
    private int x;
    private int y;
    private Image img;
    private int width;
    private int height;
    private boolean isPass;
    public static int HEIGHT = 512;

    public Pipe(Image img, int boardWidth) {
        this.img = img;
        this.x = boardWidth;
        y = 0;
        width = 64;
        height = HEIGHT;
        isPass = false;

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

    public boolean isPass() {
        return isPass;
    }

    public void setX(int velocity){
        x += velocity;
    }

    public void setY(int randomHeight){
        y = randomHeight;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }
}
