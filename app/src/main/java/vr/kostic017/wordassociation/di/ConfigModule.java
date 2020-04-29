package vr.kostic017.wordassociation.di;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vr.kostic017.wordassociation.consts.Config;

@Module
public class ConfigModule {
    private static final String TAG = ConfigModule.class.getSimpleName();

    @Provides
    @Singleton
    Properties provideProperties(AssetManager assetManager) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = assetManager.open(Config.CONFIG_FILE_NAME);
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "Could not read properties.", e);
            throw new RuntimeException(e);
        }
        return properties;
    }

}
