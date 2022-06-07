package com.google.android.exoplayer2.text.ttml;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: TtmlSubtitle.java */
/* loaded from: classes2.dex */
final class e implements Subtitle {
    private final b a;
    private final long[] b;
    private final Map<String, TtmlStyle> c;
    private final Map<String, c> d;
    private final Map<String, String> e;

    public e(b bVar, Map<String, TtmlStyle> map, Map<String, c> map2, Map<String, String> map3) {
        this.a = bVar;
        this.d = map2;
        this.e = map3;
        this.c = map != null ? Collections.unmodifiableMap(map) : Collections.emptyMap();
        this.b = bVar.b();
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
        return this.b[i];
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j) {
        return this.a.a(j, this.c, this.d, this.e);
    }
}
