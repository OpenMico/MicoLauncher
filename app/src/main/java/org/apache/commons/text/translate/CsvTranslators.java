package org.apache.commons.text.translate;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public final class CsvTranslators {
    private static final String a = String.valueOf('\"');
    private static final String b = a + a;
    private static final char[] c = {StringUtil.COMMA, '\"', '\r', '\n'};

    private CsvTranslators() {
    }

    /* loaded from: classes5.dex */
    public static class CsvEscaper extends a {
        @Override // org.apache.commons.text.translate.a, org.apache.commons.text.translate.CharSequenceTranslator
        public /* bridge */ /* synthetic */ int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
            return super.translate(charSequence, i, writer);
        }

        @Override // org.apache.commons.text.translate.a
        void a(CharSequence charSequence, Writer writer) throws IOException {
            String charSequence2 = charSequence.toString();
            if (StringUtils.containsNone(charSequence2, CsvTranslators.c)) {
                writer.write(charSequence2);
                return;
            }
            writer.write(34);
            writer.write(StringUtils.replace(charSequence2, CsvTranslators.a, CsvTranslators.b));
            writer.write(34);
        }
    }

    /* loaded from: classes5.dex */
    public static class CsvUnescaper extends a {
        @Override // org.apache.commons.text.translate.a, org.apache.commons.text.translate.CharSequenceTranslator
        public /* bridge */ /* synthetic */ int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
            return super.translate(charSequence, i, writer);
        }

        @Override // org.apache.commons.text.translate.a
        void a(CharSequence charSequence, Writer writer) throws IOException {
            if (charSequence.charAt(0) == '\"' && charSequence.charAt(charSequence.length() - 1) == '\"') {
                String charSequence2 = charSequence.subSequence(1, charSequence.length() - 1).toString();
                if (StringUtils.containsAny(charSequence2, CsvTranslators.c)) {
                    writer.write(StringUtils.replace(charSequence2, CsvTranslators.b, CsvTranslators.a));
                } else {
                    writer.write(charSequence.toString());
                }
            } else {
                writer.write(charSequence.toString());
            }
        }
    }
}
