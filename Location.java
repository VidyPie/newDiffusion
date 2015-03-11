package newDiffusion;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Represent a location in a 3-dimensional coordinate system
 *
 * @VidyPie
 * @version 2015.03.04
 */
public class Location {

    private int x;
    private int y;
    private int actualPop;
    private int newComerPop;
    private List<Location> adjacentLocations;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;

    }

    /**
     * Return a string of the form row,column
     *
     * @return A string representation of the location.
     */
    public String toString() {
        return x + "," + y;
    }

    public void steinLoop() {
        if(actualPop > 0) {
        Random r = new Random();    
        double pop = actualPop;   
        double leftLoc = adjacentLocations.size();
        for (Iterator<Location> it = adjacentLocations.iterator(); it.hasNext();) {
<<<<<<< HEAD
            Location location = it.next();
            if (leftLoc > 1) {
                double stddev = Math.sqrt(pop * (1 / leftLoc) * ((leftLoc - 1) / leftLoc));
                
                //System.out.println("Standard deviation is: " + stddev);
                double mean = pop * (1 / leftLoc);
                
                //System.out.println("Mean is: " + mean);
                double movedT = (r.nextGaussian() * stddev + mean);
                int moved = (int) movedT;
                System.out.println(movedT);
                System.out.println(moved);
                System.out.println("");
                pop = pop - moved;

                location.placeParticle(moved);
                leftLoc = leftLoc - 1;
                actualPop = actualPop - moved;
                
            }
            else {
                location.placeParticle(actualPop);
                actualPop = 0;
            }
        }
=======
            double stddev = Math.sqrt(pop * (1/leftLoc) * ((leftLoc - 1)/leftLoc));
            System.out.println("Standard deviation is: " + stddev);
            double mean = pop * (1/leftLoc);
            System.out.println("Mean is: " + mean);
            int moved = (int) (r.nextGaussian() * stddev + mean);
            System.out.println("Moved: " + moved);
            pop = pop - moved;
            leftLoc = leftLoc - 1;
            actualPop = actualPop - (int)moved;
>>>>>>> ac36ea6a10da066615744373109aba2fdd0318cf
        }
    }

    public void addList(List<Location> adjacentLocationsNew) {
        adjacentLocations = adjacentLocationsNew;
    }
    
    public void trix() {
        actualPop = newComerPop;
    }
    
    public void setAdjacentLoc(List<Location> adjLoc) {
        adjacentLocations = adjLoc;
    }
    
    public int getPop() {
        return actualPop;
    }
    /**
     * @return The row.
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void placeParticle(int newParticles) {
        newComerPop = newComerPop + newParticles;
    }

    public void removeParticle() {
        actualPop = actualPop - 1;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

}
