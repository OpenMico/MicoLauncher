package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmSession;

/* loaded from: classes.dex */
public final class FormatHolder {
    @Nullable
    public DrmSession drmSession;
    @Nullable
    public Format format;

    public void clear() {
        this.drmSession = null;
        this.format = null;
    }
}
