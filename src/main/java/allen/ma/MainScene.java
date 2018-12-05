package allen.ma;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainScene {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainScene.class);

  @FXML private VBox root;
  @FXML private HBox btnHolderBox;
  @FXML private Canvas canvas;
  @FXML private Button btnClear;
  @FXML private Button btnExport;
  @FXML private Button btnImport;

  private Integer width;
  private Integer height;

  private LongProperty bmpFileSize = new SimpleLongProperty();
  private LongProperty pngFileSize = new SimpleLongProperty();

  public MainScene() {
    width = Picture.getInstance().getWidth();
    height = Picture.getInstance().getHeight();
    root = new VBox(3);
    canvas = new Canvas(width, height);
    btnHolderBox = new HBox(3);
    btnClear = new Button("Clear");
    btnExport = new Button("Export");
    btnImport = new Button("Import");

    initView();
    applyBehaviors();
  }

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }

  private void initView() {
    root.getChildren().add(btnHolderBox);
    root.getChildren().add(canvas);

    btnHolderBox.getChildren().add(btnClear);
    btnHolderBox.getChildren().add(btnExport);
    btnHolderBox.getChildren().add(btnImport);
  }

  public VBox getRoot() {
    return root;
  }

  private void applyBehaviors() {
    // canvas behaviors
    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> onPressed(e, canvas));
    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> onDragged(e, canvas));
    canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> onReleased(e, canvas));

    // button behaviors
    btnExport.setOnAction(e -> onBtnExportClicked(e, canvas));
    btnClear.setOnAction(e -> onBtnClearClicked(e, canvas));
    btnImport.setOnAction(e -> onBtnImportClicked(e, canvas));
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
    BufferedImage bBimg = Utils.generateBinaryBimg(canvas);
    BufferedImage bimg = Utils.generateBimg(canvas);

    // exported to build/jfx/app/
    File fileOfBinary = new File("export.bmp");
    File fileOfRGB = new File("export.png");

    try {
      ImageIO.write(bBimg, "bmp", fileOfBinary);
      ImageIO.write(bimg, "png", fileOfRGB);

      setBmpFileSize(fileOfBinary.length());
      setPngFileSize(fileOfRGB.length());

      String info = "BMP size is: " + getBmpFileSize() + " KB, PNG size is: " + getPngFileSize() + " KB.";
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Export successful");
      alert.setContentText(info);
      alert.setHeaderText(null);
      alert.showAndWait();
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

  public void setBmpFileSize(long bmpFileSize) {
    this.bmpFileSize.set(bmpFileSize);
  }

  public void setPngFileSize(long pngFileSize) {
    this.pngFileSize.set(pngFileSize);
  }

  public long getBmpFileSize() {
    return bmpFileSize.get() / 1000;
  }

  public long getPngFileSize() {
    return pngFileSize.get() / 1000;
  }
}
