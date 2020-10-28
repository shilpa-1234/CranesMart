package com.example.cranesmart.paymentgateway;

import android.app.Application;

import com.example.cranesmart.paymentgateway.AppEnvironment;

/**
 * Created by Rahul Hooda on 14/7/17.
 */
public class BaseApplication extends Application {

    AppEnvironment appEnvironment;

    @Override
    public void onCreate() {
        super.onCreate();
        appEnvironment = AppEnvironment.SANDBOX;
    }

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }
}