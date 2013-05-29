package tests;

/** Do some tests automatically.
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class TestsMain
{
    public static void main(String args[])
    {
        // Assertions must be enabled
        try
        {
            assert false;
            throw new RuntimeException("Assertions must be enabled.");
        }
        catch (AssertionError error) {}
        
        System.out.println("Grid tests");
        // Check getMissingNumber() methods
        
    }
}
