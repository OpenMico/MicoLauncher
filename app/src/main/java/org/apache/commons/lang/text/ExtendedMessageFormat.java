package org.apache.commons.lang.text;

import io.netty.util.internal.StringUtil;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

/* loaded from: classes5.dex */
public class ExtendedMessageFormat extends MessageFormat {
    private static final long serialVersionUID = -2362048321261811743L;
    private final Map registry;
    private String toPattern;

    public ExtendedMessageFormat(String str) {
        this(str, Locale.getDefault());
    }

    public ExtendedMessageFormat(String str, Locale locale) {
        this(str, locale, null);
    }

    public ExtendedMessageFormat(String str, Map map) {
        this(str, Locale.getDefault(), map);
    }

    public ExtendedMessageFormat(String str, Locale locale, Map map) {
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
        ArrayList arrayList2 = new ArrayList();
        StrBuilder strBuilder = new StrBuilder(str.length());
        int i = 0;
        ParsePosition parsePosition = new ParsePosition(0);
        char[] charArray = str.toCharArray();
        int i2 = 0;
        while (parsePosition.getIndex() < str.length()) {
            char c = charArray[parsePosition.getIndex()];
            boolean z = true;
            if (c != '\'') {
                if (c == '{') {
                    i2++;
                    c(str, parsePosition);
                    int index = parsePosition.getIndex();
                    strBuilder.append('{').append(a(str, a(parsePosition)));
                    c(str, parsePosition);
                    if (charArray[parsePosition.getIndex()] == ',') {
                        str2 = b(str, a(parsePosition));
                        format = a(str2);
                        if (format == null) {
                            strBuilder.append(StringUtil.COMMA).append(str2);
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
                    Validate.isTrue(arrayList.size() == i2);
                    if (arrayList2.size() != i2) {
                        z = false;
                    }
                    Validate.isTrue(z);
                    if (charArray[parsePosition.getIndex()] != '}') {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Unreadable format element at position ");
                        stringBuffer.append(index);
                        throw new IllegalArgumentException(stringBuffer.toString());
                    }
                }
                strBuilder.append(charArray[parsePosition.getIndex()]);
                a(parsePosition);
            } else {
                a(str, parsePosition, strBuilder, true);
            }
        }
        super.applyPattern(strBuilder.toString());
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
        StrBuilder strBuilder = new StrBuilder();
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
            if ((charAt == ',' || charAt == '}') && strBuilder.length() > 0) {
                try {
                    return Integer.parseInt(strBuilder.toString());
                } catch (NumberFormatException unused) {
                }
            }
            z = !Character.isDigit(charAt);
            strBuilder.append(charAt);
            a(parsePosition);
        }
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid format argument index at position ");
            stringBuffer.append(index);
            stringBuffer.append(": ");
            stringBuffer.append(str.substring(index, parsePosition.getIndex()));
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Unterminated format element at position ");
        stringBuffer2.append(index);
        throw new IllegalArgumentException(stringBuffer2.toString());
    }

    private String b(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        c(str, parsePosition);
        int index2 = parsePosition.getIndex();
        int i = 1;
        while (parsePosition.getIndex() < str.length()) {
            char charAt = str.charAt(parsePosition.getIndex());
            if (charAt == '\'') {
                a(str, parsePosition, false);
            } else if (charAt == '{') {
                i++;
            } else if (charAt == '}' && i - 1 == 0) {
                return str.substring(index2, parsePosition.getIndex());
            }
            a(parsePosition);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unterminated format element at position ");
        stringBuffer.append(index);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    private String a(String str, ArrayList arrayList) {
        if (!a(arrayList)) {
            return str;
        }
        StrBuilder strBuilder = new StrBuilder(str.length() * 2);
        ParsePosition parsePosition = new ParsePosition(0);
        int i = -1;
        int i2 = 0;
        while (parsePosition.getIndex() < str.length()) {
            char charAt = str.charAt(parsePosition.getIndex());
            if (charAt == '\'') {
                a(str, parsePosition, strBuilder, false);
            } else if (charAt != '{') {
                if (charAt == '}') {
                    i2--;
                }
                strBuilder.append(charAt);
                a(parsePosition);
            } else {
                i2++;
                if (i2 == 1) {
                    i++;
                    strBuilder.append('{').append(a(str, a(parsePosition)));
                    String str2 = (String) arrayList.get(i);
                    if (str2 != null) {
                        strBuilder.append(StringUtil.COMMA).append(str2);
                    }
                }
            }
        }
        return strBuilder.toString();
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

    private StrBuilder a(String str, ParsePosition parsePosition, StrBuilder strBuilder, boolean z) {
        int index = parsePosition.getIndex();
        char[] charArray = str.toCharArray();
        if (!z || charArray[index] != '\'') {
            int i = index;
            for (int index2 = parsePosition.getIndex(); index2 < str.length(); index2++) {
                if (z && str.substring(index2).startsWith("''")) {
                    strBuilder.append(charArray, i, parsePosition.getIndex() - i).append('\'');
                    parsePosition.setIndex(index2 + 2);
                    i = parsePosition.getIndex();
                } else if (charArray[parsePosition.getIndex()] != '\'') {
                    a(parsePosition);
                } else {
                    a(parsePosition);
                    if (strBuilder == null) {
                        return null;
                    }
                    return strBuilder.append(charArray, i, parsePosition.getIndex() - i);
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unterminated quoted string at position ");
            stringBuffer.append(index);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        a(parsePosition);
        if (strBuilder == null) {
            return null;
        }
        return strBuilder.append('\'');
    }

    private void a(String str, ParsePosition parsePosition, boolean z) {
        a(str, parsePosition, null, z);
    }

    private boolean a(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return false;
        }
        for (Object obj : collection) {
            if (obj != null) {
                return true;
            }
        }
        return false;
    }
}
