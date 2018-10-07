import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 *
 * This class contains test cases relating to part E-5 of the exercise, in which
 * we give the snake the ability to eat food, and gain a score.
 *
 * When food is eaten, lots of things should change: the position of the food,
 * the length of the snake, the score and the score message should all change.
 *
 * We should therefore write tests at the boundary values for the snake length
 * and the score. The other two effects probably just need one test between
 * them. (To fully test the random placing of the food, we should really write a
 * test that runs several times, but we'll not bother with that for this
 * exercise.)
 *
 * (c) University of Manchester, 2016
 *
 * @author Suzanne M. Embury
 * @version 1.0.0
 */

public class GameTestsForExE5
{
  private static final int LARGE_GRID_SIZE = 14;
  private static final int MOVE_VALUE = 1;
  private static final int CENTRE_X = LARGE_GRID_SIZE / 2;
  private static final int CENTRE_Y = LARGE_GRID_SIZE / 2;
  private static final int SNAKE_LENGTH = LARGE_GRID_SIZE / 4;
  private static final int SNAKE_DIRECTION = Direction.EAST;

  private Game game;
  private Coord foodLocation;


  @Before
  public void setUpSnakeAboutToEatFood() {
    // Place the snake in the middle of a grid with plenty of room around it
    game = new Game(LARGE_GRID_SIZE);
    game.setInitialGameState(CENTRE_X, CENTRE_Y, SNAKE_LENGTH, SNAKE_DIRECTION);

    foodLocation = TestGame.putFoodInFrontOfSnake(game);
  }


  @Test
  public void shouldExtendSnakeLengthWhenFoodIsEaten()
  {
    eatTheFood();

    int newSnakeLength = TestGame.numberOfSnakeCellsOnGrid(game);
    assertThat(newSnakeLength, is(SNAKE_LENGTH + 1));
  }


  @Test
  public void shouldPlaceNewFoodWhenOldFoodIsEaten()
  {
    eatTheFood();

    int amountOfFood = TestGame.numberOfFoodCellsOnGrid(game);
    assertThat(amountOfFood, is(1));
  }


  @Test
  public void shouldUpdateTheScoreWhenFoodIsEatenForTheFirstTime()
  {
    eatTheFood();
    assertThat(game.getScore(), is(1));
  }


  @Test
  public void shouldUpdateTheScoreWhenSeveralPiecesOfFoodAreEaten()
  {
    eatTheFood();
    assertThat(game.getScore(), is(1));

    Coord newFoodLocation = foodLocation.neighbouringLocation(SNAKE_DIRECTION);
    TestGame.putFoodAt(game, newFoodLocation);
    eatTheFood();
    assertThat(game.getScore(), is(2));


    TestGame.putFoodAt(game, newFoodLocation.neighbouringLocation(SNAKE_DIRECTION));
    eatTheFood();
    assertThat(game.getScore(), is(4));
  }


  @Test
  public void shouldUpdateTheScoreWhenSeveralPiecesOfFoodAreEatenAndMoveValueIsNot1()
  {
    eatTheFood(2);
    assertThat(game.getScore(), is(2));

    Coord newFoodLocation = foodLocation.neighbouringLocation(SNAKE_DIRECTION);
    TestGame.putFoodAt(game, newFoodLocation);
    eatTheFood(2);
    assertThat(game.getScore(), is(4));


    TestGame.putFoodAt(game, newFoodLocation.neighbouringLocation(SNAKE_DIRECTION));
    eatTheFood(4);
    assertThat(game.getScore(), is(12));
  }


  @Test
  public void shouldUpdateTheScoreMessageToShowThatFoodHasBeenEaten() {
    eatTheFood();
    assertThat(game.getScoreMessage(), is("you has an increase of 1"));
  }
  



  // Helper methods

  private void eatTheFood()
  {
    game.move(MOVE_VALUE);
  }


  private void eatTheFood(int moveValue)
  {
    game.move(moveValue);
  }

}
