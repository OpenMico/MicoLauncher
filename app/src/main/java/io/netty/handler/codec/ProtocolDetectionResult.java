package io.netty.handler.codec;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public final class ProtocolDetectionResult<T> {
    private static final ProtocolDetectionResult a = new ProtocolDetectionResult(ProtocolDetectionState.NEEDS_MORE_DATA, null);
    private static final ProtocolDetectionResult b = new ProtocolDetectionResult(ProtocolDetectionState.INVALID, null);
    private final ProtocolDetectionState c;
    private final T d;

    public static <T> ProtocolDetectionResult<T> needsMoreData() {
        return a;
    }

    public static <T> ProtocolDetectionResult<T> invalid() {
        return b;
    }

    public static <T> ProtocolDetectionResult<T> detected(T t) {
        return new ProtocolDetectionResult<>(ProtocolDetectionState.DETECTED, ObjectUtil.checkNotNull(t, "protocol"));
    }

    private ProtocolDetectionResult(ProtocolDetectionState protocolDetectionState, T t) {
        this.c = protocolDetectionState;
        this.d = t;
    }

    public ProtocolDetectionState state() {
        return this.c;
    }

    public T detectedProtocol() {
        return this.d;
    }
}
