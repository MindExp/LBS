package com.administrator.hezhihaics.lbs.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.administrator.hezhihaics.lbs.view.ExampleTwoTab;
import com.administrator.hezhihaics.mylibrary.adapter.BaseAdapter;
import com.administrator.hezhihaics.mylibrary.widget.AbsTab;

/**
 * Created by lenovo on 2017/4/28.
 */
public class ExampleTwoAdapter extends BaseAdapter {

    private int[][] mIconArray;

    public ExampleTwoAdapter(Context context, Fragment[]fragments, FragmentManager fragmentManager, int[][] iconArray) {
        super(context, fragments, fragmentManager);

        mIconArray = iconArray;
    }

    @Override
    public AbsTab getTab(int index) {
        ExampleTwoTab tab = new ExampleTwoTab(mContext, index);
        int[] iconArray = mIconArray[index];
        tab.setIcons(iconArray[0], iconArray[1]);
        return tab;
    }
}
