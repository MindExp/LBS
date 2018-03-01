package com.administrator.hezhihaics.lbs.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.administrator.hezhihaics.lbs.model.ITourGuiderService;
import com.administrator.hezhihaics.lbs.model.impl.TourGuiderService;
import com.administrator.hezhihaics.lbs.view.PublishDialog;
import com.administrator.hezhihaics.lbs.view.R;


/**
 * Created by hezhihaics on 2017/3/21.
 */
public class TourGuideFragment extends Fragment {
    private Context mTourGuiderServiceInfoActivity;
    private View mTourGuiderServiceFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mTourGuiderServiceFragmentView = inflater.inflate(R.layout.fragment_tourguide, container, false);
        this.mTourGuiderServiceInfoActivity = getActivity();
        ITourGuiderService ITourGuiderService = new TourGuiderService();
        ITourGuiderService.getTourGuiderServiceInfo(mTourGuiderServiceFragmentView, this.getActivity());
        /*
        待实现初入界面隐藏键盘
         */
        return this.mTourGuiderServiceFragmentView;
    }
}
