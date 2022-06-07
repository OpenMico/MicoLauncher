package com.xiaomi.push;

import com.umeng.analytics.pro.c;
import com.xiaomi.idm.api.IDMServer;

/* loaded from: classes4.dex */
public enum en {
    ACTIVITY(IDMServer.PERSIST_TYPE_ACTIVITY),
    SERVICE_ACTION("service_action"),
    SERVICE_COMPONENT("service_component"),
    PROVIDER(c.M);
    

    /* renamed from: a  reason: collision with other field name */
    public String f25a;

    en(String str) {
        this.f25a = str;
    }
}
