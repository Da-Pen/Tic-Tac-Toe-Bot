/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;
import java.util.*;
import java.io.*;
/**
 *
 * @author Daniel Peng
 */
public class TicTacToe {
    //0=empty

    public static int choice;
    public static void main(String[] args) {
        //empty is 0, player is 1, bot is 2
        int[] board = new int[9];
        //board[4]=1;
        printBoard(board);
        Scanner sc = new Scanner(System.in);
        boolean botGoesFirst = true;
        if(botGoesFirst){
            botChoose(board, 2);
            board[choice] = 2;
            printBoard(board);
        }
        while(gameState(board) == 0){//game not finished
            boolean madeValidMove = false;
            int p = 0;
            while(!madeValidMove){
                p = Integer.parseInt(sc.nextLine())-1;
                ArrayList<Integer> poss = possMoves(board);
                //check if player move is valid
                boolean valid = false;
                for(int i : poss){
                    if (p == i){
                        valid = true;
                    }
                }
                if(!valid){
                    System.out.println("invalid move");
                }else{
                    madeValidMove = true;
                }
            }
           // p = botChoose(board, 1);
            board[p]=1;
            printBoard(board);
            if(gameState(board)==0){
                botChoose(board, 2);
                board[choice] = 2;
                printBoard(board);
            }
        }
        if(gameState(board) == 1){
            System.out.println("player won");
        }else if(gameState(board) == 2){
            System.out.println("bot won");
        }else{
            System.out.println("tie");
        }
    }
    public static int botChoose(int[] board, int turn){
        //returns the move occur
        //turn: 1 is players turn; 2 is bots turn
        if(gameState(board) !=0){//game is over
            return stateToScore(gameState(board));
        }
        else{//game not over
            if(turn == 2){//bot turn
                ArrayList<Integer> poss = possMoves(board);
                int maxScore = -20;
                int testScore;
                int bestPos = 0;
                for(int i: poss){//for each possible move, check min loss
                    board[i] = 2;//change boawrd to reflect possible move
                    testScore = botChoose(board, 1);
                    board[i] = 0;//revert board
                    if(testScore >= maxScore){
                        maxScore = testScore;
                        bestPos = i;
                    }
                }
                choice = bestPos;
                return maxScore;
                
            }else{//player's turn
                ArrayList<Integer> poss = possMoves(board);
                int minScore = 20;
                int testScore;
                int worstPos = 0;
                for(int i: poss){//for each possible move, check min loss
                    board[i] = 1;//change board to reflect possible move
                    testScore = botChoose(board, 2);
                    board[i] = 0;//revert board
                    if(testScore <= minScore){
                        minScore = testScore;
                        worstPos = i;
                    }
                }
                choice = worstPos;
                return minScore;
            }
        }
        
       // return 1;
        
    }
    public static int stateToScore(int state){
        switch(state){
            case 1://player wins
                return -10;
            case 2://bot wins
                return 10;
            case 3://tie
                return 0;
            default://game not over yet (should not occur)
                return -1;
            
        }
    }
    public static ArrayList possMoves(int[] board){
        ArrayList<Integer> poss = new ArrayList<>();
        //add empties to arrayList
        for(int i = 0; i<9;i++){
            if(board[i] == 0){
                poss.add(i);
            //    System.out.println("a possible move: " + i);
            }
        }
        return poss;
    }
    public static int gameState(int[] board){
        //0 is unfinished, 1 is player wins, 2 is bot wins, 3 is tie
        int[][] poss = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for(int i = 0; i < 8; i++){
            //if player has won
            if(board[poss[i][0]] == 1 && board[poss[i][1]] == 1 && board[poss[i][2]] == 1){
                return 1;
                
            }else if(board[poss[i][0]] == 2 && board[poss[i][1]] == 2 && board[poss[i][2]] == 2){
                return 2;
            }
        }
        //if there are any 0's (unfilled spots) it is unfinished, otherwise it is a tie
        for(int i:board){
            if(i==0){
                return 0;
            }
        }
        return 3;
    }
    public static void printBoard(int[] board){
        for(int i = 0; i < 9; i++){
            
            System.out.print(boardPrintCode(board[i],i));
            if((i+1)%3 == 0){
                System.out.println("");
            }
        }
        System.out.println("");
    }
    public static String boardPrintCode(int state, int pos){
        switch(state){
            case 1:return " O ";
            case 2: return " X ";
            default: return ("[" + (pos+1) + "]");
        }
    }
    public static int randomInRange(int min, int max){
        int range = (max-min)+1;
        return (int)(Math.random()*range) - min;
    }
}
