
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Description:
//              This is the main class of the game, the purpose of the game is to have all the characters play a sort of "capture the flag" game. Using multithreading.
//              In which all characters are trying to get "carrots" as a point token and all of them are fighting against each other to reach the goal of winning.
//              Certain characters are trying to eat each other and making it impossible to win for certain characters. Whoever eats the most and is the last person 
//              standing wins the game.
// Token ID's: 
//          0 = blank
//          1 = carrot
//          2 = mountain
//          3 = bugs
//          4 = tweety
//          5 = muttley
//          6 = marvin
//
// Board Info: 
//          [row][column]
//          i = row
//          j = column
//
// Movement ID's: s
//          0 = up
//          1 =right
//          2 = down
//          3 = left
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package pkg380.project.pkg2;

import java.util.Random;

public class Game {

    public static int[][] board = new int[5][5];
    public static int turnCount = 0;
    public static boolean winner = false;
    public static Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {

        populate();
        printBoard();

        BugsThread BThread = new BugsThread();
        TweetyThread TThread = new TweetyThread();
        MuttThread DThread = new MuttThread();
        MarvinThread MThread = new MarvinThread();

        Bugs.isTurn = true;

        BThread.start();
        TThread.start();
        DThread.start();
        MThread.start();

        try {
            BThread.join();
            TThread.join();
            DThread.join();
            MThread.join();

        } catch (InterruptedException e) {
            System.out.println("Something went wrong.");
        }

        System.out.println("Sucess");

    }

    public static void populate() {
        for (int[] board1 : board) {
            for (int j = 0; j < board.length; j++) {
                board1[j] = 0;
            }
        }

        board[0][0] = Bugs.id;
        board[4][0] = Tweety.id;
        board[2][3] = Mutt.id;
        board[0][4] = Marvin.id;
        board[3][4] = 1;
        board[3][1] = 1;
        board[0][2] = 2;
    }

    // This part of the main class creates the board being used in the game.
    public static void printBoard() {
        System.out.println("--------------------------");

        for (int[] board1 : board) {
            for (int j = 0; j < board.length; j++) {
                switch (board1[j]) {
                    case 0:
                        System.out.print("-");
                        System.out.print("     ");
                        break;
                    case 1:
                        System.out.print("C");
                        System.out.print("     ");
                        break;
                    case 2:
                        System.out.print("F");
                        System.out.print("     ");
                        break;
                    case 3:
                        System.out.print("B");
                        System.out.print("     ");
                        break;
                    case 4:
                        System.out.print("T");
                        System.out.print("     ");
                        break;
                    case 5:
                        System.out.print("D");
                        System.out.print("     ");
                        break;
                    case 6:
                        System.out.print("M");
                        System.out.print("     ");
                        break;
                    case 31:
                        System.out.print("BC");
                        System.out.print("    ");
                        break;
                    case 41:
                        System.out.print("TC");
                        System.out.print("    ");
                        break;
                    case 51:
                        System.out.print("DC");
                        System.out.print("    ");
                        break;
                    case 61:
                        System.out.print("MC");
                        System.out.print("    ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

}

// This method keeps track of the turn and winning score for Bugs. 
class BugsThread extends Thread {

    @Override
    public void run() {

        while (!Game.winner) {

            Game.turnCount++;

            while (!Bugs.isTurn) {

                if (Game.winner) {
                    return;
                }
            }

            Bugs.isTurn = false;
            boolean hasMoved = false;
            boolean canMove = true;

            long testCount = 0;

            while (!hasMoved) {

                testCount++;

                if (testCount == 1000000000) {
                    System.out.println();
                }

                if (testCount == 100000) {
                    //System.out.println("STOP");
                    break;

                }
                
                if(Bugs.isDead){
                    break;
                }

                canMove = true;
                int move = Game.rand.nextInt(4);

                if (Bugs.row == 0 && Bugs.col == 0 && (move == 0 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Bugs.row == 4 && Bugs.col == 0 && (move == 2 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Bugs.row == 0 && Bugs.col == 4 && (move == 0 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Bugs.row == 4 && Bugs.col == 4 && (move == 2 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Bugs.row == 0 && move == 0) {
                    hasMoved = false;
                    canMove = false;
                } else if (Bugs.row == 4 && move == 2) {
                    hasMoved = false;
                    canMove = false;
                } else if (Bugs.col == 0 && move == 3) {
                    hasMoved = false;
                    canMove = false;
                } else if (Bugs.col == 4 && move == 1) {
                    hasMoved = false;
                    canMove = false;
                }

                if (canMove) {
                    switch (move) {
                        case 0:
                    switch (Game.board[Bugs.row - 1][Bugs.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Bugs.row][Bugs.col] = 0;
                            Bugs.row--;
                            Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            break;
                        case 1:
                            if (Bugs.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Bugs.hasFlag = true;
                                Bugs.id = 31;
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.row--;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            }
                            break;
                        case 2:
                            if (Bugs.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.row--;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                                System.out.println("Bugs is the winner!");
                            }
                            break;
                        default:
                            hasMoved = false;
                            break;
                    }
break;
                        case 1:
                    switch (Game.board[Bugs.row][Bugs.col + 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Bugs.row][Bugs.col] = 0;
                            Bugs.col++;
                            Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            break;
                        case 1:
                            if (Bugs.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Bugs.hasFlag = true;
                                Bugs.id = 31;
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.col++;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            }
                            break;
                        case 2:
                            if (Bugs.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.col++;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                                System.out.println("Bugs is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        case 2:
                    switch (Game.board[Bugs.row + 1][Bugs.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Bugs.row][Bugs.col] = 0;
                            Bugs.row++;
                            Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            break;
                        case 1:
                            if (Bugs.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Bugs.hasFlag = true;
                                Bugs.id = 31;
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.row++;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            }
                            break;
                        case 2:
                            if (Bugs.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.row++;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                                System.out.println("Bugs is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        case 3:
                    switch (Game.board[Bugs.row][Bugs.col - 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Bugs.row][Bugs.col] = 0;
                            Bugs.col--;
                            Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            break;
                        case 1:
                            if (Bugs.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Bugs.hasFlag = true;
                                Bugs.id = 31;
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.col--;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                            }
                            break;
                        case 2:
                            if (Bugs.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Bugs.row][Bugs.col] = 0;
                                Bugs.col--;
                                Game.board[Bugs.row][Bugs.col] = Bugs.id;
                                System.out.println("Bugs is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        default:
                            break;
                    }

                }
            }

            Game.printBoard();
            Tweety.isTurn = true;
        }
    }

}

// This method keeps track of the turn and winning score for Tweety.
class TweetyThread extends Thread {

    @Override
    public void run() {

        while (!Game.winner) {

            while (!Tweety.isTurn) {
                if (Game.winner) {
                    return;
                }
            }

            Tweety.isTurn = false;
            boolean hasMoved = false;
            boolean canMove = true;

            long testCount = 0;

            while (!hasMoved) {

                testCount++;

                if (testCount == 1000000000) {
                    System.out.println();
                }

                if (testCount == 100000) {
                    //System.out.println("STOP");
                    break;

                }
                
                if(Tweety.isDead){
                    break;
                }

                canMove = true;
                int move = Game.rand.nextInt(4);

                if (Tweety.row == 0 && Tweety.col == 0 && (move == 0 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Tweety.row == 4 && Tweety.col == 0 && (move == 2 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Tweety.row == 0 && Tweety.col == 4 && (move == 0 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Tweety.row == 4 && Tweety.col == 4 && (move == 2 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Tweety.row == 0 && move == 0) {
                    hasMoved = false;
                    canMove = false;
                } else if (Tweety.row == 4 && move == 2) {
                    hasMoved = false;
                    canMove = false;
                } else if (Tweety.col == 0 && move == 3) {
                    hasMoved = false;
                    canMove = false;
                } else if (Tweety.col == 4 && move == 1) {
                    hasMoved = false;
                    canMove = false;
                }

                if (canMove) {
                    switch (move) {
                        case 0:
                    switch (Game.board[Tweety.row - 1][Tweety.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Tweety.row][Tweety.col] = 0;
                            Tweety.row--;
                            Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            break;
                        case 1:
                            if (Tweety.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Tweety.hasFlag = true;
                                Tweety.id = 41;
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.row--;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            }
                            break;
                        case 2:
                            if (Tweety.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.row--;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                                System.out.println("Tweety is the winner!");
                            }
                            break;
                        default:
                            hasMoved = false;
                            break;
                    }
break;
                        case 1:
                    switch (Game.board[Tweety.row][Tweety.col + 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Tweety.row][Tweety.col] = 0;
                            Tweety.col++;
                            Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            break;
                        case 1:
                            if (Tweety.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Tweety.hasFlag = true;
                                Tweety.id = 41;
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.col++;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            }
                            break;
                        case 2:
                            if (Tweety.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.col++;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                                System.out.println("Tweety is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        case 2:
                    switch (Game.board[Tweety.row + 1][Tweety.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Tweety.row][Tweety.col] = 0;
                            Tweety.row++;
                            Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            break;
                        case 1:
                            if (Tweety.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Tweety.hasFlag = true;
                                Tweety.id = 41;
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.row++;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            }
                            break;
                        case 2:
                            if (Tweety.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.row++;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                                System.out.println("Tweety is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        case 3:
                    switch (Game.board[Tweety.row][Tweety.col - 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Tweety.row][Tweety.col] = 0;
                            Tweety.col--;
                            Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            break;
                        case 1:
                            if (Tweety.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Tweety.hasFlag = true;
                                Tweety.id = 41;
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.col--;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                            }
                            break;
                        case 2:
                            if (Tweety.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Tweety.row][Tweety.col] = 0;
                                Tweety.col--;
                                Game.board[Tweety.row][Tweety.col] = Tweety.id;
                                System.out.println("Tweety is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        default:
                            break;
                    }

                }
            }

            Game.printBoard();
            Mutt.isTurn = true;
        }
    }

}

// This method keeps track of the turn and winning score for Mutt.
class MuttThread extends Thread {

    @Override
    public void run() {

        while (!Game.winner) {

            while (!Mutt.isTurn) {
                if (Game.winner) {
                    return;
                }
            }

            Mutt.isTurn = false;
            boolean hasMoved = false;
            boolean canMove = true;

            long testCount = 0;

            while (!hasMoved) {

                testCount++;

                if (testCount == 1000000000) {
                    System.out.println();
                }

                if (testCount == 100000) {
                    //System.out.println("STOP");
                    break;

                }
                
                if(Mutt.isDead){
                    break;
                }

                canMove = true;
                int move = Game.rand.nextInt(4);

                if (Mutt.row == 0 && Mutt.col == 0 && (move == 0 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Mutt.row == 4 && Mutt.col == 0 && (move == 2 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Mutt.row == 0 && Mutt.col == 4 && (move == 0 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Mutt.row == 4 && Mutt.col == 4 && (move == 2 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Mutt.row == 0 && move == 0) {
                    hasMoved = false;
                    canMove = false;
                } else if (Mutt.row == 4 && move == 2) {
                    hasMoved = false;
                    canMove = false;
                } else if (Mutt.col == 0 && move == 3) {
                    hasMoved = false;
                    canMove = false;
                } else if (Mutt.col == 4 && move == 1) {
                    hasMoved = false;
                    canMove = false;
                }

                if (canMove) {
                    switch (move) {
                        case 0:
                    switch (Game.board[Mutt.row - 1][Mutt.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Mutt.row][Mutt.col] = 0;
                            Mutt.row--;
                            Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            break;
                        case 1:
                            if (Mutt.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Mutt.hasFlag = true;
                                Mutt.id = 51;
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.row--;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            }
                            break;
                        case 2:
                            if (Mutt.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.row--;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                                System.out.println("Taz is the winner!");
                            }
                            break;
                        default:
                            hasMoved = false;
                            break;
                    }
break;
                        case 1:
                    switch (Game.board[Mutt.row][Mutt.col + 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Mutt.row][Mutt.col] = 0;
                            Mutt.col++;
                            Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            break;
                        case 1:
                            if (Mutt.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Mutt.hasFlag = true;
                                Mutt.id = 51;
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.col++;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            }
                            break;
                        case 2:
                            if (Mutt.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.col++;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                                System.out.println("Taz is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        case 2:
                    switch (Game.board[Mutt.row + 1][Mutt.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Mutt.row][Mutt.col] = 0;
                            Mutt.row++;
                            Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            break;
                        case 1:
                            if (Mutt.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Mutt.hasFlag = true;
                                Mutt.id = 51;
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.row++;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            }
                            break;
                        case 2:
                            if (Mutt.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.row++;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                                System.out.println("Taz is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        case 3:
                    switch (Game.board[Mutt.row][Mutt.col - 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Mutt.row][Mutt.col] = 0;
                            Mutt.col--;
                            Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            break;
                        case 1:
                            if (Mutt.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Mutt.hasFlag = true;
                                Mutt.id = 51;
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.col--;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                            }
                            break;
                        case 2:
                            if (Mutt.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Mutt.row][Mutt.col] = 0;
                                Mutt.col--;
                                Game.board[Mutt.row][Mutt.col] = Mutt.id;
                                System.out.println("Taz is the winner!");
                            }
                            break;
                        default:
                            break;
                    }
break;
                        default:
                            break;
                    }

                }
            }

            Game.printBoard();
            Marvin.isTurn = true;
        }
    }

}

// This method keeps track of the turn and winning score for Marvin.
class MarvinThread extends Thread {

    @Override
    public void run() {

        while (!Game.winner) {

            while (!Marvin.isTurn) {
                if (Game.winner || Marvin.isDead) {
                    return;
                }
            }

            Marvin.isTurn = false;
            boolean hasMoved = false;
            boolean canMove = true;

            long testCount = 0;

            while (!hasMoved) {

                testCount++;

                if (testCount == 1000000000) {
                    System.out.println();
                }

                if (testCount == 100000) {
                    //System.out.println("STOP");
                    break;

                }

                canMove = true;
                int move = Game.rand.nextInt(4);

                if (Marvin.row == 0 && Marvin.col == 0 && (move == 0 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Marvin.row == 4 && Marvin.col == 0 && (move == 2 || move == 3)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Marvin.row == 0 && Marvin.col == 4 && (move == 0 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Marvin.row == 4 && Marvin.col == 4 && (move == 2 || move == 1)) {
                    hasMoved = false;
                    canMove = false;
                } else if (Marvin.row == 0 && move == 0) {
                    hasMoved = false;
                    canMove = false;
                } else if (Marvin.row == 4 && move == 2) {
                    hasMoved = false;
                    canMove = false;
                } else if (Marvin.col == 0 && move == 3) {
                    hasMoved = false;
                    canMove = false;
                } else if (Marvin.col == 4 && move == 1) {
                    hasMoved = false;
                    canMove = false;
                }

                if (canMove) {
                    switch (move) {
                        case 0:
                    switch (Game.board[Marvin.row - 1][Marvin.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 1:
                            if (Marvin.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.row--;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            }
                            break;
                        case 2:
                            if (Marvin.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.row--;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                                System.out.println("Marvin is the winner!");
                            }
                            break;
                        default:
                    switch (Game.board[Marvin.row - 1][Marvin.col]) {
                        case 3:
                            if (Bugs.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Bugs.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 4:
                            if (Tweety.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Tweety.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 5:
                            if (Mutt.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Mutt.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        default:
                            break;
                    }
                            break;
                    }
break;
                        case 1:
                    switch (Game.board[Marvin.row][Marvin.col + 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.col++;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 1:
                            if (Marvin.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.col++;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            }
                            break;
                        case 2:
                            if (Marvin.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.col++;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                                System.out.println("Marvin is the winner!");
                            } else {
                        switch (Game.board[Marvin.row][Marvin.col + 1]) {
                            case 3:
                                if (Bugs.hasFlag) {
                                    Marvin.hasFlag = true;
                                    Marvin.id = 61;
                                }
                                Bugs.isDead = true;
                                hasMoved = true;
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.col++;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                                break;
                            case 4:
                                if (Tweety.hasFlag) {
                                    Marvin.hasFlag = true;
                                    Marvin.id = 61;
                                }
                                Tweety.isDead = true;
                                hasMoved = true;
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.col++;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                                break;
                            case 5:
                                if (Mutt.hasFlag) {
                                    Marvin.hasFlag = true;
                                    Marvin.id = 61;
                                }
                                Mutt.isDead = true;
                                hasMoved = true;
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.col++;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                                break;
                            default:
                                break;
                        }
                            }
                            break;
                        default:
                            break;
                    }
break;
                        case 2:
                    switch (Game.board[Marvin.row + 1][Marvin.col]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row++;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 1:
                            if (Marvin.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.row++;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            }
                            break;
                        case 2:
                            if (Marvin.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.row++;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                                System.out.println("Marvin is the winner!");
                            }
                            break;
                        default:
                    switch (Game.board[Marvin.row + 1][Marvin.col]) {
                        case 3:
                            if (Bugs.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Bugs.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row++;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 4:
                            if (Tweety.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Tweety.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row++;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 5:
                            if (Mutt.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Mutt.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.row++;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        default:
                            break;
                    }
                            break;
                    }
break;
                        case 3:
                    switch (Game.board[Marvin.row][Marvin.col - 1]) {
                        case 0:
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.col--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 1:
                            if (Marvin.hasFlag) {
                                hasMoved = false;
                            } else {
                                hasMoved = true;
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.col--;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            }
                            break;
                        case 2:
                            if (Marvin.hasFlag) {
                                hasMoved = true;
                                Game.winner = true;
                                
                                Game.board[Marvin.row][Marvin.col] = 0;
                                Marvin.col--;
                                Game.board[Marvin.row][Marvin.col] = Marvin.id;
                                System.out.println("Marvin is the winner!");
                            }
                            break;
                        default:
                    switch (Game.board[Marvin.row][Marvin.col - 1]) {
                        case 3:
                            if (Bugs.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Bugs.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.col--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 4:
                            if (Tweety.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Tweety.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.col--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        case 5:
                            if (Mutt.hasFlag) {
                                Marvin.hasFlag = true;
                                Marvin.id = 61;
                            }
                            Mutt.isDead = true;
                            hasMoved = true;
                            Game.board[Marvin.row][Marvin.col] = 0;
                            Marvin.col--;
                            Game.board[Marvin.row][Marvin.col] = Marvin.id;
                            break;
                        default:
                            break;
                    }
                            break;
                    }
break;
                        default:
                            break;
                    }

                }
            }
            
            if(Game.turnCount == 3){
                Game.turnCount = 0;
                boolean mountainMoved = false;
                
                while(!mountainMoved){
                    int i = Game.rand.nextInt(4);
                    int j = Game.rand.nextInt(4);
                    
                    if(Game.board[i][j] == 0){
                        Game.board[Mountain.row][Mountain.col] = 0;
                        Mountain.row = i;
                        Mountain.col = j;
                        Game.board[Mountain.row][Mountain.col] = Mountain.id;
                        mountainMoved = true;
                    }
                }
            }

            Game.printBoard();
            Bugs.isTurn = true;
        }
    }

}
