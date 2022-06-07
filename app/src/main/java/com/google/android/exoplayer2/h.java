package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* compiled from: PlaylistTimeline.java */
/* loaded from: classes.dex */
final class h extends AbstractConcatenatedTimeline {
    private final int a;
    private final int b;
    private final int[] c;
    private final int[] d;
    private final Timeline[] e;
    private final Object[] f;
    private final HashMap<Object, Integer> g = new HashMap<>();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public h(Collection<? extends f> collection, ShuffleOrder shuffleOrder) {
        super(false, shuffleOrder);
        int i = 0;
        int size = collection.size();
        this.c = new int[size];
        this.d = new int[size];
        this.e = new Timeline[size];
        this.f = new Object[size];
        int i2 = 0;
        int i3 = 0;
        for (f fVar : collection) {
            this.e[i3] = fVar.b();
            this.d[i3] = i;
            this.c[i3] = i2;
            i += this.e[i3].getWindowCount();
            i2 += this.e[i3].getPeriodCount();
            this.f[i3] = fVar.a();
            HashMap<Object, Integer> hashMap = this.g;
            Object obj = this.f[i3];
            i3++;
            hashMap.put(obj, Integer.valueOf(i3));
        }
        this.a = i;
        this.b = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Timeline> a() {
        return Arrays.asList(this.e);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getChildIndexByPeriodIndex(int i) {
        return Util.binarySearchFloor(this.c, i + 1, false, false);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getChildIndexByWindowIndex(int i) {
        return Util.binarySearchFloor(this.d, i + 1, false, false);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getChildIndexByChildUid(Object obj) {
        Integer num = this.g.get(obj);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected Timeline getTimelineByChildIndex(int i) {
        return this.e[i];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getFirstPeriodIndexByChildIndex(int i) {
        return this.c[i];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected int getFirstWindowIndexByChildIndex(int i) {
        return this.d[i];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    protected Object getChildUidByChildIndex(int i) {
        return this.f[i];
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getWindowCount() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPeriodCount() {
        return this.b;
    }
}
