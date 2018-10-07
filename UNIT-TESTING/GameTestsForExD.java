import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 *
 * This class contains test cases relating to part D of the exercise, in which
 * we add the functionality to change the snake's direction in mid-game.
 * This is interesting functionality from the point of view of testing.
 * We have a set of four starting directions, from which we should test
 * the results of attempting to change to any of the four directions, giving
 * a total of 16 cases.  In this case, therefore, it is possible to give
 * tests which fully cover the space of possibilities, whereas usually we
 * have to test only a representative sample.
 *
 * @author Suzanne M. Embury
 * @version February 2016
 */

public class GameTestsForExD {

	private static final int LARGE_GRID_SIZE = 20;
	private static final int SNAKE_LENGTH = LARGE_GRID_SIZE / 4;
	private static final int CENTRE_X = LARGE_GRID_SIZE/2;
	private static final int CENTRE_Y = LARGE_GRID_SIZE/2;

	private Game game;

	@Before
	public void createAGame() {
		game = new Game(LARGE_GRID_SIZE);
	}



	// Tests of allowable direction changes (12 in total)

	@Test
	public void shouldAllowNorthFacingSnakeToFaceNorth() {
		checkThatThisDirectionChangeIsAllowed(Direction.NORTH, Direction.NORTH);
	}


	@Test
	public void shouldAllowNorthFacingSnakeToFaceEast() {
		checkThatThisDirectionChangeIsAllowed(Direction.NORTH, Direction.EAST);
	}


	@Test
	public void shouldAllowNorthFacingSnakeToFaceWest() {
		checkThatThisDirectionChangeIsAllowed(Direction.NORTH, Direction.WEST);
	}


	@Test
	public void shouldAllowEastFacingSnakeToFaceNorth() {
		checkThatThisDirectionChangeIsAllowed(Direction.EAST, Direction.NORTH);
	}


	@Test
	public void shouldAllowEastFacingSnakeToFaceEast() {
		checkThatThisDirectionChangeIsAllowed(Direction.EAST, Direction.EAST);
	}


	@Test
	public void shouldAllowEastFacingSnakeToFaceSouth() {
		checkThatThisDirectionChangeIsAllowed(Direction.EAST, Direction.SOUTH);
	}


	@Test
	public void shouldAllowSouthFacingSnakeToFaceEast() {
		checkThatThisDirectionChangeIsAllowed(Direction.SOUTH, Direction.EAST);
	}


	@Test
	public void shouldAllowSouthFacingSnakeToFaceSouth() {
		checkThatThisDirectionChangeIsAllowed(Direction.SOUTH, Direction.SOUTH);
	}


	@Test
	public void shouldAllowSouthFacingSnakeToFaceWest() {
		checkThatThisDirectionChangeIsAllowed(Direction.SOUTH, Direction.WEST);
	}


	@Test
	public void shouldAllowWestFacingSnakeToFaceNorth() {
		checkThatThisDirectionChangeIsAllowed(Direction.WEST, Direction.NORTH);
	}


	@Test
	public void shouldAllowWestFacingSnakeToFaceSouth() {
		checkThatThisDirectionChangeIsAllowed(Direction.WEST, Direction.SOUTH);
	}


	@Test
	public void shouldAllowWestFacingSnakeToFaceWest() {
		checkThatThisDirectionChangeIsAllowed(Direction.WEST, Direction.WEST);
	}


	// Tests for disallowed direction changes

	@Test
	public void shouldNotAllowNorthFacingSnakeToFaceSouth() {
		checkThatThisDirectionChangeIsNotAllowed(Direction.NORTH, Direction.SOUTH);
	}


	@Test
	public void shouldNotAllowEastFacingSnakeToFaceWest() {
		checkThatThisDirectionChangeIsNotAllowed(Direction.EAST, Direction.WEST);
	}


	@Test
	public void shouldNotAllowSouthFacingSnakeToFaceNorth() {
		checkThatThisDirectionChangeIsNotAllowed(Direction.SOUTH, Direction.NORTH);
	}


	@Test
	public void shouldNotAllowWestFacingSnakeToFaceEast() {
		checkThatThisDirectionChangeIsNotAllowed(Direction.WEST, Direction.EAST);
	}



	// Helper Assertion Methods

	private void checkThatThisDirectionChangeIsAllowed(int initialDirection, int newDirection) {
		// We place the snake in the centre of a grid large enough to contain
		// it, regardless of the direction in which it is travelling initially.
		game.setInitialGameState(CENTRE_X, CENTRE_Y, SNAKE_LENGTH, initialDirection);

		// Make the snake change its direction
		String initialScoreMsg = game.getScoreMessage();
		game.setSnakeDirection(newDirection);

		// Check that the head is pointing in the right direction after the change
		// of direction
		int headDirection = TestGame.getSnakeDirection(game);
		assertThat(headDirection, is(newDirection));

		// Check that the change of direction operation hasn't changed the score message
		assertThat(game.getScoreMessage(), is(initialScoreMsg));
	}


	private void checkThatThisDirectionChangeIsNotAllowed(int initialDirection, int newDirection) {
		game.setInitialGameState(CENTRE_X, CENTRE_Y, SNAKE_LENGTH, initialDirection);

		game.setSnakeDirection(newDirection);

		int headDirection = TestGame.getSnakeDirection(game);
		assertThat(headDirection, is(initialDirection));

		assertThat(game.getScoreMessage(), is("Hey mate what do you think you are doing?"));
	}

}
