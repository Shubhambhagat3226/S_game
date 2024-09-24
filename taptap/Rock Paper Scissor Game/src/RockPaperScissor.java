import java.util.Random;

//backend
public class RockPaperScissor {
    //all the choices
    private final static String[]  computerChoices = {"Rock", "Paper", "Scissor"};

    //store the choice
    private String computerChoice;

    //scores container
    private int computerScore, playerScore;

    //getter
    public String getComputerChoice() {
        return computerChoice;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    //use to get random number to make choices
    private Random random;

    //constructor - to initialize random obj
    public RockPaperScissor(){
        random = new Random();
    }

    //this method is call to play the game
    public String playRockPaperScissor(String playerChoice){
        //generate computer choice
        computerChoice = computerChoices[random.nextInt(computerChoices.length)];

        //returning message container
        String result;

        if (computerChoice.equals(playerChoice)){
            result = "Draw";
        } else if ((computerChoice.equals("Rock") && playerChoice.equals("Paper"))
                || (computerChoice.equals("Paper") && playerChoice.equals("Scissor"))
                || (computerChoice.equals("Scissor") && playerChoice.equals("Rock"))) {

                result = "Player Wins";
                playerScore++;

        } else {
                result = "Computer Wins";
                computerScore++;
        }

        return result;
    }
}
