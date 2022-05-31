import java.util.Scanner;

//Class for making a connect 4 game
public class Connect4
{
  //board is a 2d matrix of chars
  //have a char for player and char for computer
  //maxdepth is lowest depth for using minimax
  private char[][] board;
  private char player;
  private char computer;
  private int maxDepth;
  
  //creates game with 7x7 board
  //uses A for player and B for computer
  //maxdepth is 5
  public Connect4()
  {
    board = new char[7][7];
    newBoard();
    player = 'A';
    computer = 'B';
    maxDepth = 5;
  }
  
  //clears the board
  public void newBoard()
  {
    for(int i=0; i<board.length; i++)
      for(int j=0; j<board[0].length; j++)
      board[i][j] = ' ';
  }
  
  //prints the board in console
  public void printBoard()
  {
    System.out.println("---------------");
    for(int i=0; i<board.length; i++)
    {
      System.out.print("|");
      for(int j=0; j<board[0].length; j++)
      {
        System.out.print(board[i][j] + "|");
      }
      System.out.println();
      System.out.println("---------------");
    }
  }
  
  //prints the winner of the game
  public void printWinner()
  {
    if(winner() == 1)
    {
      System.out.println("You won!");
    }
    else if(winner() == -1)
    {
      System.out.println("You lost!");
    }
    else
    {
      System.out.println("Tie.");
    }
  }
  
  //returns false if column if invalid
  //returns true if column is valid and can put piece in that column
  //return false otherwise
  public boolean alterable(int column)
  {
    if(column<0 || column>6)
      return false;
    for(int i=board.length-1; i>=0; i--)
    {
      if(board[i][column] == ' ')
        return true;
    }
    return false;
  }
  
  //lets player enter a number to put piece in that column
  //doesn't accept bad input
  public void alterBoard()
  {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter column: ");
    int column = sc.nextInt();
    if(!alterable(column-1))
      alterBoard();
    else
      alter(column-1, player);
  }
  
  //returns true if the board is full
  //false otherwise
  public boolean isFull()
  {
    for(int i=0; i<board.length; i++)
    {
      for(int j=0; j<board[0].length; j++)
      {
        if(board[i][j] == ' ')
          return false;
      }
    }
    return true;
  }
  
  //puts a piece (the char a) into chosen column
  public void alter(int column, char a)
  {
    for(int i=board.length-1; i>=0; i--)
    {
      if(board[i][column] == ' ')
      {
        board[i][column] = a;
        return;
      }
    }
  }
  
  //undoes most recent piece put in chosen column
  public void undo(int column)
  {
    for(int i=0; i<board.length; i++)
    {
      if(board[i][column] != ' ')
      {
        board[i][column] = ' ';
        return;
      }
    }
  }
  
  //returns 1 if player has won
  //returns -1 if player has lost
  //returns 0 if nobody has won
  public int winner()
  {
    //checking for horizontal wins
    for(int i=0; i<board.length; i++)
    {
      for(int j=0; j<board[0].length-3; j++)
      {
        if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3])
        {
          if(board[i][j] == player)
            return 1;
          else if(board[i][j] == computer)
            return -1;
        }
      }
    }
    
    //checking for vertical wins
    for(int i=0; i<board.length-3; i++)
    {
      for(int j=0; j<board[0].length; j++)
      {
        if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j] && board[i][j] == board[i+3][j])
        {
          if(board[i][j] == player)
            return 1;
          else if(board[i][j] == computer)
            return -1;
        }
      }
    }
    
    //checking for diagonal wins
    for(int i=0; i<board.length-3; i++)
    {
      for(int j=0; j<board[0].length-3; j++)
      {
        if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3])
        {
          if(board[i][j] == player)
            return 1;
          else if(board[i][j] == computer)
            return -1;
        }
      }
    }
    
    for(int i=0; i<board.length-3; i++)
    {
      for(int j=3; j<board[0].length; j++)
      {
        if(board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3])
        {
          if(board[i][j] == player)
            return 1;
          else if(board[i][j] == computer)
            return -1;
        }
      }
    }
    
    return 0;
  }
  
  //puts piece into the board for computer
  //choses best move using minimax
  public void computerAlter()
  {
    int bestMove = Integer.MIN_VALUE;
    int column = 0;
    for(int i=0; i<board.length; i++)
    {
      if(alterable(i))
      {
        alter(i, computer);
        if (minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE) > bestMove)
        {
          bestMove = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
          column = i;
        }
        undo(i);
      }
    }
    alter(column, computer);
  }
  
  //gives a guess to which player will win
  //based on current board
  //to be completed
  public int guess()
  {
    return 0;
  }
  
  //performs minimax buts makes a guess at the maximum depth
  //uses alpha beta pruning
  public int minimax(int depth, boolean maximizingPlayer, int alpha, int beta)
  {
    //bases cases
    if(depth >= maxDepth)
      return guess();
    if(winner()!=0 || isFull())
    {
      return (100 * winner()) * (maximizingPlayer ? -1 : 1);
    }
    
    //recursive case for maximizing
    if(maximizingPlayer)
    {
      int bestValue = Integer.MIN_VALUE;
      for (int i=0; i<board.length; i++)
      {
        if(alterable(i))
        {
          alter(i, computer);
          bestValue = Math.max(minimax(depth+1, false, alpha, beta)-depth, bestValue);
          alpha = Math.max(alpha, bestValue);
          undo(i);
          if (beta <=alpha)
            break;
        }
      }
      return bestValue;
    }
    
    //recursive case for minimizing
    else
    {
      int bestValue = Integer.MAX_VALUE;
      for(int i=0; i<board.length; i++)
      {
        if(alterable(i))
        {
          alter(i, player);
          bestValue = Math.min(minimax(depth+1, true, alpha, beta)+depth, bestValue);
          beta = Math.min(beta, bestValue);
          undo(i);
          if (beta <= alpha)
            break;
        }
      }
      return bestValue;
    }
  }
}