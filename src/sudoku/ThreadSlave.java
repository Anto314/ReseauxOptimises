package sudoku;

/** A Worker Thread
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class ThreadSlave extends Thread{
    
    Grid work;
    public ThreadSlave(int threadNumber,Grid work){
        this.setName("Slave"+Integer.toString(threadNumber));
        this.work = work;
    }
    
    @Override
    public void run(){
        
    }
}