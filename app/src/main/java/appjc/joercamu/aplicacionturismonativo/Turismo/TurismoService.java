package appjc.joercamu.aplicacionturismonativo.Turismo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TurismoService {
    @GET("hoteles")
    Call<EsenaTurismo> getHotels();

    @GET("operadores")
    Call<EsenaTurismo> getOperatour();

    @GET("sitiosturismo")
    Call<EsenaTurismo> getSitios();

    @POST("turismo")
    //@FormUrlEncoded
    Call<Turismo> postTurismo(@Body Turismo turismo);
    @PUT("turismo/{id}")
    Call<Turismo> updateTurismo(@Path("id") int id, @Body Turismo turismo);

    @DELETE("turismo/{id}")
    Call<Turismo> deleteTurismo(@Path("id") int id);
}
