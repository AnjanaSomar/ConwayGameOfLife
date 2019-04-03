/*
Anjana Somasundaram
January.19.2018
Mr. Radulovic - ICS3U1
Culminating Project
Purpose: This project applies course concepts to generate a cellular automaton that evolves the set of selected cells
Class: This class loads all the design elements
*/
package Culminating;

// Imports
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DesignElements 
{
	// Initializes menu buttons and labels
	private Button play = new Button();
	private Button genSelector= new Button();
	private Button done = new Button();
	private TextField genInput = new TextField();
	private Label selectorLabel;
	private Text genCounter=new Text();
	
	// Sets font styles
	private String style1="-fx-font-size: 20pt;";
	private String style2="-fx-font-size: 15pt;";
	
	// Initializes screen dimensions
	private final double width = 800;
	private final double height = 800;

	// Returns the play button
	public Button playButton() 
	{
		play.setMinWidth(0.25*width);
		play.setText("Play");
		play.setLayoutY((height/2)+(height/4));
		play.setLayoutX((width-play.getMinWidth())/2);
		play.setStyle(style1);
		return play;
	}
	
	// Returns the selector button
	public Button selectorButton() 
	{
		genSelector.setText("Selector");
		genSelector.setMinWidth(0.25*width);
		genSelector.setLayoutY((height/2)+(height/4)+(height/10));
		genSelector.setLayoutX((width-play.getMinWidth())/2);
		genSelector.setStyle(style1);
		return genSelector;
	}
	
	// Returns the done button
	public Button doneButton()
	{
		done.setText("Done");
		done.setLayoutX(0.48*width);
		done.setLayoutY(height/2);
		done.setMinWidth(width/25);
		done.setStyle(style2);
		return done;
	}
	
	// Returns the generation selector's label
	public Label selectorLabel() 
	{
		selectorLabel = new Label("Enter the desired number of generations below:");
		selectorLabel.setStyle(style2);
		selectorLabel.setLayoutX(width/4);
		selectorLabel.setLayoutY(0.375*800);
		return selectorLabel;
	}
	
	// Returns the generation counter text
	public Text genCounter()
	{
		genCounter.setX(0.01875*width);
		genCounter.setY(0.05625*height);
		genCounter.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
		genCounter.setFill(Color.PURPLE);
		return genCounter;
		
	}
	
	// Returns generation selector's input bar
	public TextField inputBar() 
	{
		genInput.setPrefWidth(width/3);
		genInput.setLayoutX(0.3625*width);
		genInput.setLayoutY(0.45*height);
		return genInput;
	}
	
	// Returns the width of the window
	public double getWidth()
	{
		return width;
	}
	
	// Returns the height of the window
	public double getHeight()
	{
		return height;
	}
}
