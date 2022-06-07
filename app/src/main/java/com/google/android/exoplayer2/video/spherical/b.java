package com.google.android.exoplayer2.video.spherical;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.spherical.Projection;
import java.util.ArrayList;
import java.util.zip.Inflater;

/* compiled from: ProjectionDecoder.java */
/* loaded from: classes2.dex */
final class b {
    private static int a(int i) {
        return (-(i & 1)) ^ (i >> 1);
    }

    @Nullable
    public static Projection a(byte[] bArr, int i) {
        ArrayList<Projection.Mesh> arrayList;
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        try {
            arrayList = a(parsableByteArray) ? b(parsableByteArray) : c(parsableByteArray);
        } catch (ArrayIndexOutOfBoundsException unused) {
            arrayList = null;
        }
        if (arrayList == null) {
            return null;
        }
        switch (arrayList.size()) {
            case 1:
                return new Projection(arrayList.get(0), i);
            case 2:
                return new Projection(arrayList.get(0), arrayList.get(1), i);
            default:
                return null;
        }
    }

    private static boolean a(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(4);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.setPosition(0);
        return readInt == 1886547818;
    }

    @Nullable
    private static ArrayList<Projection.Mesh> b(ParsableByteArray parsableByteArray) {
        int readInt;
        parsableByteArray.skipBytes(8);
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit && (readInt = parsableByteArray.readInt() + position) > position && readInt <= limit) {
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 2037673328 || readInt2 == 1836279920) {
                parsableByteArray.setLimit(readInt);
                return c(parsableByteArray);
            }
            parsableByteArray.setPosition(readInt);
            position = readInt;
        }
        return null;
    }

    @Nullable
    private static ArrayList<Projection.Mesh> c(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.readUnsignedByte() != 0) {
            return null;
        }
        parsableByteArray.skipBytes(7);
        int readInt = parsableByteArray.readInt();
        if (readInt == 1684433976) {
            ParsableByteArray parsableByteArray2 = new ParsableByteArray();
            Inflater inflater = new Inflater(true);
            try {
                if (!Util.inflate(parsableByteArray, parsableByteArray2, inflater)) {
                    return null;
                }
                inflater.end();
                parsableByteArray = parsableByteArray2;
            } finally {
                inflater.end();
            }
        } else if (readInt != 1918990112) {
            return null;
        }
        return d(parsableByteArray);
    }

    @Nullable
    private static ArrayList<Projection.Mesh> d(ParsableByteArray parsableByteArray) {
        ArrayList<Projection.Mesh> arrayList = new ArrayList<>();
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit) {
            int readInt = parsableByteArray.readInt() + position;
            if (readInt <= position || readInt > limit) {
                return null;
            }
            if (parsableByteArray.readInt() == 1835365224) {
                Projection.Mesh e = e(parsableByteArray);
                if (e == null) {
                    return null;
                }
                arrayList.add(e);
            }
            parsableByteArray.setPosition(readInt);
            position = readInt;
        }
        return arrayList;
    }

    @Nullable
    private static Projection.Mesh e(ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (readInt > 10000) {
            return null;
        }
        float[] fArr = new float[readInt];
        for (int i = 0; i < readInt; i++) {
            fArr[i] = parsableByteArray.readFloat();
        }
        int readInt2 = parsableByteArray.readInt();
        if (readInt2 > 32000) {
            return null;
        }
        double d = 2.0d;
        double log = Math.log(2.0d);
        int ceil = (int) Math.ceil(Math.log(readInt * 2.0d) / log);
        ParsableBitArray parsableBitArray = new ParsableBitArray(parsableByteArray.getData());
        int i2 = 8;
        parsableBitArray.setPosition(parsableByteArray.getPosition() * 8);
        float[] fArr2 = new float[readInt2 * 5];
        int i3 = 5;
        int[] iArr = new int[5];
        int i4 = 0;
        int i5 = 0;
        while (i4 < readInt2) {
            int i6 = 0;
            while (i6 < i3) {
                int a = iArr[i6] + a(parsableBitArray.readBits(ceil));
                if (a >= readInt || a < 0) {
                    return null;
                }
                i5++;
                fArr2[i5] = fArr[a];
                iArr[i6] = a;
                i6++;
                i3 = 5;
            }
            i4++;
            i3 = 5;
        }
        parsableBitArray.setPosition((parsableBitArray.getPosition() + 7) & (-8));
        int i7 = 32;
        int readBits = parsableBitArray.readBits(32);
        Projection.SubMesh[] subMeshArr = new Projection.SubMesh[readBits];
        int i8 = 0;
        while (i8 < readBits) {
            int readBits2 = parsableBitArray.readBits(i2);
            int readBits3 = parsableBitArray.readBits(i2);
            int readBits4 = parsableBitArray.readBits(i7);
            if (readBits4 > 128000) {
                return null;
            }
            int ceil2 = (int) Math.ceil(Math.log(readInt2 * d) / log);
            float[] fArr3 = new float[readBits4 * 3];
            float[] fArr4 = new float[readBits4 * 2];
            int i9 = 0;
            for (int i10 = 0; i10 < readBits4; i10++) {
                i9 += a(parsableBitArray.readBits(ceil2));
                if (i9 < 0 || i9 >= readInt2) {
                    return null;
                }
                int i11 = i10 * 3;
                int i12 = i9 * 5;
                fArr3[i11] = fArr2[i12];
                fArr3[i11 + 1] = fArr2[i12 + 1];
                fArr3[i11 + 2] = fArr2[i12 + 2];
                int i13 = i10 * 2;
                fArr4[i13] = fArr2[i12 + 3];
                fArr4[i13 + 1] = fArr2[i12 + 4];
            }
            subMeshArr[i8] = new Projection.SubMesh(readBits2, fArr3, fArr4, readBits3);
            i8++;
            i7 = 32;
            d = 2.0d;
            i2 = 8;
        }
        return new Projection.Mesh(subMeshArr);
    }
}
