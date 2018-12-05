package allen.ma.Storage;

import allen.ma.Picture;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class PictureStorageImpl implements PictureStorage {

  private static final Logger LOGGER = LoggerFactory.getLogger(PictureStorageImpl.class);

  @Override
  public void storePicture(UUID id) {

  }

  @Override
  public Picture loadPicture(UUID id) {
    return null;
  }

  @Override
  public Picture loadPictureFromFolder(File srcFile) {
    return null;
  }

  @Override
  public Path getPicturePath(UUID id) {
    return null;
  }

  @Override
  public Path getImageMetaFilePath(UUID id) {
    return null;
  }
}
