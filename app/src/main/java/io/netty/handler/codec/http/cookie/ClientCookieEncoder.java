package io.netty.handler.codec.http.cookie;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class ClientCookieEncoder extends CookieEncoder {
    public static final ClientCookieEncoder STRICT = new ClientCookieEncoder(true);
    public static final ClientCookieEncoder LAX = new ClientCookieEncoder(false);
    private static final Comparator<Cookie> a = new Comparator<Cookie>() { // from class: io.netty.handler.codec.http.cookie.ClientCookieEncoder.1
        /* renamed from: a */
        public int compare(Cookie cookie, Cookie cookie2) {
            String path = cookie.path();
            String path2 = cookie2.path();
            int i = Integer.MAX_VALUE;
            int length = path == null ? Integer.MAX_VALUE : path.length();
            if (path2 != null) {
                i = path2.length();
            }
            int i2 = i - length;
            if (i2 != 0) {
                return i2;
            }
            return -1;
        }
    };

    private ClientCookieEncoder(boolean z) {
        super(z);
    }

    public String encode(String str, String str2) {
        return encode(new DefaultCookie(str, str2));
    }

    public String encode(Cookie cookie) {
        StringBuilder a2 = a.a();
        a(a2, (Cookie) ObjectUtil.checkNotNull(cookie, "cookie"));
        return a.b(a2);
    }

    public String encode(Cookie... cookieArr) {
        if (((Cookie[]) ObjectUtil.checkNotNull(cookieArr, "cookies")).length == 0) {
            return null;
        }
        StringBuilder a2 = a.a();
        int i = 0;
        if (!this.strict) {
            int length = cookieArr.length;
            while (i < length) {
                a(a2, cookieArr[i]);
                i++;
            }
        } else if (cookieArr.length == 1) {
            a(a2, cookieArr[0]);
        } else {
            Cookie[] cookieArr2 = (Cookie[]) Arrays.copyOf(cookieArr, cookieArr.length);
            Arrays.sort(cookieArr2, a);
            int length2 = cookieArr2.length;
            while (i < length2) {
                a(a2, cookieArr2[i]);
                i++;
            }
        }
        return a.a(a2);
    }

    public String encode(Collection<? extends Cookie> collection) {
        if (((Collection) ObjectUtil.checkNotNull(collection, "cookies")).isEmpty()) {
            return null;
        }
        StringBuilder a2 = a.a();
        if (!this.strict) {
            for (Cookie cookie : collection) {
                a(a2, cookie);
            }
        } else if (collection.size() == 1) {
            a(a2, (Cookie) collection.iterator().next());
        } else {
            Cookie[] cookieArr = (Cookie[]) collection.toArray(new Cookie[collection.size()]);
            Arrays.sort(cookieArr, a);
            for (Cookie cookie2 : cookieArr) {
                a(a2, cookie2);
            }
        }
        return a.a(a2);
    }

    public String encode(Iterable<? extends Cookie> iterable) {
        Iterator it = ((Iterable) ObjectUtil.checkNotNull(iterable, "cookies")).iterator();
        if (!it.hasNext()) {
            return null;
        }
        StringBuilder a2 = a.a();
        if (this.strict) {
            Cookie cookie = (Cookie) it.next();
            if (!it.hasNext()) {
                a(a2, cookie);
            } else {
                ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
                arrayList.add(cookie);
                while (it.hasNext()) {
                    arrayList.add(it.next());
                }
                Cookie[] cookieArr = (Cookie[]) arrayList.toArray(new Cookie[arrayList.size()]);
                Arrays.sort(cookieArr, a);
                for (Cookie cookie2 : cookieArr) {
                    a(a2, cookie2);
                }
            }
        } else {
            while (it.hasNext()) {
                a(a2, (Cookie) it.next());
            }
        }
        return a.a(a2);
    }

    private void a(StringBuilder sb, Cookie cookie) {
        String name = cookie.name();
        String value = cookie.value() != null ? cookie.value() : "";
        validateCookie(name, value);
        if (cookie.wrap()) {
            a.b(sb, name, value);
        } else {
            a.a(sb, name, value);
        }
    }
}
