/*
Anjana Somasundaram
January.19.2018
Mr. Radulovic - ICS3U1
Culminating Project
Purpose: This project applies course concepts to generate a cellular automaton that evolves the set of selected cells
Class: This class determines the state of the cell (alive/dead) dependent on the number of neighbours
*/

package Culminating;

public class CellState 
{	
	// Initializes grid dimensions
	private final int numRows = 100;
	private final int numColumns = 100;
	
	//Initializes current generation 2D array
	private int[][] genCurrent = new int[numColumns][numRows];
	{
		for (int x = 0; x < numColumns; x += 1) 
		{
			for (int y = 0; y < numRows; y += 1)
				genCurrent[x][y] = 0;			// 0 represents a dead cell
		}
	}

	//Initializes next generation 2D array
	private int[][] genNext = new int[numColumns][numRows];
	{
		for (int x = 0; x < numColumns; x += 1) 
		{
			for (int y = 0; y < numRows; y += 1)
				genNext[x][y] = 0;
		}
	}

	// Sets the neighbour count to 0
	private int numNeighbours = 0;

	// Alive cells are represented as 1 in the gird 
	public void setAlive(int x, int y) 
	{
		genCurrent[x][y] = 1;
	}

	// Sets the cell to alive in the next generation (1)
	public void setAliveNext(int x, int y) 
	{
		genNext[x][y] = 1;
	}

	// Sets the cell to dead in the next generation (0)
	public void setDeadNext(int x, int y) 
	{
		genNext[x][y] = 0;
	}
	
	// Calculates the number of neighbours to determine the state
	public int neighbourCount(int x, int y) 
	{
		numNeighbours = 0;
		// Uses modulos to wrap around the grid such that the top most cell neighbours the bottom most cell and the left most cell
		// neighbours the right most cell (prevents negative values)
		
		// Checks the northwest cell
		if (genCurrent[(x - 1 + numRows) % numRows][(y - 1 + numColumns) % numColumns] == 1) 
		{
			numNeighbours++;
		}
		// Checks the cell to the left
		if (genCurrent[(x- 1 + numRows) % numRows][y] == 1) 
		{
			numNeighbours++;
		}
		// Checks the southwest cell
		if (genCurrent[(x - 1 + numRows) % numRows][(y + 1) % numColumns] == 1) 
		{
			numNeighbours++;
		}
		// Checks the cell to the top
		if (genCurrent[x][(y - 1 + numColumns) % numColumns] == 1) 
		{
			numNeighbours++;
		}
		// Checks the cell below
		if (genCurrent[x][(y + 1) % numColumns] == 1)
		{
			numNeighbours++;
		}
		// Checks the northeast cell
		if (genCurrent[(x + 1) % numRows][(y - 1 + numColumns) % numColumns] == 1) 
		{
			numNeighbours++;
		}
		// Checks the cell to the right
		if (genCurrent[(x + 1) % numRows][y] == 1) 
		{
			numNeighbours++;
		}
		// Checks the  southeast cell
		if (genCurrent[(x + 1) % numRows][(y + 1) % numColumns] == 1) 
		{
			numNeighbours++;
		}
		return numNeighbours;
	}

	public String cellState(int x, int y) 
	{
		String state = null;
		int currentState = genCurrent[x][y]; 		// Identifies current state
		if (currentState == 1) 						// Determines the next generation state of live cells
		{
			// Dies due to underpopulation
			if (neighbourCount(x, y) < 2) 
			{
				setDeadNext(x, y);
				state = "dead";
			}
			// Lives on to the next generation
			if (neighbourCount(x, y) == 2 || neighbourCount(x, y) == 3) 
			{
				setAliveNext(x, y);
				state = "alive";
			}
			// Dies due to overpopulation
			if (neighbourCount(x, y) > 3) 
			{
				setDeadNext(x, y);
				state = "dead";
			}
		}

		else if (currentState == 0) 				// Determines the next generation state of dead cells
		{
			if (neighbourCount(x, y) == 3) 
			{
				// Becomes alive
				setAliveNext(x, y);
				state = "alive";
			}
			else 
			{
				// Remains dead
				setDeadNext(x, y);
				state = "dead";
			}
		}
		return state;
	}

	// Updates 2D array to represent cell changes
	public void updateGrid() 
	{
		genCurrent = genNext;
		genNext = new int[numRows][numColumns];
	}
	
	// Returns the number of rows
	public int getRows() 
	{
		return numRows;
	}
	
	// Returns the number of columns
	public int getColumns() 
	{
		return numColumns;
	}
}
