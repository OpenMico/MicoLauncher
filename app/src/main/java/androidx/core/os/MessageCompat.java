package androidx.core.os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Message;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public final class MessageCompat {
    private static boolean a = true;
    private static boolean b = true;

    @SuppressLint({"NewApi"})
    public static void setAsynchronous(@NonNull Message message, boolean z) {
        if (Build.VERSION.SDK_INT >= 22) {
            message.setAsynchronous(z);
        } else if (a && Build.VERSION.SDK_INT >= 16) {
            try {
                message.setAsynchronous(z);
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean isAsynchronous(@NonNull Message message) {
        if (Build.VERSION.SDK_INT >= 22) {
            return message.isAsynchronous();
        }
        if (b && Build.VERSION.SDK_INT >= 16) {
            try {
                return message.isAsynchronous();
            } catch (NoSuchMethodError unused) {
                b = false;
            }
        }
        return false;
    }

    private MessageCompat() {
    }
}
