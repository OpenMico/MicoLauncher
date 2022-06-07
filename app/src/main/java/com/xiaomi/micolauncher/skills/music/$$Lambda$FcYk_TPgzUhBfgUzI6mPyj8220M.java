package com.xiaomi.micolauncher.skills.music;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.-$$Lambda$FcYk_TPgzUhBfgUzI6mPyj8220M  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FcYk_TPgzUhBfgUzI6mPyj8220M implements Runnable {
    public static final /* synthetic */ $$Lambda$FcYk_TPgzUhBfgUzI6mPyj8220M INSTANCE = new $$Lambda$FcYk_TPgzUhBfgUzI6mPyj8220M();

    private /* synthetic */ $$Lambda$FcYk_TPgzUhBfgUzI6mPyj8220M() {
    }

    @Override // java.lang.Runnable
    public final void run() {
        PlayerApi.refreshLoveStatus();
    }
}
