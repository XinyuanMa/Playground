package allen.ma.controller;

import allen.ma.Main;
import allen.ma.model.Picture;
import allen.ma.service.PictureService;
import allen.ma.service.PictureServiceImpl;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainScene {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainScene.class);

  private static final PictureService service = PictureServiceImpl.getInstance();

  private VBox root;
  private HBox btnHolderBox;
  private Canvas canvas;
  private Button btnClear;
  private Button btnExport;
  private Button btnImport;
  private Button btnBack;
  private FileChooser fileChooser;

  private Integer width;
  private Integer height;

  public MainScene() {
    width = service.getCurrentPicture().getWidth();
    height = service.getCurrentPicture().getHeight();
    root = new VBox(3);
    canvas = new Canvas(width, height);
    btnHolderBox = new HBox(3);
    btnClear = new Button("Clear");
    btnExport = new Button("Export");
    btnImport = new Button("Import");
    btnBack = new Button("Back");

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
    btnHolderBox.getChildren().add(btnBack);
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
    btnBack.setOnAction(this::onBtnBackClicked);
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
    if (service.exportPictureToBMP(picture, canvas.snapshot(null, null))
        && service.exportPictureToPNG(picture, canvas.snapshot(null, null))
        && service.serializeToJson(picture)) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Export result");
      alert.setHeaderText(null);
      alert.setContentText("Successfully output pictures!");
      alert.showAndWait();
    }
  }

  private void onBtnImportClicked(ActionEvent event, Canvas canvas) {
    // TODO
  }

  private void onBtnClearClicked(ActionEvent event, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  private void onBtnBackClicked(ActionEvent event) {
    Main.showLoginScene();
  }
}
