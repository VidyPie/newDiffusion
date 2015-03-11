package newDiffusion;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represent a coordinate system
 * 
 * @author VidyPie
 * @version 2015.03.04
 */
public class ParticleSystem
{
    private int xsize, ysize;
    private Particle[][] system;
    private HashMap<Location, Particle> p;
    public ParticleSystem(int x, int y)
    {
        this.xsize = x;
        this.ysize = y;
        p = new HashMap<Location, Particle>();
        system = new Particle[x][y];
    }
    
    public void clear()
    {
        for(int ax = 0; ax < xsize; ax++) {
            for(int ay = 0; ay < ysize; ay++) {

                    system[ax][ay] = null;
                    p.clear();
                
            }
        }
    }

    public void clear(Location location)
    {
        system[location.getX()][location.getY()] = null;
    }
    
    public void place(Particle particle, int x, int y)
    {
        place(particle, new Location(x, y));
    }
 
    public void place(Particle particle, Location location) {
            system[location.getX()][location.getY()] = particle;
            p.put(location, particle);
    }

    public Particle getObjectAt(Location location)
    {
        return getObjectAt(location.getX(), location.getY());
    }

    public Particle getObjectAt(int x, int y)
    {
        return system[x][y];
    }

    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        //int v = adjacent.size();
        Random r = new Random();
        int u = r.nextInt(9);
        try {
            return adjacent.get(u);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    
 
    public Location freeAdjacentLocation(Location location)
    {
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int x = location.getX();
            int y = location.getY();
            for(int xoffset = -1; xoffset <= 1; xoffset++) {
                int nextX = x + xoffset;
                if(nextX >= 0 && nextX < xsize) {
                    for(int yoffset = -1; yoffset <= 1; yoffset++) {
                        int nextY = y + yoffset;
                        if(nextY >= 0 && nextY < ysize) {
                                    locations.add(new Location(nextX, nextY));
                                
                            
                        }
                    }
                }
            }
        }
        return locations;
    }

    public int getX()
    {
        return xsize;
    }
    
    public int getY()
    {
        return ysize;
    }
    
}
