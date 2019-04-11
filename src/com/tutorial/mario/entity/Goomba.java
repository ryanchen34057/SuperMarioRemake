package com.tutorial.mario.entity;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Goomba extends Entity{

    private Random random = new Random();

    public Goomba(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        int dir = random.nextInt(2);

        switch (dir) {
            case 0:
                setVelX(-3);
                facing = 0;
                break;
            case 1:
                setVelX(3);
                facing = 1;
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        if(facing == 0) {
            g.drawImage(Game.goombas[frame+3].getBufferedImage(), x, y, width, height, null);
        }
        else {
            g.drawImage(Game.goombas[frame].getBufferedImage(), x, y, width, height, null);
        }
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
                    setVelX(2);
                    facing = 1;
                }
                if(getBoundsRight().intersects(t.getBounds())) {
                    setVelX(-2);
                    facing = 0;
                }
            }
        }

        for(int i=0;i<handler.entities.size();i++) {
            Entity e = handler.entities.get(i);
            if(e.getId() == Id.player) {
                if(getBoundsTop().intersects(e.getBoundsBottom())) {
                    die();
                }
                else if(getBounds().intersects(e.getBounds())) {
                    e.die();
                }
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
