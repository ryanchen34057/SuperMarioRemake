package com.tutorial.mario;

import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.entity.Goomba;
import com.tutorial.mario.entity.Player;
import com.tutorial.mario.powerUp.Mushroom;
import com.tutorial.mario.tile.Tile;
import com.tutorial.mario.tile.Wall;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    public LinkedList<Entity> entities = new LinkedList<>();
    public LinkedList<Tile> tiles = new LinkedList<>();

    public Handler() {

    }

    public void render(Graphics g) {
        for(Entity en: entities) {
            en.render(g);
        }
        for(Tile t: tiles) {
            t.render(g);
        }
    }

    public void tick() {
        for(int i=0;i<entities.size();i++) {
            entities.get(i).tick();
        }
        for(int i=0;i<tiles.size();i++) {
            tiles.get(i).tick();
        }
    }

    public void addObject(Entity en) {
        entities.add(en);
    }

    public void removeEntity(Entity en) {
        entities.remove(en);
    }

    public void addObject(Tile t) {
        tiles.add(t);
    }

    public void removeTile(Tile e) {
        tiles.remove(e);
    }

    public void createLevel(BufferedImage level) {
//        for(int i=0;i<=Game.WIDTH*Game.SCALE/64;i++) {
//            addObject(new Wall(i*64, Game.HEIGHT*Game.SCALE-64, 64,64,true, Id.wall, this));
//            if(i != 0 && i != 1 && i != 16 && i != 17) {
//                addObject(new Wall(i*64, 300, 64,64,true, Id.wall, this));
//            }
//        }
        int width = level.getWidth();
        int height = level.getHeight();

        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                int pixel = level.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 0 && green == 0 && blue == 0) {
                    addObject(new Wall(x*64, y*64, 64, 64, true, Id.wall, this));
                }
                if(red == 0 && green == 0 && blue == 255) {
                    addObject(new Player(x*64, y*64, 64, 64,Id.player, this));
                }

                if(red == 255 && green == 0 && blue == 0) {
                    addObject(new Mushroom(x*64, y*64, 64, 64, Id.mushroom, this));
                }

                if(red == 0 && green == 255 && blue == 0) {
                    addObject(new Goomba(x*64, y*64, 64, 64, Id.goomba, this));
                }
            }
        }
    }
}
