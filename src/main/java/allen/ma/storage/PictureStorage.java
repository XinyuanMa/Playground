package allen.ma.storage;

import allen.ma.model.Picture;
import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public interface PictureStorage {

  boolean storeBMPPicture(Picture picture, Image image);
  boolean storePNGPicture(Picture picture, Image image);
  boolean storeByteArray(Picture picture, Image image);
  boolean storeBooleanArray(Picture picture, Image image);
  boolean storeTxtFile(Picture picture, Image image);

  boolean serializeToJson(Picture picture);
  Picture deserializeFromJson(File file);

  Picture loadBMPPicture(UUID id);
  Picture loadPNGPicture(UUID id);
  Picture loadBMPPictureFromFolder(File srcFile);
  Picture loadPNGPictureFromFolder(File srcFile);

  Path getBMPPicturePath(Picture picture);
  Path getPNGPicturePath(Picture picture);
  Path getPictureMetaFilePath(Picture picture);
}
