
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
        System.out.println("Number of loop");
        foundGrid.show();
        
        //Kill all remaining Threads
        for(int i = 0;i<maxSlave;i++)
            slaves[i].interrupt();
    }
    
    public static ThreadSlave[] getSlaves(){
        return slaves;
    }
    
   
    @Override
    public void run(){
        System.out.println(getName());
        slaves = new ThreadSlave[maxSlave];
        int cpt = 0;
        Iterator<Byte> it = maximalPossibility.iterator();
        while(it.hasNext()){
            byte current = it.next();
           Grid slaveGrid = new Grid(grid);
           slaveGrid.setCellValue(iMax, jMax, current);
           slaves[cpt] = new ThreadSlave(cpt, slaveGrid);
           
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
    
   /* public void prepare(){
        
        int maxPossibility = -1;
        ArrayList<Byte> a;
        int length = grid.getWidth();
        byte cells[][] = grid.getCells();
                
        for(int i =0;i<length;i++)
            for(int j =0;i<length;j++)
              if(cells[i][j]==-1)
              {
                  a = grid.getPossibleNumberAt(i, j);
                  if(a.size()>maxPossibility){
                      maximalPossibility = a;
                      maxPossibility = a.size();
                      iMax = i;
                      jMax = j;
                  }
              }
        maxSlave = maxPossibility;
    }*/
    
    public void prepare(){
       // TODO
    }
}
