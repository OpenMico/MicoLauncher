package androidx.tracing;

import android.os.Trace;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* compiled from: TraceApi18Impl.java */
@RequiresApi(18)
/* loaded from: classes.dex */
final class a {
    public static void a(@NonNull String str) {
        Trace.beginSection(str);
    }

    public static void a() {
        Trace.endSection();
    }
}
