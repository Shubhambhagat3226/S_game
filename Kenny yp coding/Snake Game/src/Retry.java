import Constant.CommonConstant;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Retry extends JDialog {

    private MyFrame source;
    private MyPanel panel;

    public Retry(MyFrame frame, MyPanel panel){
        this.source = frame;
        this.panel = panel;
        setTitle("Message");
        setSize(300,170);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(source);

        addComponent();
    }

    public void addComponent(){
        JLabel label = new JLabel("GAME OVER!!");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JButton button = new JButton("Try again");
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            panel.gameLoop.start();
            panel.gameOver = false;
            panel.snakeHead.setXY();
            panel.snakeBody.clear();
            CommonConstant.VELOCITY_X = 0;
            CommonConstant.VELOCITY_Y = 0;
            dispose();
        });
        add(button, BorderLayout.SOUTH);
    }
}
