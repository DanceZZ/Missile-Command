package com.blackenedsystems.games.missilecommand;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Controls the flow of the game.  Manages the various non-static elements of the game, e.g. missiles and
 * explosions.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 10:03:57 PM
 */
public class GameController implements Runnable {

    private final Logger logger = Logger.getLogger(GameController.class);

    private final int DELAY = 50;
    private final GameArea gameArea;

    private List<DefensiveObject> defensiveObjects;
    protected List<Missile> missiles = new ArrayList<Missile>();

    private ExplosionController explosionController;
    private EnemyWeaponsFactory enemyWeaponsFactory;
    private boolean gameInProgress = false;

    public GameController(GameArea gameArea, List<DefensiveObject> defensiveObjects) {
        this.gameArea = gameArea;
        this.defensiveObjects = defensiveObjects;

        explosionController = new ExplosionController(missiles, defensiveObjects);
        enemyWeaponsFactory = new EnemyWeaponsFactory(gameArea, defensiveObjects);
    }

    /**
     * Draws all of the moving objects within the <code>GameArea</code>.
     *
     * @param graphics2D
     */
    public void paint(Graphics2D graphics2D) {
        explosionController.drawExplosions(graphics2D);
        drawMissiles(graphics2D);
    }

    private void drawMissiles(Graphics2D graphics2D) {
        for (Missile missile : missiles) {
            if (missile.hasReachedTarget() && !missile.isDestroyed()) {
                explosionController.explodeMissile(missile);
            } else if (!missile.isDestroyed()) {
                missile.draw(graphics2D);
            }
        }
    }

    private boolean allMissilesDestroyed() {
        if (missiles.size() == 0) {
            return false;
        }

        for (Missile missile : missiles) {
            if (!missile.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public void fireMissile(MissileBase missileBase, Point2D.Double mouseCoordinates) {
        if (!allMissilesDestroyed()) {
            AntiBallisticMissile missile = missileBase.fireMissile(mouseCoordinates);
            if (missile != null) {
                missiles.add(missile);
            }
        }
    }

    public void run() {

        if (logger.isDebugEnabled()) {
            logger.debug("Executing the ICBM Thread");
        }

        gameInProgress = true;

        long beforeTime, timeDiff, sleep;
        long totalTime = 0;

        beforeTime = System.currentTimeMillis();

        int icbmSpeed = 1;
        int maximumMissiles = 10;

        while (true) {

            explosionController.detectCollisions();

            for (Missile missile : missiles) {
                if (!missile.isDestroyed()) {
                    missile.animate();
                }
            }

            explosionController.animateExplosions();

            gameArea.repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            totalTime += DELAY;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();

            if (missiles.size() < maximumMissiles && totalTime % 500 == 0) {
                missiles.add(enemyWeaponsFactory.createMissile(icbmSpeed));
            }

            explosionController.removeCompletedExplosions();

            if (allMissilesDestroyed() && explosionController.areAllExplosionComplete()) {
                resetForNewLevel();
                // increase difficulty...
                icbmSpeed = icbmSpeed == 3 ? icbmSpeed : icbmSpeed + 1;
                maximumMissiles = maximumMissiles == 30 ? maximumMissiles : maximumMissiles + 5;
            }

            if (allCitiesDestoyed()) {
                for (Missile missile : missiles) {
                    missile.destroy();
                }
                explosionController.completeAllExplosions();
                gameArea.repaint();
                break;
            }
        }

        gameInProgress = false;
    }


    private boolean allCitiesDestoyed() {
        for (DefensiveObject defensiveObject : defensiveObjects) {
            if (defensiveObject.getType() == DefensiveObjectType.CITY) {
                if (!defensiveObject.isDestroyed()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void startGame() {
        if (gameInProgress) {
            return;
        }

        resetGameElements();

        Thread thread = new Thread(this);
        thread.start();
    }

    private void resetGameElements() {
        missiles = new ArrayList<Missile>();
        for (DefensiveObject defensiveObject : defensiveObjects) {
            defensiveObject.reset();
        }
        explosionController = new ExplosionController(missiles, defensiveObjects);
    }

    private void resetForNewLevel() {
        missiles = new ArrayList<Missile>();
        for (DefensiveObject defensiveObject : defensiveObjects) {
            if (defensiveObject.getType() == DefensiveObjectType.MISSILE_BASE) {
                defensiveObject.reset();
            }
        }
        explosionController = new ExplosionController(missiles, defensiveObjects);
    }
}
