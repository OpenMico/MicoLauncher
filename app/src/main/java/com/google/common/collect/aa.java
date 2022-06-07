package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Queue;

/* compiled from: ConsumingQueueIterator.java */
@GwtCompatible
/* loaded from: classes2.dex */
class aa<T> extends AbstractIterator<T> {
    private final Queue<T> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(Queue<T> queue) {
        this.a = (Queue) Preconditions.checkNotNull(queue);
    }

    @Override // com.google.common.collect.AbstractIterator
    public T computeNext() {
        return this.a.isEmpty() ? endOfData() : this.a.remove();
    }
}
