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
                if (lx == 10 || lx == 12 && ly == 10 || ly == 12) {
                    location.setChance(1, 1 / 12);
                    location.finalizeChance(1);
                }
                if ((lx == 11 && (ly == 10 || ly == 12)) || (ly == 11 && (lx == 10 || lx == 12))) {
                    location.setChance(1, 2 / 12);
                    location.finalizeChance(1);
                }
            }
        }
        calcChance();
    }
    
    public void calcChance() {
        for (int step = 2; step <= 10; step++) {
            for (Iterator<mapLocation> it = locations.iterator(); it.hasNext();) {
                mapLocation locationMain = it.next();
                double thisChance = locationMain.getFinalChance(step);
                if (thisChance > 0) {
                    for (Iterator<mapLocation> ita = locations.iterator(); ita.hasNext();) {
                        mapLocation locationAdj = it.next();
                        if (locationAdj.getXCoord() == locationMain.getXCoord() - 1 && locationAdj.getYCoord() == locationMain.getYCoord() - 1) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (1 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() + 1 && locationAdj.getYCoord() == locationMain.getYCoord() + 1) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (1 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() + 1 && locationAdj.getYCoord() == locationMain.getYCoord() - 1) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (1 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() - 1 && locationAdj.getYCoord() == locationMain.getYCoord() + 1) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (1 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() && locationAdj.getYCoord() == locationMain.getYCoord() - 1) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (2 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() && locationAdj.getYCoord() == locationMain.getYCoord() + 1) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (2 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() - 1 && locationAdj.getYCoord() == locationMain.getYCoord()) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (2 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                        if (locationAdj.getXCoord() == locationMain.getXCoord() + 1 && locationAdj.getYCoord() == locationMain.getYCoord()) {
                            double newChance = locationMain.getFinalChance(step);
                            newChance = newChance * (2 / 12);
                            locationAdj.setChance(step, newChance);
                        }
                    }
                }
            }
            for (Iterator<mapLocation> itb = locations.iterator(); itb.hasNext();) {
                mapLocation location = itb.next();
                location.finalizeChance(step);
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
