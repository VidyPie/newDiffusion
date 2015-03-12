/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffusjon;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author vidar
 */
public class HeatMap {
    
    private ArrayList<mapLocation> locations;
    
    public HeatMap() {
        locations = new ArrayList<mapLocation>();
        for (int lx = 1; lx <= 21; lx++) {
            for (int ly = 1; ly <= 21; ly++) {
                mapLocation location = new mapLocation(lx, ly);
                locations.add(location);
                if ((lx == 10 || lx == 12) && (ly == 10 || ly == 12)) {
                    double v = (1.0/12.0);
                    location.setChance(1, v);
                    location.finalizeChance(1);
                    System.out.println(location.getFinalChance(1));
                }
                
                if ((lx == 11 && (ly == 10 || ly == 12)) || (ly == 11 && (lx == 10 || lx == 12))) {
                    double v = (2.0/12.0);
                    location.setChance(1, v);
                    location.finalizeChance(1);
                    System.out.println(location.getFinalChance(1));
                }
                 
            }
        }
        calcChance();
    }
    
    public void calcChance() {
        mapLocation locationMain;
        mapLocation locationAdj;
        mapLocation location;
        System.out.println("Starting calculation");
        for (int step = 2; step <= 10; step++) {
            for (Iterator<mapLocation> it = locations.iterator(); it.hasNext();) {
                locationMain = it.next();
                double thisChance = locationMain.getFinalChance(step - 1);
                if (thisChance > 0.0000) {
                    System.out.println("Got a hit!");
                    for (Iterator<mapLocation> ita = locations.iterator(); ita.hasNext();) {
                        locationAdj = ita.next();
                        if (locationAdj.getXCoord() == locationMain.getXCoord() - 1 && locationAdj.getYCoord() == locationMain.getYCoord() - 1) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (1.0 / 12.0);
                            locationAdj.setChance(step, newChance);
                        }
                        if ((locationAdj.getXCoord() == locationMain.getXCoord() + 1) && (locationAdj.getYCoord() == locationMain.getYCoord() + 1)) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (1.0 / 12.0);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() + 1 && locationAdj.getYCoord() == locationMain.getYCoord() - 1) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (1.0 / 12.0);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() - 1 && locationAdj.getYCoord() == locationMain.getYCoord() + 1) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (1.000 / 12.000);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() && locationAdj.getYCoord() == locationMain.getYCoord() - 1) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (2.000 / 12.000);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() && locationAdj.getYCoord() == locationMain.getYCoord() + 1) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (2.0 / 12.0);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() - 1 && locationAdj.getYCoord() == locationMain.getYCoord()) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (2.0 / 12.0);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() + 1 && locationAdj.getYCoord() == locationMain.getYCoord()) {
                            double newChance = locationMain.getFinalChance(step - 1);
                            newChance = newChance * (2.0 / 12.0);
                            locationAdj.setChance(step, newChance);
                        }
                    }
                }
            }
            for (Iterator<mapLocation> itb = locations.iterator(); itb.hasNext();) {
                location = itb.next();
                location.finalizeChance(step);
                System.out.println(location.getFinalChance(step));
            }
        }
    }
    
    public double getChance(int step, int x, int y) {
        double chance = 0;
        for (Iterator<mapLocation> it = locations.iterator(); it.hasNext();) {
            mapLocation location = it.next();
            if (location.getXCoord() == x && location.getYCoord() == y) {
                chance = location.getFinalChance(step);
            }
        }
        return chance;
    }
    
}
