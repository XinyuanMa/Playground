package allen.ma.controller;

import allen.ma.model.Picture;
import allen.ma.service.PictureService;
import allen.ma.service.PictureServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditScene {

  private static final Logger LOGGER = LoggerFactory.getLogger(EditScene.class);

  private static final PictureService service = PictureServiceImpl.getInstance();

  private VBox root;
  private HBox btnHolderBox;
  private Canvas canvas;
  private Button btnClear;
  private Button btnExport;
  private ColorPicker colorPicker;

  private Image image;

  private Integer width;
  private Integer height;

  public EditScene() {
    width = service.getCurrentPicture().getWidth();
    height = service.getCurrentPicture().getHeight();

    root = new VBox(3);

    btnHolderBox = new HBox(3);
    btnClear = new Button("Clear");
    btnExport = new Button("Export");
    colorPicker = new ColorPicker(service.getCurrentPicture().getColor());

    image = service.loadPicture(service.getCurrentPicture());
    canvas = new Canvas(width, height);

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

    btnHolderBox.getChildren().add(colorPicker);
    btnHolderBox.getChildren().add(btnClear);
    btnHolderBox.getChildren().add(btnExport);

    canvas.getGraphicsContext2D().drawImage(image, 0, 0);
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

    // color picker behavior
    colorPicker.setOnAction(this::onColorChanged);
  }

  private void onPressed(MouseEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color color = service.getCurrentPicture().getColor();
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
    Picture picture = service.getCurrentPicture();
    if (service.exportPicture(picture, canvas.snapshot(null, null))) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Export result");
      alert.setHeaderText(null);
      alert.setContentText("Successfully output pictures!");
      alert.showAndWait();
    }
  }

  private void onBtnClearClicked(ActionEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  private void onColorChanged(Event e) {
    Color newColor = colorPicker.getValue();
    service.setCurPictureColor(newColor);
    refreshCanvas();
  }

  private void refreshCanvas() {
    WritableImage writableImage = new WritableImage(width, height);
    PixelReader reader = image.getPixelReader();
    PixelWriter writer = writableImage.getPixelWriter();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = reader.getColor(x, y);
        if (color.getRed() != 1 || color.getBlue() != 1 || color.getGreen() != 1) {
          writer.setColor(x, y, service.getCurrentPicture().getColor());
        }
      }
    }

    canvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);
  }
}
