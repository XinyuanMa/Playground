package allen.ma.storage;

import allen.ma.Config;
import allen.ma.model.Picture;
import allen.ma.Utils;
import com.google.gson.Gson;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
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
  public boolean storeBMPPicture(Picture picture, Image image) {
    BufferedImage bBimg = Utils.generateBinaryBimg(image);

    // exported to build/jfx/app/<id>.bmp
    File fileOfBinary = getBMPPicturePath(picture).toFile();

    try {
      ImageIO.write(bBimg, formats[0], fileOfBinary);
      return true;
    } catch (Exception e) {
      LOGGER.debug("Fail to export the image");
      return false;
    }
  }

  @Override
  public boolean storePNGPicture(Picture picture, Image image) {
    BufferedImage bimg = Utils.generateBimg(image);

    // exported to build/jfx/app/<id>.png
    File fileOfRGB = getPNGPicturePath(picture).toFile();

    try {
      ImageIO.write(bimg, formats[1], fileOfRGB);
      return true;
    } catch (Exception e) {
      LOGGER.debug("Fail to export the image");
      return false;
    }
  }

  @Override
  public boolean storeByteArray(Picture picture, Image image) {
    return false;
  }

  @Override
  public boolean storeBooleanArray(Picture picture, Image image) {
    return false;
  }

  @Override
  public boolean storeTxtFile(Picture picture, Image image) {
    return false;
  }

  @Override
  public boolean serializeToJson(Picture picture) {
    Gson gson = getGson();

    try (Writer writer = new FileWriter(getPictureMetaFilePath(picture).toString())) {
      gson.toJson(picture, writer);
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
  public Path getBMPPicturePath(Picture picture) {
    return Paths.get(String.format("%s.%s", picture.getId().toString(), formats[0]));
  }

  @Override
  public Path getPNGPicturePath(Picture picture) {
    return Paths.get(String.format("%s.%s", picture.getId().toString(), formats[1]));
  }

  @Override
  public Path getPictureMetaFilePath(Picture picture) {
    return Paths.get(String.format("%s.%s", picture.getId().toString(), Config.get().pictureMetaFileExt()));
  }
}
