package allen.ma;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainSceneBehaviors {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainSceneBehaviors.class);

  private MainSceneController controller;

  public MainSceneBehaviors(MainSceneController controller) {
    this.controller = controller;
  }

  private void apply() {
    // TODO
  }

  private void onPressed(MouseEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color color = Picture.getInstance().getColor();
    double markerSize = 5.0;

    gc.setFill(color);
    gc.setStroke(color);
    gc.setLineWidth(markerSize);
    gc.beginPath();
    gc.moveTo(event.getX(), event.getY());
    gc.stroke();
    gc.fillOval(event.getX(), event.getY(), markerSize, markerSize);
    event.consume();
  }

  private void onDragged(MouseEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.lineTo(event.getX(), event.getY());
    gc.stroke();
    gc.closePath();
    gc.beginPath();
    gc.moveTo(event.getX(), event.getY());
    event.consume();
  }

  private void onReleased(MouseEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.closePath();
    event.consume();
  }

  private void onBtnExportClicked(ActionEvent event, Canvas canvas) {
    BufferedImage bBimg = generateBinaryBimg(canvas);
    BufferedImage bimg = generateBimg(canvas);

    // exported to build/jfx/app/
    File fileOfBinary = new File("export.bmp");
    File fileOfRGB = new File("export.png");

    try {
      boolean result1 = ImageIO.write(bBimg, "bmp", fileOfBinary);
      LOGGER.debug("result is {}", result1);
      LOGGER.debug("{}", bBimg.getType());
      LOGGER.debug("Image is successfully exported to {}", fileOfBinary.getAbsolutePath());

      boolean result2 = ImageIO.write(bimg, "png", fileOfRGB);
      LOGGER.debug("result is {}", result2);
      LOGGER.debug("{}", bimg.getType());
      LOGGER.debug("Image is successfully exported to {}", fileOfRGB.getAbsolutePath());
    } catch (Exception e) {
      LOGGER.debug("Fail to export the image");
    }
  }

  private void onBtnImportClicked(ActionEvent event, Canvas canvas) {
    // TODO
  }

  private void onBtnClearClicked(ActionEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  private BufferedImage generateBinaryBimg(Canvas canvas) {
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

  private BufferedImage generateBimg(Canvas canvas) {
    final int width = (int)canvas.getWidth();
    final int height = (int)canvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    canvas.snapshot(null, image);

    return SwingFXUtils.fromFXImage(image, null);
  }
}
