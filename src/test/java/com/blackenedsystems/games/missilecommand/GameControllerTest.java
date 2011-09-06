package com.blackenedsystems.games.missilecommand;

import com.blackenedsystems.games.missilecommand.mock.MockGameArea;
import com.blackenedsystems.games.missilecommand.mock.MockGameController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;

/**
 * Unit tests for the <code>GameController</code> class.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 11:12:37 PM
 */
public class GameControllerTest {

    private static final int DEFENSIVE_OBJECT_Y_COORDINATE = 325;

    private static final int[] CITY_X_COORDINATES = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] MISSILE_BASE_X_COORDINATES = new int[]{15, 185, 355};

    private MockGameArea gameArea;
    private MockGameController gameController;
    private List<DefensiveObject> defensiveObjects;
    private List<MissileBase> missileBases = new ArrayList<MissileBase>(MISSILE_BASE_X_COORDINATES.length);

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
            missileBases.add(missileBase);
        }

        gameController = new MockGameController(gameArea, defensiveObjects);
    }

    @Test
    public void initialState() {
        assertFalse(gameController.isGameInProgress());
        assertEquals(0, gameController.numberOfMissiles()); 
    }

    @Test
    public void fireMissile() {
        assertEquals(0, gameController.numberOfMissiles());

        gameController.fireMissile(missileBases.get(0), new Point2D.Double(200,200));
        assertEquals(1, gameController.numberOfMissiles()); 
    }

    @Test
    public void runASingleLevel() {
        // ...
    }
}
