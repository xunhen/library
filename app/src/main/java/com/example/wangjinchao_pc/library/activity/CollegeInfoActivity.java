package com.example.wangjinchao_pc.library.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Value;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.BookDetailAdapter;
import com.example.wangjinchao_pc.library.adapter.CollegeInfoAdapter;
import com.example.wangjinchao_pc.library.adapter.DividerItemDecoration;
import com.example.wangjinchao_pc.library.adapter.RecyclerViewAdapterWrapper;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.AdvertisementApi;
import com.example.wangjinchao_pc.library.enity.api.GetCollegeApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/13.
 */

public class CollegeInfoActivity extends ToolbarActivity implements HttpOnNextListener{
    public final static String COLLEGE="college";
    public static void start(Context context){
        Intent intent =new Intent(context,CollegeInfoActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.collegeinfo_container)
    RecyclerView collegeinfo_container;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    private int page = Value.startPage;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private CollegeInfoAdapter adapter;

    //网络请求接口
    private HttpManager httpManager;
    private GetCollegeApi getCollegeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_information);
        ButterKnife.bind(this);
        setLoadView(linearLayout);
        initActionBar();

        httpManager=new HttpManager(this,this);

        collegeinfo_container.setLayoutManager(new LinearLayoutManager(this));
        collegeinfo_container.addItemDecoration(new DividerItemDecoration(this));

        //测试——————————————————————
        initData();

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(COLLEGE, (String)((TextView)view).getText());
                //bundle.putParcelable("bitmap", bitmap);
                resultIntent.putExtras(bundle);
                CollegeInfoActivity.this.setResult(RESULT_OK, resultIntent);
                CollegeInfoActivity.this.finish();
            }
        };
        adapter=new CollegeInfoAdapter(this,datas,listener);
        collegeinfo_container.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("选择学校");
        setDisplayHomeAsUpEnabled(true);
    }

    void initData(){
        startLoading();
        HashMap<String, Object> map = null;
        String[] temp={"浙江工业大学","浙江大学","清华大学","复旦大学","哈尔滨大学","合肥大学"};
        for(int i=0;i<temp.length;i++){
            map=new HashMap<String, Object>();
            map.put(CollegeInfoAdapter.CONTENT,temp[i]);
            datas.add(map);
        }
        stopLoading();
    }

    /**
     * 刷新
     */
    private void refresh(){
        getCollegeApi=new GetCollegeApi();
        httpManager.doHttpDeal(getCollegeApi);
    }

    @Override
    public void onNext(String resulte, String method) {
        if(method.equals(getCollegeApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            Utils.showToast("成功");
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if(method.equals(getCollegeApi.getMethod())){
            Utils.showToast("失败");
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }

        //——————————————————
        HashMap<String, Object> map = null;
        for(int i=0;i<5;i++){
            map=new HashMap<String, Object>();
            map.put(CollegeInfoAdapter.CONTENT,"学校refresh"+i);
            datas.add(map);
        }
        adapter.notifyDataSetChanged();
    }
}
