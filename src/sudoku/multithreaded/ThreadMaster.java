
package sudoku.multithreaded;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import sudoku.Grid;

/** A thread which give work to ThreadSlave
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class ThreadMaster extends Thread{
    
    
    /**
     * row index of maximalPossibility
     */
    private int iMax;
    
    /**
     * column index of maximalPossibility
     */
    
    private int jMax;
    /**
     * Maximal possibility of number
     */
    private ArrayList<Byte> maximalPossibility;
    /**
     * Maximal number of slave which could be created
     */
    private static int maxSlave;

    /**
     * Sudoku grid
     */
    Grid grid;
    
    /**
     * all ThreadSlave handled by the ThreadMaster
     */
    private static ThreadSlave slaves[];
     /**
     * Create a new ThreadMaster
     * @param name name given to this Thread
     * @param grid sudoku grid
     */
    public ThreadMaster(String name,Grid grid){
        this.setName(name);
        maxSlave = 10;
        this.grid = grid;
    }
    /**
     * Create a new ThreadMaster
     * @param name name given to this Thread
     * @param maxSlave  maximal number of slave
     * @param grid  sudoku grid
     */
    public ThreadMaster(String name,int maxSlave,Grid grid){
        this.setName(name);
        this.maxSlave = maxSlave;
        this.grid = grid;
    }
    
    /**
     * Commentaire à mettre
     * @param foundGrid
     * @param loopsCount 
     */
    public static void notifySolutionFound(Grid foundGrid, long loopsCount)
    {
        System.out.println("Correct Grid Found");
        System.out.println("Number of loop : "+loopsCount);
        foundGrid.show();
        
        //Kill all remaining Threads
        for(int i = 0;i<maxSlave;i++){
            if(slaves[i].isAlive())
                slaves[i].interrupt();
        }
    }
    
    public static ThreadSlave[] getSlaves(){
        return slaves;
    }
    
   
    @Override
    public void run(){
        int cpt = 0;
        prepare();
        System.out.println(getName());
        slaves = new ThreadSlave[maxSlave];
        Iterator<Byte> it = maximalPossibility.iterator();
        while(it.hasNext()){
           byte current = it.next();
           Grid slaveGrid = new Grid(grid);
           slaveGrid.setCellValue(iMax, jMax, current);
           slaves[cpt] = new ThreadSlave(cpt, slaveGrid);
           cpt++;
        }
        for(int i =0;i<maxSlave;i++){
            slaves[i].start();
        }
            try {
                for(int i = 0;i<maxSlave;i++){
                    slaves[i].join();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.println("Finish");
    }
    
   public void prepare(){
        
        int maxPossibility = -1;
        ArrayList<Byte> numbers;
        int length = grid.getGridSize();
                
        for(int i =0;i<length;i++)
            for(int j =0;j<length;j++){
                  numbers = grid.getCellMissingNumbers(i, j);
                  if(numbers!=null && numbers.size()>maxPossibility){
                      
                      maximalPossibility = numbers;
                      maxPossibility = numbers.size();
                      iMax = i;
                      jMax = j;
                  }
              }
        maxSlave = maxPossibility;
    }
    
    
}
