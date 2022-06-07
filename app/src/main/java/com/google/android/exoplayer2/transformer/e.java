package com.google.android.exoplayer2.transformer;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: SegmentSpeedProvider.java */
/* loaded from: classes2.dex */
class e implements f {
    private final ImmutableSortedMap<Long, Float> a;
    private final float b;

    public e(Format format) {
        float a = a(format);
        this.b = a == -3.4028235E38f ? 1.0f : a / 30.0f;
        this.a = a(format, this.b);
    }

    @Override // com.google.android.exoplayer2.transformer.f
    public float a(long j) {
        Assertions.checkArgument(j >= 0);
        Map.Entry<Long, Float> floorEntry = this.a.floorEntry(Long.valueOf(j));
        return floorEntry != null ? floorEntry.getValue().floatValue() : this.b;
    }

    private static ImmutableSortedMap<Long, Float> a(Format format, float f) {
        ImmutableList<SlowMotionData.Segment> b = b(format);
        if (b.isEmpty()) {
            return ImmutableSortedMap.of();
        }
        TreeMap treeMap = new TreeMap();
        for (int i = 0; i < b.size(); i++) {
            SlowMotionData.Segment segment = b.get(i);
            treeMap.put(Long.valueOf(C.msToUs(segment.startTimeMs)), Float.valueOf(f / segment.speedDivisor));
        }
        for (int i2 = 0; i2 < b.size(); i2++) {
            SlowMotionData.Segment segment2 = b.get(i2);
            if (!treeMap.containsKey(Long.valueOf(C.msToUs(segment2.endTimeMs)))) {
                treeMap.put(Long.valueOf(C.msToUs(segment2.endTimeMs)), Float.valueOf(f));
            }
        }
        return ImmutableSortedMap.copyOf((Map) treeMap);
    }

    private static float a(Format format) {
        Metadata metadata = format.metadata;
        if (metadata == null) {
            return -3.4028235E38f;
        }
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof SmtaMetadataEntry) {
                return ((SmtaMetadataEntry) entry).captureFrameRate;
            }
        }
        return -3.4028235E38f;
    }

    private static ImmutableList<SlowMotionData.Segment> b(Format format) {
        ArrayList arrayList = new ArrayList();
        Metadata metadata = format.metadata;
        if (metadata != null) {
            for (int i = 0; i < metadata.length(); i++) {
                Metadata.Entry entry = metadata.get(i);
                if (entry instanceof SlowMotionData) {
                    arrayList.addAll(((SlowMotionData) entry).segments);
                }
            }
        }
        return ImmutableList.sortedCopyOf(SlowMotionData.Segment.BY_START_THEN_END_THEN_DIVISOR, arrayList);
    }
}
