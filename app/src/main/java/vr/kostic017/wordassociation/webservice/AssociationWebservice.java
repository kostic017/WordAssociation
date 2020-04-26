package vr.kostic017.wordassociation.webservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vr.kostic017.wordassociation.model.Language;
import vr.kostic017.wordassociation.webservice.response.AssociationResponse;

public interface AssociationWebservice {
    @GET("association.php")
    Call<AssociationResponse> get(@Query("language") Language language); // String language if this doesn't work
}
