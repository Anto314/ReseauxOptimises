
package sudoku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private int maxSlave;

    /**
     * Sudoku grid
     */
    Grid grid;
    
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
        int cpt = 0;
        Iterator<Byte> it = maximalPossibility.iterator();
        while(it.hasNext()){
            byte current = it.next();
            
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
        int maxPossibility = -1;
        int width = grid.getWidth();
        int height = grid.getHeight();
        
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {    
                ArrayList<Byte> a = grid.getPossibleNumberAt(i, j);
                if (a == null) continue; // Cell is filled yet
                
                if(a.size()>maxPossibility){
                    maximalPossibility = a;
                    maxPossibility = a.size();
                    iMax = i;
                    jMax = j;
                }
            }
        }
        maxSlave = maxPossibility;
    }
}
