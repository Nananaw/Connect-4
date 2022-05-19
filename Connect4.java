import java.util.Scanner;

public class Connect4
{
  private char[][] board;
  private char player;
  private char computer;
  private int maxDepth;
  
  public Connect4()
  {
    board = new char[7][7];
    newBoard();
    player = 'A';
    computer = 'B';
    maxDepth = 5;
  }
  
  public void newBoard()
  {
    for(int i=0; i<board.length; i++)
      for(int j=0; j<board[0].length; j++)
      board[i][j] = ' ';
  }
  
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
  
  public void alter(int column, char a)
  {
    if(!alterable(column))
    {
      board[0][column]=a;
      return;
    }
    for(int i=board.length-1; i>=0; i--)
    {
      if(board[i][column] == ' ')
      {
        if(a == ' ')
        {
          if(i==6)
            board[6][column] = ' ';
          else
            board[i+1][column] = ' ';
        }
        else
        {
          board[i][column] = a;
          return;
        }
        
      }
    }
  }
  
  public int winner()
  {
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
    return 0;
  }
  
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
        alter(i, ' ');
      }
    }
    alter(column, computer);
  }
  
  public int guess()
  {
    return 1;
  }
  
  public int minimax(int depth, boolean maximizingPlayer, int alpha, int beta)
  {
    if(depth >= maxDepth)
      return guess();
    if(winner()!=0 || isFull())
      return winner();
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
          alter(i, ' ');
          if (beta <=alpha)
            break;
        }
      }
      return bestValue;
    }
    
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
          alter(i, ' ');
          if (beta <= alpha)
            break;
        }
      }
      return bestValue;
    }
  }
}