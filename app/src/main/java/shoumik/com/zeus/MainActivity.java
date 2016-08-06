package shoumik.com.zeus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import shoumik.com.zeus.API.Current.OpenWeatherCurrentModel;
import shoumik.com.zeus.API.Forecast.OpenWeatherMapModel;
import shoumik.com.zeus.API.OWMApi;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    String API = "http://api.openweathermap.org";
    String units = "metric";
    String TAG = "ShoumiksTAG";
    int id;
    int sunset;
    TextView tvTemp;
    ImageView imageView;
    ListView weatherList;
    //TextView tvLocation;
    double tvDbl;
    double lat;
    double lon;

    LinearLayout descLay;
    LinearLayout listLay;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    WeatherListAdapter weatherListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        weatherList = (ListView) findViewById(R.id.weatherList);
        imageView = (ImageView) findViewById(R.id.imageView);
        descLay = (LinearLayout) findViewById(R.id.descLay);
        listLay = (LinearLayout) findViewById(R.id.listLay);

        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }
        getLocation();


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        OWMApi owmApi = restAdapter.create(OWMApi.class);

        owmApi.getForecast(String.valueOf(lat), String.valueOf(lon), units, new Callback<OpenWeatherMapModel>() {
            @Override
            public void success(OpenWeatherMapModel openWeatherMapModel, Response response) {
                weatherListAdapter = new WeatherListAdapter(MainActivity.this, openWeatherMapModel);
                weatherList.setAdapter(weatherListAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "" + error);
            }
        });


        owmApi.getCurrent(String.valueOf(lat), String.valueOf(lon), units, new Callback<OpenWeatherCurrentModel>() {
            @Override
            public void success(OpenWeatherCurrentModel openWeatherCurrentModel, Response response) {

                id = openWeatherCurrentModel.getWeather()[0].getId();
                sunset = openWeatherCurrentModel.getSys().getSunset();
                tvDbl = openWeatherCurrentModel.getMain().getTemp();
                tvDbl = round(tvDbl, 1);

                if (sunset > System.currentTimeMillis()) {                 //DAY
                    descLay.setBackgroundColor(Color.parseColor("#9e9e9e")); //black
                    listLay.setBackgroundColor(Color.parseColor("#eeeeee")); //white
                    tvTemp.setTextColor(Color.parseColor("#ffffff"));

                }
                else{
                    descLay.setBackgroundColor(Color.parseColor("#eeeeee"));
                    listLay.setBackgroundColor(Color.parseColor("#9e9e9e"));
                    tvTemp.setTextColor(Color.parseColor("#000000"));
                }


                tvTemp.setText(tvDbl + " °C");

                imageSelector();


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }


    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();


        } else {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_maps:
                Intent i = new Intent(this, MapsActivity.class);
                i.putExtra("locationLat", lat);
                i.putExtra("locationLon", lon);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


    private void imageSelector() {

        if (sunset > System.currentTimeMillis()) {                 //sun hasn't set (DAY)


            if (id >= 200 && id <= 232) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/11d.png").into(imageView);
            } else if (id >= 300 && id <= 321) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/09d.png").into(imageView);
            } else if (id >= 500 && id <= 504) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/10d.png").into(imageView);
            } else if (id == 511) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/13d.png").into(imageView);
            } else if (id >= 520 && id <= 531) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/09d.png").into(imageView);
            } else if (id >= 600 && id <= 622) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/13d.png").into(imageView);
            } else if (id >= 700 && id <= 781) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/50d.png").into(imageView);
            } else if (id == 800) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/01d.png").into(imageView);
            } else if (id == 801) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/02d.png").into(imageView);
            } else if (id == 802) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/03d.png").into(imageView);
            } else if (id == 803 || id == 804) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/04d.png").into(imageView);
            }


        } else {                               //sun has set (NIGHT)
            if (id >= 200 && id <= 232) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/11n.png").into(imageView);
            } else if (id >= 300 && id <= 321) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/09n.png").into(imageView);
            } else if (id >= 500 && id <= 504) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/10n.png").into(imageView);
            } else if (id == 511) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/13n.png").into(imageView);
            } else if (id >= 520 && id <= 531) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/09n.png").into(imageView);
            } else if (id >= 600 && id <= 622) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/13n.png").into(imageView);
            } else if (id >= 700 && id <= 781) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/50n.png").into(imageView);
            } else if (id == 800) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/01n.png").into(imageView);
            } else if (id == 801) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/02n.png").into(imageView);
            } else if (id == 802) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/03n.png").into(imageView);
            } else if (id == 803 || id == 804) {
                Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/04n.png").into(imageView);
            }

        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


//     Google api callback methods

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        getLocation();

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
        getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
