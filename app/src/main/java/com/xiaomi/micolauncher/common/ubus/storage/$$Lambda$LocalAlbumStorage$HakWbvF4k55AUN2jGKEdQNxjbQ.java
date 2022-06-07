package com.xiaomi.micolauncher.common.ubus.storage;

import java.io.File;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.ubus.storage.-$$Lambda$LocalAlbumStorage$HakWbvF4k55-AUN2jGKEdQNxjbQ  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$LocalAlbumStorage$HakWbvF4k55AUN2jGKEdQNxjbQ implements Comparator {
    public static final /* synthetic */ $$Lambda$LocalAlbumStorage$HakWbvF4k55AUN2jGKEdQNxjbQ INSTANCE = new $$Lambda$LocalAlbumStorage$HakWbvF4k55AUN2jGKEdQNxjbQ();

    private /* synthetic */ $$Lambda$LocalAlbumStorage$HakWbvF4k55AUN2jGKEdQNxjbQ() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int a;
        a = LocalAlbumStorage.a((File) obj, (File) obj2);
        return a;
    }
}
