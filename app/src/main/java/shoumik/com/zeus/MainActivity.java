package shoumik.com.zeus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import shoumik.com.zeus.API.Current.OpenWeatherCurrentModel;
import shoumik.com.zeus.API.Forecast.OpenWeatherMapModel;
import shoumik.com.zeus.API.OWMApi;
import shoumik.com.zeus.OverflowMenu.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    String API = "http://api.openweathermap.org";
    String city = "Dhaka";
    String units = "metric";
    String TAG = "ShoumiksTAG";
    int id;
    int sunset;
    TextView tvTemp;
    TextView tvLocation;
    ImageView imageView;
    ListView weatherList;
    double tvDbl;
    LinearLayout descLay;
    LinearLayout listLay;
    //Picasso.with(getApplicationContext()).load(gitmodel.getAvatarUrl()).into(imageView);

    WeatherListAdapter weatherListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTemp= (TextView) findViewById(R.id.tvTemp);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        weatherList = (ListView) findViewById(R.id.weatherList);
        imageView = (ImageView) findViewById(R.id.imageView);

        descLay=(LinearLayout)findViewById(R.id.descLay);
        listLay=(LinearLayout)findViewById(R.id.listLay);



//        SharedPreferences preferences=this.getSharedPreferences("sp",MODE_PRIVATE);
//        city=preferences.getString("location", null);
//        units=preferences.getString("unit",null);


        tvLocation.setText(city);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        OWMApi owmApi = restAdapter.create(OWMApi.class);





        owmApi.getForecast(city, units, new Callback<OpenWeatherMapModel>() {
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


        owmApi.getCurrent(city, units, new Callback<OpenWeatherCurrentModel>() {
            @Override
            public void success(OpenWeatherCurrentModel openWeatherCurrentModel, Response response) {

                id = openWeatherCurrentModel.getWeather()[0].getId();
                sunset = openWeatherCurrentModel.getSys().getSunset();
                tvDbl = openWeatherCurrentModel.getMain().getTemp();
                tvDbl = round(tvDbl, 1);

                tvTemp.setText(tvDbl + " °C");


//                if (sunset>System.currentTimeMillis()){
//
//                    descLay.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                }

                imageSelector();


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


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
                startActivity(new Intent(this, MapsActivity.class));
                break;

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
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


}
