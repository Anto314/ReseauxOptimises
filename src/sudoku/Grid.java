package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** A sudoku grid.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class Grid
{
    /** Hold the cells content. */
    private byte _cells[][];
    /** Width of the grid in cells. */
    private int _width = 9;
    /** Height of the grid in cells. */
    private int _height = 9;
    
    /** Used by showDifference() to store the last shown grid. */
    private byte _lastGridCells[][];
    
    /** Create a new grid from a file.
     * @param fileName Name of the file describing the grid.
     */
    public Grid(String fileName) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(fileName));
        
        /** @todo : for now we use a fixed grid size */
        _width = 9;
        _height = 9;
        
        // Create grids
        _cells = new byte[_width][_height];
        _lastGridCells = new byte[_width][_height];
        
        // Read content from file
        for (int row = 0; row < _height; row++)
        {
            for (int column = 0; column < _width; column++)
            {
                byte cellValue;
                try
                {
                     cellValue = (byte) scanner.nextInt();
                }
                catch (Exception exception)
                {
                    throw new RuntimeException("Bad grid file format. " + exception.getMessage());
                }
                        
                _cells[row][column] = cellValue;
                _lastGridCells[row][column] = cellValue;
            }
        }       
    }
    
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
