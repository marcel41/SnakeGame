import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part C-1 of the exercise, in which
 * we add the basic initialisation code for the game grid.
 * 
 * This set of test cases is much shorter than it would ordinarily be, since we
 * are told explicitly in the lab instructions that we don't need to check that
 * the snake length is too long for the grid we have specified. If it were not
 * for this assumption, we would expect to have to write several more test cases
 * to check for a number of edge cases regarding snake length and grid size.
 * 
 * Just two test cases for this part of the exercise. The first checks that
 * every cell is either representing a piece of the snake, some food or a clear
 * cell. When you run this test case after implementing just part C-1, then
 * there should be no snake or food cells in the grid. So, in this case, the
 * test case will effectively just be checking that the whole grid is cleared by
 * your initialisation code. But, later, when you've implemented more of part C,
 * the test will still perform a useful and correct function, by checking for
 * the wider range of grid components.
 * 
 * Note the use of the carefully named constant for the grid size. The name
 * tells us know only what quantity the constant represents (a grid size) but
 * also whether the choice of specific value is important for this test case. In
 * this case, we're choosing an arbitrary value, indicating that there is
 * nothing special about grids of size 4 - we just needed to give some value
 * to get the code to compile.
 * 
 * @author Suzanne M. Embury
 * @version February 2016
 */
public class GameTestsForExC1
{
  private static final int ARBITRARY_GRID_SIZE = 4;


  @Test
  public void shouldInitialiseTheGridToContainOnlySnakeFoodAndClearCells()
  {
    Game game = new Game(ARBITRARY_GRID_SIZE);
    game.setInitialGameState(0, 0, ARBITRARY_GRID_SIZE - 1, Direction.EAST);

    for (int x = 0; x < ARBITRARY_GRID_SIZE; x++)
      for (int y = 0; y < ARBITRARY_GRID_SIZE; y++)
      {
        checkThatThisCellIsInitialisedCorrectly(game, x, y);
      }
  }


  @Test
  public void shouldSetScoreToZeroWhenGameIsInitialised()
  {
    Game game = new Game(ARBITRARY_GRID_SIZE);
    game.setInitialGameState(0, 0, ARBITRARY_GRID_SIZE - 1, Direction.EAST);
    assertThat(game.getScore(), is(0));
  }


  // Helper Methods

  // The test for checking that the cells had been initialised to the right
  // kinds of cell was getting a bit long and complicated looking. So, I
  // extracted this "helper method", to make the test a little easier to
  // read.
  private void checkThatThisCellIsInitialisedCorrectly(Game game, int x, int y)
  {
    int actualCellType = game.getGridCell(x, y).getType();
    assertThat(actualCellType, is(anyOf(equalTo(Cell.CLEAR),
        equalTo(Cell.FOOD),
        equalTo(Cell.SNAKE_HEAD),
        equalTo(Cell.SNAKE_BODY),
        equalTo(Cell.SNAKE_TAIL))));
  }

}
