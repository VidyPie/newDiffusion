package diffusjon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author vidar
 * @version 2015.03.04
 */
public class Simulator {

    private ArrayList<Particle> particles;
    private ArrayList<Location> locations;
    private SimulatorView view;
    private ParticleSystem system;
    private int step = 1;
    private int nValue;
    private int l;
    private int m = 0;
    private int stateConst;
    private int partCount;
    private long lastStep;
    private long speed = 20;

    public static enum SimState {

        READY, RUNNING, PAUSE, WAITING
    }

    private SimState simState;

    public Simulator() {
        particles = new ArrayList<Particle>();
        locations = new ArrayList<Location>();
        view = new SimulatorView();
        view.setSize(900, 640);
        system = new ParticleSystem(128, 128);
        simState = SimState.WAITING;
        fillLocations();
        reset();
        layoutMenu();
        simLoop();
    }

    private void fillLocations() {
        for (int x = 0; x <= 128; x++) {
            for (int y = 0; y <= 128; y++) {
                Location location = new Location(x, y);
                List<Location> adjacentLocations = system.adjacentLocations(location);
                location.addList(adjacentLocations);
                locations.add(location);
            }
        }
    }

    private void simLoop() {
        while (true) {
            //System.out.println(simState);
            switch (simState) {
                case RUNNING:
                    if (System.currentTimeMillis() >= lastStep + speed && step <= nValue && m == 0 && (stateConst == 1 || stateConst == 2)) {
                        simulateOneStep();
                        //step++;
                        lastStep = System.currentTimeMillis();
                    }
                    if (step >= nValue) {
                        simState = SimState.WAITING;
                    }
                    if(stateConst == 3 && System.currentTimeMillis() >= lastStep + speed && step <= nValue) {
                        cellularSimulationOneStep();
                        lastStep = System.currentTimeMillis();
                    }
                    break;
                case READY:

                    break;

                case PAUSE:

                    break;
            }
        }
    }

    private void start() {
        try {
            JTextField difField = new JTextField(4);
            JTextField parField = new JTextField(4);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Steps:"));
            myPanel.add(difField);
            myPanel.add(Box.createHorizontalStrut(30)); // a spacer
            myPanel.add(new JLabel("Particles:"));
            myPanel.add(parField);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter nValue", JOptionPane.OK_CANCEL_OPTION);
            nValue = Integer.parseInt(difField.getText());
            partCount = Integer.parseInt(parField.getText());
            reset();
            populate();
        } catch (NumberFormatException e) {
            int result = JOptionPane.showConfirmDialog(null, "Make sure all values are numbers", "CRITICAL ERROR", JOptionPane.OK_CANCEL_OPTION);
            //customStart();
        }

    }
    
    private void start2() {
        try {
            JTextField difField = new JTextField(4);
            JTextField parField = new JTextField(4);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Steps:"));
            myPanel.add(difField);
            myPanel.add(Box.createHorizontalStrut(30)); // a spacer
            myPanel.add(new JLabel("Particles:"));
            myPanel.add(parField);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter nValue", JOptionPane.OK_CANCEL_OPTION);
            nValue = Integer.parseInt(difField.getText());
            partCount = Integer.parseInt(parField.getText());
            reset();
            populate2();
        } catch (NumberFormatException e) {
            int result = JOptionPane.showConfirmDialog(null, "Make sure all values are numbers", "CRITICAL ERROR", JOptionPane.OK_CANCEL_OPTION);
            //customStart();
        }

    }

    private void simulateOneStep() {
        //System.out.println(step);
        List<Particle> newParticles = new ArrayList<Particle>();
        for (Iterator<Particle> it = particles.iterator(); it.hasNext();) {
            Particle particle = it.next();
            l = particle.act(newParticles);
            if (l == 1) {
                m = 1;
                simState = SimState.WAITING;
            }

        }
        particles.addAll(newParticles);
        view.prepareSystem(step, system);
        view.showStatus(step, system, stateConst, locations);
        step++;
    }

    private void cellularSimulationOneStep() {
        for (Iterator<Location> it = locations.iterator(); it.hasNext();) {
            Location location = it.next();
            List<Location> adjacentLoc = new ArrayList<Location>();
            location.steinLoop();
        }
        for (Iterator<Location> it = locations.iterator(); it.hasNext();) {
            Location location = it.next();
            location.trix();
        }
        view.prepareSystem(step, system);
        view.showStatus(step, system, stateConst, locations);
        step++;
        System.out.println("Step Complete");
    }

    private void stop() {

    }

    private void layoutMenu() {
        //menu.setLayout(null);
        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");
        JButton stopButton = new JButton("Stop");
        JButton agentButton = new JButton("Random Walk");
        JButton cellButton = new JButton("Cellular Automation");
        JButton singleButton = new JButton("Single Walk");
        JButton heatButton = new JButton("Heatmap");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("You clicked START");
                if (simState == SimState.READY) {
                    //start();
                    view.addToMenu(stopButton);
                    view.removeButton(startButton);
                    simState = SimState.RUNNING;
                } else {

                    int result = JOptionPane.showConfirmDialog(null, "Select mode first", "NOMODE", JOptionPane.CLOSED_OPTION);
                }
            }
        });
        agentButton.addActionListener((ActionEvent e) -> {
            if (simState != SimState.RUNNING) {
                stateConst = 1;
                start();
                simState = SimState.READY;
            }
        });
        cellButton.addActionListener((ActionEvent e) -> {
            if (simState != SimState.RUNNING) {
                stateConst = 3;
                start2();
                simState = SimState.READY;
            }
        });
        heatButton.addActionListener((ActionEvent e) -> {
            if(simState != SimState.RUNNING) {
                stateConst = 4;
                showHeatMap();
            }
            
        });
        stopButton.addActionListener((ActionEvent e) -> {
            simState = SimState.WAITING;
            view.addToMenu(startButton);
            view.removeButton(stopButton);
        });
        singleButton.addActionListener((ActionEvent e) -> {
            partCount = 1;
            stateConst = 2;
            nValue = 10000;
            view.clearVisitedLocations();
            reset();
            populate();
            simState = SimState.READY;
        });
        exitButton.addActionListener((ActionEvent e) -> {
            System.out.println("You clicked EXIT");
            exit();
        });
        startButton.setBounds(10, 10, 180, 30);
        stopButton.setBounds(10, 10, 180, 30);
        exitButton.setBounds(10, 115, 180, 30);
        agentButton.setBounds(10, 165, 180, 30);
        cellButton.setBounds(10, 200, 180, 30);
        singleButton.setBounds(10, 235, 180, 30);
        heatButton.setBounds(10, 270, 180, 30);
        view.addToMenu(startButton);
        view.addToMenu(agentButton);
        view.addToMenu(cellButton);
        view.addToMenu(singleButton);
        view.addToMenu(heatButton);
        view.repaint();
    }

    public void reset() {
        step = 1;
        m = 0;
        particles.clear();
    }

    private void exit() {
        System.exit(0);
    }
    
    private void showHeatMap() {
        view.showStatus(step, system, stateConst, locations);
    }

    private void populate() {
        system.clear();
        int t = 0;
        int n = 0;
        Random r = new Random();
        while (t < partCount) {
            int ax = r.nextInt(59);
            int ay = r.nextInt(59);
            Location location = new Location(64, 64);
            Particle particle = new Particle(system, location);
            particles.add(particle);
            t++;
            n++;
            if (n == 4) {
                n = 0;
            }
        }
        view.prepareSystem(step, system);
        view.showStatus(step, system, stateConst, locations);
    }
    private void populate2() {
        system.clear();
        int t = 0;
        int n = 0;
        Random r = new Random();
        for (Iterator<Location> it = locations.iterator(); it.hasNext();) {
            Location location = it.next();
            List<Location> adjacentLoc = new ArrayList<Location>();
            for (Iterator<Location> itv = locations.iterator(); itv.hasNext();) {
                Location location2 = itv.next();
                if(location2.getX() > location.getX() - 2 && location2.getX() < location.getX() + 2 && location2.getY() > location.getY() - 2 && location2.getY() < location.getY() + 2) {
                    adjacentLoc.add(location2);
                }
            }
            location.setAdjacentLoc(adjacentLoc);
        }
        locations.get(8192).placeParticle(1);
        view.prepareSystem(step, system);
        view.showStatus(step, system, stateConst, locations);
    }

}
