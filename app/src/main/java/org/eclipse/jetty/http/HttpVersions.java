package org.eclipse.jetty.http;

import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;

/* loaded from: classes5.dex */
public class HttpVersions {
    public static final String HTTP_0_9 = "";
    public static final int HTTP_0_9_ORDINAL = 9;
    public static final int HTTP_1_0_ORDINAL = 10;
    public static final int HTTP_1_1_ORDINAL = 11;
    public static final BufferCache CACHE = new BufferCache();
    public static final Buffer HTTP_0_9_BUFFER = CACHE.add("", 9);
    public static final String HTTP_1_0 = "HTTP/1.0";
    public static final Buffer HTTP_1_0_BUFFER = CACHE.add(HTTP_1_0, 10);
    public static final String HTTP_1_1 = "HTTP/1.1";
    public static final Buffer HTTP_1_1_BUFFER = CACHE.add(HTTP_1_1, 11);
}
