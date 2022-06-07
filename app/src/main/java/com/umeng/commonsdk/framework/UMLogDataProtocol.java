package com.umeng.commonsdk.framework;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface UMLogDataProtocol {

    /* loaded from: classes2.dex */
    public enum UMBusinessType {
        U_APP,
        U_INTERNAL,
        U_ZeroEnv
    }

    void removeCacheData(Object obj);

    JSONObject setupReportData(long j);

    void workEvent(Object obj, int i);
}
