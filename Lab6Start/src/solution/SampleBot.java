
package solution;

import battleship.BattleShip;
import battleship.CellState;
import java.awt.Point;
import java.util.Random;
import java.util.Stack;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once 
 * a ship is hit.
 * 
 * @author mark.yendt
 */
public class SampleBot
{
  private int gameSize;
  private BattleShip battleShip;
  private Random random;
  Point shot = null;
  private CellState a[][] ;
  Stack hitShot;
  /**
   * Constructor keeps a copy of the BattleShip instance
   * @param b previously created battleship instance - should be a new game
   */
  public SampleBot(BattleShip b)
  {
    battleShip = b;
    gameSize = b.BOARDSIZE;
    random = new Random();   // Needed for random shooter - not required for more systematic approaches
    a = new CellState[gameSize][gameSize];
    for(int i = 0; i < gameSize; i++){   
        for(int j = 0; j < gameSize; j++){
            a[i][j] = CellState.Empty;
        }
    }
    hitShot = new Stack<>();
  }
  
  /**
   * Create a random shot and calls the battleship shoot method
   * @return true if a Ship is hit, false otherwise
   */
  boolean hit = false;
  public boolean fireShot()
  {
    int sunkShips = battleShip.numberOfShipsSunk();
    if(!hitShot.isEmpty())
    {
        shot = (Point) hitShot.pop();
        //System.out.println("hey");
        hit = battleShip.shoot(shot);
        return hit;
    }
    if(battleShip.numberOfShipsSunk() > sunkShips)
        return hit;
    
    for(int i = 0; i < gameSize; i++){
        for(int j = 0; j < gameSize; j++){
            if(a[i][j] == CellState.Empty){
                shot = new Point(i,j);
                hit = battleShip.shoot(shot);
                if(hit == true){
                    a[i][j] = CellState.Hit;
                    if(j-1>=0 && a[i][j] == CellState.Empty)      
                        hitShot.push(new Point(i,j-1));
                    if(j+1<=gameSize && a[i][j] == CellState.Empty)      
                        hitShot.push(new Point(i,j+1));
                    if(i-1>=0 && a[i][j] == CellState.Empty)      
                        hitShot.push(new Point(i-1,j));
                    if(i+1<=gameSize && a[i][j] == CellState.Empty)      
                        hitShot.push(new Point(i+1,j));
                }
                else{
                    a[i][j] = CellState.Miss;
                    j=j+4;
                }
            }
            
        }
    }
    
    return hit;
  }
  
  public void cellXY(int x, int y)
  {
      
  }
  
}
