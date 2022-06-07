package com.google.android.exoplayer2.source.smoothstreaming.offline;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class SsDownloader extends SegmentDownloader<SsManifest> {
    public SsDownloader(MediaItem mediaItem, CacheDataSource.Factory factory) {
        this(mediaItem, factory, $$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo.INSTANCE);
    }

    public SsDownloader(MediaItem mediaItem, CacheDataSource.Factory factory, Executor executor) {
        this(mediaItem.buildUpon().setUri(Util.fixSmoothStreamingIsmManifestUri(((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties)).uri)).build(), new SsManifestParser(), factory, executor);
    }

    public SsDownloader(MediaItem mediaItem, ParsingLoadable.Parser<SsManifest> parser, CacheDataSource.Factory factory, Executor executor) {
        super(mediaItem, parser, factory, executor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<SegmentDownloader.Segment> getSegments(DataSource dataSource, SsManifest ssManifest, boolean z) {
        ArrayList arrayList = new ArrayList();
        SsManifest.StreamElement[] streamElementArr = ssManifest.streamElements;
        for (SsManifest.StreamElement streamElement : streamElementArr) {
            for (int i = 0; i < streamElement.formats.length; i++) {
                for (int i2 = 0; i2 < streamElement.chunkCount; i2++) {
                    arrayList.add(new SegmentDownloader.Segment(streamElement.getStartTimeUs(i2), new DataSpec(streamElement.buildRequestUri(i, i2))));
                }
            }
        }
        return arrayList;
    }
}
