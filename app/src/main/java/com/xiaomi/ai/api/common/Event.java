package com.xiaomi.ai.api.common;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Event<T> extends Message<EventHeader, T> {
    private List<Context> context;

    public Event() {
        this.context = null;
    }

    public Event(EventHeader eventHeader, T t) {
        super(eventHeader, t);
        this.context = null;
    }

    public Event(List<Context> list, EventHeader eventHeader, T t) {
        super(eventHeader, t);
        this.context = null;
        this.context = list;
    }

    public String getId() {
        return getHeader().getId();
    }

    public List<Context> getContexts() {
        if (this.context == null) {
            this.context = new ArrayList();
        }
        return this.context;
    }

    public void setContext(List<Context> list) {
        this.context = list;
    }

    public Event addContext(Context context) {
        getContexts().add(context);
        return this;
    }
}
