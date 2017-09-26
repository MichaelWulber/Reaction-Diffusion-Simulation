package Simulation;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class ImageFrame implements Runnable {
    Display display;
    public String title;
    private Thread thread;
    private boolean running = false;
    ReactionDiffusionSim rds;

    private int width;
    private int height;

    private BufferStrategy bs;
    private Graphics g;

    public ImageFrame(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        this.display = new Display(title, width, height);
        this.display.getCanvas().createBufferStrategy(3);
        this.rds = new ReactionDiffusionSim(width, height);
    }

    private void init(){
    }

    private void tick(){
        if (rds != null){
            rds.tick();
        }
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        // clear screen
        g.clearRect(0, 0, width, height);

        // render state
        if (rds != null){
            rds.render(g);
        }

        // transfers graphics from buffer to the actual display
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        while(running){
            tick();
            render();
        }
    }

    // start
    public synchronized void start(){
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    // stop
    public synchronized void stop(){
        if (running) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
