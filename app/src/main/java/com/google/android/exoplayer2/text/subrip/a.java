package com.google.android.exoplayer2.text.subrip;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* compiled from: SubripSubtitle.java */
/* loaded from: classes2.dex */
final class a implements Subtitle {
    private final Cue[] a;
    private final long[] b;

    public a(Cue[] cueArr, long[] jArr) {
        this.a = cueArr;
        this.b = jArr;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long j) {
        int binarySearchCeil = Util.binarySearchCeil(this.b, j, false, false);
        if (binarySearchCeil < this.b.length) {
            return binarySearchCeil;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return this.b.length;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int i) {
        boolean z = true;
        Assertions.checkArgument(i >= 0);
        if (i >= this.b.length) {
            z = false;
        }
        Assertions.checkArgument(z);
        return this.b[i];
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j) {
        int binarySearchFloor = Util.binarySearchFloor(this.b, j, true, false);
        if (binarySearchFloor == -1 || this.a[binarySearchFloor] == Cue.EMPTY) {
            return Collections.emptyList();
        }
        return Collections.singletonList(this.a[binarySearchFloor]);
    }
}
