package shoumik.com.zeus.API.Forecast;

/**
 * Created by shoumik on 3/20/16.
 */
public class OpenWeatherMapModel {

    private City city;
    private List[] list;

    public List[] getList() {
        return list;
    }

    public void setList(List[] list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


}
