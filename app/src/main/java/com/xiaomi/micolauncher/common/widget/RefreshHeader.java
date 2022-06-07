package com.xiaomi.micolauncher.common.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public interface RefreshHeader {
    @NonNull
    View getView(ViewGroup viewGroup);

    void onCancel();

    void onDragging(float f, float f2, View view);

    void onReadyToRelease(View view);

    void onRefreshing(View view);
}
