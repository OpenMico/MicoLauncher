package com.xiaomi.miot.host.manager.utils;

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

/* loaded from: classes2.dex */
public class Base64Coder {
    public static final int DECODE = 0;
    public static final int DONT_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "UTF-8";
    public static final int URL_SAFE = 16;
    private static final byte[] _STANDARD_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _STANDARD_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, Utf8.REPLACEMENT_BYTE, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
    private static final byte[] _URL_SAFE_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] _URL_SAFE_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, Utf8.REPLACEMENT_BYTE, -9, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
    private static final byte[] _ORDERED_ALPHABET = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] _ORDERED_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, Utf8.REPLACEMENT_BYTE, -9, -9, -9, -9};

    /* JADX INFO: Access modifiers changed from: private */
    public static final byte[] getAlphabet(int i) {
        if ((i & 16) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((i & 32) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final byte[] getDecodabet(int i) {
        if ((i & 16) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((i & 32) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    private Base64Coder() {
    }

    public static final void main(String[] strArr) {
        if (strArr.length < 3) {
            usage("Not enough arguments.");
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
            usage("Unknown flag: " + str);
        }
    }

    private static final void usage(String str) {
        System.err.println(str);
        System.err.println("Usage: java Base64Coder -e|-d inputfile outputfile");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] encode3to4(byte[] bArr, byte[] bArr2, int i, int i2) {
        encode3to4(bArr2, 0, i, bArr, 0, i2);
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] encode3to4(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        byte[] alphabet = getAlphabet(i4);
        int i5 = 0;
        int i6 = (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0) | (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0);
        if (i2 > 2) {
            i5 = (bArr[i + 2] << 24) >>> 24;
        }
        int i7 = i6 | i5;
        switch (i2) {
            case 1:
                bArr2[i3] = alphabet[i7 >>> 18];
                bArr2[i3 + 1] = alphabet[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = 61;
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 2:
                bArr2[i3] = alphabet[i7 >>> 18];
                bArr2[i3 + 1] = alphabet[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = alphabet[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 3:
                bArr2[i3] = alphabet[i7 >>> 18];
                bArr2[i3 + 1] = alphabet[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = alphabet[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = alphabet[i7 & 63];
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
        IOException e;
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
                        } catch (IOException e2) {
                            e = e2;
                            gZIPOutputStream = gZIPOutputStream;
                            objectOutputStream = null;
                            e.printStackTrace();
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
                        } catch (IOException e3) {
                            e = e3;
                            e.printStackTrace();
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
                } catch (IOException e4) {
                    e = e4;
                    objectOutputStream = null;
                    gZIPOutputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    gZIPOutputStream = null;
                }
            } catch (IOException e5) {
                e = e5;
                objectOutputStream = null;
                gZIPOutputStream = null;
                outputStream = null;
            } catch (Throwable th5) {
                th = th5;
                gZIPOutputStream = null;
                outputStream = null;
            }
        } catch (IOException e6) {
            e = e6;
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
    /* JADX WARN: Type inference failed for: r5v1, types: [com.xiaomi.miot.host.manager.utils.Base64Coder$OutputStream] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.xiaomi.miot.host.manager.utils.Base64Coder$OutputStream] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.io.OutputStream, com.xiaomi.miot.host.manager.utils.Base64Coder$OutputStream] */
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
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miot.host.manager.utils.Base64Coder.encodeBytes(byte[], int, int, int):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int decode4to3(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        byte[] decodabet = getDecodabet(i3);
        int i4 = i + 2;
        if (bArr[i4] == 61) {
            bArr2[i2] = (byte) ((((decodabet[bArr[i + 1]] & 255) << 12) | ((decodabet[bArr[i]] & 255) << 18)) >>> 16);
            return 1;
        }
        int i5 = i + 3;
        if (bArr[i5] == 61) {
            int i6 = (decodabet[bArr[i + 1]] & 255) << 12;
            int i7 = ((decodabet[bArr[i4]] & 255) << 6) | i6 | ((decodabet[bArr[i]] & 255) << 18);
            bArr2[i2] = (byte) (i7 >>> 16);
            bArr2[i2 + 1] = (byte) (i7 >>> 8);
            return 2;
        }
        try {
            int i8 = ((decodabet[bArr[i]] & 255) << 18) | ((decodabet[bArr[i + 1]] & 255) << 12) | ((decodabet[bArr[i4]] & 255) << 6) | (decodabet[bArr[i5]] & 255);
            bArr2[i2] = (byte) (i8 >> 16);
            bArr2[i2 + 1] = (byte) (i8 >> 8);
            bArr2[i2 + 2] = (byte) i8;
            return 3;
        } catch (Exception unused) {
            PrintStream printStream = System.out;
            printStream.println("" + ((int) bArr[i]) + ": " + ((int) decodabet[bArr[i]]));
            PrintStream printStream2 = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            int i9 = i + 1;
            sb.append((int) bArr[i9]);
            sb.append(": ");
            sb.append((int) decodabet[bArr[i9]]);
            printStream2.println(sb.toString());
            PrintStream printStream3 = System.out;
            printStream3.println("" + ((int) bArr[i4]) + ": " + ((int) decodabet[bArr[i4]]));
            PrintStream printStream4 = System.out;
            printStream4.println("" + ((int) bArr[i5]) + ": " + ((int) decodabet[bArr[i5]]));
            return -1;
        }
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) {
        byte[] decodabet = getDecodabet(i3);
        byte[] bArr2 = new byte[(i2 * 3) / 4];
        byte[] bArr3 = new byte[4];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = i; i6 < i + i2; i6++) {
            byte b = (byte) (bArr[i6] & Byte.MAX_VALUE);
            byte b2 = decodabet[b];
            if (b2 >= -5) {
                if (b2 >= -1) {
                    int i7 = i4 + 1;
                    bArr3[i4] = b;
                    if (i7 > 3) {
                        i5 += decode4to3(bArr3, 0, bArr2, i5, i3);
                        if (b == 61) {
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
        IOException e;
        ClassNotFoundException e2;
        try {
            str = 0;
            obj = null;
            obj = null;
            str = 0;
            try {
                byteArrayInputStream = new ByteArrayInputStream(decode(str));
            } catch (IOException e3) {
                e = e3;
                objectInputStream = null;
                byteArrayInputStream = null;
            } catch (ClassNotFoundException e4) {
                e2 = e4;
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
            } catch (IOException e5) {
                e = e5;
                e.printStackTrace();
                str = objectInputStream;
                byteArrayInputStream.close();
                str.close();
                return obj;
            } catch (ClassNotFoundException e6) {
                e2 = e6;
                e2.printStackTrace();
                str = objectInputStream;
                byteArrayInputStream.close();
                str.close();
                return obj;
            }
        } catch (IOException e7) {
            e = e7;
            objectInputStream = null;
        } catch (ClassNotFoundException e8) {
            e2 = e8;
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
        IOException e;
        BufferedOutputStream bufferedOutputStream2;
        try {
            try {
                encodeFromFile = encodeFromFile(str);
                bufferedOutputStream = null;
                try {
                    bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(str2));
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                bufferedOutputStream2.write(encodeFromFile.getBytes("US-ASCII"));
                bufferedOutputStream2.close();
            } catch (IOException e3) {
                e = e3;
                bufferedOutputStream = bufferedOutputStream2;
                e.printStackTrace();
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
        IOException e;
        BufferedOutputStream bufferedOutputStream2;
        try {
            try {
                decodeFromFile = decodeFromFile(str);
                bufferedOutputStream = null;
                try {
                    bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(str2));
                } catch (IOException e2) {
                    e = e2;
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
        } catch (IOException e3) {
            e = e3;
            bufferedOutputStream = bufferedOutputStream2;
            e.printStackTrace();
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

    /* loaded from: classes2.dex */
    public static class InputStream extends FilterInputStream {
        private byte[] alphabet;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream inputStream) {
            this(inputStream, 0);
        }

        public InputStream(java.io.InputStream inputStream, int i) {
            super(inputStream);
            boolean z = true;
            this.breakLines = (i & 8) != 8;
            this.encode = (i & 1) != 1 ? false : z;
            this.bufferLength = this.encode ? 4 : 3;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = 0;
            this.options = i;
            this.alphabet = Base64Coder.getAlphabet(i);
            this.decodabet = Base64Coder.getDecodabet(i);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            int read;
            if (this.position < 0) {
                if (this.encode) {
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
                    Base64Coder.encode3to4(bArr, 0, i, this.buffer, 0, this.options);
                    this.position = 0;
                    this.numSigBytes = 4;
                } else {
                    byte[] bArr2 = new byte[4];
                    int i3 = 0;
                    while (i3 < 4) {
                        do {
                            read = this.in.read();
                            if (read < 0) {
                                break;
                            }
                        } while (this.decodabet[read & 127] <= -5);
                        if (read < 0) {
                            break;
                        }
                        bArr2[i3] = (byte) read;
                        i3++;
                    }
                    if (i3 == 4) {
                        this.numSigBytes = Base64Coder.decode4to3(bArr2, 0, this.buffer, 0, this.options);
                        this.position = 0;
                    } else if (i3 == 0) {
                        return -1;
                    } else {
                        throw new IOException("Improperly padded Base64Coder input.");
                    }
                }
            }
            int i4 = this.position;
            if (i4 < 0) {
                throw new IOException("Error in Base64Coder code reading stream.");
            } else if (i4 >= this.numSigBytes) {
                return -1;
            } else {
                if (!this.encode || !this.breakLines || this.lineLength < 76) {
                    this.lineLength++;
                    byte[] bArr3 = this.buffer;
                    int i5 = this.position;
                    this.position = i5 + 1;
                    byte b = bArr3[i5];
                    if (this.position >= this.bufferLength) {
                        this.position = -1;
                    }
                    return b & 255;
                }
                this.lineLength = 0;
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

    /* loaded from: classes2.dex */
    public static class OutputStream extends FilterOutputStream {
        private byte[] alphabet;
        private byte[] b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream outputStream) {
            this(outputStream, 1);
        }

        public OutputStream(java.io.OutputStream outputStream, int i) {
            super(outputStream);
            boolean z = true;
            this.breakLines = (i & 8) != 8;
            this.encode = (i & 1) != 1 ? false : z;
            this.bufferLength = this.encode ? 3 : 4;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.b4 = new byte[4];
            this.options = i;
            this.alphabet = Base64Coder.getAlphabet(i);
            this.decodabet = Base64Coder.getDecodabet(i);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(int i) throws IOException {
            if (this.suspendEncoding) {
                ((FilterOutputStream) this).out.write(i);
            } else if (this.encode) {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64Coder.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                    this.lineLength += 4;
                    if (this.breakLines && this.lineLength >= 76) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                }
            } else {
                byte[] bArr2 = this.decodabet;
                int i3 = i & 127;
                if (bArr2[i3] > -5) {
                    byte[] bArr3 = this.buffer;
                    int i4 = this.position;
                    this.position = i4 + 1;
                    bArr3[i4] = (byte) i;
                    if (this.position >= this.bufferLength) {
                        this.out.write(this.b4, 0, Base64Coder.decode4to3(bArr3, 0, this.b4, 0, this.options));
                        this.position = 0;
                    }
                } else if (bArr2[i3] != -5) {
                    throw new IOException("Invalid character in Base64Coder data.");
                }
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.suspendEncoding) {
                ((FilterOutputStream) this).out.write(bArr, i, i2);
                return;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                write(bArr[i + i3]);
            }
        }

        public void flushBase64() throws IOException {
            if (this.position <= 0) {
                return;
            }
            if (this.encode) {
                this.out.write(Base64Coder.encode3to4(this.b4, this.buffer, this.position, this.options));
                this.position = 0;
                return;
            }
            throw new IOException("Base64Coder input not properly padded.");
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            flushBase64();
            this.suspendEncoding = true;
        }

        public void resumeEncoding() {
            this.suspendEncoding = false;
        }
    }
}
