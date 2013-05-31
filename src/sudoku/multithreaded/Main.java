package sudoku.multithreaded;

import java.io.FileNotFoundException;
import sudoku.Backtrack;
import sudoku.Grid;

/** Initialize program and start solving job.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class Main 
{
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
        
            // TEST
            ThreadSlave slave = new ThreadSlave(0, grid);
            slave.start();
            
            try
            {
                Thread.sleep(4);
            }
            catch (InterruptedException e) {}
            slave.interrupt();
            
            try
            {
                slave.join();
            }
            catch (InterruptedException e) {}
             
            if (slave.isGridSolved())
            {
                System.out.println("Grid successfully solved in " + slave.getLoopsCount() + " loops.");
                slave.getGrid().show();
                System.exit(0); // Useful for tests to indicate a solving success
            }
            else
            {
                System.out.println("Failure : can't solve this grid.");
                System.out.println("Found grid :");
                slave.getGrid().show();
                System.exit(-1);
            }   
        
        
        // Start solving
        /*ThreadMaster tm = new ThreadMaster("Master", grid);
        tm.start();
        
            // TEST
            // Wait for master end
            try
            {
                tm.join();
            }
            catch (InterruptedException e) {}*/
        
        /*Backtrack backtrack = new Backtrack(grid);
        if (backtrack.solve())
        {
            System.out.println("Grid successfully solved in " + backtrack.getLoopsCount() + " loops.");
            backtrack.getGrid().show();
            System.exit(0); // Useful for tests to indicate a solving success
        }
        else
        {
            System.out.println("Failure : can't solve this grid.");
            System.out.println("Found grid :");
            backtrack.getGrid().show();
            System.exit(-1);
        }*/
    }
}
