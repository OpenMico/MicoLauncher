package com.xiaomi.micolauncher.module.main;

import android.app.Activity;
import android.os.Handler;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public abstract class WeakReferenceToActivityHandler<T extends Activity> extends Handler {
    protected final WeakReference<T> mActivity;

    public WeakReferenceToActivityHandler(T t) {
        this.mActivity = new WeakReference<>(t);
    }
}
