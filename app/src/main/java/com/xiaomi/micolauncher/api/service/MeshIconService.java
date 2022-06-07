package com.xiaomi.micolauncher.api.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface MeshIconService {
    public static final String BASE_URL = "http://pv.api.io.mi.com/app/v2/public/get_product_config/";
    public static final String PREVIEW_URL = "http://pv.api.io.mi.com/app/v2/public/get_product_config/";

    @GET("http://pv.api.io.mi.com/app/v2/public/get_product_config")
    Observable<ResponseBody> getIconInfo(@Query("data") String str);

    @GET("https://api.io.mi.com/app/service/getappconfig")
    Observable<ResponseBody> getNoSupportDevice(@Query("data") String str);
}
