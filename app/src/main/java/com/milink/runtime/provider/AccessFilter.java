package com.milink.runtime.provider;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public interface AccessFilter {
    int onAcceptAccess(Context context, @NonNull Uri uri, int i);
}
