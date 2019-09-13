package appjc.joercamu.aplicacionturismonativo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appjc.joercamu.aplicacionturismonativo.Sitios.Esena;
import appjc.joercamu.aplicacionturismonativo.Sitios.Sitio;
import appjc.joercamu.aplicacionturismonativo.Sitios.SitioListAdapter;
import appjc.joercamu.aplicacionturismonativo.Sitios.SitiosService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.ArrayAdapter.*;


public class MainActivity extends AppCompatActivity {

    ListView mlistView;
    TextView textOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mlistView = findViewById(R.id.list_view_sitios);
        // textOut = findViewById(R.id.textOut);
        // mlistView.setAdapter(mArrayAdapter);
        getSitios();
    }
    private void getSitios() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SitiosService sitiosService = retrofit.create(SitiosService.class);

        Call<Esena> call = sitiosService.getSitios();

        call.enqueue(new Callback<Esena>() {
            @Override
            public void onResponse(Call<Esena> call, Response<Esena> response) {
                if(!response.isSuccessful()){
                    //textOut.setText("status" + response.code());
                    return;
                }
                List<Sitio> sitiosList = response.body().getInfo();
                SitioListAdapter mArrayAdapter = new SitioListAdapter(MainActivity.this, R.layout.list_sitio_item, sitiosList);
                mlistView.setAdapter(mArrayAdapter);
                /*for (Sitio sitio: sitiosList){
                    String content = "";
                    content += "Nombre"+sitio.getName() + "\n";
                    textOut.append(content);
                }*/

            }

            @Override
            public void onFailure(Call<Esena> call, Throwable t) {
                textOut.setText(t.getMessage());
            }
        });
    }
    public void goToSiteNewActivity(View view) {
        Intent intent = new Intent(this, SiteNewActivity.class);
        startActivity(intent);
    }
}
