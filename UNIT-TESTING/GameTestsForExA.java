import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

/**
 * Tests for the Game class of the Snake Game lab exercise for COMP16212.
 *
 * This class contains test cases relating to part A of the exercise, in which
 * we add some very basic functionality, to set the author and the score
 * message.
 *
 * The test cases for this functionality are both very simple, and show how a
 * basic mutator/accessor pair is tested using JUnit.
 *
 * In practice, we wouldn't bother to write tests for such simple code,
 * especially if (as is best practice) the code for the accessors and mutators
 * are generated automatically by the programming environment. But, they make
 * very suitable first tests for students learning JUnit for the first time.
 *
 * I've given quite a lot of comments in this first file, to help you figure out
 * what is going on. In later files, I've added comments only when there was
 * some new testing feature to talk about, or where I felt the code was a little
 * hard to follow.
 *
 * Good luck!
 *
 * Suzanne
 *
 *
 *
 * @author Suzanne M. Embury
 */
public class GameTestsForExA
{
  @Test
  public void shouldReportTheScoreMessageWeSetForTheGame()
  {
    // First, we set up the unit of code we are going to test in this
    // test case.
    // To do that, we create a game instance with the expected score
    // message. We can make a game of any size for this, as size is not
    // directly relevant to the test. We pick 1 as the smallest "legal"
    // grid size for the game (we could have picked any other legal value,
    // without affecting correctness of the test).
    Game game = new Game(1);
    String sampleScoreMessage = "test score message";
    game.setScoreMessage(sampleScoreMessage);

    // Next, we tell the test to check that the score message
    // returned by the game is the one we set in the preceding line.
    assertThat(game.getScoreMessage(), is(sampleScoreMessage));
  }


  @Test
  public void shouldReturnCorrectAuthorsName()
  {
    // This test is even simpler, since we are testing whether a value
    // has been hard-coded into the program correctly.  We set up a
    // game and then check whether the author string has the value we
    // expect to be hard-coded in it.
    Game game = new Game(1);
    assertThat(game.getAuthor(), is("Mr.Moran Marcel"));
  }

}
