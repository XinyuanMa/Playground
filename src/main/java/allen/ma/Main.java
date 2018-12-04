package allen.ma;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static Stage stage;
  private static Scene loginScene = null;
  private static Scene mainScene = null;

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    showLoginScene();

    stage.show();
  }

  private void showLoginScene() {
    LoginScene scene = new LoginScene();
    loginScene = new Scene(scene.getRoot(), 300, 275);
    stage.setScene(loginScene);
  }

  public static void showMainScene() {
    MainSceneController scene = new MainSceneController();
    mainScene = new Scene(scene.getRoot(), (int)scene.getRoot().getWidth(), (int)scene.getRoot().getHeight());
    stage.setScene(mainScene);
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    launch(args);
  }
}
