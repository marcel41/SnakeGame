import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 *
 * This class contains test cases relating to part E-4 of the exercise, in which
 * we add the functionality to check for crashes when the snake moves.
 *
 * As with the earlier movement functionality, there are a large number of cases
 * that we could potentially cover. We have to select the most useful test
 * cases, to keep the size of the test suite manageable, while still offering
 * good protection against regression.
 *
 * We have to consider crashes against the wall and crashes into the snake.
 *
 * For crashes against the wall, I have checked for crashes at each corner of
 * the grid, from 3 directions, in the hope that if these edge cases are handled
 * correctly, then the more general cases will be too. This gives a total of 12
 * test cases.
 *
 * For crashes against the snake, I have given one test case for crashes into
 * the snake in each of the four directions for both tail and body elements.
 * This gives a further 8 test cases, making this quite a lengthy test class
 * (and therefore a bit smelly because of it). In a production system, I'd
 * probably have one test class for testing crashes against the wall, and a
 * separate class for testing crashes into the snake.
 *
 * (c) University of Manchester, 2016
 *
 * @author Suzanne M. Embury
 * @version 1.0.0
 */

public class GameTestsForExE4
{
  private static final int LARGE_GRID_SIZE  = 20;
  private static final int MOVE_VALUE       = 1;
  private static final int CRASH_COUNTDOWN  = 5;

  private Game  game;
  private Coord upperLeftCorner  = new Coord(0, 0);
  private Coord lowerLeftCorner  = new Coord(0, LARGE_GRID_SIZE - 1);
  private Coord upperRightCorner = new Coord(LARGE_GRID_SIZE - 1, 0);
  private Coord lowerRightCorner = new Coord(LARGE_GRID_SIZE - 1,
                                                LARGE_GRID_SIZE - 1);
  private Coord centre           = new Coord(LARGE_GRID_SIZE / 2,
                                                LARGE_GRID_SIZE / 2);


  @Before
  public void createAGame()
  {
    game = new Game(LARGE_GRID_SIZE);
  }


  // Tests for crashes into the wall

  @Test
  public void shouldDetectCrashesStraightIntoTheNorthWall()
  {
    checkThatCrashIntoWallIsDetected(upperLeftCorner, Direction.NORTH,
        Direction.SOUTH);
  }


  @Test
  public void shouldDetectCrashesStraightIntoTheEastWall()
  {
    checkThatCrashIntoWallIsDetected(upperRightCorner, Direction.EAST,
        Direction.WEST);
  }


  @Test
  public void shouldDetectCrashesStraightIntoTheSouthWall()
  {
    checkThatCrashIntoWallIsDetected(lowerRightCorner, Direction.SOUTH,
        Direction.NORTH);
  }


  @Test
  public void shouldDetectCrashesStraightIntoTheWestWall()
  {
    checkThatCrashIntoWallIsDetected(lowerLeftCorner, Direction.WEST,
        Direction.EAST);
  }


  @Test
  public void shouldDetectCrashesIntoTheNorthWallWhenTurningRight()
  {
    checkThatCrashIntoWallIsDetected(upperLeftCorner, Direction.NORTH,
        Direction.EAST);
  }


  @Test
  public void shouldDetectCrashesIntoTheEastWallWhenTurningRight()
  {
    checkThatCrashIntoWallIsDetected(upperRightCorner, Direction.EAST,
        Direction.SOUTH);
  }


  @Test
  public void shouldDetectCrashesIntoTheSouthWallWhenTurningRight()
  {
    checkThatCrashIntoWallIsDetected(lowerRightCorner, Direction.SOUTH,
        Direction.WEST);
  }


  @Test
  public void shouldDetectCrashesIntoTheWestWallWhenTurningRight()
  {
    checkThatCrashIntoWallIsDetected(lowerLeftCorner, Direction.WEST,
        Direction.NORTH);
  }


  @Test
  public void shouldDetectCrashesIntoTheNorthWallWhenTurningLeft()
  {
    checkThatCrashIntoWallIsDetected(upperRightCorner, Direction.NORTH,
        Direction.WEST);
  }


  @Test
  public void shouldDetectCrashesIntoTheEastWallWhenTurningLeft()
  {
    checkThatCrashIntoWallIsDetected(lowerRightCorner, Direction.EAST,
        Direction.NORTH);
  }


  @Test
  public void shouldDetectCrashesIntoTheSouthWallWhenTurningLeft()
  {
    checkThatCrashIntoWallIsDetected(lowerLeftCorner, Direction.SOUTH,
        Direction.EAST);
  }


  @Test
  public void shouldDetectCrashesIntoTheWestWallWhenTurningLeft()
  {
    checkThatCrashIntoWallIsDetected(upperLeftCorner, Direction.WEST,
        Direction.SOUTH);
  }


  // Tests for crashes into the snake

  @Test
  public void shouldDetectACrashNorthIntoTheSnakeBody()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.NORTH,
        Cell.SNAKE_BODY);
  }


  @Test
  public void shouldDetectACrashEastIntoTheSnakeBody()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.EAST,
        Cell.SNAKE_BODY);
  }


  @Test
  public void shouldDetectACrashSouthIntoTheSnakeBody()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.SOUTH,
        Cell.SNAKE_BODY);
  }


  @Test
  public void shouldDetectACrashWestIntoTheSnakeBody()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.WEST,
        Cell.SNAKE_BODY);
  }


  @Test
  public void shouldDetectACrashNorthIntoTheSnakeTail()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.NORTH,
        Cell.SNAKE_TAIL);
  }


  @Test
  public void shouldDetectACrashEastIntoTheSnakeTail()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.EAST,
        Cell.SNAKE_TAIL);
  }


  @Test
  public void shouldDetectACrashSouthIntoTheSnakeTail()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.SOUTH,
        Cell.SNAKE_TAIL);
  }


  @Test
  public void shouldDetectACrashWestIntoTheSnakeTail()
  {
    checkThatCrashIntoSnakeIsDetectedWhenTravelling(Direction.WEST,
        Cell.SNAKE_TAIL);
  }


  // General parametrised test cases

  private void checkThatCrashIntoWallIsDetected(Coord head,
      int outDirection, int inDirection)
  {
    TestGame.placeStraightSnakeWithHeadAt(game, head, LARGE_GRID_SIZE,
        outDirection, inDirection);

    TestGame.moveTheSnakeUntilItCrashes(game, CRASH_COUNTDOWN, MOVE_VALUE);

    checkThatHeadHasntMoved(head);
    checkThatCellIsBloody(head);
    checkThatScoreMessageDescribesACrashIntoTheWall();
  }


  private void checkThatCrashIntoSnakeIsDetectedWhenTravelling(int direction,
      int cellType)
  {
    int snakeLength = selectSnakeLengthBasedOnTypeOfCellToCrashInto(cellType);

    Coord tempHead = centre;
    int tempOutDirection = Direction.rightTurn(direction);
    int tempInDirection = Direction.opposite(tempOutDirection);
    TestGame.placeStraightSnakeWithHeadAt(game, tempHead, snakeLength,
        tempOutDirection, tempInDirection);

    Coord bumpedBody = makeTheSnakeBumpIntoItsBody();
    Coord head = TestGame.getLocationOfHead(game);

    TestGame.moveTheSnakeUntilItCrashes(game, CRASH_COUNTDOWN, MOVE_VALUE);

    checkThatHeadHasntMoved(head);
    checkThatCellIsBloody(head);
    checkThatCellIsBloody(bumpedBody);
    checkThatScoreMessageDescribesACrashIntoTheSnake();
  }


  private void checkThatHeadHasntMoved(Coord originalHeadLocation)
  {
    Coord newHeadLocation = TestGame.getLocationOfHead(game);

    String failMsg = "Head cell is at <" + newHeadLocation.x + "," +
        newHeadLocation.y + ">, " +
        "which is not where we expected it to be.  ";
    assertThat(failMsg + "X-coord:", newHeadLocation.x,
        is(originalHeadLocation.x));
    assertThat(failMsg + "Y-coord:", newHeadLocation.y,
        is(originalHeadLocation.y));
  }


  private void checkThatCellIsBloody(Coord cellCoords)
  {
    String failMsg = "Cell <" + cellCoords.x + "," + cellCoords.y +
        "> is not bloody after crash.";
    assertThat(failMsg, game.getGridCell(cellCoords.x, cellCoords.y)
        .isSnakeBloody());
  }


  private void checkThatScoreMessageDescribesACrashIntoTheWall()
  {
    assertThat(game.getScoreMessage(),
        is("Hey!! you can not go away from this field"));
  }


  private void checkThatScoreMessageDescribesACrashIntoTheSnake()
  {
    assertThat(game.getScoreMessage(),
        is("NOO!!!, YOU CAN NOT EAT YOURSELF"));
  }


  // Helper methods

  private int selectSnakeLengthBasedOnTypeOfCellToCrashInto(int cellType)
  {
    if (cellType == Cell.SNAKE_BODY)
      return 5;
    if (cellType == Cell.SNAKE_TAIL)
      return 4;
    return 0;
  }


  public Coord makeTheSnakeBumpIntoItsBody()
  {
    TestGame.snakeTurnsRight(game);
    game.move(MOVE_VALUE);
    TestGame.snakeTurnsRight(game);
    game.move(MOVE_VALUE);
    TestGame.snakeTurnsRight(game);

    Coord head = TestGame.getLocationOfHead(game);
    return TestGame.locationThatCellIsPointingTowards(game, head);
  }

}
