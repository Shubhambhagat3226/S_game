import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RockPaperScissorGUI extends JFrame implements ActionListener{
    //player button
    JButton rockButton, paperButton, scissorButton;

    //display choice
    JLabel computerChoice;

    //display score
    JLabel computerScoreLabel ,playerScoreLabel;

    //backend obj
    RockPaperScissor rockPaperScissor;

    public RockPaperScissorGUI(){
        //frame and title of frame
        super("Rock Paper Scissor Game");

        //set size of gui
        setSize(450, 574);

        //null the layout
        setLayout(null);

        //close when click on cross
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //load gui to center
        setLocationRelativeTo(null);

        //initialize backend object
        rockPaperScissor = new RockPaperScissor();

        //add gui component
        addGUIComponent();
    }

    void addGUIComponent(){
        //create computer score label
        computerScoreLabel = new JLabel("Computer : 0");

        //set x,y coordination and height /width
        computerScoreLabel.setBounds(0,43,450, 30);

        //set font
        computerScoreLabel.setFont(new Font("Dialog", Font.BOLD, 26));

        //resizeable false
        setResizable(false);

        //label in center
        computerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //add to gui
        add(computerScoreLabel);

        //create computer choice
        computerChoice = new JLabel("?");
        computerChoice.setBounds(175,118,98,81);
        computerChoice.setFont(new Font("Dialog", Font.PLAIN, 18));
        computerChoice.setHorizontalAlignment(SwingConstants.CENTER);

        //create boarder around the choice
        computerChoice.setBorder(BorderFactory.createLineBorder(Color.black));
        add(computerChoice);

        //create player score label
        playerScoreLabel = new JLabel("Player: 0");
        playerScoreLabel.setBounds(0,317,450,30);
        playerScoreLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        playerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(playerScoreLabel);

        //rock button
        rockButton = new JButton("Rock");
        rockButton.setBounds(40, 387, 98, 81);
        rockButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        rockButton.setFocusPainted(false);
        rockButton.addActionListener(this);
        add(rockButton);

        //paper button
        paperButton = new JButton("Paper");
        paperButton.setBounds(165, 387, 98, 81);
        paperButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        paperButton.setFocusPainted(false);
        paperButton.addActionListener(this);
        add(paperButton);

        //scissor button
        scissorButton = new JButton("Scissor");
        scissorButton.setBounds(290, 387, 98, 81);
        scissorButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        scissorButton.setFocusPainted(false);
        scissorButton.addActionListener(this);
        add(scissorButton);

    }

    void showDialog(String message){
        JDialog resultDialog = new JDialog(this,"Result", true);
        resultDialog.setSize(227, 124);
        resultDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        resultDialog.setResizable(false);

        //message label
        JLabel resultLabel = new JLabel(message);
        resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultDialog.add(resultLabel, BorderLayout.CENTER);

        //try again button
        JButton tryAgainButton = new JButton("Try Again?");
        tryAgainButton.setFont(new Font("Dialog", Font.BOLD, 18));
        tryAgainButton.setFocusPainted(false);
        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //reset the computer choice
                computerChoice.setText("?");

                //close the dialogue box
                resultDialog.dispose();
            }
        });
        resultDialog.add(tryAgainButton, BorderLayout.SOUTH);

        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //get player choice
        String playerChoice = e.getActionCommand().toString();

        // play rock paper scissor and store value
        String result = rockPaperScissor.playRockPaperScissor(playerChoice);

        //load computer choice
        computerChoice.setText(rockPaperScissor.getComputerChoice());

        //update the score
        computerScoreLabel.setText("Computer: " + rockPaperScissor.getComputerScore());
        playerScoreLabel.setText("Player: " + rockPaperScissor.getPlayerScore());

        showDialog(result);
    }
}
