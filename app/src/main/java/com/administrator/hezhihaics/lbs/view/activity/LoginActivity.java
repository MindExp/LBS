package com.administrator.hezhihaics.lbs.view.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.administrator.hezhihaics.lbs.bean.Guider;
import com.administrator.hezhihaics.lbs.bean.LBSApplication;
import com.administrator.hezhihaics.lbs.bean.Spadmin;
import com.administrator.hezhihaics.lbs.bean.Tourist;
import com.administrator.hezhihaics.lbs.util.JSONUtil;
import com.administrator.hezhihaics.lbs.util.ToastUtil;
import com.administrator.hezhihaics.lbs.view.GuideMainActivity;
import com.administrator.hezhihaics.lbs.view.R;
import com.administrator.hezhihaics.lbs.view.TouristMainActivity;
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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.administrator.hezhihaics.lbs.util.ChString.To;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue mQueue;    //volley网络请求队列
    private String loginResult;     //登录成功与否header信息
    String loginUserInfo;   //登录用户信息
    ProgressDialog progress;    //进度条
    /*
    ButterKnife是一个专注于Android系统的View注入框架,
    可以减少大量的findViewById以及setOnClickListener代码，可视化一键生成。
     */
    @BindView(R.id.btn_login)
    Button loginBtn;
    @BindView(R.id.btn_register)
    TextView registerBtn;
    @BindView(R.id.login_email)
    EditText login_email;
    @BindView(R.id.login_pwd)
    EditText login_pwd;
    @BindView(R.id.btn_tourists)
    RadioButton btn_tourists;
    @BindView(R.id.btn_tourGuide)
    RadioButton btn_tourGuide;
    String requestLoginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 设置全屏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(getApplication());
        ButterKnife.bind(this);
        initDrawableSize();

    }

    private void initDrawableSize()
    {
        Drawable accountDraw=getResources().getDrawable(R.drawable.login_icon_account);
        accountDraw.setBounds(0,0,45,45);
        Drawable passwordDraw=getResources().getDrawable(R.drawable.login_icon_password);
        passwordDraw.setBounds(0,0,45,45);
        login_email.setCompoundDrawables(accountDraw,null,null,null);
        login_pwd.setCompoundDrawables(passwordDraw,null,null,null);
    }
    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                goToRegisterActivity();
                break;
        }
    }

    private void login() {
        final String email = login_email.getText().toString();
        final String password = login_pwd.getText().toString();
        if (email.isEmpty() || password.isEmpty()){
            ToastUtil.show(getApplication(), "Email or Password is Empty!");
        }else {
            /*连接服务器开始用户登录*/
            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("正在登陆...");
            progress.setCanceledOnTouchOutside(false);
            progress.show();
            userLoginRequestWithPost();
        }
    }

    private void goToRegisterActivity() {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void userLoginRequestWithPost() {
        String url = "http://192.168.43.212:8080/LBS_Server/ControlServlet";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //volley网络请求服务器端Response返回
                        loginUserInfo = response;   //用户登录账号信息
                        /*
                        使用Application保存用户身份信息，后续操作需获取用户账号信息
                         */
                        if(loginResult != null && requestLoginType.equals("touristLogin")){
                            Tourist tourist = JSONUtil.reJsonStr(loginUserInfo);
                            LBSApplication.getInstance().setTourist(tourist);
                            return;
                        }else if(loginResult != null && requestLoginType.equals("tourGuiderLogin")){
                            Guider guider = new Guider();
                            LBSApplication.getInstance().setGuider(guider);
                            return;
                        }else if(loginResult != null && requestLoginType.equals("spAdminLogin")){
                            Spadmin spadmin = new Spadmin();
                            LBSApplication.getInstance().setSpadmin(spadmin);
                            return;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.show(getApplication(), "Error Occurred in Server!");
                progress.dismiss();
            }
        }) {
            //volley带参数网络请求
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                requestLoginType = btn_tourists.isChecked() ? "touristLogin" : (btn_tourGuide.isChecked()
                        ? "tourGuiderLogin" : "spAdminLogin");
                map.put("requestType", "userLogin");
                map.put("loginType", requestLoginType);
                map.put("login_email", login_email.getText().toString().trim());
                map.put("login_pwd", login_pwd.getText().toString().trim());
                return map;
            }
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {
                    //volley获取服务器端返回的header信息，判断用户是否登录成功
                    Map<String, String> responseHeaders = response.headers;
                    loginResult = responseHeaders.get("loginResult");
                    String dataString = new String(response.data, "UTF-8");
                    progress.dismiss();
                    //登录成功则转向应用主界面，否则提示用户信息
                    if(loginResult != null && loginResult.equals("success")){
                        if(requestLoginType.equals("touristLogin")) {
                            Intent intent=new Intent(LoginActivity.this,TouristMainActivity.class);
                            startActivity(intent);
                            finish();   //销毁当前Activity
                        }else if(requestLoginType.equals("tourGuiderLogin")) {
                            Intent intent=new Intent(LoginActivity.this, GuideMainActivity.class);
                            startActivity(intent);
                            finish();   //销毁当前Activity
                        }else if(requestLoginType.equals("spAdminLogin")) {

                        }
                    }else{
                        Snackbar.make(loginBtn,"用户信息错误",Snackbar.LENGTH_LONG).show();
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