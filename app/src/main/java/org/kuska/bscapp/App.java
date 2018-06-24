package org.kuska.bscapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import org.kuska.bscapp.util.LocaleManager;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/24
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}
