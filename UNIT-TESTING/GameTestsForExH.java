import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part H of the exercise, in which
 * we add the crash countdown functionality.
 * 
 * The tests need to know the value of the crash countdown.  Ideally, the
 * tests would be able to ask the Game class what the current crashcountdown
 * value is, and even set it to some suitable value.  At present, neither of
 * these things are possible.  The crash countdown value is embedded in the
 * Game class, in a private field.  So, we have had to resort to making the
 * assumption here that the crash countdown is 5.  If you want to change
 * that, you'll need to make lots of changes throughout this class.
 * 
 * We need to test the following behaviours:
 * 
 * - the fact that a snake does not actually crash while the crash countdown
 * is counting down
 * - the eventual occurence of an actual crash when the countdown reaches the
 * end
 * - the resetting of the countdown when the snake exits a crash situation
 * - the resetting of the countdown when the cheat function is invoked
 * 
 * 
 * (c) University of Manchester, 2016.
 * 
 * @author Suzanne M. Embury
 * @version 1.0.0
 */

public class GameTestsForExH {
  
  private static final int LARGE_GRID_SIZE  = 20;

  private Game  game;
  private Coord lowerLeftCorner = new Coord(0, LARGE_GRID_SIZE - 1);
  private Coord centre          = new Coord(LARGE_GRID_SIZE / 2,
                                                LARGE_GRID_SIZE / 2);


  @Before
  public void createAGame()
  {
    game = new Game(LARGE_GRID_SIZE);
  }


  // Tests for crash countdown when snake crashes into the wall

  @Test
  public void shouldNotMakeSnakeBloodyWhenFirstCrashingIntoWall()
  {
    Coord head = placeSnakeReadyToCrashIntoWall(game, Direction.SOUTH);
      
    // Move the snake to initiate the crash countdown
    game.move(1);
    
    assertThat(snakeCellIsBloody(game, head), is(false));
  }
  
  
  @Test
  public void shouldNotMakeSnakeBloodyWhenOnLastStageOfCrashCountdownAfterCrashIntoWall()
  {
    Coord head = placeSnakeReadyToCrashIntoWall(game, Direction.SOUTH);
    game.move(1); // Crash countdown = 4
    game.move(1); // Crash countdown = 3
    game.move(1); // Crash countdown = 2
    game.move(1); // Crash countdown = 1

    assertThat(snakeCellIsBloody(game, head), is(false));
  }
  
  
  @Test
  public void shouldMakeSnakeBloodyWhenCrashCountdownHasRunOutAfterCrashIntoWall()
  {
    Coord head = placeSnakeReadyToCrashIntoWall(game, Direction.SOUTH);
    game.move(1); // Crash countdown = 4
    game.move(1); // Crash countdown = 3
    game.move(1); // Crash countdown = 2
    game.move(1); // Crash countdown = 1
    game.move(1); // Crash countdown = 0

    assertThat(snakeCellIsBloody(game, head), is(true));
  }
  
  
  @Test
  public void shouldNotMakeSnakeBloodyWhenPlayerMovesAwayFromCrashBeforeLastStageOfCrashCountdownAfterCrashIntoWall()
  {
    Coord head = placeSnakeReadyToCrashIntoWall(game, Direction.SOUTH);
    game.move(1); // Crash countdown = 4
    game.move(1); // Crash countdown = 3
    game.move(1); // Crash countdown = 2
    game.move(1); // Crash countdown = 1
    game.setSnakeDirection(Direction.EAST);
    game.move(1); // Crash countdown = 0

    assertThat(snakeCellIsBloody(game, head), is(false));
  }
  
  
  @Test
  public void shouldRestartCrashCountdownWhenPlayerMovesAwayFromCrashBeforeLastStageOfCrashCountdownAfterCrashIntoWall()
  {
    Coord head = placeSnakeReadyToCrashIntoWall(game, Direction.SOUTH);
    game.move(1); // Crash countdown = 4 after this move
    game.move(1); // Crash countdown = 3 after this move
    game.move(1); // Crash countdown = 2 after this move
    game.move(1); // Crash countdown = 1 after this move
    game.setSnakeDirection(Direction.EAST);  // Move out of crash situation
    game.move(1); // Crash countdown = 5 after this move
    
    game.setSnakeDirection(Direction.SOUTH); // Move back into crash situation
    game.move(1); // Crash countdown = 4 after this move
    head = TestGame.getLocationOfHead(game);
    assertThat(snakeCellIsBloody(game, head), is(false));
    
    game.move(1); // Crash countdown = 3 after this move
    assertThat(snakeCellIsBloody(game, head), is(false));
   
    game.move(1); // Crash countdown = 2 after this move
    assertThat(snakeCellIsBloody(game, head), is(false));
   
    game.move(1); // Crash countdown = 1 after this move
    assertThat(snakeCellIsBloody(game, head), is(false));
    
    game.move(1); // Crash countdown = 0 after this move
    assertThat(snakeCellIsBloody(game, head), is(true));
  }
  
  
  
  // Tests for crash countdown handling when the snake crashes into itself
  // (Just a selection of the above cases are repeated for this context)
  
  @Test
  public void shouldNotMakeSnakeBloodyWhenFirstCrashingIntoItself()
  {
    Coord head = placeSnakeReadyToCrashIntoItself(game, Direction.NORTH);
    game.move(1);
    assertThat(snakeCellIsBloody(game, head), is(false));
  }
  
  
  @Test
  public void shouldNotMakeSnakeBloodyWhenCrashCountdownOnLastStageAfterCrashingIntoItself()
  {
    Coord head = placeSnakeReadyToCrashIntoItself(game, Direction.NORTH);
    game.move(1); // Countdown = 4
    game.move(1); // Countdown = 3
    game.move(1); // Countdown = 2
    game.move(1); // Countdown = 1

    assertThat(snakeCellIsBloody(game, head), is(false));
  }
  
  
  @Test
  public void shouldMakeSnakeBloodyWhenCrashCountdownRunsDownAfterCrashingIntoItself()
  {
    Coord head = placeSnakeReadyToCrashIntoItself(game, Direction.NORTH);
    game.move(1); // Countdown = 4
    game.move(1); // Countdown = 3
    game.move(1); // Countdown = 2
    game.move(1); // Countdown = 1
    game.move(1); // Countdown = 0

    assertThat(snakeCellIsBloody(game, head), is(true));
  }
  
  
  
  // Tests for crash countdown handling when the snake crashes into itself
  // (Just a selection of the above cases are repeated for this context)
  
  @Test
  public void shouldNotMakeSnakeBloodyWhenFirstCrashingIntoATree()
  {
    Coord head = placeSnakeReadyToCrashIntoATree(game, Direction.NORTH);
    game.move(1);
    assertThat(snakeCellIsBloody(game, head), is(false));
  }
  
  
  @Test
  public void shouldNotMakeSnakeBloodyWhenCrashCountdownOnLastStageAfterCrashingIntoATree()
  {
    Coord head = placeSnakeReadyToCrashIntoATree(game, Direction.NORTH);
    game.move(1); // Countdown = 4
    game.move(1); // Countdown = 3
    game.move(1); // Countdown = 2
    game.move(1); // Countdown = 1

    assertThat(snakeCellIsBloody(game, head), is(false));
  }
  
  
  @Test
  public void shouldMakeSnakeBloodyWhenCrashCountdownRunsDownAfterCrashingIntoATree()
  {
    Coord head = placeSnakeReadyToCrashIntoATree(game, Direction.NORTH);
    game.move(1); // Countdown = 4
    game.move(1); // Countdown = 3
    game.move(1); // Countdown = 2
    game.move(1); // Countdown = 1
    game.move(1); // Countdown = 0

    assertThat(snakeCellIsBloody(game, head), is(true));
  }
  
  
  
  
  // Helper methods

  private Coord placeSnakeReadyToCrashIntoWall(Game game, int direction)
  {
    Coord head = lowerLeftCorner;
    TestGame.placeStraightSnakeWithHeadAt(game, head, 5,
        direction, Direction.opposite(direction));
    return head;
  }
  
  
  private Coord placeSnakeReadyToCrashIntoItself(Game game, int direction)
  {
    Coord head = centre;
    int initialDirection = Direction.rightTurn(direction);
    TestGame.placeStraightSnakeWithHeadAt(game, head, 6,
        initialDirection, Direction.opposite(initialDirection));
    TestGame.snakeTurnsRight(game);
    game.move(0);
    TestGame.snakeTurnsRight(game);
    game.move(0);
    TestGame.snakeTurnsRight(game);
    
    return TestGame.getLocationOfHead(game);
  }
  
  
  private Coord placeSnakeReadyToCrashIntoATree(Game game, int direction)
  {
    Coord head = centre;
    TestGame.placeStraightSnakeWithHeadAt(game, head, 4,
        direction, Direction.opposite(direction));
    
    Coord treeLocation = head.neighbouringLocation(direction);
    TestGame.putTreeAt(game, treeLocation);

    return head;
  }
  
  
  private Boolean snakeCellIsBloody(Game game, Coord cellLocation)
  {
    return game.getGridCell(cellLocation.x, cellLocation.y).isSnakeBloody();
  }



}
