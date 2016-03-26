package shoumik.com.zeus.API.Forecast;

/**
 * Created by shoumik on 3/20/16.
 */
public class City {


    private String name;
    private Coord coord;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
