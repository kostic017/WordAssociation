package vr.kostic017.wordassociation.webservice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;
import vr.kostic017.wordassociation.data.Language;

public class LanguageConverterFactory extends Converter.Factory {

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type.equals(Language.class)) {
            return (Converter<Language, String>) Language::getCode;
        }
        return null;
    }

    public static LanguageConverterFactory create() {
        return new LanguageConverterFactory();
    }

}
