package allen.ma.service;

import allen.ma.model.Picture;
import allen.ma.storage.PictureStorage;
import allen.ma.storage.PictureStorageImpl;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PictureServiceImpl implements PictureService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PictureServiceImpl.class);
  private static final PictureService service = new PictureServiceImpl();

  // states
  private Map<UUID, Picture> pictures = new HashMap<>();
  private Picture curPicture;
  private PictureStorage storage = new PictureStorageImpl();

  private PictureServiceImpl() {}

  public static PictureService getInstance() {
    return service;
  }

  @Override
  public void setCurPicture(Picture picture) {
    curPicture = picture;
  }

  @Override
  public Picture getCurrentPicture() {
    return curPicture;
  }

  @Override
  public Map<UUID, Picture> getAllPictures() {
    return pictures;
  }

  @Override
  public void createNewPicture(Picture picture) {
    if (!pictures.containsKey(picture.getId())) {
      pictures.put(picture.getId(), picture);
    }
  }

  @Override
  public void removePicture(Picture picture) {

  }

  @Override
  public boolean exportPictureToBMP(Picture picture, Image pictureImage) {
    return storage.storeBMPPicture(picture, pictureImage);
  }

  @Override
  public boolean exportPictureToPNG(Picture picture, Image pictureImage) {
    return storage.storePNGPicture(picture, pictureImage);
  }

  @Override
  public boolean exportPictureToByteArray(Picture picture, Image pictureImage) {
    return false;
  }

  @Override
  public boolean exportPictureToBooleanArary(Picture picture, Image pictureImage) {
    return false;
  }

  @Override
  public boolean exportPictureToTxtFile(Picture picture, Image pictureImage) {
    return false;
  }

  @Override
  public boolean serializeToJson(Picture picture) {
    return storage.serializeToJson(picture);
  }

  @Override
  public Image loadPictureFromBMP(Picture picture) {
    return null;
  }

  @Override
  public Image loadPictureFromPNG(Picture picture) {
    return null;
  }

  @Override
  public Image loadPictureFromByteArray(Picture picture) {
    return null;
  }

  @Override
  public Image loadPictureFromBooleanArray(Picture picture) {
    return null;
  }

  @Override
  public Image loadPictureFromTxtFile(Picture picture) {
    return null;
  }
}
