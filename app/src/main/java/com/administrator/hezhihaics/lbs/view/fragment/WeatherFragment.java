package com.administrator.hezhihaics.lbs.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.administrator.hezhihaics.lbs.model.IWeatherInfo;
import com.administrator.hezhihaics.lbs.model.impl.WeatherInfo;
import com.administrator.hezhihaics.lbs.util.ToastUtil;
import com.administrator.hezhihaics.lbs.view.R;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.administrator.hezhihaics.lbs.view.R.id.edt_cityName;

/**
 * Created by hezhihaics on 2017/3/21.
 */
public class WeatherFragment extends Fragment{
    private Context mWeatherSearchActivity;
    private View mWeatherFragmentView;
    private EditText edt_cityName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mWeatherSearchActivity = getActivity();
        this.mWeatherFragmentView = inflater.inflate(R.layout.fragment_weather, container, false);
        IWeatherInfo iWeatherInfo = new WeatherInfo();
        iWeatherInfo.getWeatherInfo(mWeatherFragmentView, this.getActivity());
        return this.mWeatherFragmentView;
    }
}