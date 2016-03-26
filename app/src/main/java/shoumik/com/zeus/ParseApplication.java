package shoumik.com.zeus;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by shoumik on 3/5/16.
 */
public class ParseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

    }
}
