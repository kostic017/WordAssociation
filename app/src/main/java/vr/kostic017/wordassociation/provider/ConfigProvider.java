package vr.kostic017.wordassociation.provider;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {
    private static final String CONFIG_FILE_NAME = "config.properties";

    private Properties properties;

    public ConfigProvider(AssetManager assetManager) throws IOException {
        properties = new Properties();
        InputStream inputStream = assetManager.open(CONFIG_FILE_NAME);
        properties.load(inputStream);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
