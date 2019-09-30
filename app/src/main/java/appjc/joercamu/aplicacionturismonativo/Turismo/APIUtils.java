package appjc.joercamu.aplicacionturismonativo.Turismo;

public class APIUtils {
    private APIUtils(){}

    public static final String API_URL = "https://especializacionsena.appspot.com/";

    public static TurismoService getTurismoService(){
        return RetrofitClient.getClient(API_URL).create(TurismoService.class);
    }
}
