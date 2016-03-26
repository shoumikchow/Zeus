package shoumik.com.zeus.API.Current;

/**
 * Created by shoumik on 3/25/16.
 */
public class Sys {

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    private Integer sunrise;
    private Integer sunset;
}
