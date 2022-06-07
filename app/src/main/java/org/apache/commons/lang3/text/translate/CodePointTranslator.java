package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes5.dex */
public abstract class CodePointTranslator extends CharSequenceTranslator {
    public abstract boolean translate(int i, Writer writer) throws IOException;

    @Override // org.apache.commons.lang3.text.translate.CharSequenceTranslator
    public final int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        return translate(Character.codePointAt(charSequence, i), writer) ? 1 : 0;
    }
}
