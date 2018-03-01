package com.administrator.hezhihaics.mylibrary.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.administrator.hezhihaics.mylibrary.widget.AbsTab;

/**
 * Created by hezhihaics on 2017/3/22.
 */
public abstract class BaseAdapter {

    private Fragment[] mFragmentArray;
    private FragmentManager mFragmentManager;
    protected Context mContext;

    public BaseAdapter(Context context, Fragment[] fragments, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentArray = fragments;
        mFragmentManager = fragmentManager;
    }

    /**
     *  tab数量
     */
    public int getCount() {
        return mFragmentArray != null ? mFragmentArray.length : 0;
    }

    /**
     * fragment 数组
     */
    public Fragment[] getFragmentArray() {
        return mFragmentArray;
    }

    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    /**
     *  得到tab
     * @return
     */
    public abstract AbsTab getTab(int index);

}
