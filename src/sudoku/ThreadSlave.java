package sudoku;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Worker Thread
 *
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class ThreadSlave extends Thread {

    Grid work;

    public ThreadSlave(int threadNumber, Grid work) {
        this.setName("Slave" + Integer.toString(threadNumber));
        this.work = work;
    }

    @Override
    public void run() {
        
    }
    
    public boolean backtrack(){
        
        if(work.isEntirelyFilled() && work.)
        ArrayList<Point> emptyCells = work.getEmptyCells();
        Iterator<Point> it = emptyCells.iterator();
        while(it.hasNext()){
            Point current = it.next();
            int iCurrent = (int)current.getX();
            int jCurrent = (int)current.getY();
           ArrayList<Byte> possibleNumber = work.getPossibleNumberAt(iCurrent,jCurrent);
           Iterator<Byte> it2 = possibleNumber.iterator();
           while(it2.hasNext()){
               work.setCell(iCurrent, jCurrent, it2.next());
               backtrack();
               work.setCell(iCurrent, jCurrent, (byte)0);
           }
        }
        return false;
    }
}