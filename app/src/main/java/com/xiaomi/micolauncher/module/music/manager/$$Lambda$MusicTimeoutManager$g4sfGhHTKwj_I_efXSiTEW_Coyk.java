package com.xiaomi.micolauncher.module.music.manager;

import android.os.Handler;
import android.os.Message;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicTimeoutManager$g4sfGhHTKwj_I_efXSiTEW_Coyk  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$MusicTimeoutManager$g4sfGhHTKwj_I_efXSiTEW_Coyk implements Handler.Callback {
    public static final /* synthetic */ $$Lambda$MusicTimeoutManager$g4sfGhHTKwj_I_efXSiTEW_Coyk INSTANCE = new $$Lambda$MusicTimeoutManager$g4sfGhHTKwj_I_efXSiTEW_Coyk();

    private /* synthetic */ $$Lambda$MusicTimeoutManager$g4sfGhHTKwj_I_efXSiTEW_Coyk() {
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        boolean a;
        a = MusicTimeoutManager.a(message);
        return a;
    }
}
