package androidx.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Field;

/* JADX INFO: Access modifiers changed from: package-private */
@RequiresApi(19)
/* loaded from: classes.dex */
public final class ImmLeaksCleaner implements LifecycleEventObserver {
    private static int a;
    private static Field b;
    private static Field c;
    private static Field d;
    private Activity e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmLeaksCleaner(Activity activity) {
        this.e = activity;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            if (a == 0) {
                a();
            }
            if (a == 1) {
                InputMethodManager inputMethodManager = (InputMethodManager) this.e.getSystemService("input_method");
                try {
                    Object obj = b.get(inputMethodManager);
                    if (obj != null) {
                        try {
                            synchronized (obj) {
                                try {
                                    try {
                                        View view = (View) c.get(inputMethodManager);
                                        if (view != null) {
                                            if (!view.isAttachedToWindow()) {
                                                try {
                                                    d.set(inputMethodManager, null);
                                                    inputMethodManager.isActive();
                                                } catch (IllegalAccessException unused) {
                                                }
                                            }
                                        }
                                    } catch (ClassCastException unused2) {
                                    }
                                } catch (IllegalAccessException unused3) {
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (IllegalAccessException unused4) {
                }
            }
        }
    }

    @SuppressLint({"SoonBlockedPrivateApi"})
    @MainThread
    private static void a() {
        try {
            a = 2;
            c = InputMethodManager.class.getDeclaredField("mServedView");
            c.setAccessible(true);
            d = InputMethodManager.class.getDeclaredField("mNextServedView");
            d.setAccessible(true);
            b = InputMethodManager.class.getDeclaredField("mH");
            b.setAccessible(true);
            a = 1;
        } catch (NoSuchFieldException unused) {
        }
    }
}
