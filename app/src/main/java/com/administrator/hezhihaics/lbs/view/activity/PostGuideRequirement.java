package com.administrator.hezhihaics.lbs.view.activity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.administrator.hezhihaics.lbs.bean.LBSApplication;
import com.administrator.hezhihaics.lbs.util.CommonUtils;
import com.administrator.hezhihaics.lbs.util.ToastUtil;
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.administrator.hezhihaics.lbs.view.R.id.btn_add;

/**
 * Created by Administrator on 2017/5/14.
 */

public class PostGuideRequirement extends AppCompatActivity {
    private RequestQueue mQueue;    //volley网络请求队列
    ProgressDialog progress;    //进度条
    private String addGuideReqInfo;     //需求信息发布成功与否header信息
    private IntentFilter intentFilter;      //游客需求信息发布成功时，系统发出需求广播
    @BindView(R.id.req_date)
    EditText req_date;
    @BindView(R.id.req_spName)
    EditText req_spName;
    @BindView(R.id.req_num)
    EditText req_num;
    @BindView(R.id.req_tip)
    EditText req_tip;
    @BindView(R.id.req_extraInfo)
    EditText req_extraInfo;
    @BindView(R.id.btn_post)
    Button btn_post;
    @BindView(R.id.img_back)
    ImageButton imgBtnBack;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 设置全屏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_diary);
        setContentView(R.layout.activity_add_guiderequirement);
        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(getApplication());
    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }
    @OnClick(R.id.btn_cancel)
    public void cancel(){
        finish();
    }
    @OnClick(R.id.btn_post)
    public void addDiary(){
        String r_date =req_date.getText().toString();
        String r_spName = req_spName.getText().toString();
        String r_num = req_num.getText().toString();
        String r_tip = req_tip.getText().toString();
        if (TextUtils.isEmpty(r_date)) {
            Snackbar.make(btn_post,"需求时间不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(r_spName)) {
            Snackbar.make(btn_post,"需求景点不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(r_num)) {
            Snackbar.make(btn_post,"需求人数不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(r_tip)) {
            Snackbar.make(btn_post,"每人费用不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }
        //判断用户网络连接信息
        boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
        if(!isNetConnected){
            Snackbar.make(btn_post,"网络连接出错",Snackbar.LENGTH_LONG).show();
            return;
        }
        progress = new ProgressDialog(PostGuideRequirement.this);
        progress.setMessage("正在添加...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        addGuideRequirementRequestWithPost();
    }

    private void addGuideRequirementRequestWithPost() {
        String url = "http://192.168.43.212:8080/LBS_Server/ControlServlet";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //volley网络请求服务器端Response返回
                        //ToastUtil.show(getApplication(), response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                ToastUtil.show(getApplication(), "Error Occurred in Server!");
            }
        }) {
            //volley带参数网络请求
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("requestType", "guideReqInfo");
                map.put("guideReqInfoRequestType", "addGuideReq");
                map.put("t_id", Integer.valueOf(LBSApplication.getInstance().getTourist().getT_id()).toString());
                map.put("req_date", req_date.getText().toString().trim());
                map.put("req_spName", req_spName.getText().toString().trim());
                map.put("req_num", req_num.getText().toString().trim());
                map.put("req_tip", req_tip.getText().toString().trim());
                map.put("req_extraInfo", req_extraInfo.getText().toString().trim());
                return map;
            }
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {
                    //volley获取服务器端返回的header信息，判断用户导游需求是否发布成功
                    Map<String, String> responseHeaders = response.headers;
                    addGuideReqInfo = responseHeaders.get("addGuideReqInfo");
                    String dataString = new String(response.data, "UTF-8");
                    progress.dismiss();
                    //发布成功则提示用户需求信息已经发布
                    if(addGuideReqInfo != null && addGuideReqInfo.equals("succeed")){
                        progress.dismiss();
                        Snackbar.make(btn_post,"添加成功!",Snackbar.LENGTH_LONG).show();
                        /*
                        使用广播机制向需求景点导游推送需求信息
                         */
                        intentFilter = new IntentFilter();
                        intentFilter.addAction("GuideServiceReqInfo");
                    }else{
                        progress.dismiss();
                        Snackbar.make(btn_post,"添加失败!",Snackbar.LENGTH_LONG).show();
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
