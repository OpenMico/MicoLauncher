package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.ArrayUtils;

/* loaded from: classes5.dex */
public class AggregateTranslator extends CharSequenceTranslator {
    private final CharSequenceTranslator[] b;

    public AggregateTranslator(CharSequenceTranslator... charSequenceTranslatorArr) {
        this.b = (CharSequenceTranslator[]) ArrayUtils.clone(charSequenceTranslatorArr);
    }

    @Override // org.apache.commons.lang3.text.translate.CharSequenceTranslator
    public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        for (CharSequenceTranslator charSequenceTranslator : this.b) {
            int translate = charSequenceTranslator.translate(charSequence, i, writer);
            if (translate != 0) {
                return translate;
            }
        }
        return 0;
    }
}
