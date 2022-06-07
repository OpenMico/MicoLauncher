package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.api.model.Station;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface SoundService {
    public static final String API_PREVIEW_URL = "https://preview-api.mina.mi.com";
    public static final String API_PRODUCTION_URL = "https://api.mina.mi.com";

    @ResponseHandler
    @GET("/music/station/sound_list")
    Observable<Station.Sound> getRecommendSoundList(@Query("stationId") String str, @Query("origin") String str2, @Query("offset") int i, @Query("count") int i2);
}
