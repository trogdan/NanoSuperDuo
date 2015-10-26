package barqsoft.footballscores;

import android.app.Application;
import android.content.Context;

/**
 * Created by dan on 10/26/15.
 */
public class ScoresApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        ScoresApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ScoresApplication.context;
    }
}