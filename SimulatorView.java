package diffusjon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
//import static java.awt.PageAttributes.ColorType.COLOR;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author VidyPie
 * @version 2015.03.04
 */
public class SimulatorView extends JFrame {

    private systemView loco;
    private sideMenu menu;
    private JSplitPane splitPane;
    private ArrayList<Location> visitedLocations;
    private HeatMap hMap;

    public SimulatorView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        visitedLocations = new ArrayList<Location>();
        setTitle("Diffusjon");
        menu = new sideMenu();
        hMap = new HeatMap();
        menu.setLayout(null);
        setLocation(400, 200);
        loco = new systemView(700, 1000);
        Container contents = getContentPane();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menu, loco);
        splitPane.setDividerLocation(200);
        splitPane.setLeftComponent(menu);
        splitPane.setRightComponent(loco);
        contents.add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void prepareSystem(int step, ParticleSystem system) {
        if (!isVisible()) {
            setVisible(true);
        }
        loco.preparePaint();
    }

    public void clearVisitedLocations() {
        visitedLocations.clear();
    }

    public void showStatus(int step, ParticleSystem system, int stateConst, List<Location> locations) {
        if (stateConst == 1) {
            loco.removeAll();
            for (int aix = 0; aix < system.getX(); aix++) {
                for (int aiy = 0; aiy < system.getY(); aiy++) {
                    //loco.drawEmpty(aix, aiy);

                }
            }
            for (int ax = 0; ax < system.getX(); ax++) {
                for (int ay = 0; ay < system.getY(); ay++) {
                    Particle particle = system.getObjectAt(ax, ay);
                    if (particle == null) {
                        loco.drawEmpty(ax, ay);
                    } else {
                        loco.drawMark(ax, ay);
                    }
                }
            }
        }
        if (stateConst == 2) {
            loco.removeAll();
            for (int ax = 0; ax < system.getX(); ax++) {
                for (int ay = 0; ay < system.getY(); ay++) {
                    Particle particle = system.getObjectAt(ax, ay);
                    if (particle == null) {
                        loco.drawEmpty(ax, ay);
                    } else {
                        loco.drawMark(ax, ay);
                        Location location = new Location(ax, ay);
                        visitedLocations.add(location);
                    }
                }
            }
            for (Iterator<Location> it = visitedLocations.iterator(); it.hasNext();) {
                Location location = it.next();
                loco.drawVisitedMark(location.getX(), location.getY());
            }
        }
        if (stateConst == 3) {
            loco.removeAll();
            for (Iterator<Location> it = locations.iterator(); it.hasNext();) {
                Location location = it.next();
                
                
                if (location.getPop() > 0) {
                    loco.drawMark(location.getX(), location.getY());
                } else {
                    loco.drawEmpty(location.getX(), location.getY());
                }
            }
        }
        
        if (stateConst == 4) {
            
            int STEP_MIN = 1;
            int STEP_MAX = 10;
            int STEP_INIT = 1;
            int currentStep = 10;
            mapDraw(currentStep);
            JSlider probPerStep = new JSlider(JSlider.HORIZONTAL, STEP_MIN, STEP_MAX, STEP_INIT);
            probPerStep.addChangeListener((ChangeEvent e) -> {
            JSlider source = (JSlider)e.getSource();
            if(!source.getValueIsAdjusting()) {
                int newStep = (int)source.getValue();
                mapDraw(newStep);
            }
        });
            probPerStep.setBounds(49, 10, 530, 20);
            loco.add(probPerStep);
        }
        repaint();
    }
    
    private void mapDraw(int mStep) {
        loco.preparePaint();
        loco.makeSquare();
        for(int mx = 1; mx <= 21; mx++) {
            for (int my = 1; my <= 21; my++) {
                loco.drawMapMark(mx, my, hMap.getChance(mStep, mx, my));
            }
        }
    }
    
    public void addToMenu(JButton button) {
        menu.addButton(button);
    }

    public void removeButton(JButton button) {
        menu.remove(button);
        menu.repaint();
    }

    public class sideMenu extends JPanel {

        public sideMenu() {

        }

        public void addButton(JButton button) {
            menu.add(button);
        }
    }

    public class systemView extends JPanel {

        private Image underLine, yLine, systemFront;
        private Graphics g;
        private int xScale = 4;
        private int yScale = 4;

        public systemView(int height, int width) {
            loadContent();
        }

        private void loadContent() {
            try {
                underLine = ImageIO.read(new File("graphics/line.png"));
                yLine = ImageIO.read(new File("graphics/yline.png"));
            } catch (IOException ex) {

            }
        }

        public void preparePaint() {
            systemFront = loco.createImage(750, 600);
            g = systemFront.getGraphics();

        }

        public void drawMark(int x, int y) {
            Color colorA = new Color(200, 0, 0);
            g.setColor(colorA);
            g.fillRect(49 + (x * xScale), 24 + (y * yScale), xScale - 1, yScale - 1);
            //g.fillRect(400 + (x * xScale), xScale - 0);
            //g.fillRect(400 + (x * xScale), y * yScale, xScale - 0, yScale - 0);
        }

        public void drawVisitedMark(int x, int y) {
            Color colorA = new Color(100, 0, 0);
            g.setColor(colorA);
            g.fillRect(49 + (x * xScale), 24 + (y * yScale), xScale - 1, yScale - 1);
            //g.fillRect(400 + (x * xScale), xScale - 0);
            //g.fillRect(400 + (x * xScale), y * yScale, xScale - 0, yScale - 0);
        }

        public void drawEmpty(int x, int y) {
            Color color = new Color(255, 255, 255);
            g.setColor(color);
            g.fillRect(49 + (x * xScale), 24 + (y * yScale), xScale - 1, yScale - 1);
            //g.fillRect(400 + (x * xScale), z * zScale, xScale - 0, zScale - 0);       
        }
        
        public void drawMapMark(int mx, int my, double chance) {
            Color color = new Color(230, 224, 224);
            g.setColor(color);
            DecimalFormat df = new DecimalFormat("#.####");
            String chance2 = df.format(chance);
            JLabel chanceLabel = new JLabel(chance2);
            chanceLabel.setFont(new Font("Serif", Font.BOLD, 8));
            chanceLabel.setBounds((mx * 25) + 26, (my * 25) + 26, 24, 24);
            loco.add(chanceLabel);
            g.fillRect(25 + (mx * 25), my * 25, 24, 24);
        }
        
        public void makeSquare(){
            Rectangle rect = new Rectangle();
            rect.setBounds(1, 1, 1, 1);
            g.setColor(Color.BLACK);
            g.fillRect(49, 24, (21*25) + 1, (21*25) + 1);
        }

        public void paintComponent(Graphics g) {
            if (systemFront != null) {
                g.drawImage(systemFront, 0, 25, null);
                //g.drawImage(underLine, 50, 355, null);
                //g.drawImage(yLine, 25, 50, null);

            }
        }
    }
}
