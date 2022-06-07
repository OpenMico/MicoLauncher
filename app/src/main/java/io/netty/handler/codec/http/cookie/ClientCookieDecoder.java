package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.HttpHeaderDateFormat;
import io.netty.util.internal.ObjectUtil;
import java.text.ParsePosition;
import java.util.Date;

/* loaded from: classes4.dex */
public final class ClientCookieDecoder extends CookieDecoder {
    public static final ClientCookieDecoder STRICT = new ClientCookieDecoder(true);
    public static final ClientCookieDecoder LAX = new ClientCookieDecoder(false);

    private ClientCookieDecoder(boolean z) {
        super(z);
    }

    public Cookie decode(String str) {
        int i;
        int i2;
        int length = ((String) ObjectUtil.checkNotNull(str, "header")).length();
        if (length == 0) {
            return null;
        }
        a aVar = null;
        int i3 = 0;
        while (i3 != length) {
            char charAt = str.charAt(i3);
            if (charAt == ',') {
                break;
            } else if (charAt == '\t' || charAt == '\n' || charAt == 11 || charAt == '\f' || charAt == '\r' || charAt == ' ' || charAt == ';') {
                i3++;
            } else {
                int i4 = -1;
                if (i3 != length) {
                    i = i3;
                    while (true) {
                        char charAt2 = str.charAt(i);
                        if (charAt2 != ';') {
                            if (charAt2 != '=') {
                                i++;
                                if (i == length) {
                                    i2 = -1;
                                    i3 = i;
                                    i = length;
                                    break;
                                }
                            } else {
                                int i5 = i + 1;
                                if (i5 == length) {
                                    i2 = 0;
                                    i3 = i5;
                                    i4 = 0;
                                } else {
                                    i3 = str.indexOf(59, i5);
                                    if (i3 <= 0) {
                                        i3 = length;
                                    }
                                    i2 = i5;
                                    i4 = i3;
                                }
                            }
                        } else {
                            i2 = -1;
                            i3 = i;
                            break;
                        }
                    }
                } else {
                    i2 = -1;
                    i = i3;
                    i3 = i;
                }
                int i6 = (i4 <= 0 || str.charAt(i4 + (-1)) != ',') ? i4 : i4 - 1;
                if (aVar == null) {
                    DefaultCookie initCookie = initCookie(str, i3, i, i2, i6);
                    if (initCookie == null) {
                        return null;
                    }
                    aVar = new a(initCookie, str);
                } else {
                    aVar.a(i3, i, i2, i6);
                }
            }
        }
        return aVar.a();
    }

    /* loaded from: classes4.dex */
    private static class a {
        private final String a;
        private final DefaultCookie b;
        private String c;
        private String d;
        private long e = Long.MIN_VALUE;
        private int f;
        private int g;
        private boolean h;
        private boolean i;

        public a(DefaultCookie defaultCookie, String str) {
            this.b = defaultCookie;
            this.a = str;
        }

        private long b() {
            long j = this.e;
            if (j != Long.MIN_VALUE) {
                return j;
            }
            String a = a(this.f, this.g);
            if (a != null) {
                int i = 0;
                Date parse = HttpHeaderDateFormat.get().parse(a, new ParsePosition(0));
                if (parse != null) {
                    long time = parse.getTime() - System.currentTimeMillis();
                    long j2 = time / 1000;
                    if (time % 1000 != 0) {
                        i = 1;
                    }
                    return j2 + i;
                }
            }
            return Long.MIN_VALUE;
        }

        public Cookie a() {
            this.b.setDomain(this.c);
            this.b.setPath(this.d);
            this.b.setMaxAge(b());
            this.b.setSecure(this.h);
            this.b.setHttpOnly(this.i);
            return this.b;
        }

        public void a(int i, int i2, int i3, int i4) {
            int i5 = i2 - i;
            if (i5 == 4) {
                a(i, i3, i4);
            } else if (i5 == 6) {
                b(i, i3, i4);
            } else if (i5 == 7) {
                c(i, i3, i4);
            } else if (i5 == 8) {
                d(i, i3, i4);
            }
        }

        private void a(int i, int i2, int i3) {
            if (this.a.regionMatches(true, i, CookieHeaderNames.PATH, 0, 4)) {
                this.d = a(i2, i3);
            }
        }

        private void b(int i, int i2, int i3) {
            if (this.a.regionMatches(true, i, CookieHeaderNames.DOMAIN, 0, 5)) {
                this.c = a(i2, i3);
            } else if (this.a.regionMatches(true, i, CookieHeaderNames.SECURE, 0, 5)) {
                this.h = true;
            }
        }

        private void a(String str) {
            try {
                this.e = Math.max(Long.parseLong(str), 0L);
            } catch (NumberFormatException unused) {
            }
        }

        private void c(int i, int i2, int i3) {
            if (this.a.regionMatches(true, i, "Expires", 0, 7)) {
                this.f = i2;
                this.g = i3;
            } else if (this.a.regionMatches(true, i, CookieHeaderNames.MAX_AGE, 0, 7)) {
                a(a(i2, i3));
            }
        }

        private void d(int i, int i2, int i3) {
            if (this.a.regionMatches(true, i, CookieHeaderNames.HTTPONLY, 0, 8)) {
                this.i = true;
            }
        }

        private String a(int i, int i2) {
            if (i == -1 || i == i2) {
                return null;
            }
            return this.a.substring(i, i2);
        }
    }
}
