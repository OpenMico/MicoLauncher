package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface UserProfileService {
    public static final String BASE_URL = "https://userprofile.mina.mi.com/";
    public static final String PREVIEW_URL = "https://preview-userprofile.mina.mi.com/";
    public static final String TW_BASE_URL = "https://tw.userprofile.mina.mi.com/";
    public static final String TW_PREVIEW_URL = "http://tw.preview.userprofile.mina.mi.com/";

    @ResponseHandler
    @GET("/userprofile/prefered_source")
    Observable<String> getMusicSource();

    @ResponseHandler
    @GET("/userprofile/user_phone/self/nickname")
    Observable<String> getNameCard(@Query("userId") long j);

    @ResponseHandler
    @GET("/userprofile/user_privacy")
    Observable<ThirdPartyResponse.UserPrivacy> getUserPrivacy();
}
