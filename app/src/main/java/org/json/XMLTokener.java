package org.json;

import java.util.HashMap;

/* loaded from: classes3.dex */
public class XMLTokener extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String str) {
        super(str);
    }

    public String nextCDATA() throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            char next = next();
            if (next != 0) {
                stringBuffer.append(next);
                int length = stringBuffer.length() - 3;
                if (length >= 0 && stringBuffer.charAt(length) == ']' && stringBuffer.charAt(length + 1) == ']' && stringBuffer.charAt(length + 2) == '>') {
                    stringBuffer.setLength(length);
                    return stringBuffer.toString();
                }
            } else {
                throw syntaxError("Unclosed CDATA");
            }
        }
    }

    public Object nextContent() throws JSONException {
        char next;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        if (next == 0) {
            return null;
        }
        if (next == '<') {
            return XML.LT;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (next != '<' && next != 0) {
            if (next == '&') {
                stringBuffer.append(nextEntity(next));
            } else {
                stringBuffer.append(next);
            }
            next = next();
        }
        back();
        return stringBuffer.toString().trim();
    }

    public Object nextEntity(char c) throws JSONException {
        char next;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            next = next();
            if (!Character.isLetterOrDigit(next) && next != '#') {
                break;
            }
            stringBuffer.append(Character.toLowerCase(next));
        }
        if (next == ';') {
            String stringBuffer2 = stringBuffer.toString();
            Object obj = entity.get(stringBuffer2);
            if (obj != null) {
                return obj;
            }
            return c + stringBuffer2 + ";";
        }
        throw syntaxError("Missing ';' in XML entity: &" + ((Object) stringBuffer));
    }

    public Object nextMeta() throws JSONException {
        char next;
        char next2;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        if (next != 0) {
            if (next != '\'') {
                if (next == '/') {
                    return XML.SLASH;
                }
                switch (next) {
                    case '!':
                        return XML.BANG;
                    case '\"':
                        break;
                    default:
                        switch (next) {
                            case '<':
                                return XML.LT;
                            case '=':
                                return XML.EQ;
                            case '>':
                                return XML.GT;
                            case '?':
                                return XML.QUEST;
                        }
                        while (true) {
                            char next3 = next();
                            if (Character.isWhitespace(next3)) {
                                return Boolean.TRUE;
                            }
                            if (!(next3 == 0 || next3 == '\'' || next3 == '/')) {
                                switch (next3) {
                                    case '!':
                                    case '\"':
                                        break;
                                    default:
                                        switch (next3) {
                                        }
                                }
                            }
                        }
                        back();
                        return Boolean.TRUE;
                }
            }
            do {
                next2 = next();
                if (next2 == 0) {
                    throw syntaxError("Unterminated string");
                }
            } while (next2 != next);
            return Boolean.TRUE;
        }
        throw syntaxError("Misshaped meta tag");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x004d, code lost:
        return r3.toString();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object nextToken() throws org.json.JSONException {
        /*
            r5 = this;
        L_0x0000:
            char r0 = r5.next()
            boolean r1 = java.lang.Character.isWhitespace(r0)
            if (r1 != 0) goto L_0x0000
            if (r0 == 0) goto L_0x0099
            r1 = 39
            if (r0 == r1) goto L_0x0070
            r2 = 47
            if (r0 == r2) goto L_0x006d
            switch(r0) {
                case 33: goto L_0x006a;
                case 34: goto L_0x0070;
                default: goto L_0x0017;
            }
        L_0x0017:
            switch(r0) {
                case 60: goto L_0x0063;
                case 61: goto L_0x0060;
                case 62: goto L_0x005d;
                case 63: goto L_0x005a;
                default: goto L_0x001a;
            }
        L_0x001a:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
        L_0x001f:
            r3.append(r0)
            char r0 = r5.next()
            boolean r4 = java.lang.Character.isWhitespace(r0)
            if (r4 == 0) goto L_0x0031
            java.lang.String r0 = r3.toString()
            return r0
        L_0x0031:
            if (r0 == 0) goto L_0x0055
            if (r0 == r1) goto L_0x004e
            if (r0 == r2) goto L_0x0046
            r4 = 91
            if (r0 == r4) goto L_0x0046
            r4 = 93
            if (r0 == r4) goto L_0x0046
            switch(r0) {
                case 33: goto L_0x0046;
                case 34: goto L_0x004e;
                default: goto L_0x0042;
            }
        L_0x0042:
            switch(r0) {
                case 60: goto L_0x004e;
                case 61: goto L_0x0046;
                case 62: goto L_0x0046;
                case 63: goto L_0x0046;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x001f
        L_0x0046:
            r5.back()
            java.lang.String r0 = r3.toString()
            return r0
        L_0x004e:
            java.lang.String r0 = "Bad character in a name"
            org.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        L_0x0055:
            java.lang.String r0 = r3.toString()
            return r0
        L_0x005a:
            java.lang.Character r0 = org.json.XML.QUEST
            return r0
        L_0x005d:
            java.lang.Character r0 = org.json.XML.GT
            return r0
        L_0x0060:
            java.lang.Character r0 = org.json.XML.EQ
            return r0
        L_0x0063:
            java.lang.String r0 = "Misplaced '<'"
            org.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        L_0x006a:
            java.lang.Character r0 = org.json.XML.BANG
            return r0
        L_0x006d:
            java.lang.Character r0 = org.json.XML.SLASH
            return r0
        L_0x0070:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
        L_0x0075:
            char r2 = r5.next()
            if (r2 == 0) goto L_0x0092
            if (r2 != r0) goto L_0x0082
            java.lang.String r0 = r1.toString()
            return r0
        L_0x0082:
            r3 = 38
            if (r2 != r3) goto L_0x008e
            java.lang.Object r2 = r5.nextEntity(r2)
            r1.append(r2)
            goto L_0x0075
        L_0x008e:
            r1.append(r2)
            goto L_0x0075
        L_0x0092:
            java.lang.String r0 = "Unterminated string"
            org.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        L_0x0099:
            java.lang.String r0 = "Misshaped element"
            org.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.XMLTokener.nextToken():java.lang.Object");
    }

    public boolean skipPast(String str) throws JSONException {
        boolean z;
        int length = str.length();
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            char next = next();
            if (next == 0) {
                return false;
            }
            cArr[i] = next;
        }
        int i2 = 0;
        while (true) {
            int i3 = 0;
            int i4 = i2;
            while (true) {
                if (i3 >= length) {
                    z = true;
                    break;
                } else if (cArr[i4] != str.charAt(i3)) {
                    z = false;
                    break;
                } else {
                    i4++;
                    if (i4 >= length) {
                        i4 -= length;
                    }
                    i3++;
                }
            }
            if (z) {
                return true;
            }
            char next2 = next();
            if (next2 == 0) {
                return false;
            }
            cArr[i2] = next2;
            i2++;
            if (i2 >= length) {
                i2 -= length;
            }
        }
    }
}
