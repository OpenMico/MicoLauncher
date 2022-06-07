package com.xiaomi.micolauncher.api.cache;

import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.api.cache.-$$Lambda$ApiRealmHelper$UQ73_-xh4-Gu7eh2O5HKH8PQDno  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ApiRealmHelper$UQ73_xh4Gu7eh2O5HKH8PQDno implements Comparator {
    public static final /* synthetic */ $$Lambda$ApiRealmHelper$UQ73_xh4Gu7eh2O5HKH8PQDno INSTANCE = new $$Lambda$ApiRealmHelper$UQ73_xh4Gu7eh2O5HKH8PQDno();

    private /* synthetic */ $$Lambda$ApiRealmHelper$UQ73_xh4Gu7eh2O5HKH8PQDno() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int a;
        a = ApiRealmHelper.a((VideoData) obj, (VideoData) obj2);
        return a;
    }
}
