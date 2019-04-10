package com.tutorial.mario.entity;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.tile.Tile;

import java.awt.*;

public class Player extends Entity{

    private int frame = 0;
    private int frameDelay = 0;

    private boolean animate = false;

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        if(facing == 0) {
            g.drawImage(Game.players[frame+3].getBufferedImage(), x, y, width, height, null);
        }
        else {
            g.drawImage(Game.players[frame].getBufferedImage(), x, y, width, height, null);
        }

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if(x + width >= Game.WIDTH * Game.SCALE) x = Game.WIDTH * Game.SCALE - width;
        if(y + height >= Game.WIDTH/14*10 * Game.SCALE) y = Game.WIDTH/14*10 * Game.SCALE - height;
        if(velX != 0) animate = true;
        else animate = false;
        for(Tile t: handler.tiles) {
            if(!t.solid) break;
            if(t.getId() == Id.wall) {
                if(getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);
                    if(jumping) {
                        jumping = false;
                        falling = true;
                        gravity = 0.8;
                    }
                }
                if(getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    if(falling) { falling = false; }
                }
                else {
                    if(!falling && !jumping) {
                        gravity = 0.8;
                        falling = true;
                    }
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
        if(jumping) {
            gravity -= 0.1;
            setVelY((int)(-gravity));
            if(gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }
        if(falling) {
            gravity += 0.1;
            setVelY((int)(gravity));
        }
        if(animate) {
            frameDelay++;
            if(frameDelay >= 3) {
                frame++;
                if(frame >= Game.players.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
}
