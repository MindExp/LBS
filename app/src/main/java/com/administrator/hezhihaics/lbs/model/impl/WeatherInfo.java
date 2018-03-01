package com.administrator.hezhihaics.lbs.model.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.administrator.hezhihaics.lbs.model.IWeatherInfo;
import com.administrator.hezhihaics.lbs.util.ToastUtil;
import com.administrator.hezhihaics.lbs.view.R;
import com.administrator.hezhihaics.lbs.view.fragment.WeatherFragment;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import java.util.List;
import static com.administrator.hezhihaics.lbs.view.R.id.humidity;
import static com.administrator.hezhihaics.lbs.view.R.id.reporttime1;
import static com.administrator.hezhihaics.lbs.view.R.id.reporttime2;
import static com.administrator.hezhihaics.lbs.view.R.id.weather;
import static com.administrator.hezhihaics.lbs.view.R.id.wind;

/**
 * Created by Administrator on 2017/5/8.
 */

public class WeatherInfo implements WeatherSearch.OnWeatherSearchListener, IWeatherInfo{
    private TextView forecasttv;
    private TextView reporttime1;
    private TextView reporttime2;
    private TextView weather;
    private TextView Temperature;
    private TextView wind;
    private TextView humidity;
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;
    private LocalWeatherForecast weatherforecast;
    private List<LocalDayWeatherForecast> forecastlist = null;
    private String cityname="重庆市";//天气搜索的城市，可以写名称或adcode；
    private Context mWeatherSearchActivity;
    private View mWeatherFragmentView;
    private EditText edt_cityName;

    public void getWeatherInfo(View mWeatherFragmentView, Activity mWeatherSearchActivity){
        this.mWeatherFragmentView = mWeatherFragmentView;
        this.mWeatherSearchActivity = mWeatherSearchActivity;
        init();
        searchliveweather();
        searchforcastsweather();
        edt_cityName = (EditText)mWeatherFragmentView.findViewById(R.id.edt_cityName);
        this.mWeatherFragmentView.findViewById(R.id.btn_weatherSearch).setOnClickListener(new WeatherSearchListener());
    }

    private class WeatherSearchListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            ToastUtil.show(mWeatherSearchActivity, "hello weather info!!");
            WeatherInfo.this.cityname = edt_cityName.getText().toString();
            init();
            searchliveweather();
            searchforcastsweather();
            mWeatherFragmentView.refreshDrawableState();
            edt_cityName.setHint("请输入查询地点");
            edt_cityName.setText("");
            edt_cityName.clearFocus();
            //如果输入法在窗口上已经显示，则隐藏，反之则显示
            InputMethodManager imm = (InputMethodManager) mWeatherSearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void init() {
        TextView city =(TextView)mWeatherFragmentView.findViewById(R.id.city);
        city.setText(cityname);
        forecasttv=(TextView)mWeatherFragmentView.findViewById(R.id.forecast);
        reporttime1 = (TextView)mWeatherFragmentView.findViewById(R.id.reporttime1);
        reporttime2 = (TextView)mWeatherFragmentView.findViewById(R.id.reporttime2);
        weather = (TextView)mWeatherFragmentView.findViewById(R.id.weather);
        Temperature = (TextView)mWeatherFragmentView.findViewById(R.id.temp);
        wind=(TextView)mWeatherFragmentView.findViewById(R.id.wind);
        humidity = (TextView)mWeatherFragmentView.findViewById(R.id.humidity);
    }
    private void searchforcastsweather() {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_FORECAST);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(mWeatherSearchActivity);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
    private void searchliveweather() {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(mWeatherSearchActivity);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
    /**
            * 实时天气查询回调
    */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult , int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                weatherlive = weatherLiveResult.getLiveResult();
                reporttime1.setText(weatherlive.getReportTime()+"发布");
                weather.setText(weatherlive.getWeather());
                Temperature.setText(weatherlive.getTemperature()+"°");
                wind.setText(weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级");
                humidity.setText("湿度         "+weatherlive.getHumidity()+"%");
            }else {
                ToastUtil.show(mWeatherSearchActivity, R.string.no_result);
            }
        }else {
            ToastUtil.showerror(mWeatherSearchActivity, rCode);
        }
    }
    /**
            * 天气预报查询结果回调
    */
    @Override
    public void onWeatherForecastSearched(
            LocalWeatherForecastResult weatherForecastResult, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (weatherForecastResult!=null && weatherForecastResult.getForecastResult()!=null
                    && weatherForecastResult.getForecastResult().getWeatherForecast()!=null
                    && weatherForecastResult.getForecastResult().getWeatherForecast().size()>0) {
                weatherforecast = weatherForecastResult.getForecastResult();
                forecastlist= weatherforecast.getWeatherForecast();
                fillforecast();

            }else {
                ToastUtil.show(mWeatherSearchActivity, R.string.no_result);
            }
        }else {
            ToastUtil.showerror(mWeatherSearchActivity, rCode);
        }
    }
    private void fillforecast() {
        reporttime2.setText(weatherforecast.getReportTime()+"发布");
        String forecast="";
        for (int i = 0; i < forecastlist.size(); i++) {
            LocalDayWeatherForecast localdayweatherforecast=forecastlist.get(i);
            String week = null;
            switch (Integer.valueOf(localdayweatherforecast.getWeek())) {
                case 1:
                    week = "周一";
                    break;
                case 2:
                    week = "周二";
                    break;
                case 3:
                    week = "周三";
                    break;
                case 4:
                    week = "周四";
                    break;
                case 5:
                    week = "周五";
                    break;
                case 6:
                    week = "周六";
                    break;
                case 7:
                    week = "周日";
                    break;
                default:
                    break;
            }
            String temp =String.format("%-3s/%3s",
                    localdayweatherforecast.getDayTemp()+"°",
                    localdayweatherforecast.getNightTemp()+"°");
            String date = localdayweatherforecast.getDate();
            forecast+=date+"  "+week+"                       "+temp+"\n\n";
        }
        forecasttv.setText(forecast);
    }
}
