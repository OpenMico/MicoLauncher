package com.google.android.exoplayer2.source.chunk;

import android.annotation.SuppressLint;
import android.media.MediaParser;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.mediaparser.InputReaderAdapterV30;
import com.google.android.exoplayer2.source.mediaparser.MediaParserUtil;
import com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(30)
/* loaded from: classes2.dex */
public final class MediaParserChunkExtractor implements ChunkExtractor {
    public static final ChunkExtractor.Factory FACTORY = $$Lambda$MediaParserChunkExtractor$HFwVpmYvQPz3jSxDXR5mlVWGYEk.INSTANCE;
    private final OutputConsumerAdapterV30 a;
    private final InputReaderAdapterV30 b = new InputReaderAdapterV30();
    private final MediaParser c;
    private final a d;
    private final DummyTrackOutput e;
    private long f;
    @Nullable
    private ChunkExtractor.TrackOutputProvider g;
    @Nullable
    private Format[] h;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ChunkExtractor a(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
        if (!MimeTypes.isText(format.containerMimeType)) {
            return new MediaParserChunkExtractor(i, format, list);
        }
        Log.w("MediaPrsrChunkExtractor", "Ignoring an unsupported text track.");
        return null;
    }

    @SuppressLint({"WrongConstant"})
    public MediaParserChunkExtractor(int i, Format format, List<Format> list) {
        this.a = new OutputConsumerAdapterV30(format, i, true);
        String str = MimeTypes.isMatroska((String) Assertions.checkNotNull(format.containerMimeType)) ? "android.media.mediaparser.MatroskaParser" : "android.media.mediaparser.FragmentedMp4Parser";
        this.a.setSelectedParserName(str);
        this.c = MediaParser.createByName(str, this.a);
        this.c.setParameter("android.media.mediaparser.matroska.disableCuesSeeking", true);
        this.c.setParameter(MediaParserUtil.PARAMETER_IN_BAND_CRYPTO_INFO, true);
        this.c.setParameter(MediaParserUtil.PARAMETER_INCLUDE_SUPPLEMENTAL_DATA, true);
        this.c.setParameter(MediaParserUtil.PARAMETER_EAGERLY_EXPOSE_TRACK_TYPE, true);
        this.c.setParameter(MediaParserUtil.PARAMETER_EXPOSE_DUMMY_SEEK_MAP, true);
        this.c.setParameter(MediaParserUtil.PARAMETER_EXPOSE_CHUNK_INDEX_AS_MEDIA_FORMAT, true);
        this.c.setParameter(MediaParserUtil.PARAMETER_OVERRIDE_IN_BAND_CAPTION_DECLARATIONS, true);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(MediaParserUtil.toCaptionsMediaFormat(list.get(i2)));
        }
        this.c.setParameter(MediaParserUtil.PARAMETER_EXPOSE_CAPTION_FORMATS, arrayList);
        this.a.setMuxedCaptionFormats(list);
        this.d = new a();
        this.e = new DummyTrackOutput();
        this.f = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void init(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j, long j2) {
        this.g = trackOutputProvider;
        this.a.setSampleTimestampUpperLimitFilterUs(j2);
        this.a.setExtractorOutput(this.d);
        this.f = j;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void release() {
        this.c.release();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public boolean read(ExtractorInput extractorInput) throws IOException {
        a();
        this.b.setDataReader(extractorInput, extractorInput.getLength());
        return this.c.advance(this.b);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    @Nullable
    public ChunkIndex getChunkIndex() {
        return this.a.getChunkIndex();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    @Nullable
    public Format[] getSampleFormats() {
        return this.h;
    }

    private void a() {
        MediaParser.SeekMap dummySeekMap = this.a.getDummySeekMap();
        long j = this.f;
        if (j != C.TIME_UNSET && dummySeekMap != null) {
            this.c.seek((MediaParser.SeekPoint) dummySeekMap.getSeekPoints(j).first);
            this.f = C.TIME_UNSET;
        }
    }

    /* loaded from: classes2.dex */
    private class a implements ExtractorOutput {
        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void seekMap(SeekMap seekMap) {
        }

        private a() {
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public TrackOutput track(int i, int i2) {
            return MediaParserChunkExtractor.this.g != null ? MediaParserChunkExtractor.this.g.track(i, i2) : MediaParserChunkExtractor.this.e;
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void endTracks() {
            MediaParserChunkExtractor mediaParserChunkExtractor = MediaParserChunkExtractor.this;
            mediaParserChunkExtractor.h = mediaParserChunkExtractor.a.getSampleFormats();
        }
    }
}
