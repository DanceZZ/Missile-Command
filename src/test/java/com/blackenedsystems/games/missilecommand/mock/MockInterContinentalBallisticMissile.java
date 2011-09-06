package com.blackenedsystems.games.missilecommand.mock;

import com.blackenedsystems.games.missilecommand.InterContinentalBallisticMissile;
import com.blackenedsystems.games.missilecommand.DefensiveObject;

import java.awt.geom.Point2D;

/**
 * Extends the <code>InterContinentalBallisticMissile</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 2:32:36 PM
 */
public class MockInterContinentalBallisticMissile extends InterContinentalBallisticMissile {

    public MockInterContinentalBallisticMissile(int initialXCoordinate, DefensiveObject target, int speed) {
        super(initialXCoordinate, target, speed);
    }

    public double getXIncrement() {
        return xIncrement;
    }

    public double getYIncrement() {
        return yIncrement;
    }

    public void setCurrentCoordinates(Point2D.Double currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }
}
