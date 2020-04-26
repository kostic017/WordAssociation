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
import vr.kostic017.wordassociation.model.Association;
import vr.kostic017.wordassociation.model.Difficulty;
import vr.kostic017.wordassociation.model.Language;
import vr.kostic017.wordassociation.webservice.AssociationWebservice;
import vr.kostic017.wordassociation.webservice.WebserviceUtils;
import vr.kostic017.wordassociation.webservice.response.AssociationResponse;

public class AssociationRepository {

    private static final String TAG = AssociationRepository.class.getSimpleName();

    private AssociationWebservice associationWebservice;

    @Inject
    public AssociationRepository(AssociationWebservice associationWebservice) {
        this.associationWebservice = associationWebservice;
    }

    public LiveData<Map<Difficulty, List<Association>>> get(Language language) {

        final MutableLiveData<Map<Difficulty, List<Association>>> associations = new MutableLiveData<>();

        Log.i(TAG, "Getting all associations for language " + language.getCode());
        associationWebservice.get(language).enqueue(new Callback<AssociationResponse>() {
            @Override
            public void onResponse(@NonNull Call<AssociationResponse> call, @NonNull Response<AssociationResponse> response) {
                if (WebserviceUtils.isSuccessful(response)) {
                    if (response.body() != null) {
                        associations.setValue(response.body().getAssociations());
                    } else {
                        Log.e(TAG, "Response body is missing");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AssociationResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "Request failed", t);
            }
        });

        return associations;

    }

}
