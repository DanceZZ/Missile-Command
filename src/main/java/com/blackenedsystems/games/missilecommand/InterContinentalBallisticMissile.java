package com.blackenedsystems.games.missilecommand;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Represents a missile against which the player must defend his cities and missile bases.
 * <code>InterContinentalBallisticMissiles</code> move from the top of the screen toward their
 * intended target at the bottom of the screen.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 1:54:26 PM
 */
public class InterContinentalBallisticMissile extends Missile {

    private final Logger logger = Logger.getLogger(InterContinentalBallisticMissile.class);

    private static final int BLAST_RADIUS = 15;

    private final DefensiveObject target;

    protected double xIncrement;
    protected double yIncrement;

    /**
     * ICBMs start at the top of the game area, therefore the y-coordinate is always zero.
     *
     * @param initialXCoordinate the point the missile enters the top of the screen.
     * @param target             the defensive target (city or missile base) to which this missile is aiming.
     * @param speed              the number of pixels that the missile will move each redraw.
     */
    public InterContinentalBallisticMissile(int initialXCoordinate, DefensiveObject target, int speed) {
        super(new Point2D.Double(initialXCoordinate, 0), BLAST_RADIUS);
        this.target = target;
        calculateScreenIncrements(speed);

        if (logger.isDebugEnabled()) {
            logger.debug("Created ICBM with intial coordinates: " + initialCoordinates +
                    ", target: " + target.getCoordinates() +
                    ", x increments: " + xIncrement);
        }
    }

    private void calculateScreenIncrements(int speed) {
        yIncrement = speed;
        xIncrement = ((target.getCoordinates().getX() - this.initialCoordinates.getX()) / 328) * speed;
    }

    /**
     * Move this ICBM one step closer to the bottom of the game area.
     */
    public void animate() {
        if (missileStatus == MissileStatus.IN_FLIGHT) {
            currentCoordinates = new Point2D.Double(currentCoordinates.getX() + xIncrement, currentCoordinates.getY() + yIncrement);

            if (currentCoordinates.getY() >= 308) {
                missileStatus = MissileStatus.REACHED_TARGET;
            }
        }
    }

    /**
     * @return  the coordinates of this ICBM's target.
     */
    @Override
    public Point2D.Double getTargetCoordinates() {
        return target.getCoordinates();
    }

     /**
     * @return  the colour of the trail left by this ICBM as it flies toward its target.
     */
    @Override
    protected Color getTrailColor() {
        return Color.RED;
    }
}
