# ConwayGameOfLife
Game Overview: 

Launch the app to begin the game. Use the mouse to select one of two options; play or generation selector. The generation selector will allow you to select the number of generations you wish to simulate. Enter this value in the provided space and click done once you have typed your response. If you choose the play option instead, the program will directly proceed to the simulator. The simulator will operate for a randomly selected number of generations, unless otherwise indicated. Select the seed cells with a left mouse click. Once you are done, click the right side of your mouse to begin simulating the evolution of the cells. After the desired number of generations is reached, the program will automatically close on its own. 

Rules of Evolution: Every cell has 8 neighbours and interacts with the cells that are horizontally, vertically, or diagonally adjacent to it. The initial pattern is the first generation. Future generations evolve by applying the following rules iteratively: 

1. Any live cell with fewer than two live neighbours dies due to underpopulation 
2. Any live cell with two or three live neighbours lives on to the next generation 
3. Any live cell with more than three live neighbours dies due to overpopulation 
4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction 
