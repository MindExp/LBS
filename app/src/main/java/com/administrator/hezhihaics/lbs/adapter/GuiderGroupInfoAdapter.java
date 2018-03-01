package com.administrator.hezhihaics.lbs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.administrator.hezhihaics.lbs.util.JSONUtil;
import com.administrator.hezhihaics.lbs.view.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/5/11.
 */

public class GuiderGroupInfoAdapter extends BaseAdapter{
    private LayoutInflater guiderGroupInfoInflater = null;
    private List<Map<String, Object>> dataList;

    public GuiderGroupInfoAdapter(Context context, String jsonArrayStr){
        this.guiderGroupInfoInflater = LayoutInflater.from(context);
        this.dataList = JSONUtil.reJsonArrayStr(jsonArrayStr);
    }

    static class GroupInfo{

        public TextView sp_name;
        public TextView  info_date;
        public TextView  g_name;
        public TextView info_num;
        public TextView info_detail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupInfo groupInfo = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            groupInfo = new GroupInfo();
            //根据自定义的Item布局加载布局
            convertView = guiderGroupInfoInflater.inflate(R.layout.list_item_guidergroupinfo, null);
            groupInfo.g_name = (TextView)convertView.findViewById(R.id.tv_gName);
            groupInfo.info_num = (TextView)convertView.findViewById(R.id.tv_infoNum);
            groupInfo.sp_name = (TextView)convertView.findViewById(R.id.tv_spName);
            groupInfo.info_date = (TextView)convertView.findViewById(R.id.tv_groupInfoDate);
            groupInfo.info_detail = (TextView)convertView.findViewById(R.id.tv_infoDetail);

            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(groupInfo);
        }else
        {
            groupInfo = (GroupInfo)convertView.getTag();
        }
        groupInfo.g_name.setText((String)this.dataList.get(position).get("g_name"));
        groupInfo.info_num.setText(this.dataList.get(position).get("info_num").toString());
        groupInfo.sp_name.setText((String)this.dataList.get(position).get("sp_name"));
        groupInfo.info_date.setText((String)this.dataList.get(position).get("info_date"));
        groupInfo.info_detail.setText((String)this.dataList.get(position).get("info_detail"));
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
