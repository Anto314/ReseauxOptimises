package sudoku.monothreaded;

import java.awt.Point;
import java.util.ArrayList;
import sudoku.Grid;

/** Monothreaded backtrack solving algorithm.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class Backtrack 
{
    /** The grid to solve. */
    private Grid _grid;
    /** How many loops were done. */
    private long _loopsCount;
    
    /** Initialize the backtrack solver.
     * @param grid The grid to solve.
     */
    public Backtrack(Grid grid)
    {
        assert grid != null;
        _grid = grid;
        _loopsCount = 1;
    }
    
    /** Get the grid used by this backtrack.
     * @return The grid.
     */
    public Grid getGrid()
    {
        return _grid;
    }
    
    /** Launch solving algorithm.
     * @return true if the grid was solved or false if not.
     */
    public boolean solve()
    {
        // Find an empty cell
        Point cellCoordinates = _grid.getFirstEmptyCell();
        if (cellCoordinates == null) // No empty cell remain
        {
            // Is the found grid correct ?
            if (_grid.isCorrectlyFilled()) return true;
            return false;
        }
        int cellRow = cellCoordinates.y;
        int cellColumn = cellCoordinates.x;
                    
        // Get available numbers for this cell
        ArrayList<Byte> availableNumbers = _grid.getCellMissingNumbers(cellRow, cellColumn);
        // If no number is available a bad grid has been generated...
        if (availableNumbers.isEmpty()) return false; // Go up in the tree
        
        // Try each available number
        while (!availableNumbers.isEmpty())
        {
            // Extract the number to try
            byte number = availableNumbers.get(0);
            availableNumbers.remove(0);
            
            // Try the number
            _grid.setCellValue(cellRow, cellColumn, number);
            
            _loopsCount++;
            
            // Go back in the tree if the generated grid is bad
            if (!_grid.isCorrectlyFilled())
            {
                _grid.setCellValue(cellRow, cellColumn, Grid.EMPTY_CELL_VALUE);
                return false;
            }
            
            // Simulate next grid state
            if (solve()) return true; // The grid has been solved, go to tree root
            // A bad solution was found, restore cell value
            _grid.setCellValue(cellRow, cellColumn, Grid.EMPTY_CELL_VALUE);
        }
        // All numbers have been tested unsuccessfully, go back into the tree
        return false;
    }
    
    /** How many loops were done by the backtrack.
     * @return The loops count.
     */
    public long getLoopsCount()
    {
        return _loopsCount;
    }
}
