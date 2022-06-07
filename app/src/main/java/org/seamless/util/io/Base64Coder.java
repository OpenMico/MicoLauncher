package org.seamless.util.io;

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
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.zip.GZIPOutputStream;
import okio.Utf8;

/* loaded from: classes4.dex */
public class Base64Coder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte[] _STANDARD_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _STANDARD_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, Utf8.REPLACEMENT_BYTE, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _URL_SAFE_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] _URL_SAFE_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, Utf8.REPLACEMENT_BYTE, -9, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _ORDERED_ALPHABET = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] _ORDERED_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, Utf8.REPLACEMENT_BYTE, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};

    private static final byte[] getAlphabet(int i) {
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

    public static void encode(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        byte[] bArr = new byte[3];
        byte[] bArr2 = new byte[4];
        while (byteBuffer.hasRemaining()) {
            int min = Math.min(3, byteBuffer.remaining());
            byteBuffer.get(bArr, 0, min);
            encode3to4(bArr2, bArr, min, 0);
            byteBuffer2.put(bArr2);
        }
    }

    public static void encode(ByteBuffer byteBuffer, CharBuffer charBuffer) {
        byte[] bArr = new byte[3];
        byte[] bArr2 = new byte[4];
        while (byteBuffer.hasRemaining()) {
            int min = Math.min(3, byteBuffer.remaining());
            byteBuffer.get(bArr, 0, min);
            encode3to4(bArr2, bArr, min, 0);
            for (int i = 0; i < 4; i++) {
                charBuffer.put((char) (bArr2[i] & 255));
            }
        }
    }

    public static String encodeObject(Serializable serializable) throws IOException {
        return encodeObject(serializable, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v7 */
    public static String encodeObject(Serializable serializable, int i) throws IOException {
        GZIPOutputStream gZIPOutputStream;
        IOException e;
        OutputStream outputStream;
        if (serializable != null) {
            ByteArrayOutputStream byteArrayOutputStream = null;
            r0 = null;
            r0 = null;
            r0 = 0;
            r0 = null;
            r0 = null;
            ObjectOutputStream objectOutputStream = null;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    outputStream = new OutputStream(byteArrayOutputStream, i | 1);
                    try {
                        if ((i & 2) != 0) {
                            gZIPOutputStream = new GZIPOutputStream(outputStream);
                            try {
                                objectOutputStream = new ObjectOutputStream(gZIPOutputStream);
                                gZIPOutputStream = gZIPOutputStream;
                            } catch (IOException e2) {
                                e = e2;
                                byteArrayOutputStream = byteArrayOutputStream;
                                objectOutputStream = objectOutputStream;
                                gZIPOutputStream = gZIPOutputStream;
                                try {
                                    throw e;
                                } catch (Throwable th) {
                                    th = th;
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
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                objectOutputStream.close();
                                gZIPOutputStream.close();
                                outputStream.close();
                                byteArrayOutputStream.close();
                                throw th;
                            }
                        } else {
                            objectOutputStream = new ObjectOutputStream(outputStream);
                            gZIPOutputStream = null;
                        }
                        objectOutputStream.writeObject(serializable);
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
                        try {
                            return new String(byteArrayOutputStream.toByteArray(), "US-ASCII");
                        } catch (UnsupportedEncodingException unused9) {
                            return new String(byteArrayOutputStream.toByteArray());
                        }
                    } catch (IOException e3) {
                        e = e3;
                        byteArrayOutputStream = byteArrayOutputStream;
                        objectOutputStream = objectOutputStream;
                        gZIPOutputStream = objectOutputStream;
                    } catch (Throwable th3) {
                        th = th3;
                        gZIPOutputStream = objectOutputStream;
                    }
                } catch (IOException e4) {
                    e = e4;
                    gZIPOutputStream = null;
                    outputStream = null;
                    byteArrayOutputStream = byteArrayOutputStream;
                    objectOutputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    gZIPOutputStream = null;
                    outputStream = null;
                }
            } catch (IOException e5) {
                e = e5;
                gZIPOutputStream = null;
                objectOutputStream = null;
                outputStream = null;
            } catch (Throwable th5) {
                th = th5;
                gZIPOutputStream = null;
                byteArrayOutputStream = null;
                outputStream = null;
            }
        } else {
            throw new NullPointerException("Cannot serialize a null object.");
        }
    }

    public static String encodeBytes(byte[] bArr) {
        try {
            return encodeBytes(bArr, 0, bArr.length, 0);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bArr, int i) throws IOException {
        return encodeBytes(bArr, 0, bArr.length, i);
    }

    public static String encodeBytes(byte[] bArr, int i, int i2) {
        try {
            return encodeBytes(bArr, i, i2, 0);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bArr, int i, int i2, int i3) throws IOException {
        byte[] encodeBytesToBytes = encodeBytesToBytes(bArr, i, i2, i3);
        try {
            return new String(encodeBytesToBytes, "US-ASCII");
        } catch (UnsupportedEncodingException unused) {
            return new String(encodeBytesToBytes);
        }
    }

    public static byte[] encodeBytesToBytes(byte[] bArr) {
        try {
            return encodeBytesToBytes(bArr, 0, bArr.length, 0);
        } catch (IOException unused) {
            return null;
        }
    }

    public static byte[] encodeBytesToBytes(byte[] bArr, int i, int i2, int i3) throws IOException {
        int i4;
        OutputStream outputStream;
        IOException e;
        if (bArr == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        } else if (i < 0) {
            throw new IllegalArgumentException("Cannot have negative offset: " + i);
        } else if (i2 < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + i2);
        } else if (i + i2 > bArr.length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(bArr.length)));
        } else if ((i3 & 2) != 0) {
            ByteArrayOutputStream byteArrayOutputStream = null;
            r1 = null;
            r1 = null;
            GZIPOutputStream gZIPOutputStream = null;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    outputStream = new OutputStream(byteArrayOutputStream, i3 | 1);
                    try {
                        gZIPOutputStream = new GZIPOutputStream(outputStream);
                    } catch (IOException e2) {
                        e = e2;
                        gZIPOutputStream = null;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception unused) {
                        }
                        try {
                            outputStream.close();
                        } catch (Exception unused2) {
                        }
                        try {
                            byteArrayOutputStream.close();
                        } catch (Exception unused3) {
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    outputStream = null;
                    gZIPOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = null;
                }
            } catch (IOException e4) {
                e = e4;
                outputStream = null;
                gZIPOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                byteArrayOutputStream = null;
                outputStream = null;
            }
            try {
                gZIPOutputStream.write(bArr, i, i2);
                gZIPOutputStream.close();
                try {
                    gZIPOutputStream.close();
                } catch (Exception unused4) {
                }
                try {
                    outputStream.close();
                } catch (Exception unused5) {
                }
                try {
                    byteArrayOutputStream.close();
                } catch (Exception unused6) {
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e5) {
                e = e5;
                byteArrayOutputStream = byteArrayOutputStream;
                try {
                    throw e;
                } catch (Throwable th4) {
                    th = th4;
                    gZIPOutputStream.close();
                    outputStream.close();
                    byteArrayOutputStream.close();
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                gZIPOutputStream.close();
                outputStream.close();
                byteArrayOutputStream.close();
                throw th;
            }
        } else {
            Object[] objArr = (i3 & 8) != 0 ? 1 : null;
            int i5 = ((i2 / 3) * 4) + (i2 % 3 > 0 ? 4 : 0);
            if (objArr != null) {
                i5 += i5 / 76;
            }
            byte[] bArr2 = new byte[i5];
            int i6 = i2 - 2;
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            while (i7 < i6) {
                encode3to4(bArr, i7 + i, 3, bArr2, i8, i3);
                int i10 = i9 + 4;
                if (objArr == null || i10 < 76) {
                    i9 = i10;
                } else {
                    bArr2[i8 + 4] = 10;
                    i8++;
                    i9 = 0;
                }
                i7 += 3;
                i8 += 4;
            }
            if (i7 < i2) {
                encode3to4(bArr, i7 + i, i2 - i7, bArr2, i8, i3);
                i4 = i8 + 4;
            } else {
                i4 = i8;
            }
            if (i4 > bArr2.length - 1) {
                return bArr2;
            }
            byte[] bArr3 = new byte[i4];
            System.arraycopy(bArr2, 0, bArr3, 0, i4);
            return bArr3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int decode4to3(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4;
        int i5;
        if (bArr == null) {
            throw new NullPointerException("Source array was null.");
        } else if (bArr2 == null) {
            throw new NullPointerException("Destination array was null.");
        } else if (i < 0 || (i4 = i + 3) >= bArr.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", Integer.valueOf(bArr.length), Integer.valueOf(i)));
        } else if (i2 < 0 || (i5 = i2 + 2) >= bArr2.length) {
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", Integer.valueOf(bArr2.length), Integer.valueOf(i2)));
        } else {
            byte[] decodabet = getDecodabet(i3);
            int i6 = i + 2;
            if (bArr[i6] == 61) {
                bArr2[i2] = (byte) ((((decodabet[bArr[i + 1]] & 255) << 12) | ((decodabet[bArr[i]] & 255) << 18)) >>> 16);
                return 1;
            } else if (bArr[i4] == 61) {
                int i7 = ((decodabet[bArr[i6]] & 255) << 6) | ((decodabet[bArr[i + 1]] & 255) << 12) | ((decodabet[bArr[i]] & 255) << 18);
                bArr2[i2] = (byte) (i7 >>> 16);
                bArr2[i2 + 1] = (byte) (i7 >>> 8);
                return 2;
            } else {
                int i8 = (decodabet[bArr[i4]] & 255) | ((decodabet[bArr[i + 1]] & 255) << 12) | ((decodabet[bArr[i]] & 255) << 18) | ((decodabet[bArr[i6]] & 255) << 6);
                bArr2[i2] = (byte) (i8 >> 16);
                bArr2[i2 + 1] = (byte) (i8 >> 8);
                bArr2[i5] = (byte) i8;
                return 3;
            }
        }
    }

    public static byte[] decode(byte[] bArr) throws IOException {
        return decode(bArr, 0, bArr.length, 0);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) throws IOException {
        int i4;
        if (bArr == null) {
            throw new NullPointerException("Cannot decode null source array.");
        } else if (i < 0 || (i4 = i + i2) > bArr.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        } else if (i2 == 0) {
            return new byte[0];
        } else {
            if (i2 >= 4) {
                byte[] decodabet = getDecodabet(i3);
                byte[] bArr2 = new byte[(i2 * 3) / 4];
                byte[] bArr3 = new byte[4];
                int i5 = 0;
                int i6 = 0;
                while (i < i4) {
                    byte b = decodabet[bArr[i] & 255];
                    if (b >= -5) {
                        if (b >= -1) {
                            int i7 = i5 + 1;
                            bArr3[i5] = bArr[i];
                            if (i7 > 3) {
                                i6 += decode4to3(bArr3, 0, bArr2, i6, i3);
                                if (bArr[i] == 61) {
                                    break;
                                }
                                i5 = 0;
                            } else {
                                i5 = i7;
                            }
                        }
                        i++;
                    } else {
                        throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", Integer.valueOf(bArr[i] & 255), Integer.valueOf(i)));
                    }
                }
                byte[] bArr4 = new byte[i6];
                System.arraycopy(bArr2, 0, bArr4, 0, i6);
                return bArr4;
            }
            throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + i2);
        }
    }

    public static byte[] decode(String str) throws IOException {
        return decode(str, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [int] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.ByteArrayInputStream] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.io.ByteArrayInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.ByteArrayInputStream] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] decode(java.lang.String r5, int r6) throws java.io.IOException {
        /*
            if (r5 == 0) goto L_0x008d
            java.lang.String r0 = "US-ASCII"
            byte[] r5 = r5.getBytes(r0)     // Catch: UnsupportedEncodingException -> 0x0009
            goto L_0x000d
        L_0x0009:
            byte[] r5 = r5.getBytes()
        L_0x000d:
            int r0 = r5.length
            r1 = 0
            byte[] r5 = decode(r5, r1, r0, r6)
            r0 = 4
            r6 = r6 & r0
            r2 = 1
            if (r6 == 0) goto L_0x001a
            r6 = r2
            goto L_0x001b
        L_0x001a:
            r6 = r1
        L_0x001b:
            if (r5 == 0) goto L_0x008c
            int r3 = r5.length
            if (r3 < r0) goto L_0x008c
            if (r6 != 0) goto L_0x008c
            byte r6 = r5[r1]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r0 = r5[r2]
            int r0 = r0 << 8
            r2 = 65280(0xff00, float:9.1477E-41)
            r0 = r0 & r2
            r6 = r6 | r0
            r0 = 35615(0x8b1f, float:4.9907E-41)
            if (r0 != r6) goto L_0x008c
            r6 = 2048(0x800, float:2.87E-42)
            byte[] r6 = new byte[r6]
            r0 = 0
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch: IOException -> 0x0075, all -> 0x0071
            r2.<init>()     // Catch: IOException -> 0x0075, all -> 0x0071
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch: IOException -> 0x006c, all -> 0x0069
            r3.<init>(r5)     // Catch: IOException -> 0x006c, all -> 0x0069
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch: IOException -> 0x0066, all -> 0x0064
            r4.<init>(r3)     // Catch: IOException -> 0x0066, all -> 0x0064
        L_0x0048:
            int r0 = r4.read(r6)     // Catch: IOException -> 0x0062, all -> 0x0060
            if (r0 < 0) goto L_0x0052
            r2.write(r6, r1, r0)     // Catch: IOException -> 0x0062, all -> 0x0060
            goto L_0x0048
        L_0x0052:
            byte[] r5 = r2.toByteArray()     // Catch: IOException -> 0x0062, all -> 0x0060
            r2.close()     // Catch: Exception -> 0x0059
        L_0x0059:
            r4.close()     // Catch: Exception -> 0x005c
        L_0x005c:
            r3.close()     // Catch: Exception -> 0x008c
            goto L_0x008c
        L_0x0060:
            r5 = move-exception
            goto L_0x0081
        L_0x0062:
            r6 = move-exception
            goto L_0x006f
        L_0x0064:
            r5 = move-exception
            goto L_0x0082
        L_0x0066:
            r6 = move-exception
            r4 = r0
            goto L_0x006f
        L_0x0069:
            r5 = move-exception
            r3 = r0
            goto L_0x0082
        L_0x006c:
            r6 = move-exception
            r3 = r0
            r4 = r3
        L_0x006f:
            r0 = r2
            goto L_0x0078
        L_0x0071:
            r5 = move-exception
            r2 = r0
            r3 = r2
            goto L_0x0082
        L_0x0075:
            r6 = move-exception
            r3 = r0
            r4 = r3
        L_0x0078:
            r6.printStackTrace()     // Catch: all -> 0x007f
            r0.close()     // Catch: Exception -> 0x0059
            goto L_0x0059
        L_0x007f:
            r5 = move-exception
            r2 = r0
        L_0x0081:
            r0 = r4
        L_0x0082:
            r2.close()     // Catch: Exception -> 0x0085
        L_0x0085:
            r0.close()     // Catch: Exception -> 0x0088
        L_0x0088:
            r3.close()     // Catch: Exception -> 0x008b
        L_0x008b:
            throw r5
        L_0x008c:
            return r5
        L_0x008d:
            java.lang.NullPointerException r5 = new java.lang.NullPointerException
            java.lang.String r6 = "Input string was null."
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.seamless.util.io.Base64Coder.decode(java.lang.String, int):byte[]");
    }

    public static Object decodeToObject(String str) throws IOException, ClassNotFoundException {
        return decodeToObject(str, 0, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v8 */
    public static Object decodeToObject(String str, int i, final ClassLoader classLoader) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream;
        Throwable th;
        ByteArrayInputStream byteArrayInputStream;
        IOException e;
        ClassNotFoundException e2;
        try {
            byte[] decode = decode(str, i);
            objectInputStream = 0;
            objectInputStream = 0;
            objectInputStream = null;
            objectInputStream = null;
            try {
                byteArrayInputStream = new ByteArrayInputStream(decode);
                try {
                    if (classLoader == null) {
                        objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    } else {
                        objectInputStream = new ObjectInputStream(byteArrayInputStream) { // from class: org.seamless.util.io.Base64Coder.1
                            @Override // java.io.ObjectInputStream
                            public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                                Class<?> cls = Class.forName(objectStreamClass.getName(), false, classLoader);
                                return cls == null ? super.resolveClass(objectStreamClass) : cls;
                            }
                        };
                    }
                    Object readObject = objectInputStream.readObject();
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception unused) {
                    }
                    try {
                        objectInputStream.close();
                    } catch (Exception unused2) {
                    }
                    return readObject;
                } catch (IOException e3) {
                    e = e3;
                    throw e;
                } catch (ClassNotFoundException e4) {
                    e2 = e4;
                    throw e2;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception unused3) {
                    }
                    try {
                        objectInputStream.close();
                    } catch (Exception unused4) {
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
            } catch (ClassNotFoundException e6) {
                e2 = e6;
            } catch (Throwable th3) {
                th = th3;
                byteArrayInputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            byteArrayInputStream = i;
            objectInputStream = classLoader;
        }
    }

    public static void encodeToFile(byte[] bArr, String str) throws IOException {
        Throwable th;
        if (bArr != null) {
            try {
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                OutputStream outputStream = new OutputStream(new FileOutputStream(str), 1);
                try {
                    outputStream.write(bArr);
                    try {
                        outputStream.close();
                    } catch (Exception unused) {
                    }
                } catch (IOException e) {
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        outputStream.close();
                    } catch (Exception unused2) {
                    }
                    throw th;
                }
            } catch (IOException e2) {
                throw e2;
            }
        } else {
            throw new NullPointerException("Data to encode was null.");
        }
    }

    public static void decodeToFile(String str, String str2) throws IOException {
        Throwable th;
        OutputStream outputStream;
        OutputStream outputStream2 = null;
        try {
            try {
                outputStream = new OutputStream(new FileOutputStream(str2), 0);
            } catch (IOException e) {
                throw e;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            outputStream.write(str.getBytes("US-ASCII"));
            try {
                outputStream.close();
            } catch (Exception unused) {
            }
        } catch (IOException e2) {
        } catch (Throwable th3) {
            th = th3;
            outputStream2 = outputStream;
            try {
                outputStream2.close();
            } catch (Exception unused2) {
            }
            throw th;
        }
    }

    public static byte[] decodeFromFile(String str) throws IOException {
        Throwable th;
        try {
            try {
                File file = new File(str);
                if (file.length() <= 2147483647L) {
                    byte[] bArr = new byte[(int) file.length()];
                    InputStream inputStream = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
                    int i = 0;
                    while (true) {
                        try {
                            int read = inputStream.read(bArr, i, 4096);
                            if (read < 0) {
                                break;
                            }
                            i += read;
                        } catch (IOException e) {
                            throw e;
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                inputStream.close();
                            } catch (Exception unused) {
                            }
                            throw th;
                        }
                    }
                    byte[] bArr2 = new byte[i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    try {
                        inputStream.close();
                    } catch (Exception unused2) {
                    }
                    return bArr2;
                }
                throw new IOException("File is too big for this convenience method (" + file.length() + " bytes).");
            } catch (IOException e2) {
                throw e2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static String encodeFromFile(String str) throws IOException {
        Throwable th;
        try {
            try {
                File file = new File(str);
                byte[] bArr = new byte[Math.max((int) ((file.length() * 1.4d) + 1.0d), 40)];
                InputStream inputStream = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
                int i = 0;
                while (true) {
                    try {
                        int read = inputStream.read(bArr, i, 4096);
                        if (read < 0) {
                            break;
                        }
                        i += read;
                    } catch (IOException e) {
                        throw e;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            inputStream.close();
                        } catch (Exception unused) {
                        }
                        throw th;
                    }
                }
                String str2 = new String(bArr, 0, i, "US-ASCII");
                try {
                    inputStream.close();
                } catch (Exception unused2) {
                }
                return str2;
            } catch (IOException e2) {
                throw e2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void encodeFileToFile(String str, String str2) throws IOException {
        Throwable th;
        String encodeFromFile;
        try {
            encodeFromFile = encodeFromFile(str);
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
            try {
                bufferedOutputStream.write(encodeFromFile.getBytes("US-ASCII"));
                try {
                    bufferedOutputStream.close();
                } catch (Exception unused) {
                }
            } catch (IOException e) {
            } catch (Throwable th3) {
                th = th3;
                try {
                    bufferedOutputStream.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (IOException e2) {
            throw e2;
        }
    }

    public static void decodeFileToFile(String str, String str2) throws IOException {
        Throwable th;
        byte[] decodeFromFile;
        try {
            decodeFromFile = decodeFromFile(str);
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
            try {
                bufferedOutputStream.write(decodeFromFile);
                try {
                    bufferedOutputStream.close();
                } catch (Exception unused) {
                }
            } catch (IOException e) {
            } catch (Throwable th3) {
                th = th3;
                try {
                    bufferedOutputStream.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (IOException e2) {
            throw e2;
        }
    }

    /* loaded from: classes4.dex */
    public static class InputStream extends FilterInputStream {
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
            this.options = i;
            boolean z = true;
            this.breakLines = (i & 8) > 0;
            this.encode = (i & 1) <= 0 ? false : z;
            this.bufferLength = this.encode ? 4 : 3;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = 0;
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
                        int read2 = this.in.read();
                        if (read2 < 0) {
                            break;
                        }
                        bArr[i2] = (byte) read2;
                        i++;
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
                        throw new IOException("Improperly padded Base64 input.");
                    }
                }
            }
            int i4 = this.position;
            if (i4 < 0) {
                throw new IOException("Error in Base64 code reading stream.");
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

    /* loaded from: classes4.dex */
    public static class OutputStream extends FilterOutputStream {
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
            this.breakLines = (i & 8) != 0;
            this.encode = (i & 1) == 0 ? false : z;
            this.bufferLength = this.encode ? 3 : 4;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.b4 = new byte[4];
            this.options = i;
            this.decodabet = Base64Coder.getDecodabet(i);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(int i) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(i);
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
                    throw new IOException("Invalid character in Base64 data.");
                }
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(bArr, i, i2);
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
            throw new IOException("Base64 input not properly padded.");
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

    public static byte[] encode(byte[] bArr) {
        return encodeBytes(bArr).getBytes();
    }

    public static String encodeString(String str) {
        return encodeBytes(str.getBytes());
    }

    public static String decodeString(String str) {
        try {
            return new String(decode(str));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
