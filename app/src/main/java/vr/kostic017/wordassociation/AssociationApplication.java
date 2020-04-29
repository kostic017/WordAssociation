package vr.kostic017.wordassociation;

import android.app.Application;

import vr.kostic017.wordassociation.di.ApplicationComponent;
import vr.kostic017.wordassociation.di.ConfigModule;
import vr.kostic017.wordassociation.di.DaggerApplicationComponent;

public class AssociationApplication extends Application {
    public static final String PACKAGE = "vr.kostic017.wordassociation";

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .configModule(new ConfigModule(getAssets()))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
