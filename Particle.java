
package newDiffusion;

import java.util.List;
import java.util.Random;

/**
 *
 * @author vidar
 * @version 2015.03.04
 */
public class Particle {
    private Location location;
    private ParticleSystem system;
    private int oldX, oldY, oldZ;
    int getOldX;
    int getOldY;
    int getOldZ;
    public Particle(ParticleSystem system, Location location) {
        this.system = system;
        setLocation(location);           
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            system.clear(location);
        }   
         system.place(this, newLocation);
         location = newLocation;
    }
    
    public int act(List<Particle> newParticles) {
        Location newLocation = system.randomAdjacentLocation(location);
        int returnN = 0;
        if (newLocation == null) {
            System.out.println("ohno");
            return 1;
        }
        oldX = location.getX();
        oldY = location.getY();
        
        setLocation(newLocation);
        return 0;
        
    }

    
    public int getOldX() {
       System.out.println("oldX: " + oldX);
        return oldX;
    }
    
    public int getOldY() {
        return oldY;
    }
    
    public int getOldZ() {
        return oldZ;
    }
   
}
