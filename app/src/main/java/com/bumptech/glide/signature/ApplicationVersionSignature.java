package com.bumptech.glide.signature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public final class ApplicationVersionSignature {
    private static final ConcurrentMap<String, Key> a = new ConcurrentHashMap();

    @NonNull
    public static Key obtain(@NonNull Context context) {
        String packageName = context.getPackageName();
        Key key = a.get(packageName);
        if (key != null) {
            return key;
        }
        Key a2 = a(context);
        Key putIfAbsent = a.putIfAbsent(packageName, a2);
        return putIfAbsent == null ? a2 : putIfAbsent;
    }

    @NonNull
    private static Key a(@NonNull Context context) {
        return new ObjectKey(a(b(context)));
    }

    @NonNull
    private static String a(@Nullable PackageInfo packageInfo) {
        if (packageInfo != null) {
            return String.valueOf(packageInfo.versionCode);
        }
        return UUID.randomUUID().toString();
    }

    @Nullable
    private static PackageInfo b(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AppVersionSignature", "Cannot resolve info for" + context.getPackageName(), e);
            return null;
        }
    }

    private ApplicationVersionSignature() {
    }
}
