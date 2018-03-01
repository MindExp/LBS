package com.administrator.hezhihaics.lbs.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import com.administrator.hezhihaics.lbs.view.fragment.MineCenterFragment;
import com.administrator.hezhihaics.lbs.view.fragment.POIFragment;
import com.administrator.hezhihaics.lbs.view.fragment.TourGuideFragment;
import com.administrator.hezhihaics.lbs.view.fragment.WeatherFragment;
import com.administrator.hezhihaics.mylibrary.TabContainerView;
import com.administrator.hezhihaics.mylibrary.adapter.DefaultAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by Administrator on 2017/5/15.
 */
public class GuideMainActivity extends AppCompatActivity {

    private TabContainerView tabContainerView;
    private int[] iconImageArray, selectedIconImageArray;
    private Fragment[] fragments;
    private int[][] mIconArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolBar();
        Toast.makeText(this, GuideMainActivity.getSHA1(GuideMainActivity.this), Toast.LENGTH_SHORT).show();
        Log.e("SHA1", GuideMainActivity.getSHA1(GuideMainActivity.this));
        //System.out.println("SHA1：" + GuideMainActivity.getSHA1(TouristMainActivity.this));
    }

    private void initView() {
        mIconArray = new int[][] {{R.drawable.icon_main, R.drawable.icon_main_selected}, {R.drawable.icon_work, R.drawable.icon_work_selected},
                {R.drawable.icon_app, R.drawable.icon_app_selected}, {R.drawable.icon_mine, R.drawable.icon_mine_selected}};

        iconImageArray = new int[]{R.drawable.icon_main, R.drawable.icon_work, R.drawable.icon_app, R.drawable.icon_mine};
        selectedIconImageArray = new int[]{R.drawable.icon_main_selected, R.drawable.icon_work_selected, R.drawable.icon_app_selected, R.drawable.icon_mine_selected};
        fragments = new Fragment[] {new TourGuideFragment(), new POIFragment(), new WeatherFragment(), new MineCenterFragment()};

        tabContainerView = (TabContainerView) findViewById(R.id.tab_containerview_main);
        tabContainerView.setAdapter(new DefaultAdapter(this, fragments, getSupportFragmentManager(),
                getResources().getStringArray(R.array.guideArray),
                getResources().getColor(R.color.colorPrimary), iconImageArray, selectedIconImageArray));
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("LBS");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);   //this
    }

    //获取测试版本SHA1
    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0XFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

