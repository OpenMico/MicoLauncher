package org.fourthline.cling.support.connectionmanager;

import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.types.ErrorCode;

/* loaded from: classes5.dex */
public class ConnectionManagerException extends ActionException {
    public ConnectionManagerException(int i, String str) {
        super(i, str);
    }

    public ConnectionManagerException(int i, String str, Throwable th) {
        super(i, str, th);
    }

    public ConnectionManagerException(ErrorCode errorCode, String str) {
        super(errorCode, str);
    }

    public ConnectionManagerException(ErrorCode errorCode) {
        super(errorCode);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ConnectionManagerException(org.fourthline.cling.support.connectionmanager.ConnectionManagerErrorCode r3, java.lang.String r4) {
        /*
            r2 = this;
            int r0 = r3.getCode()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = r3.getDescription()
            r1.append(r3)
            java.lang.String r3 = ". "
            r1.append(r3)
            r1.append(r4)
            java.lang.String r3 = "."
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            r2.<init>(r0, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.support.connectionmanager.ConnectionManagerException.<init>(org.fourthline.cling.support.connectionmanager.ConnectionManagerErrorCode, java.lang.String):void");
    }

    public ConnectionManagerException(ConnectionManagerErrorCode connectionManagerErrorCode) {
        super(connectionManagerErrorCode.getCode(), connectionManagerErrorCode.getDescription());
    }
}
