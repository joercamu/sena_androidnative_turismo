package appjc.joercamu.aplicacionturismonativo.Sitios;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import appjc.joercamu.aplicacionturismonativo.R;

public class SitioListAdapter extends ArrayAdapter<Sitio> {
    private Context mContext;
    int mResource;
    public SitioListAdapter(Context context, int resource, List<Sitio> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return  super.getView(position, convertView, parent);
        int siteId = getItem(position).getId();
        String siteName = getItem(position).getName();
        String siteInfo = getItem(position).getInfo();
        String sitePhoto = getItem(position).getPhoto();
        int siteRate = getItem(position).getRate();
        String siteCoords = getItem(position).getCoords();

        Sitio sitio = new Sitio();
        sitio.setId(siteId);
        sitio.setName(siteName);
        sitio.setInfo(siteInfo);
        sitio.setRate(siteRate);
        sitio.setPhoto(sitePhoto);
        sitio.setCoords(siteCoords);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView titleSitio = convertView.findViewById(R.id.titleSitio);
        TextView infoSitio = convertView.findViewById(R.id.infoSitio);
        ImageView imageSitio = convertView.findViewById(R.id.imageSitio);

        Picasso.get().load(sitio.getPhoto()).into(imageSitio);
        titleSitio.setText(sitio.getName());
        infoSitio.setText(sitio.getInfo());

        return convertView;
    }
}
