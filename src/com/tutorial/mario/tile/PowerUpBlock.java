package com.tutorial.mario.tile;

import com.tutorial.mario.Handler;
import com.tutorial.mario.Id;
import com.tutorial.mario.graphics.Sprite;

import java.awt.*;

public class PowerUpBlock extends Tile{

    private Sprite powerUp;

    private boolean poppedUp;

    private int spriteY;

    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp) {
        super(x, y, width, height, solid, id, handler);
        this.powerUp = powerUp;
        poppedUp = false;
        spriteY = getY();
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void tick() {

    }
}
