package com.soda3x.sge;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Texture {
  public int[] pixels;
  private final String resourcePath;
  public final int SIZE;

  public Texture(String resourcePath, int size) {
    this.resourcePath = resourcePath;
    this.SIZE = size;
    this.pixels = new int[SIZE * SIZE];
    load();
  }

  private void load() {
    try (InputStream is = getClass().getClassLoader()
        .getResourceAsStream(resourcePath)) {

      if (is == null) {
        throw new IOException("Resource not found: " + resourcePath);
      }

      BufferedImage image = ImageIO.read(is);
      int w = image.getWidth();
      int h = image.getHeight();

      image.getRGB(0, 0, w, h, pixels, 0, w);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static final Texture wood = new Texture("textures/wood.jpg", 64);

  public static final Texture brick = new Texture("textures/redbrick.jpg", 64);

  public static final Texture bluestone = new Texture("textures/bluestone.jpg", 64);

  public static final Texture stone = new Texture("textures/greystone.jpg", 64);

  public static final Texture ricecrispu = new Texture("textures/ricecrispu.jpg", 64);

  public static final Texture sleepyaleks = new Texture("textures/sleepyaleks.jpg", 64);
}