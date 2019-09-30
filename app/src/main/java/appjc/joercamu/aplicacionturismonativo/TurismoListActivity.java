package appjc.joercamu.aplicacionturismonativo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import appjc.joercamu.aplicacionturismonativo.Turismo.APIUtils;
import appjc.joercamu.aplicacionturismonativo.Turismo.EsenaTurismo;
import appjc.joercamu.aplicacionturismonativo.Turismo.Turismo;
import appjc.joercamu.aplicacionturismonativo.Turismo.TurismoListAdapter;
import appjc.joercamu.aplicacionturismonativo.Turismo.TurismoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurismoListActivity extends AppCompatActivity {

    Button addOperatorBtn;
    Button getOperatorListBtn;
    ListView listView;

    List<Turismo> listOperators = new ArrayList<Turismo>();

    String turismoEntity;

    TurismoService turismoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turismo_list);


        addOperatorBtn = findViewById(R.id.btnAddOperator);
        getOperatorListBtn = findViewById(R.id.btnGetOperatorList);
        listView = findViewById(R.id.listViewOperators);

        turismoService = APIUtils.getTurismoService();

        Bundle extras = getIntent().getExtras();
        try {
            turismoEntity = extras.getString("turismo_type_entity");
            if (turismoEntity.equals("hotel")) {
                addOperatorBtn.setText("Agregar Hotel");
            }else if(turismoEntity.equals("operator")){
                addOperatorBtn.setText("Agregar Operador");
            }else if(turismoEntity.equals("site")){
                addOperatorBtn.setText("Agregar Sitio");
            }
        } catch (Exception e){
            showAlert("No hay una entidad declarada");
        }

        setTitle(turismoEntity+ " App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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


        Call<EsenaTurismo> call = null;
        if (turismoEntityTxt.equals("hotel")) {
            call = turismoService.getHotels();
        } else if(turismoEntityTxt.equals("operator")){
            call = turismoService.getOperatour();
        } else if(turismoEntityTxt.equals("site")){
            call = turismoService.getSitios();
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
                .setTitle("Info Turismo")
                .setMessage(text)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
