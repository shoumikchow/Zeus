package shoumik.com.zeus;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

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
        lat=getIntent().getDoubleExtra("locationLat", 0);
        lon=getIntent().getDoubleExtra("locationLon",0);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 12));
        getAllParseData();


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {


                l1 = latLng.latitude;
                l2 = latLng.longitude;

                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(l1, l2, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0)
                    Log.d(TAG,"locality is "+addresses.get(0).getSubLocality());


                Intent i = new Intent(MapsActivity.this, WeatherSelector.class);
                i.putExtra("latCoord", l1);
                i.putExtra("lonCoord", l2);
                i.putExtra("nameLocation",addresses.get(0).getSubLocality());
                startActivity(i);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }


    public void getAllParseData(){

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Nowcast");
        query.orderByDescending("latitude").findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {

                    Double parseLat;
                    Double parseLon;
                    String parseWeather;

                    for (ParseObject pObject : markers) {

                        parseLat = pObject.getDouble("latitude");
                        parseLon = pObject.getDouble("longitude");
                        parseWeather = pObject.getString("Weather");
                        Log.d(TAG, ""+pObject.getDouble("latitude"));

                        mMap.addMarker(new MarkerOptions().position(new LatLng(parseLat, parseLon))
                                .title(parseWeather));


                    }


                } else {
                    // handle Parse Exception here
                }
            }
        });

    }







}