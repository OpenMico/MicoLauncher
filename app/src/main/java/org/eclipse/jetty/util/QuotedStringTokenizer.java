package org.eclipse.jetty.util;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: classes5.dex */
public class QuotedStringTokenizer extends StringTokenizer {
    private static final String __delim = "\t\n\r";
    private static final char[] escapes = new char[32];
    private String _delim;
    private boolean _double;
    private boolean _hasToken;
    private int _i;
    private int _lastStart;
    private boolean _returnDelimiters;
    private boolean _returnQuotes;
    private boolean _single;
    private String _string;
    private StringBuffer _token;

    @Override // java.util.StringTokenizer
    public int countTokens() {
        return -1;
    }

    public QuotedStringTokenizer(String str, String str2, boolean z, boolean z2) {
        super("");
        this._delim = __delim;
        this._returnQuotes = false;
        this._returnDelimiters = false;
        this._hasToken = false;
        this._i = 0;
        this._lastStart = 0;
        this._double = true;
        this._single = true;
        this._string = str;
        if (str2 != null) {
            this._delim = str2;
        }
        this._returnDelimiters = z;
        this._returnQuotes = z2;
        if (this._delim.indexOf(39) >= 0 || this._delim.indexOf(34) >= 0) {
            throw new Error("Can't use quotes as delimiters: " + this._delim);
        }
        this._token = new StringBuffer(this._string.length() > 1024 ? 512 : this._string.length() / 2);
    }

    public QuotedStringTokenizer(String str, String str2, boolean z) {
        this(str, str2, z, false);
    }

    public QuotedStringTokenizer(String str, String str2) {
        this(str, str2, false, false);
    }

    public QuotedStringTokenizer(String str) {
        this(str, null, false, false);
    }

    @Override // java.util.StringTokenizer
    public boolean hasMoreTokens() {
        if (this._hasToken) {
            return true;
        }
        this._lastStart = this._i;
        boolean z = false;
        boolean z2 = false;
        while (this._i < this._string.length()) {
            String str = this._string;
            int i = this._i;
            this._i = i + 1;
            char charAt = str.charAt(i);
            switch (z) {
                case false:
                    if (this._delim.indexOf(charAt) < 0) {
                        if (charAt != '\'' || !this._single) {
                            if (charAt == '\"' && this._double) {
                                if (this._returnQuotes) {
                                    this._token.append(charAt);
                                }
                                z = true;
                                break;
                            } else {
                                this._token.append(charAt);
                                this._hasToken = true;
                                z = true;
                                break;
                            }
                        } else {
                            if (this._returnQuotes) {
                                this._token.append(charAt);
                            }
                            z = true;
                            break;
                        }
                    } else if (!this._returnDelimiters) {
                        break;
                    } else {
                        this._token.append(charAt);
                        this._hasToken = true;
                        return true;
                    }
                case true:
                    this._hasToken = true;
                    if (this._delim.indexOf(charAt) < 0) {
                        if (charAt != '\'' || !this._single) {
                            if (charAt == '\"' && this._double) {
                                if (this._returnQuotes) {
                                    this._token.append(charAt);
                                }
                                z = true;
                                break;
                            } else {
                                this._token.append(charAt);
                                break;
                            }
                        } else {
                            if (this._returnQuotes) {
                                this._token.append(charAt);
                            }
                            z = true;
                            break;
                        }
                    } else {
                        if (this._returnDelimiters) {
                            this._i--;
                        }
                        return this._hasToken;
                    }
                case true:
                    this._hasToken = true;
                    if (!z2) {
                        if (charAt != '\'') {
                            if (charAt != '\\') {
                                this._token.append(charAt);
                                break;
                            } else {
                                if (this._returnQuotes) {
                                    this._token.append(charAt);
                                }
                                z2 = true;
                                break;
                            }
                        } else {
                            if (this._returnQuotes) {
                                this._token.append(charAt);
                            }
                            z = true;
                            break;
                        }
                    } else {
                        this._token.append(charAt);
                        z2 = false;
                        break;
                    }
                case true:
                    this._hasToken = true;
                    if (!z2) {
                        if (charAt != '\"') {
                            if (charAt != '\\') {
                                this._token.append(charAt);
                                break;
                            } else {
                                if (this._returnQuotes) {
                                    this._token.append(charAt);
                                }
                                z2 = true;
                                break;
                            }
                        } else {
                            if (this._returnQuotes) {
                                this._token.append(charAt);
                            }
                            z = true;
                            break;
                        }
                    } else {
                        this._token.append(charAt);
                        z2 = false;
                        break;
                    }
            }
        }
        return this._hasToken;
    }

    @Override // java.util.StringTokenizer
    public String nextToken() throws NoSuchElementException {
        StringBuffer stringBuffer;
        if (!hasMoreTokens() || (stringBuffer = this._token) == null) {
            throw new NoSuchElementException();
        }
        String stringBuffer2 = stringBuffer.toString();
        this._token.setLength(0);
        this._hasToken = false;
        return stringBuffer2;
    }

    @Override // java.util.StringTokenizer
    public String nextToken(String str) throws NoSuchElementException {
        this._delim = str;
        this._i = this._lastStart;
        this._token.setLength(0);
        this._hasToken = false;
        return nextToken();
    }

    @Override // java.util.StringTokenizer, java.util.Enumeration
    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    @Override // java.util.StringTokenizer, java.util.Enumeration
    public Object nextElement() throws NoSuchElementException {
        return nextToken();
    }

    public static String quoteIfNeeded(String str, String str2) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "\"\"";
        }
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\\' || charAt == '\"' || charAt == '\'' || Character.isWhitespace(charAt) || str2.indexOf(charAt) >= 0) {
                StringBuffer stringBuffer = new StringBuffer(str.length() + 8);
                quote(stringBuffer, str);
                return stringBuffer.toString();
            }
        }
        return str;
    }

    public static String quote(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "\"\"";
        }
        StringBuffer stringBuffer = new StringBuffer(str.length() + 8);
        quote(stringBuffer, str);
        return stringBuffer.toString();
    }

    static {
        Arrays.fill(escapes, (char) CharCompanionObject.MAX_VALUE);
        char[] cArr = escapes;
        cArr[8] = 'b';
        cArr[9] = 't';
        cArr[10] = 'n';
        cArr[12] = 'f';
        cArr[13] = 'r';
    }

    public static void quote(Appendable appendable, String str) {
        try {
            appendable.append('\"');
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt >= ' ') {
                    if (charAt == '\"' || charAt == '\\') {
                        appendable.append('\\');
                    }
                    appendable.append(charAt);
                } else {
                    char c = escapes[charAt];
                    if (c == 65535) {
                        appendable.append('\\').append('u').append('0').append('0');
                        if (charAt < 16) {
                            appendable.append('0');
                        }
                        appendable.append(Integer.toString(charAt, 16));
                    } else {
                        appendable.append('\\').append(c);
                    }
                }
            }
            appendable.append('\"');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean quoteIfNeeded(Appendable appendable, String str, String str2) {
        for (int i = 0; i < str.length(); i++) {
            if (str2.indexOf(str.charAt(i)) >= 0) {
                quote(appendable, str);
                return true;
            }
        }
        try {
            appendable.append(str);
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String unquote(String str) {
        char charAt;
        if (str == null) {
            return null;
        }
        if (str.length() < 2 || (charAt = str.charAt(0)) != str.charAt(str.length() - 1) || (charAt != '\"' && charAt != '\'')) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() - 2);
        boolean z = false;
        int i = 1;
        while (i < str.length() - 1) {
            char charAt2 = str.charAt(i);
            if (z) {
                if (charAt2 == '\"') {
                    sb.append('\"');
                } else if (charAt2 == '/') {
                    sb.append(JsonPointer.SEPARATOR);
                } else if (charAt2 == '\\') {
                    sb.append('\\');
                } else if (charAt2 == 'b') {
                    sb.append('\b');
                } else if (charAt2 == 'f') {
                    sb.append('\f');
                } else if (charAt2 == 'n') {
                    sb.append('\n');
                } else if (charAt2 != 'r') {
                    switch (charAt2) {
                        case 't':
                            sb.append('\t');
                            break;
                        case 'u':
                            int i2 = i + 1;
                            int i3 = i2 + 1;
                            int convertHexDigit = (TypeUtil.convertHexDigit((byte) str.charAt(i)) << 24) + (TypeUtil.convertHexDigit((byte) str.charAt(i2)) << 16);
                            int i4 = i3 + 1;
                            int convertHexDigit2 = convertHexDigit + (TypeUtil.convertHexDigit((byte) str.charAt(i3)) << 8);
                            i = i4 + 1;
                            sb.append((char) (convertHexDigit2 + TypeUtil.convertHexDigit((byte) str.charAt(i4))));
                            z = false;
                            break;
                        default:
                            sb.append(charAt2);
                            break;
                    }
                } else {
                    sb.append('\r');
                }
                z = false;
            } else if (charAt2 == '\\') {
                z = true;
            } else {
                sb.append(charAt2);
            }
            i++;
        }
        return sb.toString();
    }

    public boolean getDouble() {
        return this._double;
    }

    public void setDouble(boolean z) {
        this._double = z;
    }

    public boolean getSingle() {
        return this._single;
    }

    public void setSingle(boolean z) {
        this._single = z;
    }
}
