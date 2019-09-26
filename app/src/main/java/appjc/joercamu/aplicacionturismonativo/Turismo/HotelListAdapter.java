package appjc.joercamu.aplicacionturismonativo.Turismo;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import appjc.joercamu.aplicacionturismonativo.R;

public class HotelListAdapter extends ArrayAdapter<Turismo> {
    private Context mContext;
    int mResource;
    public HotelListAdapter(Context context, int resource, List<Turismo> objects) {
        super(context,resource,objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return  super.getView(position, convertView, parent);

        Turismo hotel = new Turismo();
        hotel.setId(getItem(position).getId());
        hotel.setName(getItem(position).getName());
        hotel.setAddress(getItem(position).getAddress());
        hotel.setEmail(getItem(position).getEmail());
        hotel.setSite(getItem(position).getSite());
        hotel.setPhone(getItem(position).getPhone());
        hotel.setMobile(getItem(position).getMobile());

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView titleHotel = convertView.findViewById(R.id.titleHotel);

        titleHotel.setText(hotel.getName());

        return convertView;
    }
}
