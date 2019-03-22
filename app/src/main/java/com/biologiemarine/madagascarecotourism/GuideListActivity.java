package com.biologiemarine.madagascarecotourism;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.biologiemarine.madagascarecotourism.Adapter.MyAdapter;
import com.biologiemarine.madagascarecotourism.Models.ContactPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GuideListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private MyAdapter adapter;
    private List<ContactPOJO> list;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_guide );

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Button to return to MainActivity
        final ImageButton button = findViewById( R.id.ReturnButton );
        button.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                startActivity( intent );
            }
        } );

        //ProgressCircle for images
        progressBar = findViewById( R.id.ProgressCircle );

        //RecyclerView
        mRecyclerView = findViewById( R.id.recyclerView );
        mRecyclerView.setHasFixedSize( true );


        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager( this ) );
        mRecyclerView.setItemAnimator( new DefaultItemAnimator() );

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
                    if (progressBar != null) {
                        progressBar.setVisibility( View.GONE);
                    }

                    //MAJ adapter + Click on Item
                    adapter = new MyAdapter( list);
                    adapter.notifyDataSetChanged();

                    adapter.setOnRecyclerClickListener( new OnRecyclerClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            Intent intent = new Intent( getApplicationContext(),GuideDetailsActivity.class );
                            intent.putExtra( "selected_guide",list.get(position) );
                            startActivity(intent);


                        }
                    } );

                    mRecyclerView.setAdapter( adapter );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("GuideActivity","I'm here and there is the ");
        //Inflate menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.guide_search_menu,menu );
        MenuItem item = menu.findItem( R.id.action_search );
        SearchView searchView = (SearchView) MenuItemCompat.getActionView( item );
        searchView.setQueryHint("Type something...");
        SearchManager searchManager = (SearchManager) getSystemService( SEARCH_SERVICE );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Filter as you type
                firebaseSearch(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //handle other bar item clicks here
        if(id==R.id.action_settings){

            return true;
        }
        return super.onOptionsItemSelected( item );
    }


    //Search Data
    private void firebaseSearch(String searchText){

        //TODO : implémenter/améliorer la recherche
        Query firebaseSearchQuery = mRef.orderByChild( "Nom" ).startAt( searchText ).endAt( searchText + "\uf8ff" );
        firebaseSearchQuery.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot nDataSnapshot : dataSnapshot.getChildren()){

                        ContactPOJO listData = nDataSnapshot.getValue(ContactPOJO.class);
                        list.add( listData );
                    }
                    //MAJ adapter + Click on Item
                    adapter = new MyAdapter( list);
                    adapter.setOnRecyclerClickListener( new OnRecyclerClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                           Intent intent = new Intent( getApplicationContext(),GuideDetailsActivity.class );
                           intent.putExtra( "selected_guide",list.get(position) );
                           startActivity(intent);


                        }
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