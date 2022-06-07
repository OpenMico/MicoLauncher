package com.xiaomi.ai.api.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaomi.ai.api.common.MessageHeader;

/* loaded from: classes3.dex */
public abstract class Message<H extends MessageHeader<?>, P> {
    private H header;
    private P payload;

    /* JADX INFO: Access modifiers changed from: protected */
    public Message() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Message(H h, P p) {
        this.header = h;
        this.payload = p;
    }

    public H getHeader() {
        return this.header;
    }

    public void setHeader(H h) {
        this.header = h;
    }

    public String getNamespace() {
        return getHeader().getNamespace();
    }

    public String getName() {
        return getHeader().getName();
    }

    public String getFullName() {
        return getHeader().getFullName();
    }

    public P getPayload() {
        return this.payload;
    }

    public void setPayload(P p) {
        this.payload = p;
    }

    public String toString() {
        try {
            return APIUtils.toJsonString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    public String toJsonString() throws JsonProcessingException {
        return APIUtils.toJsonString(this);
    }
}
