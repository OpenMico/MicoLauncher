package org.apache.commons.text;

import com.alibaba.fastjson.asm.Opcodes;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.CsvTranslators;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.JavaUnicodeEscaper;
import org.apache.commons.text.translate.LookupTranslator;
import org.apache.commons.text.translate.NumericEntityEscaper;
import org.apache.commons.text.translate.NumericEntityUnescaper;
import org.apache.commons.text.translate.OctalUnescaper;
import org.apache.commons.text.translate.UnicodeUnescaper;
import org.apache.commons.text.translate.UnicodeUnpairedSurrogateRemover;

/* loaded from: classes5.dex */
public class StringEscapeUtils {
    public static final CharSequenceTranslator ESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator ESCAPE_JAVA;
    public static final CharSequenceTranslator ESCAPE_JSON;
    public static final CharSequenceTranslator ESCAPE_XML10;
    public static final CharSequenceTranslator ESCAPE_XML11;
    public static final CharSequenceTranslator ESCAPE_XSI;
    public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator UNESCAPE_JAVA;
    public static final CharSequenceTranslator UNESCAPE_JSON;
    public static final CharSequenceTranslator ESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE));
    public static final CharSequenceTranslator ESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE), new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE));
    public static final CharSequenceTranslator ESCAPE_CSV = new CsvTranslators.CsvEscaper();
    public static final CharSequenceTranslator UNESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
    public static final CharSequenceTranslator UNESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE), new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
    public static final CharSequenceTranslator UNESCAPE_XML = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE), new LookupTranslator(EntityArrays.APOS_UNESCAPE), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
    public static final CharSequenceTranslator UNESCAPE_CSV = new CsvTranslators.CsvUnescaper();
    public static final CharSequenceTranslator UNESCAPE_XSI = new a();

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("\"", "\\\"");
        hashMap.put("\\", "\\\\");
        ESCAPE_JAVA = new AggregateTranslator(new LookupTranslator(Collections.unmodifiableMap(hashMap)), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE), JavaUnicodeEscaper.outsideOf(32, 127));
        HashMap hashMap2 = new HashMap();
        hashMap2.put(LrcRow.SINGLE_QUOTE, "\\'");
        hashMap2.put("\"", "\\\"");
        hashMap2.put("\\", "\\\\");
        hashMap2.put("/", "\\/");
        ESCAPE_ECMASCRIPT = new AggregateTranslator(new LookupTranslator(Collections.unmodifiableMap(hashMap2)), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE), JavaUnicodeEscaper.outsideOf(32, 127));
        HashMap hashMap3 = new HashMap();
        hashMap3.put("\"", "\\\"");
        hashMap3.put("\\", "\\\\");
        hashMap3.put("/", "\\/");
        ESCAPE_JSON = new AggregateTranslator(new LookupTranslator(Collections.unmodifiableMap(hashMap3)), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE), JavaUnicodeEscaper.outsideOf(32, 127));
        HashMap hashMap4 = new HashMap();
        hashMap4.put("\u0000", "");
        hashMap4.put("\u0001", "");
        hashMap4.put("\u0002", "");
        hashMap4.put("\u0003", "");
        hashMap4.put("\u0004", "");
        hashMap4.put("\u0005", "");
        hashMap4.put("\u0006", "");
        hashMap4.put("\u0007", "");
        hashMap4.put("\b", "");
        hashMap4.put("\u000b", "");
        hashMap4.put("\f", "");
        hashMap4.put("\u000e", "");
        hashMap4.put("\u000f", "");
        hashMap4.put("\u0010", "");
        hashMap4.put("\u0011", "");
        hashMap4.put("\u0012", "");
        hashMap4.put("\u0013", "");
        hashMap4.put("\u0014", "");
        hashMap4.put("\u0015", "");
        hashMap4.put("\u0016", "");
        hashMap4.put("\u0017", "");
        hashMap4.put("\u0018", "");
        hashMap4.put("\u0019", "");
        hashMap4.put("\u001a", "");
        hashMap4.put("\u001b", "");
        hashMap4.put("\u001c", "");
        hashMap4.put("\u001d", "");
        hashMap4.put("\u001e", "");
        hashMap4.put("\u001f", "");
        hashMap4.put("\ufffe", "");
        hashMap4.put("\uffff", "");
        ESCAPE_XML10 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.APOS_ESCAPE), new LookupTranslator(Collections.unmodifiableMap(hashMap4)), NumericEntityEscaper.between(127, 132), NumericEntityEscaper.between(134, Opcodes.IF_ICMPEQ), new UnicodeUnpairedSurrogateRemover());
        HashMap hashMap5 = new HashMap();
        hashMap5.put("\u0000", "");
        hashMap5.put("\u000b", "&#11;");
        hashMap5.put("\f", "&#12;");
        hashMap5.put("\ufffe", "");
        hashMap5.put("\uffff", "");
        ESCAPE_XML11 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.APOS_ESCAPE), new LookupTranslator(Collections.unmodifiableMap(hashMap5)), NumericEntityEscaper.between(1, 8), NumericEntityEscaper.between(14, 31), NumericEntityEscaper.between(127, 132), NumericEntityEscaper.between(134, Opcodes.IF_ICMPEQ), new UnicodeUnpairedSurrogateRemover());
        HashMap hashMap6 = new HashMap();
        hashMap6.put("|", "\\|");
        hashMap6.put(MusicGroupListActivity.SPECIAL_SYMBOL, "\\&");
        hashMap6.put(";", "\\;");
        hashMap6.put("<", "\\<");
        hashMap6.put(">", "\\>");
        hashMap6.put("(", "\\(");
        hashMap6.put(")", "\\)");
        hashMap6.put("$", "\\$");
        hashMap6.put("`", "\\`");
        hashMap6.put("\\", "\\\\");
        hashMap6.put("\"", "\\\"");
        hashMap6.put(LrcRow.SINGLE_QUOTE, "\\'");
        hashMap6.put(StringUtils.SPACE, "\\ ");
        hashMap6.put("\t", "\\\t");
        hashMap6.put("\r\n", "");
        hashMap6.put("\n", "");
        hashMap6.put("*", "\\*");
        hashMap6.put("?", "\\?");
        hashMap6.put("[", "\\[");
        hashMap6.put("#", "\\#");
        hashMap6.put(Constants.WAVE_SEPARATOR, "\\~");
        hashMap6.put("=", "\\=");
        hashMap6.put("%", "\\%");
        ESCAPE_XSI = new LookupTranslator(Collections.unmodifiableMap(hashMap6));
        HashMap hashMap7 = new HashMap();
        hashMap7.put("\\\\", "\\");
        hashMap7.put("\\\"", "\"");
        hashMap7.put("\\'", LrcRow.SINGLE_QUOTE);
        hashMap7.put("\\", "");
        UNESCAPE_JAVA = new AggregateTranslator(new OctalUnescaper(), new UnicodeUnescaper(), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE), new LookupTranslator(Collections.unmodifiableMap(hashMap7)));
        CharSequenceTranslator charSequenceTranslator = UNESCAPE_JAVA;
        UNESCAPE_ECMASCRIPT = charSequenceTranslator;
        UNESCAPE_JSON = charSequenceTranslator;
    }

    /* loaded from: classes5.dex */
    static class a extends CharSequenceTranslator {
        a() {
        }

        @Override // org.apache.commons.text.translate.CharSequenceTranslator
        public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
            if (i == 0) {
                String charSequence2 = charSequence.toString();
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    int indexOf = charSequence2.indexOf(92, i2);
                    if (indexOf == -1) {
                        break;
                    }
                    if (indexOf > i3) {
                        writer.write(charSequence2.substring(i3, indexOf));
                    }
                    i3 = indexOf + 1;
                    i2 = indexOf + 2;
                }
                if (i3 < charSequence2.length()) {
                    writer.write(charSequence2.substring(i3));
                }
                return Character.codePointCount(charSequence, 0, charSequence.length());
            }
            throw new IllegalStateException("XsiUnescaper should never reach the [1] index");
        }
    }

    /* loaded from: classes5.dex */
    public static final class Builder {
        private final StringBuilder a;
        private final CharSequenceTranslator b;

        private Builder(CharSequenceTranslator charSequenceTranslator) {
            this.a = new StringBuilder();
            this.b = charSequenceTranslator;
        }

        public Builder escape(String str) {
            this.a.append(this.b.translate(str));
            return this;
        }

        public Builder append(String str) {
            this.a.append(str);
            return this;
        }

        public String toString() {
            return this.a.toString();
        }
    }

    public static Builder builder(CharSequenceTranslator charSequenceTranslator) {
        return new Builder(charSequenceTranslator);
    }

    public static final String escapeJava(String str) {
        return ESCAPE_JAVA.translate(str);
    }

    public static final String escapeEcmaScript(String str) {
        return ESCAPE_ECMASCRIPT.translate(str);
    }

    public static final String escapeJson(String str) {
        return ESCAPE_JSON.translate(str);
    }

    public static final String unescapeJava(String str) {
        return UNESCAPE_JAVA.translate(str);
    }

    public static final String unescapeEcmaScript(String str) {
        return UNESCAPE_ECMASCRIPT.translate(str);
    }

    public static final String unescapeJson(String str) {
        return UNESCAPE_JSON.translate(str);
    }

    public static final String escapeHtml4(String str) {
        return ESCAPE_HTML4.translate(str);
    }

    public static final String escapeHtml3(String str) {
        return ESCAPE_HTML3.translate(str);
    }

    public static final String unescapeHtml4(String str) {
        return UNESCAPE_HTML4.translate(str);
    }

    public static final String unescapeHtml3(String str) {
        return UNESCAPE_HTML3.translate(str);
    }

    public static String escapeXml10(String str) {
        return ESCAPE_XML10.translate(str);
    }

    public static String escapeXml11(String str) {
        return ESCAPE_XML11.translate(str);
    }

    public static final String unescapeXml(String str) {
        return UNESCAPE_XML.translate(str);
    }

    public static final String escapeCsv(String str) {
        return ESCAPE_CSV.translate(str);
    }

    public static final String unescapeCsv(String str) {
        return UNESCAPE_CSV.translate(str);
    }

    public static final String escapeXSI(String str) {
        return ESCAPE_XSI.translate(str);
    }

    public static final String unescapeXSI(String str) {
        return UNESCAPE_XSI.translate(str);
    }
}
