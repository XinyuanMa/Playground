package allen.ma.Storage;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
  public static Gson getGson() {
    return new GsonBuilder().serializeNulls().setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
  }
}
