package androidx.tracing;

import android.os.Trace;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* compiled from: TraceApi29Impl.java */
@RequiresApi(29)
/* loaded from: classes.dex */
final class b {
    public static void a(@NonNull String str, int i) {
        Trace.beginAsyncSection(str, i);
    }

    public static void b(@NonNull String str, int i) {
        Trace.endAsyncSection(str, i);
    }

    public static void c(@NonNull String str, int i) {
        Trace.setCounter(str, i);
    }
}
