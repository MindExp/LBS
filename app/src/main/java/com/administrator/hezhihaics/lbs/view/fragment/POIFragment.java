package com.administrator.hezhihaics.lbs.view.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.administrator.hezhihaics.lbs.model.IMapInfo;
import com.administrator.hezhihaics.lbs.model.impl.MapInfo;
import com.administrator.hezhihaics.lbs.view.R;
import com.amap.api.maps2d.MapView;

/**
 * Created by hezhihaics on 2017/3/21.
 */
public class POIFragment extends Fragment{
    private MapView mapview;
    private View mPOIFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mPOIFragmentView = inflater.inflate(R.layout.fragment_poi, container, false);
        mapview = (MapView)mPOIFragmentView.findViewById(R.id.mapView);
        mapview.onCreate(savedInstanceState);
        IMapInfo iMapInfo = new MapInfo();
        /*
        mPOIFragmentView.findViewById(R.id.GPSProvider).setOnClickListener(this);
        mPOIFragmentView.findViewById(R.id.NetProvider).setOnClickListener(this);
        */
        iMapInfo.getIMapInfo(mPOIFragmentView, this.getActivity(), getResources());
        return mPOIFragmentView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 2:
                if(grantResults.length >0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //用户同意授权

                }else{
                    //用户拒绝授权
                }
                break;
        }
    }

    /**
     * 方法必须重写
     */

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case GPSProvider:
                GPSProvider = true;
               // super.onResume();
                break;
            case R.id.NetProvider:
                GPSProvider = false;
                //super.onResume();
                break;
            default:
                break;
        }
    }*/
    @Override
    public void onResume() {
        super.onResume();
        mapview.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapview.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapview.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
}