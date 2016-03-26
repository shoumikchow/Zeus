package shoumik.com.zeus.API.Current;

/**
 * Created by shoumik on 3/25/16.
 */
public class OpenWeatherCurrentModel {

    private Sys sys;
    private Weather[] weather;
    private Main main;

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
