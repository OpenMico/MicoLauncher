package com.xiaomi.mico.base.utils;

import androidx.core.view.MotionEventCompat;
import com.google.common.base.Ascii;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import okio.Utf8;

/* loaded from: classes3.dex */
public class Base64Coder {
    public static final int DECODE = 0;
    public static final int DONT_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    public static final int GZIP = 2;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    public static final int URL_SAFE = 16;
    private static final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] b = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, Utf8.REPLACEMENT_BYTE, 52, 53, 54, 55, 56, 57, 58, 59, 60, HttpConstants.EQUALS, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
    private static final byte[] c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] d = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, HttpConstants.EQUALS, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, Utf8.REPLACEMENT_BYTE, -9, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
    private static final byte[] e = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] f = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, HttpConstants.EQUALS, 62, Utf8.REPLACEMENT_BYTE, -9, -9, -9, -9};

    /* JADX INFO: Access modifiers changed from: private */
    public static final byte[] c(int i) {
        if ((i & 16) == 16) {
            return c;
        }
        if ((i & 32) == 32) {
            return e;
        }
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final byte[] d(int i) {
        if ((i & 16) == 16) {
            return d;
        }
        if ((i & 32) == 32) {
            return f;
        }
        return b;
    }

    private Base64Coder() {
    }

    public static final void main(String[] strArr) {
        if (strArr.length < 3) {
            a("Not enough arguments.");
            return;
        }
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        if (str.equals("-e")) {
            encodeFileToFile(str2, str3);
        } else if (str.equals("-d")) {
            decodeFileToFile(str2, str3);
        } else {
            a("Unknown flag: " + str);
        }
    }

    private static final void a(String str) {
        System.err.println(str);
        System.err.println("Usage: java Base64Coder -e|-d inputfile outputfile");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] b(byte[] bArr, byte[] bArr2, int i, int i2) {
        b(bArr2, 0, i, bArr, 0, i2);
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] b(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        byte[] c2 = c(i4);
        int i5 = 0;
        int i6 = (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0) | (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0);
        if (i2 > 2) {
            i5 = (bArr[i + 2] << 24) >>> 24;
        }
        int i7 = i6 | i5;
        switch (i2) {
            case 1:
                bArr2[i3] = c2[i7 >>> 18];
                bArr2[i3 + 1] = c2[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = HttpConstants.EQUALS;
                bArr2[i3 + 3] = HttpConstants.EQUALS;
                return bArr2;
            case 2:
                bArr2[i3] = c2[i7 >>> 18];
                bArr2[i3 + 1] = c2[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = c2[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = HttpConstants.EQUALS;
                return bArr2;
            case 3:
                bArr2[i3] = c2[i7 >>> 18];
                bArr2[i3 + 1] = c2[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = c2[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = c2[i7 & 63];
                return bArr2;
            default:
                return bArr2;
        }
    }

    public static String encodeObject(Serializable serializable) {
        return encodeObject(serializable, 0);
    }

    public static String encodeObject(Serializable serializable, int i) {
        GZIPOutputStream gZIPOutputStream;
        Throwable th;
        OutputStream outputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        IOException e2;
        int i2 = i & 2;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                outputStream = new OutputStream(byteArrayOutputStream, i | 1);
                try {
                    if (i2 == 2) {
                        gZIPOutputStream = new GZIPOutputStream(outputStream);
                        try {
                            gZIPOutputStream = gZIPOutputStream;
                            objectOutputStream = new ObjectOutputStream(gZIPOutputStream);
                        } catch (IOException e3) {
                            e2 = e3;
                            gZIPOutputStream = gZIPOutputStream;
                            objectOutputStream = null;
                            e2.printStackTrace();
                            try {
                                objectOutputStream.close();
                            } catch (Exception unused) {
                            }
                            try {
                                gZIPOutputStream.close();
                            } catch (Exception unused2) {
                            }
                            try {
                                outputStream.close();
                            } catch (Exception unused3) {
                            }
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused4) {
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                objectOutputStream.close();
                            } catch (Exception unused5) {
                            }
                            try {
                                gZIPOutputStream.close();
                            } catch (Exception unused6) {
                            }
                            try {
                                outputStream.close();
                            } catch (Exception unused7) {
                            }
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused8) {
                            }
                            throw th;
                        }
                    } else {
                        objectOutputStream = new ObjectOutputStream(outputStream);
                        gZIPOutputStream = null;
                    }
                    try {
                        try {
                            objectOutputStream.writeObject(serializable);
                            try {
                                objectOutputStream.close();
                            } catch (Exception unused9) {
                            }
                            try {
                                gZIPOutputStream.close();
                            } catch (Exception unused10) {
                            }
                            try {
                                outputStream.close();
                            } catch (Exception unused11) {
                            }
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused12) {
                            }
                            try {
                                return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                            } catch (UnsupportedEncodingException unused13) {
                                return new String(byteArrayOutputStream.toByteArray());
                            }
                        } catch (IOException e4) {
                            e2 = e4;
                            e2.printStackTrace();
                            objectOutputStream.close();
                            gZIPOutputStream.close();
                            outputStream.close();
                            byteArrayOutputStream.close();
                            return null;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        objectOutputStream.close();
                        gZIPOutputStream.close();
                        outputStream.close();
                        byteArrayOutputStream.close();
                        throw th;
                    }
                } catch (IOException e5) {
                    e2 = e5;
                    objectOutputStream = null;
                    gZIPOutputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    gZIPOutputStream = null;
                }
            } catch (IOException e6) {
                e2 = e6;
                objectOutputStream = null;
                gZIPOutputStream = null;
                outputStream = null;
            } catch (Throwable th5) {
                th = th5;
                gZIPOutputStream = null;
                outputStream = null;
            }
        } catch (IOException e7) {
            e2 = e7;
            objectOutputStream = null;
            gZIPOutputStream = null;
            byteArrayOutputStream = null;
            outputStream = null;
        } catch (Throwable th6) {
            th = th6;
            gZIPOutputStream = null;
            byteArrayOutputStream = null;
            outputStream = null;
        }
    }

    public static String encode(byte[] bArr) {
        return encodeBytes(bArr);
    }

    public static String encodeBytes(byte[] bArr) {
        return encodeBytes(bArr, 0, bArr.length, 8);
    }

    public static String encodeBytes(byte[] bArr, int i) {
        return encodeBytes(bArr, 0, bArr.length, i);
    }

    public static String encodeBytes(byte[] bArr, int i, int i2) {
        return encodeBytes(bArr, i, i2, 8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.io.OutputStream, java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.xiaomi.mico.base.utils.Base64Coder$OutputStream] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.xiaomi.mico.base.utils.Base64Coder$OutputStream] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.xiaomi.mico.base.utils.Base64Coder$OutputStream, java.io.OutputStream] */
    /* JADX WARN: Unknown variable types count: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String encodeBytes(byte[] r18, int r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mico.base.utils.Base64Coder.encodeBytes(byte[], int, int, int):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        byte[] d2 = d(i3);
        int i4 = i + 2;
        if (bArr[i4] == 61) {
            bArr2[i2] = (byte) ((((d2[bArr[i + 1]] & 255) << 12) | ((d2[bArr[i]] & 255) << 18)) >>> 16);
            return 1;
        }
        int i5 = i + 3;
        if (bArr[i5] == 61) {
            int i6 = (d2[bArr[i + 1]] & 255) << 12;
            int i7 = ((d2[bArr[i4]] & 255) << 6) | i6 | ((d2[bArr[i]] & 255) << 18);
            bArr2[i2] = (byte) (i7 >>> 16);
            bArr2[i2 + 1] = (byte) (i7 >>> 8);
            return 2;
        }
        try {
            int i8 = ((d2[bArr[i]] & 255) << 18) | ((d2[bArr[i + 1]] & 255) << 12) | ((d2[bArr[i4]] & 255) << 6) | (d2[bArr[i5]] & 255);
            bArr2[i2] = (byte) (i8 >> 16);
            bArr2[i2 + 1] = (byte) (i8 >> 8);
            bArr2[i2 + 2] = (byte) i8;
            return 3;
        } catch (Exception unused) {
            PrintStream printStream = System.out;
            printStream.println("" + ((int) bArr[i]) + ": " + ((int) d2[bArr[i]]));
            PrintStream printStream2 = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            int i9 = i + 1;
            sb.append((int) bArr[i9]);
            sb.append(": ");
            sb.append((int) d2[bArr[i9]]);
            printStream2.println(sb.toString());
            PrintStream printStream3 = System.out;
            printStream3.println("" + ((int) bArr[i4]) + ": " + ((int) d2[bArr[i4]]));
            PrintStream printStream4 = System.out;
            printStream4.println("" + ((int) bArr[i5]) + ": " + ((int) d2[bArr[i5]]));
            return -1;
        }
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) {
        byte[] d2 = d(i3);
        byte[] bArr2 = new byte[(i2 * 3) / 4];
        byte[] bArr3 = new byte[4];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = i; i6 < i + i2; i6++) {
            byte b2 = (byte) (bArr[i6] & Byte.MAX_VALUE);
            byte b3 = d2[b2];
            if (b3 >= -5) {
                if (b3 >= -1) {
                    int i7 = i4 + 1;
                    bArr3[i4] = b2;
                    if (i7 > 3) {
                        i5 += b(bArr3, 0, bArr2, i5, i3);
                        if (b2 == 61) {
                            break;
                        }
                        i4 = 0;
                    } else {
                        i4 = i7;
                    }
                }
            } else {
                System.err.println("Bad Base64Coder input character at " + i6 + ": " + ((int) bArr[i6]) + "(decimal)");
                return null;
            }
        }
        byte[] bArr4 = new byte[i5];
        System.arraycopy(bArr2, 0, bArr4, 0, i5);
        return bArr4;
    }

    public static byte[] decode(String str) {
        return decode(str, 0);
    }

    public static byte[] decode(String str, int i) {
        byte[] bArr;
        ByteArrayInputStream byteArrayInputStream;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            bArr = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            bArr = str.getBytes();
        }
        byte[] decode = decode(bArr, 0, bArr.length, i);
        if (decode != null && decode.length >= 4 && 35615 == ((decode[0] & 255) | ((decode[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK))) {
            try {
                byte[] bArr2 = new byte[2048];
                GZIPInputStream gZIPInputStream = null;
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byteArrayInputStream = new ByteArrayInputStream(decode);
                        try {
                            GZIPInputStream gZIPInputStream2 = new GZIPInputStream(byteArrayInputStream);
                            while (true) {
                                try {
                                    int read = gZIPInputStream2.read(bArr2);
                                    if (read < 0) {
                                        break;
                                    }
                                    byteArrayOutputStream.write(bArr2, 0, read);
                                } catch (IOException unused2) {
                                    gZIPInputStream = gZIPInputStream2;
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (Exception unused3) {
                                    }
                                    gZIPInputStream.close();
                                    byteArrayInputStream.close();
                                } catch (Throwable th2) {
                                    th = th2;
                                    gZIPInputStream = gZIPInputStream2;
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (Exception unused4) {
                                    }
                                    try {
                                        gZIPInputStream.close();
                                    } catch (Exception unused5) {
                                    }
                                    try {
                                        byteArrayInputStream.close();
                                    } catch (Exception unused6) {
                                    }
                                    throw th;
                                }
                            }
                            decode = byteArrayOutputStream.toByteArray();
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused7) {
                            }
                            gZIPInputStream2.close();
                        } catch (IOException unused8) {
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } catch (IOException unused9) {
                        byteArrayInputStream = null;
                    } catch (Throwable th4) {
                        th = th4;
                        byteArrayInputStream = null;
                    }
                } catch (IOException unused10) {
                    byteArrayOutputStream = null;
                    byteArrayInputStream = null;
                } catch (Throwable th5) {
                    th = th5;
                    byteArrayOutputStream = null;
                    byteArrayInputStream = null;
                }
            } catch (Exception unused11) {
            }
            try {
                byteArrayInputStream.close();
            } catch (Exception unused12) {
            }
        }
        return decode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.ObjectInputStream] */
    public static Object decodeToObject(String str) {
        Throwable th;
        ByteArrayInputStream byteArrayInputStream;
        Object obj;
        ObjectInputStream objectInputStream;
        IOException e2;
        ClassNotFoundException e3;
        try {
            str = 0;
            obj = null;
            obj = null;
            str = 0;
            try {
                byteArrayInputStream = new ByteArrayInputStream(decode(str));
            } catch (IOException e4) {
                e2 = e4;
                objectInputStream = null;
                byteArrayInputStream = null;
            } catch (ClassNotFoundException e5) {
                e3 = e5;
                objectInputStream = null;
                byteArrayInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                byteArrayInputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            try {
                obj = objectInputStream.readObject();
                str = objectInputStream;
            } catch (IOException e6) {
                e2 = e6;
                e2.printStackTrace();
                str = objectInputStream;
                byteArrayInputStream.close();
                str.close();
                return obj;
            } catch (ClassNotFoundException e7) {
                e3 = e7;
                e3.printStackTrace();
                str = objectInputStream;
                byteArrayInputStream.close();
                str.close();
                return obj;
            }
        } catch (IOException e8) {
            e2 = e8;
            objectInputStream = null;
        } catch (ClassNotFoundException e9) {
            e3 = e9;
            objectInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            try {
                byteArrayInputStream.close();
            } catch (Exception unused) {
            }
            try {
                str.close();
            } catch (Exception unused2) {
            }
            throw th;
        }
        try {
            byteArrayInputStream.close();
        } catch (Exception unused3) {
        }
        try {
            str.close();
        } catch (Exception unused4) {
        }
        return obj;
    }

    public static boolean encodeToFile(byte[] bArr, String str) {
        boolean z;
        Throwable th;
        try {
            z = true;
            OutputStream outputStream = null;
            try {
                OutputStream outputStream2 = new OutputStream(new FileOutputStream(str), 1);
                try {
                    outputStream2.write(bArr);
                    outputStream2.close();
                } catch (IOException unused) {
                    outputStream = outputStream2;
                    z = false;
                    outputStream.close();
                    return z;
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = outputStream2;
                    try {
                        outputStream.close();
                    } catch (Exception unused2) {
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception unused4) {
        }
        return z;
    }

    public static boolean decodeToFile(String str, String str2) {
        boolean z;
        OutputStream outputStream;
        Throwable th;
        OutputStream outputStream2;
        try {
            z = false;
            outputStream = null;
            try {
                outputStream2 = new OutputStream(new FileOutputStream(str2), 0);
            } catch (IOException unused) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused2) {
        }
        try {
            outputStream2.write(str.getBytes("UTF-8"));
            z = true;
            outputStream2.close();
        } catch (IOException unused3) {
            outputStream = outputStream2;
            outputStream.close();
            return z;
        } catch (Throwable th3) {
            th = th3;
            outputStream = outputStream2;
            try {
                outputStream.close();
            } catch (Exception unused4) {
            }
            throw th;
        }
        return z;
    }

    public static byte[] decodeFromFile(String str) {
        Throwable th;
        try {
            InputStream inputStream = null;
            byte[] bArr = null;
            try {
                File file = new File(str);
                if (file.length() > 2147483647L) {
                    System.err.println("File is too big for this convenience method (" + file.length() + " bytes).");
                    try {
                        inputStream.close();
                    } catch (Exception unused) {
                    }
                    return null;
                }
                byte[] bArr2 = new byte[(int) file.length()];
                InputStream inputStream2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
                int i = 0;
                while (true) {
                    try {
                        int read = inputStream2.read(bArr2, i, 4096);
                        if (read >= 0) {
                            i += read;
                        } else {
                            bArr = new byte[i];
                            System.arraycopy(bArr2, 0, bArr, 0, i);
                            try {
                                inputStream2.close();
                                return bArr;
                            } catch (Exception unused2) {
                                return bArr;
                            }
                        }
                    } catch (IOException unused3) {
                        inputStream = inputStream2;
                        System.err.println("Error decoding from file " + str);
                        try {
                            inputStream.close();
                        } catch (Exception unused4) {
                        }
                        return bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            inputStream2.close();
                        } catch (Exception unused5) {
                        }
                        throw th;
                    }
                }
            } catch (IOException unused6) {
                bArr = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static String encodeFromFile(String str) {
        return encodeFromFile(str, 1);
    }

    public static String encodeFromFile(String str, int i) {
        Throwable th;
        InputStream inputStream;
        try {
            File file = new File(str);
            byte[] bArr = new byte[Math.max((int) (file.length() * 1.4d), 40)];
            inputStream = new InputStream(new BufferedInputStream(new FileInputStream(file)), i);
            int i2 = 0;
            while (true) {
                try {
                    try {
                        int read = inputStream.read(bArr, i2, 4096);
                        if (read < 0) {
                            break;
                        }
                        i2 += read;
                    } catch (IOException unused) {
                        System.err.println("Error encoding from file " + str);
                        try {
                            inputStream.close();
                            return null;
                        } catch (Exception unused2) {
                            return null;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        inputStream.close();
                    } catch (Exception unused3) {
                    }
                    throw th;
                }
            }
            String str2 = new String(bArr, 0, i2, "UTF-8");
            try {
                inputStream.close();
            } catch (Exception unused4) {
            }
            return str2;
        } catch (IOException unused5) {
            inputStream = null;
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            inputStream.close();
            throw th;
        }
    }

    public static void encodeFileToFile(String str, String str2) {
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        String encodeFromFile;
        IOException e2;
        BufferedOutputStream bufferedOutputStream2;
        try {
            try {
                encodeFromFile = encodeFromFile(str);
                bufferedOutputStream = null;
                try {
                    bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(str2));
                } catch (IOException e3) {
                    e2 = e3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                bufferedOutputStream2.write(encodeFromFile.getBytes("US-ASCII"));
                bufferedOutputStream2.close();
            } catch (IOException e4) {
                e2 = e4;
                bufferedOutputStream = bufferedOutputStream2;
                e2.printStackTrace();
                bufferedOutputStream.close();
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream = bufferedOutputStream2;
                try {
                    bufferedOutputStream.close();
                } catch (Exception unused) {
                }
                throw th;
            }
        } catch (Exception unused2) {
        }
    }

    public static void decodeFileToFile(String str, String str2) {
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        byte[] decodeFromFile;
        IOException e2;
        BufferedOutputStream bufferedOutputStream2;
        try {
            try {
                decodeFromFile = decodeFromFile(str);
                bufferedOutputStream = null;
                try {
                    bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(str2));
                } catch (IOException e3) {
                    e2 = e3;
                }
            } catch (Exception unused) {
                return;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedOutputStream2.write(decodeFromFile);
            bufferedOutputStream2.close();
        } catch (IOException e4) {
            e2 = e4;
            bufferedOutputStream = bufferedOutputStream2;
            e2.printStackTrace();
            bufferedOutputStream.close();
        } catch (Throwable th3) {
            th = th3;
            bufferedOutputStream = bufferedOutputStream2;
            try {
                bufferedOutputStream.close();
            } catch (Exception unused2) {
            }
            throw th;
        }
    }

    /* loaded from: classes3.dex */
    public static class InputStream extends FilterInputStream {
        private boolean a;
        private int b;
        private byte[] c;
        private int d;
        private int e;
        private int f;
        private boolean g;
        private int h;
        private byte[] i;
        private byte[] j;

        public InputStream(java.io.InputStream inputStream) {
            this(inputStream, 0);
        }

        public InputStream(java.io.InputStream inputStream, int i) {
            super(inputStream);
            boolean z = true;
            this.g = (i & 8) != 8;
            this.a = (i & 1) != 1 ? false : z;
            this.d = this.a ? 4 : 3;
            this.c = new byte[this.d];
            this.b = -1;
            this.f = 0;
            this.h = i;
            this.i = Base64Coder.c(i);
            this.j = Base64Coder.d(i);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            int read;
            if (this.b < 0) {
                if (this.a) {
                    byte[] bArr = new byte[3];
                    int i = 0;
                    for (int i2 = 0; i2 < 3; i2++) {
                        try {
                            int read2 = this.in.read();
                            if (read2 >= 0) {
                                bArr[i2] = (byte) read2;
                                i++;
                            }
                        } catch (IOException e) {
                            if (i2 == 0) {
                                throw e;
                            }
                        }
                    }
                    if (i <= 0) {
                        return -1;
                    }
                    Base64Coder.b(bArr, 0, i, this.c, 0, this.h);
                    this.b = 0;
                    this.e = 4;
                } else {
                    byte[] bArr2 = new byte[4];
                    int i3 = 0;
                    while (i3 < 4) {
                        do {
                            read = this.in.read();
                            if (read < 0) {
                                break;
                            }
                        } while (this.j[read & 127] <= -5);
                        if (read < 0) {
                            break;
                        }
                        bArr2[i3] = (byte) read;
                        i3++;
                    }
                    if (i3 == 4) {
                        this.e = Base64Coder.b(bArr2, 0, this.c, 0, this.h);
                        this.b = 0;
                    } else if (i3 == 0) {
                        return -1;
                    } else {
                        throw new IOException("Improperly padded Base64Coder input.");
                    }
                }
            }
            int i4 = this.b;
            if (i4 < 0) {
                throw new IOException("Error in Base64Coder code reading stream.");
            } else if (i4 >= this.e) {
                return -1;
            } else {
                if (!this.a || !this.g || this.f < 76) {
                    this.f++;
                    byte[] bArr3 = this.c;
                    int i5 = this.b;
                    this.b = i5 + 1;
                    byte b = bArr3[i5];
                    if (this.b >= this.d) {
                        this.b = -1;
                    }
                    return b & 255;
                }
                this.f = 0;
                return 10;
            }
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    break;
                }
                int read = read();
                if (read >= 0) {
                    bArr[i + i3] = (byte) read;
                    i3++;
                } else if (i3 == 0) {
                    return -1;
                }
            }
            return i3;
        }
    }

    /* loaded from: classes3.dex */
    public static class OutputStream extends FilterOutputStream {
        private boolean a;
        private int b;
        private byte[] c;
        private int d;
        private int e;
        private boolean f;
        private byte[] g;
        private boolean h;
        private int i;
        private byte[] j;
        private byte[] k;

        public OutputStream(java.io.OutputStream outputStream) {
            this(outputStream, 1);
        }

        public OutputStream(java.io.OutputStream outputStream, int i) {
            super(outputStream);
            boolean z = true;
            this.f = (i & 8) != 8;
            this.a = (i & 1) != 1 ? false : z;
            this.d = this.a ? 3 : 4;
            this.c = new byte[this.d];
            this.b = 0;
            this.e = 0;
            this.h = false;
            this.g = new byte[4];
            this.i = i;
            this.j = Base64Coder.c(i);
            this.k = Base64Coder.d(i);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(int i) throws IOException {
            if (this.h) {
                ((FilterOutputStream) this).out.write(i);
            } else if (this.a) {
                byte[] bArr = this.c;
                int i2 = this.b;
                this.b = i2 + 1;
                bArr[i2] = (byte) i;
                if (this.b >= this.d) {
                    this.out.write(Base64Coder.b(this.g, this.c, this.d, this.i));
                    this.e += 4;
                    if (this.f && this.e >= 76) {
                        this.out.write(10);
                        this.e = 0;
                    }
                    this.b = 0;
                }
            } else {
                byte[] bArr2 = this.k;
                int i3 = i & 127;
                if (bArr2[i3] > -5) {
                    byte[] bArr3 = this.c;
                    int i4 = this.b;
                    this.b = i4 + 1;
                    bArr3[i4] = (byte) i;
                    if (this.b >= this.d) {
                        this.out.write(this.g, 0, Base64Coder.b(bArr3, 0, this.g, 0, this.i));
                        this.b = 0;
                    }
                } else if (bArr2[i3] != -5) {
                    throw new IOException("Invalid character in Base64Coder data.");
                }
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.h) {
                ((FilterOutputStream) this).out.write(bArr, i, i2);
                return;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                write(bArr[i + i3]);
            }
        }

        public void flushBase64() throws IOException {
            if (this.b <= 0) {
                return;
            }
            if (this.a) {
                this.out.write(Base64Coder.b(this.g, this.c, this.b, this.i));
                this.b = 0;
                return;
            }
            throw new IOException("Base64Coder input not properly padded.");
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            flushBase64();
            super.close();
            this.c = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            flushBase64();
            this.h = true;
        }

        public void resumeEncoding() {
            this.h = false;
        }
    }
}
