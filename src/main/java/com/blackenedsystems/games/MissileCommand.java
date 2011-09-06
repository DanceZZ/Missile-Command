package com.blackenedsystems.games;

import com.blackenedsystems.games.missilecommand.GameArea;

import javax.swing.*;

/**
 * Main class for the Missile Command game.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 11:52:51 AM
 */
public class MissileCommand extends JFrame {

    public MissileCommand() {
        getContentPane().add(new GameArea());
        setSize(400, 350);
        setTitle("Missile Command");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    public static void main(String[] args) {
        MissileCommand missileCommand = new MissileCommand();
    }
}
