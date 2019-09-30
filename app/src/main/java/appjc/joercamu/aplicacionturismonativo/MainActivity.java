package appjc.joercamu.aplicacionturismonativo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    ImageView imageViewSitios, imageViewOperatour, imageViewHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImages();
         }
    public void goToSiteNewActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TurismoListActivity.class);
        intent.putExtra("turismo_type_entity","site");
        startActivity(intent);
    }
    public void goToSHotelsListActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TurismoListActivity.class);
        intent.putExtra("turismo_type_entity","hotel");
        startActivity(intent);
    }
    public void goToOperatorListActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TurismoListActivity.class);
        intent.putExtra("turismo_type_entity","operator");
        startActivity(intent);
    }
    private void setImages(){
        imageViewSitios = findViewById(R.id.image_sitios);
        imageViewOperatour = findViewById(R.id.image_operatour);
        imageViewHotels = findViewById(R.id.image_hotels);
        Picasso.get().load("https://variedadesdecolombia.com/wp-content/uploads/2019/04/Principales-Sitios-Turisticos-de-Colombia.jpg").into(imageViewSitios);
        Picasso.get().load("https://www.reportur.com/wp-content/uploads/2014/10/RESUMEN-15-LIDERES.jpg").into(imageViewOperatour);
        Picasso.get().load("https://cr00.epimg.net/radio/imagenes/2016/04/02/tendencias/1459548796_976696_1459551543_noticia_normal.jpg").into(imageViewHotels);

    }
}
