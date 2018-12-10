package allen.ma.service;

import allen.ma.model.Picture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public interface PictureService {

  Picture getCurrentPicture();

  void setCurPicture(Picture picture);

  boolean exportPicture(Picture picture, Image pictureImage);

  boolean serializeToJson(Picture picture);
  Picture deserializeFromJson(File file);

  Image loadPicture(Picture picture);
}
