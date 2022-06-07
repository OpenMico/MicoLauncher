package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.model.PassportScope;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface ResourceOwnerPasswordCredentialsGrantService {
    public static final String BASE_URL = "https://account.xiaomi.com/";
    public static final String PREVIEW_URL = "https://account.xiaomi.com/";

    @FormUrlEncoded
    @POST("/oauth2/user-credentials/issued-token")
    Observable<PassportScope.IssuedTokenResult> issuedToken(@Field("grant_type") String str, @Field("client_id") String str2, @Field("client_secret") String str3, @Field("sid") String str4, @Field("code") String str5, @Field("user_id") long j, @Field("pt") int i, @Field("scope_data") String str6);

    @GET("/oauth2/user-credentials/scopes")
    Observable<PassportScope.ScopeResult> scope(@Query("client_id") long j, @Query("sid") String str, @Query("scope") String str2, @Query("pt") int i, @Query("state") String str3);
}
