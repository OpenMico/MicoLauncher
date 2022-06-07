package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.dash.DashSegmentIndex;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class Representation {
    public static final long REVISION_ID_DEFAULT = -1;
    private final RangedUri a;
    public final ImmutableList<BaseUrl> baseUrls;
    public final Format format;
    public final List<Descriptor> inbandEventStreams;
    public final long presentationTimeOffsetUs;
    public final long revisionId;

    @Nullable
    public abstract String getCacheKey();

    @Nullable
    public abstract DashSegmentIndex getIndex();

    @Nullable
    public abstract RangedUri getIndexUri();

    public static Representation newInstance(long j, Format format, List<BaseUrl> list, SegmentBase segmentBase) {
        return newInstance(j, format, list, segmentBase, null);
    }

    public static Representation newInstance(long j, Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable List<Descriptor> list2) {
        return newInstance(j, format, list, segmentBase, list2, null);
    }

    public static Representation newInstance(long j, Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable List<Descriptor> list2, @Nullable String str) {
        if (segmentBase instanceof SegmentBase.SingleSegmentBase) {
            return new SingleSegmentRepresentation(j, format, list, (SegmentBase.SingleSegmentBase) segmentBase, list2, str, -1L);
        }
        if (segmentBase instanceof SegmentBase.MultiSegmentBase) {
            return new MultiSegmentRepresentation(j, format, list, (SegmentBase.MultiSegmentBase) segmentBase, list2);
        }
        throw new IllegalArgumentException("segmentBase must be of type SingleSegmentBase or MultiSegmentBase");
    }

    private Representation(long j, Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable List<Descriptor> list2) {
        List<Descriptor> list3;
        Assertions.checkArgument(!list.isEmpty());
        this.revisionId = j;
        this.format = format;
        this.baseUrls = ImmutableList.copyOf((Collection) list);
        if (list2 == null) {
            list3 = Collections.emptyList();
        } else {
            list3 = Collections.unmodifiableList(list2);
        }
        this.inbandEventStreams = list3;
        this.a = segmentBase.getInitialization(this);
        this.presentationTimeOffsetUs = segmentBase.getPresentationTimeOffsetUs();
    }

    @Nullable
    public RangedUri getInitializationUri() {
        return this.a;
    }

    /* loaded from: classes2.dex */
    public static class SingleSegmentRepresentation extends Representation {
        @Nullable
        private final String a;
        @Nullable
        private final RangedUri b;
        @Nullable
        private final a c;
        public final long contentLength;
        public final Uri uri;

        public static SingleSegmentRepresentation newInstance(long j, Format format, String str, long j2, long j3, long j4, long j5, List<Descriptor> list, @Nullable String str2, long j6) {
            return new SingleSegmentRepresentation(j, format, ImmutableList.of(new BaseUrl(str)), new SegmentBase.SingleSegmentBase(new RangedUri(null, j2, (j3 - j2) + 1), 1L, 0L, j4, (j5 - j4) + 1), list, str2, j6);
        }

        public SingleSegmentRepresentation(long j, Format format, List<BaseUrl> list, SegmentBase.SingleSegmentBase singleSegmentBase, @Nullable List<Descriptor> list2, @Nullable String str, long j2) {
            super(j, format, list, singleSegmentBase, list2);
            this.uri = Uri.parse(list.get(0).url);
            this.b = singleSegmentBase.getIndex();
            this.a = str;
            this.contentLength = j2;
            this.c = this.b != null ? null : new a(new RangedUri(null, 0L, j2));
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        @Nullable
        public RangedUri getIndexUri() {
            return this.b;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        @Nullable
        public DashSegmentIndex getIndex() {
            return this.c;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        @Nullable
        public String getCacheKey() {
            return this.a;
        }
    }

    /* loaded from: classes2.dex */
    public static class MultiSegmentRepresentation extends Representation implements DashSegmentIndex {
        @VisibleForTesting
        final SegmentBase.MultiSegmentBase a;

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        @Nullable
        public String getCacheKey() {
            return null;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        public DashSegmentIndex getIndex() {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.Representation
        @Nullable
        public RangedUri getIndexUri() {
            return null;
        }

        public MultiSegmentRepresentation(long j, Format format, List<BaseUrl> list, SegmentBase.MultiSegmentBase multiSegmentBase, @Nullable List<Descriptor> list2) {
            super(j, format, list, multiSegmentBase, list2);
            this.a = multiSegmentBase;
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public RangedUri getSegmentUrl(long j) {
            return this.a.getSegmentUrl(this, j);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getSegmentNum(long j, long j2) {
            return this.a.getSegmentNum(j, j2);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getTimeUs(long j) {
            return this.a.getSegmentTimeUs(j);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getDurationUs(long j, long j2) {
            return this.a.getSegmentDurationUs(j, j2);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getFirstSegmentNum() {
            return this.a.getFirstSegmentNum();
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getFirstAvailableSegmentNum(long j, long j2) {
            return this.a.getFirstAvailableSegmentNum(j, j2);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getSegmentCount(long j) {
            return this.a.getSegmentCount(j);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getAvailableSegmentCount(long j, long j2) {
            return this.a.getAvailableSegmentCount(j, j2);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public long getNextSegmentAvailableTimeUs(long j, long j2) {
            return this.a.getNextSegmentAvailableTimeUs(j, j2);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashSegmentIndex
        public boolean isExplicit() {
            return this.a.isExplicit();
        }
    }
}
