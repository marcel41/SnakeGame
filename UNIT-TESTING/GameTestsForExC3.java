import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part C-3 of the exercise, in which
 * we add the code to place a snake on the grid.
 * 
 * There are 8 possible ways in which your Game class might be asked to place
 * the snake. So, I've created a test case for each one.
 * 
 * Note the way this test class is organised. Each of the 8 test cases sets
 * up some parameters to call a single test method that can verify whether a
 * snake is placed in the correct location, relative to the parameter values
 * given.
 * 
 * We make a lot of use of the "helper" class Coord here, which allows us to
 * pass an X, Y coordinate pair around the code as if it were a single thing.
 * 
 * And this test class shows lots more examples of how we hard-code the values
 * we expect to see in the test cases, in a way that would be very strange in
 * production code.  Writing test code has its own set of rules and conventions,
 * and the handling of literal values is a major difference.  Whereas in
 * production code, having lots of literal values in the code is a really
 * bad idea, in test code, it is often the cleanest and safest option, and is
 * not a code smell in the same way as it is for production code.
 * 
 * @author Suzanne M. Embury
 * @version February 2016
 */

public class GameTestsForExC3
{

  private Game game;
  private int  gridSize = 6,
               snakeLength = 5;


  @Before
  public void createAGame()
  {
    game = new Game(gridSize);
  }


  @Test
  public void shouldCorrectlyPlaceAnEastFacingSnakeWithTailInUpperLeftCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(0, 0);
    Coord[] bodyCells = { new Coord(1, 0), new Coord(2, 0), new Coord(3, 0) };
    Coord headCell = new Coord(4, 0);
    int direction = Direction.EAST;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }


  @Test
  public void shouldCorrectlyPlaceASouthFacingTailInUpperLeftCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(0, 0);
    Coord[] bodyCells = { new Coord(0, 1), new Coord(0, 2), new Coord(0, 3) };
    Coord headCell = new Coord(0, 4);
    int direction = Direction.SOUTH;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }


  @Test
  public void shouldCorrectlyPlaceAWestFacingTailInUpperRightCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(5, 0);
    Coord[] bodyCells = { new Coord(4, 0), new Coord(3, 0), new Coord(2, 0) };
    Coord headCell = new Coord(1, 0);
    int direction = Direction.WEST;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }


  @Test
  public void shouldCorrectlyPlaceASouthFacingTailInUpperRightCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(5, 0);
    Coord[] bodyCells = { new Coord(5, 1), new Coord(5, 2), new Coord(5, 3) };
    Coord headCell = new Coord(5, 4);
    int direction = Direction.SOUTH;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }


  @Test
  public void shouldCorrectlyPlaceAnEastFacingSnakeWithTailInBottomLeftCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(0, 5);
    Coord[] bodyCells = { new Coord(1, 5), new Coord(2, 5), new Coord(3, 5) };
    Coord headCell = new Coord(4, 5);
    int direction = Direction.EAST;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }


  @Test
  public void shouldCorrectlyPlaceANorthFacingTailInBottomLeftCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(0, 5);
    Coord[] bodyCells = { new Coord(0, 4), new Coord(0, 3), new Coord(0, 2) };
    Coord headCell = new Coord(0, 1);
    int direction = Direction.NORTH;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }


  @Test
  public void shouldCorrectlyPlaceAWestFacingTailInBottomRightCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(5, 5);
    Coord[] bodyCells = { new Coord(4, 5), new Coord(3, 5), new Coord(2, 5) };
    Coord headCell = new Coord(1, 5);
    int direction = Direction.WEST;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }


  @Test
  public void shouldCorrectlyPlaceANorthFacingTailInBottomRightCorner()
  {
    // Describe where the snake cells should go
    Coord tailCell = new Coord(5, 5);
    Coord[] bodyCells = { new Coord(5, 4), new Coord(5, 3), new Coord(5, 2) };
    Coord headCell = new Coord(5, 1);
    int direction = Direction.NORTH;

    // Check that the snake cells have been placed in these cells
    checkThatSnakeIsPlacedAlongEdgeCorrectly(tailCell, bodyCells, headCell,
        direction);
  }
  
  
  
  // Helper Assertion Methods

  private void checkThatSnakeIsPlacedAlongEdgeCorrectly(Coord tail,
      Coord[] body, Coord head, int direction)
  {
    game.setInitialGameState(tail.x, tail.y, snakeLength, direction);

    checkThatCellIsPlacedCorrectly(tail, Cell.SNAKE_TAIL, direction);
    checkThatOnlyThisCellIsOfThisType(tail, Cell.SNAKE_TAIL);

    checkThatBodyCellsArePlacedCorrectly(body, direction);

    checkThatCellIsPlacedCorrectly(head, Cell.SNAKE_HEAD, direction);
    checkThatOnlyThisCellIsOfThisType(head, Cell.SNAKE_HEAD);
  }


  private void checkThatBodyCellsArePlacedCorrectly(Coord[] bodyCells,
      int direction)
  {
    // Check that each of these cells are correctly configured as body cells
    for (Coord bodyCell : bodyCells)
      checkThatCellIsPlacedCorrectly(bodyCell, Cell.SNAKE_BODY, direction);

    // Check that no cells not mentioned in this list are body cells
    checkThatOnlyTheseCellsAreOfThisType(bodyCells, Cell.SNAKE_BODY);
  }


  private void checkThatCellIsPlacedCorrectly(Coord location, int type,
      int direction)
  {
    Cell cell = game.getGridCell(location.x, location.y);

    assertThat(cell.getType(), is(type));

    assertThat(cell.getSnakeOutDirection(), is(direction));
    assertThat(cell.getSnakeInDirection(), is(Direction.opposite(direction)));
  }


  private void checkThatOnlyTheseCellsAreOfThisType(Coord[] locations, int type)
  {
    for (int x2 = 0; x2 < game.getGridSize(); x2++)
      for (int y2 = 0; y2 < game.getGridSize(); y2++)
        if (!inThisListOfLocations(x2, y2, locations))
          checkThatThisCellIsNotOfType(x2, y2, type);
  }


  private void checkThatOnlyThisCellIsOfThisType(Coord location, int type)
  {
    Coord[] locations = { location };
    checkThatOnlyTheseCellsAreOfThisType(locations, type);
  }


  private boolean inThisListOfLocations(int x, int y, Coord[] locations)
  {
    boolean found = false;
    for (Coord location : locations)
      if (sameLocation(x, y, location.x, location.y))
        found = true;
    return found;
  }


  private boolean sameLocation(int x1, int y1, int x2, int y2)
  {
    return x1 == x2 && y1 == y2;
  }


  private void checkThatThisCellIsNotOfType(int x, int y, int expectedType)
  {
    // We can add a message to our assertions to provide a more useful response
    // in the event that the test fails. These messages make the assertions
    // less readable so should be used sparingly. But this assertion is called
    // many times by the code, and the results can be difficult to interpret
    // without some additional help. So, in this case, I judge the failure
    // message to be worth the loss in readability.
    String failMsg = "cell at <" + x + "," + y + "> not the expected type.";
    assertThat(failMsg, game.getGridCell(x, y).getType(),
        not(equalTo(expectedType)));
  }

}
