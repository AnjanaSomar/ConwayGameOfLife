/*
Anjana Somasundaram
January.19.2018
Mr. Radulovic - ICS3U1
Culminating Project
Purpose: This project applies course concepts to generate a cellular automaton that evolves the set of selected cells
Class: This class draws the grid, implements the design templates, and updates the cell as per the rules
*/

package Culminating;

// Imports
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

public class GameOfLife extends Application 
{
	// Accesses other Classes
	private CellState state = new CellState(); 					// Determines if cell is alive or dead
	private DesignElements design = new DesignElements(); 		// Loads GUI elements
	private PageThemes pageTheme= new PageThemes();				// Loads page designs
	
	// Window Dimensions (pixels)
	private final double width = design.getWidth();
	private final double height = design.getHeight();

	// Animation Timer
	private long oldTime;

	// Next generation is updated every 10 counts
	private int updateCounter = 0;

	// Sets dimension of the maze
	private final int numRows = state.getRows();
	private final int numColumns = state.getColumns();

	// Calculates the spacing to accurately position each tile
	private double xSpace;
	private double ySpace;

	// Initializes graphics,pane, and scene
	private GraphicsContext gc;
	private Pane root=new Pane();
	private Scene scene;
	private Stage primarystage = new Stage();

	// Stores mouse coordinates
	private int mouseX;
	private int mouseY;

	// Stores coordinates of the selected tile
	private int tile_x;
	private int tile_y;

	// Initializes GUI elements

	// Buttons
	private Button playBtn; 		// Automatically begins when selected
	private Button selectorBtn; 	// User inputs the number of generations when selected
	private Button doneBtn; 		// Proceeds to the simulator when selected

	// Page themes	
	private ImageView titleImageView; 		// Title image
	private ImageView selectorImageView; 	// Generation selector image

	// Labels
	private Label genSelector;
	
	// Text fields
	private TextField genInput;

	// Determines if the user is in the menu (changes to false when the menu selections are complete)
	private boolean menuState = true;

	// Keeps track of generations
	private int genMax; 		// Determined by the user ( if user does not choose a value, it is randomly calculated)
	private int genCount = 1; 	// Counts the generations
	private Text counterText;	// Provides instruction

	// Random integer generator
	private Random rand = new Random();

	// Identifies if the user is done selecting the seed cells (deactivated with right click)
	private boolean userSelecting = true;

	public static void main(String[] args) 
	{
		// Sets up the app and launches it
		launch(args);
	}

	@Override
	public void start(Stage primarystage1) throws Exception 
	{
		// Updates primarystage
		primarystage = primarystage1;

		// Initializes graphics and canvas
		Canvas canvas = new Canvas(width, height);
		gc = canvas.getGraphicsContext2D();
		
		// Loads menu Buttons
		playBtn = design.playButton();
		selectorBtn = design.selectorButton();
		
		// Loads generation selector GUI elements/buttons
		genInput = design.inputBar();
		doneBtn = design.doneButton();
		genSelector=design.selectorLabel();
		counterText=design.genCounter();
		
		// Loads page themes
		titleImageView = pageTheme.getTitleImageView();
		selectorImageView = pageTheme.getSelectorImageView();

		// Adds GUI elements to pane
		root.getChildren().addAll(titleImageView, canvas);		// Adds title page in background
		root.getChildren().addAll(playBtn, selectorBtn);		// Adds buttons on top

		// Sets the scene style
		root.setStyle("-fx-background: #C8FFF4");
		scene = new Scene(root, width, height, Color.WHITE);
		
		// Activates mouse handler to receive mouse input
		scene.setOnMouseClicked(mouseHandler);
		
		// Mouse only receives input to set seed cells when the user has exited the main menu
		if (menuState == false) 
		{
			scene.setOnMouseClicked(mouseHandler);
		}

		// Sets title
		primarystage.setTitle("Conway's Game of Life");

		// Sets the scene for this stage
		primarystage.setScene(scene);

		// Shows the primary stage
		primarystage.show();

		// Sets up a timer to repeatedly redraw the scene
		oldTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() 
		{
			public void handle(long now) 
			{
				double deltaT = (now - oldTime) / 1000000000.0; // in nanoseconds
				onUpdate(deltaT);
				oldTime = now;
			}
		};

		// When selected, the simulator appears
		playBtn.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				root.getChildren().removeAll(playBtn, selectorBtn, titleImageView); // Removes all the old buttons
				genMax = rand.nextInt(500) + 20; 									// Randomly generates a maximum generation value between 20 and 500
				drawScene(gc);														// Draws the grid
				timer.start(); 														// Starts the timer
			}
		});

		// When selected, the user inputs the desired maximum generation value
		selectorBtn.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				root.getChildren().removeAll(playBtn, selectorBtn, titleImageView); // Removes old buttons
				root.getChildren().addAll(selectorImageView,genSelector);			// Adds new page themes
				root.getChildren().addAll(genInput, doneBtn);  						// Adds new GUI elements
				doneBtn.setOnAction(new EventHandler<ActionEvent>() 				// When selected, the input is stored
				{ 	
					public void handle(ActionEvent event) 
					{
						genMax = Integer.parseInt((genInput.getText()));								// Converts string input to integer
						root.getChildren().removeAll(genInput, doneBtn, selectorImageView,genSelector); // Removes the buttons
						drawScene(gc);																	// Draws the grid
						primarystage.show();
						timer.start();
					}
				});
			}
		});
	}

	private void drawScene(GraphicsContext gc) 	// Draws the grid for the simulator
	{
		// Calculates the Width of the column
		xSpace = width / numColumns;

		// Calculates the height of the row
		ySpace = height / numRows;

		// Sets the line colour and width
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		
		// Sets background to white again
		root.setStyle("-fx-background: #FFFFFF");

		// Draws the rows of the grid
		for (int i = 0; i <= numRows; i += 1) 
		{
			gc.strokeLine(0, i * ySpace, width, i * ySpace);
		}

		// Draws the columns of the grid
		for (int i = 0; i <= numColumns; i += 1) 
		{
			gc.strokeLine(i * xSpace, 0, i * xSpace, height);
		}
	}

	// Receives mouse input to select seed cells
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() 
	{
		public void handle(MouseEvent mouseEvent) 
		{
			MouseButton button = mouseEvent.getButton();
			
			// Allows user to selected seed cells only if they have passed the main menu
			if (menuState == false) 
			{
				// Selects seed cells with a left mouse click
				if ((button == MouseButton.PRIMARY) && userSelecting == true) 
				{
					// Identifies mouse coordinates
					mouseX = (int) mouseEvent.getX();
					mouseY = (int) mouseEvent.getY();

					// Converts mouse coordinates to actual tile positions
					tile_x = (int) (mouseX / xSpace);
					tile_y = (int) (mouseY / ySpace);
					
					// Colours selected cells black
					gc.setFill(Color.BLACK);
					gc.fillRect(tile_x * xSpace, tile_y * ySpace, xSpace, ySpace);
					
					// Sets selected cells to alive
					state.setAlive(tile_x, tile_y);
					
					// Print generation counter on screen
					root.getChildren().remove(counterText);
					counterText.setText(Integer.toString(genCount));
					root.getChildren().add(counterText);

				} 
				
				// Disables selection with right mouse click
				else if (button == MouseButton.SECONDARY) 
				{
					userSelecting = false;
				}
			} 
		}
	};

	// Updates grid from one generation to the next
	private void onUpdate(double deltaT) 
	{
		// Menu state is now set to false as the user has passed it
		menuState = false;
		
		// Updates counter only if the user is done selecting seed cells 
		if (userSelecting == false)
			updateCounter += 1; 	// Updates the grid with new cells every 10 counts

		// Repopulates next generation accordingly
		if (userSelecting == false && updateCounter == 10 && genCount < genMax) 
		{
			for (int a = 0; a < numColumns; a += 1) 
			{
				for (int b = 0; b < numRows; b += 1) 
				{
					if (state.cellState(a, b) == "alive") 
					{
						// Colours cell black if alive
						gc.setFill(Color.BLACK);
					} 
					else if (state.cellState(a, b) == "dead") 
					{
						// Colours cell white if dead
						gc.setFill(Color.WHITE);
					}
					gc.fillRect(a * xSpace, b * ySpace, xSpace, ySpace);
				}
			}
			// Draws the rows of the grid
			for (int i = 0; i <= numRows; i += 1) 
			{
				gc.strokeLine(0, i * ySpace, width, i * ySpace);
			}

			// Draws the columns of the grid
			for (int i = 0; i <= numColumns; i += 1) 
			{
				gc.strokeLine(i * xSpace, 0, i * xSpace, height);
			}

			// Increases generation counts
			genCount += 1;
			
			// Prints the generation count on the screen
			root.getChildren().remove(counterText);
			counterText.setText(Integer.toString(genCount));
			root.getChildren().add(counterText);
		
			// Updates the current generation grid to account for the changes made to the cells
			state.updateGrid();
			
			// Resets animation counter
			updateCounter = 0;
		}

		// If the generation count exceeds the maximum value, the application is exited after 120 counts
		if (genCount == genMax && updateCounter==120) 
		{
			primarystage.close();
		}
	}
}
