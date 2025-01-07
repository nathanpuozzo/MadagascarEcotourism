package com.biologiemarine.madagascarecotourism;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.biologiemarine.madagascarecotourism.Adapter.HotelAdapter;
import com.biologiemarine.madagascarecotourism.Models.HotelsPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private HotelAdapter adapter;
    private List<HotelsPOJO> list;
    private ProgressBar progressBar;

    private Spinner mySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.hotel_layout );

        //TODO: Research bar
        /*
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        */

        //Button to return to MainActivity
        final ImageButton button = findViewById( R.id.ReturnButton2 );
        button.setOnClickListener( v -> finish() );

        //ProgressCircle for images
        progressBar = findViewById( R.id.ProgressCircleHotel );

        //RecyclerView
        mRecyclerView = findViewById( R.id.recyclerViewHotel );
        mRecyclerView.setHasFixedSize( true );


        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager( this ) );
        mRecyclerView.setItemAnimator( new DefaultItemAnimator() );

        list = new ArrayList<>(  );

        //Send query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getInstance().getReference("Hotel");
        mRef.keepSynced( true );

        mRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot nDataSnapshot : dataSnapshot.getChildren()){

                        HotelsPOJO listData = nDataSnapshot.getValue(HotelsPOJO.class);
                        list.add( listData );

                    }
                    if (progressBar != null) {
                        progressBar.setVisibility( View.GONE);
                    }

                    //MAJ adapter + Click on Item
                    adapter = new HotelAdapter( list);
                    adapter.notifyDataSetChanged();

                    adapter.setOnRecyclerClickListener( (view, position) -> {

                        Intent intent = new Intent( getApplicationContext(),HotelDescriptionActivity.class );
                        intent.putExtra( "selected_hotel",list.get(position) );
                        startActivity(intent);


                    } );

                    mRecyclerView.setAdapter( adapter );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        //Création du spinner
        mySpinner = findViewById( R.id. spinnerRegion);
        List<String> titleList = new ArrayList<String>();
        titleList.add( "Choisir la province" );
        titleList.add( "Antananarivo" );
        titleList.add( "Antsiranana" );
        titleList.add( "Fianarantsoa" );
        titleList.add( "Mahajanga" );
        titleList.add( "Toamasina" );
        titleList.add( "Toliara" );
        

        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(HotelListActivity.this, android.R.layout.simple_spinner_item, titleList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(arrayAdapter);
        mySpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                sortData();

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        } );
    }

    private void sortData() {
        //Variable pour l'élément selectionné
        String selected = mySpinner.getSelectedItem().toString();
        //Variable de recherche de la base de données
        Query query = mRef.orderByChild( "region" );
        switch (selected){

            case "Choisir la province" :
                query = mRef.orderByChild( "region" );
                break;
            case "Antananarivo" :
                query = mRef.orderByChild( "region" ).equalTo( "Antananarivo" );
                break;
            case "Antsiranana" :
                query = mRef.orderByChild( "region" ).equalTo( "Antsiranana" );
                break;
            case "Fianarantsoa" :
                query = mRef.orderByChild( "region" ).equalTo( "Fianarantsoa" );
                break;
            case "Mahajanga" :
                query = mRef.orderByChild( "region" ).equalTo( "Mahajanga" );
                break;
            case "Toamasina" :
                query = mRef.orderByChild( "region" ).equalTo( "Toamasina" );
                break;
            case "Toliara" :
                query = mRef.orderByChild( "region" ).equalTo( "Toliara" );
                break;
            default :


        }
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot nDataSnapshot : dataSnapshot.getChildren()){

                        HotelsPOJO listData = nDataSnapshot.getValue(HotelsPOJO.class);
                        list.add( listData );

                    }
                    if (progressBar != null) {
                        progressBar.setVisibility( View.GONE);
                    }

                    //MAJ adapter + Click on Item
                    adapter = new HotelAdapter( list);
                    adapter.notifyDataSetChanged();

                    adapter.setOnRecyclerClickListener( (view, position) -> {

                        Intent intent = new Intent( getApplicationContext(),HotelDescriptionActivity.class );
                        intent.putExtra( "selected_hotel",list.get(position) );
                        startActivity(intent);


                    } );

                    mRecyclerView.setAdapter( adapter );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

}
