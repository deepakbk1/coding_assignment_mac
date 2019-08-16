package com.deepak.task;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.deepak.task.di.AppComponent;
import com.deepak.task.di.DaggerAppComponent;
import com.deepak.task.di.UtilsModule;

public class MyApplication extends MultiDexApplication {

    public static Context context;
    private AppComponent appComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

}
