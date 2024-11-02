import gameElement.Bird;
import gameElement.Pipe;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
   private final MyFrame frame;

   //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image lowerPipeImage;

    //sound
    Clip clip;
    String clickSound;
    String scoreSound;
    String gameOverSound;

    //game logic
    Bird bird;
    int velocityX = -4; //move pipe to left speed(simulate bird moving right)
    int velocityY = 0;  // move bird up/down speed
    int gravity = 0;
    double score = 0;
    int highscore = 0;

    //array list of pipe
    ArrayList<Pipe> pipes;

    //game timer
    Timer gameLoop;
    Timer placePipeTimer;

    //game over
    boolean gameOver = false;

    public FlappyBird(MyFrame frame) {
        this.frame = frame;

        setPreferredSize(new Dimension(frame.getBoardWidth(), frame.getBoardHeight()));
//        setBackground(Color.BLUE);
        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("resource/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("resource/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("resource/toppipe.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("resource/bottompipe.png")).getImage();

        //load Sound
        clickSound = "resource/sfx_wing.wav";
        scoreSound = "resource/sfx_point.wav";
        gameOverSound = "resource/gameOver.wav";

        //bird
        bird = new Bird(frame.getBoardWidth(), frame.getBoardHeight(), birdImg);

        //pipes
        pipes = new ArrayList<>();
        placePipeTimer = new Timer(1500, e -> {
            placePipe();
        });
        placePipeTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

    }

    public void placePipe(){
        int randomPipeY = (int) (0 - Pipe.HEIGHT/4 - Math.random()*(Pipe.HEIGHT/2) );
        int openSpace = frame.getBoardHeight()/4;

        Pipe topPipe = new Pipe(topPipeImg, frame.getBoardWidth());
        topPipe.setY(randomPipeY);
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(lowerPipeImage, frame.getBoardWidth());
        bottomPipe.setY(topPipe.getY()+Pipe.HEIGHT+openSpace);
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //set background
        g.drawImage(backgroundImg, 0, 0, frame.getBoardWidth(), frame.getBoardHeight(), null);

        //set bird
        g.drawImage(bird.getImg(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);

        //set pipe
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));

        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    public void move() {
        //bird move
        velocityY += gravity;
        bird.setY(velocityY);

        //pipe move
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setX(velocityX);

            if (!pipe.isPass() && (bird.getX() > pipe.getX() + pipe.getWidth())){
                setSound(scoreSound);
                pipe.setPass(true);
                score += 0.5;  //pass two pipe to get 1
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        //fall from screen
        if (bird.getY() > frame.getBoardHeight()) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.getX() < b.getX() + b.getWidth() &&     //a's top left corner doesn't reach touch b's top right corner
                a.getX() + a.getWidth() > b.getX() &&    //a's top right corner doesn't reach touch b's top left corner
                a.getY() < b.getY() + b.getHeight() &&   //a's top left corner doesn't reach touch b's bottom left corner
                a.getY() + a.getHeight() > b.getY();     //a's bottom left corner doesn't reach touch b's top right corner
    }

    public void setSound(String soundFileName){
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);

            clip.setFramePosition(0);
            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver) {
            setSound(gameOverSound);
            placePipeTimer.stop();
            gameLoop.stop();
            new Retry(frame, this).setVisible(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gravity = 1;
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            setSound(clickSound);
            velocityY = -9;
//            if (gameOver) {
//                //reset the game
//                bird.setY(frame.getBoardHeight()/2);
//                bird.setX(frame.getBoardWidth()/8);
//                pipes.clear();
//                score = 0;
//                velocityY = 0;
////                gravity = 0;
//                gameOver = false;
//                placePipeTimer.start();
//                gameLoop.start();
//            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {    }

    @Override
    public void keyReleased(KeyEvent e) {    }
}
