package sudoku;

/** A Worker Thread
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class ThreadSlave extends Thread{
    
    
    public ThreadSlave(int threadNumber){
        this.setName("Slave"+Integer.toString(threadNumber));
    }
    
}
