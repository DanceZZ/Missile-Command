package com.blackenedsystems.games.missilecommand;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;

/**
 * Manages the creation, animation and drawing of <code>Explosions</code>.  Also manages the detection of
 * collisions between explosions and other game elements such as missiles, cities and missile bases.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 8:47:33 PM
 */
public class ExplosionController {

    private final Logger logger = Logger.getLogger(ExplosionController.class);

    protected List<Explosion> explosions = new ArrayList<Explosion>();
    protected List<Missile> missiles;
    protected List<DefensiveObject> defensiveObjects;

    /**
     * NB. The missiles and defensive objects lists are shared between a number of objects, i.e.
     * there state is changed in objects other than this one.
     *
     * @param missiles          a list of missiles
     * @param defensiveObjects  a list of defensive OBJECTS
     */
    public ExplosionController(List<Missile> missiles, List<DefensiveObject> defensiveObjects) {
        this.missiles = missiles;
        this.defensiveObjects = defensiveObjects;
    }

    /**
     * @return Whether or not all the currently listed <code>Explosion</code>s complete.
     */
    public boolean areAllExplosionComplete() {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all completed explosions from the internal list of current
     * explostions.
     */
    public void removeCompletedExplosions() {
        Iterator<Explosion> iterator = explosions.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isComplete()) {
                iterator.remove();
            }
        }
    }

    /**
     * Animate each <code>Explosion</code> currently in the internal list. i.e. expand those
     * explosions that are expanding and contract those that are contracting.
     */
    public void animateExplosions() {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                explosion.animate();
            }
        }
    }

    /**
     * Draw the current state of all incomplete <code>Explosions</code> currently in the internal
     * list.
     *
     * @param graphics2D
     */
    public void drawExplosions(Graphics2D graphics2D) {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                explosion.draw(graphics2D);
            }
        }
    }

    /**
     * Have any of the current <code>Explosions</code> come into contact with any of the missiles
     * or defensive objects currently on screen?  Where there is contact, the missile or defensive
     * object is destroyed, and in the case of missiles, a secondary explosion is generated.
     */
    public void detectCollisions() {
        List<Explosion> newExplosions = new ArrayList<Explosion>();

        for (Explosion explosion : explosions) {
            if (explosion.isComplete()) {
                continue;
            }

            Rectangle explosionBounds = explosion.getBounds();
            if (explosionBounds == null) {
                continue;
            }

            for (Missile missile : missiles) {
                if (!missile.isDestroyed() && explosionBounds.contains(missile.getLocation())) {
                    explodeMissile(missile, newExplosions);
                }
            }

            for (DefensiveObject defensiveObject : defensiveObjects) {
                if (!defensiveObject.isDestroyed() && explosionBounds.intersects(defensiveObject.getBounds())) {
                    defensiveObject.destroy();
                }
            }
        }

        if (newExplosions.size() > 0) {
            explosions.addAll(newExplosions);
        }
    }

    /**
     * Generates an <code>Explosion</code> from the given missile and adds it to the internal list of
     * <code>Explosions</code>.
     *
     * @param missile   the missile to be exploded
     */
    public void explodeMissile(Missile missile) {
        explodeMissile(missile, explosions);
    }

    /**
     * Generates an <code>Explosion</code> from the given missile and adds it to the given list of
     * <code>Explosions</code>.
     *
     * @param missile
     * @param explosionsList
     */
    private void explodeMissile(Missile missile, List<Explosion> explosionsList) {
        if (logger.isDebugEnabled()) {
            logger.debug("Missile exploded at coordinates: " + missile.getCurrentCoordinates());
        }

        Explosion explosion = new Explosion(missile.getCurrentCoordinates(), missile.getGeneratedBlastRadius());
        explosionsList.add(explosion);
        missile.destroy();
    }

    public void completeAllExplosions() {
        for (Explosion explosion : explosions) {
            explosion.destroy();
        }
    }
}
