package com.biologiemarine.madagascarecotourism;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotelDescriptionActivity extends MainActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private static final String TAG = "description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean test = getIntent().getExtras().getBoolean("test");
        String hotel = getIntent().getExtras().getString( "name" );

        String nuit = getIntent().getExtras().getString( "nuit" );
        String descr = getIntent().getExtras().getString( "descr" );


        setContentView(R.layout.hotel_description);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        TextView name_hotel=findViewById(R.id.NomHotel);
        name_hotel.setText(hotel);
        Log.d( TAG,"le nom = "+hotel );
        TextView descr_hotel=findViewById( R.id.DescriptionHotel );
        descr_hotel.setText( descr );
        String hyppo = "Hotel Hyppocampo";
        String baku = "Bakuba";

        TextView prix_chambre=findViewById(R.id.prixNuit);
        prix_chambre.setText("Prix d'une nuit : "+nuit);

        ImageView image=findViewById( R.id.imageHotel );
        if(hotel.contains( "Hyppocampo" )){
            image.setImageResource( R.drawable.hotel01 );
        }
        else if(hotel.contains( "Bakuba" )){
            image.setImageResource( R.drawable.hotel02 );
        }
        else{
            image.setImageResource( R.drawable.hotel00 );
        }

        expandableListDetail = getData();

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        final ImageButton return_button = findViewById(R.id.ReturnButton2);
        return_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d(TAG,"position = "+childPosition);
                String site = getIntent().getExtras().getString( "site" );
                String booking = getIntent().getExtras().getString( "booking" );
                String tripadvisor = getIntent().getExtras().getString( "trip" );

                if(groupPosition>=0) {
                    if(childPosition>=0) {
                        if(groupPosition==1 && childPosition==0){
                            String url = booking;

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));

                            startActivity(i);
                        }
                        else if(groupPosition==1 && childPosition==1){
                            String url = tripadvisor;

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));

                            startActivity(i);
                        }
                        else if(groupPosition==2 && childPosition==3){
                            String url = site;

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));

                            startActivity(i);
                        }

                    }
                }
                    Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });


         }

    public HashMap<String, List<String>> getData() {
        String energie = getIntent().getExtras().getString( "energie" );
        String dechets = getIntent().getExtras().getString( "dechets" );
        String salaire = getIntent().getExtras().getString( "salaire" );
        String communaute = getIntent().getExtras().getString( "communaute" );

        String tel = getIntent().getExtras().getString( "tel" );
        String mail = getIntent().getExtras().getString( "mail" );
        String site = getIntent().getExtras().getString( "site" );
        String booking = getIntent().getExtras().getString( "booking" );
        String tripadvisor = getIntent().getExtras().getString( "tripadvisor" );
        String adresse = getIntent().getExtras().getString( "adresse" );
        String ipe = getIntent().getExtras().getString( "ipe" );

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

