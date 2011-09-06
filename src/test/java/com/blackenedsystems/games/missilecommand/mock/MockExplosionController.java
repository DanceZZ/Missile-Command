package com.blackenedsystems.games.missilecommand.mock;

import com.blackenedsystems.games.missilecommand.ExplosionController;
import com.blackenedsystems.games.missilecommand.Missile;
import com.blackenedsystems.games.missilecommand.DefensiveObject;
import com.blackenedsystems.games.missilecommand.Explosion;

import java.util.List;

/**
 * Extends the <code>ExplosionController</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 9:07:47 PM
 */
public class MockExplosionController extends ExplosionController {

    public MockExplosionController(List<Missile> missiles, List<DefensiveObject> defensiveObjects) {
        super(missiles, defensiveObjects);
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public List<DefensiveObject> getDefensiveObjects() {
        return defensiveObjects;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public int numberOfExplosions() {
        return explosions.size();
    }
}
