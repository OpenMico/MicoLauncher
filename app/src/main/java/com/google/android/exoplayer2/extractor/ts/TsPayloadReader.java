package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public interface TsPayloadReader {
    public static final int FLAG_DATA_ALIGNMENT_INDICATOR = 4;
    public static final int FLAG_PAYLOAD_UNIT_START_INDICATOR = 1;
    public static final int FLAG_RANDOM_ACCESS_INDICATOR = 2;

    /* loaded from: classes2.dex */
    public interface Factory {
        SparseArray<TsPayloadReader> createInitialPayloadReaders();

        @Nullable
        TsPayloadReader createPayloadReader(int i, EsInfo esInfo);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    void consume(ParsableByteArray parsableByteArray, int i) throws ParserException;

    void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator);

    void seek();

    /* loaded from: classes2.dex */
    public static final class EsInfo {
        public final byte[] descriptorBytes;
        public final List<DvbSubtitleInfo> dvbSubtitleInfos;
        @Nullable
        public final String language;
        public final int streamType;

        public EsInfo(int i, @Nullable String str, @Nullable List<DvbSubtitleInfo> list, byte[] bArr) {
            List<DvbSubtitleInfo> list2;
            this.streamType = i;
            this.language = str;
            if (list == null) {
                list2 = Collections.emptyList();
            } else {
                list2 = Collections.unmodifiableList(list);
            }
            this.dvbSubtitleInfos = list2;
            this.descriptorBytes = bArr;
        }
    }

    /* loaded from: classes2.dex */
    public static final class DvbSubtitleInfo {
        public final byte[] initializationData;
        public final String language;
        public final int type;

        public DvbSubtitleInfo(String str, int i, byte[] bArr) {
            this.language = str;
            this.type = i;
            this.initializationData = bArr;
        }
    }

    /* loaded from: classes2.dex */
    public static final class TrackIdGenerator {
        private final String a;
        private final int b;
        private final int c;
        private int d;
        private String e;

        public TrackIdGenerator(int i, int i2) {
            this(Integer.MIN_VALUE, i, i2);
        }

        public TrackIdGenerator(int i, int i2, int i3) {
            String str;
            if (i != Integer.MIN_VALUE) {
                StringBuilder sb = new StringBuilder(12);
                sb.append(i);
                sb.append("/");
                str = sb.toString();
            } else {
                str = "";
            }
            this.a = str;
            this.b = i2;
            this.c = i3;
            this.d = Integer.MIN_VALUE;
            this.e = "";
        }

        public void generateNewId() {
            int i = this.d;
            this.d = i == Integer.MIN_VALUE ? this.b : i + this.c;
            String str = this.a;
            int i2 = this.d;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11);
            sb.append(str);
            sb.append(i2);
            this.e = sb.toString();
        }

        public int getTrackId() {
            a();
            return this.d;
        }

        public String getFormatId() {
            a();
            return this.e;
        }

        private void a() {
            if (this.d == Integer.MIN_VALUE) {
                throw new IllegalStateException("generateNewId() must be called before retrieving ids.");
            }
        }
    }
}
