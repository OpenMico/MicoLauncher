package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.video.AvcConfig;

/* compiled from: VideoTagPayloadReader.java */
/* loaded from: classes2.dex */
final class c extends TagPayloadReader {
    private final ParsableByteArray b = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private final ParsableByteArray c = new ParsableByteArray(4);
    private int d;
    private boolean e;
    private boolean f;
    private int g;

    public c(TrackOutput trackOutput) {
        super(trackOutput);
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray) throws TagPayloadReader.UnsupportedFormatException {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = (readUnsignedByte >> 4) & 15;
        int i2 = readUnsignedByte & 15;
        if (i2 == 7) {
            this.g = i;
            return i != 5;
        }
        StringBuilder sb = new StringBuilder(39);
        sb.append("Video format not supported: ");
        sb.append(i2);
        throw new TagPayloadReader.UnsupportedFormatException(sb.toString());
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray, long j) throws ParserException {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        long readInt24 = j + (parsableByteArray.readInt24() * 1000);
        if (readUnsignedByte == 0 && !this.e) {
            ParsableByteArray parsableByteArray2 = new ParsableByteArray(new byte[parsableByteArray.bytesLeft()]);
            parsableByteArray.readBytes(parsableByteArray2.getData(), 0, parsableByteArray.bytesLeft());
            AvcConfig parse = AvcConfig.parse(parsableByteArray2);
            this.d = parse.nalUnitLengthFieldLength;
            this.a.format(new Format.Builder().setSampleMimeType(MimeTypes.VIDEO_H264).setCodecs(parse.codecs).setWidth(parse.width).setHeight(parse.height).setPixelWidthHeightRatio(parse.pixelWidthAspectRatio).setInitializationData(parse.initializationData).build());
            this.e = true;
            return false;
        } else if (readUnsignedByte != 1 || !this.e) {
            return false;
        } else {
            int i = this.g == 1 ? 1 : 0;
            if (!this.f && i == 0) {
                return false;
            }
            byte[] data = this.c.getData();
            data[0] = 0;
            data[1] = 0;
            data[2] = 0;
            int i2 = 4 - this.d;
            int i3 = 0;
            while (parsableByteArray.bytesLeft() > 0) {
                parsableByteArray.readBytes(this.c.getData(), i2, this.d);
                this.c.setPosition(0);
                int readUnsignedIntToInt = this.c.readUnsignedIntToInt();
                this.b.setPosition(0);
                this.a.sampleData(this.b, 4);
                this.a.sampleData(parsableByteArray, readUnsignedIntToInt);
                i3 = i3 + 4 + readUnsignedIntToInt;
            }
            this.a.sampleMetadata(readInt24, i, i3, 0, null);
            this.f = true;
            return true;
        }
    }
}
