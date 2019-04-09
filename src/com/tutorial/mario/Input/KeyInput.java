package com.tutorial.mario.Input;

import com.tutorial.mario.Game;
import com.tutorial.mario.entity.Entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Game.handler.entities) {
            switch (key) {
                case KeyEvent.VK_W:
                    en.setVelY(-5);
                    break;
                case KeyEvent.VK_S:
                    en.setVelY(5);
                    break;
                case KeyEvent.VK_A:
                    en.setVelX(-5);
                    break;
                case KeyEvent.VK_D:
                    en.setVelX(5);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Game.handler.entities) {
            switch (key) {
                case KeyEvent.VK_W:
                    en.setVelY(0);
                    break;
                case KeyEvent.VK_S:
                    en.setVelY(0);
                    break;
                case KeyEvent.VK_A:
                    en.setVelX(0);
                    break;
                case KeyEvent.VK_D:
                    en.setVelX(0);
                    break;
            }
        }
    }
}
