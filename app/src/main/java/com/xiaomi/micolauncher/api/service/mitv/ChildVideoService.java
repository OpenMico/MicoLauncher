package com.xiaomi.micolauncher.api.service.mitv;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.ChildVideoDetail;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/* loaded from: classes3.dex */
public interface ChildVideoService {
    public static final String API_PREVIEW_URL = "https://pwapp.tv.mi.com";
    public static final String API_PRODUCTION_URL = "https://pwapp.tv.mi.com";
    public static final String FIRST_URL = "/tv/lean/fl/in?id=2041";

    @GET
    Observable<ChildVideo> getChildVideo(@Url String str, @Query("ptf") int i, @Query("app") int i2, @Query("codever") int i3, @Query("deviceid") String str2, @Query("opaque") String str3);

    @GET("/tv/lean/v")
    Observable<ChildVideoDetail> getMiTvDetail(@Query("id") String str, @Query("ptf") int i, @Query("app") int i2, @Query("codever") int i3, @Query("deviceid") String str2, @Query("opaque") String str3);
}
