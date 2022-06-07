package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class HevcConfig {
    @Nullable
    public final String codecs;
    @Nullable
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;

    public static HevcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        try {
            parsableByteArray.skipBytes(21);
            int readUnsignedByte = parsableByteArray.readUnsignedByte() & 3;
            int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition();
            int i = 0;
            int i2 = 0;
            while (i < readUnsignedByte2) {
                parsableByteArray.skipBytes(1);
                int readUnsignedShort = parsableByteArray.readUnsignedShort();
                int i3 = i2;
                for (int i4 = 0; i4 < readUnsignedShort; i4++) {
                    int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                    i3 += readUnsignedShort2 + 4;
                    parsableByteArray.skipBytes(readUnsignedShort2);
                }
                i++;
                i2 = i3;
            }
            parsableByteArray.setPosition(position);
            byte[] bArr = new byte[i2];
            int i5 = 0;
            int i6 = 0;
            String str = null;
            while (i5 < readUnsignedByte2) {
                int readUnsignedByte3 = parsableByteArray.readUnsignedByte() & 127;
                int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
                String str2 = str;
                for (int i7 = 0; i7 < readUnsignedShort3; i7++) {
                    int readUnsignedShort4 = parsableByteArray.readUnsignedShort();
                    System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, bArr, i6, NalUnitUtil.NAL_START_CODE.length);
                    int length = i6 + NalUnitUtil.NAL_START_CODE.length;
                    System.arraycopy(parsableByteArray.getData(), parsableByteArray.getPosition(), bArr, length, readUnsignedShort4);
                    if (readUnsignedByte3 == 33 && i7 == 0) {
                        str2 = CodecSpecificDataUtil.buildHevcCodecStringFromSps(new ParsableNalUnitBitArray(bArr, length, length + readUnsignedShort4));
                    }
                    i6 = length + readUnsignedShort4;
                    parsableByteArray.skipBytes(readUnsignedShort4);
                }
                i5++;
                str = str2;
                i6 = i6;
            }
            return new HevcConfig(i2 == 0 ? null : Collections.singletonList(bArr), readUnsignedByte + 1, str);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw ParserException.createForMalformedContainer("Error parsing HEVC config", e);
        }
    }

    private HevcConfig(@Nullable List<byte[]> list, int i, @Nullable String str) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i;
        this.codecs = str;
    }
}
