package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes2.dex */
public final class DolbyVisionConfig {
    public final String codecs;
    public final int level;
    public final int profile;

    @Nullable
    public static DolbyVisionConfig parse(ParsableByteArray parsableByteArray) {
        String str;
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte >> 1;
        int readUnsignedByte2 = ((parsableByteArray.readUnsignedByte() >> 3) & 31) | ((readUnsignedByte & 1) << 5);
        if (i == 4 || i == 5 || i == 7) {
            str = "dvhe";
        } else if (i == 8) {
            str = "hev1";
        } else if (i != 9) {
            return null;
        } else {
            str = "avc3";
        }
        String str2 = readUnsignedByte2 < 10 ? ".0" : ".";
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 24 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(".0");
        sb.append(i);
        sb.append(str2);
        sb.append(readUnsignedByte2);
        return new DolbyVisionConfig(i, readUnsignedByte2, sb.toString());
    }

    private DolbyVisionConfig(int i, int i2, String str) {
        this.profile = i;
        this.level = i2;
        this.codecs = str;
    }
}
