package com.blackenedsystems.games.missilecommand;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.*;

import com.blackenedsystems.games.missilecommand.mock.MockInterContinentalBallisticMissile;

/**
 * Unit tests for the <code>InterContinentalBallisticMissile</code> test.
 * 
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 2:27:40 PM
 */
public class InterContinentalBallisticMissileTest {

    private final int gameAreaHeight = 350;
    private final int gameAreaWidth = 400;

    private final City targetCity = new City(new Point2D.Double(120, gameAreaHeight - 5));

    private MockInterContinentalBallisticMissile icbm;

    @Before
    public void setUp() {
        icbm = new MockInterContinentalBallisticMissile(50, targetCity, 1);
    }

    @Test
    public void createMissile() {
        assertFalse(icbm.hasReachedTarget());
        assertEquals(Color.RED, icbm.getTrailColor());
        assertEquals(15, icbm.getGeneratedBlastRadius());
    }

    @Test
    public void yIncrementIsPositive() {
        assertTrue(icbm.getYIncrement() > 0);
    }

    @Test
    public void positiveIncrement() {
        assertTrue(icbm.getXIncrement() >= 0.0);
    }

    @Test
    public void negativeIncrement() {
        MockInterContinentalBallisticMissile icbm2 = new MockInterContinentalBallisticMissile(250, targetCity, 1);
        assertTrue(icbm2.getXIncrement() <= 0.0);
    }

    @Test
    public void moveMissile() {
        Point2D.Double initialCoordinates = icbm.getInitialCoordinates();
        icbm.animate();
        assertTrue(initialCoordinates.getX() < icbm.getCurrentCoordinates().getX());
        assertTrue(initialCoordinates.getY() < icbm.getCurrentCoordinates().getY());
    }

    @Test
    public void reachedTarget() {
        for (int i=0; i<targetCity.coordinates.getY(); i++) {
            icbm.animate();
        }
        assertTrue(icbm.hasReachedTarget());
    }

    @Test
    public void destroy() {
        assertFalse(icbm.isDestroyed());
        icbm.destroy();
        assertTrue(icbm.isDestroyed());
    }

    @Test (expected=UnsupportedOperationException.class)
    public void testBounds() {
        Rectangle bounds = icbm.getBounds();
    }
}
