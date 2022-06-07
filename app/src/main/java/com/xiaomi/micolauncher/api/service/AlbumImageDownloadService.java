package com.xiaomi.micolauncher.api.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/* loaded from: classes3.dex */
public interface AlbumImageDownloadService {
    public static final String BASE_URL = "https://www.fakeurl.com";

    @GET
    Observable<ResponseBody> downloadFile(@Url String str);
}
