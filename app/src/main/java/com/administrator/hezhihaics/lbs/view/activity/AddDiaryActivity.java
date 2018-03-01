package com.administrator.hezhihaics.lbs.view.activity;

import android.app.ProgressDialog;
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
import com.administrator.hezhihaics.lbs.bean.Tourist;
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

import static com.administrator.hezhihaics.lbs.view.R.id.btn_tourGuide;
import static com.administrator.hezhihaics.lbs.view.R.id.btn_tourists;
import static com.administrator.hezhihaics.lbs.view.R.id.register_email;
import static com.administrator.hezhihaics.lbs.view.R.id.register_password;

/**
 * Created by Administrator on 2017/5/13.
 */

public class AddDiaryActivity extends AppCompatActivity {
    private RequestQueue mQueue;    //volley网络请求队列
    ProgressDialog progress;    //进度条
    private String addDiaryResult;     //旅行日记添加成功与否header信息

    @BindView(R.id.diary_title)
    EditText diary_title;
    @BindView(R.id.diary_date)
    EditText diary_date;
    @BindView(R.id.diary_content)
    EditText diary_content;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.img_back)
    ImageButton imgBtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 设置全屏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_diary);
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

    @OnClick(R.id.btn_add)
    public void addDiary(){
        String d_title =diary_title.getText().toString();
        String d_date = diary_date.getText().toString();
        String d_content = diary_content.getText().toString();
        if (TextUtils.isEmpty(d_title)) {
            Snackbar.make(btn_add,"标题不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(d_date)) {
            Snackbar.make(btn_add,"时间不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(d_content)) {
            Snackbar.make(btn_add,"日记内容不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }
        //判断用户网络连接信息
        boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
        if(!isNetConnected){
            Snackbar.make(btn_add,"网络连接出错",Snackbar.LENGTH_LONG).show();
            return;
        }
        progress = new ProgressDialog(AddDiaryActivity.this);
        progress.setMessage("正在添加...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        addDiaryRequestWithPost();
    }
    private void addDiaryRequestWithPost() {
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
                map.put("requestType", "diaryInfo");
                map.put("diaryRequestType", "addDiary");
                map.put("t_id", Integer.valueOf(LBSApplication.getInstance().getTourist().getT_id()).toString());
                map.put("diary_title", diary_title.getText().toString().trim());
                map.put("diary_date", diary_date.getText().toString().trim());
                map.put("diary_content", diary_content.getText().toString().trim());
                return map;
            }
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {
                    //volley获取服务器端返回的header信息，判断用户是否登录成功
                    Map<String, String> responseHeaders = response.headers;
                    addDiaryResult = responseHeaders.get("addDiaryResult");
                    String dataString = new String(response.data, "UTF-8");
                    progress.dismiss();
                    //提示用户日记添加状态信息
                    if(addDiaryResult != null && addDiaryResult.equals("succeed")){
                        progress.dismiss();
                        Snackbar.make(btn_add,"日记添加成功!",Snackbar.LENGTH_LONG).show();
                    }else{
                        progress.dismiss();
                        Snackbar.make(btn_add,"日记添加失败!",Snackbar.LENGTH_LONG).show();
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