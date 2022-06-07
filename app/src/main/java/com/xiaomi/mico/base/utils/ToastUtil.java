package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.xiaomi.mico.utils.Threads;

/* loaded from: classes3.dex */
public class ToastUtil {
    private static Context a;

    public static void init(Context context) {
        a = context;
    }

    public static void showToast(int i) {
        a();
        showToast(a.getString(i));
    }

    public static void showToast(CharSequence charSequence) {
        showToast(charSequence, 0);
    }

    public static void showToast(int i, int i2) {
        a();
        showToast(a.getString(i), i2);
    }

    public static void showToast(final CharSequence charSequence, final int i) {
        a();
        if (!TextUtils.isEmpty(charSequence)) {
            if (Threads.isMainThread()) {
                Toast.makeText(a, charSequence, i).show();
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$ToastUtil$vZ60CU6uHAcAfMdqG2f7AeSSf8E
                    @Override // java.lang.Runnable
                    public final void run() {
                        ToastUtil.a(charSequence, i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(CharSequence charSequence, int i) {
        Toast.makeText(a, charSequence, i).show();
    }

    public static void showCustomToast(int i, int i2) {
        showToast(i, i2);
    }

    private static void a() {
        if (a == null) {
            throw new RuntimeException("Call ToastUtil.init first");
        }
    }
}
