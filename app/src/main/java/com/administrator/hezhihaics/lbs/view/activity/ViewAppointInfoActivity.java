package com.administrator.hezhihaics.lbs.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.administrator.hezhihaics.lbs.adapter.AppointInfoAdapter;
import com.administrator.hezhihaics.lbs.bean.LBSApplication;
import com.administrator.hezhihaics.lbs.util.ToastUtil;
import com.administrator.hezhihaics.lbs.view.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.administrator.hezhihaics.lbs.util.ToastUtil.show;
import static com.administrator.hezhihaics.lbs.view.R.id.lv_guideGroupInfo;

/**
 * Created by Administrator on 2017/5/21.
 */

public class ViewAppointInfoActivity extends AppCompatActivity {
    private Map<String, String> map = new HashMap();
    private RequestQueue mQueue;    //volley网络请求队列
    private String jsonArrayStr;
    private ListView lv_appointInfo;
    private Button btn_viewAppointInfo;
    private AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 设置全屏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.appointinfo);
        mQueue = Volley.newRequestQueue(this);
        this.lv_appointInfo = (ListView)this.findViewById(R.id.lv_appointInfo);
        appCompatActivity  = this;
        btn_viewAppointInfo = (Button)this.findViewById(R.id.btn_viewAppointInfo);
        btn_viewAppointInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointInfoRequestWithPost();
                AppointInfoAdapter appointInfoAdapter = new AppointInfoAdapter(appCompatActivity, jsonArrayStr);
                lv_appointInfo.setAdapter(appointInfoAdapter);
                lv_appointInfo.refreshDrawableState();
            }
        });
    }

    private void appointInfoRequestWithPost() {
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
                show(ViewAppointInfoActivity.this, "Error Occurred in Server!");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.put("requestType","appointTourGuide");
                map.put("appointInfoRequestType", "viewAppointInfo");
                map.put("t_id", String.valueOf(LBSApplication.getInstance().getTourist().getT_id()));
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
