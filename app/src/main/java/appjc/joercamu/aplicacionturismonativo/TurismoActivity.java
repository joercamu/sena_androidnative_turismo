package appjc.joercamu.aplicacionturismonativo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import appjc.joercamu.aplicacionturismonativo.Turismo.Turismo;
import appjc.joercamu.aplicacionturismonativo.Turismo.TurismoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TurismoActivity extends AppCompatActivity {

    EditText editId;
    EditText editName;
    EditText editAdrres;
    EditText editEmail;
    EditText editSite;
    EditText editPhone;
    EditText editMobile;
    EditText editEntity;

    Button btnSave;
    Button btnRemove;

    TextView txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turismo);

        setTitle("Turismo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editId = findViewById(R.id.idTurismoEditText);
        editName = findViewById(R.id.nameTurismoEditText);
        editAdrres = findViewById(R.id.addressTurismoEditText);
        editEmail = findViewById(R.id.emailTurismoEditText);
        editSite = findViewById(R.id.siteTurismoEditText);
        editPhone = findViewById(R.id.phoneTurismoEditText);
        editMobile = findViewById(R.id.mobileTurismoEditText);
        editEntity = findViewById(R.id.entityTurismoEditText);

        btnSave = findViewById(R.id.saveButton);
        btnRemove = findViewById(R.id.deleteButton);

        txtId = findViewById(R.id.idTurismoText);

        Bundle extras = getIntent().getExtras();
        final String turismoId = extras.getString("turismo_id");
        String turismoName = extras.getString("turismo_name");
        String turismoAddress = extras.getString("turismo_address");
        String turismoEmail = extras.getString("turismo_email");
        String turismoSite = extras.getString("turismo_site");
        String turismoPhone = extras.getString("turismo_phone");
        String turismoMobile = extras.getString("turismo_mobile");
        String turismoEntity = extras.getString("turismo_entity");

        String turismoTypeEntity = extras.getString("turismo_type_entity");

        editId.setText(turismoId);
        editName.setText(turismoName);
        editAdrres.setText(turismoAddress);
        editEmail.setText(turismoEmail);
        editSite.setText(turismoSite);
        editMobile.setText(turismoMobile);
        editPhone.setText(turismoPhone);
        editEntity.setText(turismoEntity);

        editEntity.setFocusable(false);

        if (turismoTypeEntity != null) {
            if (turismoTypeEntity.equals("hotel")) {
                editEntity.setText("hotel");
            } else if (turismoTypeEntity.equals("operator")) {
                editEntity.setText("operador");
            } else if (turismoTypeEntity.equals("site")) {
                editEntity.setText("sitio");
            }
        }

        if (turismoId != null && turismoId.trim().length() > 0) {
            editId.setFocusable(false);
        } else {
            txtId.setVisibility(View.INVISIBLE);
            editId.setVisibility(View.INVISIBLE);
            btnRemove.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Turismo turismo = new Turismo();
                turismo.setName(editName.getText().toString());
                turismo.setAddress(editAdrres.getText().toString());
                turismo.setEmail(editEmail.getText().toString());
                turismo.setSite(editSite.getText().toString());
                turismo.setPhone(editPhone.getText().toString());
                turismo.setMobile(editMobile.getText().toString());
                turismo.setType_entity(editEntity.getText().toString());

                if (turismoId != null && turismoId.trim().length() > 0) {
                    //update user
                    updateTurismo(Integer.parseInt(turismoId), turismo);
                } else {
                    //add user
                    addTurismo(turismo);
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTurismo(Integer.parseInt(turismoId));

                Intent intent = new Intent(TurismoActivity.this, TurismoListActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addTurismo(Turismo turismo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TurismoService turismoService = retrofit.create(TurismoService.class);

        Call<Turismo> call = turismoService.postTurismo(turismo);
        call.enqueue(new Callback<Turismo>() {
            @Override
            public void onResponse(Call<Turismo> call, Response<Turismo> response) {
                if (!response.isSuccessful()) {
                    showAlert(response.message());
                    return;
                }
                showAlert("Registro insertado correctamente");
                Intent intent = new Intent(TurismoActivity.this, TurismoListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Turismo> call, Throwable t) {
                showAlert(t.getMessage());
            }
        });
    }

    public void updateTurismo(int id, Turismo turismo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TurismoService turismoService = retrofit.create(TurismoService.class);

        Call<Turismo> call = turismoService.updateTurismo(id, turismo);
        call.enqueue(new Callback<Turismo>() {
            @Override
            public void onResponse(Call<Turismo> call, Response<Turismo> response) {
                if (!response.isSuccessful()) {
                    showAlert(response.message());
                    return;
                }
                showAlert("Registro Actualizado correctamente");
                Intent intent = new Intent(TurismoActivity.this, TurismoListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Turismo> call, Throwable t) {
                showAlert(t.getMessage());
            }
        });
    }

    public void deleteTurismo(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://especializacionsena.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TurismoService turismoService = retrofit.create(TurismoService.class);

        Call<Turismo> call = turismoService.deleteTurismo(id);
        call.enqueue(new Callback<Turismo>() {
            @Override
            public void onResponse(Call<Turismo> call, Response<Turismo> response) {
                if (!response.isSuccessful()) {
                    showAlert(response.message());
                    return;
                }
                showAlert("Registro Actualizado correctamente");
                Intent intent = new Intent(TurismoActivity.this, TurismoListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Turismo> call, Throwable t) {
                showAlert(t.getMessage());
            }
        });
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

    public void showAlert(String text) {
        new AlertDialog.Builder(this)
                .setTitle("Informacion Turismo")
                .setMessage(text)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
