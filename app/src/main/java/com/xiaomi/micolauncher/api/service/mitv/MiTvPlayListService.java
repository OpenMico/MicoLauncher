package com.xiaomi.micolauncher.api.service.mitv;

import com.xiaomi.micolauncher.api.model.ChildVideoPlayList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface MiTvPlayListService {
    public static final String API_PREVIEW_URL = "http://media.pre.pandora.xiaomi.com";
    public static final String API_PRODUCTION_URL = "https://pwapp.tv.mi.com";

    @GET("/tv2/lean/v/play/list")
    Observable<ChildVideoPlayList> getMiTvPlayList(@Query("id") String str, @Query("page") int i, @Query("page_size") int i2, @Query("ptf") int i3, @Query("app") int i4, @Query("codever") int i5, @Query("deviceid") String str2, @Query("opaque") String str3);
}
