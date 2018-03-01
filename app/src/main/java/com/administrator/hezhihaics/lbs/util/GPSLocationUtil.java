package com.administrator.hezhihaics.lbs.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class GPSLocationUtil {
    // 定义LocationManager对象
    LocationManager localManager;
    // 定义程序界面中的EditText组件
    private String provider;
    private Activity mainActivity;
    private double longitude;
    private double latitude;

    public GPSLocationUtil(Activity mainActivity){
        this.mainActivity = mainActivity;
        getGPSLocationInfo();
    }

    public void getGPSLocationInfo() {
        // 创建LocationManager对象
        localManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

        //动态权限检查
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(mainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                return;
            }
        }

        // 从GPS获取最近的最近的定位信息
        List<String> providerList=localManager.getProviders(true);
        //GPS定位出现异常，location一直返回为null
        if(providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        else if(providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        else {
            Toast.makeText(mainActivity,"No location provider to use",Toast.LENGTH_LONG).show();
            return;
        }
        Location location = localManager.getLastKnownLocation(provider);
        // 使用location来更新EditText的显示
        updateView(location);
        // 设置每3秒获取一次GPS的定位信息
        localManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                , 3000, 8, new LocationListener()  // ①
                {
                    @Override
                    public void onLocationChanged(Location location) {
                        // 当GPS定位信息发生改变时，更新位置
                        updateView(location);
                        //Toast.makeText(MainActivity.this, "Location changed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        updateView(null);
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        //动态权限检查
                        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            return;
                        }
                        // 当GPS LocationProvider可用时，更新位置
                        updateView(localManager.getLastKnownLocation(provider));
                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                });
    }

    public void updateView(Location newLocation) {
        if (newLocation != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(newLocation.getProvider() + "\n");
            sb.append("经度：");
            this.longitude = newLocation.getLongitude();
            sb.append(this.longitude);
            sb.append("\n纬度：");
            this.latitude = newLocation.getLatitude();
            sb.append(this.latitude);
            Toast.makeText(mainActivity, "Location refreshed!\n" + sb.toString(), Toast.LENGTH_SHORT).show();
            Log.e("Location refreshed:", sb.toString());
        } else {
            // 如果传入的Location对象为空则清空EditText
            Toast.makeText(mainActivity, provider + ": new location is null.", Toast.LENGTH_SHORT).show();
        }
    }
    public double getLongitude(){
        return this.longitude;
    }
    public double getLatitude(){
        return this.latitude;
    }
}
