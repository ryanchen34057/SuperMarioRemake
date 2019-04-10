package com.tutorial.mario;

import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;
import com.tutorial.mario.tile.Wall;
import org.omg.IOP.ENCODING_CDR_ENCAPS;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    public LinkedList<Entity> entities = new LinkedList<>();
    public LinkedList<Tile> tiles = new LinkedList<>();

    public Handler() {
        createLevel();
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
        for(Entity en: entities) {
            en.tick();
        }
        for(Tile t: tiles) {
            t.tick();
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

    public void createLevel() {
        for(int i=0;i<=Game.WIDTH*Game.SCALE/64;i++) {
            addObject(new Wall(i*64, Game.HEIGHT*Game.SCALE-64, 64,64,true, Id.wall, this));
            if(i != 0 && i != 1 && i != 16 && i != 17) {
                addObject(new Wall(i*64, 300, 64,64,true, Id.wall, this));
            }
        }
    }
}
