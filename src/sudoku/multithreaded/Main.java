package sudoku.multithreaded;

import java.io.FileNotFoundException;
import sudoku.Backtrack;
import sudoku.Grid;

/** Initialize program and start solving job.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class Main 
{
    /** Set to true to turn on debug stuff. */
    public static final boolean DEBUG = false;
    
    public static void main(String args[])
    {
        // Show title
        System.out.println("+----------------------------+");
        System.out.println("| SudokuSolver multithreaded |");
        System.out.println("+----------------------------+");
        
        // Assertions must be enabled
        try
        {
            assert false;
            System.out.println("Error : assertions must be enabled (add -ea to java command line).");
            System.exit(-1);
        }
        catch (AssertionError error) {}
                
        // Check parameters
        if (args.length != 1)
        {
            System.out.println("Error : bad arguments.");
            System.out.println("Usage : Sudoku Grid_File_Name");
            System.exit(-1);
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
            System.exit(-1);
            return; // NetBeans does not allow the use of System.exit() alone (it believes that grid variable is not initialized)
        }
        
        // Show file name
        System.out.println("File : " + args[0] + "\n");
        // Show grid to solve
        grid.show();
        
        // Start solving
        ThreadMaster tm = new ThreadMaster("Master", grid);
        tm.start();
        
        // Wait for master end
        try
        {
            tm.join();
        }
        catch (InterruptedException e) {}
    }
}
