package io.netty.handler.codec.http;

import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
/* loaded from: classes4.dex */
public final class CookieDecoder {
    private static final CookieDecoder b = new CookieDecoder(true);
    private static final CookieDecoder c = new CookieDecoder(false);
    private final InternalLogger a = InternalLoggerFactory.getInstance(getClass());
    private final boolean d;

    public static Set<Cookie> decode(String str) {
        return decode(str, true);
    }

    public static Set<Cookie> decode(String str, boolean z) {
        return (z ? b : c).a(str);
    }

    private Set<Cookie> a(String str) {
        int i;
        int i2;
        String str2;
        CookieDecoder cookieDecoder;
        int i3;
        ArrayList arrayList = new ArrayList(8);
        ArrayList arrayList2 = new ArrayList(8);
        a(str, arrayList, arrayList2);
        if (arrayList.isEmpty()) {
            return Collections.emptySet();
        }
        boolean z = false;
        if (((String) arrayList.get(0)).equalsIgnoreCase("Version")) {
            try {
                i = Integer.parseInt((String) arrayList2.get(0));
            } catch (NumberFormatException unused) {
                i = 0;
            }
            i2 = 1;
        } else {
            i2 = 0;
            i = 0;
        }
        if (arrayList.size() <= i2) {
            return Collections.emptySet();
        }
        TreeSet treeSet = new TreeSet();
        while (i2 < arrayList.size()) {
            String str3 = (String) arrayList.get(i2);
            String str4 = (String) arrayList2.get(i2);
            if (str4 == null) {
                str2 = "";
                cookieDecoder = this;
            } else {
                str2 = str4;
                cookieDecoder = this;
            }
            DefaultCookie a = cookieDecoder.a(str3, str2);
            if (a == null) {
                return treeSet;
            }
            long j = Long.MIN_VALUE;
            ArrayList arrayList3 = new ArrayList(2);
            int i4 = i2 + 1;
            String str5 = null;
            int i5 = i2;
            String str6 = null;
            String str7 = null;
            boolean z2 = z;
            boolean z3 = z2;
            String str8 = null;
            while (true) {
                if (i4 >= arrayList.size()) {
                    arrayList = arrayList;
                    arrayList2 = arrayList2;
                    break;
                }
                String str9 = (String) arrayList.get(i4);
                arrayList = arrayList;
                String str10 = (String) arrayList2.get(i4);
                arrayList2 = arrayList2;
                if (!"Discard".equalsIgnoreCase(str9)) {
                    if (!CookieHeaderNames.SECURE.equalsIgnoreCase(str9)) {
                        if (!CookieHeaderNames.HTTPONLY.equalsIgnoreCase(str9)) {
                            if (!"Comment".equalsIgnoreCase(str9)) {
                                if (!"CommentURL".equalsIgnoreCase(str9)) {
                                    if (!CookieHeaderNames.DOMAIN.equalsIgnoreCase(str9)) {
                                        if (!CookieHeaderNames.PATH.equalsIgnoreCase(str9)) {
                                            if (!"Expires".equalsIgnoreCase(str9)) {
                                                if (!CookieHeaderNames.MAX_AGE.equalsIgnoreCase(str9)) {
                                                    if (!"Version".equalsIgnoreCase(str9)) {
                                                        if (!"Port".equalsIgnoreCase(str9)) {
                                                            break;
                                                        }
                                                        String[] split = StringUtil.split(str10, StringUtil.COMMA);
                                                        int length = split.length;
                                                        int i6 = 0;
                                                        while (i6 < length) {
                                                            try {
                                                                arrayList3.add(Integer.valueOf(split[i6]));
                                                            } catch (NumberFormatException unused2) {
                                                            }
                                                            i6++;
                                                            split = split;
                                                        }
                                                    } else {
                                                        i = Integer.parseInt(str10);
                                                    }
                                                } else {
                                                    j = Integer.parseInt(str10);
                                                }
                                            } else {
                                                try {
                                                    long time = HttpHeaderDateFormat.get().parse(str10).getTime() - System.currentTimeMillis();
                                                    j = (time / 1000) + (time % 1000 != 0 ? 1 : 0);
                                                } catch (ParseException unused3) {
                                                }
                                            }
                                        } else {
                                            str6 = str10;
                                        }
                                    } else {
                                        str5 = str10;
                                    }
                                } else {
                                    str8 = str10;
                                }
                            } else {
                                str7 = str10;
                            }
                        } else {
                            z3 = true;
                        }
                    } else {
                        z2 = true;
                    }
                } else {
                    z = true;
                }
                i4++;
                i5++;
                arrayList2 = arrayList2;
                arrayList = arrayList;
            }
            a.setVersion(i);
            a.setMaxAge(j);
            a.setPath(str6);
            a.setDomain(str5);
            a.setSecure(z2);
            a.setHttpOnly(z3);
            if (i > 0) {
                a.setComment(str7);
                i3 = 1;
            } else {
                i3 = 1;
            }
            if (i > i3) {
                a.setCommentUrl(str8);
                a.setPorts(arrayList3);
                a.setDiscard(z);
            }
            treeSet.add(a);
            i2 = i5 + 1;
            treeSet = treeSet;
            z = false;
        }
        return treeSet;
    }

    private static void a(String str, List<String> list, List<String> list2) {
        String str2;
        int length = str.length();
        int i = 0;
        while (i != length) {
            char charAt = str.charAt(i);
            if (!(charAt == ' ' || charAt == ',' || charAt == ';')) {
                switch (charAt) {
                    case '\t':
                    case '\n':
                    case 11:
                    case '\f':
                    case '\r':
                        break;
                    default:
                        while (i != length) {
                            if (str.charAt(i) == '$') {
                                i++;
                            } else {
                                String str3 = null;
                                if (i == length) {
                                    str2 = null;
                                } else {
                                    int i2 = i;
                                    while (true) {
                                        char charAt2 = str.charAt(i2);
                                        if (charAt2 == ';') {
                                            str2 = str.substring(i, i2);
                                            i = i2;
                                        } else if (charAt2 != '=') {
                                            i2++;
                                            if (i2 == length) {
                                                str2 = str.substring(i);
                                                i = i2;
                                            }
                                        } else {
                                            str2 = str.substring(i, i2);
                                            int i3 = i2 + 1;
                                            if (i3 == length) {
                                                str3 = "";
                                                i = i3;
                                            } else {
                                                char charAt3 = str.charAt(i3);
                                                if (charAt3 == '\"' || charAt3 == '\'') {
                                                    StringBuilder sb = new StringBuilder(str.length() - i3);
                                                    int i4 = i3 + 1;
                                                    boolean z = false;
                                                    while (true) {
                                                        if (i4 == length) {
                                                            str3 = sb.toString();
                                                            i = i4;
                                                        } else if (z) {
                                                            i4++;
                                                            char charAt4 = str.charAt(i4);
                                                            if (charAt4 == '\"' || charAt4 == '\'' || charAt4 == '\\') {
                                                                sb.setCharAt(sb.length() - 1, charAt4);
                                                            } else {
                                                                sb.append(charAt4);
                                                            }
                                                            z = false;
                                                        } else {
                                                            int i5 = i4 + 1;
                                                            char charAt5 = str.charAt(i4);
                                                            if (charAt5 == charAt3) {
                                                                str3 = sb.toString();
                                                                i = i5;
                                                            } else {
                                                                sb.append(charAt5);
                                                                if (charAt5 == '\\') {
                                                                    z = true;
                                                                    i4 = i5;
                                                                } else {
                                                                    i4 = i5;
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    i = str.indexOf(59, i3);
                                                    if (i > 0) {
                                                        str3 = str.substring(i3, i);
                                                    } else {
                                                        str3 = str.substring(i3);
                                                        i = length;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                list.add(str2);
                                list2.add(str3);
                                continue;
                            }
                        }
                        return;
                }
            }
            i++;
        }
    }

    private CookieDecoder(boolean z) {
        this.d = z;
    }

    private DefaultCookie a(String str, String str2) {
        int b2;
        int a;
        if (str == null || str.length() == 0) {
            this.a.debug("Skipping cookie with null name");
            return null;
        } else if (str2 == null) {
            this.a.debug("Skipping cookie with null value");
            return null;
        } else {
            CharSequence c2 = b.c(str2);
            if (c2 == null) {
                this.a.debug("Skipping cookie because starting quotes are not properly balanced in '{}'", c2);
                return null;
            } else if (!this.d || (a = b.a(str)) < 0) {
                boolean z = c2.length() != str2.length();
                if (!this.d || (b2 = b.b(c2)) < 0) {
                    DefaultCookie defaultCookie = new DefaultCookie(str, c2.toString());
                    defaultCookie.setWrap(z);
                    return defaultCookie;
                }
                if (this.a.isDebugEnabled()) {
                    this.a.debug("Skipping cookie because value '{}' contains invalid char '{}'", c2, Character.valueOf(c2.charAt(b2)));
                }
                return null;
            } else {
                if (this.a.isDebugEnabled()) {
                    this.a.debug("Skipping cookie because name '{}' contains invalid char '{}'", str, Character.valueOf(str.charAt(a)));
                }
                return null;
            }
        }
    }
}
