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

  void exportPictureToBMP(Picture picture, Image pictureImage);
  void exportPictureToPNG(Picture picture, Image pictureImage);
  void exportPictureToByteArray(Picture picture, Image pictureImage);
  void exportPictureToBooleanArary(Picture picture, Image pictureImage);
  void exportPictureToTxtFile(Picture picture, Image pictureImage);

  Image loadPictureFromBMP(Picture picture);
  Image loadPictureFromPNG(Picture picture);
  Image loadPictureFromByteArray(Picture picture);
  Image loadPictureFromBooleanArray(Picture picture);
  Image loadPictureFromTxtFile(Picture picture);
}
