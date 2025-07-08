package edu.cnm.deepdive.noted.service;

import androidx.core.content.FileProvider;
import edu.cnm.deepdive.noted.R;

public class ImageFileProvider extends FileProvider {

  public ImageFileProvider() {
    super(R.xml.provider_paths);
  }

}
