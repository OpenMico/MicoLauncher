package kotlin.concurrent;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.JvmName;

/* compiled from: Thread.kt */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aJ\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u001a3\u0010\u000e\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u000f0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u000f0\fH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0014"}, d2 = {"thread", "Ljava/lang/Thread;", "start", "", "isDaemon", "contextClassLoader", "Ljava/lang/ClassLoader;", "name", "", "priority", "", "block", "Lkotlin/Function0;", "", "getOrSet", ExifInterface.GPS_DIRECTION_TRUE, "", "Ljava/lang/ThreadLocal;", "default", "(Ljava/lang/ThreadLocal;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
@JvmName(name = "ThreadsKt")
/* loaded from: classes5.dex */
public final class ThreadsKt {
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.concurrent.ThreadsKt$thread$thread$1] */
    /* JADX WARN: Unknown variable types count: 1 */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Thread thread(boolean r1, boolean r2, @org.jetbrains.annotations.Nullable java.lang.ClassLoader r3, @org.jetbrains.annotations.Nullable java.lang.String r4, int r5, @org.jetbrains.annotations.NotNull final kotlin.jvm.functions.Function0<kotlin.Unit> r6) {
        /*
            java.lang.String r0 = "block"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            kotlin.concurrent.ThreadsKt$thread$thread$1 r0 = new kotlin.concurrent.ThreadsKt$thread$thread$1
            r0.<init>()
            if (r2 == 0) goto L_0x0010
            r2 = 1
            r0.setDaemon(r2)
        L_0x0010:
            if (r5 <= 0) goto L_0x0015
            r0.setPriority(r5)
        L_0x0015:
            if (r4 == 0) goto L_0x001a
            r0.setName(r4)
        L_0x001a:
            if (r3 == 0) goto L_0x001f
            r0.setContextClassLoader(r3)
        L_0x001f:
            if (r1 == 0) goto L_0x0024
            r0.start()
        L_0x0024:
            java.lang.Thread r0 = (java.lang.Thread) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.concurrent.ThreadsKt.thread(boolean, boolean, java.lang.ClassLoader, java.lang.String, int, kotlin.jvm.functions.Function0):java.lang.Thread");
    }
}
