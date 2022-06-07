package com.google.android.exoplayer2.source;

import android.annotation.SuppressLint;
import android.media.MediaParser;
import android.net.Uri;
import android.util.Pair;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.mediaparser.InputReaderAdapterV30;
import com.google.android.exoplayer2.source.mediaparser.MediaParserUtil;
import com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30;
import com.google.android.exoplayer2.upstream.DataReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiresApi(30)
/* loaded from: classes2.dex */
public final class MediaParserExtractorAdapter implements ProgressiveMediaExtractor {
    public static final ProgressiveMediaExtractor.Factory FACTORY = $$Lambda$kK1JHkwGcOZQwzkZeIwJrbU9SDs.INSTANCE;
    private final OutputConsumerAdapterV30 a = new OutputConsumerAdapterV30();
    private final InputReaderAdapterV30 b = new InputReaderAdapterV30();
    private final MediaParser c = MediaParser.create(this.a, new String[0]);
    private String d = "android.media.mediaparser.UNKNOWN";

    @SuppressLint({"WrongConstant"})
    public MediaParserExtractorAdapter() {
        this.c.setParameter(MediaParserUtil.PARAMETER_EAGERLY_EXPOSE_TRACK_TYPE, true);
        this.c.setParameter(MediaParserUtil.PARAMETER_IN_BAND_CRYPTO_INFO, true);
        this.c.setParameter(MediaParserUtil.PARAMETER_INCLUDE_SUPPLEMENTAL_DATA, true);
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void init(DataReader dataReader, Uri uri, Map<String, List<String>> map, long j, long j2, ExtractorOutput extractorOutput) throws IOException {
        this.a.setExtractorOutput(extractorOutput);
        this.b.setDataReader(dataReader, j2);
        this.b.setCurrentPosition(j);
        String parserName = this.c.getParserName();
        if ("android.media.mediaparser.UNKNOWN".equals(parserName)) {
            this.c.advance(this.b);
            this.d = this.c.getParserName();
            this.a.setSelectedParserName(this.d);
        } else if (!parserName.equals(this.d)) {
            this.d = this.c.getParserName();
            this.a.setSelectedParserName(this.d);
        }
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void release() {
        this.c.release();
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void disableSeekingOnMp3Streams() {
        if ("android.media.mediaparser.Mp3Parser".equals(this.d)) {
            this.a.disableSeeking();
        }
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public long getCurrentInputPosition() {
        return this.b.getPosition();
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void seek(long j, long j2) {
        this.b.setCurrentPosition(j);
        Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> seekPoints = this.a.getSeekPoints(j2);
        this.c.seek((MediaParser.SeekPoint) (((MediaParser.SeekPoint) seekPoints.second).position == j ? seekPoints.second : seekPoints.first));
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public int read(PositionHolder positionHolder) throws IOException {
        boolean advance = this.c.advance(this.b);
        positionHolder.position = this.b.getAndResetSeekPosition();
        if (!advance) {
            return -1;
        }
        return positionHolder.position != -1 ? 1 : 0;
    }
}
