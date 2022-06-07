package com.google.android.exoplayer2.util;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class CodecSpecificDataUtil {
    private static final byte[] a = {0, 0, 0, 1};
    private static final String[] b = {"", "A", "B", HomePageRecordHelper.AREA_C};

    public static Pair<Integer, Integer> parseAlacAudioSpecificConfig(byte[] bArr) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        parsableByteArray.setPosition(9);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.setPosition(20);
        return Pair.create(Integer.valueOf(parsableByteArray.readUnsignedIntToInt()), Integer.valueOf(readUnsignedByte));
    }

    public static List<byte[]> buildCea708InitializationData(boolean z) {
        return Collections.singletonList(z ? new byte[]{1} : new byte[]{0});
    }

    public static boolean parseCea708InitializationData(List<byte[]> list) {
        return list.size() == 1 && list.get(0).length == 1 && list.get(0)[0] == 1;
    }

    public static String buildAvcCodecString(int i, int i2, int i3) {
        return String.format("avc1.%02X%02X%02X", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static String buildHevcCodecStringFromSps(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        parsableNalUnitBitArray.skipBits(24);
        int readBits = parsableNalUnitBitArray.readBits(2);
        boolean readBit = parsableNalUnitBitArray.readBit();
        int readBits2 = parsableNalUnitBitArray.readBits(5);
        int i = 0;
        for (int i2 = 0; i2 < 32; i2++) {
            if (parsableNalUnitBitArray.readBit()) {
                i |= 1 << i2;
            }
        }
        int[] iArr = new int[6];
        for (int i3 = 0; i3 < iArr.length; i3++) {
            iArr[i3] = parsableNalUnitBitArray.readBits(8);
        }
        int readBits3 = parsableNalUnitBitArray.readBits(8);
        Object[] objArr = new Object[5];
        objArr[0] = b[readBits];
        objArr[1] = Integer.valueOf(readBits2);
        objArr[2] = Integer.valueOf(i);
        objArr[3] = Character.valueOf(readBit ? 'H' : 'L');
        objArr[4] = Integer.valueOf(readBits3);
        StringBuilder sb = new StringBuilder(Util.formatInvariant("hvc1.%s%d.%X.%c%d", objArr));
        int length = iArr.length;
        while (length > 0 && iArr[length - 1] == 0) {
            length--;
        }
        for (int i4 = 0; i4 < length; i4++) {
            sb.append(String.format(".%02X", Integer.valueOf(iArr[i4])));
        }
        return sb.toString();
    }

    public static byte[] buildNalUnit(byte[] bArr, int i, int i2) {
        byte[] bArr2 = a;
        byte[] bArr3 = new byte[bArr2.length + i2];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, i, bArr3, a.length, i2);
        return bArr3;
    }

    @Nullable
    public static byte[][] splitNalUnits(byte[] bArr) {
        if (!b(bArr, 0)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        do {
            arrayList.add(Integer.valueOf(i));
            i = a(bArr, i + a.length);
        } while (i != -1);
        byte[][] bArr2 = new byte[arrayList.size()];
        int i2 = 0;
        while (i2 < arrayList.size()) {
            int intValue = ((Integer) arrayList.get(i2)).intValue();
            byte[] bArr3 = new byte[(i2 < arrayList.size() + (-1) ? ((Integer) arrayList.get(i2 + 1)).intValue() : bArr.length) - intValue];
            System.arraycopy(bArr, intValue, bArr3, 0, bArr3.length);
            bArr2[i2] = bArr3;
            i2++;
        }
        return bArr2;
    }

    private static int a(byte[] bArr, int i) {
        int length = bArr.length - a.length;
        while (i <= length) {
            if (b(bArr, i)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private static boolean b(byte[] bArr, int i) {
        if (bArr.length - i <= a.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = a;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i + i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private CodecSpecificDataUtil() {
    }
}
