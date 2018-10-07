import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part E-1 of the exercise, in which
 * we begin to add the functionality to move the snake on each game tick.
 * 
 * For the first part of exercise E, we are just putting in place the main
 * driver method, with stubs carrying out most of the work at this stage. So,
 * there is not much that can sensibly be tested for this exercise. Therefore,
 * this test class just contains a test that a snake with a bloody head cannot
 * move. We won't be able to really use this test until the movement code is in
 * place, but we can run it at this stage, and it should pass when we have
 * completed the work for E1.
 * 
 * (c) University of Manchester, 2016.
 * 
 * @author Suzanne M. Embury
 * @version February 2016
 */

public class GameTestsForExE1
{
  @Test
  public void shouldNotMoveIfSnakeHeadIsBloody()
  {
    // Place the snake in the middle of a grid with plenty of room around it
    int largeGridSize = 20;
    Game game = new Game(largeGridSize);
    
    int centreX = largeGridSize / 2;
    int centreY = largeGridSize / 2;
    int snakeLength = largeGridSize / 4;
    game.setInitialGameState(centreX, centreY, snakeLength, Direction.EAST);

    Coord originalHeadLocation = TestGame.getLocationOfHead(game);
    makeSnakeHeadBloody(game, originalHeadLocation);

    game.move(1);

    // Work out where the head is after the move. (We should really
    // check that all the snake cells are in the same place, but that
    // would really clutter up the test code. So, I'm going to just check
    // that the head hasn't moved for now.)
    Coord newHeadLocation = TestGame.getLocationOfHead(game);
    assertThat(newHeadLocation.x, is(originalHeadLocation.x));
    assertThat(newHeadLocation.y, is(originalHeadLocation.y));
  }



 
  // Helper methods

  private void makeSnakeHeadBloody(Game game, Coord originalHeadLocation)
  {
    Cell head = game.getGridCell(originalHeadLocation.x, originalHeadLocation.y);
    head.setSnakeBloody(true);
  }

}
