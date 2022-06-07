package androidx.fragment.app;

import android.util.Log;
import java.io.Writer;

/* compiled from: LogWriter.java */
/* loaded from: classes.dex */
final class q extends Writer {
    private final String a;
    private StringBuilder b = new StringBuilder(128);

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(String str) {
        this.a = str;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        a();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        a();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                a();
            } else {
                this.b.append(c);
            }
        }
    }

    private void a() {
        if (this.b.length() > 0) {
            Log.d(this.a, this.b.toString());
            StringBuilder sb = this.b;
            sb.delete(0, sb.length());
        }
    }
}
