package io.netty.util;

import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public final class ReferenceCountUtil {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);

    public static <T> T retain(T t) {
        return t instanceof ReferenceCounted ? (T) ((ReferenceCounted) t).retain() : t;
    }

    public static <T> T retain(T t, int i) {
        return t instanceof ReferenceCounted ? (T) ((ReferenceCounted) t).retain(i) : t;
    }

    public static <T> T touch(T t) {
        return t instanceof ReferenceCounted ? (T) ((ReferenceCounted) t).touch() : t;
    }

    public static <T> T touch(T t, Object obj) {
        return t instanceof ReferenceCounted ? (T) ((ReferenceCounted) t).touch(obj) : t;
    }

    public static boolean release(Object obj) {
        if (obj instanceof ReferenceCounted) {
            return ((ReferenceCounted) obj).release();
        }
        return false;
    }

    public static boolean release(Object obj, int i) {
        if (obj instanceof ReferenceCounted) {
            return ((ReferenceCounted) obj).release(i);
        }
        return false;
    }

    public static void safeRelease(Object obj) {
        try {
            release(obj);
        } catch (Throwable th) {
            a.warn("Failed to release a message: {}", obj, th);
        }
    }

    public static void safeRelease(Object obj, int i) {
        try {
            release(obj, i);
        } catch (Throwable th) {
            if (a.isWarnEnabled()) {
                a.warn("Failed to release a message: {} (decrement: {})", obj, Integer.valueOf(i), th);
            }
        }
    }

    public static <T> T releaseLater(T t) {
        return (T) releaseLater(t, 1);
    }

    public static <T> T releaseLater(T t, int i) {
        if (t instanceof ReferenceCounted) {
            ThreadDeathWatcher.watch(Thread.currentThread(), new a((ReferenceCounted) t, i));
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a implements Runnable {
        private final ReferenceCounted a;
        private final int b;

        a(ReferenceCounted referenceCounted, int i) {
            this.a = referenceCounted;
            this.b = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!this.a.release(this.b)) {
                    ReferenceCountUtil.a.warn("Non-zero refCnt: {}", this);
                } else {
                    ReferenceCountUtil.a.debug("Released: {}", this);
                }
            } catch (Exception e) {
                ReferenceCountUtil.a.warn("Failed to release an object: {}", this.a, e);
            }
        }

        public String toString() {
            return StringUtil.simpleClassName(this.a) + ".release(" + this.b + ") refCnt: " + this.a.refCnt();
        }
    }

    private ReferenceCountUtil() {
    }
}
