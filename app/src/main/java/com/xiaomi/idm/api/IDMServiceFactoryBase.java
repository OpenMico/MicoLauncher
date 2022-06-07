package com.xiaomi.idm.api;

import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.IDMServiceFactory;

/* loaded from: classes3.dex */
public class IDMServiceFactoryBase {
    public IDMService createIDMService(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
        return IDMServiceFactory.createIDMService(iDMClient, iDMService);
    }
}
