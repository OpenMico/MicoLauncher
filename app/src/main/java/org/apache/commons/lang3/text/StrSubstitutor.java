package org.apache.commons.lang3.text;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class StrSubstitutor {
    public static final char DEFAULT_ESCAPE = '$';
    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
    public static final StrMatcher DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");
    private char a;
    private StrMatcher b;
    private StrMatcher c;
    private StrMatcher d;
    private StrLookup<?> e;
    private boolean f;
    private boolean g;

    public static <V> String replace(Object obj, Map<String, V> map) {
        return new StrSubstitutor(map).replace(obj);
    }

    public static <V> String replace(Object obj, Map<String, V> map, String str, String str2) {
        return new StrSubstitutor(map, str, str2).replace(obj);
    }

    public static String replace(Object obj, Properties properties) {
        if (properties == null) {
            return obj.toString();
        }
        HashMap hashMap = new HashMap();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String str = (String) propertyNames.nextElement();
            hashMap.put(str, properties.getProperty(str));
        }
        return replace(obj, hashMap);
    }

    public static String replaceSystemProperties(Object obj) {
        return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(obj);
    }

    public StrSubstitutor() {
        this((StrLookup<?>) null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public <V> StrSubstitutor(Map<String, V> map) {
        this((StrLookup<?>) StrLookup.mapLookup(map), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public <V> StrSubstitutor(Map<String, V> map, String str, String str2) {
        this((StrLookup<?>) StrLookup.mapLookup(map), str, str2, '$');
    }

    public <V> StrSubstitutor(Map<String, V> map, String str, String str2, char c) {
        this(StrLookup.mapLookup(map), str, str2, c);
    }

    public <V> StrSubstitutor(Map<String, V> map, String str, String str2, char c, String str3) {
        this(StrLookup.mapLookup(map), str, str2, c, str3);
    }

    public StrSubstitutor(StrLookup<?> strLookup) {
        this(strLookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(StrLookup<?> strLookup, String str, String str2, char c) {
        this.g = false;
        setVariableResolver(strLookup);
        setVariablePrefix(str);
        setVariableSuffix(str2);
        setEscapeChar(c);
        setValueDelimiterMatcher(DEFAULT_VALUE_DELIMITER);
    }

    public StrSubstitutor(StrLookup<?> strLookup, String str, String str2, char c, String str3) {
        this.g = false;
        setVariableResolver(strLookup);
        setVariablePrefix(str);
        setVariableSuffix(str2);
        setEscapeChar(c);
        setValueDelimiter(str3);
    }

    public StrSubstitutor(StrLookup<?> strLookup, StrMatcher strMatcher, StrMatcher strMatcher2, char c) {
        this(strLookup, strMatcher, strMatcher2, c, DEFAULT_VALUE_DELIMITER);
    }

    public StrSubstitutor(StrLookup<?> strLookup, StrMatcher strMatcher, StrMatcher strMatcher2, char c, StrMatcher strMatcher3) {
        this.g = false;
        setVariableResolver(strLookup);
        setVariablePrefixMatcher(strMatcher);
        setVariableSuffixMatcher(strMatcher2);
        setEscapeChar(c);
        setValueDelimiterMatcher(strMatcher3);
    }

    public String replace(String str) {
        if (str == null) {
            return null;
        }
        StrBuilder strBuilder = new StrBuilder(str);
        return !substitute(strBuilder, 0, str.length()) ? str : strBuilder.toString();
    }

    public String replace(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(i2).append(str, i, i2);
        if (!substitute(append, 0, i2)) {
            return str.substring(i, i2 + i);
        }
        return append.toString();
    }

    public String replace(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(cArr.length).append(cArr);
        substitute(append, 0, cArr.length);
        return append.toString();
    }

    public String replace(char[] cArr, int i, int i2) {
        if (cArr == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(i2).append(cArr, i, i2);
        substitute(append, 0, i2);
        return append.toString();
    }

    public String replace(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(stringBuffer.length()).append(stringBuffer);
        substitute(append, 0, append.length());
        return append.toString();
    }

    public String replace(StringBuffer stringBuffer, int i, int i2) {
        if (stringBuffer == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(i2).append(stringBuffer, i, i2);
        substitute(append, 0, i2);
        return append.toString();
    }

    public String replace(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return replace(charSequence, 0, charSequence.length());
    }

    public String replace(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(i2).append(charSequence, i, i2);
        substitute(append, 0, i2);
        return append.toString();
    }

    public String replace(StrBuilder strBuilder) {
        if (strBuilder == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(strBuilder.length()).append(strBuilder);
        substitute(append, 0, append.length());
        return append.toString();
    }

    public String replace(StrBuilder strBuilder, int i, int i2) {
        if (strBuilder == null) {
            return null;
        }
        StrBuilder append = new StrBuilder(i2).append(strBuilder, i, i2);
        substitute(append, 0, i2);
        return append.toString();
    }

    public String replace(Object obj) {
        if (obj == null) {
            return null;
        }
        StrBuilder append = new StrBuilder().append(obj);
        substitute(append, 0, append.length());
        return append.toString();
    }

    public boolean replaceIn(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return false;
        }
        return replaceIn(stringBuffer, 0, stringBuffer.length());
    }

    public boolean replaceIn(StringBuffer stringBuffer, int i, int i2) {
        if (stringBuffer == null) {
            return false;
        }
        StrBuilder append = new StrBuilder(i2).append(stringBuffer, i, i2);
        if (!substitute(append, 0, i2)) {
            return false;
        }
        stringBuffer.replace(i, i2 + i, append.toString());
        return true;
    }

    public boolean replaceIn(StringBuilder sb) {
        if (sb == null) {
            return false;
        }
        return replaceIn(sb, 0, sb.length());
    }

    public boolean replaceIn(StringBuilder sb, int i, int i2) {
        if (sb == null) {
            return false;
        }
        StrBuilder append = new StrBuilder(i2).append(sb, i, i2);
        if (!substitute(append, 0, i2)) {
            return false;
        }
        sb.replace(i, i2 + i, append.toString());
        return true;
    }

    public boolean replaceIn(StrBuilder strBuilder) {
        if (strBuilder == null) {
            return false;
        }
        return substitute(strBuilder, 0, strBuilder.length());
    }

    public boolean replaceIn(StrBuilder strBuilder, int i, int i2) {
        if (strBuilder == null) {
            return false;
        }
        return substitute(strBuilder, i, i2);
    }

    protected boolean substitute(StrBuilder strBuilder, int i, int i2) {
        return a(strBuilder, i, i2, null) > 0;
    }

    private int a(StrBuilder strBuilder, int i, int i2, List<String> list) {
        List<String> list2;
        String resolveVariable;
        int isMatch;
        StrMatcher variablePrefixMatcher = getVariablePrefixMatcher();
        StrMatcher variableSuffixMatcher = getVariableSuffixMatcher();
        char escapeChar = getEscapeChar();
        StrMatcher valueDelimiterMatcher = getValueDelimiterMatcher();
        boolean isEnableSubstitutionInVariables = isEnableSubstitutionInVariables();
        boolean z = list == null;
        int i3 = i + i2;
        List<String> list3 = list;
        char[] cArr = strBuilder.buffer;
        int i4 = 0;
        int i5 = 0;
        int i6 = i;
        while (i6 < i3) {
            int isMatch2 = variablePrefixMatcher.isMatch(cArr, i6, i, i3);
            if (isMatch2 != 0) {
                if (i6 > i) {
                    int i7 = i6 - 1;
                    if (cArr[i7] == escapeChar) {
                        if (this.g) {
                            i6++;
                        } else {
                            strBuilder.deleteCharAt(i7);
                            i4--;
                            i3--;
                            variablePrefixMatcher = variablePrefixMatcher;
                            variableSuffixMatcher = variableSuffixMatcher;
                            escapeChar = escapeChar;
                            cArr = strBuilder.buffer;
                            z = z;
                            i5 = 1;
                        }
                    }
                }
                int i8 = i6 + isMatch2;
                int i9 = i8;
                int i10 = 0;
                while (true) {
                    if (i9 >= i3) {
                        variablePrefixMatcher = variablePrefixMatcher;
                        variableSuffixMatcher = variableSuffixMatcher;
                        escapeChar = escapeChar;
                        z = z;
                        i6 = i9;
                        break;
                    } else if (!isEnableSubstitutionInVariables || (isMatch = variablePrefixMatcher.isMatch(cArr, i9, i, i3)) == 0) {
                        int isMatch3 = variableSuffixMatcher.isMatch(cArr, i9, i, i3);
                        if (isMatch3 == 0) {
                            i9++;
                        } else if (i10 == 0) {
                            variableSuffixMatcher = variableSuffixMatcher;
                            escapeChar = escapeChar;
                            String str = new String(cArr, i8, (i9 - i6) - isMatch2);
                            if (isEnableSubstitutionInVariables) {
                                StrBuilder strBuilder2 = new StrBuilder(str);
                                substitute(strBuilder2, 0, strBuilder2.length());
                                str = strBuilder2.toString();
                            }
                            i6 = i9 + isMatch3;
                            if (valueDelimiterMatcher != null) {
                                char[] charArray = str.toCharArray();
                                z = z;
                                int i11 = 0;
                                while (true) {
                                    if (i11 < charArray.length) {
                                        if (!isEnableSubstitutionInVariables && variablePrefixMatcher.isMatch(charArray, i11, i11, charArray.length) != 0) {
                                            variablePrefixMatcher = variablePrefixMatcher;
                                            break;
                                        }
                                        int isMatch4 = valueDelimiterMatcher.isMatch(charArray, i11);
                                        if (isMatch4 != 0) {
                                            variablePrefixMatcher = variablePrefixMatcher;
                                            String substring = str.substring(0, i11);
                                            resolveVariable = str.substring(i11 + isMatch4);
                                            str = substring;
                                            break;
                                        }
                                        i11++;
                                        variablePrefixMatcher = variablePrefixMatcher;
                                    } else {
                                        variablePrefixMatcher = variablePrefixMatcher;
                                        break;
                                    }
                                }
                            } else {
                                variablePrefixMatcher = variablePrefixMatcher;
                                z = z;
                            }
                            resolveVariable = null;
                            if (list3 == null) {
                                list2 = new ArrayList<>();
                                list2.add(new String(cArr, i, i2));
                            } else {
                                list2 = list3;
                            }
                            a(str, list2);
                            list2.add(str);
                            resolveVariable = resolveVariable(str, strBuilder, i6, i6);
                            if (resolveVariable == null) {
                            }
                            if (resolveVariable != null) {
                                int length = resolveVariable.length();
                                strBuilder.replace(i6, i6, resolveVariable);
                                int a = (a(strBuilder, i6, length, list2) + length) - (i6 - i6);
                                i6 += a;
                                i3 += a;
                                i4 += a;
                                cArr = strBuilder.buffer;
                                i5 = 1;
                            }
                            list2.remove(list2.size() - 1);
                            list3 = list2;
                        } else {
                            i10--;
                            i9 += isMatch3;
                            escapeChar = escapeChar;
                            variablePrefixMatcher = variablePrefixMatcher;
                        }
                    } else {
                        i10++;
                        i9 += isMatch;
                    }
                }
            } else {
                i6++;
                variablePrefixMatcher = variablePrefixMatcher;
                variableSuffixMatcher = variableSuffixMatcher;
                escapeChar = escapeChar;
                z = z;
            }
        }
        return z ? i5 : i4;
    }

    private void a(String str, List<String> list) {
        if (list.contains(str)) {
            StrBuilder strBuilder = new StrBuilder(256);
            strBuilder.append("Infinite loop in property interpolation of ");
            strBuilder.append(list.remove(0));
            strBuilder.append(": ");
            strBuilder.appendWithSeparators(list, "->");
            throw new IllegalStateException(strBuilder.toString());
        }
    }

    protected String resolveVariable(String str, StrBuilder strBuilder, int i, int i2) {
        StrLookup<?> variableResolver = getVariableResolver();
        if (variableResolver == null) {
            return null;
        }
        return variableResolver.lookup(str);
    }

    public char getEscapeChar() {
        return this.a;
    }

    public void setEscapeChar(char c) {
        this.a = c;
    }

    public StrMatcher getVariablePrefixMatcher() {
        return this.b;
    }

    public StrSubstitutor setVariablePrefixMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.b = strMatcher;
            return this;
        }
        throw new IllegalArgumentException("Variable prefix matcher must not be null!");
    }

    public StrSubstitutor setVariablePrefix(char c) {
        return setVariablePrefixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariablePrefix(String str) {
        if (str != null) {
            return setVariablePrefixMatcher(StrMatcher.stringMatcher(str));
        }
        throw new IllegalArgumentException("Variable prefix must not be null!");
    }

    public StrMatcher getVariableSuffixMatcher() {
        return this.c;
    }

    public StrSubstitutor setVariableSuffixMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.c = strMatcher;
            return this;
        }
        throw new IllegalArgumentException("Variable suffix matcher must not be null!");
    }

    public StrSubstitutor setVariableSuffix(char c) {
        return setVariableSuffixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariableSuffix(String str) {
        if (str != null) {
            return setVariableSuffixMatcher(StrMatcher.stringMatcher(str));
        }
        throw new IllegalArgumentException("Variable suffix must not be null!");
    }

    public StrMatcher getValueDelimiterMatcher() {
        return this.d;
    }

    public StrSubstitutor setValueDelimiterMatcher(StrMatcher strMatcher) {
        this.d = strMatcher;
        return this;
    }

    public StrSubstitutor setValueDelimiter(char c) {
        return setValueDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setValueDelimiter(String str) {
        if (!StringUtils.isEmpty(str)) {
            return setValueDelimiterMatcher(StrMatcher.stringMatcher(str));
        }
        setValueDelimiterMatcher(null);
        return this;
    }

    public StrLookup<?> getVariableResolver() {
        return this.e;
    }

    public void setVariableResolver(StrLookup<?> strLookup) {
        this.e = strLookup;
    }

    public boolean isEnableSubstitutionInVariables() {
        return this.f;
    }

    public void setEnableSubstitutionInVariables(boolean z) {
        this.f = z;
    }

    public boolean isPreserveEscapes() {
        return this.g;
    }

    public void setPreserveEscapes(boolean z) {
        this.g = z;
    }
}
