package com.milink.runtime.provider;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.milink.base.utils.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/* compiled from: CallMethodHandler.java */
/* loaded from: classes2.dex */
class a {
    @NonNull
    private final Method a;
    private final String[] b;
    private final String c;
    private Pattern d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull Method method, String[] strArr, String str) {
        this.a = method;
        this.b = strArr != null ? (String[]) Arrays.copyOf(strArr, strArr.length, String[].class) : null;
        this.c = str;
        String[] strArr2 = this.b;
        if (strArr2 != null) {
            Arrays.sort(strArr2);
        }
        this.a.setAccessible(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(@NonNull String str) {
        String[] strArr = this.b;
        if (strArr != null && Arrays.binarySearch(strArr, str) >= 0) {
            return true;
        }
        if (!TextUtils.isEmpty(this.c)) {
            this.d = Pattern.compile(this.c);
        }
        Pattern pattern = this.d;
        if (pattern != null) {
            return pattern.matcher(str).find();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bundle a(@NonNull Object obj, @NonNull String str, String str2, Bundle bundle) throws DataProviderException {
        try {
            Method method = this.a;
            Object requireNonNull = Objects.requireNonNull(obj);
            Object[] objArr = new Object[3];
            objArr[0] = str;
            objArr[1] = str2;
            objArr[2] = bundle != null ? new Bundle(bundle) : null;
            Object invoke = method.invoke(requireNonNull, objArr);
            return invoke instanceof Bundle ? (Bundle) invoke : Bundle.EMPTY;
        } catch (IllegalAccessException unused) {
            Logger.w("CallMethodHandler", "fail access method", new Object[0]);
            throw new UnsupportedOperationException(str);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof DataProviderException) {
                throw ((DataProviderException) e.getCause());
            }
            Logger.w("CallMethodHandler", "fail invocation method %s", str);
            throw new UnsupportedOperationException(str);
        }
    }
}
