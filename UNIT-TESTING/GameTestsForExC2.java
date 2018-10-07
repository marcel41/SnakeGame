import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 * 
 * This class contains test cases relating to part C-2 of the exercise, in which
 * we add the code to place some food on the grid when we initialise it.
 * 
 * This is a fairly simple increment, so there is not a lot of testing to
 * be done.  We check that some food appears after initialisation, and then
 * (since the game could be initialised several times in one run) we check that
 * there is still just one piece of food when the game is re-initialised right
 * afterwards.
 * 
 * If you have decided to make your class remember where you have placed the
 * food, then there are some extra test cases you can run, to verify the
 * integrity of this information.  You will need to uncomment the code below
 * and modify it, so that it accesses the food location information using the
 * approach you have chosen.
 * 
 * Note that in this set of tests I introduce a "fixture" method: a method
 * that JUnit will execute before each test case, to set up any shared state.
 * This method has the annotation @Before.  To understand each individual
 * test case, you have to work out what happens when the @Before method is
 * executed immediately before it, as that is what happens when JUnit executes
 * the class.
 * 
 * (c) University of Manchester, 2016.
 * 
 * @author Suzanne M. Embury
 * @version February 2016
 */
public class GameTestsForExC2 {
	
	private static final int ROOMY_GRID_SIZE = 6;
	private static final int ARBITRARY_SNAKE_LENGTH = ROOMY_GRID_SIZE - 1;
	
	// All the tests in this class can use the same game instance as their
	// starting point.  Therefore, we use JUnit's @Before annotation to
	// describe the set-up for all the tests in one place (rather than
	// having this code clutter up the individual test cases).
	private Game game;

	@Before
	public void createASuitableGameInstance() {
		game = new Game(ROOMY_GRID_SIZE);
		game.setInitialGameState(0, 0, ARBITRARY_SNAKE_LENGTH, Direction.EAST);
	}

	
	@Test
	public void shouldPlaceOneFoodOnTheGridAfterInitialisation() {
		assertThat(TestGame.numberOfFoodCellsOnGrid(game), is(1));
	}
	
	
	// This next test checks that a single item of food is placed
	// when the game is re-initialised.
	@Test
	public void shouldPlaceOneFoodOnTheGridAfterReInitialisation() {
		game.setInitialGameState(0, 0, ARBITRARY_SNAKE_LENGTH, Direction.EAST);

		assertThat(TestGame.numberOfFoodCellsOnGrid(game), is(1));
	}
	
	
	// If you have decided to make your class remember where the food is
	// placed, then you can also run the tests below.  They check that
	// the food coordinates stored by your code correspond to the actual
	// location of the food.
	// To make the tests run, replace the "GET FOOD X COORD" and 
	// "GET FOOD Y COORD" strings with the code to get the food position
	// in your implementation.  Then uncomment the test cases (and the
	// helper method at the end of the class), recompile and run.

/****
	@Test
	public void shouldReportTheCorrectLocationOfTheFoodWhenFirstPlaced() {		
		int storedFoodXCoord = GET FOOD X COORD;
		int storedFoodYCoord = GET FOOD Y COORD;
		
		Coord actualFoodCoords = TestGame.findFoodCoordinates(game);
		assertThat(storedFoodXCoord, is(actualFoodCoords.x));
		assertThat(storedFoodYCoord, is(actualFoodCoords.y));
	}
	
	
	@Test
	public void shouldReportTheCorrectLocationOfTheFoodWhenReInitialised() {		
		int storedFoodXCoord = GET FOOD X COORD;
		int storedFoodYCoord = GET FOOD Y COORD;
		
		Coord actualFoodCoords = TestGame.findFoodCoordinates(game);
		assertThat(storedFoodXCoord, is(actualFoodCoords.x));
		assertThat(storedFoodYCoord, is(actualFoodCoords.y));
	}
****/
	
}
