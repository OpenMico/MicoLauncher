package org.apache.commons.lang.builder;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

/* loaded from: classes5.dex */
public class ToStringBuilder {
    private static volatile ToStringStyle a = ToStringStyle.DEFAULT_STYLE;
    private final StringBuffer b;
    private final Object c;
    private final ToStringStyle d;

    public static ToStringStyle getDefaultStyle() {
        return a;
    }

    public static void setDefaultStyle(ToStringStyle toStringStyle) {
        if (toStringStyle != null) {
            a = toStringStyle;
            return;
        }
        throw new IllegalArgumentException("The style must not be null");
    }

    public static String reflectionToString(Object obj) {
        return ReflectionToStringBuilder.toString(obj);
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle);
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle, boolean z) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle, z, false, null);
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle, boolean z, Class cls) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle, z, false, cls);
    }

    public ToStringBuilder(Object obj) {
        this(obj, null, null);
    }

    public ToStringBuilder(Object obj, ToStringStyle toStringStyle) {
        this(obj, toStringStyle, null);
    }

    public ToStringBuilder(Object obj, ToStringStyle toStringStyle, StringBuffer stringBuffer) {
        toStringStyle = toStringStyle == null ? getDefaultStyle() : toStringStyle;
        stringBuffer = stringBuffer == null ? new StringBuffer(512) : stringBuffer;
        this.b = stringBuffer;
        this.d = toStringStyle;
        this.c = obj;
        toStringStyle.appendStart(stringBuffer, obj);
    }

    public ToStringBuilder append(boolean z) {
        this.d.append(this.b, (String) null, z);
        return this;
    }

    public ToStringBuilder append(boolean[] zArr) {
        this.d.append(this.b, (String) null, zArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(byte b) {
        this.d.append(this.b, (String) null, b);
        return this;
    }

    public ToStringBuilder append(byte[] bArr) {
        this.d.append(this.b, (String) null, bArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(char c) {
        this.d.append(this.b, (String) null, c);
        return this;
    }

    public ToStringBuilder append(char[] cArr) {
        this.d.append(this.b, (String) null, cArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(double d) {
        this.d.append(this.b, (String) null, d);
        return this;
    }

    public ToStringBuilder append(double[] dArr) {
        this.d.append(this.b, (String) null, dArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(float f) {
        this.d.append(this.b, (String) null, f);
        return this;
    }

    public ToStringBuilder append(float[] fArr) {
        this.d.append(this.b, (String) null, fArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(int i) {
        this.d.append(this.b, (String) null, i);
        return this;
    }

    public ToStringBuilder append(int[] iArr) {
        this.d.append(this.b, (String) null, iArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(long j) {
        this.d.append(this.b, (String) null, j);
        return this;
    }

    public ToStringBuilder append(long[] jArr) {
        this.d.append(this.b, (String) null, jArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(Object obj) {
        this.d.append(this.b, (String) null, obj, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(Object[] objArr) {
        this.d.append(this.b, (String) null, objArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(short s) {
        this.d.append(this.b, (String) null, s);
        return this;
    }

    public ToStringBuilder append(short[] sArr) {
        this.d.append(this.b, (String) null, sArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, boolean z) {
        this.d.append(this.b, str, z);
        return this;
    }

    public ToStringBuilder append(String str, boolean[] zArr) {
        this.d.append(this.b, str, zArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, boolean[] zArr, boolean z) {
        this.d.append(this.b, str, zArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, byte b) {
        this.d.append(this.b, str, b);
        return this;
    }

    public ToStringBuilder append(String str, byte[] bArr) {
        this.d.append(this.b, str, bArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, byte[] bArr, boolean z) {
        this.d.append(this.b, str, bArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, char c) {
        this.d.append(this.b, str, c);
        return this;
    }

    public ToStringBuilder append(String str, char[] cArr) {
        this.d.append(this.b, str, cArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, char[] cArr, boolean z) {
        this.d.append(this.b, str, cArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, double d) {
        this.d.append(this.b, str, d);
        return this;
    }

    public ToStringBuilder append(String str, double[] dArr) {
        this.d.append(this.b, str, dArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, double[] dArr, boolean z) {
        this.d.append(this.b, str, dArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, float f) {
        this.d.append(this.b, str, f);
        return this;
    }

    public ToStringBuilder append(String str, float[] fArr) {
        this.d.append(this.b, str, fArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, float[] fArr, boolean z) {
        this.d.append(this.b, str, fArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, int i) {
        this.d.append(this.b, str, i);
        return this;
    }

    public ToStringBuilder append(String str, int[] iArr) {
        this.d.append(this.b, str, iArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, int[] iArr, boolean z) {
        this.d.append(this.b, str, iArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, long j) {
        this.d.append(this.b, str, j);
        return this;
    }

    public ToStringBuilder append(String str, long[] jArr) {
        this.d.append(this.b, str, jArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, long[] jArr, boolean z) {
        this.d.append(this.b, str, jArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, Object obj) {
        this.d.append(this.b, str, obj, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, Object obj, boolean z) {
        this.d.append(this.b, str, obj, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, Object[] objArr) {
        this.d.append(this.b, str, objArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, Object[] objArr, boolean z) {
        this.d.append(this.b, str, objArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder append(String str, short s) {
        this.d.append(this.b, str, s);
        return this;
    }

    public ToStringBuilder append(String str, short[] sArr) {
        this.d.append(this.b, str, sArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, short[] sArr, boolean z) {
        this.d.append(this.b, str, sArr, BooleanUtils.toBooleanObject(z));
        return this;
    }

    public ToStringBuilder appendAsObjectToString(Object obj) {
        ObjectUtils.identityToString(getStringBuffer(), obj);
        return this;
    }

    public ToStringBuilder appendSuper(String str) {
        if (str != null) {
            this.d.appendSuper(this.b, str);
        }
        return this;
    }

    public ToStringBuilder appendToString(String str) {
        if (str != null) {
            this.d.appendToString(this.b, str);
        }
        return this;
    }

    public Object getObject() {
        return this.c;
    }

    public StringBuffer getStringBuffer() {
        return this.b;
    }

    public ToStringStyle getStyle() {
        return this.d;
    }

    public String toString() {
        if (getObject() == null) {
            getStringBuffer().append(getStyle().getNullText());
        } else {
            this.d.appendEnd(getStringBuffer(), getObject());
        }
        return getStringBuffer().toString();
    }
}
