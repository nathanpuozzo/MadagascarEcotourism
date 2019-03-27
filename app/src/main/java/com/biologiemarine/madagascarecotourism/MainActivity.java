package com.biologiemarine.madagascarecotourism;

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
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.biologiemarine.madagascarecotourism.Adapter.CustomHotelAdapter;
import com.biologiemarine.madagascarecotourism.Models.ContactPOJO;
import com.biologiemarine.madagascarecotourism.Models.HotelsPOJO;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
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
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.style.sources.VectorSource;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

// classes needed to add location layer

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,LocationEngineListener,PermissionsListener,
        OnLocationClickListener, MapboxMap.OnMapClickListener {


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;


    private ArrayList <ContactPOJO> mArrayList = new ArrayList <>();
    public ArrayList <HotelsPOJO> hotelArrayList = new ArrayList <>();
    public ArrayList <HotelsPOJO> areaArrayList = new ArrayList <>();
    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;

    private CustomHotelAdapter hotelAdapter;
    private CustomHotelAdapter areaAdapter;

    private MapView mapView;
    private MapboxMap map;
    private Switch hideShow;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationComponent locationComponent;
    private Location originLocation;
    private static final String geoJsonSourceId = "geoJsonData";
    private static final String geoJsonLayerId = "polygonFillLayer";
    private FillLayer layer;
    private GeoJsonSource source;
    private Point originPosition;
    private Point destinationPosition;
    private Marker destinationMarker;
    private LatLng originCoord;
    private LatLng destinationCoord;
    private DirectionsRoute currentRoute;
    private static final String TAG2 = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
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
    private String geojsonSourceId = "geojsonSourceId";
    private FeatureCollection featureCollection;
    private FeatureCollection areaCollection;
    private FeatureCollection guideCollection;
    private static final String AREA_LAYER_ID = "protected-area";
    private static final String GUIDE_LAYER_ID = "guides";

    private static final String PROPERTY_AREA_NAME = "Nom";

    //Spinner data to sort hotels
    Spinner spinner;
    String sort[] = {"Noms", "Score", "Prix"};
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
    private GlideRequest<Drawable> fullRequest;

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
        hideShow = (Switch) findViewById( R.id.hideShow );

        mapView.onCreate( savedInstanceState );
        mapView.getMapAsync( this );

        //bouttons floating action menu (FAM)
        materialDesignFAM = findViewById( R.id.material_design_android_floating_action_menu );
        floatingActionButton1 = findViewById( R.id.material_design_floating_action_menu_item1 );
        floatingActionButton2 = findViewById( R.id.material_design_floating_action_menu_item2 );
        floatingActionButton3 = findViewById( R.id.material_design_floating_action_menu_item3 );

        //FireBase
        mStorageRef = FirebaseStorage.getInstance().getReference();


        //Aire protégées
        floatingActionButton1.setOnClickListener( v -> {

            //TODO : Aires protégées

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
        map.addSource( vectorSource );

        Source guideSource = new GeoJsonSource( GUIDE_LAYER_ID,"mapbox://nathanpuozzo.2zrilie4" );
        map.addSource( guideSource );

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
    public void onMapClick(@NonNull LatLng point) {
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
                String guide_predi = feature.getStringProperty( "Région de prédilection1" );

                featureMarker=map.addMarker( new MarkerOptions().position( point ).title( guide_name ).snippet( "Zone de prédilection :" + guide_predi ) );
            }
        }

        map.selectMarker( featureMarker );
        map.setOnInfoWindowClickListener( featureMarker -> {
            if(!areaFeatures.isEmpty()) {
                (findViewById( R.id.includedActivityArea )).setVisibility( View.VISIBLE );
                (findViewById( R.id.material_design_android_floating_action_menu )).setVisibility( View.GONE );
                final ImageButton return_button = findViewById( R.id.ReturnButton3 );
                return_button.setOnClickListener( v -> {
                    // Code here executes on main thread after user presses button
                    (findViewById( R.id.includedActivityArea )).setVisibility( View.GONE );
                    (findViewById( R.id.material_design_android_floating_action_menu )).setVisibility( View.VISIBLE );
                } );

                List <Feature> areaFeatures1 = map.queryRenderedFeatures( rectF, AREA_LAYER_ID );
                for (Feature feature : areaFeatures1) {
                    String area_name = feature.getStringProperty( "SHORT_NAME" );
                    String area_descr = feature.getStringProperty( "Description" );
                    String type = feature.getStringProperty( "Type" );
                    String lien = feature.getStringProperty( "Lien1" );

                    ImageView ImageArea = findViewById( R.id.imageArea );

                    if(area_name.contains( "Isalo" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Isalo.jpg" );
                    }
                    else if(area_name.contains( "Ambohitantely" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Ambohitantely.jpg" );
                    }else if(area_name.contains( "Bemaraha" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Bemaraha.jpg" );
                    }else if(area_name.contains( "Beza Mahafaly" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Beza_Mahafaly.jpg" );
                    }else if(area_name.contains( "Cap Sainte Marie" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/cap saiten_marie.jpg" );
                    }else if(area_name.contains( "Lokobe" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Lokobe.jpg" );
                    }else if(area_name.contains( "Manongarivo" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Manongarivo.jpg" );
                    }else if(area_name.contains( "Mikea" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Mikea.jpg" );
                    }else if(area_name.contains( "Ranomafana" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Ranomafana.jpg" );
                    }else if(area_name.contains( "Tsimanampesotse" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/Tsimanampetsotse.jpg" );
                    }
                    else if(area_name.contains( "Zombitse" )){
                        imageStorage = mStorageRef.child( "AiresProtegees/zombitse_Vohibasia.jpg" );
                    }
                    else{
                        imageStorage = mStorageRef.child( "AiresProtegees/area1.jpg" );
                    }
                    Glide.with( getApplicationContext() ).load( imageStorage ).into( ImageArea );

                    TextView name_area = findViewById( R.id.NomArea );
                    name_area.setText( area_name );

                    TextView descr_area = findViewById( R.id.DescriptionArea );
                    descr_area.setText( area_descr );

                    TextView cat_area = findViewById( R.id.CategorieArea );
                    cat_area.setText( type );

                    final Button lien_area = findViewById( R.id.LienArea );
                    lien_area.setOnClickListener( v -> {
                        String url = lien;

                        Intent i = new Intent( Intent.ACTION_VIEW );
                        i.setData( Uri.parse( url ) );

                        startActivity( i );

                    } );

                }
            }

            else if(!guidesFeatures.isEmpty()){

                for(Feature feature : guidesFeatures){

                    String guide_name = feature.getStringProperty( "Nom" );
                    String guide_descr = feature.getStringProperty( "Description" );
                    String guide_agreg = feature.getStringProperty( "Agrégation par le ministère" );
                    String guide_assoc = feature.getStringProperty( "Association" );
                    String guide_diplome = feature.getStringProperty( "Diplôme" );
                    String guide_email = feature.getStringProperty( "Email" );
                    String guide_langue1 = feature.getStringProperty( "Langue1" );
                    String guide_langue2 = feature.getStringProperty( "Langue2" );
                    String guide_tel = feature.getStringProperty( "Téléphone" );
                    String guide_predi1 = feature.getStringProperty( "Zone de prédilection1" );
                    String guide_predi2 = feature.getStringProperty( "Zone de prédilection2" );
                    String guide_spec = feature.getStringProperty( "Spécialités" );

                    Intent intent = new Intent( getApplicationContext(),GuideDetailsActivity.class );
                    intent.putExtra( "guide_name",guide_name );
                    intent.putExtra( "guide_descr",guide_descr );
                    intent.putExtra( "guide_agreg",guide_agreg );
                    intent.putExtra( "guide_assoc",guide_assoc );
                    intent.putExtra( "guide_diplome",guide_diplome );
                    intent.putExtra( "guide_email",guide_email );
                    intent.putExtra( "guide_langue1",guide_langue1 );
                    intent.putExtra( "guide_langue2",guide_langue2);
                    intent.putExtra( "guide_tel",guide_tel );
                    intent.putExtra( "guide_predil1",guide_predi1 );
                    intent.putExtra( "guide_predil2",guide_predi2 );
                    intent.putExtra( "guide_spec",guide_spec );
                    startActivity( intent );

                }
            }
            return true;
        } );

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
        hitRectText.offset( 0, -view.getMeasuredHeight() );

        // hit test if clicked point is in textview hitbox
        if (hitRectText.contains( (int) screenPoint.x, (int) screenPoint.y )) {
            // user clicked on text
            String name = feature.getStringProperty( "hotel" );
            String nuit = feature.getStringProperty( "prix" );
            String ipe = feature.getStringProperty( "ipe" );

            String energie = feature.getStringProperty( "energie verte (sur 2)" );
            String dechets = feature.getStringProperty( "traitement de dechets  (sur 2)" );
            String communaute = feature.getStringProperty( "communaute  (sur 2)" );
            String salaire = feature.getStringProperty( "salaire_equitable(sur 2)" );

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
        map.addSource( source );
    }

    /**
     * Adds the marker image to the map for use as a SymbolLayer icon
     */
    private void setUpImage() {
        Bitmap icon = BitmapFactory.decodeResource( this.getResources(), R.drawable.hotel_icon );
        map.addImage( MARKER_IMAGE_ID, icon );
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
        map.addLayer( new SymbolLayer( MARKER_LAYER_ID, geojsonSourceId ).withProperties( iconImage( MARKER_IMAGE_ID ), iconAllowOverlap( false )));
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
            map.addLayer (clusterLayer);

        }
        //Add the count labels
        count = new SymbolLayer( "count", geojsonSourceId );
        count.setProperties( textField(Expression.toString( get( "point_count" ) )),
                textSize( 12f ),
                textColor( Color.WHITE ),
                textIgnorePlacement( true ),
                textAllowOverlap( false ));
        map.addLayer( count);

    }


    /**
     * Setup a layer with Android SDK call-outs
     * <p>
     * name of the feature is used as key for the iconImage
     * </p>
     */
    private void setUpInfoWindowLayer() {
        map.addLayer(new SymbolLayer(CALLOUT_LAYER_ID, geojsonSourceId)
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
            map.addImages(imageMap);
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
                return new String(buffer, "UTF-8");
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
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
            Toast.makeText(activity, R.string.mapbox_mapActionDescription, Toast.LENGTH_SHORT).show();

        }
    }



    /**
     * Utility class to generate Bitmaps for Symbol.
     * <p>
     * Bitmaps can be added to the map with {@link MapboxMap#addImage(String, Bitmap)}
     * </p>
     */
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
        locationComponent.activateLocationComponent( this,locationEngine);
        locationComponent.setLocationComponentEnabled(true);
        locationComponent.addOnLocationClickListener(this);
        locationComponent.setCameraMode(CameraMode.TRACKING_GPS);
        locationComponent.setRenderMode(RenderMode.NORMAL);
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority( LocationEnginePriority.HIGH_ACCURACY );
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if(lastLocation != null){
            originLocation = lastLocation;
            setCameraPosition( lastLocation );
        }else{
            locationEngine.addLocationEngineListener( this );
        }
    }

    private void setCameraPosition(Location lastLocation) {
        map.animateCamera( CameraUpdateFactory.newLatLngZoom( new LatLng( lastLocation.getLatitude(),lastLocation.getLongitude() ),21 ) );
    }



    private void toggleLayer() {


        Layer layer = map.getLayer(AREA_LAYER_ID);
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
            locationEngine.deactivate();
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



    @SuppressWarnings( "MissingPermission" )
    @Override
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }



}
