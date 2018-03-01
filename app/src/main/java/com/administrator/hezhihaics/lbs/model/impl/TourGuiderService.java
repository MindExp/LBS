package com.administrator.hezhihaics.lbs.model.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.administrator.hezhihaics.lbs.adapter.GuiderGroupInfoAdapter;
import com.administrator.hezhihaics.lbs.bean.LBSApplication;
import com.administrator.hezhihaics.lbs.model.ITourGuiderService;
import com.administrator.hezhihaics.lbs.util.ToastUtil;
import com.administrator.hezhihaics.lbs.view.PublishDialog;
import com.administrator.hezhihaics.lbs.view.R;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.administrator.hezhihaics.lbs.util.ToastUtil.show;

/**
 * Created by Administrator on 2017/5/9.
 */

public class TourGuiderService implements ITourGuiderService{

    private Context mTourGuiderServiceInfoActivity;
    private View mTourGuiderServiceFragmentView;
    private RequestQueue mQueue;
    private EditText edt_spKeyWords;
    private ListView lv_guideGroupInfo;
    private String jsonArrayStr;
    private Map<String, String> map = new HashMap();
    private int info_id;
    private String addAppointInfoResult;
    private String appointInfoRequestType;

    public void getTourGuiderServiceInfo(View mTourGuiderServiceFragmentView, Activity mTourGuiderServiceInfoActivity){
        this.mTourGuiderServiceFragmentView = mTourGuiderServiceFragmentView;
        this.mTourGuiderServiceInfoActivity = mTourGuiderServiceInfoActivity;
        this.mTourGuiderServiceFragmentView.findViewById(R.id.btn_spSearch).setOnClickListener(new SPSearchListener());
        this.mTourGuiderServiceFragmentView.findViewById(R.id.circle_addMenu).setOnClickListener(new CircleMenuListener());
        edt_spKeyWords = (EditText) this.mTourGuiderServiceFragmentView.findViewById(R.id.edt_spName);
        this.lv_guideGroupInfo = (ListView) this.mTourGuiderServiceFragmentView.findViewById(R.id.lv_guideGroupInfo);
        mQueue = Volley.newRequestQueue(mTourGuiderServiceInfoActivity);
    }

    private class SPSearchListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            map.put("requestType", "searchSPGuiderGroupInfo");
            spRequestWithPost();
            GuiderGroupInfoAdapter guiderGroupInfoAdapter = new GuiderGroupInfoAdapter(mTourGuiderServiceInfoActivity,
                    jsonArrayStr);
            lv_guideGroupInfo.setAdapter(guiderGroupInfoAdapter);
            lv_guideGroupInfo.refreshDrawableState();
            ((ListView)(mTourGuiderServiceFragmentView.findViewById(R.id.lv_guideGroupInfo))).setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           try {
                               String guideGroupInfoJsonStr = lv_guideGroupInfo.getAdapter().getItem(position).toString();
                               JSONObject jsonObject = new JSONObject(guideGroupInfoJsonStr);
                               info_id  = jsonObject.getInt("info_id");     //获取带团信息id
                               //弹出是否确认预约对话框
                               new AlertDialog.Builder(mTourGuiderServiceInfoActivity).setTitle(jsonObject.getString("sp_name")).setMessage(info_id + "号导游" + "，确认预约？")
                                       .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               /**
                                                * 预约导游
                                                */
                                               map.put("requestType", "appointTourGuide");
                                               map.put("appointInfoRequestType", "addAppointInfo");
                                               map.put("info_id", String.valueOf(info_id));
                                               map.put("t_id", String.valueOf(LBSApplication.getInstance().getTourist().getT_id()));
                                               spRequestWithPost();
                                           }
                                       })
                                       .setPositiveButton("取消", new DialogInterface.OnClickListener(){
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               /**
                                                * 取消预约
                                                */
                                           }
                                       }).show();
                            }catch (Exception e){
                                ToastUtil.show(mTourGuiderServiceInfoActivity, "Error Occurred!");
                            }
                        }
                    });
        }
    }

    private class CircleMenuListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            new PublishDialog(mTourGuiderServiceInfoActivity).show();
        }
    }

    private void spRequestWithPost() {
        String url = "http://192.168.43.212:8080/LBS_Server/ControlServlet";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonArrayStr = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                show(mTourGuiderServiceInfoActivity, "Error Occurred in Server!");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.put("spKeyWords", edt_spKeyWords.getText().toString().trim());
                return map;
            }
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {
                    //volley获取服务器端返回的header信息，判断用户是否登录成功
                    Map<String, String> responseHeaders = response.headers;
                    addAppointInfoResult = responseHeaders.get("addAppointInfo");
                    String dataString = new String(response.data, "UTF-8");
                    //提示游客预约状态信息
                    appointInfoRequestType = map.get("appointInfoRequestType");
                    if(appointInfoRequestType != null && appointInfoRequestType.equals("addAppointInfo")){
                        if(addAppointInfoResult != null && addAppointInfoResult.equals("succeed")){
                            Snackbar.make(lv_guideGroupInfo,"预约成功!",Snackbar.LENGTH_LONG).show();
                            ToastUtil.show(mTourGuiderServiceInfoActivity, "预约成功!");
                        }else{
                            Snackbar.make(lv_guideGroupInfo,"预约失败!",Snackbar.LENGTH_LONG).show();
                            ToastUtil.show(mTourGuiderServiceInfoActivity, "预约失败!");
                        }
                    }
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        mQueue.add(stringRequest);
    }
}