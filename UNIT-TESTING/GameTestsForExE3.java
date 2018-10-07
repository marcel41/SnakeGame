import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part E-3 of the exercise, in which
 * we add the functionality to move the snake tail.
 * 
 * The tail can move in one of four directions, either in a straight line or
 * turning left or right. Therefore, as with E-2, we have twelve cases to cover.
 * 
 * (c) University of Manchester
 * 
 * @author Suzanne M. Embury
 * @version 1.0.0
 */

public class GameTestsForExE3
{
  private static final int LARGE_GRID_SIZE = 20;
  private static final int MOVE_VALUE      = 1;

  private Game             game;


  @Before
  public void createAGame()
  {
    game = new Game(LARGE_GRID_SIZE);
  }


  // Tests in which the snake moves forward
  
  @Test
  public void shouldMoveForwardFacingSnakeTailNorth()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.NORTH, Direction.SOUTH, new Coord(10, 9));
  }
  
  
  @Test
  public void shouldMoveForwardFacingSnakeTailEast()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.EAST, Direction.WEST, new Coord(11, 10));
  }
  
  @Test
  public void shouldMoveForwardFacingSnakeTailSouth()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.SOUTH, Direction.NORTH, new Coord(10, 11));
  }
  
  
  @Test
  public void shouldMoveForwardFacingSnakeTailWest()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.WEST, Direction.EAST, new Coord(9, 10));
  }
  
  
  
  // Tests in which the snake is turning left
  
  @Test
  public void shouldMoveLeftTurningSnakeTailNorth()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.NORTH, Direction.WEST, new Coord(10, 9));
  }


  @Test
  public void shouldMoveLeftTurningSnakeTailEast()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.EAST, Direction.NORTH, new Coord(11, 10));
  }


  @Test
  public void shouldMoveLeftTurningSnakeTailSouth()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.SOUTH, Direction.EAST, new Coord(10, 11));
  }

  
  @Test
  public void shouldMoveLeftTurningSnakeTailWest()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.WEST, Direction.SOUTH, new Coord(9, 10));
  }



  // Tests in which the snake is turning right

  @Test
  public void shouldMoveRightTurningSnakeTailNorth()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.NORTH, Direction.EAST, new Coord(10, 9));
  }


  @Test
  public void shouldMoveRightTurningSnakeTailEast()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.EAST, Direction.SOUTH, new Coord(11, 10));
  }


  @Test
  public void shouldMoveRightTurningSnakeTailSouth()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.SOUTH, Direction.WEST, new Coord(10, 11));
  }

  
  @Test
  public void shouldMoveRightTurningSnakeTailWest()
  {
    checkThatSnakeTailMovesCorrectly(new Coord(10, 10), 
        Direction.WEST, Direction.NORTH, new Coord(9, 10));
  }

  
  
  // General test case method
  
  private void checkThatSnakeTailMovesCorrectly(Coord initialTailLocation,
      int outDirection, int inDirection, Coord newTailLocation)
  {
    // Getting the grid to a state where the snake's tail is about to
    // turn a corner is quite tricky when we can only use the public
    // methods on the Game class.  We have to place a straight snake
    // with its head where we want the tail to eventually be, set the
    // snake direction, and then move as many times as the length of the
    // snake.  At this point, the tail will be where we expect it to be.

    placeSnakeWithTailAt(game, initialTailLocation, outDirection, inDirection);
    
    game.move(MOVE_VALUE);

    int newOutDirection = outDirection;
    int newInDirection  = Direction.opposite(outDirection);
    checkThatTheNewLocationIsATailCell(newTailLocation,
        newOutDirection, newInDirection);
    checkThatTheOldLocationIsAClearCell(initialTailLocation);
    checkThatNoOtherCellsAreTailCells(newTailLocation);
  }

  
  

  // Helper methods for placing the snake where we need it to be

  private void placeSnakeWithTailAt(Game game, Coord tailLocation,
      int outDirection, int inDirection)
  {
    int initialOutDirection = Direction.opposite(inDirection);
    int initialInDirection  = inDirection;
    int snakeLength = 4;
    TestGame.placeStraightSnakeWithHeadAt(game, tailLocation, snakeLength,
        initialOutDirection, initialInDirection);
    
    game.setSnakeDirection(outDirection);
    for (int i = 0; i < snakeLength - 1; i++)
      game.move(1);
  }




  // Helper methods for checking that the moved tail is where we expect it to be

  private void checkThatTheNewLocationIsATailCell(Coord tailCoords,
      int expectedOut, int expectedIn)
  {
    checkThatCellIsExpectedType(tailCoords, Cell.SNAKE_TAIL);
    checkThatCellHasExpectedInDirection(tailCoords, expectedIn);
    checkThatCellHasExpectedOutDirection(tailCoords, expectedOut);
  }


  private void checkThatTheOldLocationIsAClearCell(Coord tailCoords)
  {
    checkThatCellIsExpectedType(tailCoords, Cell.CLEAR);
    // checkThatCellHasExpectedInDirection(tailCoords, Direction.NONE);
    // checkThatCellHasExpectedOutDirection(tailCoords, Direction.NONE);
  }


  private void checkThatCellIsExpectedType(Coord cellCoords, int expectedType)
  {
    int x = cellCoords.x;
    int y = cellCoords.y;
    String failMsg = "Cell at <" + x + "," + y + "> has unexpected type";
    
    Cell cell = game.getGridCell(x, y);
    assertThat(failMsg, cell.getType(), is(expectedType));
  }
  
  
  private void checkThatCellHasExpectedInDirection(Coord cellCoords,
      int expectedDirection)
  {
    int x = cellCoords.x;
    int y = cellCoords.y;
    String failMsg = "Cell at <" + x + "," + y + "> has unexpected in direction";
    
    Cell cell = game.getGridCell(x, y);
    assertThat(failMsg, cell.getSnakeInDirection(), is(expectedDirection));
  }
  

  private void checkThatCellHasExpectedOutDirection(Coord cellCoords,
      int expectedDirection)
  {
    int x = cellCoords.x;
    int y = cellCoords.y;
    String failMsg = "Cell at <" + x + "," + y + "> has unexpected out direction";
    
    Cell cell = game.getGridCell(x, y);
    assertThat(failMsg, cell.getSnakeOutDirection(), is(expectedDirection));
  }


  private void checkThatNoOtherCellsAreTailCells(Coord newHead)
  {
    for (int x = 0; x < LARGE_GRID_SIZE; x++)
      for (int y = 0; y < LARGE_GRID_SIZE; y++)
         if (!sameLocation(x, y, newHead.x, newHead.y))
         {
           String failMsg = "Cell at <" + x + "," + y + "> not the expected type.";
           assertThat(failMsg,
               game.getGridCell(x, y).getType(), is(not(equalTo(Cell.SNAKE_TAIL))));
         }
  }


  private boolean sameLocation(int x1, int y1, int x2, int y2)
  {
    return x1 == x2 && y1 == y2;
  }


}
