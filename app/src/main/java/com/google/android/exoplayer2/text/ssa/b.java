package com.google.android.exoplayer2.text.ssa;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* compiled from: SsaSubtitle.java */
/* loaded from: classes2.dex */
final class b implements Subtitle {
    private final List<List<Cue>> a;
    private final List<Long> b;

    public b(List<List<Cue>> list, List<Long> list2) {
        this.a = list;
        this.b = list2;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long j) {
        int binarySearchCeil = Util.binarySearchCeil((List<? extends Comparable<? super Long>>) this.b, Long.valueOf(j), false, false);
        if (binarySearchCeil < this.b.size()) {
            return binarySearchCeil;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return this.b.size();
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int i) {
        boolean z = true;
        Assertions.checkArgument(i >= 0);
        if (i >= this.b.size()) {
            z = false;
        }
        Assertions.checkArgument(z);
        return this.b.get(i).longValue();
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j) {
        int binarySearchFloor = Util.binarySearchFloor((List<? extends Comparable<? super Long>>) this.b, Long.valueOf(j), true, false);
        if (binarySearchFloor == -1) {
            return Collections.emptyList();
        }
        return this.a.get(binarySearchFloor);
    }
}
