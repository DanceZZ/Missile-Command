package com.blackenedsystems.games.missilecommand.mock;

import com.blackenedsystems.games.missilecommand.MissileBase;

import java.awt.geom.Point2D;

/**
 * Extends the <code>MissileBase</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author Alan Tibbetts
 * @since Feb 21, 2010, 10:17:52 PM
 */
public class MockMissileBase extends MissileBase {

    public MockMissileBase(Point2D.Double bottomLeftCoordinates) {
        super(bottomLeftCoordinates);
    }

    public Point2D.Double getTopOfTriangle() {
        return new Point2D.Double(xCoordinates[1], yCoordinates[1]);
    }
}
