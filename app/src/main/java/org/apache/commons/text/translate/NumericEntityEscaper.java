package org.apache.commons.text.translate;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes5.dex */
public class NumericEntityEscaper extends CodePointTranslator {
    private final int b;
    private final int c;
    private final boolean d;

    private NumericEntityEscaper(int i, int i2, boolean z) {
        this.b = i;
        this.c = i2;
        this.d = z;
    }

    public NumericEntityEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }

    public static NumericEntityEscaper below(int i) {
        return outsideOf(i, Integer.MAX_VALUE);
    }

    public static NumericEntityEscaper above(int i) {
        return outsideOf(0, i);
    }

    public static NumericEntityEscaper between(int i, int i2) {
        return new NumericEntityEscaper(i, i2, true);
    }

    public static NumericEntityEscaper outsideOf(int i, int i2) {
        return new NumericEntityEscaper(i, i2, false);
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
        writer.write("&#");
        writer.write(Integer.toString(i, 10));
        writer.write(59);
        return true;
    }
}
