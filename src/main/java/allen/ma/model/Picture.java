package allen.ma.model;

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

  public Picture(Color color, Integer width, Integer height) {
    this.id = UUID.randomUUID();
    this.color = color;
    this.width = width;
    this.height = height;
  }

  // I'll just leave it this way. Maybe later I'll put all existing pictures together in a meta.json file.
  public Picture(UUID id, Color color, Integer width, Integer height) {
    this.id = id;
    this.color = color;
    this.width = width;
    this.height = height;
  }

  public UUID getId() {
    return id;
  }

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}