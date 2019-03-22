package com.biologiemarine.madagascarecotourism;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.Models.ContactPOJO;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class GuideDetailsActivity extends AppCompatActivity {

    TextView Name, Agregation, Association, Description, Zones, Specialites, Langues, Grade, Tel, Mail;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.guide_description_layout );
        //TODO : make this activity and compare firebase Database with mapbox features --> https://www.youtube.com/watch?v=-ofYpirp4wA

        final ImageButton return_button = findViewById( R.id.ReturnButton8 );
        return_button.setOnClickListener( v -> {
            //TODO : return to liste
        } );

        Name = findViewById( R.id.NomDescrGuide );
        Agregation = findViewById( R.id.Agregation );
        Association = findViewById( R.id.Association );
        Description = findViewById( R.id.DescriptionGuide );
        Zones = findViewById( R.id.ZonesPred );
        Specialites = findViewById( R.id.Specialités );
        Langues = findViewById( R.id.LanguesDescrGuide );
        Grade = findViewById( R.id.gradeGuide );
        Tel = findViewById( R.id.TelGuide );
        Mail = findViewById( R.id.mailGuide);
        mImage = findViewById( R.id.imageDescrGuide );


        //Retrieve data from previous activity
        if(getIntent().hasExtra( "selected_guide" )){
            Log.d("GuideDetailsActivity","ok bro");
            ContactPOJO contactPOJO = getIntent().getParcelableExtra( "selected_guide" );
            Name.setText( contactPOJO.getNom() );
            Langues.setText( "Langues : "+contactPOJO.getLangues() );
            Zones.setText( "Zones de prédilection : "+contactPOJO.getZones());
            Agregation.setText( "Agrégation du ministère : "+contactPOJO.getAgregation() );
            Association.setText( "Association : "+contactPOJO.getAssociation() );
            Description.setText( contactPOJO.getDescription() );
            Specialites.setText( "Spécialités : "+contactPOJO.getSpecialites() );
            Grade.setText( "Grade : "+contactPOJO.getGrade() );
            Mail.setText( "Email : "+contactPOJO.getMail() );
            Tel.setText( "Téléphone : "+contactPOJO.getTel() );
            Picasso.get().load( contactPOJO.getImage() ).transform( new CircleTransform() ).into( mImage );
            Log.d("GuideDetailsActivity","OnCreate" + contactPOJO.toString());
        }

    }
    //Handle onBackPressed(go to previous activity

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
