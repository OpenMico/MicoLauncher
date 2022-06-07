package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: WebvttSubtitle.java */
/* loaded from: classes2.dex */
final class c implements Subtitle {
    private final List<WebvttCueInfo> a;
    private final long[] b;
    private final long[] c;

    public c(List<WebvttCueInfo> list) {
        this.a = Collections.unmodifiableList(new ArrayList(list));
        this.b = new long[list.size() * 2];
        for (int i = 0; i < list.size(); i++) {
            WebvttCueInfo webvttCueInfo = list.get(i);
            int i2 = i * 2;
            this.b[i2] = webvttCueInfo.startTimeUs;
            this.b[i2 + 1] = webvttCueInfo.endTimeUs;
        }
        long[] jArr = this.b;
        this.c = Arrays.copyOf(jArr, jArr.length);
        Arrays.sort(this.c);
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getNextEventTimeIndex(long j) {
        int binarySearchCeil = Util.binarySearchCeil(this.c, j, false, false);
        if (binarySearchCeil < this.c.length) {
            return binarySearchCeil;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public int getEventTimeCount() {
        return this.c.length;
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public long getEventTime(int i) {
        boolean z = true;
        Assertions.checkArgument(i >= 0);
        if (i >= this.c.length) {
            z = false;
        }
        Assertions.checkArgument(z);
        return this.c[i];
    }

    @Override // com.google.android.exoplayer2.text.Subtitle
    public List<Cue> getCues(long j) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.a.size(); i++) {
            long[] jArr = this.b;
            int i2 = i * 2;
            if (jArr[i2] <= j && j < jArr[i2 + 1]) {
                WebvttCueInfo webvttCueInfo = this.a.get(i);
                if (webvttCueInfo.cue.line == -3.4028235E38f) {
                    arrayList2.add(webvttCueInfo);
                } else {
                    arrayList.add(webvttCueInfo.cue);
                }
            }
        }
        Collections.sort(arrayList2, $$Lambda$c$3A9r7RSxISicy_dpJEWk4Cn0HPU.INSTANCE);
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            arrayList.add(((WebvttCueInfo) arrayList2.get(i3)).cue.buildUpon().setLine((-1) - i3, 1).build());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(WebvttCueInfo webvttCueInfo, WebvttCueInfo webvttCueInfo2) {
        return Long.compare(webvttCueInfo.startTimeUs, webvttCueInfo2.startTimeUs);
    }
}
