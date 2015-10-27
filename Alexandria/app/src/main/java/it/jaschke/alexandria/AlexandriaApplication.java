package it.jaschke.alexandria;

import android.app.Application;
import android.content.Context;

/**
 * Created by dan on 10/26/15.
 */
public class AlexandriaApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        AlexandriaApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AlexandriaApplication.context;
    }
}