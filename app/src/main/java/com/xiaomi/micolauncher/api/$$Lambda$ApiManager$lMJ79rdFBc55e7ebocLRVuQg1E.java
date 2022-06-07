package com.xiaomi.micolauncher.api;

import com.xiaomi.micolauncher.api.ApiManager;
import okhttp3.OkHttpClient;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.api.-$$Lambda$ApiManager$lMJ79rdFBc55e7ebo-cLRVuQg1E  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ApiManager$lMJ79rdFBc55e7ebocLRVuQg1E implements ApiManager.b {
    public static final /* synthetic */ $$Lambda$ApiManager$lMJ79rdFBc55e7ebocLRVuQg1E INSTANCE = new $$Lambda$ApiManager$lMJ79rdFBc55e7ebocLRVuQg1E();

    private /* synthetic */ $$Lambda$ApiManager$lMJ79rdFBc55e7ebocLRVuQg1E() {
    }

    @Override // com.xiaomi.micolauncher.api.ApiManager.b
    public final OkHttpClient.Builder config(OkHttpClient.Builder builder) {
        OkHttpClient.Builder a;
        a = ApiManager.a(builder);
        return a;
    }
}
