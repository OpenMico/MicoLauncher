package org.apache.commons.lang;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class StringEscapeUtils {
    private static final String a = String.valueOf('\"');
    private static final char[] b = {StringUtil.COMMA, '\"', '\r', '\n'};

    public static String escapeJava(String str) {
        return a(str, false, false);
    }

    public static void escapeJava(Writer writer, String str) throws IOException {
        a(writer, str, false, false);
    }

    public static String escapeJavaScript(String str) {
        return a(str, true, true);
    }

    public static void escapeJavaScript(Writer writer, String str) throws IOException {
        a(writer, str, true, true);
    }

    private static String a(String str, boolean z, boolean z2) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter(str.length() * 2);
            a(stringWriter, str, z, z2);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    private static void a(Writer writer, String str, boolean z, boolean z2) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        } else if (str != null) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (charAt > 4095) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("\\u");
                    stringBuffer.append(a(charAt));
                    writer.write(stringBuffer.toString());
                } else if (charAt > 255) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("\\u0");
                    stringBuffer2.append(a(charAt));
                    writer.write(stringBuffer2.toString());
                } else if (charAt > 127) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("\\u00");
                    stringBuffer3.append(a(charAt));
                    writer.write(stringBuffer3.toString());
                } else if (charAt < ' ') {
                    switch (charAt) {
                        case '\b':
                            writer.write(92);
                            writer.write(98);
                            continue;
                        case '\t':
                            writer.write(92);
                            writer.write(116);
                            continue;
                        case '\n':
                            writer.write(92);
                            writer.write(110);
                            continue;
                        case 11:
                        default:
                            if (charAt <= 15) {
                                StringBuffer stringBuffer4 = new StringBuffer();
                                stringBuffer4.append("\\u000");
                                stringBuffer4.append(a(charAt));
                                writer.write(stringBuffer4.toString());
                                break;
                            } else {
                                StringBuffer stringBuffer5 = new StringBuffer();
                                stringBuffer5.append("\\u00");
                                stringBuffer5.append(a(charAt));
                                writer.write(stringBuffer5.toString());
                                continue;
                            }
                        case '\f':
                            writer.write(92);
                            writer.write(102);
                            continue;
                        case '\r':
                            writer.write(92);
                            writer.write(114);
                            continue;
                    }
                } else if (charAt == '\"') {
                    writer.write(92);
                    writer.write(34);
                } else if (charAt == '\'') {
                    if (z) {
                        writer.write(92);
                    }
                    writer.write(39);
                } else if (charAt == '/') {
                    if (z2) {
                        writer.write(92);
                    }
                    writer.write(47);
                } else if (charAt != '\\') {
                    writer.write(charAt);
                } else {
                    writer.write(92);
                    writer.write(92);
                }
            }
        }
    }

    private static String a(char c) {
        return Integer.toHexString(c).toUpperCase(Locale.ENGLISH);
    }

    public static String unescapeJava(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter(str.length());
            unescapeJava(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    public static void unescapeJava(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        } else if (str != null) {
            int length = str.length();
            StrBuilder strBuilder = new StrBuilder(4);
            boolean z = false;
            boolean z2 = false;
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (z2) {
                    strBuilder.append(charAt);
                    if (strBuilder.length() == 4) {
                        try {
                            writer.write((char) Integer.parseInt(strBuilder.toString(), 16));
                            strBuilder.setLength(0);
                            z = false;
                            z2 = false;
                        } catch (NumberFormatException e) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("Unable to parse unicode value: ");
                            stringBuffer.append(strBuilder);
                            throw new NestableRuntimeException(stringBuffer.toString(), e);
                        }
                    } else {
                        continue;
                    }
                } else if (z) {
                    if (charAt == '\"') {
                        writer.write(34);
                    } else if (charAt == '\'') {
                        writer.write(39);
                    } else if (charAt == '\\') {
                        writer.write(92);
                    } else if (charAt == 'b') {
                        writer.write(8);
                    } else if (charAt == 'f') {
                        writer.write(12);
                    } else if (charAt == 'n') {
                        writer.write(10);
                    } else if (charAt != 'r') {
                        switch (charAt) {
                            case 't':
                                writer.write(9);
                                break;
                            case 'u':
                                z = false;
                                z2 = true;
                                break;
                            default:
                                writer.write(charAt);
                                break;
                        }
                    } else {
                        writer.write(13);
                    }
                    z = false;
                } else if (charAt == '\\') {
                    z = true;
                } else {
                    writer.write(charAt);
                }
            }
            if (z) {
                writer.write(92);
            }
        }
    }

    public static String unescapeJavaScript(String str) {
        return unescapeJava(str);
    }

    public static void unescapeJavaScript(Writer writer, String str) throws IOException {
        unescapeJava(writer, str);
    }

    public static String escapeHtml(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter((int) (str.length() * 1.5d));
            escapeHtml(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    public static void escapeHtml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (str != null) {
            a.e.a(writer, str);
        }
    }

    public static String unescapeHtml(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter((int) (str.length() * 1.5d));
            unescapeHtml(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    public static void unescapeHtml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (str != null) {
            a.e.b(writer, str);
        }
    }

    public static void escapeXml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (str != null) {
            a.c.a(writer, str);
        }
    }

    public static String escapeXml(String str) {
        if (str == null) {
            return null;
        }
        return a.c.b(str);
    }

    public static void unescapeXml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (str != null) {
            a.c.b(writer, str);
        }
    }

    public static String unescapeXml(String str) {
        if (str == null) {
            return null;
        }
        return a.c.c(str);
    }

    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, LrcRow.SINGLE_QUOTE, "''");
    }

    public static String escapeCsv(String str) {
        if (StringUtils.containsNone(str, b)) {
            return str;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            escapeCsv(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    public static void escapeCsv(Writer writer, String str) throws IOException {
        if (!StringUtils.containsNone(str, b)) {
            writer.write(34);
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt == '\"') {
                    writer.write(34);
                }
                writer.write(charAt);
            }
            writer.write(34);
        } else if (str != null) {
            writer.write(str);
        }
    }

    public static String unescapeCsv(String str) {
        if (str == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            unescapeCsv(stringWriter, str);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    public static void unescapeCsv(Writer writer, String str) throws IOException {
        if (str != null) {
            if (str.length() < 2) {
                writer.write(str);
            } else if (str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"') {
                String substring = str.substring(1, str.length() - 1);
                if (StringUtils.containsAny(substring, b)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(a);
                    stringBuffer.append(a);
                    str = StringUtils.replace(substring, stringBuffer.toString(), a);
                }
                writer.write(str);
            } else {
                writer.write(str);
            }
        }
    }
}
