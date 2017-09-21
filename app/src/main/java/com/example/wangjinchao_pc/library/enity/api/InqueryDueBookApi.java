package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class InqueryDueBookApi extends BaseApi{
    String token;

    String account;

    public InqueryDueBookApi() {
        init();
    }

    public InqueryDueBookApi(String account) {
        init();
        this.account = account;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("inqueryDueBook");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.inqueryDueBook(account);
    }

    public void setAllParam(String account){
        this.account=account;
    }
}
