package org.apache.commons.lang3.text;

import io.netty.util.internal.StringUtil;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class ExtendedMessageFormat extends MessageFormat {
    static final /* synthetic */ boolean a = !ExtendedMessageFormat.class.desiredAssertionStatus();
    private static final long serialVersionUID = -2362048321261811743L;
    private final Map<String, ? extends FormatFactory> registry;
    private String toPattern;

    public ExtendedMessageFormat(String str) {
        this(str, Locale.getDefault());
    }

    public ExtendedMessageFormat(String str, Locale locale) {
        this(str, locale, null);
    }

    public ExtendedMessageFormat(String str, Map<String, ? extends FormatFactory> map) {
        this(str, Locale.getDefault(), map);
    }

    public ExtendedMessageFormat(String str, Locale locale, Map<String, ? extends FormatFactory> map) {
        super("");
        setLocale(locale);
        this.registry = map;
        applyPattern(str);
    }

    @Override // java.text.MessageFormat
    public String toPattern() {
        return this.toPattern;
    }

    @Override // java.text.MessageFormat
    public final void applyPattern(String str) {
        Format format;
        String str2;
        if (this.registry == null) {
            super.applyPattern(str);
            this.toPattern = super.toPattern();
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList<String> arrayList2 = new ArrayList<>();
        StringBuilder sb = new StringBuilder(str.length());
        int i = 0;
        ParsePosition parsePosition = new ParsePosition(0);
        char[] charArray = str.toCharArray();
        int i2 = 0;
        while (parsePosition.getIndex() < str.length()) {
            char c = charArray[parsePosition.getIndex()];
            if (c != '\'') {
                if (c == '{') {
                    i2++;
                    c(str, parsePosition);
                    int index = parsePosition.getIndex();
                    int a2 = a(str, a(parsePosition));
                    sb.append('{');
                    sb.append(a2);
                    c(str, parsePosition);
                    if (charArray[parsePosition.getIndex()] == ',') {
                        str2 = b(str, a(parsePosition));
                        format = a(str2);
                        if (format == null) {
                            sb.append(StringUtil.COMMA);
                            sb.append(str2);
                        }
                    } else {
                        str2 = null;
                        format = null;
                    }
                    arrayList.add(format);
                    if (format == null) {
                        str2 = null;
                    }
                    arrayList2.add(str2);
                    boolean z = true;
                    Validate.isTrue(arrayList.size() == i2);
                    if (arrayList2.size() != i2) {
                        z = false;
                    }
                    Validate.isTrue(z);
                    if (charArray[parsePosition.getIndex()] != '}') {
                        throw new IllegalArgumentException("Unreadable format element at position " + index);
                    }
                }
                sb.append(charArray[parsePosition.getIndex()]);
                a(parsePosition);
            } else {
                a(str, parsePosition, sb);
            }
        }
        super.applyPattern(sb.toString());
        this.toPattern = a(super.toPattern(), arrayList2);
        if (a(arrayList)) {
            Format[] formats = getFormats();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Format format2 = (Format) it.next();
                if (format2 != null) {
                    formats[i] = format2;
                }
                i++;
            }
            super.setFormats(formats);
        }
    }

    @Override // java.text.MessageFormat
    public void setFormat(int i, Format format) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormatByArgumentIndex(int i, Format format) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormats(Format[] formatArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormatsByArgumentIndex(Format[] formatArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !super.equals(obj) || ObjectUtils.notEqual(getClass(), obj.getClass())) {
            return false;
        }
        ExtendedMessageFormat extendedMessageFormat = (ExtendedMessageFormat) obj;
        return !ObjectUtils.notEqual(this.toPattern, extendedMessageFormat.toPattern) && !ObjectUtils.notEqual(this.registry, extendedMessageFormat.registry);
    }

    @Override // java.text.MessageFormat
    public int hashCode() {
        return (((super.hashCode() * 31) + ObjectUtils.hashCode(this.registry)) * 31) + ObjectUtils.hashCode(this.toPattern);
    }

    private Format a(String str) {
        String str2;
        if (this.registry != null) {
            int indexOf = str.indexOf(44);
            if (indexOf > 0) {
                str = str.substring(0, indexOf).trim();
                str2 = str.substring(indexOf + 1).trim();
            } else {
                str2 = null;
            }
            FormatFactory formatFactory = (FormatFactory) this.registry.get(str);
            if (formatFactory != null) {
                return formatFactory.getFormat(str, str2, getLocale());
            }
        }
        return null;
    }

    private int a(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        c(str, parsePosition);
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        while (!z && parsePosition.getIndex() < str.length()) {
            char charAt = str.charAt(parsePosition.getIndex());
            if (Character.isWhitespace(charAt)) {
                c(str, parsePosition);
                charAt = str.charAt(parsePosition.getIndex());
                if (!(charAt == ',' || charAt == '}')) {
                    z = true;
                    a(parsePosition);
                }
            }
            if ((charAt == ',' || charAt == '}') && sb.length() > 0) {
                try {
                    return Integer.parseInt(sb.toString());
                } catch (NumberFormatException unused) {
                }
            }
            z = !Character.isDigit(charAt);
            sb.append(charAt);
            a(parsePosition);
        }
        if (z) {
            throw new IllegalArgumentException("Invalid format argument index at position " + index + ": " + str.substring(index, parsePosition.getIndex()));
        }
        throw new IllegalArgumentException("Unterminated format element at position " + index);
    }

    private String b(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        c(str, parsePosition);
        int index2 = parsePosition.getIndex();
        int i = 1;
        while (parsePosition.getIndex() < str.length()) {
            char charAt = str.charAt(parsePosition.getIndex());
            if (charAt == '\'') {
                d(str, parsePosition);
            } else if (charAt == '{') {
                i++;
            } else if (charAt == '}' && i - 1 == 0) {
                return str.substring(index2, parsePosition.getIndex());
            }
            a(parsePosition);
        }
        throw new IllegalArgumentException("Unterminated format element at position " + index);
    }

    private String a(String str, ArrayList<String> arrayList) {
        String str2;
        if (!a(arrayList)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() * 2);
        int i = 0;
        ParsePosition parsePosition = new ParsePosition(0);
        int i2 = -1;
        while (parsePosition.getIndex() < str.length()) {
            char charAt = str.charAt(parsePosition.getIndex());
            if (charAt == '\'') {
                a(str, parsePosition, sb);
            } else if (charAt != '{') {
                if (charAt == '}') {
                    i--;
                }
                sb.append(charAt);
                a(parsePosition);
            } else {
                i++;
                sb.append('{');
                sb.append(a(str, a(parsePosition)));
                if (i == 1 && (str2 = arrayList.get((i2 = i2 + 1))) != null) {
                    sb.append(StringUtil.COMMA);
                    sb.append(str2);
                }
            }
        }
        return sb.toString();
    }

    private void c(String str, ParsePosition parsePosition) {
        char[] charArray = str.toCharArray();
        do {
            int isMatch = StrMatcher.splitMatcher().isMatch(charArray, parsePosition.getIndex());
            parsePosition.setIndex(parsePosition.getIndex() + isMatch);
            if (isMatch <= 0) {
                return;
            }
        } while (parsePosition.getIndex() < str.length());
    }

    private ParsePosition a(ParsePosition parsePosition) {
        parsePosition.setIndex(parsePosition.getIndex() + 1);
        return parsePosition;
    }

    private StringBuilder a(String str, ParsePosition parsePosition, StringBuilder sb) {
        if (a || str.toCharArray()[parsePosition.getIndex()] == '\'') {
            if (sb != null) {
                sb.append('\'');
            }
            a(parsePosition);
            int index = parsePosition.getIndex();
            char[] charArray = str.toCharArray();
            for (int index2 = parsePosition.getIndex(); index2 < str.length(); index2++) {
                if (charArray[parsePosition.getIndex()] != '\'') {
                    a(parsePosition);
                } else {
                    a(parsePosition);
                    if (sb == null) {
                        return null;
                    }
                    sb.append(charArray, index, parsePosition.getIndex() - index);
                    return sb;
                }
            }
            throw new IllegalArgumentException("Unterminated quoted string at position " + index);
        }
        throw new AssertionError("Quoted string must start with quote character");
    }

    private void d(String str, ParsePosition parsePosition) {
        a(str, parsePosition, null);
    }

    private boolean a(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next() != null) {
                return true;
            }
        }
        return false;
    }
}
