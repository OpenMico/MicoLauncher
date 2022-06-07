package org.json;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/* loaded from: classes3.dex */
public class JSONTokener {
    private int a;
    private Reader b;
    private char c;
    private boolean d;

    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c < 'a' || c > 'f') {
            return -1;
        }
        return c - 'W';
    }

    public JSONTokener(Reader reader) {
        this.b = !reader.markSupported() ? new BufferedReader(reader) : reader;
        this.d = false;
        this.a = 0;
    }

    public JSONTokener(String str) {
        this(new StringReader(str));
    }

    public void back() throws JSONException {
        int i;
        if (this.d || (i = this.a) <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.a = i - 1;
        this.d = true;
    }

    public boolean more() throws JSONException {
        if (next() == 0) {
            return false;
        }
        back();
        return true;
    }

    public char next() throws JSONException {
        if (this.d) {
            this.d = false;
            if (this.c != 0) {
                this.a++;
            }
            return this.c;
        }
        try {
            int read = this.b.read();
            if (read <= 0) {
                this.c = (char) 0;
                return (char) 0;
            }
            this.a++;
            this.c = (char) read;
            return this.c;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public char next(char c) throws JSONException {
        char next = next();
        if (next == c) {
            return next;
        }
        throw syntaxError("Expected '" + c + "' and instead saw '" + next + LrcRow.SINGLE_QUOTE);
    }

    public String next(int i) throws JSONException {
        if (i == 0) {
            return "";
        }
        char[] cArr = new char[i];
        int i2 = 0;
        if (this.d) {
            this.d = false;
            cArr[0] = this.c;
            i2 = 1;
        }
        while (i2 < i) {
            try {
                int read = this.b.read(cArr, i2, i - i2);
                if (read == -1) {
                    break;
                }
                i2 += read;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        this.a += i2;
        if (i2 >= i) {
            this.c = cArr[i - 1];
            return new String(cArr);
        }
        throw syntaxError("Substring bounds error");
    }

    public char nextClean() throws JSONException {
        char next;
        do {
            next = next();
            if (next == 0) {
                break;
            }
        } while (next <= ' ');
        return next;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x007f, code lost:
        throw syntaxError("Unterminated string");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String nextString(char r6) throws org.json.JSONException {
        /*
            r5 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            char r1 = r5.next()
            if (r1 == 0) goto L_0x0079
            r2 = 10
            if (r1 == r2) goto L_0x0079
            r3 = 13
            if (r1 == r3) goto L_0x0079
            r4 = 92
            if (r1 == r4) goto L_0x0022
            if (r1 != r6) goto L_0x001e
            java.lang.String r6 = r0.toString()
            return r6
        L_0x001e:
            r0.append(r1)
            goto L_0x0005
        L_0x0022:
            char r1 = r5.next()
            r4 = 98
            if (r1 == r4) goto L_0x0073
            r4 = 102(0x66, float:1.43E-43)
            if (r1 == r4) goto L_0x006d
            r4 = 110(0x6e, float:1.54E-43)
            if (r1 == r4) goto L_0x0069
            r2 = 114(0x72, float:1.6E-43)
            if (r1 == r2) goto L_0x0065
            r2 = 120(0x78, float:1.68E-43)
            r3 = 16
            if (r1 == r2) goto L_0x0057
            switch(r1) {
                case 116: goto L_0x0051;
                case 117: goto L_0x0043;
                default: goto L_0x003f;
            }
        L_0x003f:
            r0.append(r1)
            goto L_0x0005
        L_0x0043:
            r1 = 4
            java.lang.String r1 = r5.next(r1)
            int r1 = java.lang.Integer.parseInt(r1, r3)
            char r1 = (char) r1
            r0.append(r1)
            goto L_0x0005
        L_0x0051:
            r1 = 9
            r0.append(r1)
            goto L_0x0005
        L_0x0057:
            r1 = 2
            java.lang.String r1 = r5.next(r1)
            int r1 = java.lang.Integer.parseInt(r1, r3)
            char r1 = (char) r1
            r0.append(r1)
            goto L_0x0005
        L_0x0065:
            r0.append(r3)
            goto L_0x0005
        L_0x0069:
            r0.append(r2)
            goto L_0x0005
        L_0x006d:
            r1 = 12
            r0.append(r1)
            goto L_0x0005
        L_0x0073:
            r1 = 8
            r0.append(r1)
            goto L_0x0005
        L_0x0079:
            java.lang.String r6 = "Unterminated string"
            org.json.JSONException r6 = r5.syntaxError(r6)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONTokener.nextString(char):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001c, code lost:
        back();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String nextTo(char r4) throws org.json.JSONException {
        /*
            r3 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            char r1 = r3.next()
            if (r1 == r4) goto L_0x001a
            if (r1 == 0) goto L_0x001a
            r2 = 10
            if (r1 == r2) goto L_0x001a
            r2 = 13
            if (r1 != r2) goto L_0x0016
            goto L_0x001a
        L_0x0016:
            r0.append(r1)
            goto L_0x0005
        L_0x001a:
            if (r1 == 0) goto L_0x001f
            r3.back()
        L_0x001f:
            java.lang.String r4 = r0.toString()
            java.lang.String r4 = r4.trim()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONTokener.nextTo(char):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
        back();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String nextTo(java.lang.String r4) throws org.json.JSONException {
        /*
            r3 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            char r1 = r3.next()
            int r2 = r4.indexOf(r1)
            if (r2 >= 0) goto L_0x001e
            if (r1 == 0) goto L_0x001e
            r2 = 10
            if (r1 == r2) goto L_0x001e
            r2 = 13
            if (r1 != r2) goto L_0x001a
            goto L_0x001e
        L_0x001a:
            r0.append(r1)
            goto L_0x0005
        L_0x001e:
            if (r1 == 0) goto L_0x0023
            r3.back()
        L_0x0023:
            java.lang.String r4 = r0.toString()
            java.lang.String r4 = r4.trim()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONTokener.nextTo(java.lang.String):java.lang.String");
    }

    public Object nextValue() throws JSONException {
        char nextClean = nextClean();
        if (nextClean != '\"') {
            if (nextClean != '[') {
                if (nextClean != '{') {
                    switch (nextClean) {
                        case '\'':
                            break;
                        case '(':
                            break;
                        default:
                            StringBuffer stringBuffer = new StringBuffer();
                            while (nextClean >= ' ' && ",:]}/\\\"[{;=#".indexOf(nextClean) < 0) {
                                stringBuffer.append(nextClean);
                                nextClean = next();
                            }
                            back();
                            String trim = stringBuffer.toString().trim();
                            if (!trim.equals("")) {
                                return JSONObject.stringToValue(trim);
                            }
                            throw syntaxError("Missing value");
                    }
                } else {
                    back();
                    return new JSONObject(this);
                }
            }
            back();
            return new JSONArray(this);
        }
        return nextString(nextClean);
    }

    public char skipTo(char c) throws JSONException {
        char next;
        try {
            int i = this.a;
            this.b.mark(Integer.MAX_VALUE);
            do {
                next = next();
                if (next == 0) {
                    this.b.reset();
                    this.a = i;
                    return next;
                }
            } while (next != c);
            back();
            return next;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public JSONException syntaxError(String str) {
        return new JSONException(str + toString());
    }

    public String toString() {
        return " at character " + this.a;
    }
}
