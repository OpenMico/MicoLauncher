package com.xiaomi.smarthome.library.apache.http.message;

import com.xiaomi.smarthome.library.apache.http.NameValuePair;
import com.xiaomi.smarthome.library.apache.http.util.CharArrayBuffer;
import com.xiaomi.smarthome.library.apache.http.util.LangUtils;

/* loaded from: classes4.dex */
public class BasicNameValuePair implements NameValuePair, Cloneable {
    private final String a;
    private final String b;

    public BasicNameValuePair(String str, String str2) {
        if (str != null) {
            this.a = str;
            this.b = str2;
            return;
        }
        throw new IllegalArgumentException("Name may not be null");
    }

    @Override // com.xiaomi.smarthome.library.apache.http.NameValuePair
    public String getName() {
        return this.a;
    }

    @Override // com.xiaomi.smarthome.library.apache.http.NameValuePair
    public String getValue() {
        return this.b;
    }

    public String toString() {
        int length = this.a.length();
        String str = this.b;
        if (str != null) {
            length += str.length() + 1;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(length);
        charArrayBuffer.append(this.a);
        if (this.b != null) {
            charArrayBuffer.append("=");
            charArrayBuffer.append(this.b);
        }
        return charArrayBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NameValuePair)) {
            return false;
        }
        BasicNameValuePair basicNameValuePair = (BasicNameValuePair) obj;
        return this.a.equals(basicNameValuePair.a) && LangUtils.equals(this.b, basicNameValuePair.b);
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.a), this.b);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
