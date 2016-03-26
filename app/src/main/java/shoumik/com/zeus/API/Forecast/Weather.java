package shoumik.com.zeus.API.Forecast;

/**
 * Created by shoumik on 3/20/16.
 */
public class Weather {

    private String main;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
