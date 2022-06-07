package fftlib;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class ByteUtils {
    private static final String a = "ByteUtils";

    public static byte[] toBytes(short[] sArr) {
        int length = sArr.length;
        byte[] bArr = new byte[length << 1];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i2] = (byte) sArr[i];
            bArr[i2 + 1] = (byte) (sArr[i] >> 8);
        }
        return bArr;
    }

    public static byte[] toBytes(float f) {
        int floatToIntBits = Float.floatToIntBits(f);
        byte[] bArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) (floatToIntBits >> (24 - (i * 8)));
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        for (int i2 = 0; i2 < length / 2; i2++) {
            byte b = bArr2[i2];
            int i3 = (length - i2) - 1;
            bArr2[i2] = bArr2[i3];
            bArr2[i3] = b;
        }
        return bArr2;
    }

    public static byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static byte[] toBytes(short s) {
        return new byte[]{(byte) s, (byte) (s >> 8)};
    }

    public static byte[] toBytes(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static byte[] toBytes(String str) {
        return str.getBytes();
    }

    public static byte[] toBytes(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.putLong(0, j);
        return allocate.array();
    }

    public static int toInt(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public static int toInt(byte[] bArr) {
        return toInt(bArr, 0);
    }

    public static long toLong(byte[] bArr) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.put(bArr, 0, bArr.length);
        return allocate.getLong();
    }

    public static short[] toShorts(byte[] bArr) {
        int length = bArr.length >> 1;
        short[] sArr = new short[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            sArr[i] = (short) (((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255));
        }
        return sArr;
    }

    public static byte[] merger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static String toString(byte[] bArr) {
        return Arrays.toString(bArr);
    }

    public static void byte2File(byte[] bArr, File file) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(file.length());
            randomAccessFile.write(bArr);
            randomAccessFile.close();
        } catch (Exception unused) {
        }
    }

    public static byte[] byte2FileForResult(byte[] bArr, File file) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(file.length());
            randomAccessFile.write(bArr);
            randomAccessFile.close();
        } catch (Exception unused) {
        }
        return file2Bytes(file);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x0058: RETURN  (r2 I:byte[]), block:B:54:?
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public static byte[] file2Bytes(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x0058: RETURN  (r2 I:byte[]), block:B:54:?
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r6v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    public static double[] toHardDouble(short[] sArr) {
        double[] dArr = new double[512];
        for (int i = 0; i < 512; i++) {
            dArr[i] = sArr[i];
        }
        return dArr;
    }

    public static byte[] toHardBytes(double[] dArr) {
        byte[] bArr = new byte[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            double d = dArr[i];
            if (d > 127.0d) {
                d = 127.0d;
            }
            bArr[i] = (byte) d;
        }
        return bArr;
    }

    public static short[] toHardShort(double[] dArr) {
        short[] sArr = new short[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            double d = dArr[i];
            if (d > 32767.0d) {
                d = 32767.0d;
            }
            sArr[i] = (short) d;
        }
        return sArr;
    }

    public static byte[] toSoftBytes(double[] dArr) {
        double max = getMax(dArr);
        double d = max > 127.0d ? max / 128.0d : 1.0d;
        byte[] bArr = new byte[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            double d2 = dArr[i] / d;
            if (d2 > 127.0d) {
                d2 = 127.0d;
            }
            bArr[i] = (byte) d2;
        }
        return bArr;
    }

    public static short[] toSoftShorts(double[] dArr) {
        double max = getMax(dArr);
        double d = max > 127.0d ? max / 128.0d : 1.0d;
        short[] sArr = new short[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            double d2 = dArr[i] / d;
            if (d2 > 32767.0d) {
                d2 = 32767.0d;
            }
            sArr[i] = (short) d2;
        }
        return sArr;
    }

    public static double getMax(double[] dArr) {
        double d = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            if (dArr[i] > d) {
                d = dArr[i];
            }
        }
        return d;
    }
}
