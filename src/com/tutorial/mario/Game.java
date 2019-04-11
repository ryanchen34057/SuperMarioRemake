package com.tutorial.mario;

import com.tutorial.mario.Input.KeyInput;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.entity.Player;
import com.tutorial.mario.graphics.Sprite;
import com.tutorial.mario.graphics.SpriteSheet;
import com.tutorial.mario.tile.Wall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 270;
    public static final int HEIGHT = WIDTH / 14*10;
    public static final int SCALE = 4;
    public static final String TITLE = "Mario";
    public static Handler handler;
    public static SpriteSheet sheet;
    public static Sprite grass;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;

    public static Sprite[] players;
    public static Sprite[] goombas;
    public static Camera cam;
    public static Sprite mushroom;

    private Thread thread;
    private boolean running = false;
    private BufferedImage image;

    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("/res/spriteSheet.png");
        cam = new Camera();

        addKeyListener(new KeyInput());

        grass = new Sprite(sheet, 1,1);
        powerUp = new Sprite(sheet, 4, 1);
        usedPowerUp = new Sprite(sheet, 5, 1);
        mushroom = new Sprite(sheet, 3, 1);
        players = new Sprite[6];
        goombas = new Sprite[6];

        for(int i=0;i<players.length;i++) {
            players[i] = new Sprite(sheet, i+1, 16);
        }

        for(int i=0;i<goombas.length;i++) {
            goombas[i] = new Sprite(sheet, i+1, 15);
        }

        try {
            image = ImageIO.read(getClass().getResource("/res/level.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.createLevel(image);
    }

    private synchronized void start() {
        if(running)return;
        running = true;
        thread = new Thread(this, "Thread");
        thread.start();
    }

    private synchronized void stop() {
        if(!running)return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60;
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0 / amountOfTicks;
        int frames = 0;
        int ticks = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(frames + " Frame Per Second " + ticks + " Updates Per Second");
                frames = 0;
                ticks = 0;
            }

        }
        stop();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        g.translate(cam.getX(), cam.getY());
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public void tick() {
        handler.tick();

        for(Entity e: handler.entities) {
            if(e.getId() == Id.player) {
                cam.tick(e);
            }
        }
    }

    public int getFrameWidth() {
        return WIDTH * SCALE;
    }

    public int getFrameHeight() {
        return HEIGHT * SCALE;
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}
