package io.netty.handler.codec.http;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: classes4.dex */
public class QueryStringDecoder {
    private final Charset a;
    private final String b;
    private final boolean c;
    private final int d;
    private String e;
    private Map<String, List<String>> f;
    private int g;

    private static char a(char c) {
        return ('0' > c || c > '9') ? ('a' > c || c > 'f') ? ('A' > c || c > 'F') ? CharCompanionObject.MAX_VALUE : (char) ((c - 'A') + 10) : (char) ((c - 'a') + 10) : (char) (c - '0');
    }

    public QueryStringDecoder(String str) {
        this(str, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringDecoder(String str, boolean z) {
        this(str, HttpConstants.DEFAULT_CHARSET, z);
    }

    public QueryStringDecoder(String str, Charset charset) {
        this(str, charset, true);
    }

    public QueryStringDecoder(String str, Charset charset, boolean z) {
        this(str, charset, z, 1024);
    }

    public QueryStringDecoder(String str, Charset charset, boolean z, int i) {
        if (str == null) {
            throw new NullPointerException("getUri");
        } else if (charset == null) {
            throw new NullPointerException("charset");
        } else if (i > 0) {
            this.b = str;
            this.a = charset;
            this.d = i;
            this.c = z;
        } else {
            throw new IllegalArgumentException("maxParams: " + i + " (expected: a positive integer)");
        }
    }

    public QueryStringDecoder(URI uri) {
        this(uri, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringDecoder(URI uri, Charset charset) {
        this(uri, charset, 1024);
    }

    public QueryStringDecoder(URI uri, Charset charset, int i) {
        String str;
        if (uri == null) {
            throw new NullPointerException("getUri");
        } else if (charset == null) {
            throw new NullPointerException("charset");
        } else if (i > 0) {
            String rawPath = uri.getRawPath();
            if (rawPath != null) {
                this.c = true;
            } else {
                rawPath = "";
                this.c = false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(rawPath);
            if (uri.getRawQuery() == null) {
                str = "";
            } else {
                str = '?' + uri.getRawQuery();
            }
            sb.append(str);
            this.b = sb.toString();
            this.a = charset;
            this.d = i;
        } else {
            throw new IllegalArgumentException("maxParams: " + i + " (expected: a positive integer)");
        }
    }

    public String uri() {
        return this.b;
    }

    public String path() {
        if (this.e == null) {
            if (!this.c) {
                this.e = "";
                return "";
            }
            int indexOf = this.b.indexOf(63);
            if (indexOf < 0) {
                this.e = this.b;
            } else {
                String substring = this.b.substring(0, indexOf);
                this.e = substring;
                return substring;
            }
        }
        return this.e;
    }

    public Map<String, List<String>> parameters() {
        if (this.f == null) {
            if (this.c) {
                int length = path().length();
                if (this.b.length() == length) {
                    return Collections.emptyMap();
                }
                a(this.b.substring(length + 1));
            } else if (this.b.isEmpty()) {
                return Collections.emptyMap();
            } else {
                a(this.b);
            }
        }
        return this.f;
    }

    private void a(String str) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.f = linkedHashMap;
        int i = 0;
        this.g = 0;
        int i2 = 0;
        String str2 = null;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '=' && str2 == null) {
                if (i2 != i) {
                    str2 = decodeComponent(str.substring(i2, i), this.a);
                }
                i2 = i + 1;
            } else if (charAt == '&' || charAt == ';') {
                if (str2 != null || i2 == i) {
                    if (str2 != null) {
                        if (a(linkedHashMap, str2, decodeComponent(str.substring(i2, i), this.a))) {
                            str2 = null;
                        } else {
                            return;
                        }
                    }
                } else if (!a(linkedHashMap, decodeComponent(str.substring(i2, i), this.a), "")) {
                    return;
                }
                i2 = i + 1;
            }
            i++;
        }
        if (i2 != i) {
            if (str2 == null) {
                a(linkedHashMap, decodeComponent(str.substring(i2, i), this.a), "");
            } else {
                a(linkedHashMap, str2, decodeComponent(str.substring(i2, i), this.a));
            }
        } else if (str2 != null) {
            a(linkedHashMap, str2, "");
        }
    }

    private boolean a(Map<String, List<String>> map, String str, String str2) {
        if (this.g >= this.d) {
            return false;
        }
        List<String> list = map.get(str);
        if (list == null) {
            list = new ArrayList<>(1);
            map.put(str, list);
        }
        list.add(str2);
        this.g++;
        return true;
    }

    public static String decodeComponent(String str) {
        return decodeComponent(str, HttpConstants.DEFAULT_CHARSET);
    }

    public static String decodeComponent(String str, Charset charset) {
        boolean z;
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '%' || charAt == '+') {
                z = true;
                break;
            }
        }
        z = false;
        if (!z) {
            return str;
        }
        byte[] bArr = new byte[length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char charAt2 = str.charAt(i2);
            if (charAt2 != '%') {
                if (charAt2 == '+') {
                    i3++;
                    bArr[i3] = 32;
                    i2++;
                }
                i3++;
                bArr[i3] = (byte) charAt2;
                i2++;
            } else {
                int i4 = length - 1;
                if (i2 != i4) {
                    i2++;
                    char charAt3 = str.charAt(i2);
                    if (charAt3 == '%') {
                        i3++;
                        bArr[i3] = 37;
                        i2++;
                    } else if (i2 != i4) {
                        char a = a(charAt3);
                        i2++;
                        char a2 = a(str.charAt(i2));
                        if (a == 65535 || a2 == 65535) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("invalid escape sequence `%");
                            sb.append(str.charAt(i2 - 1));
                            sb.append(str.charAt(i2));
                            sb.append("' at index ");
                            sb.append(i2 - 2);
                            sb.append(" of: ");
                            sb.append(str);
                            throw new IllegalArgumentException(sb.toString());
                        }
                        charAt2 = (char) ((a * 16) + a2);
                        i3++;
                        bArr[i3] = (byte) charAt2;
                        i2++;
                    } else {
                        throw new IllegalArgumentException("partial escape sequence at end of string: " + str);
                    }
                } else {
                    throw new IllegalArgumentException("unterminated escape sequence at end of string: " + str);
                }
            }
        }
        return new String(bArr, 0, i3, charset);
    }
}
