package allen.ma.storage;

import allen.ma.Config;
import allen.ma.model.Picture;
import allen.ma.Utils;
import com.google.gson.Gson;
import javafx.scene.canvas.Canvas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class PictureStorageImpl extends GsonProvider implements PictureStorage {

  private static final Logger LOGGER = LoggerFactory.getLogger(PictureStorageImpl.class);

  // formats[0] is bmp, formats[1] is png
  private static String[] formats = Config.get().pictureFileExt().split(",");

  @Override
  public boolean storeBMPPicture(UUID id, Canvas canvas) {
    BufferedImage bBimg = Utils.generateBinaryBimg(canvas);

    // exported to build/jfx/app/<id>.bmp
    File fileOfBinary = getBMPPicturePath(id).toFile();

    try {
      ImageIO.write(bBimg, formats[0], fileOfBinary);
      return true;
    } catch (Exception e) {
      LOGGER.debug("Fail to export the image");
      return false;
    }
  }

  @Override
  public boolean storePNGPicture(UUID id, Canvas canvas) {
    BufferedImage bimg = Utils.generateBimg(canvas);

    // exported to build/jfx/app/<id>.png
    File fileOfRGB = getPNGPicturePath(id).toFile();

    try {
      ImageIO.write(bimg, formats[1], fileOfRGB);
      return true;
    } catch (Exception e) {
      LOGGER.debug("Fail to export the image");
      return false;
    }
  }

  @Override
  public boolean serializeToJson(UUID id) {
    Gson gson = getGson();

    try (Writer writer = new FileWriter(getPictureMetaFilePath(id).toString())) {
      gson.toJson(Picture.getInstance(), writer);
      return true;
    } catch (IOException e) {
      LOGGER.error("Fail to write picture data as JSON file", e);
      return false;
    }
  }

  @Override
  public Picture loadBMPPicture(UUID id) {
    return null;
  }

  @Override
  public Picture loadPNGPicture(UUID id) {
    return null;
  }

  @Override
  public Picture loadBMPPictureFromFolder(File srcFile) {
    return null;
  }

  @Override
  public Picture loadPNGPictureFromFolder(File srcFile) {
    return null;
  }

  @Override
  public Path getBMPPicturePath(UUID id) {
    return Paths.get(String.format("%s.%s", id.toString(), formats[0]));
  }

  @Override
  public Path getPNGPicturePath(UUID id) {
    return Paths.get(String.format("%s.%s", id.toString(), formats[1]));
  }

  @Override
  public Path getPictureMetaFilePath(UUID id) {
    return Paths.get(String.format("%s.%s", id.toString(), Config.get().pictureMetaFileExt()));
  }
}