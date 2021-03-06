package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class GetAboutBookApi extends BaseApi {
    int page;

    public GetAboutBookApi() {
        init();
    }

    public GetAboutBookApi(int page) {
        init();
        this.page = page;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("getAboutBook");
    }
    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getAboutBook(page);
    }
}
