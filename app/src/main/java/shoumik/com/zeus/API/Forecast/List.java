package shoumik.com.zeus.API.Forecast;

/**
 * Created by shoumik on 3/20/16.
 */
public class List {

    private String dt_txt;
    private Main main;
    private Weather[]weather;


    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }



}
