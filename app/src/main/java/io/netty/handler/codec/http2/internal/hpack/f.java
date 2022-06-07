package io.netty.handler.codec.http2.internal.hpack;

import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import io.netty.util.CharsetUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.internal.http2.Header;

/* compiled from: StaticTable.java */
/* loaded from: classes4.dex */
final class f {
    private static final List<b> b = Arrays.asList(new b(Header.TARGET_AUTHORITY_UTF8, ""), new b(Header.TARGET_METHOD_UTF8, "GET"), new b(Header.TARGET_METHOD_UTF8, "POST"), new b(Header.TARGET_PATH_UTF8, "/"), new b(Header.TARGET_PATH_UTF8, "/index.html"), new b(Header.TARGET_SCHEME_UTF8, "http"), new b(Header.TARGET_SCHEME_UTF8, "https"), new b(Header.RESPONSE_STATUS_UTF8, "200"), new b(Header.RESPONSE_STATUS_UTF8, "204"), new b(Header.RESPONSE_STATUS_UTF8, "206"), new b(Header.RESPONSE_STATUS_UTF8, "304"), new b(Header.RESPONSE_STATUS_UTF8, "400"), new b(Header.RESPONSE_STATUS_UTF8, "404"), new b(Header.RESPONSE_STATUS_UTF8, "500"), new b("accept-charset", ""), new b("accept-encoding", "gzip, deflate"), new b("accept-language", ""), new b("accept-ranges", ""), new b("accept", ""), new b("access-control-allow-origin", ""), new b("age", ""), new b("allow", ""), new b(Common.AUTHORIZATION, ""), new b(Common.CACHE_CONTROL, ""), new b("content-disposition", ""), new b(Common.CONTENT_ENCODING, ""), new b("content-language", ""), new b(Common.CONTENT_LENGTH, ""), new b("content-location", ""), new b(Common.CONTENT_RANGE, ""), new b(Common.CONTENT_TYPE, ""), new b("cookie", ""), new b(Common.DATE, ""), new b("etag", ""), new b("expect", ""), new b("expires", ""), new b("from", ""), new b(b.E, ""), new b("if-match", ""), new b("if-modified-since", ""), new b("if-none-match", ""), new b("if-range", ""), new b("if-unmodified-since", ""), new b(Common.LAST_MODIFIED, ""), new b(OneTrack.Param.LINK, ""), new b("location", ""), new b("max-forwards", ""), new b("proxy-authenticate", ""), new b("proxy-authorization", ""), new b(Common.RANGE, ""), new b("referer", ""), new b("refresh", ""), new b("retry-after", ""), new b("server", ""), new b("set-cookie", ""), new b("strict-transport-security", ""), new b("transfer-encoding", ""), new b("user-agent", ""), new b("vary", ""), new b("via", ""), new b("www-authenticate", ""));
    private static final Map<String, Integer> c = a();
    static final int a = b.size();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static b a(int i) {
        return b.get(i - 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(byte[] bArr) {
        Integer num = c.get(new String(bArr, 0, bArr.length, CharsetUtil.ISO_8859_1));
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(byte[] bArr, byte[] bArr2) {
        int a2 = a(bArr);
        if (a2 == -1) {
            return -1;
        }
        while (a2 <= a) {
            b a3 = a(a2);
            if (!c.a(bArr, a3.f)) {
                break;
            } else if (c.a(bArr2, a3.g)) {
                return a2;
            } else {
                a2++;
            }
        }
        return -1;
    }

    private static Map<String, Integer> a() {
        int size = b.size();
        HashMap hashMap = new HashMap(size);
        while (size > 0) {
            b a2 = a(size);
            hashMap.put(new String(a2.f, 0, a2.f.length, CharsetUtil.ISO_8859_1), Integer.valueOf(size));
            size--;
        }
        return hashMap;
    }
}
