package allen.ma;

import com.google.gson.annotations.Expose;
import javafx.scene.paint.Color;

import java.util.UUID;

public class Picture {
  @Expose private UUID id;
  @Expose private Color color;

  public Picture(Color color) {
    this.id = UUID.randomUUID();
    this.color = color;
  }

  public Color getColor() {
    return color;
  }
}
