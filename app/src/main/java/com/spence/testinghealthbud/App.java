package com.spence.testinghealthbud;

import android.app.Application;
import android.content.res.Configuration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        languages.setFromPreference(this, "myPreferenceKey");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        languages.setFromPreference(this, "myPreferenceKey");
    }
}
