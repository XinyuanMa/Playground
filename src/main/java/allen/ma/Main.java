package allen.ma;

import allen.ma.controller.EditScene;
import allen.ma.controller.LoginScene;
import allen.ma.controller.MainScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static Stage stage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    showLoginScene();

    stage.show();
  }

  public static void showLoginScene() {
    LoginScene scene = new LoginScene();
    Scene loginScene = new Scene(scene.getRoot(), Config.get().initWindowWidth(), Config.get().initWindowHeight());
    stage.setScene(loginScene);
    stage.centerOnScreen();
  }

  public static void showMainScene() {
    MainScene scene = new MainScene();
    Scene mainScene = new Scene(scene.getRoot(), scene.getWidth(), scene.getHeight());
    stage.setScene(mainScene);
    stage.centerOnScreen();
  }

  public static void showEditScene() {
    EditScene scene = new EditScene();
    Scene editScene = new Scene(scene.getRoot(), scene.getWidth(), scene.getHeight());
    stage.setScene(editScene);
    stage.centerOnScreen();
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    launch(args);
  }
}
