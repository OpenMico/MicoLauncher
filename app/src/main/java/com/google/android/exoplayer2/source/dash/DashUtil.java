package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.List;

/* loaded from: classes2.dex */
public final class DashUtil {
    public static DataSpec buildDataSpec(Representation representation, String str, RangedUri rangedUri, int i) {
        return new DataSpec.Builder().setUri(rangedUri.resolveUri(str)).setPosition(rangedUri.start).setLength(rangedUri.length).setKey(resolveCacheKey(representation, rangedUri)).setFlags(i).build();
    }

    public static DataSpec buildDataSpec(Representation representation, RangedUri rangedUri, int i) {
        return buildDataSpec(representation, representation.baseUrls.get(0).url, rangedUri, i);
    }

    public static DashManifest loadManifest(DataSource dataSource, Uri uri) throws IOException {
        return (DashManifest) ParsingLoadable.load(dataSource, new DashManifestParser(), uri, 4);
    }

    @Nullable
    public static Format loadFormatWithDrmInitData(DataSource dataSource, Period period) throws IOException {
        int i = 2;
        Representation a = a(period, 2);
        if (a == null) {
            i = 1;
            a = a(period, 1);
            if (a == null) {
                return null;
            }
        }
        Format format = a.format;
        Format loadSampleFormat = loadSampleFormat(dataSource, i, a);
        return loadSampleFormat == null ? format : loadSampleFormat.withManifestFormatInfo(format);
    }

    /* JADX WARN: Finally extract failed */
    @Nullable
    public static Format loadSampleFormat(DataSource dataSource, int i, Representation representation, int i2) throws IOException {
        if (representation.getInitializationUri() == null) {
            return null;
        }
        ChunkExtractor a = a(i, representation.format);
        try {
            a(a, dataSource, representation, i2, false);
            a.release();
            return ((Format[]) Assertions.checkStateNotNull(a.getSampleFormats()))[0];
        } catch (Throwable th) {
            a.release();
            throw th;
        }
    }

    @Nullable
    public static Format loadSampleFormat(DataSource dataSource, int i, Representation representation) throws IOException {
        return loadSampleFormat(dataSource, i, representation, 0);
    }

    /* JADX WARN: Finally extract failed */
    @Nullable
    public static ChunkIndex loadChunkIndex(DataSource dataSource, int i, Representation representation, int i2) throws IOException {
        if (representation.getInitializationUri() == null) {
            return null;
        }
        ChunkExtractor a = a(i, representation.format);
        try {
            a(a, dataSource, representation, i2, true);
            a.release();
            return a.getChunkIndex();
        } catch (Throwable th) {
            a.release();
            throw th;
        }
    }

    @Nullable
    public static ChunkIndex loadChunkIndex(DataSource dataSource, int i, Representation representation) throws IOException {
        return loadChunkIndex(dataSource, i, representation, 0);
    }

    private static void a(ChunkExtractor chunkExtractor, DataSource dataSource, Representation representation, int i, boolean z) throws IOException {
        RangedUri rangedUri = (RangedUri) Assertions.checkNotNull(representation.getInitializationUri());
        if (z) {
            RangedUri indexUri = representation.getIndexUri();
            if (indexUri != null) {
                RangedUri attemptMerge = rangedUri.attemptMerge(indexUri, representation.baseUrls.get(i).url);
                if (attemptMerge == null) {
                    a(dataSource, representation, i, chunkExtractor, rangedUri);
                    rangedUri = indexUri;
                } else {
                    rangedUri = attemptMerge;
                }
            } else {
                return;
            }
        }
        a(dataSource, representation, i, chunkExtractor, rangedUri);
    }

    public static void loadInitializationData(ChunkExtractor chunkExtractor, DataSource dataSource, Representation representation, boolean z) throws IOException {
        a(chunkExtractor, dataSource, representation, 0, z);
    }

    private static void a(DataSource dataSource, Representation representation, int i, ChunkExtractor chunkExtractor, RangedUri rangedUri) throws IOException {
        new InitializationChunk(dataSource, buildDataSpec(representation, representation.baseUrls.get(i).url, rangedUri, 0), representation.format, 0, null, chunkExtractor).load();
    }

    public static String resolveCacheKey(Representation representation, RangedUri rangedUri) {
        String cacheKey = representation.getCacheKey();
        return cacheKey != null ? cacheKey : rangedUri.resolveUri(representation.baseUrls.get(0).url).toString();
    }

    private static ChunkExtractor a(int i, Format format) {
        String str = format.containerMimeType;
        return new BundledChunkExtractor(str != null && (str.startsWith(MimeTypes.VIDEO_WEBM) || str.startsWith(MimeTypes.AUDIO_WEBM)) ? new MatroskaExtractor() : new FragmentedMp4Extractor(), i, format);
    }

    @Nullable
    private static Representation a(Period period, int i) {
        int adaptationSetIndex = period.getAdaptationSetIndex(i);
        if (adaptationSetIndex == -1) {
            return null;
        }
        List<Representation> list = period.adaptationSets.get(adaptationSetIndex).representations;
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private DashUtil() {
    }
}
