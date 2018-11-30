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
    btnExport.setOnAction(e -> onBtnExportClicked(e, canvas));
    btnClear.setOnAction(e -> onBtnClearClicked(e, canvas));
    btnImport.setOnAction(e -> onBtnImportClicked(e, canvas));
    box.getChildren().add(btnClear);
    box.getChildren().add(btnExport);
    box.getChildren().add(btnImport);

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

  private void onBtnImportClicked(ActionEvent event, Canvas canvas) {
    // TODO
  }


  private void onBtnExportClicked(ActionEvent event, Canvas canvas) {
    final int width = (int)canvas.getWidth();
    final int height = (int)canvas.getHeight();
    WritableImage image = new WritableImage(width, height);
    canvas.snapshot(null, image);

    // exported to build/jfx/app/
    File file = new File("export.png");

    try {
      ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
      LOGGER.debug("Image is successfully exported to {}", file.getAbsolutePath());
    } catch (Exception e) {
      LOGGER.debug("Fail to export the image");
    }
  }

  private void onBtnClearClicked(ActionEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    launch(args);
  }
}
