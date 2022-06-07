package com.xiaomi.idm.api.conn;

import com.google.protobuf.GeneratedMessageLite;

/* loaded from: classes3.dex */
public abstract class ConnConfig<T extends GeneratedMessageLite> {
    public static final String TAG = "ConnConfig";

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract T toProto();
}
