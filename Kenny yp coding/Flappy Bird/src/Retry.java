
import javax.swing.*;
import java.awt.*;

public class Retry extends JDialog {

    private MyFrame frame;
    private FlappyBird panel;

    public Retry(MyFrame frame, FlappyBird panel){
        this.frame = frame;
        this.panel = panel;
        panel.highscore = Math.max(panel.highscore, (int) panel.score);
        setTitle("GAME OVER!!");
        setSize(250,130);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(frame);

        addComponent();

    }

    public void addComponent(){
        JLabel label = new JLabel("SCORE: " + (int) panel.score);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JLabel label1 = new JLabel("HIGHSCORE: " + panel.highscore);
        label1.setFont(new Font("Arial", Font.PLAIN, 20));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        add(label1, BorderLayout.CENTER);

        JButton button = new JButton("Try again");
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
                //reset the game
            panel.score = 0;
            panel.velocityY = 0;
            panel.bird.resetX(frame.getBoardWidth()/8);
            panel.bird.resetY(frame.getBoardHeight()/2);
            panel.pipes.clear();
//            panel.gravity = 0;
            panel.gameOver = false;
            panel.placePipeTimer.start();
            panel.gameLoop.start();
            dispose();
        });
        add(button, BorderLayout.SOUTH);
    }
}