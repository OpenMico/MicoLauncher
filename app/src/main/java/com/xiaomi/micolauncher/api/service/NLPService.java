package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.model.MIBrain;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/* loaded from: classes3.dex */
public interface NLPService {
    public static final String NLP_PREVIEW_URL = "https://nlp-preview.ai.xiaomi.com";
    public static final String NLP_PRODUCTION_URL = "https://nlp.ai.xiaomi.com";

    @FormUrlEncoded
    @POST("/loadmore")
    Observable<MIBrain.LoadMoreResponse> loadMore(@Header("Authorization") String str, @Field("app_id") String str2, @Field("token") String str3, @Field("request_id") String str4, @Field("timestamp") long j, @Field("device_id") String str5, @Field("user_info") String str6, @Field("is_internal") boolean z, @Field("version") String str7, @Field("intention") String str8, @Field("offset") int i, @Field("context") String str9, @Field("session_id") String str10, @Field("device") String str11);
}
