package com.xiaomi.miplay.mylibrary;

import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.IIDMServiceFactory;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.miplay.mylibrary.IDMAudioService;

/* loaded from: classes4.dex */
public class IDMAudioFactory implements IIDMServiceFactory {
    public IDMService createIdmService(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
        if (iDMService == null || !DataModel.SERVICE_TYPE.equals(iDMService.getType())) {
            return null;
        }
        return new IDMAudioService.Stub(iDMClient, iDMService);
    }
}
