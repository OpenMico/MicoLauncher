package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import com.xiaomi.infra.galaxy.fds.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class ConvertUtils {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static double byte2MemorySize(long j, int i) {
        if (j < 0) {
            return -1.0d;
        }
        return j / i;
    }

    public static long memorySize2Byte(long j, int i) {
        if (j < 0) {
            return -1L;
        }
        return j * i;
    }

    public static long timeSpan2Millis(long j, int i) {
        return j * i;
    }

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String int2HexString(int i) {
        return Integer.toHexString(i);
    }

    public static int hexString2Int(String str) {
        return Integer.parseInt(str, 16);
    }

    public static String bytes2Bits(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            for (int i = 7; i >= 0; i--) {
                sb.append(((b2 >> i) & 1) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    public static byte[] bits2Bytes(String str) {
        int length = str.length() % 8;
        int length2 = str.length() / 8;
        if (length != 0) {
            while (length < 8) {
                str = "0" + str;
                length++;
            }
            length2++;
        }
        byte[] bArr = new byte[length2];
        for (int i = 0; i < length2; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                bArr[i] = (byte) (bArr[i] << 1);
                bArr[i] = (byte) (bArr[i] | (str.charAt((i * 8) + i2) - '0'));
            }
        }
        return bArr;
    }

    public static char[] bytes2Chars(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return null;
        }
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return cArr;
    }

    public static byte[] chars2Bytes(char[] cArr) {
        if (cArr == null || cArr.length <= 0) {
            return null;
        }
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) cArr[i];
        }
        return bArr;
    }

    public static String bytes2HexString(byte[] bArr) {
        return bytes2HexString(bArr, true);
    }

    public static String bytes2HexString(byte[] bArr, boolean z) {
        if (bArr == null) {
            return "";
        }
        char[] cArr = z ? a : b;
        int length = bArr.length;
        if (length <= 0) {
            return "";
        }
        char[] cArr2 = new char[length << 1];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] >> 4) & 15];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return new String(cArr2);
    }

    public static byte[] hexString2Bytes(String str) {
        if (b.o(str)) {
            return new byte[0];
        }
        int length = str.length();
        if (length % 2 != 0) {
            str = "0" + str;
            length++;
        }
        char[] charArray = str.toUpperCase().toCharArray();
        byte[] bArr = new byte[length >> 1];
        for (int i = 0; i < length; i += 2) {
            bArr[i >> 1] = (byte) ((a(charArray[i]) << 4) | a(charArray[i + 1]));
        }
        return bArr;
    }

    private static int a(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - 'A') + 10;
        }
        throw new IllegalArgumentException();
    }

    public static String bytes2String(byte[] bArr) {
        return bytes2String(bArr, "");
    }

    public static String bytes2String(byte[] bArr, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, a(str));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(bArr);
        }
    }

    public static byte[] string2Bytes(String str) {
        return string2Bytes(str, "");
    }

    public static byte[] string2Bytes(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(a(str2));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str.getBytes();
        }
    }

    public static JSONObject bytes2JSONObject(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return new JSONObject(new String(bArr));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] jsonObject2Bytes(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        return jSONObject.toString().getBytes();
    }

    public static JSONArray bytes2JSONArray(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return new JSONArray(new String(bArr));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] jsonArray2Bytes(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        return jSONArray.toString().getBytes();
    }

    public static <T> T bytes2Parcelable(byte[] bArr, Parcelable.Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        T createFromParcel = creator.createFromParcel(obtain);
        obtain.recycle();
        return createFromParcel;
    }

    public static byte[] parcelable2Bytes(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0033 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object bytes2Object(byte[] r3) {
        /*
            r0 = 0
            if (r3 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch: Exception -> 0x001f, all -> 0x001d
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: Exception -> 0x001f, all -> 0x001d
            r2.<init>(r3)     // Catch: Exception -> 0x001f, all -> 0x001d
            r1.<init>(r2)     // Catch: Exception -> 0x001f, all -> 0x001d
            java.lang.Object r3 = r1.readObject()     // Catch: Exception -> 0x001b, all -> 0x002f
            r1.close()     // Catch: IOException -> 0x0016
            goto L_0x001a
        L_0x0016:
            r0 = move-exception
            r0.printStackTrace()
        L_0x001a:
            return r3
        L_0x001b:
            r3 = move-exception
            goto L_0x0021
        L_0x001d:
            r3 = move-exception
            goto L_0x0031
        L_0x001f:
            r3 = move-exception
            r1 = r0
        L_0x0021:
            r3.printStackTrace()     // Catch: all -> 0x002f
            if (r1 == 0) goto L_0x002e
            r1.close()     // Catch: IOException -> 0x002a
            goto L_0x002e
        L_0x002a:
            r3 = move-exception
            r3.printStackTrace()
        L_0x002e:
            return r0
        L_0x002f:
            r3 = move-exception
            r0 = r1
        L_0x0031:
            if (r0 == 0) goto L_0x003b
            r0.close()     // Catch: IOException -> 0x0037
            goto L_0x003b
        L_0x0037:
            r0 = move-exception
            r0.printStackTrace()
        L_0x003b:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ConvertUtils.bytes2Object(byte[]):java.lang.Object");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 1, insn: 0x0033: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:22:0x0033
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public static byte[] serializable2Bytes(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 1, insn: 0x0033: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:22:0x0033
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r3v0 ??
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

    public static Bitmap bytes2Bitmap(byte[] bArr) {
        return b.h(bArr);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        return b.a(bitmap);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i) {
        return b.a(bitmap, compressFormat, i);
    }

    public static Drawable bytes2Drawable(byte[] bArr) {
        return b.i(bArr);
    }

    public static byte[] drawable2Bytes(Drawable drawable) {
        return b.a(drawable);
    }

    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat compressFormat, int i) {
        return b.a(drawable, compressFormat, i);
    }

    @SuppressLint({"DefaultLocale"})
    public static String byte2FitMemorySize(long j) {
        return byte2FitMemorySize(j, 3);
    }

    @SuppressLint({"DefaultLocale"})
    public static String byte2FitMemorySize(long j, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("precision shouldn't be less than zero!");
        } else if (j < 0) {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        } else if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return String.format("%." + i + "fB", Double.valueOf(j));
        } else if (j < 1048576) {
            return String.format("%." + i + "fKB", Double.valueOf(j / 1024.0d));
        } else if (j < Constants.DEFAULT_SPACE_LIMIT) {
            return String.format("%." + i + "fMB", Double.valueOf(j / 1048576.0d));
        } else {
            return String.format("%." + i + "fGB", Double.valueOf(j / 1.073741824E9d));
        }
    }

    public static long millis2TimeSpan(long j, int i) {
        return j / i;
    }

    public static String millis2FitTimeSpan(long j, int i) {
        return b.a(j, i);
    }

    public static ByteArrayOutputStream input2OutputStream(InputStream inputStream) {
        try {
            if (inputStream == null) {
                return null;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = inputStream.read(bArr, 0, 8192);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                inputStream.close();
                return byteArrayOutputStream;
            } catch (IOException e2) {
                e2.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return null;
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

    public static ByteArrayInputStream output2InputStream(OutputStream outputStream) {
        if (outputStream == null) {
            return null;
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
    }

    public static byte[] inputStream2Bytes(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        return input2OutputStream(inputStream).toByteArray();
    }

    public static InputStream bytes2InputStream(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        return new ByteArrayInputStream(bArr);
    }

    public static byte[] outputStream2Bytes(OutputStream outputStream) {
        if (outputStream == null) {
            return null;
        }
        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0030 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.OutputStream bytes2OutputStream(byte[] r2) {
        /*
            r0 = 0
            if (r2 == 0) goto L_0x0039
            int r1 = r2.length
            if (r1 > 0) goto L_0x0007
            goto L_0x0039
        L_0x0007:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: IOException -> 0x001c, all -> 0x001a
            r1.<init>()     // Catch: IOException -> 0x001c, all -> 0x001a
            r1.write(r2)     // Catch: IOException -> 0x0018, all -> 0x002c
            r1.close()     // Catch: IOException -> 0x0013
            goto L_0x0017
        L_0x0013:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0017:
            return r1
        L_0x0018:
            r2 = move-exception
            goto L_0x001e
        L_0x001a:
            r2 = move-exception
            goto L_0x002e
        L_0x001c:
            r2 = move-exception
            r1 = r0
        L_0x001e:
            r2.printStackTrace()     // Catch: all -> 0x002c
            if (r1 == 0) goto L_0x002b
            r1.close()     // Catch: IOException -> 0x0027
            goto L_0x002b
        L_0x0027:
            r2 = move-exception
            r2.printStackTrace()
        L_0x002b:
            return r0
        L_0x002c:
            r2 = move-exception
            r0 = r1
        L_0x002e:
            if (r0 == 0) goto L_0x0038
            r0.close()     // Catch: IOException -> 0x0034
            goto L_0x0038
        L_0x0034:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0038:
            throw r2
        L_0x0039:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ConvertUtils.bytes2OutputStream(byte[]):java.io.OutputStream");
    }

    public static String inputStream2String(InputStream inputStream, String str) {
        if (inputStream == null) {
            return "";
        }
        try {
            ByteArrayOutputStream input2OutputStream = input2OutputStream(inputStream);
            return input2OutputStream == null ? "" : input2OutputStream.toString(a(str));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static InputStream string2InputStream(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return new ByteArrayInputStream(str.getBytes(a(str2)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String outputStream2String(OutputStream outputStream, String str) {
        if (outputStream == null) {
            return "";
        }
        try {
            return new String(outputStream2Bytes(outputStream), a(str));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static OutputStream string2OutputStream(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return bytes2OutputStream(str.getBytes(a(str2)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> inputStream2Lines(InputStream inputStream) {
        return inputStream2Lines(inputStream, "");
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> inputStream2Lines(java.io.InputStream r4, java.lang.String r5) {
        /*
            r0 = 0
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: IOException -> 0x002b, all -> 0x0029
            r1.<init>()     // Catch: IOException -> 0x002b, all -> 0x0029
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: IOException -> 0x002b, all -> 0x0029
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: IOException -> 0x002b, all -> 0x0029
            java.lang.String r5 = a(r5)     // Catch: IOException -> 0x002b, all -> 0x0029
            r3.<init>(r4, r5)     // Catch: IOException -> 0x002b, all -> 0x0029
            r2.<init>(r3)     // Catch: IOException -> 0x002b, all -> 0x0029
        L_0x0014:
            java.lang.String r4 = r2.readLine()     // Catch: IOException -> 0x0027, all -> 0x003b
            if (r4 == 0) goto L_0x001e
            r1.add(r4)     // Catch: IOException -> 0x0027, all -> 0x003b
            goto L_0x0014
        L_0x001e:
            r2.close()     // Catch: IOException -> 0x0022
            goto L_0x0026
        L_0x0022:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0026:
            return r1
        L_0x0027:
            r4 = move-exception
            goto L_0x002d
        L_0x0029:
            r4 = move-exception
            goto L_0x003d
        L_0x002b:
            r4 = move-exception
            r2 = r0
        L_0x002d:
            r4.printStackTrace()     // Catch: all -> 0x003b
            if (r2 == 0) goto L_0x003a
            r2.close()     // Catch: IOException -> 0x0036
            goto L_0x003a
        L_0x0036:
            r4 = move-exception
            r4.printStackTrace()
        L_0x003a:
            return r0
        L_0x003b:
            r4 = move-exception
            r0 = r2
        L_0x003d:
            if (r0 == 0) goto L_0x0047
            r0.close()     // Catch: IOException -> 0x0043
            goto L_0x0047
        L_0x0043:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0047:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ConvertUtils.inputStream2Lines(java.io.InputStream, java.lang.String):java.util.List");
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        return b.b(drawable);
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return b.b(bitmap);
    }

    public static Bitmap view2Bitmap(View view) {
        return b.a(view);
    }

    public static int dp2px(float f) {
        return b.a(f);
    }

    public static int px2dp(float f) {
        return b.b(f);
    }

    public static int sp2px(float f) {
        return b.c(f);
    }

    public static int px2sp(float f) {
        return b.d(f);
    }

    private static String a(String str) {
        return (b.o(str) || !Charset.isSupported(str)) ? "UTF-8" : str;
    }
}
