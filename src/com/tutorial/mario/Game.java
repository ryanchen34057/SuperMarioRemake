package com.tutorial.mario;

import com.tutorial.mario.Input.KeyInput;
import com.tutorial.mario.entity.Player;
import com.tutorial.mario.graphics.Sprite;
import com.tutorial.mario.graphics.SpriteSheet;
import com.tutorial.mario.tile.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 270;
    public static final int HEIGHT = WIDTH / 14*10;
    public static final int SCALE = 4;
    public static final String TITLE = "Mario";
    public static Handler handler;
    public static SpriteSheet sheet;
    public static Sprite grass;
    public static Sprite[] players;

    private Thread thread;
    private boolean running = false;

    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        players = new Sprite[6];
    }

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("/res/spriteSheet.png");
        addKeyListener(new KeyInput());
        handler.addObject(new Player(300, 512, 64, 64, true, Id.player, handler));

        grass = new Sprite(sheet, 1,1);

        for(int i=0;i<players.length;i++) {
            players[i] = new Sprite(sheet, i+1, 16);
        }
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
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public void tick() {
        handler.tick();
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
