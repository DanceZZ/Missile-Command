package com.blackenedsystems.games.missilecommand;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.*;

/**
 * Unit tests for the <code>City</code> class.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 3:09:28 PM
 */
public class CityTest {

    private City city;

    @Before
    public void setup() {
        city = new City(new Point2D.Double(20, 300));
    }

    @Test
    public void notInitiallyDestroyed() {
        assertFalse(city.isDestroyed());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void animate() {
        city.animate();
    }

    @Test
    public void destroyCity() {
        assertFalse(city.isDestroyed());
        city.destroy();
        assertTrue(city.isDestroyed());
    }

    @Test
    public void testBounds() {
        Rectangle bounds = city.getBounds();
        assertNotNull(bounds);
        assertEquals(20, (int) bounds.getHeight());
        assertEquals(20, (int) bounds.getWidth());
        
        Point point = new Point(25, 295);
        assertTrue(bounds.contains(point));

        point = new Point(10,10);
        assertFalse(bounds.contains(point));
    }
}
