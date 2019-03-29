package com.biologiemarine.madagascarecotourism;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.biologiemarine.madagascarecotourism.Models.AirePOJO;
import com.biologiemarine.madagascarecotourism.Models.ContactPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AreaActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private List<ContactPOJO> list;

    private ProgressBar progressBar;

    TextView Nom,Statut,Province,Description,Categorie,Lien1,Lien2,Lien3,Recherche1,Recherche2,Recherche3,Region,District,Type;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_area );

        //Send query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getInstance().getReference( "Aire" );
        mRef.keepSynced( true );

        list = new ArrayList <>();

        final ImageButton return_button = findViewById( R.id.ReturnButton3 );
        return_button.setOnClickListener( v -> {
            finish();
        } );

        //ProgressCircle for images
        progressBar = findViewById( R.id.ProgressCircleAireDescr );

        //TODO: TextViews pour les liens, recherches, statut, etc.
        Nom = findViewById( R.id.NomArea );
        Description = findViewById( R.id.DescriptionArea );
        Statut = findViewById( R.id.StatutArea );
        Province = findViewById( R.id.RegionProvArea );
        Lien1 = findViewById( R.id.Lien1 );
        Lien2 = findViewById( R.id.Lien2 );
        Lien3 = findViewById( R.id.Lien3 );
        Recherche1 = findViewById( R.id.Rech1 );
        Recherche2 = findViewById( R.id.Rech2 );
        Recherche3 = findViewById( R.id.Rech3 );
        District = findViewById( R.id.DistrictArea );
        mImage = findViewById( R.id.imageArea );


        Lien1.setText( "Lien n°1" );
        Lien2.setText( "Lien n°2" );
        Lien3.setText( "Lien n°3" );
        Recherche1.setText( "Recherche n°1" );
        Recherche2.setText( "Recherche n°2"  );
        Recherche3.setText( "Recherche n°3" );

        //Retrieve data from list activity
        if (getIntent().hasExtra( "selected_area" )) {

            AirePOJO airePOJO = getIntent().getParcelableExtra( "selected_area" );
            Nom.setText( airePOJO.getSHORT_NAME() );
            Description.setText( airePOJO.getDescription() );
            Statut.setText(airePOJO.getSTATUT_JUR() );
            Province.setText( airePOJO.getREGION()+", "+ airePOJO.getPROVINCE() );
            District.setText( airePOJO.getDISTRICT() );

            Picasso.get().load( airePOJO.getImage() ).fit().centerCrop().into( mImage );


            Lien1.setOnClickListener( v -> {
                String url = airePOJO.getLien1();
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }


            } );

            Lien2.setOnClickListener( v -> {
                String url = airePOJO.getLien2();;
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Lien3.setOnClickListener( v -> {
                String url = airePOJO.getLien3();
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Recherche1.setOnClickListener( v -> {
                String url = airePOJO.getRecherche1();
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }
            } );

            Recherche2.setOnClickListener( v -> {
                String url = airePOJO.getRecherche2();
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Recherche3.setOnClickListener( v -> {
                String url = airePOJO.getRecherche3();
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );


        }

        //Retrieve data from Map Activity
        if (getIntent().hasExtra( "SHORT_NAME" )) {


            String name = getIntent().getExtras().getString( "SHORT_NAME" );
            String descr = getIntent().getExtras().getString( "Description" );
            String statut = getIntent().getExtras().getString( "STATUT_JUR" );
            String prov = getIntent().getExtras().getString( "PROVINCE" );
            String reg = getIntent().getExtras().getString( "REGION" );
            String district = getIntent().getExtras().getString( "DISTRICT" );
            String lien1 = getIntent().getExtras().getString( "Lien1" );
            String lien2 = getIntent().getExtras().getString( "Lien2" );
            String lien3 = getIntent().getExtras().getString( "Lien3" );
            String rech1 = getIntent().getExtras().getString( "Recherche1" );
            String rech2 = getIntent().getExtras().getString( "Recherche2" );
            String rech3 = getIntent().getExtras().getString( "Recherche3" );


            Nom.setText( name );
            Description.setText( descr );
            Statut.setText(statut );
            Province.setText( reg+", "+prov );
            District.setText(district );


            //Compare with Database to get the image value

                Query queryMail = mRef.orderByChild( "SHORT_NAME" ).equalTo( name );
                queryMail.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if (progressBar != null) {
                                progressBar.setVisibility( View.GONE );
                            }

                            String fail = snapshot.child( "Image" ).getValue( String.class );
                            Picasso.get().load( fail ).fit().centerCrop().into( mImage );

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e( "GuideDetailsActivity", "Le mail ne fonctionne pas :|" );
                    }
                } );


            Lien1.setOnClickListener( v -> {
                String url = lien1;
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Lien2.setOnClickListener( v -> {
                String url = lien2;
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Lien3.setOnClickListener( v -> {
                String url = lien3;
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Recherche1.setOnClickListener( v -> {
                String url = rech1;
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Recherche2.setOnClickListener( v -> {
                String url = rech2;
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            Recherche3.setOnClickListener( v -> {
                String url = rech3;
                if(!url.equals("_")){
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    i.setData( Uri.parse( url ) );
                    startActivity( i );
                }
                else {
                    Toast.makeText( getApplicationContext(),"Pas de lien communiqué",Toast.LENGTH_SHORT ).show();
                }

            } );

            }




        }

}
