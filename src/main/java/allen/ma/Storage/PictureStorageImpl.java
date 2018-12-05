package allen.ma.Storage;

import allen.ma.Picture;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class PictureStorageImpl implements PictureStorage {

  private static final Logger LOGGER = LoggerFactory.getLogger(PictureStorageImpl.class);
  Picture curPicture = Picture.getInstance();

  @Override
  public void storePicture(UUID id) {
    Path getPath = getPicturePath(id);

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
    return Paths.get(id.toString(), ".bmp");
  }

  @Override
  public Path getPictureMetaFilePath(UUID id) {
    return Paths.get(id.toString(), ".meta.json");
  }

}
