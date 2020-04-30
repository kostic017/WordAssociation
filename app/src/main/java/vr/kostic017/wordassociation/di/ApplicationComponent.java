package vr.kostic017.wordassociation.di;

import javax.inject.Singleton;

import dagger.Component;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;

@Singleton
@Component(modules = {
        AssistedInjectModule.class,
        ConfigModule.class,
        WebModule.class
})
public interface ApplicationComponent {
    AssociationViewModel.Factory associationViewModelFactory();
}
