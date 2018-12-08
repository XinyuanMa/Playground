package allen.ma.controller;

import allen.ma.Config;
import allen.ma.Main;
import allen.ma.Utils;
import allen.ma.model.Picture;
import allen.ma.service.PictureService;
import allen.ma.service.PictureServiceImpl;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoginScene {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginScene.class);

  private static final PictureService service = PictureServiceImpl.getInstance();

  private GridPane root;
  private Label widthLabel;
  private Label heightLabel;
  private Label colorPickerLabel;
  private TextField widthField;
  private TextField heightField;
  private Button startBtn;
  private Button importBtn;
  private ColorPicker colorPicker;
  private FileChooser fileChooser;

  public LoginScene() {
    root = new GridPane();
    widthLabel = new Label("Width");
    heightLabel = new Label("Height");
    colorPickerLabel = new Label("Color");
    widthField = new TextField();
    heightField = new TextField();
    startBtn = new Button("Start");
    importBtn = new Button("Import");
    fileChooser = new FileChooser();
    colorPicker = new ColorPicker(Color.BLACK);

    startBtn.setOnAction(this::onStartButton);
    importBtn.setOnAction(this::onImportButton);

    intiView();
  }

  public GridPane getRoot() {
    return root;
  }

  private void intiView() {
    fileChooser.getExtensionFilters().addAll(
        Utils.buildExtensionFilter(Config.get().pictureMetaFileExt())
    );

    root.setAlignment(Pos.CENTER);
    root.setHgap(10);
    root.setVgap(10);
    root.setPadding(new Insets(25, 25, 25, 25));
    root.add(widthLabel, 0, 0);
    root.add(heightLabel, 0, 1);
    root.add(colorPickerLabel, 0, 2);
    root.add(widthField, 1, 0);
    root.add(heightField, 1, 1);
    root.add(colorPicker, 1, 2);
    root.add(importBtn, 0, 3);
    root.add(startBtn, 1, 3);
  }

  private Color getColor() {
    return colorPicker.getValue();
  }

  private Integer getWidth() throws NumberFormatException {
    return Integer.parseInt(widthField.getText());
  }

  private Integer getHeight() throws  NumberFormatException {
    return Integer.parseInt(heightField.getText());
  }

  private void onStartButton(ActionEvent event) {
    Color color = getColor();
    if (color.equals(Color.WHITE)) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Color WHITE is not allowed");
      alert.setContentText("What? You want to drawn on whiteboard with white color?");
      alert.showAndWait();
      return;
    }

    try {
      Integer width = getWidth();
      Integer height = getHeight();
      Picture picture = new Picture(color, width, height);
      service.setCurPicture(picture);
      Main.showMainScene();
    } catch (NumberFormatException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Error in width/height value");
      alert.setContentText("Please kindly type numbers in width/height fields, would ya?");
      alert.showAndWait();
      return;
    }
  }

  private void onImportButton(ActionEvent event) {
    Window stage = root.getScene().getWindow();
    File selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile == null || !selectedFile.isFile()) {
      return;
    }
    LOGGER.debug("selectedFile is {}", selectedFile);
  }
}
