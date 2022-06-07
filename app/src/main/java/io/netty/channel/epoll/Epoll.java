package io.netty.channel.epoll;

/* loaded from: classes4.dex */
public final class Epoll {
    private static final Throwable a;

    /* JADX WARN: Removed duplicated region for block: B:23:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0032  */
    static {
        /*
            r0 = 0
            io.netty.channel.unix.FileDescriptor r1 = io.netty.channel.epoll.Native.newEpollCreate()     // Catch: Throwable -> 0x0026, all -> 0x001c
            io.netty.channel.unix.FileDescriptor r2 = io.netty.channel.epoll.Native.newEventFd()     // Catch: Throwable -> 0x0017, all -> 0x0015
            if (r1 == 0) goto L_0x000e
            r1.close()     // Catch: Exception -> 0x000e
        L_0x000e:
            if (r2 == 0) goto L_0x0013
            r2.close()     // Catch: Exception -> 0x0013
        L_0x0013:
            r1 = r0
            goto L_0x002d
        L_0x0015:
            r0 = move-exception
            goto L_0x0020
        L_0x0017:
            r2 = move-exception
            r3 = r2
            r2 = r1
            r1 = r3
            goto L_0x0028
        L_0x001c:
            r1 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
        L_0x0020:
            if (r1 == 0) goto L_0x0025
            r1.close()     // Catch: Exception -> 0x0025
        L_0x0025:
            throw r0
        L_0x0026:
            r1 = move-exception
            r2 = r0
        L_0x0028:
            if (r2 == 0) goto L_0x002d
            r2.close()     // Catch: Exception -> 0x002d
        L_0x002d:
            if (r1 == 0) goto L_0x0032
            io.netty.channel.epoll.Epoll.a = r1
            goto L_0x0042
        L_0x0032:
            boolean r1 = io.netty.util.internal.PlatformDependent.hasUnsafe()
            if (r1 == 0) goto L_0x0039
            goto L_0x0040
        L_0x0039:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "sun.misc.Unsafe not available"
            r0.<init>(r1)
        L_0x0040:
            io.netty.channel.epoll.Epoll.a = r0
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.Epoll.<clinit>():void");
    }

    public static boolean isAvailable() {
        return a == null;
    }

    public static void ensureAvailability() {
        if (a != null) {
            throw ((Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(a));
        }
    }

    public static Throwable unavailabilityCause() {
        return a;
    }

    private Epoll() {
    }
}
