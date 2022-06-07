package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.content.ClipData;
import android.os.Build;
import android.os.IInterface;
import com.xiaomi.miplay.mylibrary.mirror.Ln;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class ClipboardManager {
    private final IInterface a;
    private Method b;
    private Method c;

    public ClipboardManager(IInterface iInterface) {
        this.a = iInterface;
    }

    private Method a() throws NoSuchMethodException {
        if (this.b == null) {
            if (Build.VERSION.SDK_INT < 29) {
                this.b = this.a.getClass().getMethod("getPrimaryClip", String.class);
            } else {
                this.b = this.a.getClass().getMethod("getPrimaryClip", String.class, Integer.TYPE);
            }
        }
        return this.b;
    }

    private Method b() throws NoSuchMethodException {
        if (this.c == null) {
            if (Build.VERSION.SDK_INT < 29) {
                this.c = this.a.getClass().getMethod("setPrimaryClip", ClipData.class, String.class);
            } else {
                this.c = this.a.getClass().getMethod("setPrimaryClip", ClipData.class, String.class, Integer.TYPE);
            }
        }
        return this.c;
    }

    private static ClipData a(Method method, IInterface iInterface) throws InvocationTargetException, IllegalAccessException {
        return Build.VERSION.SDK_INT < 29 ? (ClipData) method.invoke(iInterface, "com.android.shell") : (ClipData) method.invoke(iInterface, "com.android.shell", 0);
    }

    private static void a(Method method, IInterface iInterface, ClipData clipData) throws InvocationTargetException, IllegalAccessException {
        if (Build.VERSION.SDK_INT < 29) {
            method.invoke(iInterface, clipData, "com.android.shell");
        } else {
            method.invoke(iInterface, clipData, "com.android.shell", 0);
        }
    }

    public CharSequence getText() {
        try {
            ClipData a = a(a(), this.a);
            if (!(a == null || a.getItemCount() == 0)) {
                return a.getItemAt(0).getText();
            }
            return null;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
            return null;
        }
    }

    public void setText(CharSequence charSequence) {
        try {
            a(b(), this.a, ClipData.newPlainText(null, charSequence));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
        }
    }
}
