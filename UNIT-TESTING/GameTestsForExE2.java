import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part E-2 of the exercise, in which
 * we add the functionality to move the snake head.
 * 
 * We have to check that the head moves correctly in each of the four
 * directions, when going straight ahead and when turning a corner. This gives a
 * total of 12 cases (straight, turn left and turn right for each direction). It
 * is not unreasonable in this case to include them all, so that is what I have
 * done.
 * 
 * Note that in all these test cases there must be a free space in front of the
 * head of the snake before it moves.
 * 
 * (c) University of Mancheste, 2016.
 * 
 * @author Suzanne M. Embury
 * @version February 2016
 */

public class GameTestsForExE2
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
  public void shouldMoveForwardFacingSnakeHeadNorthWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.NORTH, Direction.SOUTH, new Coord(10, 9));
  }
  
  
  @Test
  public void shouldMoveForwardFacingSnakeHeadEastWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.EAST, Direction.WEST, new Coord(11, 10));
  }
  
  @Test
  public void shouldMoveForwardFacingSnakeHeadSouthWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.SOUTH, Direction.NORTH, new Coord(10, 11));
  }
  
  
  @Test
  public void shouldMoveForwardFacingSnakeHeadWestWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.WEST, Direction.EAST, new Coord(9, 10));
  }
  
  
  
  // Tests in which the snake is turning left
  
  @Test
  public void shouldMoveLeftTurningSnakeHeadNorthWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.NORTH, Direction.WEST, new Coord(10, 9));
  }


  @Test
  public void shouldMoveLeftTurningSnakeHeadEastWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.EAST, Direction.NORTH, new Coord(11, 10));
  }


  @Test
  public void shouldMoveLeftTurningSnakeHeadSouthWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.SOUTH, Direction.EAST, new Coord(10, 11));
  }

  
  @Test
  public void shouldMoveLeftTurningSnakeHeadWestWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.WEST, Direction.SOUTH, new Coord(9, 10));
  }



  // Tests in which the snake is turning right

  @Test
  public void shouldMoveRightTurningSnakeHeadNorthWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.NORTH, Direction.EAST, new Coord(10, 9));
  }


  @Test
  public void shouldMoveRightTurningSnakeHeadEastWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.EAST, Direction.SOUTH, new Coord(11, 10));
  }


  @Test
  public void shouldMoveRightTurningSnakeHeadSouthWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.SOUTH, Direction.WEST, new Coord(10, 11));
  }

  
  @Test
  public void shouldMoveRightTurningSnakeHeadWestWhenThereIsSpaceToMove()
  {
    checkThatSnakeHeadMovesCorrectly(new Coord(10, 10), 
        Direction.WEST, Direction.NORTH, new Coord(9, 10));
  }

  
  
  // Helper Assertion Methods
  
  private void checkThatSnakeHeadMovesCorrectly(Coord initialHeadLocation,
      int outDirection, int inDirection, Coord newHeadLocation)
  {
    // Create a snake facing in the desired direction
    int initialOutDirection = Direction.opposite(inDirection);
    int initialInDirection  = inDirection;
    TestGame.placeStraightSnakeWithHeadAt(game, initialHeadLocation, 4,
        initialOutDirection, initialInDirection);
    
    game.setSnakeDirection(outDirection);
    
    // Move the snake
    game.move(MOVE_VALUE);

    // Check that the snake head was moved correctly
    int newOutDirection = outDirection;
    int newInDirection  = Direction.opposite(outDirection);
    checkThatTheNewLocationIsAHeadCell(newHeadLocation,
        newOutDirection, newInDirection);
    checkThatTheOldLocationIsABodyCell(initialHeadLocation,
        outDirection, inDirection);
    checkThatNoOtherCellsAreHeadCells(newHeadLocation);
  }


  private void checkThatTheNewLocationIsAHeadCell(Coord head, int out, int in)
  {
    Cell headCell = game.getGridCell(head.x, head.y);
    String cellMsg = "Cell at <" + head.x + "," + head.y + "> has unexpected ";
    assertThat(cellMsg + "type.",
        headCell.getType(), is(Cell.SNAKE_HEAD));
    assertThat(cellMsg + "out direction.",
        headCell.getSnakeOutDirection(), is(out));
    assertThat(cellMsg + "in direction.",
        headCell.getSnakeInDirection(),  is(in));
  }


  private void checkThatTheOldLocationIsABodyCell(Coord body, int out, int in)
  {
    Cell bodyCell = game.getGridCell(body.x, body.y);
    String cellMsg = "Cell at <" + body.x + "," + body.y + "> has unexpected ";
    assertThat(cellMsg + "type.",
        bodyCell.getType(), is(Cell.SNAKE_BODY));
    assertThat(cellMsg + "out direction.",
        bodyCell.getSnakeOutDirection(), is(out));
    assertThat(cellMsg + "in direction.",
        bodyCell.getSnakeInDirection(),  is(in));
  }


  private void checkThatNoOtherCellsAreHeadCells(Coord newHead)
  {
    for (int x = 0; x < LARGE_GRID_SIZE; x++)
      for (int y = 0; y < LARGE_GRID_SIZE; y++)
         if (!sameLocation(x, y, newHead.x, newHead.y))
         {
           String failMsg = "Cell at <" + x + "," + y + "> not the expected type.";
           assertThat(failMsg,
               game.getGridCell(x, y).getType(), is(not(equalTo(Cell.SNAKE_HEAD))));
         }
  }


  private boolean sameLocation(int x1, int y1, int x2, int y2)
  {
    return x1 == x2 && y1 == y2;
  }

}
