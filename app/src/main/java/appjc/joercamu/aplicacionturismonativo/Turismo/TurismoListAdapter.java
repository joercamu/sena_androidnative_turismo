package appjc.joercamu.aplicacionturismonativo.Turismo;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import appjc.joercamu.aplicacionturismonativo.R;
import appjc.joercamu.aplicacionturismonativo.TurismoActivity;

public class TurismoListAdapter extends ArrayAdapter<Turismo> {
    private Context context;
    private List<Turismo> listTurismo;
    private  String turismo_type_entity;

    public TurismoListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Turismo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listTurismo = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_turismo_item, parent, false);

        TextView txtTurismoName = (TextView) rowView.findViewById(R.id.nameTurismoTextView);
        TextView txtTurismoAddress = (TextView) rowView.findViewById(R.id.addressTurismoTextView);
        TextView txtTurismoSite = (TextView) rowView.findViewById(R.id.siteTurismoTextView);

        ImageView picTurismo = rowView.findViewById(R.id.picTurismoImageView);

        txtTurismoName.setText(listTurismo.get(pos).getName() + " | " + listTurismo.get(pos).getType_entity());
        txtTurismoAddress.setText(listTurismo.get(pos).getAddress());
        txtTurismoSite.setText(String.format("Sitio: %s", listTurismo.get(pos).getSite()));

        if (listTurismo.get(pos).getType_entity().equals("hotel")) {
            picTurismo.setImageResource(R.drawable.ic_hotel_black_24dp);
            turismo_type_entity = "hotel";
        } else if (listTurismo.get(pos).getType_entity().equals("operador")) {
            picTurismo.setImageResource(R.drawable.ic_store_mall_directory_black_24dp);
            turismo_type_entity = "operator";
        } else if (listTurismo.get(pos).getType_entity().equals("sitio")) {
            picTurismo.setImageResource(R.drawable.ic_pin_drop_black_24dp);
            turismo_type_entity = "site";
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity User Form
                Intent intent = new Intent(context, TurismoActivity.class);
                intent.putExtra("turismo_id", String.valueOf(listTurismo.get(pos).getId()));
                intent.putExtra("turismo_name", listTurismo.get(pos).getName());
                intent.putExtra("turismo_address", listTurismo.get(pos).getAddress());

                intent.putExtra("turismo_site", listTurismo.get(pos).getSite());
                intent.putExtra("turismo_phone", listTurismo.get(pos).getPhone());
                intent.putExtra("turismo_mobile", listTurismo.get(pos).getMobile());
                intent.putExtra("turismo_email", listTurismo.get(pos).getEmail());
                intent.putExtra("turismo_entity", listTurismo.get(pos).getType_entity());

                intent.putExtra("turismo_type_entity", turismo_type_entity);


                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
