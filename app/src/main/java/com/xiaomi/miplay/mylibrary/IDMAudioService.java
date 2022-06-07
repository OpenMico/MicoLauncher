package com.xiaomi.miplay.mylibrary;

import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import java.util.UUID;

/* loaded from: classes4.dex */
public class IDMAudioService extends IDMService {
    private static final String a = UUID.randomUUID().toString();

    @Override // com.xiaomi.idm.api.IDMService
    public IDMServiceProto.IDMResponse request(IDMServiceProto.IDMRequest iDMRequest) {
        return null;
    }

    protected IDMAudioService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public IDMAudioService(String str) {
        super(a, str, DataModel.SERVICE_TYPE);
    }

    /* loaded from: classes4.dex */
    public static class Stub extends IDMAudioService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Skeleton extends IDMAudioService {
        public Skeleton(String str) {
            super(str);
        }
    }
}
