package allen.ma.Storage;

import allen.ma.Picture;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public interface PictureStorage {

  boolean storeBMPPicture(UUID id, Canvas canvas);
  boolean storePNGPicture(UUID id, Canvas canvas);
  boolean serializeToJson(UUID id);

  Picture loadBMPPicture(UUID id);
  Picture loadPNGPicture(UUID id);
  Picture loadBMPPictureFromFolder(File srcFile);
  Picture loadPNGPictureFromFolder(File srcFile);

  Path getBMPPicturePath(UUID id);
  Path getPNGPicturePath(UUID id);
  Path getPictureMetaFilePath(UUID id);
}
