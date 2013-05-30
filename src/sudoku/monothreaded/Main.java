package sudoku.monothreaded;

import java.io.FileNotFoundException;
import sudoku.Grid;

/** Initialize program and start solving job.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class Main 
{
    public static void main(String args[])
    {
        // Show title
        System.out.println("+---------------------------+");
        System.out.println("| SudokuSolver monothreaded |");
        System.out.println("+---------------------------+");
        
        // Assertions must be enabled
        try
        {
            assert false;
            System.out.println("Error : assertions must be enabled (add -ea to java command line).");
            return;
        }
        catch (AssertionError error) {}
                
        // Check parameters
        if (args.length != 1)
        {
            System.out.println("Error : bad arguments.");
            System.out.println("Usage : Sudoku Grid_File_Name");
            return;
        }
        
        // Try to load the provided grid file
        Grid grid;
        try
        {
            grid = new Grid(args[0]);
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Error : can't find '" + args[0]);
            return;
        }
        
        // Show file name
        System.out.println("File : " + args[0] + "\n");
        // Show grid to solve
        grid.show();
                
        // Start solving
        Backtrack backtrack = new Backtrack(grid);
        if (backtrack.solve())
        {
            System.out.println("Grid successfully solved in " + backtrack.getLoopsCount() + " loops.");
        }
        else
        {
            System.out.println("Failure : can't solve this grid.");
            System.out.println("Found grid :");
        }
        backtrack.getGrid().show();
    }
}
