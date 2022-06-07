package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import sun.misc.Cleaner;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Cleaner0.java */
/* loaded from: classes4.dex */
public final class g {
    private static final long a;
    private static final InternalLogger b = InternalLoggerFactory.getInstance(g.class);

    static {
        long j;
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1);
        if (w.b()) {
            try {
                Field declaredField = allocateDirect.getClass().getDeclaredField("cleaner");
                declaredField.setAccessible(true);
                ((Cleaner) declaredField.get(allocateDirect)).clean();
                j = w.a(declaredField);
            } catch (Throwable unused) {
                j = -1;
            }
        } else {
            j = -1;
        }
        b.debug("java.nio.ByteBuffer.cleaner(): {}", j != -1 ? "available" : "unavailable");
        a = j;
        a(allocateDirect);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(ByteBuffer byteBuffer) {
        if (a != -1 && byteBuffer.isDirect()) {
            try {
                Cleaner cleaner = (Cleaner) w.a(byteBuffer, a);
                if (cleaner != null) {
                    cleaner.clean();
                }
            } catch (Throwable unused) {
            }
        }
    }

    private g() {
    }
}
