package allen.ma;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class Utils {

  public static BufferedImage generateBinaryBimg(Canvas canvas) {
    final int width = (int)canvas.getWidth();
    final int height = (int)canvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    canvas.snapshot(null, image);
    PixelReader reader = image.getPixelReader();

    BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = reader.getColor(x, y);
        if (color.getRed() != 1 || color.getGreen() != 1 || color.getBlue() != 1) {
          bimg.setRGB(x, y, java.awt.Color.WHITE.getRGB());
        }
      }
    }

    return bimg;
  }

  public static BufferedImage generateBimg(Canvas canvas) {
    final int width = (int)canvas.getWidth();
    final int height = (int)canvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    canvas.snapshot(null, image);

    return SwingFXUtils.fromFXImage(image, null);
  }
}
