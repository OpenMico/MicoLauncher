package kotlinx.coroutines;

import com.xiaomi.onetrack.api.b;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: Debug.kt */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0017\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0015H\u0081\b\u001a\b\u0010\u0016\u001a\u00020\u0013H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0014\u0010\b\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u000e\u0010\n\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000\"\u0014\u0010\u000f\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0003\"\u000e\u0010\u0011\u001a\u00020\u000bX\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"ASSERTIONS_ENABLED", "", "getASSERTIONS_ENABLED", "()Z", "COROUTINE_ID", "Ljava/util/concurrent/atomic/AtomicLong;", "getCOROUTINE_ID", "()Ljava/util/concurrent/atomic/AtomicLong;", "DEBUG", "getDEBUG", "DEBUG_PROPERTY_NAME", "", "DEBUG_PROPERTY_VALUE_AUTO", "DEBUG_PROPERTY_VALUE_OFF", "DEBUG_PROPERTY_VALUE_ON", "RECOVER_STACK_TRACES", "getRECOVER_STACK_TRACES", "STACKTRACE_RECOVERY_PROPERTY_NAME", "assert", "", b.p, "Lkotlin/Function0;", "resetCoroutineId", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class DebugKt {
    @NotNull
    public static final String DEBUG_PROPERTY_NAME = "kotlinx.coroutines.debug";
    @NotNull
    public static final String DEBUG_PROPERTY_VALUE_AUTO = "auto";
    @NotNull
    public static final String DEBUG_PROPERTY_VALUE_OFF = "off";
    @NotNull
    public static final String DEBUG_PROPERTY_VALUE_ON = "on";
    @NotNull
    public static final String STACKTRACE_RECOVERY_PROPERTY_NAME = "kotlinx.coroutines.stacktrace.recovery";
    private static final boolean a = CoroutineId.class.desiredAssertionStatus();
    private static final boolean b;
    private static final boolean c;
    @NotNull
    private static final AtomicLong d;

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002c, code lost:
        if (r0.equals(kotlinx.coroutines.DebugKt.DEBUG_PROPERTY_VALUE_AUTO) != false) goto L_0x006e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:
        if (r0.equals("on") != false) goto L_0x004a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0048, code lost:
        if (r0.equals("") != false) goto L_0x004a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004a, code lost:
        r0 = true;
     */
    static {
        /*
            java.lang.Class<kotlinx.coroutines.CoroutineId> r0 = kotlinx.coroutines.CoroutineId.class
            boolean r0 = r0.desiredAssertionStatus()
            kotlinx.coroutines.DebugKt.a = r0
            java.lang.String r0 = "kotlinx.coroutines.debug"
            java.lang.String r0 = kotlinx.coroutines.internal.SystemPropsKt.systemProp(r0)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x006e
            int r3 = r0.hashCode()
            if (r3 == 0) goto L_0x0042
            r4 = 3551(0xddf, float:4.976E-42)
            if (r3 == r4) goto L_0x0039
            r4 = 109935(0x1ad6f, float:1.54052E-40)
            if (r3 == r4) goto L_0x002f
            r4 = 3005871(0x2dddaf, float:4.212122E-39)
            if (r3 != r4) goto L_0x004c
            java.lang.String r3 = "auto"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x004c
            goto L_0x006e
        L_0x002f:
            java.lang.String r3 = "off"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x004c
            r0 = r1
            goto L_0x0072
        L_0x0039:
            java.lang.String r3 = "on"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x004c
            goto L_0x004a
        L_0x0042:
            java.lang.String r3 = ""
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x004c
        L_0x004a:
            r0 = r2
            goto L_0x0072
        L_0x004c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "System property 'kotlinx.coroutines.debug' has unrecognized value '"
            r1.append(r2)
            r1.append(r0)
            r0 = 39
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            throw r1
        L_0x006e:
            boolean r0 = getASSERTIONS_ENABLED()
        L_0x0072:
            kotlinx.coroutines.DebugKt.b = r0
            boolean r0 = kotlinx.coroutines.DebugKt.b
            if (r0 == 0) goto L_0x0081
            java.lang.String r0 = "kotlinx.coroutines.stacktrace.recovery"
            boolean r0 = kotlinx.coroutines.internal.SystemPropsKt.systemProp(r0, r2)
            if (r0 == 0) goto L_0x0081
            r1 = r2
        L_0x0081:
            kotlinx.coroutines.DebugKt.c = r1
            java.util.concurrent.atomic.AtomicLong r0 = new java.util.concurrent.atomic.AtomicLong
            r1 = 0
            r0.<init>(r1)
            kotlinx.coroutines.DebugKt.d = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DebugKt.<clinit>():void");
    }

    public static final boolean getASSERTIONS_ENABLED() {
        return a;
    }

    public static final boolean getDEBUG() {
        return b;
    }

    public static final boolean getRECOVER_STACK_TRACES() {
        return c;
    }

    @NotNull
    public static final AtomicLong getCOROUTINE_ID() {
        return d;
    }

    public static final void resetCoroutineId() {
        d.set(0L);
    }
}
