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
 * Created by Administrator on 2017/5/10.
 */

public class ServiceAppealInfoAdapter extends BaseAdapter {
    private LayoutInflater appealSPInflater = null;
    private List<Map<String, Object>> dataList;

    public ServiceAppealInfoAdapter(Context context, String jsonArrayStr){
        this.appealSPInflater = LayoutInflater.from(context);
        this.dataList = JSONUtil.reJsonAppealInfoArrayStr(jsonArrayStr);
    }

    static class ServiceAppealInfo{
        public TextView sp_name;
        public TextView appoint_id;
        public TextView  appeal_gName;
        public TextView  sp_hotline;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceAppealInfo serviceAppealInfo = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            serviceAppealInfo = new ServiceAppealInfo();
            //根据自定义的Item布局加 载布局
            convertView = appealSPInflater.inflate(R.layout.list_item_service_appeal, null);
            serviceAppealInfo.sp_name = (TextView)convertView.findViewById(R.id.tv_appeal_SPName);
            serviceAppealInfo.appoint_id = (TextView)convertView.findViewById(R.id.appoint_id);
            serviceAppealInfo.appeal_gName = (TextView)convertView.findViewById(R.id.appeal_gName);
            serviceAppealInfo.sp_hotline = (TextView)convertView.findViewById(R.id.sp_hotline);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(serviceAppealInfo);
        }else
        {
            serviceAppealInfo = (ServiceAppealInfo) convertView.getTag();
        }
        serviceAppealInfo.sp_name.setText((String)this.dataList.get(position).get("sp_name"));
        serviceAppealInfo.appoint_id.setText(String.valueOf(this.dataList.get(position).get("app_id")));
        serviceAppealInfo.appeal_gName.setText((String)this.dataList.get(position).get("appeal_gName"));
        serviceAppealInfo.sp_hotline.setText((String)this.dataList.get(position).get("sp_hotline"));
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
