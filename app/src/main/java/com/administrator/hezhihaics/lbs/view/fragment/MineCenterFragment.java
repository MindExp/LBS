package com.administrator.hezhihaics.lbs.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.administrator.hezhihaics.lbs.bean.LBSApplication;
import com.administrator.hezhihaics.lbs.view.R;
import com.administrator.hezhihaics.lbs.view.activity.ServiceAppealActivity;
import com.administrator.hezhihaics.lbs.view.activity.ViewAppointInfoActivity;

/**
 * Created by hezhihaics on 2017/3/21.
 */
public class MineCenterFragment extends Fragment {
    private View mMineCenterView;
    private static final int IMAGE = 1;
    private String picPath = Environment.getExternalStorageDirectory().getPath()+ "/" + "temp.png";
    protected int mScreenWidth;
    protected int mScreenHeight;
    private Button btn_exit;
    private ImageView mine_avatar;
    private TextView mine_email;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mMineCenterView = inflater.inflate(R.layout.fragment_minecenter, container, false);;
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        btn_exit = (Button) this.mMineCenterView.findViewById(R.id.btn_exit);
        mine_avatar = (ImageView) this.mMineCenterView.findViewById(R.id.mine_avatar);
        mine_email = (TextView)  this.mMineCenterView.findViewById(R.id.mine_email);
        mine_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        mine_email.setText("欢迎：" + LBSApplication.getInstance().getTourist().getT_email().toString());
        MineCenterClickListener mineCenterClickListener = new MineCenterClickListener();
        mine_avatar.setOnClickListener(mineCenterClickListener);
        this.mMineCenterView.findViewById(R.id.view_appInfo).setOnClickListener(mineCenterClickListener);
        this.mMineCenterView.findViewById(R.id.serviceAppeal).setOnClickListener(mineCenterClickListener);
        btn_exit.setOnClickListener(mineCenterClickListener);
        return this.mMineCenterView;
    }

    private class MineCenterClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mine_avatar:
                    showAvatarPop();
                    break;
                case R.id.view_appInfo:
                    intent = new Intent(getActivity(), ViewAppointInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.serviceAppeal:
                    intent = new Intent(getActivity(), ServiceAppealActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_exit:
                    Snackbar.make(mMineCenterView,"退出!",Snackbar.LENGTH_LONG).show();
                    break;
            }
        }
    }

    RelativeLayout layout_choose;
    RelativeLayout layout_photo;
    PopupWindow avatorPop;
    public String filePath = "";
    private void showAvatarPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_showavator,null);
        layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
        layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
        layout_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //动态权限检查
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkPermission = ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                        return;
                    }
                }
                layout_choose.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_photo.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /*保存路径错误exposed beyond app through ClipData.Item.getUri()
                Uri uri = Uri.fromFile(new File(picPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                */
                MineCenterFragment.this.startActivityForResult(intent, IMAGE);
            }
        });
        layout_choose.setOnClickListener(new View.OnClickListener() {
            /*
            从相册中选择
             */
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //动态权限检查
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkPermission = ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        return;
                    }
                }
                layout_photo.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_choose.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                MineCenterFragment.this.startActivityForResult(intent, IMAGE);
            }
        });

        avatorPop = new PopupWindow(view, mScreenWidth, 600);
        avatorPop.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    avatorPop.dismiss();
                    return true;
                }
                return false;
            }
        });

        avatorPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        avatorPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        avatorPop.setTouchable(true);
        avatorPop.setFocusable(true);
        avatorPop.setOutsideTouchable(true);
        avatorPop.setBackgroundDrawable(new BitmapDrawable());
        // 动画效果 从底部弹起
        avatorPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
        avatorPop.showAtLocation(btn_exit, Gravity.BOTTOM, 0, 0);
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

    // 提示用户去应用设置界面手动开启权限

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }
    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        mine_avatar.setImageBitmap(bm);
    }
}
