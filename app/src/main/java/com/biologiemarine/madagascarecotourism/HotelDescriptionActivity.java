package com.biologiemarine.madagascarecotourism;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.biologiemarine.madagascarecotourism.Models.HotelsPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

public class HotelDescriptionActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private static final String TAG = "description";
    String booking;
    String tripadvisor;
    String site;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private List<HotelsPOJO> list;
    private ProgressBar progressBar;

    public  List<Integer> groupImages;
    public  HashMap<Integer, List<Integer>> childImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_description);

        final ImageButton return_button = findViewById(R.id.ReturnButton2);
        return_button.setOnClickListener( v -> finish() );

        //ProgressCircle for images
        progressBar = findViewById( R.id.ProgressCircleHotelDetails );

        expandableListView = findViewById(R.id.expandableListView);
        ImageView image=findViewById( R.id.imageHotel );
        TextView prix_chambre=findViewById(R.id.prixNuit);
        TextView descr_hotel=findViewById( R.id.DescriptionHotel );
        TextView name_hotel=findViewById(R.id.NomHotel);

        prepareListData();

        //Send query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Hotel");
        mRef.keepSynced( true );
        list = new ArrayList <>(  );
        if(getIntent().hasExtra( "selected_hotel" )){
            HotelsPOJO hotelsPOJO = getIntent().getParcelableExtra( "selected_hotel" );
            name_hotel.setText( hotelsPOJO.getHotel() );
            descr_hotel.setText( hotelsPOJO.getDescription() );
            prix_chambre.setText( "Prix d'une nuit: "+hotelsPOJO.getPrix());
            Picasso.get().load( hotelsPOJO.getImage() ).fit().centerCrop().into( image );

            site = hotelsPOJO.getWeb();
            booking = hotelsPOJO.getBooking();
            tripadvisor = hotelsPOJO.getTrip_advisor();
            expandableListDetail = getDataFirebase();
        }

        if(getIntent().hasExtra( "name" )){
            String hotel = getIntent().getExtras().getString( "name" );
            String nuit = getIntent().getExtras().getString( "nuit" );
            String descr = getIntent().getExtras().getString( "descr" );

            name_hotel.setText(hotel);
            descr_hotel.setText( descr );
            prix_chambre.setText("Prix d'une nuit : "+nuit);

            site = getIntent().getExtras().getString( "site" );
            booking = getIntent().getExtras().getString( "booking" );
            tripadvisor = getIntent().getExtras().getString( "trip" );

            expandableListDetail = getData();
            //Compare with Database to get the image value

            Query queryMail = mRef.orderByChild( "hotel" ).equalTo( hotel );
            queryMail.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (progressBar != null) {
                            progressBar.setVisibility( View.GONE);
                        }

                        String fail = snapshot.child( "image" ).getValue(String.class);
                        Picasso.get().load( fail ).fit().centerCrop().into( image );

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Timber.tag("HotelDescrActivity").e("Le mail ne fonctionne pas :|");
                }
            } );

        }






        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle,groupImages, expandableListDetail,childImages);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener( groupPosition -> Toast.makeText(getApplicationContext(),
                expandableListTitle.get(groupPosition) + " List Expanded.",
                Toast.LENGTH_SHORT).show() );

        expandableListView.setOnGroupCollapseListener( groupPosition -> Toast.makeText(getApplicationContext(),
                expandableListTitle.get(groupPosition) + " List Collapsed.",
                Toast.LENGTH_SHORT).show() );

        expandableListView.setOnChildClickListener( (parent, v, groupPosition, childPosition, id) -> {

            if(groupPosition>=0) {
                if(childPosition>=0) {
                    if(groupPosition==1 && childPosition==0){
                        String url = booking;
                        if(url.equals("_")){
                            Timber.tag("TAG").d("Pas de lien disponible");
                        }
                        else {
                            Intent i = new Intent( Intent.ACTION_VIEW );
                            i.setData( Uri.parse( url ) );
                            startActivity( i );
                        }
                    }
                    else if(groupPosition==1 && childPosition==1){
                        String url = tripadvisor;
                        if(url.equals("_")){
                            Timber.tag("TAG").d("Pas de lien disponible");
                        }
                        else {
                            Intent i = new Intent( Intent.ACTION_VIEW );
                            i.setData( Uri.parse( url ) );
                            startActivity( i );
                        }
                    }
                    else if(groupPosition==2 && childPosition==3){
                        String url = site;
                        if(url.equals("_")){
                            Timber.tag("TAG").d("Pas de lien disponible");
                        }
                        else {
                            Intent i = new Intent( Intent.ACTION_VIEW );
                            i.setData( Uri.parse( url ) );
                            startActivity( i );
                        }
                    }

                }
            }

            return false;
        } );


         }

    private HashMap <String, List <String>> getDataFirebase() {

            String energie,dechets,salaire,communaute,tel,mail,site,booking,tripadvisor,adresse,ipe;
            HotelsPOJO hotelsPOJO = getIntent().getParcelableExtra( "selected_hotel" );

            energie = hotelsPOJO.getEnergie_verte();

            dechets = hotelsPOJO.getDechets();
            communaute = hotelsPOJO.getCommunaute();
            tel = hotelsPOJO.getTel();
            mail = hotelsPOJO.getEmail();
            site = hotelsPOJO.getWeb();
            salaire = hotelsPOJO.getSalaire_equitable();
            booking = hotelsPOJO.getBooking();
            tripadvisor = hotelsPOJO.getTrip_advisor();
            adresse = hotelsPOJO.getAdresse();
            ipe = hotelsPOJO.getIpe();


            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

            List <String> cricket = new ArrayList <String>();
            cricket.add( "Energie verte : " + energie  );
            cricket.add( "Traitement de déchets : " + dechets );
            cricket.add( "Communauté : " + communaute );
            cricket.add( "Salaire équitable : " + salaire );
            // cricket.add("Indice d'effort sur salaire équitable : 1.3");

            List <String> football = new ArrayList <String>();
            football.add( "Booking" );
            football.add( "TripAdvisor");


            List <String> basketball = new ArrayList <String>();
            basketball.add( "Adresse :\n " + adresse );
            basketball.add( "Téléphone : \n " + tel );
            basketball.add( "E-mail : \n " + mail );
            basketball.add( "Site web :\n " + site );


            expandableListDetail.put( "IPE (Indice de Performance Ecotouristique) : " + ipe, cricket );
            expandableListDetail.put( "Qualité des services*", football );
            expandableListDetail.put( "Contact", basketball );

            return expandableListDetail;

    }

    @SuppressLint("UseSparseArrays")
    private void prepareListData(){
        groupImages = new ArrayList <Integer>(  );
        groupImages.add( R.drawable.ic_leaf );
        groupImages.add( R.drawable.ic_review );
        groupImages.add( R.drawable.ic_contacts_book );

        childImages = new HashMap <Integer, List<Integer>>(  );

        List<Integer> ipeImages = new ArrayList <Integer>(  );
        ipeImages.add( R.drawable.ic_fleur );
        ipeImages.add( R.drawable.ic_fleur);
        ipeImages.add( R.drawable.ic_fleur );
        ipeImages.add( R.drawable.ic_fleur );

        List<Integer> qualiteImages = new ArrayList <Integer>(  );
        qualiteImages.add( R.drawable.ic_fleur );
        qualiteImages.add( R.drawable.ic_fleur );

        List<Integer> contactImages = new ArrayList <Integer>(  );
        contactImages.add( R.drawable.ic_fleur );
        contactImages.add( R.drawable.ic_fleur );
        contactImages.add( R.drawable.ic_fleur );
        contactImages.add( R.drawable.ic_fleur );

        childImages.put(0,ipeImages);
        childImages.put(1,qualiteImages);
        childImages.put(2,contactImages);

    }

    public HashMap<String, List<String>> getData() {

        String energie,dechets,salaire,communaute,tel,mail,site,booking,tripadvisor,adresse,ipe;

            energie = getIntent().getExtras().getString( "energie" );
            dechets = getIntent().getExtras().getString( "dechets" );
            salaire = getIntent().getExtras().getString( "salaire" );
            communaute = getIntent().getExtras().getString( "communaute" );
            tel = getIntent().getExtras().getString( "tel" );
            mail = getIntent().getExtras().getString( "mail" );
            site = getIntent().getExtras().getString( "site" );
            booking = getIntent().getExtras().getString( "booking" );
            tripadvisor = getIntent().getExtras().getString( "tripadvisor" );
            adresse = getIntent().getExtras().getString( "adresse" );
            ipe = getIntent().getExtras().getString( "ipe" );

            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

            List <String> cricket = new ArrayList <String>();
            cricket.add( "Energie verte : " + energie  );
            cricket.add( "Traitement de déchets : " + dechets );
            cricket.add( "Communauté : " + communaute );
            cricket.add( "Salaire équitable : " + salaire );
            // cricket.add("Indice d'effort sur salaire équitable : 1.3");

            List <String> football = new ArrayList <String>();
            football.add( "Booking" );
            football.add( "TripAdvisor");


            List <String> basketball = new ArrayList <String>();
            basketball.add( "Adresse :\n " + adresse );
            basketball.add( "Téléphone : \n " + tel );
            basketball.add( "E-mail : \n " + mail );
            basketball.add( "Site web :\n " + site );


            expandableListDetail.put( "IPE (Indice de Performance Ecotouristique) : " + ipe, cricket );
            expandableListDetail.put( "Qualité des services*", football );
            expandableListDetail.put( "Contact", basketball );




            return expandableListDetail;

        }
    }


