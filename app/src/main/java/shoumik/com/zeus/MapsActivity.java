package shoumik.com.zeus;

import android.content.Context;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String TAG = "ShoumiksTAG";
    double l1;
    double l2;
    double lat;
    double lon;
    int lastTot;
    double lastLat;
    double lastLon;
    String lastWthr;
    String lastLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lat = getIntent().getDoubleExtra("locationLat", 0);
        lon = getIntent().getDoubleExtra("locationLon", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 13));
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
                    Log.d(TAG, "locality is " + addresses.get(0).getSubLocality());


                Intent i = new Intent(MapsActivity.this, WeatherSelector.class);
                i.putExtra("nameLocation", addresses.get(0).getSubLocality());
                startActivity(i);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }


    public void getAllParseData() {

        ParseQuery<ParseObject> query = new ParseQuery<>("Nowcast");
        query.orderByAscending("Location").findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {

                    int total = 0;


                    Map map = new HashMap<String, Integer>();
                    map.put("Sunny", total);
                    map.put("Stormy", total);
                    map.put("Cloudy", total);
                    map.put("Rainy", total);
                    map.put("Foggy", total);
                    map.put("Snowy", total);


                    String location_tag = markers.get(0).getString("Location");


                    for (ParseObject pObject : markers) {
                        if (!location_tag.equals(pObject.getString("Location"))) {

                            calcPercentage(total, map, location_tag);

                            //re initialize
                            total = 0;
                            map.put("Sunny", total);
                            map.put("Stormy", total);
                            map.put("Cloudy", total);
                            map.put("Rainy", total);
                            map.put("Foggy", total);
                            map.put("Snowy", total);
                            location_tag = pObject.getString("Location");

                        }


                        map.put(pObject.getString("Weather"), (int) map.get(pObject.getString("Weather")) + 1);
                        total++;

                        //to deal with the last entry if it is single entry
//                        lastTot = total;
//                        lastLoc = location_tag;
//                        lastLat = pObject.getDouble("latitude");
//                        lastLon = pObject.getDouble("longitude");
//                        lastWthr = pObject.getString("Weather");



                    }
                    //last single entry
//                    Log.d(TAG,"location is "+location_tag + " total is "+lastTot + " Weather is "+lastWthr+" latitude is "
//                    + lastLat+ " longitude is "+lastLon);
//                    mMap.addMarker(new MarkerOptions().position(new LatLng(lastLat, lastLon))
//                            .title("100% " + lastWthr + " at " + location_tag));
                    Log.d(TAG, "location is "+ location_tag+" total is "+ total +" and map is "+map);
              calcPercentage(total,map,location_tag);

                } else {
                    // handle Parse Exception here
                }
            }
        });

    }

    public void calcPercentage(int tot, Map map, String loc) {


        double sunny = ((Double.valueOf((int) map.get("Sunny"))) / Double.valueOf(tot)) * 100;
        double stormy = (Double.valueOf((int) map.get("Stormy"))) / Double.valueOf(tot) * 100;
        double cloudy = (Double.valueOf((int) map.get("Cloudy"))) / Double.valueOf(tot) * 100;
        double rainy = (Double.valueOf((int) map.get("Rainy"))) / Double.valueOf(tot) * 100;
        double foggy = (Double.valueOf((int) map.get("Foggy"))) / Double.valueOf(tot) * 100;
        double snowy = (Double.valueOf((int) map.get("Snowy"))) / Double.valueOf(tot) * 100;


        String output = "";
        if (sunny != 0.0)
            output = output + " " + sunny + "% sunny ";
        if (stormy != 0.0)
            output = output + " " + stormy + "% stormy ";
        if (cloudy != 0.0)
            output = output + " " + cloudy + "% cloudy ";
        if (rainy != 0.0)
            output = output + " " + rainy + "% rainy ";
        if (foggy != 0.0)
            output = output + " " + foggy + "% foggy ";
        if (snowy != 0.0)
            output = output + " " + snowy + "% snowy ";


        Double latMarker=getLocationFromAddress(getApplicationContext(),loc).latitude;
        Double lonMarker=getLocationFromAddress(getApplicationContext(),loc).longitude;

        mMap.addMarker(new MarkerOptions().position(new LatLng(latMarker, lonMarker))
                .title(output + "at " + loc));

//        mMap.

    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }


}