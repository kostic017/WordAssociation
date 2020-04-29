package vr.kostic017.wordassociation.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vr.kostic017.wordassociation.data.Association;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.webservice.AssociationWebservice;
import vr.kostic017.wordassociation.webservice.WebserviceUtils;

public class AssociationRepository {
    private static final String TAG = AssociationRepository.class.getSimpleName();

    private AssociationWebservice associationWebservice;

    @Inject
    public AssociationRepository(AssociationWebservice associationWebservice) {
        this.associationWebservice = associationWebservice;
    }

    public LiveData<Map<Difficulty, List<Association>>> get(Language language, Difficulty difficulty) {

        final MutableLiveData<Map<Difficulty, List<Association>>> associations = new MutableLiveData<>();

        Log.i(TAG, "Getting all associations for language " + language.getCode());
        associationWebservice.get(language, difficulty).enqueue(new Callback<Map<Difficulty, List<Association>>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Difficulty, List<Association>>> call, @NonNull Response<Map<Difficulty, List<Association>>> response) {
                if (WebserviceUtils.isSuccessful(response)) {
                    if (response.body() != null) {
                        associations.setValue(response.body());
                    } else {
                        Log.e(TAG, "Response body is missing");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<Difficulty, List<Association>>> call, @NonNull Throwable t) {
                Log.i(TAG, "Request failed", t);
            }
        });

        return associations;

    }

}
