package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.mico.utils.ThreadUtil;
import com.zhangyue.we.x2c.X2C;

/* loaded from: classes3.dex */
public class X2CWrapper {
    private static boolean a = false;
    private static final Object b = new Object();

    public static View inflate(Context context, int i, ViewGroup viewGroup) {
        View inflate;
        if (a()) {
            return LayoutInflater.from(context).inflate(i, viewGroup);
        }
        synchronized (b) {
            inflate = X2C.inflate(context, i, viewGroup);
        }
        return inflate;
    }

    public static View inflate(Context context, int i, ViewGroup viewGroup, boolean z) {
        View inflate;
        if (a()) {
            return LayoutInflater.from(context).inflate(i, viewGroup, z);
        }
        synchronized (b) {
            inflate = X2C.inflate(context, i, viewGroup, z);
        }
        return inflate;
    }

    public static View inflate(LayoutInflater layoutInflater, int i, ViewGroup viewGroup) {
        View inflate;
        if (a()) {
            return layoutInflater.inflate(i, viewGroup);
        }
        synchronized (b) {
            inflate = X2C.inflate(layoutInflater, i, viewGroup);
        }
        return inflate;
    }

    public static View inflate(LayoutInflater layoutInflater, int i, ViewGroup viewGroup, boolean z) {
        View inflate;
        if (a()) {
            return layoutInflater.inflate(i, viewGroup, z);
        }
        synchronized (b) {
            inflate = X2C.inflate(layoutInflater, i, viewGroup, z);
        }
        return inflate;
    }

    public static View inflateNoX2C(Context context, int i, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(i, viewGroup);
    }

    public static View inflateNoX2C(Context context, int i, ViewGroup viewGroup, boolean z) {
        return LayoutInflater.from(context).inflate(i, viewGroup, z);
    }

    public static View inflateNoX2C(LayoutInflater layoutInflater, int i, ViewGroup viewGroup) {
        return layoutInflater.inflate(i, viewGroup);
    }

    public static View inflateNoX2C(LayoutInflater layoutInflater, int i, ViewGroup viewGroup, boolean z) {
        return layoutInflater.inflate(i, viewGroup, z);
    }

    private static boolean a() {
        return a || ThreadUtil.isMainThread();
    }
}
