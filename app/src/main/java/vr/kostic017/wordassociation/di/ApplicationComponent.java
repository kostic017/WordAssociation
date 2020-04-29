package vr.kostic017.wordassociation.di;

import javax.inject.Singleton;

import dagger.Component;
import vr.kostic017.wordassociation.activity.PlayActivity;

@Singleton
@Component(modules = {ConfigModule.class, WebModule.class})
public interface ApplicationComponent {
    void inject(PlayActivity playActivity);
}
