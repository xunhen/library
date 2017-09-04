package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.AdvertisementApi;
import com.example.wangjinchao_pc.library.enity.api.LoginApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.enity.result.SubjectResulte;
import com.example.wangjinchao_pc.library.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/5.
 */

public class LoginActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener{

    public static void start(Context context){
        Intent intent =new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.headPhoto)
    SimpleDraweeView headPhoto;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText passwd;
    @BindView(R.id.select_passwd)
    TextView select_passwd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    //网络请求接口
    private LoginApi loginApi;
    private HttpManager httpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        httpManager=new HttpManager(this,this);

        initActionBar();
    }

    private void initActionBar(){
        setTitle(R.string.login);
        setDisplayHomeAsUpEnabled(false);
    }

    @OnClick({R.id.select_passwd, R.id.login,R.id.register})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.select_passwd:

                FindPasswdActivity.start(this);
                break;
            case R.id.login:

                //正则判断??????????????????????????????????
                if(true){
                    loginApi=new LoginApi(account.getText().toString(),passwd.getText().toString());
                    httpManager.doHttpDeal(loginApi);
                }
                break;
            case R.id.register:

                RegisterActivity.start(this);
                break;
            default:
                break;

        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(loginApi.getMethod())) {
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
            MyApplication.setToken(result.getData());

            MainActivity.start(this);
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        Utils.showToast(e.getDisplayMessage());

        //测试————————————————————————————
        MainActivity.start(this);
    }
}
