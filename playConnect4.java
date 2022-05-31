public class playConnect4
{
  public static void main(String[] args)
  {
    Connect4 game = new Connect4();
    game.printBoard();
    while(game.winner()==0 && !game.isFull())
    {
      game.alterBoard();
      if(game.winner() != 0 || game.isFull())
      {
        game.printBoard();
        break;
      }
      game.computerAlter();
      game.printBoard();
    }
    game.printWinner();
  }
}