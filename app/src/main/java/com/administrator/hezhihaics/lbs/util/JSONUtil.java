package com.administrator.hezhihaics.lbs.util;

import com.administrator.hezhihaics.lbs.bean.GuideGroupInfo;
import com.administrator.hezhihaics.lbs.bean.Tourist;
import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */

public class JSONUtil {

    /*public static Tourist reJsonStr(String jsonStr){
        *//*
        使用JSONObject解析字符串
         *//*
        Tourist tourist = new Tourist();
        try{
            JSONObject jsonObject = new JSONObject(jsonStr);
            tourist.setT_id(jsonObject.getInt("t_id"));
            tourist.setT_email(jsonObject.getString("t_email"));
            tourist.setT_age(jsonObject.getInt("t_age"));
            tourist.setT_gender(jsonObject.getString("t_gender"));
            tourist.setT_name(jsonObject.getString("t_name"));
            tourist.setT_phone(jsonObject.getString("t_phone"));
        }catch (Exception e){

        }
        return tourist;
    }*/

    /*
    使用fastJson解析字符串
     */
    public static Tourist reJsonStr(String jsonStr){
        return JSON.parseObject(jsonStr, Tourist.class);
    }

    public static GuideGroupInfo reJsonStrGuideGroupInfo(String jsonStr){
        return JSON.parseObject(jsonStr, GuideGroupInfo.class);
    }
    /*
    使用JSONObject解析jsonArray
     */
    public static List<Map<String, Object>> reJsonArrayStr(String jsonArrayStr) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonArrayStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                // 每条记录又由几个Object对象组成
                JSONObject item = jsonArray.getJSONObject(i);
                String g_name = item.getString("g_name");
                String sp_name = item.getString("sp_name");
                int info_num = item.getInt("info_num");
                String info_date = item.getString("info_date");
                int info_id = item.getInt("info_id");
                String info_detail = item.getString("info_detail");
                map = new HashMap<>();
                map.put("g_name", g_name);
                map.put("sp_name", sp_name);
                map.put("info_num",info_num);
                map.put("info_date",info_date);
                map.put("info_id",info_id);
                map.put("info_detail", info_detail);
                list.add(map);
            }
        }catch (Exception e){

        }
        return list;
    }

    public static List<Map<String, Object>> reJsonAppealInfoArrayStr(String jsonArrayStr) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        JSONArray jsonArray;
        try {
            if (jsonArrayStr != null){
                jsonArray = new JSONArray(jsonArrayStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    // 每条记录又由几个Object对象组成
                    JSONObject item = jsonArray.getJSONObject(i);
                    int app_id = item.getInt("app_id");
                    String sp_name = item.getString("sp_name");
                    String appeal_gName = item.getString("g_name");
                    String sp_hotline = item.getString("sp_hotline");
                    map = new HashMap<>();
                    map.put("app_id", app_id);
                    map.put("sp_name", sp_name);
                    map.put("appeal_gName",appeal_gName);
                    map.put("sp_hotline",sp_hotline);
                    list.add(map);
                }
            }
        }catch (Exception e){

        }
        return list;
    }

    public static List<Map<String, Object>> reJsonAppointInfoArrayStr(String jsonArrayStr) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        JSONArray jsonArray;
        try {
            if (jsonArrayStr != null){
                jsonArray = new JSONArray(jsonArrayStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    // 每条记录又由几个Object对象组成
                    JSONObject item = jsonArray.getJSONObject(i);
                    String info_date = item.getString("info_date");
                    String sp_name = item.getString("sp_name");
                    String g_name = item.getString("g_name");
                    String g_phone = item.getString("g_phone");
                    map = new HashMap<>();
                    map.put("info_date", info_date);
                    map.put("sp_name", sp_name);
                    map.put("g_name",g_name);
                    map.put("g_phone",g_phone);
                    list.add(map);
                }
            }
        }catch (Exception e){

        }
        return list;
    }

    /*
    解析景点信息
     */
    public static List<Map<String, Object>> reJsonSPArrayStr(String jsonArrayStr) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonArrayStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                // 每条记录又由几个Object对象组成
                JSONObject item = jsonArray.getJSONObject(i);
                map = new HashMap<>();
                map.put("sp_id", item.getInt("sp_id"));
                map.put("sp_name", item.getString("sp_name"));
                list.add(map);
            }
        }catch (Exception e){

        }
        return list;
    }
}
