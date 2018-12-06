package allen.ma.adapter;

import com.google.gson.*;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
  @Override
  public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject colorJson = new JsonObject();
    colorJson.addProperty("r", src.getRed());
    colorJson.addProperty("g", src.getGreen());
    colorJson.addProperty("b", src.getBlue());

    return context.serialize(colorJson);
  }

  @Override
  public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject colorJson = json.getAsJsonObject();
    if (colorJson.get("r") == null
      || colorJson.get("g") == null
      || colorJson.get("b") == null) {
      throw new JsonParseException("Fail to deserialize RGB.");
    }

    Color colorObject = Color.color(colorJson.get("r").getAsDouble()
                                    , colorJson.get("g").getAsDouble()
                                    , colorJson.get("b").getAsDouble());

    return colorObject;
  }
}
