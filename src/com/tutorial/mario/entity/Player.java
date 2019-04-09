package com.tutorial.mario.entity;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.tile.Tile;

import java.awt.*;

public class Player extends Entity{
    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if(x <= 0) x = 0;
        if(y <= 0) y = 0;
        if(x + width >= Game.WIDTH * Game.SCALE) x = Game.WIDTH * Game.SCALE - width;
        if(y + height >= Game.WIDTH/14*10 * Game.SCALE) y = Game.WIDTH/14*10 * Game.SCALE - height;
        for(Tile t: handler.tiles) {
            if(!t.solid) break;
            if(t.getId() == Id.wall) {
                if(getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);
                    y = t.getY() + t.height;
                }
                if(getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    y = t.getY() - t.height;
                }
                if(getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(0);
                    x = t.getX() + t.width;
                }
                if(getBoundsRight().intersects(t.getBounds())) {
                    setVelX(0);
                    x = t.getX() - t.width;
                }
            }
        }
    }
}
