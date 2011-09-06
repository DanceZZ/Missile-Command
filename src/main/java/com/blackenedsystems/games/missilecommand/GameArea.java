package com.blackenedsystems.games.missilecommand;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the area in which the game is played.  Manages:
 * <ul>
 * <li> the display of all static elements </li>
 * <li> the creation of the <code>GameController</code> </li>
 * <li> the interation of the mouse and keyboard with the <code>GameController</code> </li>
 * </ul>
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 11:58:13 AM
 */
public class GameArea extends JPanel {

    private final Logger logger = Logger.getLogger(GameArea.class);

    private static final int NUMBER_OF_CITIES = 6;
    private static final int NUMBER_OF_MISSILE_BASES = 3;

    private static final int[] CITY_X_COORDINATES = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] MISSILE_BASE_X_COORDINATES = new int[]{15, 185, 355};

    private List<DefensiveObject> defensiveObjects = new ArrayList<DefensiveObject>(NUMBER_OF_CITIES + NUMBER_OF_MISSILE_BASES);
    private List<MissileBase> missileBases = new ArrayList<MissileBase>(NUMBER_OF_MISSILE_BASES);

    private GameController gameController;

    public GameArea() {
        setFocusable(true);
        setBackground(Color.BLACK);

        gameController = new GameController(this, defensiveObjects);

        MissileCommandMouseAdapter missileCommandMouseAdapter = new MissileCommandMouseAdapter();
        addMouseListener(missileCommandMouseAdapter);
        addMouseMotionListener(missileCommandMouseAdapter);
        
        addKeyListener(new MissileCommandKeyAdapter(missileCommandMouseAdapter));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;

        // Is this really the earliest I can have access to the parent.height?
        constructDefensiveArea(getParent().getHeight());

        drawLand(graphics2D, getParent().getHeight(), getParent().getWidth());
        drawDefensiveObjects(graphics2D);
        gameController.paint(graphics2D);
    }

    private void drawDefensiveObjects(Graphics2D graphics2D) {
        for (DefensiveObject defensiveObject : defensiveObjects) {
            defensiveObject.draw(graphics2D);
        }
    }

    private void drawLand(Graphics2D graphics2D, int parentHeight, int parentWidth) {
        graphics2D.setPaint(Color.GREEN);
        graphics2D.fill(new Rectangle2D.Double(0, parentHeight - 5, parentWidth, 5));
    }

    private void constructDefensiveArea(int parentHeight) {
        if (defensiveObjects.size() == 0) {
            for (int xCoordinate : CITY_X_COORDINATES) {
                City city = new City(new Point2D.Double(xCoordinate, parentHeight - 3));
                defensiveObjects.add(city);
            }

            for (int xCoordinate : MISSILE_BASE_X_COORDINATES) {
                MissileBase missileBase = new MissileBase(new Point2D.Double(xCoordinate, parentHeight - 3));
                missileBases.add(missileBase);
                defensiveObjects.add(missileBase);
            }
        }
    }

    /**
     * Handles user input via the keyboard.
     */
    private class MissileCommandKeyAdapter extends KeyAdapter {

        private final MissileCommandMouseAdapter mouseAdapter;

        public MissileCommandKeyAdapter(GameArea.MissileCommandMouseAdapter mouseAdapter) {
            this.mouseAdapter = mouseAdapter;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            AntiBallisticMissile abm = null;
            switch (key) {
                case 'a':
                case 'A':
                    if (!missileBases.get(0).isDestroyed()) {
                        gameController.fireMissile(missileBases.get(0), mouseAdapter.getMouseCoordinates());
                    }
                    break;
                case 's':
                case 'S':
                    if (!missileBases.get(1).isDestroyed()) {
                        gameController.fireMissile(missileBases.get(1), mouseAdapter.getMouseCoordinates());
                    }
                    break;
                case 'd':
                case 'D':
                    if (!missileBases.get(2).isDestroyed()) {
                        gameController.fireMissile(missileBases.get(2), mouseAdapter.getMouseCoordinates());
                    }
                    break;
                case ' ':
                    gameController.startGame();
                    break;
            }
        }
    }

    /**
     * Handles user input via the mouse.
     */
    private class MissileCommandMouseAdapter extends MouseAdapter {

        private double mouseXCoordinates;
        private double mouseYCoordinates;

        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseXCoordinates = e.getPoint().getX();
            mouseYCoordinates = e.getPoint().getY();
        }

        /**
         * @return  current coordinates of the mouse pointer within the game area.
         */
        public Point2D.Double getMouseCoordinates() {
            return new Point2D.Double(mouseXCoordinates, mouseYCoordinates);
        }
    }
}
