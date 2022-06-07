package org.eclipse.jetty.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.util.Utf8StringBuilder;

/* loaded from: classes5.dex */
public class HttpURI {
    private static final int ASTERISK = 10;
    private static final int AUTH = 4;
    private static final int AUTH_OR_PATH = 1;
    private static final int IPV6 = 5;
    private static final int PARAM = 8;
    private static final int PATH = 7;
    private static final int PORT = 6;
    private static final int QUERY = 9;
    private static final int SCHEME_OR_PATH = 2;
    private static final int START = 0;
    private static final byte[] __empty = new byte[0];
    int _authority;
    boolean _encoded;
    int _end;
    int _fragment;
    int _host;
    int _param;
    boolean _partial;
    int _path;
    int _port;
    int _portValue;
    int _query;
    byte[] _raw;
    String _rawString;
    int _scheme;
    final Utf8StringBuilder _utf8b;

    public HttpURI() {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
    }

    public HttpURI(boolean z) {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        this._partial = z;
    }

    public HttpURI(String str) {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        this._rawString = str;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            parse(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public HttpURI(byte[] bArr, int i, int i2) {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        parse2(bArr, i, i2);
    }

    public HttpURI(URI uri) {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        parse(uri.toASCIIString());
    }

    public void parse(String str) {
        byte[] bytes = str.getBytes();
        parse2(bytes, 0, bytes.length);
        this._rawString = str;
    }

    public void parse(byte[] bArr, int i, int i2) {
        this._rawString = null;
        parse2(bArr, i, i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x006e, code lost:
        r7 = r6._port;
        r9 = r6._path;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0072, code lost:
        if (r7 >= r9) goto L_0x0086;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0074, code lost:
        r6._portValue = org.eclipse.jetty.util.TypeUtil.parseInt(r6._raw, r7 + 1, (r9 - r7) - 1, 10);
        r6._path = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0085, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x008d, code lost:
        throw new java.lang.IllegalArgumentException("No port");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parseConnect(byte[] r7, int r8, int r9) {
        /*
            r6 = this;
            r0 = 0
            r6._rawString = r0
            r0 = 0
            r6._encoded = r0
            r6._raw = r7
            int r7 = r8 + r9
            r6._end = r7
            r6._scheme = r8
            r6._authority = r8
            r6._host = r8
            int r0 = r6._end
            r6._port = r0
            r1 = -1
            r6._portValue = r1
            r6._path = r0
            r6._param = r0
            r6._query = r0
            r6._fragment = r0
            r0 = 4
            r1 = r8
            r2 = r0
        L_0x0024:
            if (r1 >= r7) goto L_0x006e
            byte[] r3 = r6._raw
            byte r3 = r3[r1]
            r3 = r3 & 255(0xff, float:3.57E-43)
            char r3 = (char) r3
            int r4 = r1 + 1
            switch(r2) {
                case 4: goto L_0x005f;
                case 5: goto L_0x0034;
                default: goto L_0x0032;
            }
        L_0x0032:
            r1 = r4
            goto L_0x0024
        L_0x0034:
            r1 = 47
            if (r3 == r1) goto L_0x0040
            r1 = 93
            if (r3 == r1) goto L_0x003d
            goto L_0x003e
        L_0x003d:
            r2 = r0
        L_0x003e:
            r1 = r4
            goto L_0x0024
        L_0x0040:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "No closing ']' for "
            r0.append(r1)
            byte[] r1 = r6._raw
            java.lang.String r2 = org.eclipse.jetty.util.URIUtil.__CHARSET
            java.lang.String r8 = org.eclipse.jetty.util.StringUtil.toString(r1, r8, r9, r2)
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            r7.<init>(r8)
            throw r7
        L_0x005f:
            r5 = 58
            if (r3 == r5) goto L_0x006c
            r1 = 91
            if (r3 == r1) goto L_0x0068
            goto L_0x006a
        L_0x0068:
            r1 = 5
            r2 = r1
        L_0x006a:
            r1 = r4
            goto L_0x0024
        L_0x006c:
            r6._port = r1
        L_0x006e:
            int r7 = r6._port
            int r9 = r6._path
            if (r7 >= r9) goto L_0x0086
            byte[] r0 = r6._raw
            int r1 = r7 + 1
            int r9 = r9 - r7
            int r9 = r9 + (-1)
            r7 = 10
            int r7 = org.eclipse.jetty.util.TypeUtil.parseInt(r0, r1, r9, r7)
            r6._portValue = r7
            r6._path = r8
            return
        L_0x0086:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "No port"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpURI.parseConnect(byte[], int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x013b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parse2(byte[] r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 496
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpURI.parse2(byte[], int, int):void");
    }

    private String toUtf8String(int i, int i2) {
        this._utf8b.reset();
        this._utf8b.append(this._raw, i, i2);
        return this._utf8b.toString();
    }

    public String getScheme() {
        int i = this._scheme;
        int i2 = this._authority;
        if (i == i2) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 == 5) {
            byte[] bArr = this._raw;
            if (bArr[i] == 104 && bArr[i + 1] == 116 && bArr[i + 2] == 116 && bArr[i + 3] == 112) {
                return "http";
            }
        }
        if (i3 == 6) {
            byte[] bArr2 = this._raw;
            int i4 = this._scheme;
            if (bArr2[i4] == 104 && bArr2[i4 + 1] == 116 && bArr2[i4 + 2] == 116 && bArr2[i4 + 3] == 112 && bArr2[i4 + 4] == 115) {
                return "https";
            }
        }
        int i5 = this._scheme;
        return toUtf8String(i5, (this._authority - i5) - 1);
    }

    public String getAuthority() {
        int i = this._authority;
        int i2 = this._path;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getHost() {
        int i = this._host;
        int i2 = this._port;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public int getPort() {
        return this._portValue;
    }

    public String getPath() {
        int i = this._path;
        int i2 = this._param;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getDecodedPath() {
        byte b;
        int i = this._path;
        int i2 = this._param;
        byte[] bArr = null;
        if (i == i2) {
            return null;
        }
        int i3 = i2 - i;
        int i4 = 0;
        while (true) {
            int i5 = this._param;
            if (i < i5) {
                byte[] bArr2 = this._raw;
                byte b2 = bArr2[i];
                if (b2 == 37) {
                    i += 2;
                    if (i < i5) {
                        b = (byte) (TypeUtil.parseInt(bArr2, i + 1, 2, 16) & 255);
                    } else {
                        throw new IllegalArgumentException("Bad % encoding: " + this);
                    }
                } else if (bArr == null) {
                    i4++;
                    i++;
                } else {
                    i = i;
                    b = b2;
                }
                if (bArr == null) {
                    bArr = new byte[i3];
                    System.arraycopy(this._raw, this._path, bArr, 0, i4);
                }
                i4++;
                bArr[i4] = b;
                i++;
            } else if (bArr == null) {
                return toUtf8String(this._path, i3);
            } else {
                this._utf8b.reset();
                this._utf8b.append(bArr, 0, i4);
                return this._utf8b.toString();
            }
        }
    }

    public String getDecodedPath(String str) {
        byte b;
        int i = this._path;
        int i2 = this._param;
        byte[] bArr = null;
        if (i == i2) {
            return null;
        }
        int i3 = i2 - i;
        int i4 = 0;
        while (true) {
            int i5 = this._param;
            if (i < i5) {
                byte[] bArr2 = this._raw;
                byte b2 = bArr2[i];
                if (b2 == 37) {
                    i += 2;
                    if (i < i5) {
                        b = (byte) (TypeUtil.parseInt(bArr2, i + 1, 2, 16) & 255);
                    } else {
                        throw new IllegalArgumentException("Bad % encoding: " + this);
                    }
                } else if (bArr == null) {
                    i4++;
                    i++;
                } else {
                    i = i;
                    b = b2;
                }
                if (bArr == null) {
                    bArr = new byte[i3];
                    System.arraycopy(this._raw, this._path, bArr, 0, i4);
                }
                i4++;
                bArr[i4] = b;
                i++;
            } else if (bArr != null) {
                return StringUtil.toString(bArr, 0, i4, str);
            } else {
                byte[] bArr3 = this._raw;
                int i6 = this._path;
                return StringUtil.toString(bArr3, i6, i5 - i6, str);
            }
        }
    }

    public String getPathAndParam() {
        int i = this._path;
        int i2 = this._query;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getCompletePath() {
        int i = this._path;
        int i2 = this._end;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getParam() {
        int i = this._param;
        int i2 = this._query;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i + 1, (i2 - i) - 1);
    }

    public String getQuery() {
        int i = this._query;
        int i2 = this._fragment;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i + 1, (i2 - i) - 1);
    }

    public String getQuery(String str) {
        int i = this._query;
        int i2 = this._fragment;
        if (i == i2) {
            return null;
        }
        return StringUtil.toString(this._raw, i + 1, (i2 - i) - 1, str);
    }

    public boolean hasQuery() {
        return this._fragment > this._query;
    }

    public String getFragment() {
        int i = this._fragment;
        int i2 = this._end;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i + 1, (i2 - i) - 1);
    }

    public void decodeQueryTo(MultiMap multiMap) {
        if (this._query != this._fragment) {
            this._utf8b.reset();
            byte[] bArr = this._raw;
            int i = this._query;
            UrlEncoded.decodeUtf8To(bArr, i + 1, (this._fragment - i) - 1, multiMap, this._utf8b);
        }
    }

    public void decodeQueryTo(MultiMap multiMap, String str) throws UnsupportedEncodingException {
        if (this._query != this._fragment) {
            if (str == null || StringUtil.isUTF8(str)) {
                byte[] bArr = this._raw;
                int i = this._query;
                UrlEncoded.decodeUtf8To(bArr, i + 1, (this._fragment - i) - 1, multiMap);
                return;
            }
            byte[] bArr2 = this._raw;
            int i2 = this._query;
            UrlEncoded.decodeTo(StringUtil.toString(bArr2, i2 + 1, (this._fragment - i2) - 1, str), multiMap, str);
        }
    }

    public void clear() {
        this._end = 0;
        this._fragment = 0;
        this._query = 0;
        this._param = 0;
        this._path = 0;
        this._port = 0;
        this._host = 0;
        this._authority = 0;
        this._scheme = 0;
        this._raw = __empty;
        this._rawString = "";
        this._encoded = false;
    }

    public String toString() {
        if (this._rawString == null) {
            int i = this._scheme;
            this._rawString = toUtf8String(i, this._end - i);
        }
        return this._rawString;
    }

    public void writeTo(Utf8StringBuilder utf8StringBuilder) {
        byte[] bArr = this._raw;
        int i = this._scheme;
        utf8StringBuilder.append(bArr, i, this._end - i);
    }
}
