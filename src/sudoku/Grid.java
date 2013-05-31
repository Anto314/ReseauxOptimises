package sudoku;

import java.awt.Point;
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
    /** Size of the grid in cells. */
    private int _gridSize;

    public int getGridSize() {
        return _gridSize;
    }

    public void setGridSize(int _gridSize) {
        this._gridSize = _gridSize;
    }
    /** Width of a square in cells. */
    private int _squareWidth;
    /** Height of a square in cells. */
    private int _squareHeight;
    
    /** Used by showDifference() to store the last shown grid. */
    private byte _lastGridCells[][];
    
    /** An empty cell is represented by this value. */
    public static final byte EMPTY_CELL_VALUE = 0;
    
    /** Create a new grid from a file.
     * @param fileName Name of the file describing the grid.
     */
    public Grid(String fileName) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(fileName));
        
        // Retrieve grid parameters
        _gridSize = scanner.nextInt();
        _squareWidth = scanner.nextInt();
        _squareHeight = scanner.nextInt();
        
        // Create grids
        _cells = new byte[_gridSize][_gridSize];
        _lastGridCells = new byte[_gridSize][_gridSize];
        
        // Read content from file
        for (int row = 0; row < _gridSize; row++)
        {
            for (int column = 0; column < _gridSize; column++)
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
        scanner.close();
    }
    
    /** Create a new grid filled with the content of another.
     * @param grid The grid to copy content from.
     */
    public Grid(Grid grid)
    {
        _gridSize = grid._gridSize;
        _squareWidth = grid._squareWidth;
        _squareHeight = grid._squareHeight;
        _cells = new byte[_gridSize][_gridSize];
        _lastGridCells = new byte[_gridSize][_gridSize];
        for (int row = 0; row < _gridSize; row++)
        {
            for (int column = 0; column < _gridSize; column++)
            {
                _cells[row][column] = grid._cells[row][column];
                _lastGridCells[row][column] = grid._lastGridCells[row][column];
            }
        }
    }
    
    /** Display grid content to the console. */
    public void show()
    {
        for (int row = 0; row < _gridSize; row++)
        {
            for (int column = 0; column < _gridSize; column++)
            {
                if (_cells[row][column] == EMPTY_CELL_VALUE) System.out.print(" . ");
                else System.out.printf("%2d ", _cells[row][column]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /** Display the grid and colorize changes between the current grid and the last call of this function. */
    public void showDifferences()
    {
        for (int row = 0; row < _gridSize; row++)
        {
            for (int column = 0; column < _gridSize; column++)
            {
                // Check if there is a difference between this last cell and current cell
                boolean hasColorChanged = false;
                if (_lastGridCells[row][column] != _cells[row][column])
                {
                    System.out.print("\u001B[34m"); // Use VT100 escape sequence
                    hasColorChanged = true;
                }
                
                // Show value
                if (_cells[row][column] == EMPTY_CELL_VALUE) System.out.print(". ");
                else System.out.printf("%2d ", _cells[row][column]);
                
                // Restore color if needed
                if (hasColorChanged) System.out.print("\u001B[0m");
                
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
     * @return The allowed numbers for this cell or null if the cell is not empty.
     */
    public ArrayList<Byte> getCellMissingNumbers(int row, int column)
    {
        // Check if the cell is empty
        if (_cells[row][column] != EMPTY_CELL_VALUE) return null;
        
        // Tell if a number is existing or not at the end of the process
        boolean existingNumbers[] = new boolean[_gridSize + 1]; // The +1 is needed because the numbers we search are 1-based
                    
        // List existing numbers on the cell row
        for (int i = 0; i < _gridSize; i++)
        {
            byte cellValue = _cells[row][i];
            existingNumbers[cellValue] = true;
        }
        
        // List existing numbers on the cell column (it's done separately from cell row in case of different grid width and height)
        for (int i = 0; i < _gridSize; i++)
        {
            byte cellValue = _cells[i][column];
            existingNumbers[cellValue] = true;
        }
        
        // Find square bounds in which the cell is located
        int squareRowStart = (row / _squareHeight) * _squareHeight; // Do an euclidian division
        int squareColumnStart = (column / _squareWidth) * _squareWidth;
        int squareRowEnd = squareRowStart + _squareHeight;
        int squareColumnEnd = squareColumnStart + _squareWidth;
        
        for (int squareRow = squareRowStart; squareRow < squareRowEnd; squareRow++)
        {
            for (int squareColumn = squareColumnStart; squareColumn < squareColumnEnd; squareColumn++)
            {
                byte cellValue = _cells[squareRow][squareColumn];
                existingNumbers[cellValue] = true;
            }
        }        
        
        // Fill list with missing numbers
        ArrayList<Byte> result = new ArrayList<Byte>();
        for (byte i = 1; i < existingNumbers.length; i++) // numbers in the grid are 1-based so we start with 1
        {
            if (!existingNumbers[i]) result.add(i);
        }
        return result;
    }
    
    
    
    /** Set a cell value.
     * @param row Cell row coordinate.
     * @param column Cell column coordinate.
     * @param value Cell value.
     */
    public void setCellValue(int row, int column, byte value)
    {
        assert row >= 0;
        assert row < _gridSize;
        assert column >= 0;
        assert column < _gridSize;
        _cells[row][column] = value;
    }
    
    /** Tell if the grid is correctly filled according to Sudoku rules.
     * This function does not care about empty cells.
     * @return true if the grid is correctly filled or false if there is a mistake.
     */
    public boolean isCorrectlyFilled()
    {
        boolean isNumberFound[] = new boolean[_gridSize + 1]; // Grid numbers are 1-based
        
        // Check each row to find one and only one instance of each number
        for (int row = 0; row < _gridSize; row++)
        {
            // Reset array
            for (int i = 0; i < isNumberFound.length; i++) isNumberFound[i] = false;
            
            for (int column = 0; column < _gridSize; column++)
            {
                byte cellValue = _cells[row][column];
                if (cellValue == EMPTY_CELL_VALUE) continue; // Avoid empty cells
                if (isNumberFound[cellValue]) return false;
                isNumberFound[cellValue] = true;
            }
        }
        
        // Check each column to find one and only one instance of each number
        for (int column = 0; column < _gridSize; column++)
        {
            // Reset array
            for (int i = 0; i < isNumberFound.length; i++) isNumberFound[i] = false;
            
            for (int row = 0; row < _gridSize; row++)
            {
                byte cellValue = _cells[row][column];
                if (cellValue == EMPTY_CELL_VALUE) continue; // Avoid empty cells
                if (isNumberFound[cellValue]) return false;
                isNumberFound[cellValue] = true;
            }
        }
        
        // Check each square to find one and only one instance of each number
        for (int row = 0; row < _gridSize; row++)
        {
            for (int column = 0; column < _gridSize; column++)
            {
                // Reset array
                for (int i = 0; i < isNumberFound.length; i++) isNumberFound[i] = false;
                
                // Find square bounds in which the cell is located
                int squareRowStart = (row / _squareHeight) * _squareHeight; // Do an euclidian division
                int squareColumnStart = (column / _squareWidth) * _squareWidth;
                int squareRowEnd = squareRowStart + _squareHeight;
                int squareColumnEnd = squareColumnStart + _squareWidth;
                
                // Find numbers present into the square
                for (int squareRow = squareRowStart; squareRow < squareRowEnd; squareRow++)
                {
                    for (int squareColumn = squareColumnStart; squareColumn < squareColumnEnd; squareColumn++)
                    {
                        byte cellValue = _cells[squareRow][squareColumn];
                        if (cellValue == EMPTY_CELL_VALUE) continue; // Avoid empty cells
                        if (isNumberFound[cellValue]) return false;
                        isNumberFound[cellValue] = true;
                    }
                }
            }
        }
        return true;
    }
    
    /** Get all empty cells of the grid.
     * @return A list of points (x = column; y = row) representing all empty cells.
     */
    /*public ArrayList<Point> getEmptyCells()
    {
        ArrayList<Point> result = new ArrayList<Point>();
        
        for (int row = 0; row < _gridSize; row++)
        {
            for (int column = 0; column < _gridSize; column++)
            {
                if (_cells[row][column] == 0) result.add(new Point(column, row));
            }
        }
        return result;
    }*/
    
    /** Retrive the coordinates of the first empty cell.
     * @return null if all grid cells are filled or the coordinates (x = column; y = row) of the first empty cell.
     */
    public Point getFirstEmptyCell()
    {
        for (int row = 0; row < _gridSize; row++)
        {
            for (int column = 0; column < _gridSize; column++)
            {
                if (_cells[row][column] == EMPTY_CELL_VALUE) return new Point(column, row);
            }
        }
        return null; // All cells are filled in
    }
}
