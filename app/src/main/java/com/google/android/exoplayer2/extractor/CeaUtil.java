package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes2.dex */
public final class CeaUtil {
    public static final int USER_DATA_IDENTIFIER_GA94 = 1195456820;
    public static final int USER_DATA_TYPE_CODE_MPEG_CC = 3;

    public static void consume(long j, ParsableByteArray parsableByteArray, TrackOutput[] trackOutputArr) {
        while (true) {
            boolean z = true;
            if (parsableByteArray.bytesLeft() > 1) {
                int a = a(parsableByteArray);
                int a2 = a(parsableByteArray);
                int position = parsableByteArray.getPosition() + a2;
                if (a2 == -1 || a2 > parsableByteArray.bytesLeft()) {
                    Log.w("CeaUtil", "Skipping remainder of malformed SEI NAL unit.");
                    position = parsableByteArray.limit();
                } else if (a == 4 && a2 >= 8) {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    int readUnsignedShort = parsableByteArray.readUnsignedShort();
                    int readInt = readUnsignedShort == 49 ? parsableByteArray.readInt() : 0;
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (readUnsignedShort == 47) {
                        parsableByteArray.skipBytes(1);
                    }
                    boolean z2 = readUnsignedByte == 181 && (readUnsignedShort == 49 || readUnsignedShort == 47) && readUnsignedByte2 == 3;
                    if (readUnsignedShort == 49) {
                        if (readInt != 1195456820) {
                            z = false;
                        }
                        z2 &= z;
                    }
                    if (z2) {
                        consumeCcData(j, parsableByteArray, trackOutputArr);
                    }
                }
                parsableByteArray.setPosition(position);
            } else {
                return;
            }
        }
    }

    public static void consumeCcData(long j, ParsableByteArray parsableByteArray, TrackOutput[] trackOutputArr) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(1);
            int i = (readUnsignedByte & 31) * 3;
            int position = parsableByteArray.getPosition();
            for (TrackOutput trackOutput : trackOutputArr) {
                parsableByteArray.setPosition(position);
                trackOutput.sampleData(parsableByteArray, i);
                if (j != C.TIME_UNSET) {
                    trackOutput.sampleMetadata(j, 1, i, 0, null);
                }
            }
        }
    }

    private static int a(ParsableByteArray parsableByteArray) {
        int i = 0;
        while (parsableByteArray.bytesLeft() != 0) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            i += readUnsignedByte;
            if (readUnsignedByte != 255) {
                return i;
            }
        }
        return -1;
    }

    private CeaUtil() {
    }
}
