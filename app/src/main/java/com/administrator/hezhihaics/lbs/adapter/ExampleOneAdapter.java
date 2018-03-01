package com.administrator.hezhihaics.lbs.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.administrator.hezhihaics.lbs.view.ExampleOneTab;
import com.administrator.hezhihaics.mylibrary.adapter.BaseAdapter;
import com.administrator.hezhihaics.mylibrary.widget.AbsTab;

/**
 * Created by hezhihaics on 2017/4/28.
 */
public class ExampleOneAdapter extends BaseAdapter {

    private String[] mStrArray;

    public ExampleOneAdapter(Context context, Fragment[] fragments, FragmentManager fragmentManager, String[] strArray) {
        super(context, fragments, fragmentManager);

        mStrArray = strArray;
    }

    @Override
    public AbsTab getTab(int index) {
        ExampleOneTab tab = new ExampleOneTab(mContext, index);
        tab.setText(mStrArray[index]);
        return tab;
    }
}
