package appjc.joercamu.aplicacionturismonativo;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class SiteListActivity extends AppCompatActivity {

    ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list);

        mlistView = findViewById(R.id.list_view_sitios);
        /*mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent siteShow = new Intent(SiteListActivity.this, SiteShowActivity.class);
                //siteShow.putExtra("param",content)
                startActivity(siteShow);
            }
        });*/
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
                    return;
                }
                List<Sitio> sitiosList = response.body().getInfo();
                SitioListAdapter mArrayAdapter = new SitioListAdapter(SiteListActivity.this, R.layout.list_sitio_item, sitiosList);
                mlistView.setAdapter(mArrayAdapter);

            }

            @Override
            public void onFailure(Call<Esena> call, Throwable t) {
                showAlert(t.getMessage());
            }
        });
    }
    public void goToSiteNewActivity(View view) {
        Intent intent = new Intent(this, SiteNewActivity.class);
        startActivity(intent);
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
