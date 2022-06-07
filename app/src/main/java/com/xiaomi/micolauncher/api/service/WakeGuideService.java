package com.xiaomi.micolauncher.api.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/* loaded from: classes3.dex */
public interface WakeGuideService {
    public static final String API_PREVIEW4TEST_URL = "http://preview4test-query-suggestion.ai.xiaomi.com";
    public static final String API_PREVIEW_URL = "http://query-suggestion-staging.ai.xiaomi.com";
    public static final String API_PRODUCTION_URL = "http://query-suggestion-staging.ai.xiaomi.com";

    @FormUrlEncoded
    @POST("/simple/litecrypto/uniform/suggest/")
    Observable<ResponseBody> getWakeSuggest(@Header("user_agent") String str, @Header("Authorization") String str2, @Field("trace_id") String str3, @Field("device") String str4, @Field("app_name") String str5, @Field("app_version") int i, @Field("request_origin") String str6);
}
