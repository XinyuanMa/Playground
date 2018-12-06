package allen.ma.Scenes;

import allen.ma.Main;
import allen.ma.Picture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class LoginScene {
  @FXML private GridPane root;
  @FXML private Label widthLabel;
  @FXML private Label heightLabel;
  @FXML private Label colorPickerLabel;
  @FXML private TextField widthField;
  @FXML private TextField heightField;
  @FXML private Button startBtn;
  @FXML private ColorPicker colorPicker;

  public LoginScene() {
    root = new GridPane();
    widthLabel = new Label("Width");
    heightLabel = new Label("Height");
    colorPickerLabel = new Label("Color");
    widthField = new TextField();
    heightField = new TextField();
    startBtn = new Button("Start");
    colorPicker = new ColorPicker();
    startBtn.setOnAction(this::onStartButton);

    intiView();
  }

  public GridPane getRoot() {
    return root;
  }

  private void intiView() {
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

  private void onStartButton (ActionEvent event) {
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
      Picture.getInstance().init(color, width, height);
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
}
