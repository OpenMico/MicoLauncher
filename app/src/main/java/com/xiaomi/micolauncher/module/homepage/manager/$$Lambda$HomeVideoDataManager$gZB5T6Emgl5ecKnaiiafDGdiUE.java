package com.xiaomi.micolauncher.module.homepage.manager;

import android.os.Handler;
import android.os.Message;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$gZB5T6-Emgl5ecKnaiiafDGdiUE  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeVideoDataManager$gZB5T6Emgl5ecKnaiiafDGdiUE implements Handler.Callback {
    public static final /* synthetic */ $$Lambda$HomeVideoDataManager$gZB5T6Emgl5ecKnaiiafDGdiUE INSTANCE = new $$Lambda$HomeVideoDataManager$gZB5T6Emgl5ecKnaiiafDGdiUE();

    private /* synthetic */ $$Lambda$HomeVideoDataManager$gZB5T6Emgl5ecKnaiiafDGdiUE() {
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        boolean a;
        a = HomeVideoDataManager.a(message);
        return a;
    }
}
