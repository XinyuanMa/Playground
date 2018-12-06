package allen.ma;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Config {

  public interface AppCfg {
    Integer initWindowWidth();
    Integer initWindowHeight();
    Double markerSize();
    String pictureMetaFileExt();
    String pictureFileExt();
  }

  static public class Cfg implements AppCfg {

    private AppCfg appCfg;
    private Cfg(AppCfg appCfg) {
      this.appCfg = appCfg;
    }

    @Override
    public Integer initWindowWidth() {
      return appCfg.initWindowWidth();
    }

    @Override
    public Integer initWindowHeight() {
      return appCfg.initWindowHeight();
    }

    @Override
    public Double markerSize() {
      return appCfg.markerSize();
    }

    @Override
    public String pictureMetaFileExt() {
      return appCfg.pictureMetaFileExt();
    }

    @Override
    public String pictureFileExt() {
      return appCfg.pictureFileExt();
    }
  }

  private static final String CFG_FILE_NAME = "config.yaml";
  private static final String CFG_PREFIX = "app";
  private static final Cfg cfg = initCfg();

  private static Cfg initCfg() {
    List<Path> configFiles = new LinkedList<>();
    configFiles.add(Paths.get(CFG_FILE_NAME));

    // go to http://www.cfg4j.org/ for more information
    ConfigFilesProvider configFilesProvider = () -> configFiles;
    ConfigurationSource source = new ClasspathConfigurationSource(configFilesProvider);
    ConfigurationProvider provider = new ConfigurationProviderBuilder()
        .withConfigurationSource(source)
        .build();

    AppCfg appCfg = provider.bind(CFG_PREFIX, AppCfg.class);
    Cfg result = new Cfg(appCfg);

    return result;
  }

  public static Cfg get() {
    return cfg;
  }
}
