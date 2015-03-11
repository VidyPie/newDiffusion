package diffusjon;

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
        Random r = new Random();
        double pop = actualPop;
        double leftLoc = 8;
        for (Iterator<Location> it = adjacentLocations.iterator(); it.hasNext();) {
            double stddev = Math.sqrt(pop * (1/leftLoc) * ((leftLoc - 1)/leftLoc));
            System.out.println("Standard deviation is: " + stddev);
            double mean = pop * 0.125d;
            System.out.println("Mean is: " + mean);
            int moved = (int) (r.nextGaussian() * stddev + mean);
            System.out.println("Moved: " + moved);
            pop = pop - moved;
            leftLoc = leftLoc - 1;
            actualPop = actualPop - (int)moved;
        }
    }

    public void addList(List<Location> adjacentLocationsNew) {
        adjacentLocations = adjacentLocationsNew;
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

    public void placeParticle() {
        actualPop = actualPop + 1;
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
