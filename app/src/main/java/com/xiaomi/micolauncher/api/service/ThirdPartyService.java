package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.model.ThirdParty;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/* loaded from: classes3.dex */
public interface ThirdPartyService {
    @GET
    Observable<ThirdParty.GrayUpdateResponse> checkDailyUpgrade(@Url String str);

    @GET("https://api.account.xiaomi.com/pass/usersCard")
    Observable<ThirdPartyResponse.UserCardResponse> getUserCard(@Query("ids") String str);

    @GET
    Observable<ThirdPartyResponse.GrayUpgradeResponse> greyUpgrade(@Url String str, @Query("model") String str2, @Query("version") String str3, @Query("channel") String str4, @Query("filterID") String str5, @Query("locale") String str6, @Query("time") String str7, @Query("s") String str8);
}
