package com.blackenedsystems.games.missilecommand.mock;

import com.blackenedsystems.games.missilecommand.GameArea;

import java.awt.*;

/**
 * Extends the <code>GameArea</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 10:53:08 PM
 */
public class MockGameArea extends GameArea {

    private MockGameAreaParent mockGameAreaParent = new MockGameAreaParent();

    @Override
    public Container getParent() {
        return mockGameAreaParent;
    }

    private class MockGameAreaParent extends Container {
        @Override
        public int getWidth() {
            return 400;
        }
    }
}
