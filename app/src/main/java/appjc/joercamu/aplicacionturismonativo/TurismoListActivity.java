package appjc.joercamu.aplicacionturismonativo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import appjc.joercamu.aplicacionturismonativo.Turismo.EsenaTurismo;
import appjc.joercamu.aplicacionturismonativo.Turismo.Turismo;
import appjc.joercamu.aplicacionturismonativo.Turismo.TurismoListAdapter;
import appjc.joercamu.aplicacionturismonativo.Turismo.TurismoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TurismoListActivity extends AppCompatActivity {

    Button addOperatorBtn;
    Button getOperatorListBtn;
    ListView listView;

    List<Turismo> listOperators = new ArrayList<Turismo>();

    String turismoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turismo_list);

        addOperatorBtn = findViewById(R.id.btnAddOperator);
        getOperatorListBtn = findViewById(R.id.btnGetOperatorList);
        listView = findViewById(R.id.listViewOperators);

        Bundle extras = getIntent().getExtras();
        turismoEntity = extras.getString("turismo_entity");

        if (turismoEntity.equals("hotel")) {
            addOperatorBtn.setText("Agregar Hotel");
        }else if(turismoEntity.equals("operator")){
            addOperatorBtn.setText("Agregar Operador");
        }else if(turismoEntity.equals("site")){
            addOperatorBtn.setText("Agregar Sitio");
        }

        addOperatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TurismoListActivity.this, TurismoActivity.class);
                intent.putExtra("turismo_type_entity", turismoEntity);
                startActivity(intent);
            }
        });

        getOperatorListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOperatorLis(turismoEntity);
            }
        });
    }

    private void getOperatorLis(String turismoEntityTxt) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TurismoService turismoService = retrofit.create(TurismoService.class);

        Call<EsenaTurismo> call;
        if (turismoEntityTxt.equals("hotel")) {
            call = turismoService.getHotels();
        } else if(turismoEntityTxt.equals("operator")){
            call = turismoService.getOperatour();
        } else if(turismoEntityTxt.equals("site")){
            call = turismoService.getSitios();
        }else {
            call = turismoService.getOperatour();
        }

        call.enqueue(new Callback<EsenaTurismo>() {
            @Override
            public void onResponse(Call<EsenaTurismo> call, Response<EsenaTurismo> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                listOperators = response.body().getInfo();
                TurismoListAdapter turismoListAdapter = new TurismoListAdapter(TurismoListActivity.this, R.layout.list_turismo_item, listOperators);
                listView.setAdapter(turismoListAdapter);
            }

            @Override
            public void onFailure(Call<EsenaTurismo> call, Throwable t) {
                showAlert(t.getMessage());
            }
        });
    }

    public void showAlert(String text) {
        new AlertDialog.Builder(this)
                .setTitle("Error al crear sitio")
                .setMessage(text)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
