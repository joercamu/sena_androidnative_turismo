package appjc.joercamu.aplicacionturismonativo.Turismo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TurismoService {
    @GET("hoteles")
    Call<EsenaTurismo> getHotels();

    @GET("operadores")
    Call<EsenaTurismo> getOperatour();

    @POST("turismo")
    @FormUrlEncoded
    Call<Turismo> postTurismo(@Field("name") String name,
                              @Field("address") String address,
                              @Field("site") String site,
                              @Field("email") String email,
                              @Field("phone") int phone,
                              @Field("mobile") String mobile,
                              @Field("type_entity") String type_entity
    );
}
