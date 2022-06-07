package org.fourthline.cling.controlpoint.event;

import org.fourthline.cling.controlpoint.ActionCallback;

/* loaded from: classes5.dex */
public class ExecuteAction {
    protected ActionCallback callback;

    public ExecuteAction(ActionCallback actionCallback) {
        this.callback = actionCallback;
    }

    public ActionCallback getCallback() {
        return this.callback;
    }
}
