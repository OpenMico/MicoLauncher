package org.seamless.statemachine;

/* loaded from: classes4.dex */
public class TransitionException extends RuntimeException {
    public TransitionException(String str) {
        super(str);
    }

    public TransitionException(String str, Throwable th) {
        super(str, th);
    }
}
