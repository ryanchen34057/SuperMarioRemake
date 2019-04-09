package com.tutorial.mario;

import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;
import org.omg.IOP.ENCODING_CDR_ENCAPS;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    public LinkedList<Entity> entities = new LinkedList<>();
    public LinkedList<Tile> tiles = new LinkedList<>();

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
}
