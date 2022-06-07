package com.allenliu.versionchecklib.v2.callback;

import androidx.annotation.Nullable;
import com.allenliu.versionchecklib.v2.builder.UIData;

/* loaded from: classes.dex */
public interface RequestVersionListener {
    void onRequestVersionFailure(String str);

    @Nullable
    UIData onRequestVersionSuccess(String str);
}
