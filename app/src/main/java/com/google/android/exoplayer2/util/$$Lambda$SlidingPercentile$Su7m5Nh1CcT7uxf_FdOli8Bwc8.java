package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.util.SlidingPercentile;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.util.-$$Lambda$SlidingPercentile$Su7m5Nh1CcT7uxf_FdOli8Bw-c8  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$SlidingPercentile$Su7m5Nh1CcT7uxf_FdOli8Bwc8 implements Comparator {
    public static final /* synthetic */ $$Lambda$SlidingPercentile$Su7m5Nh1CcT7uxf_FdOli8Bwc8 INSTANCE = new $$Lambda$SlidingPercentile$Su7m5Nh1CcT7uxf_FdOli8Bwc8();

    private /* synthetic */ $$Lambda$SlidingPercentile$Su7m5Nh1CcT7uxf_FdOli8Bwc8() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int b;
        b = SlidingPercentile.b((SlidingPercentile.a) obj, (SlidingPercentile.a) obj2);
        return b;
    }
}
