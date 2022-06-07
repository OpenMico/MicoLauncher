package org.eclipse.jetty.continuation;

import java.util.EventListener;

/* loaded from: classes5.dex */
public interface ContinuationListener extends EventListener {
    void onComplete(Continuation continuation);

    void onTimeout(Continuation continuation);
}
