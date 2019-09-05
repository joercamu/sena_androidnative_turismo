package appjc.joercamu.aplicacionturismonativo.Sitios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SitiosService {

    @GET("sitios")
    Call< Esena > getSitios();

}