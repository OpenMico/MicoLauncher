package org.apache.commons.text.translate;

import java.io.IOException;
import java.io.Writer;

/* compiled from: SinglePassTranslator.java */
/* loaded from: classes5.dex */
abstract class a extends CharSequenceTranslator {
    abstract void a(CharSequence charSequence, Writer writer) throws IOException;

    @Override // org.apache.commons.text.translate.CharSequenceTranslator
    public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        if (i == 0) {
            a(charSequence, writer);
            return Character.codePointCount(charSequence, i, charSequence.length());
        }
        throw new IllegalArgumentException(a() + ".translate(final CharSequence input, final int index, final Writer out) can not handle a non-zero index.");
    }

    private String a() {
        Class<?> cls = getClass();
        return cls.isAnonymousClass() ? cls.getName() : cls.getSimpleName();
    }
}
