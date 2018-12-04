package allen.ma;

import com.google.gson.annotations.Expose;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Picture {

  private static final Logger LOGGER = LoggerFactory.getLogger(Picture.class);

  @Expose
  private UUID id;
  @Expose
  private Color color;
  @Expose
  private Integer width;
  @Expose
  private Integer height;

  private static Picture pictureInstance;

  private Picture() {}

  public static Picture getInstance() {
    if (pictureInstance == null) {
      pictureInstance = new Picture();
    }

    return pictureInstance;
  }

  public void init(Color color, Integer width, Integer height) {
    pictureInstance.id = UUID.randomUUID();
    pictureInstance.color = color;
    pictureInstance.width = width;
    pictureInstance.height = height;
  }

  public UUID getId() {
    return pictureInstance.id;
  }

  public Integer getWidth() {
    return pictureInstance.width;
  }

  public Integer getHeight() {
    return pictureInstance.height;
  }

  public Color getColor() {
    return pictureInstance.color;
  }
}