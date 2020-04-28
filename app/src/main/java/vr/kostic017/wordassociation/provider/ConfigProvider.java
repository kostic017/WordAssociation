package vr.kostic017.wordassociation.provider;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {

    private static final String CONFIG_FILE_NAME = "config.properties";

    private static Properties properties;

    public static void init(AssetManager assetManager) throws IOException {
        if (properties == null) {
            properties = new Properties();
            InputStream inputStream = assetManager.open(CONFIG_FILE_NAME);
            properties.load(inputStream);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
