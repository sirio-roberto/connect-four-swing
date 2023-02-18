package four;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectFour extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int ROWS = 6;
    final int COLUMNS = 7;
    String player = "X";
    Board board;
    Boolean gameOver = false;

    public ConnectFour() {
        setTitle("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(ROWS, COLUMNS, 0, 0));
        add(gridPanel, BorderLayout.CENTER);

        JPanel resetButtonPanel = new JPanel();
        resetButtonPanel.setLayout(new GridLayout(1, 1));
        addResetButton(resetButtonPanel);
        add(resetButtonPanel, BorderLayout.SOUTH);

        board = new Board();

        for (int i = ROWS - 1; i >= 0; i--) {
            for (int j = 0; j < COLUMNS; j++) {
                gridPanel.add(board.cells[i][j]);
            }
        }

        setVisible(true);
    }

    private void addResetButton(JPanel jPanel) {
        JButton buttonReset = new JButton("Reset");
        buttonReset.setName("ButtonReset");
        buttonReset.setBorderPainted(false);
        buttonReset.setBackground(Color.CYAN);
        jPanel.add(buttonReset);

        buttonReset.addActionListener(e -> {
            board.resetGame();
            gameOver = false;
            this.player = "X";
        });
    }

    class Board {
        JButton[][] cells = new JButton[ROWS][COLUMNS];

        public Board() {
            for (int i = ROWS; i >= 1; i--) {
                for (int j = 'A'; j <= 'G'; j++) {
                    String buttonName = "Button" + Character.toString(j) + i;
                    JButton jButton = new JButton(" ");
                    jButton.setName(buttonName);
                    jButton.setFocusPainted(false);
                    jButton.setFont(jButton.getFont().deriveFont(Font.BOLD, 16));
                    jButton.setBackground(Color.GRAY);

                    jButton.addActionListener(e -> {
                        if (!gameOver) {
                            char col = jButton.getName().charAt(jButton.getName().length() - 2);
                            for (int k = 0; k < ROWS; k++) {
                                if (" ".equals(cells[k][col - 'A'].getText())) {
                                    cells[k][col - 'A'].setText(player);

                                    checkGameStatus(k, col - 'A');

                                    player = player.equals("X") ? "O" : "X";
                                    break;
                                }
                            }
                        }
                    });

                    cells[i - 1][j - 'A'] = jButton;
                }
            }
        }

        private void checkGameStatus(int row, int column) {
            List<JButton> winnerSequence = new ArrayList<>();
            winnerSequence.add(cells[row][column]);
            // horizontal check
            // left
            int i = column - 1;
            while (i >= 0) {
                if (cells[row][i].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[row][i]);
                    i--;
                }
                else {
                    break;
                }
            }
            // right
            i = column + 1;
            while (i < COLUMNS) {
                if (cells[row][i].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[row][i]);
                    i++;
                }
                else {
                    break;
                }
            }
            if (winnerSequence.size() == 4) {
                paintWinnerCells(winnerSequence);
                gameOver = true;
                return;
            } else {
                winnerSequence.clear();
                winnerSequence.add(cells[row][column]);
            }

            // vertical check
            // above
            i = row - 1;
            while (i >= 0) {
                if (cells[i][column].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[i][column]);
                    i--;
                }
                else {
                    break;
                }
            }
            // below
            i = row + 1;
            while (i < ROWS) {
                if (cells[i][column].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[i][column]);
                    i++;
                }
                else {
                    break;
                }
            }
            if (winnerSequence.size() == 4) {
                paintWinnerCells(winnerSequence);
                gameOver = true;
                return;
            } else {
                winnerSequence.clear();
                winnerSequence.add(cells[row][column]);
            }

            // diagonal check > from upper-left to lower-right
            // upper-left
            i = row - 1;
            while (i >= 0) {
                if (cells[i][i].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[i][i]);
                    i--;
                }
                else {
                    break;
                }
            }
            // lower-right
            i = row + 1;
            while (i < ROWS) {
                if (cells[i][i].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[i][i]);
                    i++;
                }
                else {
                    break;
                }
            }
            if (winnerSequence.size() == 4) {
                paintWinnerCells(winnerSequence);
                gameOver = true;
                return;
            } else {
                winnerSequence.clear();
                winnerSequence.add(cells[row][column]);
            }

            // diagonal check > from upper-right to lower-left
            // upper-right
            i = row - 1;
            int j = column + 1;
            while (i >= 0 && j < COLUMNS) {
                if (cells[i][j].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[i][j]);
                    i--;
                    j++;
                }
                else {
                    break;
                }
            }
            // lower-right
            i = row + 1;
            j = column - 1;
            while (i < ROWS && j >= 0) {
                if (cells[i][j].getText().equals(cells[row][column].getText())) {
                    winnerSequence.add(cells[i][j]);
                    i++;
                    j--;
                }
                else {
                    break;
                }
            }
            if (winnerSequence.size() == 4) {
                paintWinnerCells(winnerSequence);
                gameOver = true;
            } else {
                winnerSequence.clear();
            }
        }

        private void paintWinnerCells(List<JButton> winnerSequence) {
            for (JButton jButton: winnerSequence) {
                jButton.setBackground(Color.CYAN);
            }
        }

        public void resetGame() {
            for (JButton[] rows: cells) {
                for (JButton jButton: rows) {
                    jButton.setText(" ");
                    jButton.setBackground(Color.GRAY);
                }
            }
        }
    }
}

