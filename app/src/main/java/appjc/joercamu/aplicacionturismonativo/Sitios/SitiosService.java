package appjc.joercamu.aplicacionturismonativo.Sitios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SitiosService {

    @GET("sitios")
    Call< Esena > getSitios();

    @POST("sitios")
    @FormUrlEncoded
    Call< Sitio > postSitios(@Field("name") String name,
                            @Field("info") String info,
                            @Field("photo") String photo,
                            @Field("rate") int rate,
                            @Field("coords") String coords
                            );

}