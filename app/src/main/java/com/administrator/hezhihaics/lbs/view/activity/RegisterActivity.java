package com.administrator.hezhihaics.lbs.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import com.administrator.hezhihaics.lbs.util.CommonUtils;
import com.administrator.hezhihaics.lbs.util.JSONUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.id.list;

public class RegisterActivity extends AppCompatActivity {
    private RequestQueue mQueue;    //volley网络请求队列
    ProgressDialog progress;    //进度条
    private String registerResult;     //登录成功与否header信息
    //private String jsonArrayStr;    //景点JSON信息
    private List<Map<String, Object>> list = new ArrayList<>();

    // Butterknife插件的使用 http://www.cnblogs.com/zhaoyanjun/p/6016341.html
    @BindView(R.id.register_email)
    EditText register_email;
    @BindView(R.id.register_password)
    EditText register_password;
    @BindView(R.id.register_confirm_password)
    EditText register_confirm_password;
    @BindView(R.id.btn_tourists)
    RadioButton btn_tourists;
    @BindView(R.id.btn_tourGuide)
    RadioButton btn_tourGuide;
    @BindView(R.id.btn_register)
    Button registerBtn;
    @BindView(R.id.img_back)
    ImageButton imgBtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mQueue = Volley.newRequestQueue(getApplication());
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

/*

    @OnClick(R.id.btn_tourGuide)
    public void loadSPInfo(){
        //加载景点信息
        spRequestWithPost();
        list = JSONUtil.reJsonSPArrayStr(jsonArrayStr);
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
                ToastUtil.show(RegisterActivity.this, "Error Occurred in Server!");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("requestType", "searchSPInfo");
                map.put("spKeyWords", "");      //检索有所景点信息
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
*/

    @OnClick(R.id.btn_register)
    public void register(){
        String email =register_email.getText().toString();
        String password = register_password.getText().toString();
        String confirm_password = register_confirm_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(registerBtn,"账号不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(password)) {
            Snackbar.make(registerBtn,"密码不能为空",Snackbar.LENGTH_LONG).show();
            return;
        }else if (!confirm_password.equals(password)) {
            Snackbar.make(registerBtn,"两次密码输入不一致",Snackbar.LENGTH_LONG).show();
            return;
        }
        //判断用户网络连接信息
        boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
        if(!isNetConnected){
            Snackbar.make(registerBtn,"网络连接出错",Snackbar.LENGTH_LONG).show();
            return;
        }
        progress = new ProgressDialog(RegisterActivity.this);
        progress.setMessage("正在注册...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        userRegisterRequestWithPost();
    }

    private void userRegisterRequestWithPost() {
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
                String requestRegisterType = btn_tourists.isChecked() ? "touristRegister" : (btn_tourGuide.isChecked()
                        ? "guiderRegister" : "spAdminRegister");
                map.put("requestType", "userRegister");
                map.put("registerType", requestRegisterType);
                //map.put("sp_id", "");       //导游申请景点ID
                map.put("register_email", register_email.getText().toString().trim());
                map.put("register_pwd", register_password.getText().toString().trim());
                return map;
            }
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {
                    //volley获取服务器端返回的header信息，判断用户是否登录成功
                    Map<String, String> responseHeaders = response.headers;
                    registerResult = responseHeaders.get("registerResult");
                    String dataString = new String(response.data, "UTF-8");
                    progress.dismiss();
                    //登录成功则转向应用主界面，否则提示用户信息
                    if(registerResult != null && registerResult.equals("succeed")){
                        progress.dismiss();
                        Snackbar.make(registerBtn,"用户注册成功!",Snackbar.LENGTH_LONG).show();
                    }else{
                        progress.dismiss();
                        Snackbar.make(registerBtn,"用户已存在！",Snackbar.LENGTH_LONG).show();
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