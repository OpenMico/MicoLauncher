package com.xiaomi.micolauncher.api.service.mitv;

import com.xiaomi.micolauncher.api.model.CheckOrder;
import com.xiaomi.micolauncher.api.model.ProductPrice;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface MiTvBuyService {
    public static final String API_PREVIEW_URL = "http://h5-test.sys.tv.mi.com";
    public static final String API_PRODUCTION_URL = "https://h5.tv.mi.com";

    @GET("/store/pricetag/order/check/{shortkey}")
    Observable<CheckOrder.CheckNotRenewOrder> checkNotRenewOrder(@Path("shortkey") String str, @Query("isRenew") int i, @Query("shortKey") String str2);

    @GET("/store/pricetag/order/check/{shortkey}")
    Observable<CheckOrder.CheckRenewOrder> checkRenewOrder(@Path("shortkey") String str, @Query("isRenew") int i, @Query("shortKey") String str2);

    @GET("/store/pricetag/shortkey/create")
    Observable<ProductPrice.PriceTag> getPriceTagShortKey(@Query("isLogin") int i, @Query("isRenew") int i2, @Query("orderInfo") String str);
}
