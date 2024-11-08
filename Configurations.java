/*This class implements all the methods needed by algorithm computerPlay*/

import java.util.Arrays;

public class Configurations {
    private char[][] board; // game board
    private int size; // game board size
    private int length; // the length of the X shape or + shape needed to win
    private int maxLevel; // maximum level of the game tree that will be explored

    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
        size = boardSize;
        length = lengthToWin;
        maxLevel = maxLevels;

        // initialize the game so that every entry of board initially stores a space ' '
        board = new char[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(board[i], ' ');
        }

    }

    // this method creates and returns an empty HashDictionary with fixed size
    // the size need to be a prime number
    public HashDictionary createDictionary() {
        return new HashDictionary(13441);
    }

    // this method first stores the characters of board in a String as described above;
    // then it checks whether the String representing the board is in hashTable
    public int repeatedConfiguration(HashDictionary hashTable) {
        String boardToString = covertBoardToString();

        // the get method in HashDictionary will return the associated score if a record exists
        // otherwise it will return -1
        return hashTable.get(boardToString);

    }

    // this method first represents the content of board as a String as described above
    // then it inserts this String and score in hashDictionary.
    public void addConfiguration(HashDictionary hashDictionary, int score) {
        String boardToString = covertBoardToString();

        Data data = new Data(boardToString, score);

        hashDictionary.put(data);
    }

    // this method stores symbol in certain row and column
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    // returns true if board[row][col] is ' '; otherwise it returns false.
    public boolean squareIsEmpty(int row, int col) {
        return board[row][col] == ' ';
    }


    // this method check for if there exists an X or + shape of length k in rows, columns, and diagonals
    public boolean wins(char symbol) {
        int k = length;

        // i: row, j: column
        // Check for + shape wins
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                int plusCountHorizontal = 0;
                int plusCountVertical = 0;

                // Check horizontal line (left and right)
                for (int h = -k + 1; h < k; h++) {
                    if (j + h >= 0 && j + h < size) {
                        if (board[i][j + h] == symbol) {
                            plusCountHorizontal++;
                        }
                    }
                }

                // Check vertical line (up and down)
                for (int v = -k + 1; v < k; v++) {
                    if (i + v >= 0 && i + v < size) {
                        if (board[i + v][j] == symbol) {
                            plusCountVertical++;
                        }
                    }
                }

                // Check if either horizontal or vertical line meets the win condition
                if (plusCountHorizontal >= 3 && plusCountVertical >= 3) {
                    if (plusCountHorizontal + plusCountVertical - 1 >= k) {
                        // Check the positions to the left and right
                        boolean horizontalCheck = true;
                        for (int x = -1; x <= 1; x++) {
                            if (j + x >= 0 && j + x < size && board[i][j + x] != symbol) {
                                horizontalCheck = false;
                                break;
                            }
                        }

                        // Check the positions above and below
                        boolean verticalCheck = true;
                        for (int y = -1; y <= 1; y++) {
                            if (i + y >= 0 && i + y < size && board[i + y][j] != symbol) {
                                verticalCheck = false;
                                break;
                            }
                        }

                        if (horizontalCheck && verticalCheck) {
                            return true;
                        }
                    }
                }
            }
        }

        // i: row, j: column
        // Check for x shape wins
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                int xCountLeft = 0;
                int xCountRight = 0;

                // Check the left diagonal (top-left to bottom-right)
                for (int d1 = -k + 1; d1 < k; d1++) {
                    if (i + d1 >= 0 && i + d1 < size && j + d1 >= 0 && j + d1 < size) {
                        if (board[i + d1][j + d1] == symbol) {
                            xCountLeft++;
                        }

                    }
                }

                // Check the right diagonal (top-right to bottom-left)
                for (int d2 = -k + 1; d2 < k; d2++) {
                    if (i + d2 >= 0 && i + d2 < size && j - d2 >= 0 && j - d2 < size) {
                        if (board[i + d2][j - d2] == symbol) {
                            xCountRight++;
                        }

                    }
                }

                // Check if the total diagonal counts meet the win condition
                if (xCountLeft >= 3 && xCountRight >= 3) {
                    if (xCountLeft + xCountRight - 1 >= k) {
                        boolean leftDiagonalCheck = true;
                        for (int x = -1; x <= 1; x++) {
                            if(i + x >= 0 && i + x < size && j + x >= 0 && j + x < size
                                    && board[i + x][j + x] != symbol){
                                leftDiagonalCheck = false;
                                break;
                            }
                        }

                        boolean rightDiagonalCheck = true;
                        for (int x = -1; x <= 1; x++) {
                            if(i + x >= 0 && i + x < size && j - x >= 0 && j - x < size
                                    && board[i + x][j - x] != symbol){
                                rightDiagonalCheck = false;
                                break;
                            }
                        }

                        if (leftDiagonalCheck && rightDiagonalCheck) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    // this method returns true if board has no empty positions left and no player has won the game.
    public boolean isDraw() {
        if (!wins('X') && !wins('O')) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == ' ') {
                        return false; // There's an empty position
                    }
                }
            }
            return true; // No empty positions and no player has won
        }
        return false;
    }

    // this method valuate the current state of the board
    public int evalBoard() {
        if (wins('O')) {
            return 3;
        } // Computer has won
        if (wins('X')) {
            return 0; // Human player has won
        }
        if (isDraw()) {
            return 2; // It's a draw
        }
        return 1; // Undecided, the game is ongoing
    }


    // a helper method to convert the board to a string
    private String covertBoardToString() {
        StringBuilder newBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            newBuilder.append(board[i]);
        }

        return newBuilder.toString();
    }
}
