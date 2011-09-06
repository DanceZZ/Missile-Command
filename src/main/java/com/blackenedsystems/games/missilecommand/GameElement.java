package com.blackenedsystems.games.missilecommand;

import java.awt.*;

/**
 * Base interface for the various elements of the game.
 *
 * @author Alan Tibbetts
 * @since Feb 21, 2010, 10:54:30 AM
 */
public interface GameElement {

    /**
     * Draw the current state of the <code>GameElement</code> on the given graphics
     * context.
     *
     * @param graphicsContext
     */
    void draw(Graphics2D graphicsContext);

    /**
     * Modify the current state of the <code>GameElement</code> ahead of a redraw.
     */
    void animate();

    /**
     * Mark the <code>GameElement</code> as destroyed.
     */
    void destroy();

    /**
     * @return  a <code>Rectangle</code> that completely surrounds this game element.  Used by the
     *  collision detection routines.
     */
    Rectangle getBounds();
}
