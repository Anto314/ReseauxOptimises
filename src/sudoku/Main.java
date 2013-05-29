package sudoku;

import java.io.FileNotFoundException;

/** Initialize program and start solving job.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class Main 
{
    public static void main(String args[])
    {
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
            System.out.println("Error : can't find '" + args[0] + "' grid file" + exception.getMessage());
            return;
        }
        
                // TEST
                grid.showDifferences();
                grid.showDifferences();
        
        
    }
}
