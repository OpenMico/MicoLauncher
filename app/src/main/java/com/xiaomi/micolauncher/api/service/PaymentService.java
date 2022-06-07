package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.api.model.MusicPrice;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface PaymentService {
    public static final String BASE_URL = "https://payment.api.mina.mi.com";
    public static final String PREVIEW_URL = "https://preview-payment.mina.mi.com";

    @ResponseHandler
    @FormUrlEncoded
    @POST("/payment/v2/sdk/album/order")
    Observable<MusicPrice.Order> getAlbumOrder(@Field("productName") String str, @Field("origin") String str2, @Field("pictureUrl") String str3, @Field("cpAlbumId") String str4, @Field("cpSoundIds") String str5, @Field("platform") String str6, @Field("action") String str7, @Field("channel") String str8, @Field("saleType") int i);

    @ResponseHandler
    @GET("/payment/mipay/micoin/contract")
    Observable<String> getContractState(@Query("origin") String str, @Query("cpAlbumId") String str2);

    @ResponseHandler
    @GET("/payment/get_qqmusic_member_price")
    Observable<List<MusicPrice>> getMusicPrice(@Query("type") String str, @Query("supportContract") boolean z);

    @ResponseHandler
    @FormUrlEncoded
    @POST("/payment/v2/sdk/product/order")
    Observable<MusicPrice.Order> getOrder(@Field("productName") String str, @Field("productId") String str2, @Field("productCount") int i, @Field("origin") String str3, @Field("pictureUrl") String str4, @Field("contractResourceId") String str5, @Field("platform") String str6, @Field("channel") String str7);

    @ResponseHandler
    @GET("/payment/unpurchased/sound_list")
    Observable<ArrayList<String>> getUnpurchasedQuantity(@Query("cpAlbumId") String str, @Query("origin") String str2, @Query("orderType") String str3, @Query("startEpisodesNum") int i);

    @ResponseHandler
    @GET("/payment/order/status")
    Observable<String> orderStatus(@Query("orderId") String str);

    @ResponseHandler
    @GET("/payment/scan_order")
    Observable<Order> paymentOrder(@Query("nextIndex") long j, @Query("limit") int i, @Query("group") String str);
}
