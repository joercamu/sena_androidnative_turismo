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

public class OperatorListActivity extends AppCompatActivity {

    Button addOperatorBtn;
    Button getOperatorListBtn;
    ListView listView;

    List<Turismo> listOperators = new ArrayList<Turismo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);

        addOperatorBtn = findViewById(R.id.btnAddOperator);
        getOperatorListBtn = findViewById(R.id.btnGetOperatorList);
        listView = findViewById(R.id.listViewOperators);

        addOperatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OperatorListActivity.this, TurismoActivity.class);
                intent.putExtra("turismo_name", "");
                startActivity(intent);
            }
        });

        getOperatorListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOperatorLis();
            }
        });
    }

    private void getOperatorLis() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TurismoService turismoService = retrofit.create(TurismoService.class);

        Call<EsenaTurismo> call = turismoService.getOperatour();

        call.enqueue(new Callback<EsenaTurismo>() {
            @Override
            public void onResponse(Call<EsenaTurismo> call, Response<EsenaTurismo> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                listOperators = response.body().getInfo();
                TurismoListAdapter turismoListAdapter = new TurismoListAdapter(OperatorListActivity.this, R.layout.list_turismo_item, listOperators);
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
