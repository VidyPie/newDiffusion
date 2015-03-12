/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffusjon;

import java.util.ArrayList;

/**
 *
 * @author vidar
 */
public class mapLocation {

    private double chancetemp1;
    private double chancetemp2;
    private double chancetemp3;
    private double chancetemp4;
    private double chancetemp5;
    private double chancetemp6;
    private double chancetemp7;
    private double chancetemp8;
    private double chancetemp9;
    private double chancetemp10;
    private double chanceFinal1;
    private double chanceFinal2;
    private double chanceFinal3;
    private double chanceFinal4;
    private double chanceFinal5;
    private double chanceFinal6;
    private double chanceFinal7;
    private double chanceFinal8;
    private double chanceFinal9;
    private double chanceFinal10;
    private int xCoord;
    private int yCoord;

    public mapLocation(int x, int y) {
        this.chancetemp1 = 0;
        this.chancetemp2 = 0;
        this.chancetemp3 = 0;
        this.chancetemp4 = 0;
        this.chancetemp5 = 0;
        this.chancetemp6 = 0;
        this.chancetemp7 = 0;
        this.chancetemp8 = 0;
        this.chancetemp9 = 0;
        this.chancetemp10 = 0;
        this.chanceFinal1 = 0;
        this.chanceFinal2 = 0;
        this.chanceFinal3 = 0;
        this.chanceFinal4 = 0;
        this.chanceFinal5 = 0;
        this.chanceFinal6 = 0;
        this.chanceFinal7 = 0;
        this.chanceFinal8 = 0;
        this.chanceFinal9 = 0;
        this.chanceFinal10 = 0;
        this.xCoord = x;
        this.yCoord = y;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public double getTempChance(int step) {
        if (step == 1) {
            return chancetemp1;
        } else if (step == 2) {
            return chancetemp2;
        } else if (step == 3) {
            return chancetemp3;
        } else if (step == 4) {
            return chancetemp4;
        } else if (step == 5) {
            return chancetemp5;
        } else if (step == 6) {
            return chancetemp6;
        } else if (step == 7) {
            return chancetemp7;
        } else if (step == 8) {
            return chancetemp8;
        } else if (step == 9) {
            return chancetemp9;
        } else if (step == 10) {
            return chancetemp10;
        }
        return 0;
    }

    public double getFinalChance(int step) {
        if (step == 1) {
            return chanceFinal1;
        } else if (step == 2) {
            return chanceFinal2;
        } else if (step == 3) {
            return chanceFinal3;
        } else if (step == 4) {
            return chanceFinal4;
        } else if (step == 5) {
            return chanceFinal5;
        } else if (step == 6) {
            return chanceFinal6;
        } else if (step == 7) {
            return chanceFinal7;
        } else if (step == 8) {
            return chanceFinal8;
        } else if (step == 9) {
            return chanceFinal9;
        } else if (step == 10) {
            return chanceFinal10;
        }
        return 0;
    }

    public void setChance(int step, double chance) {
        if (step == 1) {
            chancetemp1 = chancetemp1 + chance;
        } else if (step == 2) {
            chancetemp2 = chancetemp2 + chance;
        } else if (step == 3) {
            chancetemp3 = chancetemp3 + chance;
        } else if (step == 4) {
            chancetemp4 = chancetemp4 + chance;
        } else if (step == 5) {
            chancetemp5 = chancetemp5 + chance;
        } else if (step == 6) {
            chancetemp6 = chancetemp6 + chance;
        } else if (step == 7) {
            chancetemp7 = chancetemp7 + chance;
        } else if (step == 8) {
            chancetemp8 = chancetemp8 + chance;
        } else if (step == 9) {
            chancetemp9 = chancetemp9 + chance;
        } else if (step == 10) {
            chancetemp10 = chancetemp10 + chance;
        }
    }

    public void finalizeChance(int step) {
        if (step == 1) {
            chanceFinal1 = chancetemp1;
        } else if (step == 2) {
            chanceFinal2 = chancetemp2;
        } else if (step == 3) {
            chanceFinal3 = chancetemp3;
        } else if (step == 4) {
            chanceFinal4 = chancetemp4;
        } else if (step == 5) {
            chanceFinal5 = chancetemp5;
        } else if (step == 6) {
            chanceFinal6 = chancetemp6;
        } else if (step == 7) {
            chanceFinal7 = chancetemp7;
        } else if (step == 8) {
            chanceFinal8 = chancetemp8;
        } else if (step == 9) {
            chanceFinal9 = chancetemp9;
        } else if (step == 10) {
            chanceFinal10 = chancetemp10;
        }
    }
}
