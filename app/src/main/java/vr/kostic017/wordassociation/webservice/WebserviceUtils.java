package vr.kostic017.wordassociation.webservice;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

public class WebserviceUtils {

    private static final String TAG = WebserviceUtils.class.getSimpleName();

    public static <T> boolean isSuccessful(Response<T> response) {

        if (response.isSuccessful()) {
            return true;
        }

        try {
            if (response.errorBody() != null) {
                String message = response.errorBody().string();

                if (message.isEmpty()) {
                    Log.e(TAG, "Request failed with status code " + response.code());
                } else {
                    Log.e(TAG, "Request failed with status code " + response.code() + ": " + message);
                }

            } else {
                Log.e(TAG, "Request failed with status code " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Request failed with status code " + response.code() + ": <error>", e);
        }

        return false;

    }

}
