package org.kuska.bscapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.kuska.bscapp.injection.AppComponent;
import org.kuska.bscapp.injection.DaggerAppComponent;
import org.kuska.bscapp.injection.NetworkModule;
import org.kuska.bscapp.util.LocaleManager;

import java.io.File;

import static android.content.pm.PackageManager.GET_META_DATA;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2017/06/23
 */
public class BaseActivity extends AppCompatActivity {
    AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetTitles();
        File cacheFile = new File(getCacheDir(), "responses");
        appComponent = DaggerAppComponent.builder().networkModule(new NetworkModule(cacheFile)).build();
    }

    private void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected void showNotificationDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_notification_dialog_positive_button, null)
                .create()
                .show();
    }

}
