package shoumik.com.zeus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String TAG="ShoumiksTAG";
    double l1;
    double l2;
    double lat;
    double lon;
    String wthr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getAllData();


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {


                l1=latLng.latitude;
                l2=latLng.longitude;

                Intent i=new Intent(MapsActivity.this,WeatherSelector.class);
                i.putExtra("latCoord",l1);
                i.putExtra("lonCoord",l2);
                startActivity(i);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

    }


    public void getAllData(){

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Nowcast");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {


                    for (ParseObject pObject : markers){
                        Log.d(TAG,"latitude is "+pObject.getDouble("latitude"));
                        Log.d(TAG, "longitude is "+pObject.getDouble("longitude"));
                        Log.d(TAG, "weather is "+pObject.getString("Weather"));

                        mMap.addMarker(new MarkerOptions().position(new LatLng(pObject.getDouble("latitude"),
                                pObject.getDouble("longitude"))).title(pObject.getString("Weather")));
                    }


                } else {
                    // handle Parse Exception here
                }
            }
        });

    }



}