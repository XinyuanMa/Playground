package allen.ma;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {

  public static BufferedImage generateBinaryBimg(Image image) {
    final int width = (int)image.getWidth();
    final int height = (int)image.getHeight();

    PixelReader reader = image.getPixelReader();
    BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = reader.getColor(x, y);
        if (color.getRed() != 1 || color.getGreen() != 1 || color.getBlue() != 1) {
          bimg.setRGB(x, y, java.awt.Color.BLACK.getRGB());
        } else {
          bimg.setRGB(x, y, java.awt.Color.WHITE.getRGB());
        }
      }
    }

    return bimg;
  }

  public static BufferedImage generateBimg(Image image) {
    return SwingFXUtils.fromFXImage(image, null);
  }

  public static FileChooser.ExtensionFilter buildExtensionFilter(String extension) {
    String[] exts = extension.split(",");
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
        "Picture JSON files",
        Arrays.stream(exts)
            .map(ext -> String.format("*.%s", ext))
            .collect(Collectors.toList())
    );

    return filter;
  }
}
