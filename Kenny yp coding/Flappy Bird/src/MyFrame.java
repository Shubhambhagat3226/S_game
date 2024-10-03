import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private int boardWidth = 360;
    private int boardHeight = 640;


    public MyFrame(){
        setTitle("Flappy Bird");
        setSize(boardWidth, boardHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird(this);
        add(flappyBird);
        flappyBird.requestFocus();
        pack();
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}
