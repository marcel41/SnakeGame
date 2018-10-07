import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part B of the exercise, in which
 * we add the functionality to create a basic game, with a grid full of
 * new cells.
 * 
 * I've chosen 3 test cases to code up here, covering just the main behaviour.
 * We should really add some test cases to cover some edge cases, but I didn't
 * want to clutter up the class while you are still getting used to JUnit.
 * 
 * Note how I use the long test case method names as a form of documentation
 * for the test cases.  We never write code which explicitly calls these
 * JUnit methods, since they are called by JUnit through reflection.  (It
 * executes all methods it can find on its classpath that have the @Test
 * annotation.)  So, there're no problem with writing these really long
 * descriptive names for the methods.  Their only function is to explain what
 * the test is all about, so there's no point in skimping on characters
 * just to keep them short.
 * 
 * @author Suzanne M. Embury
 * @version February 2016
 */
public class GameTestsForExB {

	@Test
	public void shouldReportGridSizeThatWasSetWhenGameCreated() {
		int gridSize = 4;
		Game game = new Game(gridSize);
		assertThat(game.getGridSize(), is(gridSize));
	}
	
	
	// The next test seems to be testing the same behaviour as the
	// previous one, but with a different grid size.  I've included it
	// to guard against hard-coded values in the code which could make
	// the test appear to pass, even though the required behaviour is not
	// actually implemented.
	@Test
	public void shouldReportAnotherGridSizeThatWasSetWhenGameCreated() {
		int gridSize = 20;
		Game game = new Game(gridSize);
		assertThat(game.getGridSize(), is(gridSize));
	}
	
	
	// This test checks whether the correct sized grid is created when
	// we make a new Game, and also that it is populated with the right
	// type of cell.
	//
	// It is a good example of the different approach to coding we take
	// when writing tests.  We would never think of writing out the steps
	// needed to process an array of fixed size like this when writing
	// production code (or very rarely).  We would instead write a loop
	// which processes an arbitrary number of cells, just like you will
	// write in your solution to this part of the exercise.  In the test
	// code, however, we don't care about generality.  We just want to
	// express the correct behaviour as simply and as obviously correctly
	// as we can.
	//
	// In a full version of this test suite, we would also include tests to
	// check that the grid created by "new Game" is not bigger than that
	// specified in the grid size.  To write these tests, we would need
	// to use exceptions, which you've not covered, yet.  (They're coming
	// soon!)
	
	@Test
	public void shouldCreateAGridOf9NewCellsWhenAGameWithGridSize3IsCreated() {
		Game game = new Game(3);
		assertThat(game.getGridCell(0, 0).getType(), is(Cell.NEW));
		assertThat(game.getGridCell(0, 1).getType(), is(Cell.NEW));
		assertThat(game.getGridCell(0, 2).getType(), is(Cell.NEW));

		assertThat(game.getGridCell(1, 0).getType(), is(Cell.NEW));
		assertThat(game.getGridCell(1, 1).getType(), is(Cell.NEW));
		assertThat(game.getGridCell(1, 2).getType(), is(Cell.NEW));

		assertThat(game.getGridCell(2, 0).getType(), is(Cell.NEW));
		assertThat(game.getGridCell(2, 1).getType(), is(Cell.NEW));
		assertThat(game.getGridCell(2, 2).getType(), is(Cell.NEW));
	}
	
}
