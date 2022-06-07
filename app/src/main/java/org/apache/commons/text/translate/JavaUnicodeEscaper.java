package org.apache.commons.text.translate;

/* loaded from: classes5.dex */
public class JavaUnicodeEscaper extends UnicodeEscaper {
    public static JavaUnicodeEscaper above(int i) {
        return outsideOf(0, i);
    }

    public static JavaUnicodeEscaper below(int i) {
        return outsideOf(i, Integer.MAX_VALUE);
    }

    public static JavaUnicodeEscaper between(int i, int i2) {
        return new JavaUnicodeEscaper(i, i2, true);
    }

    public static JavaUnicodeEscaper outsideOf(int i, int i2) {
        return new JavaUnicodeEscaper(i, i2, false);
    }

    public JavaUnicodeEscaper(int i, int i2, boolean z) {
        super(i, i2, z);
    }

    @Override // org.apache.commons.text.translate.UnicodeEscaper
    protected String toUtf16Escape(int i) {
        char[] chars = Character.toChars(i);
        return "\\u" + hex(chars[0]) + "\\u" + hex(chars[1]);
    }
}
