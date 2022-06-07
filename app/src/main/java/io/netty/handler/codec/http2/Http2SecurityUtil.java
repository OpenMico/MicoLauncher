package io.netty.handler.codec.http2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class Http2SecurityUtil {
    public static final List<String> CIPHERS;
    private static final List<String> a = Collections.unmodifiableList(Arrays.asList("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256"));
    private static final List<String> b = Collections.unmodifiableList(Arrays.asList("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384"));

    static {
        ArrayList arrayList = new ArrayList(a.size() + b.size());
        arrayList.addAll(a);
        arrayList.addAll(b);
        CIPHERS = Collections.unmodifiableList(arrayList);
    }

    private Http2SecurityUtil() {
    }
}
