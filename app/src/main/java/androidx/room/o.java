package androidx.room;

import androidx.annotation.NonNull;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/* compiled from: TransactionExecutor.java */
/* loaded from: classes.dex */
class o implements Executor {
    private final Executor a;
    private final ArrayDeque<Runnable> b = new ArrayDeque<>();
    private Runnable c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(@NonNull Executor executor) {
        this.a = executor;
    }

    @Override // java.util.concurrent.Executor
    public synchronized void execute(final Runnable runnable) {
        this.b.offer(new Runnable() { // from class: androidx.room.o.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnable.run();
                } finally {
                    o.this.a();
                }
            }
        });
        if (this.c == null) {
            a();
        }
    }

    synchronized void a() {
        Runnable poll = this.b.poll();
        this.c = poll;
        if (poll != null) {
            this.a.execute(this.c);
        }
    }
}
