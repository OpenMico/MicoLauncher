package com.mijiasdk.bleserver.protocol.bouncycastle.util;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Vector;
import okio.Utf8;

/* loaded from: classes2.dex */
public final class Strings {
    private static String a;

    static {
        try {
            try {
                a = (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: com.mijiasdk.bleserver.protocol.bouncycastle.util.Strings.1
                    /* renamed from: a */
                    public String run() {
                        return System.getProperty("line.separator");
                    }
                });
            } catch (Exception unused) {
                a = String.format("%n", new Object[0]);
            }
        } catch (Exception unused2) {
            a = "\n";
        }
    }

    public static String fromUTF8ByteArray(byte[] bArr) {
        char c;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            i3++;
            if ((bArr[i2] & 240) == 240) {
                i3++;
                i2 += 4;
            } else if ((bArr[i2] & 224) == 224) {
                i2 += 3;
            } else {
                i2 = (bArr[i2] & 192) == 192 ? i2 + 2 : i2 + 1;
            }
        }
        char[] cArr = new char[i3];
        int i4 = 0;
        while (i < bArr.length) {
            if ((bArr[i] & 240) == 240) {
                int i5 = (((((bArr[i] & 3) << 18) | ((bArr[i + 1] & Utf8.REPLACEMENT_BYTE) << 12)) | ((bArr[i + 2] & Utf8.REPLACEMENT_BYTE) << 6)) | (bArr[i + 3] & Utf8.REPLACEMENT_BYTE)) - 65536;
                c = (char) ((i5 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320);
                i4++;
                cArr[i4] = (char) (55296 | (i5 >> 10));
                i += 4;
            } else if ((bArr[i] & 224) == 224) {
                c = (char) (((bArr[i] & 15) << 12) | ((bArr[i + 1] & Utf8.REPLACEMENT_BYTE) << 6) | (bArr[i + 2] & Utf8.REPLACEMENT_BYTE));
                i += 3;
            } else if ((bArr[i] & 208) == 208) {
                c = (char) (((bArr[i] & Ascii.US) << 6) | (bArr[i + 1] & Utf8.REPLACEMENT_BYTE));
                i += 2;
            } else if ((bArr[i] & 192) == 192) {
                c = (char) (((bArr[i] & Ascii.US) << 6) | (bArr[i + 1] & Utf8.REPLACEMENT_BYTE));
                i += 2;
            } else {
                c = (char) (bArr[i] & 255);
                i++;
            }
            i4++;
            cArr[i4] = c;
        }
        return new String(cArr);
    }

    public static byte[] toUTF8ByteArray(String str) {
        return toUTF8ByteArray(str.toCharArray());
    }

    public static byte[] toUTF8ByteArray(char[] cArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            toUTF8ByteArray(cArr, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new IllegalStateException("cannot encode string to byte array!");
        }
    }

    public static void toUTF8ByteArray(char[] cArr, OutputStream outputStream) throws IOException {
        int i = 0;
        while (i < cArr.length) {
            char c = cArr[i];
            if (c < 128) {
                outputStream.write(c);
            } else if (c < 2048) {
                outputStream.write((c >> 6) | 192);
                outputStream.write((c & '?') | 128);
            } else if (c < 55296 || c > 57343) {
                outputStream.write((c >> '\f') | 224);
                outputStream.write(((c >> 6) & 63) | 128);
                outputStream.write((c & '?') | 128);
            } else {
                i++;
                if (i < cArr.length) {
                    char c2 = cArr[i];
                    if (c <= 56319) {
                        int i2 = (((c & 1023) << 10) | (c2 & 1023)) + 65536;
                        outputStream.write((i2 >> 18) | PsExtractor.VIDEO_STREAM_MASK);
                        outputStream.write(((i2 >> 12) & 63) | 128);
                        outputStream.write(((i2 >> 6) & 63) | 128);
                        outputStream.write((i2 & 63) | 128);
                    } else {
                        throw new IllegalStateException("invalid UTF-16 codepoint");
                    }
                } else {
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }
            }
            i++;
        }
    }

    public static String toUpperCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('a' <= c && 'z' >= c) {
                charArray[i] = (char) ((c - 'a') + 65);
                z = true;
            }
        }
        return z ? new String(charArray) : str;
    }

    public static String toLowerCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('A' <= c && 'Z' >= c) {
                charArray[i] = (char) ((c - 'A') + 97);
                z = true;
            }
        }
        return z ? new String(charArray) : str;
    }

    public static byte[] toByteArray(char[] cArr) {
        byte[] bArr = new byte[cArr.length];
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = (byte) cArr[i];
        }
        return bArr;
    }

    public static byte[] toByteArray(String str) {
        byte[] bArr = new byte[str.length()];
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        return bArr;
    }

    public static int toByteArray(String str, byte[] bArr, int i) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i + i2] = (byte) str.charAt(i2);
        }
        return length;
    }

    public static String fromByteArray(byte[] bArr) {
        return new String(asCharArray(bArr));
    }

    public static char[] asCharArray(byte[] bArr) {
        char[] cArr = new char[bArr.length];
        for (int i = 0; i != cArr.length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return cArr;
    }

    public static String[] split(String str, char c) {
        int i;
        Vector vector = new Vector();
        boolean z = true;
        while (true) {
            if (!z) {
                break;
            }
            int indexOf = str.indexOf(c);
            if (indexOf > 0) {
                vector.addElement(str.substring(0, indexOf));
                str = str.substring(indexOf + 1);
            } else {
                vector.addElement(str);
                z = false;
            }
        }
        String[] strArr = new String[vector.size()];
        for (i = 0; i != strArr.length; i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    public static StringList newList() {
        return new a();
    }

    public static String lineSeparator() {
        return a;
    }

    /* loaded from: classes2.dex */
    private static class a extends ArrayList<String> implements StringList {
        private a() {
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List, com.mijiasdk.bleserver.protocol.bouncycastle.util.StringList
        public /* bridge */ /* synthetic */ String get(int i) {
            return (String) super.get(i);
        }

        @Override // com.mijiasdk.bleserver.protocol.bouncycastle.util.StringList
        public boolean add(String str) {
            return super.add((a) str);
        }

        /* renamed from: a */
        public String set(int i, String str) {
            return (String) super.set(i, str);
        }

        /* renamed from: b */
        public void add(int i, String str) {
            super.add(i, str);
        }

        @Override // com.mijiasdk.bleserver.protocol.bouncycastle.util.StringList
        public String[] toStringArray() {
            String[] strArr = new String[size()];
            for (int i = 0; i != strArr.length; i++) {
                strArr[i] = (String) get(i);
            }
            return strArr;
        }

        @Override // com.mijiasdk.bleserver.protocol.bouncycastle.util.StringList
        public String[] toStringArray(int i, int i2) {
            String[] strArr = new String[i2 - i];
            for (int i3 = i; i3 != size() && i3 != i2; i3++) {
                strArr[i3 - i] = (String) get(i3);
            }
            return strArr;
        }
    }
}
