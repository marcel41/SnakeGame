import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 *
 * This class contains test cases relating to part F of the exercise, in which
 * we add the cheat functionality.
 *
 * As we progress through the more complex snake game functionalities, so
 * (unsurprisingly) the test code is getting more complex.  For the tests of
 * the cheat functionality, we need to set up a game grid that has a snake
 * with a known score that has crashed in some way.  We can't directly
 * set the state of the game grid, so we have to use the public methods on
 * Game that are available to us to create the situations we want to test.
 *
 * In particular, we need to set up a snake with a known score.  Since we
 * can't set the score directly from outside the Game class, we need to
 * manufacture a situation in which the snake acquires a known score, by
 * eating some food with a fixed move value.
 *
 * The game starts off with a score of zero, and we want to change this to
 * some known value after one piece of food has been eaten.  In other words,
 * we need to ensure that:
 *
 *  moveValue * ((snakeLength / (gridSize * gridSize / 36 + 1)) + 1) = 100
 *
 * If we have a snake that is shorter than (gridSize^2 / 36 + 1) then the
 * middle component of this score will be 0, and the overall value added to
 * the score will be moveValue.
 *
 * So, to set up the game, we need to choose appropriate values for the grid
 * size, the snake length and move value.  Then we get the snake to eat a
 * piece of food that we place in front of it.
 *
 *
 * This test case illustrates the kinds of hoops we have to jump through when
 * code is written long before the tests for the code are written.  The code
 * was not written with testability in mind, and therefore it does not provide
 * us with the hooks we need to easily control the state of the snake game and
 * set up particular game situations for testing.  This is not a criticism of
 * the Snake Game code.  It is a general feature of even well-written code
 * that was created without accompanying automated test code.  In later course
 * units, we'll explore how this property can be exploited to help us write
 * highly readable and maintainable code from the get-go.  For now, it is enough
 * just to experience the challenges of writing tests for code that was not
 * written with testing in mind from the outset.
 *
 * (c) University of Manchester, 2016.
 *
 * @author Suzanne M. Embury
 * @version 1.0.0
 */

public class GameTestsForExF {

  private static final int GRID_SIZE = 36;
  private static final int SNAKE_LENGTH = GRID_SIZE - 1;
	private static final int MOVE_VALUE = 100;

	private Game game;


	@Before
	public void createAGameWithAKnownScoreAndASnakeAboutToCrash() {
    game = new Game(GRID_SIZE);

    Coord head = new Coord(GRID_SIZE - 2, 0);
    int snakeOutDirection = Direction.EAST;
    int snakeInDirection = Direction.opposite(snakeOutDirection);

    TestGame.placeStraightSnakeWithHeadAt(game, head, SNAKE_LENGTH,
        snakeOutDirection, snakeInDirection);

    TestGame.putFoodAt(game, head.neighbouringLocation(snakeOutDirection));
    game.move(MOVE_VALUE);
	}


	@Test
	public void shouldClearBloodAndHalveTheScoreWhenSnakeHasCrashedIntoWallAndCheatFunctionInvoked() {
	  // Run a pre-condition check that the score is what we expect it to be.
	  // (There's no point us running the test if there is something wrong with
	  // our set up that means the score isn't what we expect.)
	  assertThat(game.getScore(), is(100));

    // Now we have set up the game ready to run the test.  The next step
    // is to run the function that is under test.  We make the snake crash
    // into the wall, and then invoke the cheat function
    game.move(MOVE_VALUE);
	  game.cheat();

	  // Finally, check that the changes we expected to see have occurred
	  assertThat(game.getScore(), is(50));
	  assertThat(game.getScoreMessage(), is("you are lucky, you have escaped from death by 4 moves"));
	  checkThatNoCellIsBloody();
	}


  @Test
  public void shouldClearBloodAndHalveTheScoreWhenSnakeHasCrashedIntoItsBodySeveralTimesAndCheatFunctionInvoked() {
    // Make the snake crash into itself in several places.  We have to do this
    // in just a few moves, so that the crash countdown doesn't run down before
    // we're done.  (This would be much easier if we could control the crash
    // countdown feature from outside the Game class.)
    TestGame.snakeTurnsRight(game);
    game.move(0);
    TestGame.snakeTurnsRight(game);
    game.move(0); // First crash into body
    TestGame.snakeTurnsRight(game);
    game.move(0); // Second crash into body - should be two bloody cells now

    game.cheat();

    assertThat(game.getScore(), is(50));
    assertThat(game.getScoreMessage(), is("you are lucky, you have escaped from death by 4 moves"));
    checkThatNoCellIsBloody();
  }


  @Test
  public void shouldHalveScoreWhenCheatFunctionInvokedOutsideOfCrashSituation() {
    // Turn on the cheat function without first making the snake crash
    game.cheat();

    // Finally, check that the changes we expected to see have occurred
    assertThat(game.getScore(), is(50));
    assertThat(game.getScoreMessage(), is("You lost 50"));
    checkThatNoCellIsBloody();
  }


  @Test
  public void shouldQuarterTheScoreWhenSnakeHasCrashedAndCheatFunctionInvokedTwice() {
    // Run a pre-condition check that the score is what we expect it to be.
    // (There's no point us running the test if there is something wrong with
    // our set up that means the score isn't what we expect.)
    assertThat(game.getScore(), is(100));

    // Now we have set up the game ready to run the test.  The next step
    // is to run the function that is under test.  We make the snake crash
    // into the wall, and then invoke the cheat function
    game.move(MOVE_VALUE);
    game.cheat();
    game.cheat();

    // Finally, check that the changes we expected to see have occurred
    assertThat(game.getScore(), is(25));
    assertThat(game.getScoreMessage(), is("You lost 25"));
    checkThatNoCellIsBloody();
  }


  /* Finally, two tests to check what happens at the edge cases, when the
   * score is 0 or 1, and the cheat function is invoked.
   *
   * Strictly speaking, these tests should be in another test class, as it does
   * not use the common fixture created in the @Before method.  This is
   * inefficient, and perhaps confusing for students.  I preferred to risk
   * this than to add more confusion by breaking with the "one test class
   * per exercise" organisation of the whole suite.
   */

  @Test
  public void shouldLeaveZeroScoreUnchangedWhenCheatFunctionInvoked() {
    game = new Game(GRID_SIZE);

    Coord head = new Coord(GRID_SIZE - 2, 0);
    TestGame.placeStraightSnakeWithHeadAt(game, head, SNAKE_LENGTH,
        Direction.EAST, Direction.WEST);

    game.cheat();

    assertThat(game.getScore(), is(0));
  }


  @Test
  public void shouldMakeScoreZeroWhenScoreIsOneAndCheatFunctionInvoked() {
    game = new Game(GRID_SIZE);

    Coord head = new Coord(GRID_SIZE - 2, 0);
    TestGame.placeStraightSnakeWithHeadAt(game, head, SNAKE_LENGTH,
        Direction.EAST, Direction.WEST);

    TestGame.putFoodAt(game, head.neighbouringLocation(Direction.EAST));
    game.move(1);

    game.cheat();

    assertThat(game.getScore(), is(0));
  }




	// Helper Assertion Methods

  private void checkThatNoCellIsBloody()
  {
    for (int x = 0; x < game.getGridSize(); x++)
      for (int y = 0; y < game.getGridSize(); y++)
        assertThat(game.getGridCell(x, y).isSnakeBloody(), is(false));
  }

}
