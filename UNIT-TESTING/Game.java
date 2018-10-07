public class Game
{

// ----------------------------------------------------------------------
// Part a: the score message

//create a String var and make private for security
  private String score_message;
//make a var int to save the score
	private int score;
  public String getScoreMessage()
  {
    return score_message;
  } // getScoreMessage

  public void setScoreMessage(String message)
  {
//update string something else
    score_message = message;
  } // setScoreMessage


  public String getAuthor()
  {
    return "Mr.Moran Marcel";
  } // getAuthor


// ----------------------------------------------------------------------
// Part b: constructor and grid accessors

//build the variable for gridsize and grid(x,y)
  private final int grid_size;
	private final Cell [][] grid;
  public Game(int requiredGridSize)
  {
		grid_size = requiredGridSize;
//assign grid
		grid = new Cell[requiredGridSize][requiredGridSize];
//make for loop to pass cell into the grid double array
    for(int indexhorizontal = 0; indexhorizontal < grid_size;
				indexhorizontal++)
		 for(int indexvertical = 0; indexvertical < grid_size;
			   indexvertical++)
				  grid [indexhorizontal][indexvertical] =
					new Cell();
  } // Game


  public int getGridSize()
  {
    return grid_size;
  } // getGridSize


  public Cell getGridCell(int x, int y)
  {

//obtain the cell from the grid
    return grid [x] [y];
  } // getGridCell


// ----------------------------------------------------------------------
// Part c: initial game state

// Part c-1: setInitialGameState method

  public void setInitialGameState(int requiredTailX, int requiredTailY,
                                  int requiredLength, int requiredDirection)
  {
//make two nested loop to get green background
    for(int indexhorizontal = 0; indexhorizontal < grid_size;
				indexhorizontal++)
		 for(int indexvertical = 0; indexvertical < grid_size;
			   indexvertical++)
				  grid [indexhorizontal][indexvertical].setClear();

		placeSnake(requiredTailX, requiredTailY,
						   requiredLength, requiredDirection);
		placeFood(grid_size);
		currentTrees = 0;
		if(treesEnable)
    {
      planttree();

    }

		score = 0;
  } // setInitialGameState


// ----------------------------------------------------------------------
// Part c-2 place food
//define var outside method
	private int positionX;
	private int positionY;
//method to the food randomly
	private void placeFood(int n)
	{
//while loop to generate cells to put the food
		do{
		positionX = (int) (Math.random() * n);
		positionY = (int) (Math.random() * n);
		}
		while(grid[positionX][positionY].getType() != Cell.CLEAR);
//put in that cell the food
		grid[positionX][positionY].setFood();
	}

// ----------------------------------------------------------------------
// Part c-3: place snake
//instance var to store
/*direction of snake
	x and y position tail
	x and y position head
	length of snake */
	public int new_direction;
	public int xPosition_tail;
	public int yPosition_tail;
	public int xPosition_head;
	public int yPosition_head;
	public int length_snake;
	private void placeSnake(int tailx, int taily, int length, int direction)
	{
//placing the tail of the snake
		xPosition_tail = tailx;
		yPosition_tail = taily;
		new_direction = direction;
		length_snake =  length;
		grid[xPosition_tail][yPosition_tail].setSnakeTail
				(Direction.opposite(new_direction),new_direction);
//place the direction in of the snake

//make the loop to place the snake body
//but first create the cell position
		int newXPositionBodySnake = xPosition_tail
                                + Direction.xDelta(new_direction);
		int newYPositionBodySnake = yPosition_tail
                                + Direction.yDelta(new_direction);
		Cell cellPosition = grid[newXPositionBodySnake]
										[newYPositionBodySnake];
		for(int index = 0; index < length-2;index++ )
		{
			cellPosition.setSnakeBody(Direction.opposite(new_direction),
                                new_direction);
			newXPositionBodySnake+=Direction.xDelta(new_direction);
			newYPositionBodySnake+=Direction.yDelta(new_direction);
			cellPosition = grid[newXPositionBodySnake]
										     [newYPositionBodySnake];

		}
//easy way to define the direction of the snake
		xPosition_head = newXPositionBodySnake;
		yPosition_head = newYPositionBodySnake;
		cellPosition.setSnakeHead(Direction.opposite(new_direction),
                              new_direction);
//remember to make the direction to be opposite
//place dir out of snake
	}
// ----------------------------------------------------------------------
// Part d: set snake direction
  public void setSnakeDirection(int requiredDirection)
  {
   if(requiredDirection == grid[xPosition_head]
                               [yPosition_head].getSnakeInDirection())
   {
     setScoreMessage("Hey mate what do you think you are doing?");
   }
   else
   {
    if(!grid[xPosition_head][yPosition_head].isSnakeBloody())
    {
     new_direction = requiredDirection;
     grid[xPosition_head][yPosition_head].setSnakeOutDirection
  	 (new_direction);
    }
 	 // setSnakeDirection
   }
  }
// ----------------------------------------------------------------------
// Part e: snake movement

// Part e-1: move method


  public void move(int moveValue)
  {
//code to decrease level of each cell
//optional part
    for(int indexhorizontal = 0; indexhorizontal < grid_size;
				indexhorizontal++)
		 for(int indexvertical = 0; indexvertical < grid_size;
			    indexvertical++)
      if(grid[indexhorizontal][indexvertical].getType() == Cell.OTHER)
        {
        if(grid[indexhorizontal][indexvertical].getOtherLevel() > 0)
          grid[indexhorizontal][indexvertical].setOther
            (grid[indexhorizontal][indexvertical].getOtherLevel() - 5);
          else
            grid[indexhorizontal][indexvertical].setClear();
        }
//------------------------------------------------------------------------
//get to know if the snake is bloody
	  if(!grid[xPosition_head][yPosition_head].isSnakeBloody())
		{
//Compute the value of the new position of the head
      int newXPosition_head = xPosition_head
                                + Direction.xDelta(new_direction);
	    int newYPosition_head = yPosition_head
                                + Direction.yDelta(new_direction);
      boolean is_okey_move = checkAndDeal(newXPosition_head,
                                          newYPosition_head);
//pass the coordinates next for optional part
//-------------------------------------------
      xTree = newXPosition_head;
      yTree = newYPosition_head;

//-------------------------------------------
      if(is_okey_move)
      {

        boolean food_at_newposition = grid[newXPosition_head]
                [newYPosition_head].getType() == Cell.FOOD;
//place the method
        moveHead(newXPosition_head, newYPosition_head);
        if(food_at_newposition)
          eatFood(moveValue);
        else
          {
          moveTheTail();
          snakeTrail();
          }
       }
     if(mEnable)
         foodMovement();
     }

  } // move


// ----------------------------------------------------------------------
// Part e-2: move the snake head
  public void moveHead(int new_x_position, int new_y_position)
  {
//place the snake in a new cell
    grid[new_x_position][new_y_position].setSnakeHead
        (Direction.opposite(new_direction), new_direction);
//place the body where it was the snake head
//direction in should change for the body when it turns around
//    if(Direction.opposite(new_direction) == grid[xPosition_head]
//    [yPosition_head].getSnakeInDirection())
    grid[xPosition_head][yPosition_head].setSnakeBody();
//        (Direction.opposite(new_direction), new_direction);
//    else
//    grid[xPosition_head][yPosition_head].setSnakeBody
//        (Direction.rightTurn(new_direction), new_direction);
//update x and y position
    xPosition_head = new_x_position;
    yPosition_head = new_y_position;
  }

// ----------------------------------------------------------------------
// Part e-3: move the snake tail
  public void moveTheTail()
  {
//moving the tail and clearing the cell
    int direction_nextcell = grid[xPosition_tail]
                             [yPosition_tail].getSnakeOutDirection();
//now use to calculate new cells position
    grid[xPosition_tail][yPosition_tail].setClear();
    grid[xPosition_tail + Direction.xDelta(direction_nextcell)]
        [yPosition_tail + Direction.yDelta(direction_nextcell)].setSnakeTail
        ();

//now save the old directions of the tail that will
//be updated if the gutter trail is on
    xTrail = xPosition_tail;
    yTrail = yPosition_tail;
//now update the var of the directions
    xPosition_tail += Direction.xDelta(direction_nextcell);
    yPosition_tail += Direction.yDelta(direction_nextcell);
  }

// ----------------------------------------------------------------------
// Part e-4: check for and deal with crashes
  public boolean checkAndDeal(int Xposition, int Yposition)
  {

//boolean expresion that will compare the cell with part of the snake
//and knowing if the snake is in the grid(field of the game)
//string var to display text
    String killbyOutBoundaries = "Hey!! you can not go away from this field";
    String killbyItself = "NOO!!!, YOU CAN NOT EAT YOURSELF";
    String KillbyTrees = "TREES ARE BAD FOR YOU!";
    boolean isSnakeDead;
    //plug method
    if ((Xposition < 0) || (Xposition >= grid_size) ||
    (Yposition >= grid_size) || (Yposition < 0))
    {
      isSnakeDead = reduceCountDown();
//if the snake is dead then reproduce a suitable message and make
//a part of the snake head and the cell where is crashed is red
      if(isSnakeDead)
      {
        setScoreMessage(killbyOutBoundaries);
        grid[xPosition_head][yPosition_head].setSnakeBloody(true);
      }
//do not allow to move the snake if the snake mov is out of conditions
      return false;
    }
    else if((grid[Xposition][Yposition].getType() == Cell.SNAKE_HEAD) ||
    (grid[Xposition][Yposition].getType() == Cell.SNAKE_BODY)    ||
    (grid[Xposition][Yposition].getType() == Cell.SNAKE_TAIL))
    {
//if the snake is dead then reproduce a suitable message and make
//a part of the snake head and the cell where is crashed is red
      isSnakeDead = reduceCountDown();
      if(isSnakeDead)
      {
        setScoreMessage(killbyItself);
        grid[xPosition_head][yPosition_head].setSnakeBloody(true);
	      grid[Xposition][Yposition].setSnakeBloody(true);
      }
//do not allow to move the snake if the snake mov is out of conditions
      return false;
    }
    else if(grid[Xposition][Yposition].getType() == Cell.TREE)
    {
//if the snake is dead then reproduce a suitable message and make
//a part of the snake head and the cell where is crashed is red
      isSnakeDead = reduceCountDown();
      if(isSnakeDead)
      {
        score_message = KillbyTrees;
        grid[xPosition_head][yPosition_head].setSnakeBloody(true);
      }
//do not allow to move the snake if the snake mov is out of conditions
      return false;
    }
    else
    {
//if the player change dir and move in a suitable cell then restart countdown
//and allow the movement
      resetCountDown();
      return true;
    }
  }

// ----------------------------------------------------------------------
// Part e-5: eat the food
  public void eatFood(int value)
  {
//calculting the score
   	length_snake++;
    int increase_score = value * ((length_snake
                                 /(grid_size * grid_size/ 36 + 1)) + 1);
//increase in score for trees
   if(treesEnable)
    {
//calculate raw score
      int raw_score = increase_score * currentTrees;
//add raw score to the score
      score += raw_score;
      score_message = "you score is " + raw_score + ", there are "
                      + (currentTrees + 1) + " threes, you have "
                      + "an increase of " + (score - raw_score);
			planttree();
    }
		else
		{
//add increase score to original score
			score += increase_score;
			setScoreMessage("you has an increase of " + (increase_score));
		}
//putting more food when is eaten
//call the method place food

    placeFood(grid_size);
  }

  public int getScore()
  {
    return score;
  } // getScore


// ----------------------------------------------------------------------
// Part f: cheat


  public void cheat()
  {
//half the score
    score /= 2;
//advise to the player
    score_message = "You lost " + score ;
//make double loop to find the bloody celll and
//up to be normal cells
    for(int indexhorizontal = 0; indexhorizontal < grid_size;
				indexhorizontal++)
		 for(int indexvertical = 0; indexvertical < grid_size;
			   indexvertical++)
				  if (grid [indexhorizontal][indexvertical].isSnakeBloody())
            grid [indexhorizontal][indexvertical].setSnakeBloody(false);
//reset the countdown to allow the snake to crash again
    resetCountDown();
  } // cheat


// ----------------------------------------------------------------------
//var to store current trees
  public int currentTrees;
//var to know if trees are enabled or not
  public boolean treesEnable = false;
// Part g: trees
  public void toggleTrees()
  {
//switch in case is true
    if(treesEnable)
      treesEnable = false;
    else
//switch in case is false
      treesEnable = true;
//plant trees or delete trees
    if(treesEnable)
    {
      planttree();
    }
    else
//search for all the cells and delete
    {
     for(int indexhorizontal = 0; indexhorizontal < grid_size;
				indexhorizontal++)
		  for(int indexvertical = 0; indexvertical < grid_size;
			   indexvertical++)
       if (grid [indexhorizontal][indexvertical].getType() == Cell.TREE)
            grid [indexhorizontal][indexvertical].setClear();

			currentTrees = 0;
    }

  } // toggleTrees
//private method to plant tree
  public int positionXTree;
  public int positionYTree;
  private void planttree()
  {
    //while loop to generate cells to put the food
		do{
		 positionXTree = (int) (Math.random() * grid_size);
		 positionYTree = (int) (Math.random() * grid_size);
		}
		while(grid[positionXTree][positionYTree].getType() != Cell.CLEAR);
//put in that cell the food
		grid[positionXTree][positionYTree].setTree();
    currentTrees++;
  }

// ----------------------------------------------------------------------
// Part h: crash countdown
  public final int numberOfmoves = 5;
  public int countDownValue = numberOfmoves;
  private void resetCountDown()
  {
//now if current coundown  then it should incate
//player that he escape from death
    if(countDownValue < numberOfmoves)
    {
       score_message = "you are lucky, you have escaped from death by "
                        + countDownValue + " moves";
//restart the countdown value
       countDownValue = numberOfmoves;
    }
  }
  private boolean reduceCountDown()
  {
    countDownValue--;
    if(countDownValue > 0)
    {
      score_message = "move quickly, you have " + countDownValue + "moves";
//return false to show that the snake is not dead
      return false;
    }
    else
    {
//the snake dead restart the countdownValue when you start a new game or
//when you use the function chear
      countDownValue = numberOfmoves;
      return true;
    }
  }

// ----------------------------------------------------------------------
// Part i: optional extras
//trail
  public boolean gEnable = false;
//var of the trail x and trail
  public int xTrail;
  public int yTrail;
//-----------------------------------------------------------------
//method trail
  private void snakeTrail()
  {
//set cells to be other with 50 of shadows if g is push
    if(gEnable)
     grid[xTrail][yTrail].setOther(50);
    else
    {
//low the level of cells until go to 0 then set the cellothers to be clear
      for(int indexhorizontal = 0; indexhorizontal < grid_size;
				indexhorizontal++)
		   for(int indexvertical = 0; indexvertical < grid_size;
			    indexvertical++)
          if(grid[indexhorizontal][indexvertical].getType() == Cell.OTHER)
             grid[indexhorizontal][indexvertical].setClear();
    }
  }
//----------------------------------------------------------------
//method to burn trees
//var that the position of a tree
//when the head is there
  public int xTree;
  public int yTree;
  private void burnTree()
  {
//check if x tree and y is a tree
    if((xTree >= 0) && (xTree < grid_size)
    && (yTree < grid_size) && (yTree >= 0))
      if(grid[xTree][yTree].getType() == Cell.TREE)
      {
//set that cell to be clear and make the currentTrees reduce by one
        grid[xTree][yTree].setClear();
        currentTrees--;
      }
  }
//----------------------------------------------------------------
//method of movement
//make a boolean exp to switch
  public boolean mEnable = false;
//make a direction where the food will move
  public  int movedir = Direction.EAST;
  private void foodMovement()
  {
//make direction
    int nextPositionFoodx = positionX + Direction.xDelta(movedir);
//leave white a same direction
    int nextPositionFoody = positionY + Direction.yDelta(movedir);
//obtaining modules if the nextPositionfood
    int moduleNextPosition = (int) Math.pow(
                              (nextPositionFoodx - xPosition_head),2) +
                            (int) Math.pow(
                              (nextPositionFoody - yPosition_head),2);

//obtaining modules of the initiolpositionfood
    int modulePosition = (int)Math.pow(
                          (positionX - xPosition_head),2) +
                         (int) Math.pow(
                          (positionY - yPosition_head),2);
//check if the nextposition of the food will be in the range of the grid_size
//
    if((nextPositionFoodx >= 0) && (nextPositionFoodx < grid_size)
    && (nextPositionFoody < grid_size) && (nextPositionFoody >= 0)
    && (grid[nextPositionFoodx][nextPositionFoody].getType() == Cell.CLEAR)
    && (moduleNextPosition >= modulePosition))
    {
      grid[positionX][positionY].setClear();
      grid[nextPositionFoodx][nextPositionFoody].setFood();
      positionX += Direction.xDelta(movedir);
      positionY += Direction.yDelta(movedir);
    }
    else
    {
//turn left y the conditions above where not fulfill
      movedir = Direction.leftTurn(movedir);
/*      if((positionX + Direction.xDelta(movedir) < 0)
      || (positionX + Direction.xDelta(movedir) >= grid_size)
      || (positionY + Direction.yDelta(movedir) >= grid_size)
      || (positionY + Direction.yDelta(movedir) < 0))
      {
        movedir = Direction.rightTurn(movedir);

	     if(grid[positionX + Direction.xDelta(movedir)]
       [positionY + Direction.yDelta(movedir)].getType() == Cell.CLEAR)
	     {
	      grid[positionX][positionY].setClear();
        positionX += Direction.xDelta(movedir);
        positionY += Direction.yDelta(movedir);
         grid[positionX][positionY].setFood();
	     }
      }

*/
    }
  }
//----------------------------------------------------------------
  public String optionalExtras()
  {
//text to be displayed in help box
    return "g: when it is activated a gutter trail appears\n" +
           "b: burn trees in front of you\n" +
           "m: the food will run away from the snake\n";
  } // optionalExtras


  public void optionalExtraInterface(char c)
  {
//if g key is pressed then enable the function gutter trail otherwise turn off
    if( c == 'g')
    {
      if(gEnable)
      {
        gEnable = false;
        setScoreMessage("gutter trail OFF");
      }
      else
      {
        gEnable =  true;
        setScoreMessage("gutter trail ON");
      }
    }
//desroy the tree in fron of the head of the snake
    else if( c == 'b')
    {
      burnTree();
      setScoreMessage("Burn Tree ON");
    }
    else if( c == 'm')
    {
//if m key is pressed then enable the function foodMovement otherwise turn off
      if(mEnable)
      {
        mEnable = false;
        setScoreMessage("food AI OFF");
      }
      else
      {
        mEnable =  true;
        setScoreMessage("food AI ON");
      }
    }
    else if (c > ' ' && c <= '~')
      setScoreMessage("Key " + new Character(c).toString()
                      + " is unrecognised (try h)");
  } // optionalExtraInterface
} // class Game
