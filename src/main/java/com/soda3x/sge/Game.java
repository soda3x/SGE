package com.soda3x.sge;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

  private static final long serialVersionUID = 1L;
  public int mapWidth = 15;
  public int mapHeight = 15;
  private Thread thread;
  private boolean running;
  private BufferedImage image;
  public int[] pixels;
  public ArrayList<Texture> textures;
  public Camera camera;
  public Screen screen;

  // the numbers correspond to the load values of textures, 0 denotes empty
  public static int[][] map = {
      { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2 },
      { 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2 },
      { 1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 2 },
      { 1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2 },
      { 1, 0, 3, 0, 0, 0, 3, 0, 2, 2, 2, 0, 2, 2, 2 },
      { 1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2 },
      { 1, 0, 3, 3, 0, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2 },
      { 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2 },
      { 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4 },
      { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 },
      { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 },
      { 1, 0, 0, 5, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 },
      { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 },
      { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
      { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 }
  };

  public Game() {
    thread = new Thread(this);
    image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    textures = new ArrayList<Texture>();
    textures.add(Texture.wood);
    textures.add(Texture.brick);
    textures.add(Texture.bluestone);
    textures.add(Texture.stone);
    textures.add(Texture.sleepyaleks);
    camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);

    this.addMouseMotionListener(camera);
    this.addMouseListener(camera);
    this.addMouseWheelListener(camera);

    screen = new Screen(map, mapWidth, mapHeight, textures, 800, 600);
    addKeyListener(camera);
    setSize(800, 600);
    setResizable(false);
    setTitle("Shitty Game Engine");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.black);
    setLocationRelativeTo(null);
    setUndecorated(false);
    setVisible(true);
  }

  public static int proceduralGen() {
    // doesnt work just yet, eventually i'll work out how to randomly generate
    // levels
    int result = (int) Math.random() * 10;
    if (result < 4) {
      return result;
    } else
      return 0;
  }

  public void changeSize(int xAxis, int yAxis) {
    // doesnt work just yet, eventually it will be resizable without restarting the
    // game
  }

  public synchronized void start() {
    running = true;
    thread.start();
  }

  public synchronized void stop() {
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    bs.show();
  }

  public void run() {
    long lastTime = System.nanoTime();
    // 60 times per second
    final double ns = 1000000000.0 / 60.0;
    double delta = 0;
    requestFocus();
    while (running) {
      long now = System.nanoTime();
      delta = delta + ((now - lastTime) / ns);
      lastTime = now;
      // Make sure update is only happening 60 times a second
      while (delta >= 1) {
        // handles all of the logic restricted time
        screen.update(camera, pixels);
        camera.update(map);
        delta--;
      }
      // displays to the screen unrestricted time
      render();
    }
  }

  public static void close() {
    System.exit(0);

  }
}