
package sudoku;

import java.util.logging.Level;
import java.util.logging.Logger;

/** A thread which give work to ThreadSlave
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class ThreadMaster extends Thread{
    
    /**
     * Maximal number of slave which could be created
     */
    private int maxSlave;

    /**
     * Create a new ThreadMaster
     * @param name name given to this Thread
     */
    public ThreadMaster(String name){
        this.setName(name);
        maxSlave = 10;
    }
    /**
     * Create a new ThreadMaster
     * @param name name given to this Thread
     * @param maxSlave  maximal number of slave
     */
    public ThreadMaster(String name,int maxSlave){
        this.setName(name);
        this.maxSlave = maxSlave;
    }
    
    public int getMaxSlave() {
        return maxSlave;
    }

    public void setMaxSlave(int maxSlave) {
        this.maxSlave = maxSlave;
    }
    @Override
    public void run(){
        System.out.println(getName());
        ThreadSlave slaves[] = new ThreadSlave[maxSlave];
        for(int i = 0;i<maxSlave;i++){
            slaves[i] = new ThreadSlave(i);
            slaves[i].start();
            try {
                slaves[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
