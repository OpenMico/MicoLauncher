package org.fourthline.cling.model.action;

import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.profile.RemoteClientInfo;

/* loaded from: classes5.dex */
public class RemoteActionInvocation extends ActionInvocation {
    protected final RemoteClientInfo remoteClientInfo;

    public RemoteActionInvocation(Action action, ActionArgumentValue[] actionArgumentValueArr, ActionArgumentValue[] actionArgumentValueArr2, RemoteClientInfo remoteClientInfo) {
        super(action, actionArgumentValueArr, actionArgumentValueArr2, null);
        this.remoteClientInfo = remoteClientInfo;
    }

    public RemoteActionInvocation(Action action, RemoteClientInfo remoteClientInfo) {
        super(action);
        this.remoteClientInfo = remoteClientInfo;
    }

    public RemoteActionInvocation(ActionException actionException, RemoteClientInfo remoteClientInfo) {
        super(actionException);
        this.remoteClientInfo = remoteClientInfo;
    }

    public RemoteClientInfo getRemoteClientInfo() {
        return this.remoteClientInfo;
    }
}
