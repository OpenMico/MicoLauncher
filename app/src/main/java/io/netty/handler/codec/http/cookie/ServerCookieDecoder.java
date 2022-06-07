package io.netty.handler.codec.http.cookie;

import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes4.dex */
public final class ServerCookieDecoder extends CookieDecoder {
    public static final ServerCookieDecoder STRICT = new ServerCookieDecoder(true);
    public static final ServerCookieDecoder LAX = new ServerCookieDecoder(false);

    private ServerCookieDecoder(boolean z) {
        super(z);
    }

    public Set<Cookie> decode(String str) {
        int i;
        int i2;
        int i3;
        int i4;
        DefaultCookie initCookie;
        int length = ((String) ObjectUtil.checkNotNull(str, "header")).length();
        if (length == 0) {
            return Collections.emptySet();
        }
        TreeSet treeSet = new TreeSet();
        boolean z = true;
        if (str.regionMatches(true, 0, "$Version", 0, 8)) {
            i = str.indexOf(59) + 1;
        } else {
            z = false;
            i = 0;
        }
        while (i != length) {
            char charAt = str.charAt(i);
            if (charAt == '\t' || charAt == '\n' || charAt == 11 || charAt == '\f' || charAt == '\r' || charAt == ' ' || charAt == ',' || charAt == ';') {
                i++;
            } else {
                if (i != length) {
                    int i5 = i;
                    while (true) {
                        char charAt2 = str.charAt(i5);
                        if (charAt2 != ';') {
                            if (charAt2 != '=') {
                                i5++;
                                if (i5 == length) {
                                    i4 = length;
                                    i3 = -1;
                                    i2 = -1;
                                    i = i5;
                                    break;
                                }
                            } else {
                                i = i5 + 1;
                                if (i == length) {
                                    i3 = 0;
                                    i2 = 0;
                                    i4 = i5;
                                } else {
                                    i = str.indexOf(59, i);
                                    if (i <= 0) {
                                        i = length;
                                    }
                                    i3 = i;
                                    i4 = i5;
                                    i2 = i;
                                }
                            }
                        } else {
                            i3 = -1;
                            i2 = -1;
                            i = i5;
                            i4 = i;
                            break;
                        }
                    }
                } else {
                    i3 = -1;
                    i2 = -1;
                    i = i;
                    i4 = i;
                }
                if ((!z || (!str.regionMatches(i, "$Path", 0, 5) && !str.regionMatches(i, "$Domain", 0, 7) && !str.regionMatches(i, "$Port", 0, 5))) && (initCookie = initCookie(str, i, i4, i3, i2)) != null) {
                    treeSet.add(initCookie);
                }
            }
        }
        return treeSet;
    }
}
