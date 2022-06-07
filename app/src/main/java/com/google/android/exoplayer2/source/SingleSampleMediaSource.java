package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;

/* loaded from: classes2.dex */
public final class SingleSampleMediaSource extends BaseMediaSource {
    private final DataSpec a;
    private final DataSource.Factory b;
    private final Format c;
    private final long d;
    private final LoadErrorHandlingPolicy e;
    private final boolean f;
    private final Timeline g;
    private final MediaItem h;
    @Nullable
    private TransferListener i;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
    }

    /* loaded from: classes2.dex */
    public static final class Factory {
        private final DataSource.Factory a;
        private LoadErrorHandlingPolicy b = new DefaultLoadErrorHandlingPolicy();
        private boolean c = true;
        @Nullable
        private Object d;
        @Nullable
        private String e;

        public Factory(DataSource.Factory factory) {
            this.a = (DataSource.Factory) Assertions.checkNotNull(factory);
        }

        public Factory setTag(@Nullable Object obj) {
            this.d = obj;
            return this;
        }

        public Factory setTrackId(@Nullable String str) {
            this.e = str;
            return this;
        }

        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.b = loadErrorHandlingPolicy;
            return this;
        }

        public Factory setTreatLoadErrorsAsEndOfStream(boolean z) {
            this.c = z;
            return this;
        }

        public SingleSampleMediaSource createMediaSource(MediaItem.Subtitle subtitle, long j) {
            return new SingleSampleMediaSource(this.e, subtitle, this.a, j, this.b, this.c, this.d);
        }

        @Deprecated
        public SingleSampleMediaSource createMediaSource(Uri uri, Format format, long j) {
            return new SingleSampleMediaSource(format.id == null ? this.e : format.id, new MediaItem.Subtitle(uri, (String) Assertions.checkNotNull(format.sampleMimeType), format.language, format.selectionFlags), this.a, j, this.b, this.c, this.d);
        }
    }

    private SingleSampleMediaSource(@Nullable String str, MediaItem.Subtitle subtitle, DataSource.Factory factory, long j, LoadErrorHandlingPolicy loadErrorHandlingPolicy, boolean z, @Nullable Object obj) {
        this.b = factory;
        this.d = j;
        this.e = loadErrorHandlingPolicy;
        this.f = z;
        this.h = new MediaItem.Builder().setUri(Uri.EMPTY).setMediaId(subtitle.uri.toString()).setSubtitles(Collections.singletonList(subtitle)).setTag(obj).build();
        this.c = new Format.Builder().setId(str).setSampleMimeType(subtitle.mimeType).setLanguage(subtitle.language).setSelectionFlags(subtitle.selectionFlags).setRoleFlags(subtitle.roleFlags).setLabel(subtitle.label).build();
        this.a = new DataSpec.Builder().setUri(subtitle.uri).setFlags(1).build();
        this.g = new SinglePeriodTimeline(j, true, false, false, (Object) null, this.h);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.h;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.i = transferListener;
        refreshSourceInfo(this.g);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        return new d(this.a, this.b, this.i, this.c, this.d, this.e, createEventDispatcher(mediaPeriodId), this.f);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((d) mediaPeriod).a();
    }
}
