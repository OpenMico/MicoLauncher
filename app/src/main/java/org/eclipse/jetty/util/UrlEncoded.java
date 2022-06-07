package org.eclipse.jetty.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import kotlin.text.Typography;
import org.eclipse.jetty.util.Utf8Appendable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class UrlEncoded extends MultiMap implements Cloneable {
    private static final Logger LOG = Log.getLogger(UrlEncoded.class);
    public static final String ENCODING = System.getProperty("org.eclipse.jetty.util.UrlEncoding.charset", "UTF-8");

    public UrlEncoded(UrlEncoded urlEncoded) {
        super((MultiMap) urlEncoded);
    }

    public UrlEncoded() {
        super(6);
    }

    public UrlEncoded(String str) {
        super(6);
        decode(str, ENCODING);
    }

    public UrlEncoded(String str, String str2) {
        super(6);
        decode(str, str2);
    }

    public void decode(String str) {
        decodeTo(str, this, ENCODING, -1);
    }

    public void decode(String str, String str2) {
        decodeTo(str, this, str2, -1);
    }

    public String encode() {
        return encode(ENCODING, false);
    }

    public String encode(String str) {
        return encode(str, false);
    }

    public synchronized String encode(String str, boolean z) {
        return encode(this, str, z);
    }

    public static String encode(MultiMap multiMap, String str, boolean z) {
        if (str == null) {
            str = ENCODING;
        }
        StringBuilder sb = new StringBuilder(128);
        Iterator it = multiMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String obj = entry.getKey().toString();
            Object value = entry.getValue();
            int size = LazyList.size(value);
            if (size == 0) {
                sb.append(encodeString(obj, str));
                if (z) {
                    sb.append('=');
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (i > 0) {
                        sb.append(Typography.amp);
                    }
                    Object obj2 = LazyList.get(value, i);
                    sb.append(encodeString(obj, str));
                    if (obj2 != null) {
                        String obj3 = obj2.toString();
                        if (obj3.length() > 0) {
                            sb.append('=');
                            sb.append(encodeString(obj3, str));
                        } else if (z) {
                            sb.append('=');
                        }
                    } else if (z) {
                        sb.append('=');
                    }
                }
            }
            if (it.hasNext()) {
                sb.append(Typography.amp);
            }
        }
        return sb.toString();
    }

    public static void decodeTo(String str, MultiMap multiMap, String str2) {
        decodeTo(str, multiMap, str2, -1);
    }

    public static void decodeTo(String str, MultiMap multiMap, String str2, int i) {
        String str3;
        String str4;
        if (str2 == null) {
            str2 = ENCODING;
        }
        synchronized (multiMap) {
            int i2 = -1;
            String str5 = null;
            boolean z = false;
            for (int i3 = 0; i3 < str.length(); i3++) {
                char charAt = str.charAt(i3);
                if (charAt == '+') {
                    z = true;
                } else if (charAt != '=') {
                    switch (charAt) {
                        case '%':
                            z = true;
                            continue;
                        case '&':
                            int i4 = (i3 - i2) - 1;
                            if (i4 == 0) {
                                str4 = "";
                            } else {
                                str4 = z ? decodeString(str, i2 + 1, i4, str2) : str.substring(i2 + 1, i3);
                            }
                            if (str5 != null) {
                                multiMap.add(str5, str4);
                            } else if (str4 != null && str4.length() > 0) {
                                multiMap.add(str4, "");
                            }
                            if (i <= 0 || multiMap.size() <= i) {
                                i2 = i3;
                                str5 = null;
                                z = false;
                                continue;
                            } else {
                                LOG.warn("maxFormKeys limit exceeded keys>{}", Integer.valueOf(i));
                                return;
                            }
                        default:
                            continue;
                    }
                } else if (str5 == null) {
                    str5 = z ? decodeString(str, i2 + 1, (i3 - i2) - 1, str2) : str.substring(i2 + 1, i3);
                    i2 = i3;
                    z = false;
                }
            }
            if (str5 != null) {
                int length = (str.length() - i2) - 1;
                if (length == 0) {
                    str3 = "";
                } else {
                    str3 = z ? decodeString(str, i2 + 1, length, str2) : str.substring(i2 + 1);
                }
                multiMap.add(str5, str3);
            } else if (i2 < str.length()) {
                String decodeString = z ? decodeString(str, i2 + 1, (str.length() - i2) - 1, str2) : str.substring(i2 + 1);
                if (decodeString != null && decodeString.length() > 0) {
                    multiMap.add(decodeString, "");
                }
            }
        }
    }

    public static void decodeUtf8To(byte[] bArr, int i, int i2, MultiMap multiMap) {
        decodeUtf8To(bArr, i, i2, multiMap, new Utf8StringBuilder());
    }

    public static void decodeUtf8To(byte[] bArr, int i, int i2, MultiMap multiMap, Utf8StringBuilder utf8StringBuilder) {
        synchronized (multiMap) {
            int i3 = i2 + i;
            String str = null;
            while (i < i3) {
                try {
                    byte b = bArr[i];
                    char c = (char) (b & 255);
                    if (c == '+') {
                        utf8StringBuilder.append((byte) 32);
                    } else if (c != '=') {
                        switch (c) {
                            case '%':
                                if (i + 2 < i3) {
                                    int i4 = i + 1;
                                    i = i4 + 1;
                                    utf8StringBuilder.append((byte) ((TypeUtil.convertHexDigit(bArr[i4]) << 4) + TypeUtil.convertHexDigit(bArr[i])));
                                    break;
                                } else {
                                    continue;
                                }
                            case '&':
                                String utf8StringBuilder2 = utf8StringBuilder.length() == 0 ? "" : utf8StringBuilder.toString();
                                utf8StringBuilder.reset();
                                if (str != null) {
                                    multiMap.add(str, utf8StringBuilder2);
                                } else if (utf8StringBuilder2 != null && utf8StringBuilder2.length() > 0) {
                                    multiMap.add(utf8StringBuilder2, "");
                                }
                                str = null;
                                continue;
                            default:
                                try {
                                    utf8StringBuilder.append(b);
                                    continue;
                                } catch (Utf8Appendable.NotUtf8Exception e) {
                                    LOG.warn(e.toString(), new Object[0]);
                                    LOG.debug(e);
                                    break;
                                }
                        }
                    } else if (str != null) {
                        utf8StringBuilder.append(b);
                    } else {
                        str = utf8StringBuilder.toString();
                        utf8StringBuilder.reset();
                    }
                    i++;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (str != null) {
                String utf8StringBuilder3 = utf8StringBuilder.length() == 0 ? "" : utf8StringBuilder.toString();
                utf8StringBuilder.reset();
                multiMap.add(str, utf8StringBuilder3);
            } else if (utf8StringBuilder.length() > 0) {
                multiMap.add(utf8StringBuilder.toString(), "");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0094, code lost:
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0096, code lost:
        if (r4 > r9) goto L_0x009a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a1, code lost:
        throw new java.lang.IllegalStateException("Form too large");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void decode88591To(java.io.InputStream r7, org.eclipse.jetty.util.MultiMap r8, int r9, int r10) throws java.io.IOException {
        /*
            monitor-enter(r8)
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch: all -> 0x00c9
            r0.<init>()     // Catch: all -> 0x00c9
            r1 = 0
            r2 = 0
            r3 = r1
            r4 = r2
        L_0x000a:
            int r5 = r7.read()     // Catch: all -> 0x00c9
            if (r5 < 0) goto L_0x00a2
            char r5 = (char) r5     // Catch: all -> 0x00c9
            r6 = 43
            if (r5 == r6) goto L_0x008d
            r6 = 61
            if (r5 == r6) goto L_0x007f
            switch(r5) {
                case 37: goto L_0x0060;
                case 38: goto L_0x0021;
                default: goto L_0x001c;
            }     // Catch: all -> 0x00c9
        L_0x001c:
            r0.append(r5)     // Catch: all -> 0x00c9
            goto L_0x0092
        L_0x0021:
            int r5 = r0.length()     // Catch: all -> 0x00c9
            if (r5 != 0) goto L_0x002a
            java.lang.String r5 = ""
            goto L_0x002e
        L_0x002a:
            java.lang.String r5 = r0.toString()     // Catch: all -> 0x00c9
        L_0x002e:
            r0.setLength(r2)     // Catch: all -> 0x00c9
            if (r3 == 0) goto L_0x0037
            r8.add(r3, r5)     // Catch: all -> 0x00c9
            goto L_0x0044
        L_0x0037:
            if (r5 == 0) goto L_0x0044
            int r3 = r5.length()     // Catch: all -> 0x00c9
            if (r3 <= 0) goto L_0x0044
            java.lang.String r3 = ""
            r8.add(r5, r3)     // Catch: all -> 0x00c9
        L_0x0044:
            if (r10 <= 0) goto L_0x005e
            int r3 = r8.size()     // Catch: all -> 0x00c9
            if (r3 <= r10) goto L_0x005e
            org.eclipse.jetty.util.log.Logger r7 = org.eclipse.jetty.util.UrlEncoded.LOG     // Catch: all -> 0x00c9
            java.lang.String r9 = "maxFormKeys limit exceeded keys>{}"
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: all -> 0x00c9
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch: all -> 0x00c9
            r0[r2] = r10     // Catch: all -> 0x00c9
            r7.warn(r9, r0)     // Catch: all -> 0x00c9
            monitor-exit(r8)     // Catch: all -> 0x00c9
            return
        L_0x005e:
            r3 = r1
            goto L_0x0092
        L_0x0060:
            int r5 = r7.read()     // Catch: all -> 0x00c9
            int r6 = r7.read()     // Catch: all -> 0x00c9
            if (r5 < 0) goto L_0x0092
            if (r6 >= 0) goto L_0x006d
            goto L_0x0092
        L_0x006d:
            byte r5 = (byte) r5     // Catch: all -> 0x00c9
            byte r5 = org.eclipse.jetty.util.TypeUtil.convertHexDigit(r5)     // Catch: all -> 0x00c9
            int r5 = r5 << 4
            byte r6 = (byte) r6     // Catch: all -> 0x00c9
            byte r6 = org.eclipse.jetty.util.TypeUtil.convertHexDigit(r6)     // Catch: all -> 0x00c9
            int r5 = r5 + r6
            char r5 = (char) r5     // Catch: all -> 0x00c9
            r0.append(r5)     // Catch: all -> 0x00c9
            goto L_0x0092
        L_0x007f:
            if (r3 == 0) goto L_0x0085
            r0.append(r5)     // Catch: all -> 0x00c9
            goto L_0x0092
        L_0x0085:
            java.lang.String r3 = r0.toString()     // Catch: all -> 0x00c9
            r0.setLength(r2)     // Catch: all -> 0x00c9
            goto L_0x0092
        L_0x008d:
            r5 = 32
            r0.append(r5)     // Catch: all -> 0x00c9
        L_0x0092:
            if (r9 < 0) goto L_0x000a
            int r4 = r4 + 1
            if (r4 > r9) goto L_0x009a
            goto L_0x000a
        L_0x009a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch: all -> 0x00c9
            java.lang.String r9 = "Form too large"
            r7.<init>(r9)     // Catch: all -> 0x00c9
            throw r7     // Catch: all -> 0x00c9
        L_0x00a2:
            if (r3 == 0) goto L_0x00b8
            int r7 = r0.length()     // Catch: all -> 0x00c9
            if (r7 != 0) goto L_0x00ad
            java.lang.String r7 = ""
            goto L_0x00b1
        L_0x00ad:
            java.lang.String r7 = r0.toString()     // Catch: all -> 0x00c9
        L_0x00b1:
            r0.setLength(r2)     // Catch: all -> 0x00c9
            r8.add(r3, r7)     // Catch: all -> 0x00c9
            goto L_0x00c7
        L_0x00b8:
            int r7 = r0.length()     // Catch: all -> 0x00c9
            if (r7 <= 0) goto L_0x00c7
            java.lang.String r7 = r0.toString()     // Catch: all -> 0x00c9
            java.lang.String r9 = ""
            r8.add(r7, r9)     // Catch: all -> 0x00c9
        L_0x00c7:
            monitor-exit(r8)     // Catch: all -> 0x00c9
            return
        L_0x00c9:
            r7 = move-exception
            monitor-exit(r8)     // Catch: all -> 0x00c9
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decode88591To(java.io.InputStream, org.eclipse.jetty.util.MultiMap, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ad, code lost:
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00af, code lost:
        if (r4 > r11) goto L_0x00b3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ba, code lost:
        throw new java.lang.IllegalStateException("Form too large");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void decodeUtf8To(java.io.InputStream r9, org.eclipse.jetty.util.MultiMap r10, int r11, int r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decodeUtf8To(java.io.InputStream, org.eclipse.jetty.util.MultiMap, int, int):void");
    }

    public static void decodeUtf16To(InputStream inputStream, MultiMap multiMap, int i, int i2) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-16");
        StringWriter stringWriter = new StringWriter(8192);
        IO.copy(inputStreamReader, stringWriter, i);
        decodeTo(stringWriter.getBuffer().toString(), multiMap, ENCODING, i2);
    }

    public static void decodeTo(InputStream inputStream, MultiMap multiMap, String str, int i, int i2) throws IOException {
        if (str == null) {
            str = ENCODING;
        }
        if ("UTF-8".equalsIgnoreCase(str)) {
            decodeUtf8To(inputStream, multiMap, i, i2);
        } else if ("ISO-8859-1".equals(str)) {
            decode88591To(inputStream, multiMap, i, i2);
        } else if ("UTF-16".equalsIgnoreCase(str)) {
            decodeUtf16To(inputStream, multiMap, i, i2);
        } else {
            synchronized (multiMap) {
                ByteArrayOutputStream2 byteArrayOutputStream2 = new ByteArrayOutputStream2();
                String str2 = null;
                boolean z = false;
                byte b = 0;
                int i3 = 0;
                while (true) {
                    int read = inputStream.read();
                    if (read > 0) {
                        char c = (char) read;
                        if (c == '+') {
                            byteArrayOutputStream2.write(32);
                        } else if (c != '=') {
                            switch (c) {
                                case '%':
                                    z = true;
                                    break;
                                case '&':
                                    String byteArrayOutputStream22 = byteArrayOutputStream2.size() == 0 ? "" : byteArrayOutputStream2.toString(str);
                                    byteArrayOutputStream2.setCount(0);
                                    if (str2 != null) {
                                        multiMap.add(str2, byteArrayOutputStream22);
                                    } else if (byteArrayOutputStream22 != null && byteArrayOutputStream22.length() > 0) {
                                        multiMap.add(byteArrayOutputStream22, "");
                                    }
                                    str2 = null;
                                    break;
                                default:
                                    if (!z) {
                                        if (!z) {
                                            byteArrayOutputStream2.write(read);
                                            break;
                                        } else {
                                            byteArrayOutputStream2.write((b << 4) + TypeUtil.convertHexDigit((byte) read));
                                            z = false;
                                            break;
                                        }
                                    } else {
                                        b = TypeUtil.convertHexDigit((byte) read);
                                        z = true;
                                        break;
                                    }
                            }
                        } else if (str2 != null) {
                            byteArrayOutputStream2.write(read);
                        } else {
                            str2 = byteArrayOutputStream2.size() == 0 ? "" : byteArrayOutputStream2.toString(str);
                            byteArrayOutputStream2.setCount(0);
                        }
                        i3++;
                        if (i >= 0 && i3 > i) {
                            throw new IllegalStateException("Form too large");
                        }
                    } else {
                        int size = byteArrayOutputStream2.size();
                        if (str2 != null) {
                            String byteArrayOutputStream23 = size == 0 ? "" : byteArrayOutputStream2.toString(str);
                            byteArrayOutputStream2.setCount(0);
                            multiMap.add(str2, byteArrayOutputStream23);
                        } else if (size > 0) {
                            multiMap.add(byteArrayOutputStream2.toString(str), "");
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bd, code lost:
        r12 = new java.lang.StringBuffer(r19);
        r12.append((java.lang.CharSequence) r17, r18, r13 + 1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String decodeString(java.lang.String r17, int r18, int r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decodeString(java.lang.String, int, int, java.lang.String):java.lang.String");
    }

    public static String encodeString(String str) {
        return encodeString(str, ENCODING);
    }

    public static String encodeString(String str, String str2) {
        byte[] bArr;
        int i;
        if (str2 == null) {
            str2 = ENCODING;
        }
        try {
            bArr = str.getBytes(str2);
        } catch (UnsupportedEncodingException unused) {
            bArr = str.getBytes();
        }
        byte[] bArr2 = new byte[bArr.length * 3];
        boolean z = true;
        int i2 = 0;
        for (byte b : bArr) {
            if (b == 32) {
                i2++;
                bArr2[i2] = 43;
                z = false;
            } else if ((b < 97 || b > 122) && ((b < 65 || b > 90) && (b < 48 || b > 57))) {
                int i3 = i2 + 1;
                bArr2[i2] = 37;
                byte b2 = (byte) ((b & 240) >> 4);
                if (b2 >= 10) {
                    i = i3 + 1;
                    bArr2[i3] = (byte) ((b2 + 65) - 10);
                } else {
                    i = i3 + 1;
                    bArr2[i3] = (byte) (b2 + 48);
                }
                byte b3 = (byte) (b & 15);
                if (b3 >= 10) {
                    i2 = i + 1;
                    bArr2[i] = (byte) ((b3 + 65) - 10);
                    z = false;
                } else {
                    i2 = i + 1;
                    bArr2[i] = (byte) (b3 + 48);
                    z = false;
                }
            } else {
                i2++;
                bArr2[i2] = b;
            }
        }
        if (z) {
            return str;
        }
        try {
            return new String(bArr2, 0, i2, str2);
        } catch (UnsupportedEncodingException unused2) {
            return new String(bArr2, 0, i2);
        }
    }

    public Object clone() {
        return new UrlEncoded(this);
    }
}
