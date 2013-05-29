package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/** A sudoku grid.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class Grid
{
    /** Hold the cells content. */
    private byte _cells[][];
    /** Width of the grid in cells. */
    private int _width;
    /** Height of the grid in cells. */
    private int _height;
    /** How many numbers are used by the grid. */
    private int _numbersCount;
    /** How many squares are represented in the grid. */
    private int _squaresCount;
    
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
        _numbersCount = _width;
        _squaresCount = 3;
        
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
    
    /** Display grid content to the console. */
    public void show()
    {
        for (int row = 0; row < _height; row++)
        {
            for (int column = 0; column < _width; column++)
            {
                if (_cells[row][column] == 0) System.out.print(". ");
                else System.out.print(_cells[row][column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /** Display the grid and colorize changes between the current grid and the last call of this function. */
    public void showDifferences()
    {
        for (int row = 0; row < _height; row++)
        {
            for (int column = 0; column < _width; column++)
            {
                // Check if there is a difference between this last cell and current cell
                boolean isColorChanged = false;
                if (_lastGridCells[row][column] != _cells[row][column])
                {
                    System.out.print("\u001B[34m"); // Use VT100 escape sequence
                    isColorChanged = true;
                }
                
                // Show value
                if (_cells[row][column] == 0) System.out.print(". ");
                else System.out.print(_cells[row][column] + " ");
                
                // Restore color if needed
                if (isColorChanged) System.out.print("\u001B[0m");
                
                // Update last grid in the same time
                _lastGridCells[row][column] = _cells[row][column];
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /** Get a list of the allowed missing numbers for a specified cell.
     * @param row Cell row coordinate.
     * @param column Cell column coordinate.
     * @return The allowed numbers for this cell.
     */
    public ArrayList<Byte> getPossibleNumberAt(int row, int column)
    {
        // Be sure that the requested cell is empty
        assert _cells[row][column] == 0;
        
        // Tell if a number is existing or not at the end of the process
        boolean existingNumbers[] = new boolean[_numbersCount + 1];
                    
        // List existing numbers on the cell row
        for (int i = 0; i < _width; i++)
        {
            byte cellValue = _cells[row][i];
            if (cellValue != 0) existingNumbers[cellValue] = true;
        }
        
        // List existing numbers on the cell column (it's done separately from cell row in case of different grid width and height)
        for (int i = 0; i < _height; i++)
        {
            byte cellValue = _cells[i][column];
            if (cellValue != 0) existingNumbers[cellValue] = true;
        }
        
        // Find in what square the cell is located
        int squareRowLimit = (row / _squaresCount) + _squaresCount; // Cache loops limit to avoid recalculating them every time
        int squareColumnLimit = (column /_squaresCount) + _squaresCount;
        for (int squareRow = row / _squaresCount; squareRow < squareRowLimit; squareRow++)
        {
            for (int squareColumn = column / _squaresCount; squareColumn < squareColumnLimit; squareColumn++)
            {
                byte cellValue = _cells[squareRow][squareColumn];
                if (cellValue != 0) existingNumbers[cellValue] = true;
            }
        }        
        
        // Fill list with missing numbers
        ArrayList<Byte> result = new ArrayList<Byte>();
        for (byte i = 1; i < existingNumbers.length; i++)
        {
            if (!existingNumbers[i]) result.add(i);
        }
        return result;
    }
    
    /**
     * Compute figure that can be put at cass (i,j) according to Sudoku rule
     *
     * @param i row index
     * @param j column index
     * @return all possible figure
     */
    /*public ArrayList<Byte> getPossibleNumberAt(int i, int j) {
        boolean figureRow[] = new boolean[9];
        boolean figureColumn[] = new boolean[9];
        ArrayList<Byte> possibleNumber = new ArrayList<Byte>();
        for (int k = 0; k < _width; k++) {
            figureRow[k] = false;
            figureColumn[k] = false;
        }
        
        for(int k = 0;k<_width;k++){
            figureRow[_cells[i][k]-1] = true;
            figureColumn[_cells[k][j]-1] = true;  
        }
        
        for(int k =0;k<_width;k++){
            if(!figureRow[k] && !figureColumn[k])
                possibleNumber.add(new Byte((byte)(k+1)));
        }
        return possibleNumber;
    }*/
}
