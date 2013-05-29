package sudoku;

import java.io.FileNotFoundException;
import java.io.FileReader;

/** A sudoku grid.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
/** Small Test for commit */
public class Grid
{
    /** Hold the cells content. */
    private byte _cells[][];
    /** Width of the grid in cells. */
    private int _width = 9;
    /** Height of the grid in cells. */
    private int _height = 9;
    
    /** Grid used by showDifference() function. */
    private Grid _lastGrid;
    
    /** Create a new grid from a file.
     * @param fileName Name of the file describing the grid.
     */
    public Grid(String fileName) throws FileNotFoundException
    {
        FileReader fileReader = new FileReader(fileName);
                
        
        // Read the file content
       /* for (int row = 0; row < HEIGHT; row++)
        {
            for (int column = 0; column < WIDTH; column++)
            {
                
            }
        }*/
        
    }
    
    public Grid() {_width = 9; _height = 9; _cells = new byte [9][9];}
    
    /** Display grid content to the console */
    public void show()
    {
        for (int row = 0; row < _height; row++)
        {
            for (int column = 0; column < _width; column++) System.out.print(_cells[row][column] + " ");
            System.out.println();
        }
        System.out.println();
    }
    
    /** Display the grid and colorize changes between the current grid and the last call of this function. */
    public void showDifferences()
    {
   
    
    }
}
