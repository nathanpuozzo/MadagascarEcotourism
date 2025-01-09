package com.biologiemarine.madagascarecotourism;

import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.biologiemarine.madagascarecotourism.Adapter.CustomHotelAdapter;
import com.biologiemarine.madagascarecotourism.Models.ContactPOJO;
import com.biologiemarine.madagascarecotourism.Models.HotelsPOJO;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.BubbleLayout;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.style.sources.VectorSource;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

// classes needed to add location layer


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener,PermissionsListener,
        OnLocationClickListener, MapboxMap.OnMapClickListener {


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    private LocationListeningCallback callback = new LocationListeningCallback(this);
    private Button privacy;
    private EditText PPScrollV;
    private ImageButton PPClose;
    private final ArrayList <ContactPOJO> mArrayList = new ArrayList <>();
    public ArrayList <HotelsPOJO> hotelArrayList = new ArrayList <>();
    public ArrayList <HotelsPOJO> areaArrayList = new ArrayList <>();
    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;

    private CustomHotelAdapter hotelAdapter;
    private CustomHotelAdapter areaAdapter;


    private MapView mapView;
    private MapboxMap map;
    private SwitchCompat hideShow;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationComponent locationComponent;
    private Location originLocation;
    private static final String geoJsonSourceId = "geoJsonData";
    private static final String geoJsonLayerId = "polygonFillLayer";
    private GeoJsonSource source;
    private static final String TAG2 = "DirectionsActivity";
    private static final String TAG = "MainActivity";

    private Marker featureMarker;
    private Marker guideMarker;

    private HashMap <String, View> viewMap;

    public boolean test;


    private static final String MARKER_IMAGE_ID = "MARKER_IMAGE_ID";
    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";
    private static final String AREA_CALLOUT_LAYER_ID = "AREA_CALLOUT_LAYER_ID";
    private static final String PROPERTY_SELECTED = "selected";
    public static final String PROPERTY_TITLE = "hotel";
    public static final String PROPERTY_PRICE = "prix";
    public static final String PROPERTY_IPE = "ipe";
    private final String geojsonSourceId = "geojsonSourceId";
    private FeatureCollection featureCollection;
    private FeatureCollection areaCollection;
    private FeatureCollection guideCollection;
    private static final String AREA_LAYER_ID = "protected-area";
    private static final String GUIDE_LAYER_ID = "guides";

    private static final String PROPERTY_AREA_NAME = "Nom";

    //Spinner data to sort hotels
    Spinner spinner;
    String[] sort = {"Noms", "Score", "Prix"};
    ArrayAdapter <String> stringArrayAdapter;
    String record = "";


    //Cluster test1
    static final String KEY_UNIQUE_FEATURE = "key";
    static final String TOKEN_UNIQUE_FEATURE = "{" + KEY_UNIQUE_FEATURE + "}";
    static final String ID_SOURCE = "cluster_source";
    static final String ID_LAYER_UNCLUSTERED = "unclustered_layer";
    int[][] layers;
    private Expression pointCount;
    private SymbolLayer count;
    private CircleLayer clusterLayer;

    public MainActivity() {
    }

    //FireBase
    private StorageReference mStorageRef;
    private StorageReference imageStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //IntroActivity
        SharedPreferences sp = getSharedPreferences( "MyPrefs", Context.MODE_PRIVATE );
        if (!sp.getBoolean( "first", false )) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean( "first", true );
            editor.apply();
            Intent intent = new Intent( getApplicationContext(), IntroActivity.class );
            startActivity( intent );
        }


        //Ordre des déclarations!!!

        Mapbox.getInstance( this, "pk.eyJ1IjoibmF0aGFucHVvenpvIiwiYSI6ImNqbms4aGhnajEzeGQzcW8zamszNDdhM2wifQ.CN78uGunuSV16TT71FhXmA" );

        setContentView( R.layout.activity_main );
        mapView = findViewById( R.id.mapView );

        //button hide/show aires protégées
        hideShow = findViewById( R.id.hideShow );

        mapView.onCreate( savedInstanceState );
        mapView.getMapAsync( this );

        //bouttons floating action menu (FAM)
        materialDesignFAM = findViewById( R.id.material_design_android_floating_action_menu );
        floatingActionButton1 = findViewById( R.id.material_design_floating_action_menu_item1 );
        floatingActionButton2 = findViewById( R.id.material_design_floating_action_menu_item2 );
        floatingActionButton3 = findViewById( R.id.material_design_floating_action_menu_item3 );

        //FireBase
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Buttons Privacy
        privacy = findViewById( R.id.PrivacyPolicy );
        PPScrollV = findViewById( R.id.PPScroll );
        PPClose = findViewById( R.id.PPClose );

        PPScrollV.setVisibility( View.INVISIBLE );
        PPClose.setVisibility( View.INVISIBLE );

        privacy.setOnClickListener( v -> {
            PPScrollV.setVisibility( View.VISIBLE );
            PPClose.setVisibility( View.VISIBLE );

                PPClose.setOnClickListener( v1 -> {

                    PPScrollV.setVisibility( View.INVISIBLE );
                    PPClose.setVisibility( View.INVISIBLE );

                } );


        } );


        //Aire protégées
        floatingActionButton1.setOnClickListener( v -> {

            Intent intent = new Intent( getApplicationContext(), AireListActivity.class );
            startActivity( intent );

        } );

        //Hotels
        floatingActionButton2.setOnClickListener( v -> {

            Intent intent = new Intent( getApplicationContext(), HotelListActivity.class );
            startActivity( intent );

        } );

        //Guides
        floatingActionButton3.setOnClickListener( v -> {

            Intent intent = new Intent( getApplicationContext(), GuideListActivity.class );
            startActivity( intent );

            //Fin OnClick(View v)
        } );

    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;

        //Activer la localisation
       enableLocation();

        new LoadGeoJsonDataTask( this ).execute();

        VectorSource vectorSource = new VectorSource( AREA_LAYER_ID, "mapbox://nathanpuozzo.8j95651c" );
        map.getStyle().addSource(vectorSource);

        Source guideSource = new GeoJsonSource( GUIDE_LAYER_ID,"mapbox://nathanpuozzo.2zrilie4" );
        map.getStyle().addSource( guideSource );

        map.addOnMapClickListener( this );
        //aires protégées afficher/masquer
        hideShow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleLayer();
            }
        } );


    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        if(featureMarker!=null){
            map.removeMarker( featureMarker );
        }
        PointF screenPoint = map.getProjection().toScreenLocation( point );
        RectF rectF = new RectF( screenPoint.x - 10, screenPoint.y - 10, screenPoint.x + 10, screenPoint.y + 10 );
        List <Feature> features = map.queryRenderedFeatures( screenPoint, CALLOUT_LAYER_ID );
        List <Feature> areaFeatures = map.queryRenderedFeatures( rectF, AREA_LAYER_ID );
        List <Feature> guidesFeatures = map.queryRenderedFeatures( screenPoint, GUIDE_LAYER_ID );
        if (!features.isEmpty()) {
            // we received a click event on the callout layer
            Feature feature = features.get( 0 );
            PointF symbolScreenPoint = map.getProjection().toScreenLocation( convertToLatLng( feature ) );
            handleClickCallout( feature, screenPoint, symbolScreenPoint );
        } else {
            // we didn't find a click event on callout layer, try clicking maki layer
                handleClickIcon( map.getProjection().toScreenLocation( point ) );

        }
        if (!areaFeatures.isEmpty()) {
            for (Feature feature : areaFeatures) {
                String area_name = feature.getStringProperty( "SHORT_NAME" );


                featureMarker = map.addMarker( new MarkerOptions().position( point ).title( area_name ) );
            }
        }
        if(!guidesFeatures.isEmpty()){
            for(Feature feature : guidesFeatures){
                String guide_name = feature.getStringProperty( "Nom" );
                String guide_predi = feature.getStringProperty( "Zones" );

                featureMarker=map.addMarker( new MarkerOptions().position( point ).title( guide_name ).snippet( "Zone de prédilection :" + guide_predi ) );
            }
        }

        map.selectMarker( featureMarker );
        map.setOnInfoWindowClickListener( featureMarker -> {
            if(!areaFeatures.isEmpty()) {

                List <Feature> areaFeatures1 = map.queryRenderedFeatures( rectF, AREA_LAYER_ID );
                for (Feature feature : areaFeatures1) {

                    String area_name = feature.getStringProperty( "SHORT_NAME" );
                    String area_descr = feature.getStringProperty( "Description" );
                    String statut = feature.getStringProperty( "STATUT_JUR" );
                    String prov = feature.getStringProperty( "PROVINCE" );
                    String reg = feature.getStringProperty( "REGION" );
                    String district = feature.getStringProperty( "DISTRICT" );
                    String lien1 = feature.getStringProperty( "Lien1" );
                    String lien2 = feature.getStringProperty( "Lien2" );
                    String lien3 = feature.getStringProperty( "Lien3" );
                    String rech1 = feature.getStringProperty( "Recherche1" );
                    String rech2 = feature.getStringProperty( "Recherche2" );
                    String rech3 = feature.getStringProperty( "Recherche3" );

                    Intent intent = new Intent( getApplicationContext(),AreaActivity.class );
                    intent.putExtra( "SHORT_NAME",area_name );
                    intent.putExtra( "Description",area_descr );
                    intent.putExtra( "STATUT_JUR",statut );
                    intent.putExtra( "PROVINCE",prov );
                    intent.putExtra( "REGION",reg );
                    intent.putExtra( "DISTRICT",district );
                    intent.putExtra( "Lien1",lien1 );
                    intent.putExtra( "Lien2",lien2 );
                    intent.putExtra( "Lien3",lien3 );
                    intent.putExtra( "Recherche1",rech1 );
                    intent.putExtra( "Recherche2",rech2 );
                    intent.putExtra( "Recherche3",rech3 );
                    startActivity( intent );


                }
            }

            else if(!guidesFeatures.isEmpty()){

                for(Feature feature : guidesFeatures){

                    String guide_name = feature.getStringProperty( "Nom" );
                    String guide_descr = feature.getStringProperty( "Description" );
                    String guide_agreg = feature.getStringProperty( "Agregation" );
                    String guide_assoc = feature.getStringProperty( "Association" );
                    String guide_diplome = feature.getStringProperty( "Diplome" );
                    String guide_email = feature.getStringProperty( "Mail" );
                    String guide_langue1 = feature.getStringProperty( "Langues" );
                    String guide_tel = feature.getStringProperty( "Tel" );
                    String guide_predi1 = feature.getStringProperty( "Zones" );
                    String guide_spec = feature.getStringProperty( "Specialites" );

                    Intent intent = new Intent( getApplicationContext(),GuideDetailsActivity.class );
                    intent.putExtra( "guide_name",guide_name );
                    intent.putExtra( "guide_descr",guide_descr );
                    intent.putExtra( "guide_agreg",guide_agreg );
                    intent.putExtra( "guide_assoc",guide_assoc );
                    intent.putExtra( "guide_diplome",guide_diplome );
                    intent.putExtra( "guide_email",guide_email );
                    intent.putExtra( "guide_langue1",guide_langue1 );
                    intent.putExtra( "guide_tel",guide_tel );
                    intent.putExtra( "guide_predil1",guide_predi1 );
                    intent.putExtra( "guide_spec",guide_spec );
                    startActivity( intent );

                }
            }
            return true;
        } );

        return false;
    }


    private void handleClickCallout(Feature feature, PointF screenPoint, PointF symbolScreenPoint) {
        View view = viewMap.get( feature.getStringProperty( PROPERTY_TITLE ) );
        View textContainer = view.findViewById( R.id.text_container );
        // create hitbox for textView
        Rect hitRectText = new Rect();
        textContainer.getHitRect( hitRectText );

        // move hitbox to location of symbol
        hitRectText.offset( (int) symbolScreenPoint.x, (int) symbolScreenPoint.y );

        // offset vertically to match anchor behaviour
        hitRectText.offset(  -view.getMeasuredWidth()/2, -view.getMeasuredHeight() );

        // hit test if clicked point is in textview hitbox
        if (hitRectText.contains( (int) screenPoint.x, (int) screenPoint.y )) {
            Timber.tag(TAG).d("User click on text");
            // user clicked on text
            String name = feature.getStringProperty( "hotel" );
            String nuit = feature.getStringProperty( "prix" );
            String ipe = feature.getStringProperty( "ipe" );

            String energie = feature.getStringProperty( "energie verte (sur 2)" );
            String dechets = feature.getStringProperty( "traitement de dechets  (sur 2)" );
            String communaute = feature.getStringProperty( "communaute (sur 2)" );
            String salaire = feature.getStringProperty( "salaire_equitable (sur 2)" );

            String adresse = feature.getStringProperty( "adresse" );
            String tel = feature.getStringProperty( "tel" );
            String mail = feature.getStringProperty( "email" );
            String site = feature.getStringProperty( "site web" );

            String descr = feature.getStringProperty( "description" );
            String booking = feature.getStringProperty( "booking" );
            String trip = feature.getStringProperty( "trip_advisor" );
            Intent intent = new Intent( getApplicationContext(), HotelDescriptionActivity.class );
            intent.putExtra( "name", name );
            intent.putExtra( "nuit", nuit );
            intent.putExtra( "ipe", ipe );

            intent.putExtra( "energie", energie );
            intent.putExtra( "dechets", dechets );
            intent.putExtra( "communaute", communaute );
            intent.putExtra( "salaire", salaire );

            intent.putExtra( "adresse", adresse );
            intent.putExtra( "tel", tel );
            intent.putExtra( "mail", mail );
            intent.putExtra( "site", site );
            intent.putExtra( "descr", descr );
            intent.putExtra( "trip", trip );
            intent.putExtra( "booking", booking );
            startActivity( intent );
        } else {
            List <Feature> featureList = featureCollection.features();
            for (int i = 0; i < featureList.size(); i++) {

                if (featureList.get( i ).getStringProperty( PROPERTY_TITLE ).equals( feature.getStringProperty( PROPERTY_TITLE ) )) {

                    if (featureSelectStatus( i )) {
                        setFeatureSelectState( featureList.get( i ), false );
                    } else {
                        setSelected( i );

                    }
                }
            }
        }

    }


    private LatLng convertToLatLng(Feature feature) {
        Point symbolPoint = (Point) feature.geometry();
        return new LatLng( symbolPoint.latitude(), symbolPoint.longitude() );
    }

    /**
     * Sets up all of the sources and layers needed for this example
     *
     * @param collection the FeatureCollection to set equal to the globally-declared FeatureCollection
     */
    public void setUpData(final FeatureCollection collection) {
        if (map == null) {
            return;
        }
        featureCollection = collection;
        setupSource();
        setUpImage();
        setUpMarkerLayer();
       // setupClusterSource();
        setUpInfoWindowLayer();

    }


    /**
     * Adds the GeoJSON source to the map
     */
    private void setupSource() {
        source = new GeoJsonSource( geojsonSourceId, featureCollection );
        map.getStyle().addSource(source );
    }

    /**
     * Adds the marker image to the map for use as a SymbolLayer icon
     */
    private void setUpImage() {
        Bitmap icon = BitmapFactory.decodeResource( this.getResources(), R.drawable.ic_hotel_icon );
        map.getStyle().addImage(MARKER_IMAGE_ID, icon );

    }

    /**
     * Updates the display of data on the map after the FeatureCollection has been modified
     */
    private void refreshSource() {
        if (source != null && featureCollection != null) {
            source.setGeoJson( featureCollection );
        }
    }
    /**
     * Setup a layer with maki icons, eg. west coast city.
     */
    private void setUpMarkerLayer() {
        map.getStyle().addLayer( new SymbolLayer( MARKER_LAYER_ID, geojsonSourceId ).withProperties( iconImage( MARKER_IMAGE_ID ), iconAllowOverlap( false )));
    }

    /**
     * Setup a Cluster source.
     */
    private void setupClusterSource() {

        layers = new int[][]{new int[]{40, Color.RED}, new int[]{20, Color.BLUE}, new int[]{0, Color.GREEN}};

        for (int i = 0; i < layers.length; i++) {
            //Add cluster circles
            clusterLayer = new CircleLayer( "cluster-" + i, geojsonSourceId );
            clusterLayer.setProperties( circleColor( layers[i][1] ), circleRadius( 18f ) );
            pointCount=toNumber( get( "point_count" ) );
            // Add a filter to the cluster layer that hides the circles based on "point_count"
            clusterLayer.setFilter( i==0
                    ? all( has( "point_count" ),
                    gte( pointCount,literal(layers[i][0]) )
                    ) : all(has("point_count"),
                    gt(pointCount,literal(layers[i][0])),
                    lt(pointCount, literal(layers[i-1][0]))
                    )
            );
            map.getStyle().addLayer (clusterLayer);

        }
        //Add the count labels
        count = new SymbolLayer( "count", geojsonSourceId );
        count.setProperties( textField(Expression.toString( get( "point_count" ) )),
                textSize( 12f ),
                textColor( Color.WHITE ),
                textIgnorePlacement( true ),
                textAllowOverlap( false ));
        map.getStyle().addLayer( count);

    }


    /**
     * Setup a layer with Android SDK call-outs
     * <p>
     * name of the feature is used as key for the iconImage
     * </p>
     */
    private void setUpInfoWindowLayer() {
        map.getStyle().addLayer(new SymbolLayer(CALLOUT_LAYER_ID, geojsonSourceId)
                .withProperties(
                        /* show image with id title based on the value of the name feature property */
                        iconImage("{hotel}"),

                        /* set anchor of icon to bottom-left */
                        iconAnchor(ICON_ANCHOR_BOTTOM),

                        /* all info window and marker image to appear at the same time*/
                        iconAllowOverlap(false),

                        /* offset the info window to be above the marker */
                        iconOffset(new Float[] {-2f, -25f}),

                        iconSize( 1.0f )


                )
/* add a filter to show only when selected feature property is true */
                .withFilter(eq((get(PROPERTY_SELECTED)), literal(true)))

        );

    }



    /**
     * This method handles click events for SymbolLayer symbols.
     * <p>
     * When a SymbolLayer icon is clicked, we moved that feature to the selected state.
     * </p>
     *
     * @param screenPoint the point on screen clicked
     */
    private void handleClickIcon(PointF screenPoint) {
        List<Feature> features = map.queryRenderedFeatures(screenPoint, MARKER_LAYER_ID);

            if (!features.isEmpty()){
                String name = features.get( 0 ).getStringProperty( PROPERTY_TITLE );
                List <Feature> featureList = featureCollection.features();

                for (int i = 0; i < featureList.size(); i++) {

                    if (featureList.get( i ).getStringProperty( PROPERTY_TITLE ).equals( name )) {

                        if (featureSelectStatus( i )) {
                            setFeatureSelectState( featureList.get( i ), false );
                        } else {
                            setSelected( i );

                        }
                    }
                }

            }

    }

    /**
     * Set a feature selected state.
     *
     * @param index the index of selected feature
     */
    private void setSelected(int index) {
        Feature feature = featureCollection.features().get(index);
        setFeatureSelectState(feature, true);
        refreshSource();
    }

    /**
     * Selects the state of a feature
     *
     * @param feature the feature to be selected.
     */
    private void setFeatureSelectState(Feature feature, boolean selectedState) {
        feature.properties().addProperty(PROPERTY_SELECTED, selectedState);
        refreshSource();
    }

    /**
     * Checks whether a Feature's boolean "selected" property is true or false
     *
     * @param index the specific Feature's index position in the FeatureCollection's list of Features.
     * @return true if "selected" is true. False if the boolean property is false.
     */
    private boolean featureSelectStatus(int index) {
        if (featureCollection == null) {
            return false;
        }

        return featureCollection.features().get(index).getBooleanProperty(PROPERTY_SELECTED);
    }

    /**
     * Invoked when the bitmaps have been generated from a view.
     */
    public void setImageGenResults(HashMap<String, View> viewMap, HashMap<String, Bitmap> imageMap) {
        if (map != null) {
// calling addImages is faster as separate addImage calls for each bitmap.
            map.getStyle().addImages(imageMap);
        }
// need to store reference to views to be able to use them as hitboxes for click events.
        this.viewMap = viewMap;

    }


    /**
     * AsyncTask to load data from the assets folder.
     */
    private static class LoadGeoJsonDataTask extends AsyncTask<Void, Void, FeatureCollection> {
        private final WeakReference<MainActivity> activityRef;

        LoadGeoJsonDataTask(MainActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        protected FeatureCollection doInBackground(Void... params) {
            MainActivity activity = activityRef.get();

            if (activity == null) {
                return null;
            }

            String geoJson = loadGeoJsonFromAsset(activity, "hotels.geojson");


            return FeatureCollection.fromJson(geoJson);
        }

        @Override
        protected void onPostExecute(FeatureCollection featureCollection) {
            super.onPostExecute(featureCollection);
            MainActivity activity = activityRef.get();
            if (featureCollection == null || activity == null) {
                return;
            }

// This example runs on the premise that each GeoJSON Feature has a "selected" property,
// with a boolean value. If your data's Features don't have this boolean property,
// add it to the FeatureCollection 's features with the following code:
            for (Feature singleFeature : featureCollection.features()) {
                singleFeature.addBooleanProperty(PROPERTY_SELECTED, false);
            }

            activity.setUpData(featureCollection);
            new GenerateViewIconTask(activity).execute(featureCollection);
        }

        static String loadGeoJsonFromAsset(Context context, String filename) {
            try {
// Load GeoJSON file from local asset folder
                InputStream is = context.getAssets().open(filename);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return new String(buffer, StandardCharsets.UTF_8);
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            return filename;
        }

    }


    /**
     * AsyncTask to generate Bitmap from Views to be used as iconImage in a SymbolLayer.
     * <p>
     * Call be optionally be called to update the underlying data source after execution.
     * </p>
     * <p>
     * Generating Views on background thread since we are not going to be adding them to the view hierarchy.
     * </p>
     */
    private static class GenerateViewIconTask extends AsyncTask<FeatureCollection, Void, HashMap<String, Bitmap>> {

        private final HashMap<String, View> viewMap = new HashMap<>();
        private final WeakReference<MainActivity> activityRef;
        private final boolean refreshSource;

        GenerateViewIconTask(MainActivity activity, boolean refreshSource) {
            this.activityRef = new WeakReference<>(activity);
            this.refreshSource = refreshSource;
        }

        GenerateViewIconTask(MainActivity activity) {
            this(activity, false);
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected HashMap<String, Bitmap> doInBackground(FeatureCollection... params) {
            MainActivity activity = activityRef.get();
            if (activity != null) {
                HashMap<String, Bitmap> imagesMap = new HashMap<>();
                LayoutInflater inflater = LayoutInflater.from(activity);

                FeatureCollection featureCollection = params[0];

                for (Feature feature : featureCollection.features()) {

                    BubbleLayout bubbleLayout = (BubbleLayout)
                            inflater.inflate(R.layout.layout_callout, null);

                    String name = feature.getStringProperty(PROPERTY_TITLE);
                    TextView titleTextView = bubbleLayout.findViewById(R.id.info_window_title);
                    titleTextView.setText(name);

                    String style = feature.getStringProperty(PROPERTY_PRICE);
                    if(style==null){
                        TextView descriptionTextView = bubbleLayout.findViewById(R.id.info_window_description);
                        descriptionTextView.setText("Prix d'une nuit : 0");
                    }
                    TextView descriptionTextView = bubbleLayout.findViewById(R.id.info_window_description);
                    descriptionTextView.setText("Prix d'une nuit : "+style);

                    String ipe = feature.getStringProperty(PROPERTY_IPE);
                    if(ipe==null){
                        TextView ipeTextView = bubbleLayout.findViewById(R.id.info_window_ipe);
                        ipeTextView.setText("Score IPE : 0");
                    }
                    TextView ipeTextView = bubbleLayout.findViewById(R.id.info_window_ipe);
                    ipeTextView.setText("Score IPE : "+ipe);



                    int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    bubbleLayout.measure(measureSpec, measureSpec);

                    int measuredWidth = bubbleLayout.getMeasuredWidth();

                    bubbleLayout.setArrowPosition(measuredWidth / 2 - 5);

                    Bitmap bitmap = SymbolGenerator.generate(bubbleLayout);
                    imagesMap.put(name, bitmap);
                    viewMap.put(name, bubbleLayout);

                }

                return imagesMap;
            } else {
                return null;
            }
        }
        @Override
        protected void onPostExecute(HashMap<String, Bitmap> bitmapHashMap) {
            super.onPostExecute(bitmapHashMap);
            MainActivity activity = activityRef.get();
            if (activity != null && bitmapHashMap != null) {
                activity.setImageGenResults(viewMap, bitmapHashMap);
                if (refreshSource) {
                    activity.refreshSource();
                }
            }
            Toast.makeText(activity, com.mapbox.mapboxsdk.R.string.mapbox_mapActionDescription, Toast.LENGTH_SHORT).show();

        }
    }




    private static class SymbolGenerator {
    /**
     * Generate a Bitmap from an Android SDK View.
     *
     * @param view the View to be drawn to a Bitmap
     * @return the generated bitmap
     */
        static Bitmap generate(@NonNull View view) {
            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(measureSpec, measureSpec);

            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            view.layout(0, 0, measuredWidth, measuredHeight);
            Bitmap bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }
    }

    private void enableLocation() {
        if(PermissionsManager.areLocationPermissionsGranted( this )){
            initializeLocationEngine();
            initializeLocationLayer();
        }else{
            permissionsManager = new PermissionsManager( this );
            permissionsManager.requestLocationPermissions( this );
        }
    }
    @SuppressLint("WrongConstant")
    private void initializeLocationLayer() {
        locationComponent = map.getLocationComponent();
        locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this,map.getStyle()).build());
        locationComponent.setLocationComponentEnabled(true);
        locationComponent.addOnLocationClickListener(this);
        locationComponent.setCameraMode(CameraMode.TRACKING_GPS);
        locationComponent.setRenderMode(RenderMode.NORMAL);
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine() {
        long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
        long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);
        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_NO_POWER)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
                .build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    private void setCameraPosition(Location lastLocation) {
        map.animateCamera( CameraUpdateFactory.newLatLngZoom( new LatLng( lastLocation.getLatitude(),lastLocation.getLongitude() ),21 ) );
    }



    private void toggleLayer() {


        Layer layer = map.getStyle().getLayer(AREA_LAYER_ID);
        if (layer != null) {
            if (VISIBLE.equals(layer.getVisibility().getValue())) {
                layer.setProperties(visibility(NONE));
            } else {
                layer.setProperties(visibility(VISIBLE));
            }
    }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }

    @Override
    public void onExplanationNeeded(List <String> permissionsToExplain) {
        //Present toast or dialog of why user have to allow location
        Toast.makeText(this, "You have to enable location on your device to use the map navigation", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocation();
        }else{
            Toast.makeText(this, "user_location_permission_not_granted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @SuppressWarnings( "MissingPermission" )
    @Override
    protected void onStart(){
        super.onStart();
        if(locationEngine != null){
            locationEngine.requestLocationUpdates();
        }
        if(locationComponent != null){
            locationComponent.onStart();
        }
        mapView.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if(locationComponent != null){
            locationComponent.onStart();
        }
        mapView.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(locationEngine != null){
        //    locationEngine.deactivate();
        }
        if (map != null) {
            map.removeOnMapClickListener(this);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLocationChanged(Location location) {

        if (location != null){
            originLocation = location;
            setCameraPosition( location );
        }



    }
    @Override
    public void onLocationComponentClick() {

        Toast.makeText(this, "You are here", Toast.LENGTH_LONG).show();
    }





    private static class LocationListeningCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MainActivity> activityWeakReference;

        LocationListeningCallback(MainActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(LocationEngineResult result) {

            // The LocationEngineCallback interface's method which fires when the device's location has changed.


            Location lastLocation = result.getLastLocation();


        }

        @Override
        public void onFailure(@NonNull Exception exception) {

            // The LocationEngineCallback interface's method which fires when the device's location can not be captured



        }
    }

}
