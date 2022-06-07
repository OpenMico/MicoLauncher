package com.xiaomi.micolauncher.api.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/* loaded from: classes3.dex */
public interface RawService {
    @FormUrlEncoded
    @POST("http://api.io.mi.com/device/devicepass")
    Observable<ResponseBody> devicepass(@Field("data") String str);

    @GET("")
    Observable<ResponseBody> getLoginResult(@Url String str);

    @GET("https://account.xiaomi.com/longPolling/loginUrl")
    Observable<ResponseBody> getLoginUrl(@Query("sid") String str, @Query("callback") String str2);

    @GET("http://{ip}:9095/request?action=deviceinfo")
    Observable<ResponseBody> getMiTvActionDeviceInfo(@Path("ip") String str);

    @GET
    Observable<ResponseBody> getMusicLyric(@Url String str);

    @GET("https://api2.mina.mi.com/ping")
    Observable<ResponseBody> pingServer();

    @GET("http://api2.mina.mi.com/ping/time")
    Observable<ResponseBody> pingTime();
}
