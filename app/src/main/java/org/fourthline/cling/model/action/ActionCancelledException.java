package org.fourthline.cling.model.action;

import org.fourthline.cling.model.types.ErrorCode;

/* loaded from: classes5.dex */
public class ActionCancelledException extends ActionException {
    public ActionCancelledException(InterruptedException interruptedException) {
        super(ErrorCode.ACTION_FAILED, "Action execution interrupted", interruptedException);
    }
}
