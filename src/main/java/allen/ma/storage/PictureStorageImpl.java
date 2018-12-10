package allen.ma.storage;

import allen.ma.Config;
import allen.ma.model.Picture;
import allen.ma.Utils;
import com.google.gson.Gson;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
  public Picture deserializeFromJson(File file) {
    try (Reader reader = new FileReader(file)) {
      return getGson().fromJson(reader, Picture.class);
    } catch (Exception e) {
      LOGGER.error("Fail to load the picture", e);
      throw new IllegalStateException("Invalid Picture JSON file.");
    }
  }

  @Override
  public Image loadBMPPicture(Picture picture) {
    String path = getBMPPicturePath(picture).toUri().toString();
    Image imageFromBMP = new Image(path);
    return imageFromBMP;
  }

  @Override
  public Image loadPNGPicture(Picture picture) {
    String path = getPNGPicturePath(picture).toUri().toString();
    Image imageFromPNG = new Image(path);
    return imageFromPNG;
  }

  @Override
  public Image loadBytePicture(Picture picture) {
    return null;
  }

  @Override
  public Image loadBooleanPicture(Picture picture) {
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

  private boolean isPictureMetaFile(File file) {
    return file != null && file.isFile()
        && StringUtils.endsWithIgnoreCase(file.getName(), Config.get().pictureMetaFileExt());
  }
}
