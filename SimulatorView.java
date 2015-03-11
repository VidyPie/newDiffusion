package diffusjon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Image;
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
import java.util.List;

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

    public SimulatorView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        visitedLocations = new ArrayList<Location>();
        setTitle("Diffusjon");
        menu = new sideMenu();
        menu.setLayout(null);
        setLocation(400, 200);
        loco = new systemView(700, 1000);
        Container contents = getContentPane();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menu, loco);
        splitPane.setDividerLocation(110);
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
            for (Iterator<Location> it = locations.iterator(); it.hasNext();) {
                Location location = it.next();
                
                
                if (location.getPop() > 0) {
                    loco.drawMark(location.getX(), location.getY());
                } else {
                    loco.drawEmpty(location.getX(), location.getY());
                }
            }
        }
        repaint();
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
        private int xScale = 3;
        private int yScale = 3;

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
            g.fillRect(100 + (x * xScale), y * yScale, xScale - 1, yScale - 1);
            //g.fillRect(400 + (x * xScale), xScale - 0);
            //g.fillRect(400 + (x * xScale), y * yScale, xScale - 0, yScale - 0);
        }

        public void drawVisitedMark(int x, int y) {
            Color colorA = new Color(100, 0, 0);
            g.setColor(colorA);
            g.fillRect(100 + (x * xScale), y * yScale, xScale - 1, yScale - 1);
            //g.fillRect(400 + (x * xScale), xScale - 0);
            //g.fillRect(400 + (x * xScale), y * yScale, xScale - 0, yScale - 0);
        }

        public void drawEmpty(int x, int y) {
            Color color = new Color(255, 255, 255);
            g.setColor(color);
            g.fillRect(100 + (x * xScale), y * yScale, xScale - 1, yScale - 1);
            //g.fillRect(400 + (x * xScale), z * zScale, xScale - 0, zScale - 0);       
        }

        public void paintComponent(Graphics g) {
            if (systemFront != null) {
                g.drawImage(systemFront, 0, 50, null);
                //g.drawImage(underLine, 50, 355, null);
                //g.drawImage(yLine, 25, 50, null);

            }
        }
    }
}
