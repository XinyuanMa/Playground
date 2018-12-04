package allen.ma;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainSceneController {
  @FXML private VBox root;
  @FXML private HBox btnHolderBox;
  @FXML private Canvas canvas;
  @FXML private Button btnClear;
  @FXML private Button btnExport;
  @FXML private Button btnImport;

  private Integer width;
  private Integer height;

  private MainSceneBehaviors mainSceneBehaviors;

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }

  public MainSceneController() {
    width = Picture.getInstance().getWidth();
    height = Picture.getInstance().getHeight();
    root = new VBox(3);
    canvas = new Canvas(getWidth(), getHeight());
    btnHolderBox = new HBox(3);
    btnClear = new Button("Clear");
    btnExport = new Button("Export");
    btnImport = new Button("Import");

    initView();
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

}
