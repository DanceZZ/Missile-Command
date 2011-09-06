package com.blackenedsystems.games.missilecommand;

import java.util.Random;
import java.util.List;

/**
 * Creates the various enemy weapons against which the player must defend his cities and
 * missile bases.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 9:42:04 PM
 */
public class EnemyWeaponsFactory {

    private final Random randomNumberGenerator = new Random();

    private final List<DefensiveObject> targets;
    private final GameArea gameArea;

    public EnemyWeaponsFactory(GameArea gameArea, List<DefensiveObject> targets) {
        this.gameArea = gameArea;
        this.targets = targets;
    }

    /**
     * Constructs an <code>InterContinentalBallisticMissile</code>.  Its target will be randomly
     * selected from a list of defensive installations (cities and missile bases).  The missile
     * will start its flight at a random point along the top edge of the game area.
     *
     * @param   speed   the speed at which the missile will travel toward its target.
     * @return  an enemy missile.
     */
    public InterContinentalBallisticMissile createMissile(int speed) {
        return new InterContinentalBallisticMissile(generateInitialXCoordinate(), selectTarget(), speed);
    }

    private DefensiveObject selectTarget() {
        int targetId = randomNumberGenerator.nextInt(targets.size() - 1);
        return targets.get(targetId);
    }

    public int generateInitialXCoordinate() {
        return randomNumberGenerator.nextInt(gameArea.getParent().getWidth() - 1);
    }
}
