package com.blackenedsystems.games.missilecommand;

import java.awt.geom.Point2D;

/**
 * Base properties of the defensive objects (cities and missile bases) that the player
 * must defend from incoming <code>InterContinentalBallisticMissiles</code>
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 3:02:17 PM
 */
public abstract class DefensiveObject implements GameElement {

    protected final Point2D.Double coordinates;

    protected boolean destroyed = false;

    public DefensiveObject(Point2D.Double coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * The coordinates used by enemy missiles when targeting this defensive object.
     *
     * @return
     */
    public Point2D.Double getCoordinates() {
        return coordinates;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * Resets the <code>DefensiveObject</code> back to its factory settings.
     */
    public abstract void reset();

    public abstract DefensiveObjectType getType();        
}
