package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;

/* compiled from: AudioTagPayloadReader.java */
/* loaded from: classes2.dex */
final class a extends TagPayloadReader {
    private static final int[] b = {5512, 11025, 22050, 44100};
    private boolean c;
    private boolean d;
    private int e;

    public a(TrackOutput trackOutput) {
        super(trackOutput);
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray) throws TagPayloadReader.UnsupportedFormatException {
        if (!this.c) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            this.e = (readUnsignedByte >> 4) & 15;
            int i = this.e;
            if (i == 2) {
                this.a.format(new Format.Builder().setSampleMimeType("audio/mpeg").setChannelCount(1).setSampleRate(b[(readUnsignedByte >> 2) & 3]).build());
                this.d = true;
            } else if (i == 7 || i == 8) {
                this.a.format(new Format.Builder().setSampleMimeType(this.e == 7 ? MimeTypes.AUDIO_ALAW : MimeTypes.AUDIO_MLAW).setChannelCount(1).setSampleRate(8000).build());
                this.d = true;
            } else if (i != 10) {
                StringBuilder sb = new StringBuilder(39);
                sb.append("Audio format not supported: ");
                sb.append(i);
                throw new TagPayloadReader.UnsupportedFormatException(sb.toString());
            }
            this.c = true;
        } else {
            parsableByteArray.skipBytes(1);
        }
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray, long j) throws ParserException {
        if (this.e == 2) {
            int bytesLeft = parsableByteArray.bytesLeft();
            this.a.sampleData(parsableByteArray, bytesLeft);
            this.a.sampleMetadata(j, 1, bytesLeft, 0, null);
            return true;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if (readUnsignedByte == 0 && !this.d) {
            byte[] bArr = new byte[parsableByteArray.bytesLeft()];
            parsableByteArray.readBytes(bArr, 0, bArr.length);
            AacUtil.Config parseAudioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArr);
            this.a.format(new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_AAC).setCodecs(parseAudioSpecificConfig.codecs).setChannelCount(parseAudioSpecificConfig.channelCount).setSampleRate(parseAudioSpecificConfig.sampleRateHz).setInitializationData(Collections.singletonList(bArr)).build());
            this.d = true;
            return false;
        } else if (this.e == 10 && readUnsignedByte != 1) {
            return false;
        } else {
            int bytesLeft2 = parsableByteArray.bytesLeft();
            this.a.sampleData(parsableByteArray, bytesLeft2);
            this.a.sampleMetadata(j, 1, bytesLeft2, 0, null);
            return true;
        }
    }
}
