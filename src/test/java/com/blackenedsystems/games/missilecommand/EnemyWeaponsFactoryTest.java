package com.blackenedsystems.games.missilecommand;

import com.blackenedsystems.games.missilecommand.mock.MockGameArea;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the <code>EnemyWeaponsFactory</code> class.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 10:51:58 PM
 */
public class EnemyWeaponsFactoryTest {

    private static final int DEFENSIVE_OBJECT_Y_COORDINATE = 325;

    private static final int[] CITY_X_COORDINATES = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] MISSILE_BASE_X_COORDINATES = new int[]{15, 185, 355};

    private List<DefensiveObject> defensiveObjects;
    private EnemyWeaponsFactory enemyWeaponsFactory;
    private MockGameArea gameArea;

    @Before
    public void setup() {
        gameArea = new MockGameArea();

        defensiveObjects = new ArrayList<DefensiveObject>();

        for (int xCoordinate : CITY_X_COORDINATES) {
            City city = new City(new Point2D.Double(xCoordinate, DEFENSIVE_OBJECT_Y_COORDINATE));
            defensiveObjects.add(city);
        }

        for (int xCoordinate : MISSILE_BASE_X_COORDINATES) {
            MissileBase missileBase = new MissileBase(new Point2D.Double(xCoordinate, DEFENSIVE_OBJECT_Y_COORDINATE));
            defensiveObjects.add(missileBase);
        }

        enemyWeaponsFactory = new EnemyWeaponsFactory(gameArea, defensiveObjects);
    }

    @Test
    public void createIcbm() {
        InterContinentalBallisticMissile icbm = enemyWeaponsFactory.createMissile(1);
        assertNotNull(icbm);
        assertTrue(icbm.getInitialCoordinates().getX() >= 0);
        assertTrue(icbm.getInitialCoordinates().getX() < gameArea.getParent().getWidth());
    }
}
