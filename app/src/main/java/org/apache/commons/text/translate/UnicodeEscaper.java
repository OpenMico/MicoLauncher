package org.apache.commons.text.translate;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes5.dex */
public class UnicodeEscaper extends CodePointTranslator {
    private final int b;
    private final int c;
    private final boolean d;

    public UnicodeEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public UnicodeEscaper(int i, int i2, boolean z) {
        this.b = i;
        this.c = i2;
        this.d = z;
    }

    public static UnicodeEscaper below(int i) {
        return outsideOf(i, Integer.MAX_VALUE);
    }

    public static UnicodeEscaper above(int i) {
        return outsideOf(0, i);
    }

    public static UnicodeEscaper outsideOf(int i, int i2) {
        return new UnicodeEscaper(i, i2, false);
    }

    public static UnicodeEscaper between(int i, int i2) {
        return new UnicodeEscaper(i, i2, true);
    }

    @Override // org.apache.commons.text.translate.CodePointTranslator
    public boolean translate(int i, Writer writer) throws IOException {
        if (this.d) {
            if (i < this.b || i > this.c) {
                return false;
            }
        } else if (i >= this.b && i <= this.c) {
            return false;
        }
        if (i > 65535) {
            writer.write(toUtf16Escape(i));
            return true;
        }
        writer.write("\\u");
        writer.write(a[(i >> 12) & 15]);
        writer.write(a[(i >> 8) & 15]);
        writer.write(a[(i >> 4) & 15]);
        writer.write(a[i & 15]);
        return true;
    }

    protected String toUtf16Escape(int i) {
        return "\\u" + hex(i);
    }
}
