import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 *
 * This class contains test cases relating to part G of the exercise, in which
 * we add the ability to place trees on the grid, that the snake can crash
 * into.
 *
 * There is quite a lot of functionality to add for this exercise, and therefore
 * quite a lot of tests to think about.  We have to test:
 *
 * - the placing of trees on the board
 * - the removal of trees from the board
 * - the snake crashing into the trees
 *
 * We also have to test that existing parts of the code behave sensibly
 * in respect of trees:
 *
 * - that the grid is initialised with 0 trees to begin with
 * - that the score is calculated correctly in the presence of trees
 * - the addition of a new tree to the board when food is eaten
 *
 * That's a lot of test cases to cover, and ideally we would split this test
 * class into several smaller classes, each focusing on one part of the
 * functionality.  For now, though, I'm going to retain the structure where
 * the test cases for one exercise are kept in a single test class.  You can
 * let me know if this was the wrong decision for your particular brain.
 *
 *
 * You may start to find the test suite more useful from this stage, as if
 * your changes break any of the earlier functionality you have already
 * implemented, there is a chance that the test failures will point it out
 * to you.  Remember to run the test suite frequently (ideally, after every
 * small code change) to get the most value out of it as an early warning
 * indicator of unintentional regression of earlier functionality.
 *
 *
 * A complicating factor in the implementation of these tests is that the
 * Game class maintains a private field recording information about the number
 * of trees on the board.  We can place trees on the board where we like, by
 * changing the type of specific cells, but we can't update this private
 * variable by any means other than by calling the public methods on the Game
 * class.  So, our only means of placing a tree with the current version of
 * the code is to make the snake eat some food, so that the Game class adds
 * a new tree.  We can set the moveValue to 0 for this, so that the score
 * is not affected.  But we have very little control over exactly where the
 * tree will be placed, which adds an element of randomness into the tests
 * (something to be avoided wherever possible).
 *
 * All this means that if you get an individual failure of a test from this
 * test class, try running the tests again several times to make sure it is
 * a systematic failure, and not the result of this random tree placement
 * problem.
 *
 *
 * (c) University of Manchester, 2016.
 *
 * @author Suzanne M. Embury
 * @version 1.0.0
 * @version 1.0.1: One test fixed, another deleted: JTL 19/02/16
 */

public class GameTestsForExG {

  private static final int GRID_SIZE = 36;
  private static final int SNAKE_LENGTH = GRID_SIZE / 4;
  private static final int CRASH_COUNTDOWN = 5;

  private Game game;


  @Before
  public void createAGame() {
    game = new Game(GRID_SIZE);
  }


  // Tests relating to toggling trees on and off

  @Test
  public void shouldBeNoTreesOnInitialisedGridIfTreesAreSwitchedOffWhenGameBegins() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);
    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(0));
  }


   @Test
   public void shouldBeOneTreeOnInitialisedGridIfTreesAreSwitchedOnWhenGameBegins() {
     game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);
     game.toggleTrees();
     game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);
     int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
     assertThat(numTrees, is(1));
   }



  @Test
  public void shouldBeOneTreePlantedWhenTreesAreToggledOnAfterGameIsInitialised() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);
    game.toggleTrees();

    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(1));
  }


  @Test
  public void shouldBeNoTreesPlantedWhenTreesAreToggledOnAndThenOffAgain() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);
    game.toggleTrees();
    game.toggleTrees();

    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(0));
  }


  @Test
  public void shouldBeOneTreePlantedWhenTreesAreToggledOnAndThenGameIsReinitialised() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);
    game.toggleTrees();
    game.setInitialGameState(0, GRID_SIZE-1, SNAKE_LENGTH, Direction.NORTH);

    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(1));
  }


  @Test
  public void shouldBeTwoTreesPlantedWhenTreesAreToggledOnAndFirstFoodIsEaten() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);

    game.toggleTrees();
    eatSomeFood(0);

    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(2));
  }


  @Test
  public void shouldBeFiveTreesPlantedWhenTreesAreToggledOnAndFirstFourPiecesOfFoodIsEaten() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);

    game.toggleTrees();
    eatSomeFood(0);
    eatSomeFood(0);
    eatSomeFood(0);
    eatSomeFood(0);

    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(5));
  }


  @Test
  public void shouldBeNoTreesWhenTreesAreToggledOnAfterSeveralTreesHaveBeenPlanted() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);

    game.toggleTrees();
    eatSomeFood(0);
    eatSomeFood(0);
    eatSomeFood(0);
    eatSomeFood(0);
    game.toggleTrees();

    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(0));
  }


/* Always loops forever!
  @Test
  public void shouldBeNoTreesWhenTreesAreToggledOnButTheBoardIsFull() {
    game.setInitialGameState(0, 0, SNAKE_LENGTH, Direction.SOUTH);
    fillTheBoardWithFood();

    game.toggleTrees();

    int numTrees = TestGame.numberOfTreeCellsOnGrid(game);
    assertThat(numTrees, is(0));
  }
*/


  // Tests regarding the snake crashing into the trees

  @Test
  public void shouldCauseNorthFacingSnakeToCrashWhenAttemptsToMoveIntoTreeCell() {
    checkThatCrashIntoTreeIsDetectedWhenTravelling(Direction.NORTH);
  }

  @Test
  public void shouldCauseEastFacingSnakeToCrashWhenAttemptsToMoveIntoTreeCell() {
    checkThatCrashIntoTreeIsDetectedWhenTravelling(Direction.EAST);
  }

  @Test
  public void shouldCauseSouthFacingSnakeToCrashWhenAttemptsToMoveIntoTreeCell() {
    checkThatCrashIntoTreeIsDetectedWhenTravelling(Direction.SOUTH);
  }

  @Test
  public void shouldCauseWestFacingSnakeToCrashWhenAttemptsToMoveIntoTreeCell() {
    checkThatCrashIntoTreeIsDetectedWhenTravelling(Direction.WEST);
  }



  // Tests regarding the effect on score calculation when trees are present

  @Test
  public void shouldAwardNormalScoreWhenOnlyOneTreeIsOnTheGrid() {
    placeSnakeThatIsAboutToEatFood();
    game.toggleTrees();
    game.move(5);
    assertThat(game.getScore(), is(5));
  }


  @Test
  public void shouldAwardDoubleScoreWhenTwoTreesAreOnTheGrid() {
    Coord head = new Coord(GRID_SIZE / 2, GRID_SIZE / 2);
    TestGame.placeStraightSnakeWithHeadAt(game, head, 3,
        Direction.WEST, Direction.EAST);

    // Make sure there are two trees on the board
    game.toggleTrees(); // Adds one tree
    eatSomeFood(0);     // Adds a second tree

    // Now eat some food with a non-zero move value, to see what effect it has
    // on the score
    eatSomeFood(5);
    assertThat(game.getScore(), is(2 * 5));
  }


  @Test
  public void shouldAwardFiveFoldScoreOnTopOfNonZeroScoreWhenFiveTreesAreOnTheGrid() {
    Coord head = new Coord(GRID_SIZE / 2, GRID_SIZE / 2);
    TestGame.placeStraightSnakeWithHeadAt(game, head, 3,
        Direction.WEST, Direction.EAST);

    // Make sure there are five trees on the board
    game.toggleTrees(); // Adds one tree
    eatSomeFood(4);     // Adds a second tree
    eatSomeFood(0);     // Adds a third tree
    eatSomeFood(0);     // Adds a fourth tree
    eatSomeFood(0);     // Adds a fifth tree and increases the score

    assertThat("Score before move", game.getScore(), is(4));

    // Now eat some food with a non-zero move value, to see what effect it has
    // on the score
    eatSomeFood(3);

    assertThat("Score after move", game.getScore(), is(4 + 5 * 3));
  }



  // Tests about adding new trees to the board when food is eaten mid game

  @Test
  public void shouldAddASecondTreeWhenTheFirstPieceOfFoodIsEatenAndTreesAreEnabled() {
    placeSnakeThatIsAboutToEatFood();
    game.toggleTrees();
    game.move(0);
    assertThat(TestGame.numberOfTreeCellsOnGrid(game), is(2));
  }


  @Test
  public void shouldAddAFifthTreeWhenFoodIsEatenAndThereAreFourTreesOnTheBoard() {
    placeSnakeThatIsAboutToEatFood();
    game.toggleTrees();
    game.move(0);
    eatSomeFood(0);
    eatSomeFood(0);

    assertThat(TestGame.numberOfTreeCellsOnGrid(game), is(4));
    eatSomeFood(0);

    assertThat(TestGame.numberOfTreeCellsOnGrid(game), is(5));
  }





  // Helper Assertion Methods

  private void checkThatCrashIntoTreeIsDetectedWhenTravelling(int direction)
  {
    Coord headLocation = new Coord(GRID_SIZE / 2, GRID_SIZE / 2);
    TestGame.placeStraightSnakeWithHeadAt(game, headLocation, SNAKE_LENGTH,
        direction, Direction.opposite(direction));

    Coord treeLocation = headLocation.neighbouringLocation(direction);
    TestGame.putTreeAt(game, treeLocation);

    TestGame.moveTheSnakeUntilItCrashes(game, CRASH_COUNTDOWN, 1);

    checkThatHeadHasntMoved(headLocation);
    checkThatCellIsBloody(headLocation);
    checkThatScoreMessageDescribesACrashIntoATree();
  }


  private void checkThatHeadHasntMoved(Coord originalHeadLocation)
  {
    Coord newHeadLocation = TestGame.getLocationOfHead(game);

    String failMsg = "Head cell is not where we expected it to be.  ";
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


  private void checkThatScoreMessageDescribesACrashIntoATree()
  {
    assertThat(game.getScoreMessage(),
        is("TREES ARE BAD FOR YOU!"));
  }




  // Helper Methods

  private void eatSomeFood(int moveValue)
  {
    Coord head = TestGame.getLocationOfHead(game);
    int snakeDirection = game.getGridCell(head.x, head.y).getSnakeOutDirection();
    TestGame.putFoodAt(game, head.neighbouringLocation(snakeDirection));
    game.move(moveValue);
  }


  private void fillTheBoardWithFood()
  {
    Cell cellToFill;
    for (int x = 0; x < game.getGridSize(); x++)
      for (int y = 0; y < game.getGridSize(); y++) {
        cellToFill = game.getGridCell(x, y);
        if (cellToFill.getType() == Cell.CLEAR)
          cellToFill.setFood();
      }
  }


  private void placeSnakeThatIsAboutToEatFood()
  {
    Coord headLocation = new Coord(0, SNAKE_LENGTH);
    TestGame.placeStraightSnakeWithHeadAt(game, headLocation, SNAKE_LENGTH,
        Direction.SOUTH, Direction.NORTH);
    TestGame.putFoodInFrontOfSnake(game);
  }

}
