package vr.kostic017.wordassociation.provider;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitProvider {

    private static Retrofit retrofit;

    public static void init(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
