package com.google.android.exoplayer2.source.rtsp;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

/* compiled from: RtspPlayResponse.java */
/* loaded from: classes2.dex */
final class h {
    public final int a;
    public final j b;
    public final ImmutableList<l> c;

    public h(int i, j jVar, List<l> list) {
        this.a = i;
        this.b = jVar;
        this.c = ImmutableList.copyOf((Collection) list);
    }
}
