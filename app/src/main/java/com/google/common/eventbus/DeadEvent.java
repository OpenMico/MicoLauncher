package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Beta
/* loaded from: classes2.dex */
public class DeadEvent {
    private final Object a;
    private final Object b;

    public DeadEvent(Object obj, Object obj2) {
        this.a = Preconditions.checkNotNull(obj);
        this.b = Preconditions.checkNotNull(obj2);
    }

    public Object getSource() {
        return this.a;
    }

    public Object getEvent() {
        return this.b;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("source", this.a).add("event", this.b).toString();
    }
}
