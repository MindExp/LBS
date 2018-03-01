package com.administrator.hezhihaics.lbs.bean;

import android.app.Application;

/**
 * Created by Administrator on 2017/5/14.
 */

public class LBSApplication extends Application {
    private static LBSApplication instance;
    private Tourist tourist;
    private Spadmin spadmin;
    private Guider guider;

    @Override
    public void onCreate()
    {
        instance = this;
        super.onCreate();
    }

    public static LBSApplication getInstance(){
        return instance;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    public void setSpadmin(Spadmin spadmin) {
        this.spadmin = spadmin;
    }

    public void setGuider(Guider guider) {
        this.guider = guider;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public Spadmin getSpadmin() {
        return spadmin;
    }

    public Guider getGuider() {
        return guider;
    }
}
