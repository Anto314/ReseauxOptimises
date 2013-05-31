package sudoku.multithreaded;

import sudoku.Backtrack;
import sudoku.Grid;

/**
 * A Worker Thread
 *
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class ThreadSlave extends Thread 
{
    /** This the same version than the monothreaded backtrack except that it can be interrupted at any time. */
    private class BacktrackInterruptible extends Backtrack
    {
        /** Create a new interruptible backtrack solving algorithm.
         * @param grid The grid to solve.
         */
        public BacktrackInterruptible(Grid grid)
        {
            super(grid);
        }
        
        @Override
        public boolean solve()
        {
            if (interrupted()) return true; // Go to tree root (we don't care about the returned result as the solution has been found by another thread)
            return super.solve();
        }
    }

    /** The backtrack solving job done by this thread. */
    private Backtrack _backtrack;
    
    /** Create a slave thread.
     * @param threadNumber A number identifying the thread for debugging.
     * @param work The grid to work with.
     */
    public ThreadSlave(int threadNumber, Grid work) {
        this.setName("Slave" + Integer.toString(threadNumber));
        _backtrack = new BacktrackInterruptible(work);
    }

    @Override
    public void run() 
    {
        // Only the thread which found the solution can tell the master
        if (_backtrack.solve()) ThreadMaster.notifySolutionFound(_backtrack.getGrid(), _backtrack.getLoopsCount());
    }
}
    