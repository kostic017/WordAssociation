package vr.kostic017.wordassociation.webservice;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vr.kostic017.wordassociation.data.Association;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;

public interface AssociationWebservice {
    @GET("association")
    Call<Map<Difficulty, List<Association>>> get(@Query("language") Language language, @Query("difficulty") Difficulty difficulty);
}
