package org.seamless.statemachine;

/* loaded from: classes4.dex */
public interface StateMachine<S> {
    public static final String METHOD_CURRENT_STATE = "getCurrentState";
    public static final String METHOD_FORCE_STATE = "forceState";

    void forceState(Class<? extends S> cls);

    S getCurrentState();
}
