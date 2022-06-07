package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.util.Consumer;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.drm.-$$Lambda$BrYRYnbSvqr_udlxRiVssV28H70  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$BrYRYnbSvqr_udlxRiVssV28H70 implements Consumer {
    public static final /* synthetic */ $$Lambda$BrYRYnbSvqr_udlxRiVssV28H70 INSTANCE = new $$Lambda$BrYRYnbSvqr_udlxRiVssV28H70();

    private /* synthetic */ $$Lambda$BrYRYnbSvqr_udlxRiVssV28H70() {
    }

    @Override // com.google.android.exoplayer2.util.Consumer
    public final void accept(Object obj) {
        ((DrmSessionEventListener.EventDispatcher) obj).drmKeysLoaded();
    }
}
