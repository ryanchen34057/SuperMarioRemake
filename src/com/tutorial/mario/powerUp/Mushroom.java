package com.tutorial.mario.powerUp;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Mushroom extends Entity {

    private Random random = new Random();

    public Mushroom(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        int dir = random.nextInt(2);

        switch (dir) {
            case 0:
                setVelX(-3);
                break;
            case 1:
                setVelX(3);
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        for(Tile t: handler.tiles) {
            if(!t.solid) break;
            if(getBoundsBottom().intersects(t.getBounds())) {
                setVelY(0);
                if(falling) { falling = false; }
                else {
                    if(!falling) {
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if(getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(3);
                }
                if(getBoundsRight().intersects(t.getBounds())) {
                    setVelX(-3);
                }
            }
        }

        for(int i=0;i<handler.entities.size();i++) {
            Entity e = handler.entities.get(i);

            if(e.getId() == Id.player) {
                if(getBounds().intersects(e.getBounds())) {
                    int tpX = e.getX();
                    int tpY = e.getY();
                    e.setWidth(e.getWidth() * 2);
                    e.setHeight(e.getHeight() * 2);
                    e.setX(tpX - width);
                    e.setY(tpY - height);
                    die();
                }
            }
        }

        if(falling) {
            gravity += 0.1;
            setVelY((int)(gravity));
        }
    }
}
