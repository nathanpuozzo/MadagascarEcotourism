package com.biologiemarine.madagascarecotourism;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.Models.ContactPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;


public class GuideDetailsActivity extends AppCompatActivity {

    TextView Name, Agregation, Association, Description, Zones, Specialites, Langues, Grade, Tel, Mail;
    ImageView mImage;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private List<ContactPOJO> list;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.guide_description_layout );


        //Send query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getInstance().getReference("Data");
        mRef.keepSynced( true );

        list = new ArrayList <>(  );

        final ImageButton return_button = findViewById( R.id.ReturnButton8 );
        return_button.setOnClickListener( v -> {
            finish();
        } );

        //ProgressCircle for images
        progressBar = findViewById( R.id.ProgressCircleDetails );

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


        //Retrieve data from list activity
        if(getIntent().hasExtra( "selected_guide" )){

            ContactPOJO contactPOJO = getIntent().getParcelableExtra( "selected_guide" );
            Name.setText( contactPOJO.getNom() );
            Langues.setText( "Langue(s) : "+contactPOJO.getLangues() );
            Zones.setText( "Zones de prédilection : "+contactPOJO.getZones());
            Agregation.setText( "Agrégation du ministère : "+contactPOJO.getAgregation() );
            Association.setText( "Association : "+contactPOJO.getAssociation() );
            Description.setText( contactPOJO.getDescription() );
            Specialites.setText( "Spécialité(s) : "+contactPOJO.getSpecialites() );
            Grade.setText( "Grade : "+contactPOJO.getGrade() );
            Mail.setText( "Email : "+contactPOJO.getMail() );
            Tel.setText( "Téléphone : "+contactPOJO.getTel() );
            Picasso.get().load( contactPOJO.getImage() ).transform( new CircleTransform() ).into( mImage );
            Log.d("GuideDetailsActivity","OnCreate" + contactPOJO.toString());
        }

        //Retrieve data from Map Activity
        if(getIntent().hasExtra( "guide_name" )){


            String name = getIntent().getExtras().getString( "guide_name" );
            String langue1 = getIntent().getExtras().getString( "guide_langue1" );
            String zone1 = getIntent().getExtras().getString( "guide_predil1" );
            String agreg = getIntent().getExtras().getString( "guide_agreg" );
            String assoc = getIntent().getExtras().getString( "guide_assoc" );
            String descr = getIntent().getExtras().getString( "guide_descr" );
            String spec = getIntent().getExtras().getString( "guide_spec" );
            String tel = getIntent().getExtras().getString( "guide_tel" );
            String diplome = getIntent().getExtras().getString( "guide_diplome" );
            String mail = getIntent().getExtras().getString( "guide_email" );

            Name.setText( name );
            Langues.setText( "Langue(s) : "+langue1 );
            Zones.setText( "Zones de prédilection : "+zone1);
            Agregation.setText( "Agrégation du ministère : "+agreg );
            Association.setText( "Association : "+assoc );
            Description.setText( descr );
            Specialites.setText( "Spécialité(s) : "+spec );
            Grade.setText( "Grade : "+diplome );
            Mail.setText( "Email : "+mail);
            Tel.setText( "Téléphone : "+tel );


            //Compare with Database to get the image value

            if(!mail.contentEquals( "_" )) {
                Query queryMail = mRef.orderByChild( "Mail" ).equalTo( mail );
                queryMail.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if (progressBar != null) {
                                progressBar.setVisibility( View.GONE);
                            }

                            String fail = snapshot.child( "Image" ).getValue(String.class);
                            Picasso.get().load( fail ).transform( new CircleTransform() ).into( mImage );

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e( "GuideDetailsActivity", "Le mail ne fonctionne pas :|" );
                    }
                } );
            }
            else {
                Query queryTel = mRef.orderByChild( "Tel" ).equalTo( tel );
                queryTel.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String fail = snapshot.child( "Image" ).getValue(String.class);
                            Picasso.get().load( fail ).transform( new CircleTransform() ).into( mImage );

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e( "GuideDetailsActivity", "Le tel ne fonctionne pas :|" );
                    }
                } );
            }


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
