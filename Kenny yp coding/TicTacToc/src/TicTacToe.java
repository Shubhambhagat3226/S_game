import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {

    int boardWidth = 600;
    int boardHeight = 700;

    JLabel textLabel;
    JPanel textPanel;
    JPanel boardPanel;
    JPanel resetPanel;

    String[] player = {"X", "O"};
    String currentPlayer = player[0];

    JButton[][] board = new JButton[3][3];
    boolean gameOver = false;
    int turn = 0;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(boardWidth, boardHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        addTitle();
        addGameArea();
    }

    void addTitle() {
        textLabel = new JLabel();
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel = new JPanel(new BorderLayout());
        textPanel.add(textLabel);
        add(textPanel, BorderLayout.NORTH);

        JButton reset = new JButton("New Game");
        reset.setFont(new Font("Arial", Font.BOLD, 50));
        reset.setForeground(Color.WHITE);
        reset.setBackground(Color.GRAY);
        reset.setFocusable(false);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        JButton tile = board[i][j];
                        tile.setBackground(Color.DARK_GRAY);
                        tile.setForeground(Color.WHITE);
                        tile.setText("");
                    }
                }
                turn = 0;
                gameOver = false;

                textLabel.setText("Tic-Tac-Toe");
            }
        });

        resetPanel = new JPanel(new BorderLayout());
        resetPanel.add(reset, BorderLayout.CENTER);
        add(resetPanel,BorderLayout.SOUTH);
    }

    void addGameArea() {
        boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(Color.DARK_GRAY);
        add(boardPanel);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton tile = new JButton();
                board[row][col] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
//                tile.setFocusPainted(false);

                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (gameOver) return;

                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);
                            turn++;
                            checkWinner();

                            if (!gameOver){
                                currentPlayer = (currentPlayer == player[0]) ? player[1] : player[0];
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });

            }
        }
    }

    void checkWinner() {
        // HORIZONTAL
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;

            if (board[r][0].getText() == board[r][1].getText()
                    && board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        //VERTICAL
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;

            if (board[0][c].getText() == board[1][c].getText()
                    && board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        // DIAGONALLY
        if (board[0][0].getText() != "" &&
            board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText()) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver=true;
            return;
        }

        // ANTI-DIAGONALLY
        if (board[0][2].getText() != ""
                && board[0][2].getText() == board[1][1].getText()
                && board[1][1].getText() == board[2][0].getText()) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][2-i]);
            }
            gameOver=true;
            return;
        }

        if (turn == 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                       setTie(board[i][j]);
                }
            }
            gameOver=true;
        }
    }

    void setWinner(JButton tile) {
        tile.setBackground(Color.GRAY);
        tile.setForeground(Color.GREEN);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void setTie(JButton tile) {
        tile.setBackground(Color.GRAY);
        tile.setForeground(Color.ORANGE);
        textLabel.setText("Tie!");
    }
}











