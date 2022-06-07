package io.netty.handler.codec.stomp;

/* loaded from: classes4.dex */
public interface StompHeadersSubframe extends StompSubframe {
    StompCommand command();

    StompHeaders headers();
}
