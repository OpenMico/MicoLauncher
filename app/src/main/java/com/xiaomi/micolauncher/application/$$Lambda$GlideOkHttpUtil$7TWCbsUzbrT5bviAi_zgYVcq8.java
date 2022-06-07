package com.xiaomi.micolauncher.application;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.application.-$$Lambda$GlideOkHttpUtil$-7TWCbsUzbrT5bviAi_-zgYVcq8  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$GlideOkHttpUtil$7TWCbsUzbrT5bviAi_zgYVcq8 implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$GlideOkHttpUtil$7TWCbsUzbrT5bviAi_zgYVcq8 INSTANCE = new $$Lambda$GlideOkHttpUtil$7TWCbsUzbrT5bviAi_zgYVcq8();

    private /* synthetic */ $$Lambda$GlideOkHttpUtil$7TWCbsUzbrT5bviAi_zgYVcq8() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        boolean a;
        a = GlideOkHttpUtil.a(str, sSLSession);
        return a;
    }
}
