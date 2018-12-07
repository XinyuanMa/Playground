package allen.ma.service;

import allen.ma.model.Picture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.UUID;

public interface PictureService {

  Picture getCurrentPicture();

  Map<UUID, Picture> getAllPictures();

  void setCurPicture(Picture picture);

  void createNewPicture(Picture picture);
  void removePicture(Picture picture);

  boolean exportPictureToBMP(Picture picture, Image pictureImage);
  boolean exportPictureToPNG(Picture picture, Image pictureImage);
  boolean exportPictureToByteArray(Picture picture, Image pictureImage);
  boolean exportPictureToBooleanArary(Picture picture, Image pictureImage);
  boolean exportPictureToTxtFile(Picture picture, Image pictureImage);

  boolean serializeToJson(Picture picture);

  Image loadPictureFromBMP(Picture picture);
  Image loadPictureFromPNG(Picture picture);
  Image loadPictureFromByteArray(Picture picture);
  Image loadPictureFromBooleanArray(Picture picture);
  Image loadPictureFromTxtFile(Picture picture);
}
