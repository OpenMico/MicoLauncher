package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.SignedBytes;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class DefaultTsPayloadReaderFactory implements TsPayloadReader.Factory {
    public static final int FLAG_ALLOW_NON_IDR_KEYFRAMES = 1;
    public static final int FLAG_DETECT_ACCESS_UNITS = 8;
    public static final int FLAG_ENABLE_HDMV_DTS_AUDIO_STREAMS = 64;
    public static final int FLAG_IGNORE_AAC_STREAM = 2;
    public static final int FLAG_IGNORE_H264_STREAM = 4;
    public static final int FLAG_IGNORE_SPLICE_INFO_STREAM = 16;
    public static final int FLAG_OVERRIDE_CAPTION_DESCRIPTORS = 32;
    private final int a;
    private final List<Format> b;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    public DefaultTsPayloadReaderFactory() {
        this(0);
    }

    public DefaultTsPayloadReaderFactory(int i) {
        this(i, ImmutableList.of());
    }

    public DefaultTsPayloadReaderFactory(int i, List<Format> list) {
        this.a = i;
        this.b = list;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader.Factory
    public SparseArray<TsPayloadReader> createInitialPayloadReaders() {
        return new SparseArray<>();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader.Factory
    @Nullable
    public TsPayloadReader createPayloadReader(int i, TsPayloadReader.EsInfo esInfo) {
        switch (i) {
            case 2:
                return new PesReader(new H262Reader(b(esInfo)));
            case 3:
            case 4:
                return new PesReader(new MpegAudioReader(esInfo.language));
            case 15:
                if (a(2)) {
                    return null;
                }
                return new PesReader(new AdtsReader(false, esInfo.language));
            case 16:
                return new PesReader(new H263Reader(b(esInfo)));
            case 17:
                if (a(2)) {
                    return null;
                }
                return new PesReader(new LatmReader(esInfo.language));
            case 21:
                return new PesReader(new Id3Reader());
            case 27:
                if (a(4)) {
                    return null;
                }
                return new PesReader(new H264Reader(a(esInfo), a(1), a(8)));
            case 36:
                return new PesReader(new H265Reader(a(esInfo)));
            case 89:
                return new PesReader(new DvbSubtitleReader(esInfo.dvbSubtitleInfos));
            case 129:
            case 135:
                return new PesReader(new Ac3Reader(esInfo.language));
            case 130:
                if (!a(64)) {
                    return null;
                }
                break;
            case 134:
                if (a(16)) {
                    return null;
                }
                return new SectionReader(new PassthroughSectionPayloadReader(MimeTypes.APPLICATION_SCTE35));
            case TsExtractor.TS_STREAM_TYPE_DTS /* 138 */:
                break;
            case TsExtractor.TS_STREAM_TYPE_AC4 /* 172 */:
                return new PesReader(new Ac4Reader(esInfo.language));
            case 257:
                return new SectionReader(new PassthroughSectionPayloadReader(MimeTypes.APPLICATION_AIT));
            default:
                return null;
        }
        return new PesReader(new DtsReader(esInfo.language));
    }

    private SeiReader a(TsPayloadReader.EsInfo esInfo) {
        return new SeiReader(c(esInfo));
    }

    private f b(TsPayloadReader.EsInfo esInfo) {
        return new f(c(esInfo));
    }

    private List<Format> c(TsPayloadReader.EsInfo esInfo) {
        String str;
        int i;
        if (a(32)) {
            return this.b;
        }
        ParsableByteArray parsableByteArray = new ParsableByteArray(esInfo.descriptorBytes);
        List<Format> list = this.b;
        while (parsableByteArray.bytesLeft() > 0) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
            if (readUnsignedByte == 134) {
                list = new ArrayList<>();
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
                for (int i2 = 0; i2 < readUnsignedByte2; i2++) {
                    String readString = parsableByteArray.readString(3);
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    boolean z = true;
                    boolean z2 = (readUnsignedByte3 & 128) != 0;
                    if (z2) {
                        str = MimeTypes.APPLICATION_CEA708;
                        i = readUnsignedByte3 & 63;
                    } else {
                        str = MimeTypes.APPLICATION_CEA608;
                        i = 1;
                    }
                    byte readUnsignedByte4 = (byte) parsableByteArray.readUnsignedByte();
                    parsableByteArray.skipBytes(1);
                    List<byte[]> list2 = null;
                    if (z2) {
                        if ((readUnsignedByte4 & SignedBytes.MAX_POWER_OF_TWO) == 0) {
                            z = false;
                        }
                        list2 = CodecSpecificDataUtil.buildCea708InitializationData(z);
                    }
                    list.add(new Format.Builder().setSampleMimeType(str).setLanguage(readString).setAccessibilityChannel(i).setInitializationData(list2).build());
                }
            }
            parsableByteArray.setPosition(position);
        }
        return list;
    }

    private boolean a(int i) {
        return (i & this.a) != 0;
    }
}
