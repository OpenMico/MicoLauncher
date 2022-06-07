package io.reactivex.internal.util;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public final class BlockingIgnoringReceiver extends CountDownLatch implements Action, Consumer<Throwable> {
    public Throwable error;

    public BlockingIgnoringReceiver() {
        super(1);
    }

    public void accept(Throwable th) {
        this.error = th;
        countDown();
    }

    @Override // io.reactivex.functions.Action
    public void run() {
        countDown();
    }
}
