package gui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    private Game game;
    public Window(Game game, String title, int w, int h) {
        super(title);
        this.game = game;
        setSize(w, h);
        setResizable(false);
        getContentPane().setPreferredSize(new Dimension(w,h));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new MyListener());
    }
    class MyListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            game.close();
        }
    }
}
