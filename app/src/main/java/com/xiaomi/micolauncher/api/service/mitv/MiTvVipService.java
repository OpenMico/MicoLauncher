package com.xiaomi.micolauncher.api.service.mitv;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface MiTvVipService {
    public static final String API_PREVIEW_URL = "http://stag.bssmini.pandora.xiaomi.com";
    public static final String API_PRODUCTION_URL = "https://miniapp.tv.mi.com";

    @GET("/audio/api/v1/auth/activecode/audioseven")
    Observable<ChildVideo.VipStatus> getSevenDayVip(@Header("Cookie") String str, @Query("language") String str2, @Query("country") String str3, @Query("pcode") String str4, @Query("mac") String str5, @Query("ptf") String str6, @Query("sdk_version") String str7, @Query("deviceid") String str8, @Query("biz") String str9);

    @GET("/audio/api/v1/auth/userbuy/query")
    Observable<ChildVideo.VipStatus> getUserBuyCheck(@Header("Cookie") String str, @Query("language") String str2, @Query("country") String str3, @Query("pcode") String str4, @Query("mac") String str5, @Query("ptf") String str6, @Query("sdk_version") String str7, @Query("deviceid") String str8, @Query("biz") String str9);
}
