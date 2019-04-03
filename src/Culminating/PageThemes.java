/*
Anjana Somasundaram
January.19.2018
Mr. Radulovic - ICS3U1
Culminating Project
Purpose: This project applies course concepts to generate a cellular automaton that evolves the set of selected cells
Class: This class loads the themes and designs of the menu pages
*/

package Culminating;

// Imports
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PageThemes 
{
	// Loads screen dimensions
	private DesignElements designElement = new DesignElements();
	private final double width = designElement.getWidth();
	private final double height = designElement.getHeight();
	
	// Loads the necessary images/templates
	private final Image titleImage;
	private final ImageView titleImageView;
	private final Image selectorImage;
	private final ImageView selectorImageView;
	
	//Loads files
	private final String titleFilename = "file:resources//images//TitlePage.png";
	private final String selectorFilename = "file:resources//images//SelectorPage.png";

	public PageThemes() 
	{
		// Loads the title page and sets the width to correspond with the window dimensions
		titleImage = new Image(titleFilename);
		titleImageView = new ImageView();
		titleImageView.setImage(titleImage);
		titleImageView.setFitWidth(width);
		titleImageView.setPreserveRatio(true);

		// Loads the generation selector page and sets the width to correspond with the window dimensions
		selectorImage = new Image(selectorFilename);
		selectorImageView = new ImageView();
		selectorImageView.setImage(selectorImage);
		selectorImageView.setFitHeight(height);
		selectorImageView.setPreserveRatio(true);
		selectorImageView.setX(width / 8);
	}

	// Returns title page
	public ImageView getTitleImageView() 
	{
		return titleImageView;
	}

	// Retuns generation selector page
	public ImageView getSelectorImageView() 
	{
		return selectorImageView;
	}
}