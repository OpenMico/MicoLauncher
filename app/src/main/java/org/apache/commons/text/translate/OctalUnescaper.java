package org.apache.commons.text.translate;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes5.dex */
public class OctalUnescaper extends CharSequenceTranslator {
    private boolean a(char c) {
        return c >= '0' && c <= '7';
    }

    private boolean b(char c) {
        return c >= '0' && c <= '3';
    }

    @Override // org.apache.commons.text.translate.CharSequenceTranslator
    public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        int length = (charSequence.length() - i) - 1;
        StringBuilder sb = new StringBuilder();
        if (charSequence.charAt(i) != '\\' || length <= 0) {
            return 0;
        }
        int i2 = i + 1;
        if (!a(charSequence.charAt(i2))) {
            return 0;
        }
        int i3 = i + 2;
        int i4 = i + 3;
        sb.append(charSequence.charAt(i2));
        if (length > 1 && a(charSequence.charAt(i3))) {
            sb.append(charSequence.charAt(i3));
            if (length > 2 && b(charSequence.charAt(i2)) && a(charSequence.charAt(i4))) {
                sb.append(charSequence.charAt(i4));
            }
        }
        writer.write(Integer.parseInt(sb.toString(), 8));
        return sb.length() + 1;
    }
}