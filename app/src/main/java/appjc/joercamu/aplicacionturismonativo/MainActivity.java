package appjc.joercamu.aplicacionturismonativo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToSiteNewActivity(View view) {
        Intent intent = new Intent(this, SiteListActivity.class);
        startActivity(intent);
    }
}
