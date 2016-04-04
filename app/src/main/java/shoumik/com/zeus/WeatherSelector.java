package shoumik.com.zeus;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

import java.util.List;

public class WeatherSelector extends AppCompatActivity {

    String TAG="ShoumiksTAG";
    ParseObject nowcast = new ParseObject("Nowcast");
    double lat;
    double lon;
    double generalLatitude;
    double generalLongitude;
    String locationName;
    ImageButton sunButton;
    ImageButton rainButton;
    ImageButton stormButton;
    ImageButton cloudButton;
    ImageButton fogButton;
    ImageButton snowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_selector);

        final Intent i =new Intent(this,MapsActivity.class);
        sunButton = (ImageButton) findViewById(R.id.sunButton);
        rainButton = (ImageButton) findViewById(R.id.rainButton);
        stormButton = (ImageButton) findViewById(R.id.stormButton);
        cloudButton = (ImageButton) findViewById(R.id.cloudButton);
        fogButton = (ImageButton) findViewById(R.id.fogButton);
        snowButton = (ImageButton) findViewById(R.id.snowButton);

        locationName=getIntent().getStringExtra("nameLocation");
        generalLatitude=getLocationFromAddress(getApplicationContext(),locationName).latitude;
        generalLongitude=getLocationFromAddress(getApplicationContext(),locationName).longitude;


        sunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nowcast.put("latitude",generalLatitude);
                nowcast.put("longitude",generalLongitude);
                nowcast.put("Weather", "Sunny");
                nowcast.put("Location",locationName);
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!", Toast.LENGTH_LONG).show();
            }
        });

        rainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",generalLatitude);
                nowcast.put("longitude",generalLongitude);
                nowcast.put("Weather","Rainy");
                nowcast.put("Location",locationName);
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        stormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",generalLatitude);
                nowcast.put("longitude",generalLongitude);
                nowcast.put("Weather","Stormy");
                nowcast.put("Location",locationName);
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        cloudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",generalLatitude);
                nowcast.put("longitude",generalLongitude);
                nowcast.put("Weather","Cloudy");
                nowcast.put("Location",locationName);
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        fogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",generalLatitude);
                nowcast.put("longitude",generalLongitude);
                nowcast.put("Weather","Foggy");
                nowcast.put("Location",locationName);
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        snowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",generalLatitude);
                nowcast.put("longitude",generalLongitude);
                nowcast.put("Weather", "Snowy");
                nowcast.put("Location",locationName);
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!", Toast.LENGTH_LONG).show();
            }
        });

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
