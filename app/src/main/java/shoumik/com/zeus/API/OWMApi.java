package shoumik.com.zeus.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import shoumik.com.zeus.API.Current.OpenWeatherCurrentModel;
import shoumik.com.zeus.API.Forecast.OpenWeatherMapModel;

/**
 * Created by shoumik on 3/20/16.
 */
public interface OWMApi {


    @GET("/data/2.5/forecast?appid=12d9525d7795c0762bb07cc5469d2716")
    public void getForecast(@Query("q") String q, @Query("units") String units, Callback<OpenWeatherMapModel> response);

    @GET("/data/2.5/weather?appid=12d9525d7795c0762bb07cc5469d2716")
    public void getCurrent(@Query("q") String q,@Query("units") String units, Callback<OpenWeatherCurrentModel> reply);
}
