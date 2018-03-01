package com.administrator.hezhihaics.lbs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.administrator.hezhihaics.lbs.util.JSONUtil;
import com.administrator.hezhihaics.lbs.view.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/21.
 */

public class AppointInfoAdapter extends BaseAdapter {
    private LayoutInflater appointInfoInflater = null;
    private List<Map<String, Object>> dataList;

    public AppointInfoAdapter(Context context, String jsonArrayStr){
        this.appointInfoInflater = LayoutInflater.from(context);
        this.dataList = JSONUtil.reJsonAppointInfoArrayStr(jsonArrayStr);
    }
    static class AppointInfo{
        public TextView appoint_date;
        public TextView  appoint_sp;
        public TextView  appoint_tourguide;
        public TextView tourguide_phone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppointInfo appointInfo = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            appointInfo = new AppointInfo();
            //根据自定义的Item布局加载布局
            convertView = appointInfoInflater.inflate(R.layout.list_item_appointinfo, null);
            appointInfo.appoint_date = (TextView)convertView.findViewById(R.id.tv_appoint_date);
            appointInfo.appoint_sp = (TextView)convertView.findViewById(R.id.tv_appoint_sp);
            appointInfo.appoint_tourguide = (TextView)convertView.findViewById(R.id.tv_appoint_tourguide);
            appointInfo.tourguide_phone = (TextView)convertView.findViewById(R.id.tv_tourguide_phone);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(appointInfo);
        }else
        {
            appointInfo = (AppointInfo) convertView.getTag();
        }

        appointInfo.appoint_date.setText((String)this.dataList.get(position).get("info_date"));
        appointInfo.appoint_sp.setText(this.dataList.get(position).get("sp_name").toString());
        appointInfo.appoint_tourguide.setText((String)this.dataList.get(position).get("g_name"));
        appointInfo.tourguide_phone.setText((String)this.dataList.get(position).get("g_phone"));
        return convertView;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }
}
