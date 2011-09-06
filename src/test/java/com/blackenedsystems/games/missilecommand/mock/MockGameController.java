package com.blackenedsystems.games.missilecommand.mock;

import com.blackenedsystems.games.missilecommand.GameController;
import com.blackenedsystems.games.missilecommand.GameArea;
import com.blackenedsystems.games.missilecommand.DefensiveObject;

import java.util.List;

/**
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 11:21:22 PM
 */
public class MockGameController extends GameController {

    public MockGameController(GameArea gameArea, List<DefensiveObject> defensiveObjects) {
        super(gameArea, defensiveObjects);
    }

    public int numberOfMissiles() {
        return missiles.size();
    }
}
