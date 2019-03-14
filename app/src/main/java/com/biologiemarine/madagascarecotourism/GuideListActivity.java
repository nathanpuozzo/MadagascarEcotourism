package com.biologiemarine.madagascarecotourism;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.biologiemarine.madagascarecotourism.Adapter.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GuideListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private MyAdapter adapter;
    private List<ContactPOJO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_guide );

        //RecyclerView
        mRecyclerView = findViewById( R.id.recyclerView );
        mRecyclerView.setHasFixedSize( true );

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(  new LinearLayoutManager( this ) );
        list = new ArrayList <>(  );

        //Send query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getInstance().getReference("Data");
        mRef.keepSynced( true );

        mRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot nDataSnapshot : dataSnapshot.getChildren()){
                        ContactPOJO listData = nDataSnapshot.getValue(ContactPOJO.class);
                        list.add( listData );
                    }
                    adapter = new MyAdapter( list,getApplicationContext() );
                    mRecyclerView.setAdapter( adapter );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }


}
