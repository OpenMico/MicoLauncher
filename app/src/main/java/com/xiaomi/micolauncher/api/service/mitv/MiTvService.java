package com.xiaomi.micolauncher.api.service.mitv;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.ProductPrice;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface MiTvService {
    public static final String API_PREVIEW_URL = "http://preview.bss.duokanbox.com";
    public static final String API_PRODUCTION_URL = "https://bss.tv.mi.com";

    @GET("/bss2/product/price/{deviceID}/{platform}/{sdk_version}/{language}/{country}")
    Observable<ProductPrice> getMiTvProductPrice(@Path("deviceID") String str, @Path("platform") int i, @Path("sdk_version") int i2, @Path("language") String str2, @Path("country") String str3, @Query("pcode") String str4, @Query("mac") String str5, @Query("bizChannel") String str6, @Query("codever") int i3, @Query("userid") String str7);

    @GET("/security/bss/promotion/{deviceID}/{platform}/{sdk_version}/{language}/{country}")
    Observable<ChildVideo.MiTvPromotion> getMiTvPromotion(@Path("deviceID") String str, @Path("platform") int i, @Path("sdk_version") int i2, @Path("language") String str2, @Path("country") String str3, @Query("mac") String str4, @Query("ts") long j, @Query("xiaomiId") String str5);
}
