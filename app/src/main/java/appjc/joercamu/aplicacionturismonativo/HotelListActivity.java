package appjc.joercamu.aplicacionturismonativo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import appjc.joercamu.aplicacionturismonativo.Turismo.EsenaTurismo;
import appjc.joercamu.aplicacionturismonativo.Turismo.HotelListAdapter;
import appjc.joercamu.aplicacionturismonativo.Turismo.Turismo;
import appjc.joercamu.aplicacionturismonativo.Turismo.TurismoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelListActivity extends AppCompatActivity {

    ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        mlistView = findViewById(R.id.list_view_hotels);
        getHotels();
    }
    private void getHotels(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TurismoService turismoService = retrofit.create(TurismoService.class);

        Call<EsenaTurismo> call = turismoService.getHotels();

        call.enqueue(new Callback<EsenaTurismo>() {
            @Override
            public void onResponse(Call<EsenaTurismo> call, Response<EsenaTurismo> response) {
                if(!response.isSuccessful()){
                    return;
                }
                List<Turismo> hotelsList = response.body().getInfo();
                HotelListAdapter hotelsListAdapter = new HotelListAdapter(HotelListActivity.this, R.layout.list_turismo_item, hotelsList);
                mlistView.setAdapter(hotelsListAdapter);
            }

            @Override
            public void onFailure(Call<EsenaTurismo> call, Throwable t) {
                showAlert(t.getMessage());
            }
        });
    }
    public void showAlert(String text){
        new AlertDialog.Builder(this)
                .setTitle("Error al crear sitio")
                .setMessage(text)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
