package appjc.joercamu.aplicacionturismonativo;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import appjc.joercamu.aplicacionturismonativo.Sitios.Sitio;
import appjc.joercamu.aplicacionturismonativo.Sitios.SitiosService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SiteNewActivity extends AppCompatActivity {
    EditText siteName, siteInfo, sitePhoto, siteRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_new);
        siteName = findViewById(R.id.editTextName);
        siteInfo = findViewById(R.id.editTextInfo);
        sitePhoto = findViewById(R.id.editTextPhoto);
        siteRate = findViewById(R.id.editTextRate);

        Toolbar toolbar = findViewById(R.id.toolbar_site);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Nuevo Sitio");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(),SiteListActivity.class);
                startActivity(intent);
            }
        });



    }
    public void siteSave(View view) {
        try {
            Sitio sitio = new Sitio();
            sitio.setName(siteName.getText().toString());
            sitio.setInfo(siteInfo.getText().toString());
            sitio.setPhoto(sitePhoto.getText().toString());
            sitio.setRate(Integer.parseInt(siteRate.getText().toString()));
            sitio.setCoords("{\"latitude\":3.42158,\"longitude\":-76.5205}");
            postSitio(sitio, this);

        } catch (Exception e){
            showAlert(e.getMessage());
        }
    }
    public void postSitio(Sitio sitio, final Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SitiosService sitiosService = retrofit.create(SitiosService.class);

        Call<Sitio> call = sitiosService.postSitios(sitio.getName(),sitio.getInfo(),sitio.getPhoto(),sitio.getRate(),sitio.getCoords());
        call.enqueue(new Callback<Sitio>() {
            @Override
            public void onResponse(Call<Sitio> call, Response<Sitio> response) {
                if(!response.isSuccessful()){
                    showAlert(response.message());
                    return;
                }
                showAlert("Registro insertado correctamente");
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Sitio> call, Throwable t) {
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
