package com.blackenedsystems.games.missilecommand.mock;

import com.blackenedsystems.games.missilecommand.AntiBallisticMissile;

import java.awt.geom.Point2D;

/**
 * Extends the <code>AntiBallisticMissile</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author Alan Tibbetts
 * @since Feb 21, 2010, 10:03:32 PM
 */
public class MockAntiBallisticMissile extends AntiBallisticMissile {

    public MockAntiBallisticMissile(Point2D.Double initialCoordinates, Point2D.Double targetCoordinates, int speed) {
        super(initialCoordinates, targetCoordinates, speed);
    }

    public double getYDecrement() {
        return yDecrement;
    }

    public double getXIncrement() {
        return xIncrement;
    }

    public void setCurrentCoordinates(Point2D.Double currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }
}
