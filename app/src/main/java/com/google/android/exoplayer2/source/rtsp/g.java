package com.google.android.exoplayer2.source.rtsp;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

/* compiled from: RtspOptionsResponse.java */
/* loaded from: classes2.dex */
final class g {
    public final int a;
    public final ImmutableList<Integer> b;

    public g(int i, List<Integer> list) {
        this.a = i;
        this.b = ImmutableList.copyOf((Collection) list);
    }
}
