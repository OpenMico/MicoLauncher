package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.module.intercom.BaseIntercomResponse;
import com.xiaomi.micolauncher.module.intercom.DeviceHomeInfo;
import com.xiaomi.micolauncher.module.intercom.IntercomSettings;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface AiMiService {
    public static final String API_PRODUCTION_URL = "https://i.ai.mi.com";
    public static final String PREVIEW_4TEST_URL = "https://preview4test.i.ai.mi.com";
    public static final String PREVIEW_URL = "https://preview.i.ai.mi.com";
    public static final String UPLOAD_INTERCOM_ORIGIN_AUDIO = "/miot/intercom/upload_intercom_origin_audio";

    @ResponseHandler
    @GET("/userPortrait/api/children/blacklist")
    Observable<List<String>> getBlackList(@Header("Authorization") String str);

    @GET("/miot/intercom/get_home_members")
    Observable<List<DeviceHomeInfo>> getHomeMembers(@Header("Authorization") String str, @Query("device_id") String str2);

    @GET("/miot/intercom/get_intercom_settings")
    Observable<BaseIntercomResponse<IntercomSettings>> getIntercomSettings(@Header("Authorization") String str, @Query("device_id") String str2, @Query("home_id") String str3, @Query("caller_category") String str4);

    @Headers({"Not-Log-Request-Body: true", "CONNECT_TIMEOUT:1200000", "READ_TIMEOUT:1200000", "WRITE_TIMEOUT:1200000"})
    @POST(UPLOAD_INTERCOM_ORIGIN_AUDIO)
    Observable<ResponseBody> uploadIntercomAudio(@Header("Authorization") String str, @Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("/miot/intercom/upload_intercom_message_status")
    Observable<ResponseBody> uploadIntercomMsgStatus(@Header("Authorization") String str, @Field("user_id") String str2, @Field("device_id") String str3, @Field("message_id") String str4, @Field("status") String str5);

    @FormUrlEncoded
    @POST("/miot/intercom/upload_intercom_settings")
    Observable<BaseIntercomResponse<IntercomSettings>> uploadIntercomSettings(@Header("Authorization") String str, @Field("home_id") String str2, @Field("intercom_settings") String str3, @Field("caller_category") String str4);
}
