package com.xiaomi.micolauncher.api.service.mitv;

import com.xiaomi.micolauncher.api.model.ChildVideoHistory;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/* loaded from: classes3.dex */
public interface ChildVideoHistoryService {
    public static final String API_PREVIEW_URL = "https://user.ptmi.gitv.tv";
    public static final String API_PRODUCTION_URL = "https://user.ptmi.gitv.tv";

    @GET
    Observable<ChildVideoHistory> getChildVideoHistory(@Url String str, @Query("userid") String str2, @Query("ptf") int i, @Query("app") int i2, @Query("codever") int i3, @Query("deviceid") String str3, @Query("opaque") String str4);
}
