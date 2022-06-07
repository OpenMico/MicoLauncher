package com.xiaomi.idm.api;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.conn.ConnParam;
import com.xiaomi.idm.api.conn.EndPoint;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.utils.ResponseHelper;
import com.xiaomi.mi_connect_sdk.BuildConfig;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mi_connect_service.IIDMServiceProcCallback;
import com.xiaomi.mi_connect_service.proto.IPCParam;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class IDMServer extends IDM {
    public static final String PERSIST_TYPE_ACTIVITY = "activity";
    public static final String PERSIST_TYPE_SERVICE = "service";
    private long c;
    private IIDMServiceProcCallback d = new IIDMServiceProcCallback.Stub() { // from class: com.xiaomi.idm.api.IDMServer.1
        @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
        public void onRequest(byte[] bArr) {
            IPCParam.OnRequest onRequest;
            IDMServiceProto.IDMResponse iDMResponse;
            LogUtil.d("IDMServer", "onRequest", new Object[0]);
            if (bArr == null) {
                LogUtil.e("IDMServer", "onRequest called but param is null. Ignore request.", new Object[0]);
                return;
            }
            IDMServiceProto.IDMRequest iDMRequest = null;
            try {
                onRequest = IPCParam.OnRequest.parseFrom(bArr);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
                onRequest = null;
            }
            if (onRequest != null) {
                iDMRequest = onRequest.getIdmRequest();
            }
            if (iDMRequest == null) {
                LogUtil.e("IDMServer", "onRequest called but parse failed. Ignore request.", new Object[0]);
                return;
            }
            IDMService iDMService = (IDMService) IDMServer.this.b.get(iDMRequest.getUuid());
            if (iDMService != null) {
                iDMResponse = iDMService.request(iDMRequest);
            } else {
                LogUtil.e("IDMServer", "onRequest service not found: " + iDMRequest.getUuid(), new Object[0]);
                iDMResponse = ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_SERVICE_NOT_FOUND);
            }
            if (iDMResponse == null) {
                LogUtil.e("IDMServer", "onRequest response null", new Object[0]);
                iDMResponse = ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_RESPONSE_NULL);
            }
            if (IDMServer.this.serviceAvailable()) {
                try {
                    IDMServer.this.mService.response(IDMServer.this.getClientId(), IPCParam.Response.newBuilder().setIdmResponse(iDMResponse).build().toByteArray());
                } catch (RemoteException e2) {
                    LogUtil.e("IDMServer", e2.getMessage(), e2);
                }
            } else {
                LogUtil.e("IDMServer", "onRequest, service unavailable", new Object[0]);
            }
        }

        @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
        public void onConnectServiceStatus(byte[] bArr) {
            LogUtil.d("IDMServer", "onServiceConnectStatus", new Object[0]);
            if (bArr == null) {
                LogUtil.e("IDMServer", "onServiceConnectStatus called but param is null. Ignore request.", new Object[0]);
                return;
            }
            IPCParam.OnConnectServiceRequest onConnectServiceRequest = null;
            try {
                onConnectServiceRequest = IPCParam.OnConnectServiceRequest.parseFrom(bArr);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
            }
            if (onConnectServiceRequest == null) {
                LogUtil.e("IDMServer", "onServiceConnectStatus onConnectServiceRequestParam is null", new Object[0]);
                return;
            }
            IDMServiceProto.IDMConnectServiceRequest idmConnectServiceRequest = onConnectServiceRequest.getIdmConnectServiceRequest();
            if (idmConnectServiceRequest == null) {
                LogUtil.e("IDMServer", "onServiceConnectStatus called but parse failed. Ignore request.", new Object[0]);
                return;
            }
            String serviceId = idmConnectServiceRequest.getServiceId();
            IDMService iDMService = (IDMService) IDMServer.this.b.get(serviceId);
            if (iDMService == null) {
                LogUtil.e("IDMServer", "onServiceConnectStatus service not found: " + serviceId, new Object[0]);
                return;
            }
            int status = idmConnectServiceRequest.getStatus();
            if (AnonymousClass3.a[ResponseCode.ConnectCode.fromCode(status).ordinal()] == 1) {
                long currentTimeMillis = System.currentTimeMillis();
                IDMServer.this.c = currentTimeMillis;
                LogUtil.i("IDMServer", "IDM_TIME_USAGE: onServiceConnectStatus[%s]: TO_BE_CONFIRMED Time usage = [%d]", iDMService.getUUID(), Long.valueOf(currentTimeMillis - IDMServer.this.c));
            }
            LogUtil.d("IDMServer", "onConnectServiceStatus : status = " + status + " clientId = " + idmConnectServiceRequest.getClientId(), new Object[0]);
            if (!iDMService.onServiceConnectStatus(idmConnectServiceRequest.getStatus(), idmConnectServiceRequest.getClientId(), EndPoint.buildFromProto(idmConnectServiceRequest.getEndpoint()), ConnParam.buildFromProto(idmConnectServiceRequest.getConnParam())) && status == ResponseCode.ConnectCode.CONN_STAT_TO_BE_CONFIRM.getCode()) {
                IDMServer.this.acceptConnection(serviceId, idmConnectServiceRequest.getClientId());
            }
        }

        @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
        public void onAdvertisingResult(byte[] bArr) {
            LogUtil.d("IDMServer", "onAdvertisingResult", new Object[0]);
            if (bArr == null) {
                LogUtil.e("IDMServer", "onAdvertisingResult called but param is null. Ignore request.", new Object[0]);
                return;
            }
            IPCParam.OnIDMAdvertisingResult onIDMAdvertisingResult = null;
            try {
                onIDMAdvertisingResult = IPCParam.OnIDMAdvertisingResult.parseFrom(bArr);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
            }
            if (onIDMAdvertisingResult == null) {
                LogUtil.e("IDMServer", "onAdvertisingResult onIDMAdvertisingResult is null", new Object[0]);
                return;
            }
            IDMServiceProto.IDMAdvertisingResult idmAdvertisingResult = onIDMAdvertisingResult.getIdmAdvertisingResult();
            if (idmAdvertisingResult == null) {
                LogUtil.e("IDMServer", "onAdvertisingResult called but parse failed. Ignore request.", new Object[0]);
                return;
            }
            String serviceId = idmAdvertisingResult.getServiceId();
            IDMService iDMService = (IDMService) IDMServer.this.b.get(serviceId);
            if (iDMService == null) {
                LogUtil.e("IDMServer", "onAdvertisingResult service not found: " + serviceId, new Object[0]);
                return;
            }
            iDMService.onAdvertisingResult(idmAdvertisingResult.getStatus());
        }

        @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
        public int onSetEventCallback(byte[] bArr) {
            IPCParam.OnSetEventCallback onSetEventCallback;
            try {
                onSetEventCallback = IPCParam.OnSetEventCallback.parseFrom(bArr);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
                onSetEventCallback = null;
            }
            int i = -1;
            if (onSetEventCallback == null) {
                LogUtil.e("IDMServer", "onSetEventCallback eventParam is null", new Object[0]);
                return -1;
            }
            IDMServiceProto.IDMEvent idmEvent = onSetEventCallback.getIdmEvent();
            if (idmEvent == null) {
                LogUtil.e("IDMServer", "onSetEventCallback idmEvent is null", new Object[0]);
                return -1;
            }
            String uuid = idmEvent.getUuid();
            int eid = idmEvent.getEid();
            boolean enable = idmEvent.getEnable();
            IDMService iDMService = (IDMService) IDMServer.this.b.get(uuid);
            if (iDMService != null) {
                i = iDMService.enableEvent(eid, enable);
                if (iDMService.isEventEnabled()) {
                    iDMService.setEventCallback(IDMServer.this.a);
                } else {
                    iDMService.setEventCallback(null);
                }
            }
            return i;
        }

        @Override // com.xiaomi.mi_connect_service.IIDMServiceProcCallback
        public void onServiceChanged(byte[] bArr) {
            IPCParam.ServiceOnServiceChanged serviceOnServiceChanged;
            LogUtil.d("IDMServer", "onServiceChanged", new Object[0]);
            try {
                serviceOnServiceChanged = IPCParam.ServiceOnServiceChanged.parseFrom(bArr);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
                serviceOnServiceChanged = null;
            }
            if (serviceOnServiceChanged == null) {
                LogUtil.e("IDMServer", "onServiceChanged proto is null", new Object[0]);
                return;
            }
            String oldServiceId = serviceOnServiceChanged.getOldServiceId();
            String newServiceId = serviceOnServiceChanged.getNewServiceId();
            if (oldServiceId == null || oldServiceId.isEmpty()) {
                LogUtil.e("IDMServer", "oldServiceId is null", new Object[0]);
            } else if (newServiceId == null || newServiceId.isEmpty()) {
                LogUtil.e("IDMServer", "newServiceId is null", new Object[0]);
            } else {
                IDMService iDMService = (IDMService) IDMServer.this.b.remove(oldServiceId);
                if (iDMService == null) {
                    LogUtil.e("IDMServer", "onServiceChanged Could not find service, oldServiceId = [" + oldServiceId + "]", new Object[0]);
                    return;
                }
                iDMService.setUUID(newServiceId);
                IDMServer.this.b.put(newServiceId, iDMService);
            }
        }
    };
    IDMService.IDMEventCallback a = new IDMService.IDMEventCallback() { // from class: com.xiaomi.idm.api.IDMServer.2
        @Override // com.xiaomi.idm.api.IDMService.IDMEventCallback
        public void onEvent(IDMService iDMService, IDMServiceProto.IDMEvent iDMEvent) {
            LogUtil.d("IDMServer", "onEvent event = " + iDMEvent, new Object[0]);
            if (IDMServer.this.serviceAvailable()) {
                try {
                    IDMServer.this.mService.event(IDMServer.this.getClientId(), IPCParam.Event.newBuilder().setIdmService(iDMService.getIDMServiceProto()).setIdmEvent(iDMEvent).build().toByteArray());
                } catch (RemoteException e) {
                    LogUtil.e("IDMServer", e.getMessage(), e);
                }
            } else {
                LogUtil.e("IDMServer", "onEvent, but service unavailable", new Object[0]);
            }
        }
    };
    private Map<String, IDMService> b = new ConcurrentHashMap();

    public IDMServer(Context context, String str, IDMProcessCallback iDMProcessCallback) {
        super(context, str, iDMProcessCallback);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.idm.api.IDMBinderBase
    public void onServiceConnected() {
        try {
            this.mService.registerProc(getClientId(), IPCParam.RegisterIDMServer.newBuilder().setSdkVersion(BuildConfig.VERSION_CODE).build().toByteArray(), this.d);
        } catch (RemoteException e) {
            LogUtil.e("IDMServer", e.getMessage(), e);
        }
    }

    public int registerService(@NonNull IDMService iDMService) {
        return registerService(new RSParamBuilder(iDMService));
    }

    public int registerService(@NonNull RSParamBuilder rSParamBuilder) {
        IDMService iDMService = rSParamBuilder.f;
        String str = rSParamBuilder.a;
        String str2 = rSParamBuilder.b;
        StringBuilder sb = new StringBuilder();
        sb.append("registerService -> service: \n");
        sb.append(iDMService.getIDMServiceProto());
        sb.append("\nisPersistent? :");
        sb.append((str == null || str2 == null) ? false : true);
        LogUtil.d("IDMServer", sb.toString(), new Object[0]);
        String str3 = "";
        if (serviceAvailable()) {
            IPCParam.StartAdvertisingIDM.Builder newBuilder = IPCParam.StartAdvertisingIDM.newBuilder();
            newBuilder.setIdmService(iDMService.getIDMServiceProto()).setDiscType(rSParamBuilder.c).setCommType(rSParamBuilder.d).setServiceSecurityType(rSParamBuilder.e);
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                newBuilder.setIntentStr(str);
                newBuilder.setIntentType(str2);
            }
            try {
                str3 = this.mService.startAdvertisingIDM(getClientId(), newBuilder.build().toByteArray());
                LogUtil.d("IDMServer", "new UUID return, set new UUID: " + str3, new Object[0]);
                if (!TextUtils.isEmpty(str3)) {
                    iDMService.setUUID(str3);
                    this.b.put(iDMService.getUUID(), iDMService);
                }
            } catch (RemoteException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
            }
        }
        return str3.isEmpty() ? -1 : 0;
    }

    public int unregisterService(@NonNull IDMService iDMService) {
        if (!serviceAvailable()) {
            return -1;
        }
        try {
            return this.mService.stopAdvertisingIDM(getClientId(), IPCParam.StopAdvertisingIDM.newBuilder().setIdmService(iDMService.getIDMServiceProto()).build().toByteArray());
        } catch (RemoteException e) {
            LogUtil.e("IDMServer", e.getMessage(), e);
            return -1;
        }
    }

    /* loaded from: classes3.dex */
    public static final class RSParamBuilder {
        String a = null;
        String b = null;
        int c = 3;
        int d = 8;
        int e = 0;
        IDMService f;

        public RSParamBuilder(@NonNull IDMService iDMService) {
            this.f = iDMService;
        }

        public RSParamBuilder commType(int i) {
            this.d = i;
            return this;
        }

        public RSParamBuilder discType(int i) {
            this.c = i;
            return this;
        }

        public RSParamBuilder intentType(String str) {
            this.b = str;
            return this;
        }

        public RSParamBuilder intentStr(String str) {
            this.a = str;
            return this;
        }

        public RSParamBuilder serviceSecurityType(int i) {
            this.e = i;
            return this;
        }
    }

    public void acceptConnection(@NonNull String str, @NonNull String str2) {
        LogUtil.d("IDMServer", "accepting connection...", new Object[0]);
        LogUtil.i("IDMServer", "IDM_TIME_USAGE: acceptConnection[%s]: Time usage = [%d]", str, Long.valueOf(System.currentTimeMillis() - this.c));
        this.c = 0L;
        a(ResponseCode.ConnectCode.CONN_STAT_CONNECTED.getCode(), str2, str, -1);
    }

    public void rejectConnection(@NonNull String str, @NonNull String str2) {
        LogUtil.d("IDMServer", "rejecting connection...", new Object[0]);
        a(ResponseCode.ConnectCode.CONN_STAT_LOCAL_REJECTED.getCode(), str2, str, -1);
    }

    public void disconnectClient(@NonNull String str, @NonNull String str2) {
        disconnectClient(str, str2, 0);
    }

    public void disconnectClient(@NonNull String str, @NonNull String str2, int i) {
        LogUtil.d("IDMServer", "disconnecting connection...", new Object[0]);
        a(ResponseCode.ConnectCode.CONN_STAT_DISCONNECT.getCode(), str2, str, i);
    }

    public void acceptInvitation(String str, String str2) {
        LogUtil.d("IDMServer", "acceptInvitation, serviceId = [" + str + "], inviteStr = [" + str2 + "]", new Object[0]);
        this.c = System.currentTimeMillis();
        LogUtil.i("IDMServer", "IDM_TIME_USAGE: acceptInvitation[%s]: Timer start", str);
        if (serviceAvailable()) {
            try {
                this.mService.acceptInvitation(getClientId(), IPCParam.AcceptInvitation.newBuilder().setServiceId(str).setInviteStr(str2).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
            }
        }
    }

    @Override // com.xiaomi.idm.api.IDMBinderBase
    protected void doDestroy() {
        if (serviceAvailable()) {
            try {
                this.mService.unregisterProc(getClientId());
            } catch (RemoteException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
            }
        }
    }

    private void a(int i, String str, String str2, int i2) {
        LogUtil.d("IDMServer", "sendServiceStatusResponse", new Object[0]);
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            LogUtil.d("IDMServer", "clientId and serviceId are required. ClientId = " + str + " serviceId = " + str2, new Object[0]);
            i = ResponseCode.ConnectCode.CONN_STAT_ERR_ILLEGAL_PARAMETER.getCode();
        } else if (this.b.get(str2) == null) {
            LogUtil.d("IDMServer", "Service is not registered : ServiceId = " + str2, new Object[0]);
            i = ResponseCode.ConnectCode.CONN_STAT_ERR_SERVICE_NOT_FOUND.getCode();
        }
        IDMServiceProto.IDMConnectServiceResponse.Builder serviceId = IDMServiceProto.IDMConnectServiceResponse.newBuilder().setStatus(i).setClientId(str).setServiceId(str2);
        if (i2 != -1) {
            serviceId.setConnLevel(i2);
        }
        if (serviceAvailable()) {
            try {
                this.mService.connectServiceStatusResponse(getClientId(), IPCParam.ConnectServiceResponse.newBuilder().setIdmConnectServiceResponse(serviceId.build()).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMServer", e.getMessage(), e);
            }
        } else {
            LogUtil.e("IDMServer", "sendServiceStatusResponse, service unavailable", new Object[0]);
        }
    }

    /* renamed from: com.xiaomi.idm.api.IDMServer$3  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[ResponseCode.ConnectCode.values().length];

        static {
            try {
                a[ResponseCode.ConnectCode.CONN_STAT_TO_BE_CONFIRM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }
}
