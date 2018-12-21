package car.andrej.lab3;

import car.andrej.lab3.wrapper.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleAPIService {
    @GET("json?")
    Call<Example> getInfo(@Query ("location") String location, @Query("key") String key, @Query("radius") String radius, @Query("types") String types);


}
