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
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.biologiemarine.madagascarecotourism.Adapter.AireAdapter;
import com.biologiemarine.madagascarecotourism.Models.AirePOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AireListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private AireAdapter adapter;
    private List<AirePOJO> list;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.aires_list_layout );

        //Button to return to MainActivity
        final ImageButton button = findViewById( R.id.ReturnButtonAire );
        button.setOnClickListener( v -> finish() );

        //ProgressCircle for images
        progressBar = findViewById( R.id.ProgressCircleAire );

        //RecyclerView
        mRecyclerView = findViewById( R.id.recyclerViewAire );
        mRecyclerView.setHasFixedSize( true );


        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager( this ) );
        mRecyclerView.setItemAnimator( new DefaultItemAnimator() );

        list = new ArrayList<>(  );

        //Send query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getInstance().getReference("Aire");
        mRef.keepSynced( true );

        mRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot nDataSnapshot : dataSnapshot.getChildren()){

                        AirePOJO listData = nDataSnapshot.getValue(AirePOJO.class);
                        list.add( listData );
                    }
                    if (progressBar != null) {
                        progressBar.setVisibility( View.GONE);
                    }

                    //MAJ adapter + Click on Item
                    adapter = new AireAdapter( list);
                    adapter.notifyDataSetChanged();

                    adapter.setOnRecyclerClickListener( (view, position) -> {

                        Intent intent = new Intent( getApplicationContext(), AreaActivity.class );
                        intent.putExtra( "selected_area",list.get(position) );
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
