package allen.ma;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Drawing Operations Test");

    // root contains 1 HBox and 1 canvas
    VBox root = new VBox(3);

    // canvas
    Canvas canvas = new Canvas(800, 600);
    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> onPressed(e, canvas));
    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> onDragged(e, canvas));
    canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> onReleased(e, canvas));

    // buttons
    HBox box = new HBox(3);
    Button btnClear = new Button("Clear");
    Button btnExport = new Button("Export");
    Button btnImport = new Button("Import");
    Button btnRender = new Button("Render");
    btnExport.setOnAction(e -> onBtnExportClicked(e, canvas));
    btnClear.setOnAction(e -> onBtnClearClicked(e, canvas));
    btnImport.setOnAction(e -> onBtnImportClicked(e, canvas));
    btnRender.setOnAction(e -> onBtnRenderClicked(e, canvas));
    box.getChildren().add(btnClear);
    box.getChildren().add(btnExport);
    box.getChildren().add(btnImport);
    box.getChildren().add(btnRender);

    // put HBox and canvas into root and root into stage
    root.getChildren().add(box);
    root.getChildren().add(canvas);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  private void onPressed(MouseEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color color = Color.rgb(204, 0, 204);
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
//    WritableImage image = changeOutputImageColor(canvas);
//    BufferedImage bimg = new BufferedImage((int)image.getWidth(), (int)image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
//    BufferedImage bimg = SwingFXUtils.fromFXImage(image, null);

    final int width = (int)canvas.getWidth();
    final int height = (int)canvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    byte[] imageData = generateImageData(canvas);

    PixelWriter writer = image.getPixelWriter();
    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
    writer.setPixels(0, 0, width, height, pixelFormat, imageData, 0, width * 3);

    // exported to build/jfx/app/
    File file = new File("export.png");

    try {
      ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
//      ImageIO.write(bimg, "png", file);
      LOGGER.debug("Image is successfully exported to {}", file.getAbsolutePath());
    } catch (Exception e) {
      LOGGER.debug("Fail to export the image");
    }
  }

  private void onBtnClearClicked(ActionEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  private WritableImage changeOutputImageColor(Canvas canvas) {
    final int width = (int)canvas.getWidth();
    final int height = (int)canvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    canvas.snapshot(null, image);

    PixelWriter pixelWriter = image.getPixelWriter();
    PixelReader pixelReader = image.getPixelReader();
    LOGGER.debug("{}", pixelReader.getPixelFormat().getType());

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = pixelReader.getColor(x, y);
        if (color.getRed() != 1.0 && color.getGreen() != 1.0 && color.getBlue() != 1.0) {
          pixelWriter.setColor(x, y, new Color(0.5, 0.5, 0.5, 1));
        }
      }
    }

    return image;
  }

  private byte[] generateImageData(Canvas canvas) {
    final int width = (int)canvas.getWidth();
    final int height = (int)canvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    canvas.snapshot(null, image);
    PixelReader pixelReader = image.getPixelReader();

    byte[] imageData = new byte[width * height * 3];
    Arrays.fill(imageData, (byte)128);

    int i = 0;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color color = pixelReader.getColor(x, y);
        if (color.getRed() != 0 && color.getGreen() != 0 && color.getBlue() != 0) {
          imageData[i] = (byte)255;
          imageData[i + 1] = (byte)255;
          imageData[i + 2] = (byte)255;
        }
        i += 3;
      }
    }

    return imageData;
  }

  private void onBtnImportClicked(ActionEvent event, Canvas canvas) {
    byte[] imageData = generateImageData(canvas);


  }

  private void onBtnRenderClicked(ActionEvent event, Canvas canvas) {
    byte[] imageData = createImageData(canvas);
    drawImageData(canvas, imageData);
  }

  private byte[] createImageData(Canvas canvas) {
    final int IMAGE_WIDTH = 10;
    final int IMAGE_HEIGHT = 10;
    byte[] imageData = new byte[IMAGE_WIDTH * IMAGE_HEIGHT * 3];

    int i = 0;
    for (int y = 0; y < IMAGE_HEIGHT; y++) {
      int r = y * 255 / IMAGE_HEIGHT;
      for (int x = 0; x < IMAGE_WIDTH; x++) {
        int g = x * 255 / IMAGE_WIDTH;
        imageData[i] = (byte) r;
        imageData[i + 1] = (byte) g;
        i += 3;
      }
    }

    return imageData;
  }

  private void drawImageData(Canvas canvas, byte[] imageData) {
    final int IMAGE_WIDTH = 10;
    final int IMAGE_HEIGHT = 10;
    GraphicsContext gc = canvas.getGraphicsContext2D();
    boolean on = true;
    PixelWriter pixelWriter = gc.getPixelWriter();
    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
    for (int y = 50; y < 150; y += IMAGE_HEIGHT) {
      for (int x = 50; x < 150; x += IMAGE_WIDTH) {
        if (on) {
          pixelWriter.setPixels(x, y, IMAGE_WIDTH,
              IMAGE_HEIGHT, pixelFormat, imageData,
              0, IMAGE_WIDTH * 3);
        }
        on = !on;
      }
      on = !on;
    }

    // Add drop shadow effect
    gc.applyEffect(new DropShadow(20, 20, 20, Color.GRAY));
  }



  public static void main(String[] args) {
    BasicConfigurator.configure();
    launch(args);
  }
}
