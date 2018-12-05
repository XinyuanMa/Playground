package allen.ma.Storage;

import allen.ma.Picture;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public interface PictureStorage {

  void storePicture(UUID id);

  Picture loadPicture(UUID id);
  Picture loadPictureFromFolder(File srcFile);

  Path getPicturePath(UUID id);
  Path getPictureMetaFilePath(UUID id);
}
