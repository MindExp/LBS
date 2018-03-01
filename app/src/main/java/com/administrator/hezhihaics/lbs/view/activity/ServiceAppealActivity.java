package com.administrator.hezhihaics.lbs.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.administrator.hezhihaics.lbs.adapter.ServiceAppealInfoAdapter;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.administrator.hezhihaics.lbs.util.ToastUtil.show;

/**
 * Created by Administrator on 2017/6/1.
 */

public class ServiceAppealActivity extends AppCompatActivity {
    private Map<String, String> map = new HashMap();
    private RequestQueue mQueue;    //volley网络请求队列
    private String jsonArrayStr;
    private ListView lv_serviceAppealInfo;
    private Button btn_service_appeal;
    private AppCompatActivity appCompatActivity;
    private String sp_hotline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 设置全屏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.serviceappeal);
        mQueue = Volley.newRequestQueue(this);
        this.lv_serviceAppealInfo = (ListView)this.findViewById(R.id.lv_serviceAppealInfo);
        appCompatActivity  = this;
        btn_service_appeal = (Button)this.findViewById(R.id.btn_service_appeal);
        btn_service_appeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spInfoRequestWithPost();
                ServiceAppealInfoAdapter serviceAppealInfo = new ServiceAppealInfoAdapter(appCompatActivity, jsonArrayStr);
                lv_serviceAppealInfo.setAdapter(serviceAppealInfo);
                lv_serviceAppealInfo.refreshDrawableState();
            }
        });

        ((ListView)(this.findViewById(R.id.lv_serviceAppealInfo))).setOnItemClickListener
                (new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            String guideGroupInfoJsonStr = lv_serviceAppealInfo.getAdapter().getItem(position).toString();
                            JSONObject jsonObject = new JSONObject(guideGroupInfoJsonStr);
                            sp_hotline  = jsonObject.getString("sp_hotline");     //获取带团信息id
                            //弹出是否确认预约对话框
                            new AlertDialog.Builder(ServiceAppealActivity.this).setTitle("确认投诉？")
                                    .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            /**
                                             * 电话投诉书响应
                                             */
                                            //动态权限检查
                                            if (Build.VERSION.SDK_INT >= 23) {
                                                int checkPermission = ContextCompat.checkSelfPermission(ServiceAppealActivity.this,
                                                        Manifest.permission.CALL_PHONE);
                                                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(ServiceAppealActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                                    return;
                                                }
                                            }
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sp_hotline));
                                            startActivity(intent);
                                        }
                                    })
                                    .setPositiveButton("取消", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            /**
                                             * 取消投诉
                                             */
                                        }
                                    }).show();
                        }catch (Exception e){
                            ToastUtil.show(ServiceAppealActivity.this, "Error Occurred!");
                        }
                    }
                });
    }

    // 用户权限申请的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length >0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //用户同意相册授权

                }else{
                    //用户拒绝授权
                }
                break;
        }
    }

    private void spInfoRequestWithPost() {
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
                show(ServiceAppealActivity.this, "Error Occurred in Server!");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.put("requestType","serviceAppeal");
                map.put("t_id", String.valueOf(LBSApplication.getInstance().getTourist().getT_id()));
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
