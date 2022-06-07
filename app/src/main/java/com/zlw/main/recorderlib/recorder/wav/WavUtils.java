package com.zlw.main.recorderlib.recorder.wav;

import com.xiaomi.mipush.sdk.Constants;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.utils.ByteUtils;
import com.zlw.main.recorderlib.utils.FileUtils;
import com.zlw.main.recorderlib.utils.Logger;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes4.dex */
public class WavUtils {
    private static final String a = "WavUtils";

    public static byte[] generateWavFileHeader(int i, int i2, int i3, int i4) {
        return new WavHeader(i, i2, (short) i3, (short) i4).getHeader();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v6 */
    public static void writeHeader(File file, byte[] bArr) {
        Throwable th;
        RandomAccessFile randomAccessFile;
        Exception e;
        RandomAccessFile randomAccessFile2;
        RandomAccessFile isFile = FileUtils.isFile(file);
        if (isFile != 0) {
            try {
                try {
                    randomAccessFile = null;
                    isFile = 0;
                    try {
                        randomAccessFile2 = new RandomAccessFile(file, "rw");
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (IOException e3) {
                    Logger.e(e3, a, e3.getMessage(), new Object[0]);
                    return;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                randomAccessFile2.seek(0L);
                randomAccessFile2.write(bArr);
                randomAccessFile2.close();
                randomAccessFile2.close();
            } catch (Exception e4) {
                e = e4;
                randomAccessFile = randomAccessFile2;
                Logger.e(e, a, e.getMessage(), new Object[0]);
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    isFile = randomAccessFile;
                }
            } catch (Throwable th3) {
                th = th3;
                isFile = randomAccessFile2;
                if (isFile != 0) {
                    try {
                        isFile.close();
                    } catch (IOException e5) {
                        Logger.e(e5, a, e5.getMessage(), new Object[0]);
                    }
                }
                throw th;
            }
        }
    }

    public static void pcmToWav(File file, byte[] bArr) throws IOException {
        if (FileUtils.isFile(file)) {
            String absolutePath = file.getAbsolutePath();
            writeHeader(new File(absolutePath.substring(0, absolutePath.length() - 4) + ".wav"), bArr);
        }
    }

    /*  JADX ERROR: Type inference failed with exception
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [java.lang.String, int], vars: [r8v0 ??, r8v1 ??, r8v6 ??, r8v4 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.rerun(InitCodeVariables.java:39)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.trySplitConstInsns(TypeInferenceVisitor.java:631)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:106)
        */
    private static byte[] a(
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r8v0 ??
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
    /*  JADX ERROR: Type inference failed with exception
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [java.lang.String, int], vars: [r8v0 ??, r8v1 ??, r8v6 ??, r8v4 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.rerun(InitCodeVariables.java:39)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.trySplitConstInsns(TypeInferenceVisitor.java:631)
        */

    public static long getWavDuration(String str) {
        if (!str.endsWith(RecordConfig.RecordFormat.WAV.getExtension())) {
            return -1L;
        }
        return getWavDuration(a(str));
    }

    public static long getWavDuration(byte[] bArr) {
        if (bArr == null || bArr.length < 44) {
            Logger.e(a, "header size有误", new Object[0]);
            return -1L;
        }
        return (ByteUtils.toInt(bArr, 40) * 1000) / ByteUtils.toInt(bArr, 28);
    }

    public static String headerToString(byte[] bArr) {
        int i;
        int i2;
        if (bArr == null || bArr.length < 44) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < 4; i3++) {
            sb.append((char) bArr[i3]);
        }
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(ByteUtils.toInt(bArr, 4));
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        int i4 = 8;
        while (true) {
            if (i4 >= 16) {
                break;
            }
            sb.append((char) bArr[i4]);
            i4++;
        }
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (i = 16; i < 24; i++) {
            sb.append((int) bArr[i]);
        }
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(ByteUtils.toInt(bArr, 24));
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(ByteUtils.toInt(bArr, 28));
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        int i5 = 32;
        while (true) {
            if (i5 >= 36) {
                break;
            }
            sb.append((int) bArr[i5]);
            i5++;
        }
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (i2 = 36; i2 < 40; i2++) {
            sb.append((char) bArr[i2]);
        }
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(ByteUtils.toInt(bArr, 40));
        return sb.toString();
    }

    /* loaded from: classes4.dex */
    public static class WavHeader {
        int b;
        short g;
        int h;
        int i;
        short j;
        short k;
        int m;
        final String a = "RIFF";
        final String c = "WAVE";
        final String d = "fmt ";
        final int e = 16;
        final short f = 1;
        final String l = "data";

        WavHeader(int i, int i2, short s, short s2) {
            this.b = i;
            this.g = s;
            this.h = i2;
            this.i = ((i2 * s2) / 8) * s;
            this.j = (short) ((s * s2) / 8);
            this.k = s2;
            this.m = i - 44;
        }

        public byte[] getHeader() {
            return ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.merger(ByteUtils.toBytes("RIFF"), ByteUtils.toBytes(this.b)), ByteUtils.toBytes("WAVE")), ByteUtils.toBytes("fmt ")), ByteUtils.toBytes(16)), ByteUtils.toBytes((short) 1)), ByteUtils.toBytes(this.g)), ByteUtils.toBytes(this.h)), ByteUtils.toBytes(this.i)), ByteUtils.toBytes(this.j)), ByteUtils.toBytes(this.k)), ByteUtils.toBytes("data")), ByteUtils.toBytes(this.m));
        }
    }
}
