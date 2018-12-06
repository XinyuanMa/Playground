package allen.ma.Storage;

import allen.ma.Adapter.ColorAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;

public class GsonProvider {
  public static Gson getGson() {
    // go to https://github.com/google/gson for more information
    return new GsonBuilder()
        .serializeNulls()
        .setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .registerTypeAdapter(Color.class, new ColorAdapter())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
  }
}
