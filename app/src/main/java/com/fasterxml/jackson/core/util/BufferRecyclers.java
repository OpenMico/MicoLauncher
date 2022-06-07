package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import java.lang.ref.SoftReference;

/* loaded from: classes.dex */
public class BufferRecyclers {
    public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _encoderRef;
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;
    private static final a a;

    static {
        a = "true".equals(System.getProperty(SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS)) ? a.a() : null;
        _recyclerRef = new ThreadLocal<>();
        _encoderRef = new ThreadLocal<>();
    }

    public static BufferRecycler getBufferRecycler() {
        SoftReference<BufferRecycler> softReference;
        SoftReference<BufferRecycler> softReference2 = _recyclerRef.get();
        BufferRecycler bufferRecycler = softReference2 == null ? null : softReference2.get();
        if (bufferRecycler == null) {
            bufferRecycler = new BufferRecycler();
            a aVar = a;
            if (aVar != null) {
                softReference = aVar.a(bufferRecycler);
            } else {
                softReference = new SoftReference<>(bufferRecycler);
            }
            _recyclerRef.set(softReference);
        }
        return bufferRecycler;
    }

    public static int releaseBuffers() {
        a aVar = a;
        if (aVar != null) {
            return aVar.b();
        }
        return -1;
    }

    public static JsonStringEncoder getJsonStringEncoder() {
        SoftReference<JsonStringEncoder> softReference = _encoderRef.get();
        JsonStringEncoder jsonStringEncoder = softReference == null ? null : softReference.get();
        if (jsonStringEncoder != null) {
            return jsonStringEncoder;
        }
        JsonStringEncoder jsonStringEncoder2 = new JsonStringEncoder();
        _encoderRef.set(new SoftReference<>(jsonStringEncoder2));
        return jsonStringEncoder2;
    }

    public static byte[] encodeAsUTF8(String str) {
        return getJsonStringEncoder().encodeAsUTF8(str);
    }

    public static char[] quoteAsJsonText(String str) {
        return getJsonStringEncoder().quoteAsString(str);
    }

    public static void quoteAsJsonText(CharSequence charSequence, StringBuilder sb) {
        getJsonStringEncoder().quoteAsString(charSequence, sb);
    }

    public static byte[] quoteAsJsonUTF8(String str) {
        return getJsonStringEncoder().quoteAsUTF8(str);
    }
}
