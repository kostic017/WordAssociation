package vr.kostic017.wordassociation.di;

import java.util.Properties;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import vr.kostic017.wordassociation.consts.Config;
import vr.kostic017.wordassociation.webservice.AssociationWebservice;
import vr.kostic017.wordassociation.webservice.LanguageConverterFactory;

@Module
public class WebModule {

    @Provides
    @Singleton
    static Retrofit provideRetrofit(Properties properties) {
        return new Retrofit.Builder()
                .baseUrl(properties.getProperty(Config.Keys.API_BASE_URL))
                .addConverterFactory(JacksonConverterFactory.create())
                .addConverterFactory(LanguageConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static AssociationWebservice provideAssociationWebservice(Retrofit retrofit) {
        return retrofit.create(AssociationWebservice.class);
    }

}
