package shoumik.com.zeus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

public class WeatherSelector extends AppCompatActivity {

    String TAG="ShoumiksTAG";
    ParseObject nowcast = new ParseObject("Nowcast");
    LatLng latLng;
    Double lat;
    Double lon;
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

        lat=getIntent().getDoubleExtra("latCoord",0);
        lon=getIntent().getDoubleExtra("lonCoord", 0);

        Log.d(TAG,"in weatherselector lat= "+lat);
        Log.d(TAG,"in weatherselector lon= "+lon);
      //  Log.d(TAG,"in weatherselector"+latLngStr);

        sunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",lat);
                nowcast.put("longitude",lon);
                nowcast.put("Weather", "Sunny");
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!", Toast.LENGTH_LONG).show();
            }
        });

        rainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",lat);
                nowcast.put("longitude",lon);
                nowcast.put("Weather","Rainy");
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        stormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",lat);
                nowcast.put("longitude",lon);
                nowcast.put("Weather","Stormy");
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        cloudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",lat);
                nowcast.put("longitude",lon);
                nowcast.put("Weather","Cloudy");
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        fogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",lat);
                nowcast.put("longitude",lon);
                nowcast.put("Weather","Foggy");
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!",Toast.LENGTH_LONG).show();
            }
        });

        snowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nowcast.put("Location", latLngStr);
                nowcast.put("latitude",lat);
                nowcast.put("longitude",lon);
                nowcast.put("Weather", "Snowy");
                nowcast.saveInBackground();
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Nowcast data saved!", Toast.LENGTH_LONG).show();
            }
        });

    }


}
