package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.model.MIBrain;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface MiBrainService {
    public static final String API_PREVIEW4TEST_URL = "https://api.preview4test.ai.xiaomi.com";
    public static final String API_PREVIEW_URL = "https://api-preview.ai.xiaomi.com";
    public static final String API_PRODUCTION_URL = "https://api.ai.xiaomi.com";

    @FormUrlEncoded
    @POST("/aivs/v1/todo/audioDownLoad")
    Observable<ResponseBody> audioDownLoad(@Header("Authorization") String str, @Field("device_id") String str2, @Field("audio_file") String str3);

    @FormUrlEncoded
    @POST("/aivs/v1/todo/delByGuid")
    Observable<Void> delByGuid(@Header("Authorization") String str, @Field("device_id") String str2, @Field("memo_id") String str3);

    @GET("/aivs/v1/todo/fetchTodoListByStartTime")
    Observable<MIBrain.FetchTodoListByStartTimeResponse> fetchTodoListByStartTime(@Header("Authorization") String str, @Query("deviceId") String str2, @Query("startTime") long j, @Query("limit") int i);

    @GET("/cp_resource_locator/c/v1.0/provider/{cp}/ids/{id}")
    Observable<MIBrain.MVResource> getCpMVResource(@Header("Authorization") String str, @Path("cp") String str2, @Path("id") String str3, @Query("xiaomiId") String str4, @Query("appId") String str5, @Query("deviceId") String str6, @Query("type") String str7);

    @GET("/cp_resource_locator/c/v1.0/provider/{cp}/ids/{id}")
    Observable<MIBrain.CpResource> getCpResource(@Header("Authorization") String str, @Path("cp") String str2, @Path("id") String str3, @Query("type") String str4, @Query("xiaomiId") String str5, @Query("appId") String str6, @Query("deviceId") String str7);

    @GET("/cp_resource_locator/c/v1.0/provider/{cp}/ids/{id}")
    Observable<MIBrain.CpResource> getCpVideoResource(@Header("Authorization") String str, @Path("cp") String str2, @Path("id") String str3, @Query("xiaomiId") String str4, @Query("appId") String str5, @Query("deviceId") String str6, @Query("ci") int i);

    @GET("/cp_resource_locator/c/v1.0/provider/{cp}/ids/{id}")
    Observable<MIBrain.MiTvResource> getMiTvResource(@Header("Authorization") String str, @Path("cp") String str2, @Path("id") String str3, @Query("ci") int i, @Query("xiaomiId") String str4, @Query("deviceId") String str5, @Query("type") String str6, @Query("support_trial") int i2, @Query("pcode") String str7);

    @Headers({"Not-Log-Request-Body: true"})
    @POST("/aivs/v1/todo/saveAudio")
    @Multipart
    Observable<MIBrain.SaveAudioResult> saveAudio(@Header("Authorization") String str, @Part("device_id") RequestBody requestBody, @Part("audio_file \"; filename=\"audio.m4a\"") RequestBody requestBody2, @Part("duration") RequestBody requestBody3);
}
