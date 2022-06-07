package com.xiaomi.idm.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.conn.ConnParam;
import com.xiaomi.idm.api.conn.EndPoint;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.mi_connect_sdk.util.LogUtil;

/* loaded from: classes3.dex */
public abstract class IDMService extends IIDMService {
    public static final String TYPE_HANDOFF = "urn:aiot-spec-v3:service:handoff:00000001:1";
    public static final String TYPE_INPUT = "urn:aiot-spec-v3:service:input:00000001:1";
    public static final String TYPE_IOT = "urn:aiot-spec-v3:service:iot-local-control:00000001:1";
    public static final String TYPE_IPC = "urn:aiot-spec-v3:service:ip-camera:00000001:1";
    public static final String TYPE_LIGHT = "urn:aiot-spec-v3:service:light:00000001:1";
    public static final String TYPE_MOTIONSENSOR = "urn:aiot-spec-v3:service:motionsensor:00000001:1";
    private IDMServiceProto.IDMService a;
    protected IDMEventCallback mEventCallback;
    protected boolean mEventEnable;

    /* loaded from: classes3.dex */
    public interface IDMEventCallback {
        void onEvent(IDMService iDMService, IDMServiceProto.IDMEvent iDMEvent);
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public int enableEvent(int i, boolean z) {
        return -1;
    }

    public abstract IDMServiceProto.IDMResponse request(IDMServiceProto.IDMRequest iDMRequest);

    public IDMService(String str, String str2, String str3) {
        this.a = IDMServiceProto.IDMService.newBuilder().setUuid(str).setName(str2).setType(str3).setOriginalUuid(str).build();
    }

    protected IDMService(String str, String str2, String str3, String str4) {
        this(str, str2, str3);
        this.a = IDMServiceProto.IDMService.newBuilder(this.a).setSuperType(str4).build();
    }

    protected IDMService() {
        this.a = IDMServiceProto.IDMService.newBuilder().build();
    }

    public IDMService(IDMServiceProto.IDMService iDMService) {
        this.a = iDMService;
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public String getName() {
        return this.a.getName();
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public String getUUID() {
        return this.a.getUuid();
    }

    public void setUUID(String str) {
        this.a = IDMServiceProto.IDMService.newBuilder(this.a).setUuid(str).build();
    }

    public void update(IDMServiceProto.IDMService iDMService) {
        this.a = iDMService;
    }

    public String getOriginalUuid() {
        return this.a.getOriginalUuid();
    }

    public String getUseSuper() {
        return this.a.getSuperType();
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public String getType() {
        return this.a.getType();
    }

    public IDMServiceProto.Endpoint getEndpoint() {
        return this.a.getEndpoint();
    }

    public IDMServiceProto.IDMService getIDMServiceProto() {
        return this.a;
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public byte[] request(byte[] bArr) {
        IDMServiceProto.IDMRequest iDMRequest;
        try {
            iDMRequest = IDMServiceProto.IDMRequest.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("IDMService", e.getMessage(), e);
            iDMRequest = null;
        }
        if (iDMRequest != null) {
            return request(iDMRequest).toByteArray();
        }
        LogUtil.e("IDMService", "request Called but bytes is null", new Object[0]);
        return null;
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public boolean onServiceConnectStatus(byte[] bArr) {
        IDMServiceProto.IDMConnectServiceRequest iDMConnectServiceRequest;
        try {
            iDMConnectServiceRequest = IDMServiceProto.IDMConnectServiceRequest.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("IDMService", e.getMessage(), e);
            iDMConnectServiceRequest = null;
        }
        if (iDMConnectServiceRequest != null) {
            return onServiceConnectStatus(iDMConnectServiceRequest.getStatus(), iDMConnectServiceRequest.getClientId(), EndPoint.buildFromProto(iDMConnectServiceRequest.getEndpoint()), ConnParam.buildFromProto(iDMConnectServiceRequest.getConnParam()));
        }
        LogUtil.e("IDMService", " onConnectServiceRequest Called but param is null", new Object[0]);
        return false;
    }

    public boolean onServiceConnectStatus(int i, String str, EndPoint endPoint, ConnParam connParam) {
        LogUtil.d("IDMService", "onServiceConnectStatus request: default ", new Object[0]);
        return false;
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public void onServiceChanged(byte[] bArr) {
        IDMServiceProto.OnServiceChangeResult onServiceChangeResult;
        LogUtil.d("IDMService", "onServiceChanged", new Object[0]);
        try {
            onServiceChangeResult = IDMServiceProto.OnServiceChangeResult.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("IDMService", e.getMessage(), e);
            onServiceChangeResult = null;
        }
        if (onServiceChangeResult == null) {
            LogUtil.e("IDMService", "onServiceChanged: onServiceChangeResult proto from native is null!", new Object[0]);
            return;
        }
        String oldServiceId = onServiceChangeResult.getOldServiceId();
        if (!getUUID().equals(oldServiceId)) {
            LogUtil.e("IDMService", "onServiceChanged: oldServiceId from native does not match current serviceId!", new Object[0]);
        }
        String newServiceId = onServiceChangeResult.getNewServiceId();
        LogUtil.d("IDMService", "onServiceChanged: oldServiceId = [" + oldServiceId + "], newServiceId = [" + newServiceId + "]", new Object[0]);
        setUUID(newServiceId);
    }

    @Override // com.xiaomi.idm.api.IIDMService
    public void onAdvertisingResult(int i) {
        LogUtil.d("IDMService", "onAdvertisingResult: status = " + i, new Object[0]);
    }

    public byte[] toByteArray() {
        IDMServiceProto.IDMService iDMService = this.a;
        if (iDMService == null) {
            return null;
        }
        return iDMService.toByteArray();
    }

    public boolean isEventEnabled() {
        return this.mEventEnable;
    }

    public void setEventCallback(IDMEventCallback iDMEventCallback) {
        this.mEventCallback = iDMEventCallback;
    }

    @Override // com.xiaomi.idm.api.IIDMService
    protected void notifyEvent(int i, byte[] bArr) {
        if (this.mEventCallback != null && this.mEventEnable) {
            this.mEventCallback.onEvent(this, IDMServiceProto.IDMEvent.newBuilder().setEid(i).setUuid(getUUID()).setEvent(ByteString.copyFrom(bArr)).build());
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Action<T> {
        protected int aid;
        protected IDMService service;

        public abstract byte[] invoke();

        public abstract T parseResponse(byte[] bArr) throws RmiException;

        public abstract byte[] toBytes();

        public Action(int i, IDMService iDMService) {
            this.aid = i;
            this.service = iDMService;
        }

        public int getAid() {
            return this.aid;
        }

        public String getServiceUUID() {
            return this.service.getUUID();
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class BuiltinService extends IDMService {
        protected BuiltinService() {
        }

        public BuiltinService(IDMServiceProto.IDMService iDMService) {
            super(iDMService);
        }

        public BuiltinService(String str, String str2, String str3) {
            super(str, str2, str3);
        }

        public BuiltinService(String str, String str2, String str3, String str4) {
            super(str, str2, str3, str4);
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Event {
        private IDMService a;
        private int b;

        /* JADX INFO: Access modifiers changed from: protected */
        public abstract void onEvent(byte[] bArr);

        public Event(IDMService iDMService, int i) {
            this.a = iDMService;
            this.b = i;
        }

        public String getUUID() {
            return this.a.getUUID();
        }

        public int getEid() {
            return this.b;
        }
    }
}
