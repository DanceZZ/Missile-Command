package com.blackenedsystems.games.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.*;

/**
 * Base implementation of a missile containing logic common to both friendly and enemy missiles.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 3:18:13 PM
 */
public abstract class Missile implements GameElement {

    private final Logger logger = Logger.getLogger(Missile.class);

    protected final Point2D.Double initialCoordinates;
    protected final int generatedBlastRadius;

    protected Point2D.Double currentCoordinates;
    protected MissileStatus missileStatus = MissileStatus.IN_FLIGHT;

    /**
     * @param initialCoordinates    the origin of this missile
     * @param generatedBlastRadius           the blast radius generated by this missile when it explodes.
     */
    public Missile(Point2D.Double initialCoordinates, int generatedBlastRadius) {
        this.initialCoordinates = initialCoordinates;
        this.currentCoordinates = initialCoordinates;
        this.generatedBlastRadius = generatedBlastRadius;
    }

    /**
     * {@inheritDoc}
     */
    public void draw(Graphics2D graphics2D) {
        switch (missileStatus) {
            case IN_FLIGHT:
                drawInFlight(graphics2D);
                break;
            case REACHED_TARGET:
                logger.warn("Attempting to draw a missile that has already reached its target.");
                break;
            case DESTROYED:
                logger.warn("Attempting to draw a missile that has already been destroyed.");
                break;
            default:
                logger.warn("Unexpected Missile Status: " + missileStatus);
        }
    }

    private void drawInFlight(Graphics2D graphics2D) {
        Line2D.Double line = new Line2D.Double(initialCoordinates, currentCoordinates);
        graphics2D.setColor(getTrailColor());
        graphics2D.draw(line);
    }

    public boolean hasReachedTarget() {
        return missileStatus == MissileStatus.REACHED_TARGET;
    }

    public boolean isDestroyed() {
        return missileStatus == MissileStatus.DESTROYED;
    }

    public void destroy() {        
        missileStatus = MissileStatus.DESTROYED;
    }

    public int getGeneratedBlastRadius() {
        return generatedBlastRadius;
    }

    /**
     * The point at which the missile enters the top of the game area.
     *
     * @return
     */
    public Point2D.Double getInitialCoordinates() {
        return initialCoordinates;
    }

    /**
     * The ICBM's current location within the game area.
     *
     * @return
     */
    public Point2D.Double getCurrentCoordinates() {
        return currentCoordinates;
    }

    /**
     * Reports the current location of this missile.  Used during collision detection.
     *
     * @return
     */
    public Point getLocation() {
        Point point = new Point();
        point.setLocation(currentCoordinates.getX(), currentCoordinates.getY());
        return point;
    }

    /**
     * Not Supported.
     */
    public Rectangle getBounds() {
        throw new UnsupportedOperationException();
    }

    public abstract Point2D.Double getTargetCoordinates();

    protected abstract Color getTrailColor();

}