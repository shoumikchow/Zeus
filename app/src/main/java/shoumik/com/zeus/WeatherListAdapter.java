package shoumik.com.zeus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import shoumik.com.zeus.API.Forecast.List;
import shoumik.com.zeus.API.Forecast.OpenWeatherMapModel;

/**
 * Created by shoumik on 3/20/16.
 */
public class WeatherListAdapter extends BaseAdapter {

    private OpenWeatherMapModel openWeatherMapModel;
    private LayoutInflater inflater;
    TextView tvWeather;
    double tempMax;
    double tempMin;
    String dt_text = "";
    String TAG = "ShoumiksTAG";
    int sunset;

    public WeatherListAdapter(Context context, OpenWeatherMapModel openWeatherMapModel) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.openWeatherMapModel = openWeatherMapModel;

    }


    @Override
    public int getCount() {
        return openWeatherMapModel.getList().length;
    }

    @Override
    public List getItem(int position) {
        return openWeatherMapModel.getList()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View single_row = inflater.inflate(R.layout.single_row, null);
        tvWeather = (TextView) single_row.findViewById(R.id.tvWeather);
        tempMax = getItem(position).getMain().getTemp_max();
        tempMin = getItem(position).getMain().getTemp_min();
        tempMax= round(tempMax, 1);
        tempMin= round(tempMin, 1);
        dt_text = getItem(position).getDt_txt();
        tvWeather.setText(dt_text + " - " + tempMax + " °C"+" / " + tempMin +  " °C");


        return single_row;
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
