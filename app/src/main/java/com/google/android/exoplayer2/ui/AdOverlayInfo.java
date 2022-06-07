package com.google.android.exoplayer2.ui;

import android.view.View;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class AdOverlayInfo {
    public static final int PURPOSE_CLOSE_AD = 1;
    public static final int PURPOSE_CONTROLS = 0;
    public static final int PURPOSE_NOT_VISIBLE = 3;
    public static final int PURPOSE_OTHER = 2;
    public final int purpose;
    @Nullable
    public final String reasonDetail;
    public final View view;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Purpose {
    }

    public AdOverlayInfo(View view, int i) {
        this(view, i, null);
    }

    public AdOverlayInfo(View view, int i, @Nullable String str) {
        this.view = view;
        this.purpose = i;
        this.reasonDetail = str;
    }
}
