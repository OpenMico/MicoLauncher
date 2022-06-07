package com.xiaomi.idm.api;

import android.content.Context;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.conn.ConnParam;
import com.xiaomi.idm.api.conn.EndPoint;
import com.xiaomi.idm.api.identify.IdentifyParam;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.mi_connect_sdk.BuildConfig;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mi_connect_service.IIDMClientCallback;
import com.xiaomi.mi_connect_service.proto.IPCParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class IDMClient extends IDM {
    private IDMClientCallback a;
    private IDMServiceFactoryBase b;
    private int c;
    private ConcurrentHashMap<String, Call> d;
    private ConcurrentHashMap<String, IDMService.Event> e;
    private ConcurrentHashMap<String, IDMService> f;
    private IIDMClientCallback g;

    public IDMClient(Context context, String str, IDMProcessCallback iDMProcessCallback) {
        this(context, str, new IDMServiceFactoryBase(), iDMProcessCallback);
    }

    public IDMClient(Context context, String str, IDMServiceFactoryBase iDMServiceFactoryBase, IDMProcessCallback iDMProcessCallback) {
        super(context, str, iDMProcessCallback);
        this.g = new IIDMClientCallback.Stub() { // from class: com.xiaomi.idm.api.IDMClient.1
            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onServiceFound(byte[] bArr) {
                IPCParam.OnServiceFound onServiceFound;
                LogUtil.d("IDMClient", "onServiceFound", new Object[0]);
                if (IDMClient.this.a != null && IDMClient.this.b != null) {
                    IDMServiceProto.IDMService iDMService = null;
                    try {
                        onServiceFound = IPCParam.OnServiceFound.parseFrom(bArr);
                    } catch (InvalidProtocolBufferException e) {
                        LogUtil.e("IDMClient", e.getMessage(), e);
                        onServiceFound = null;
                    }
                    if (onServiceFound != null) {
                        iDMService = onServiceFound.getIdmService();
                    }
                    if (iDMService != null) {
                        IDMService createIDMService = IDMClient.this.f.containsKey(iDMService.getUuid()) ? (IDMService) IDMClient.this.f.get(iDMService.getUuid()) : IDMClient.this.b.createIDMService(IDMClient.this, iDMService);
                        if (createIDMService != null) {
                            createIDMService.update(iDMService);
                            IDMClient.this.f.put(createIDMService.getUUID(), createIDMService);
                            IDMClient.this.a.onServiceFound(createIDMService);
                        }
                    }
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onServiceLost(byte[] bArr) {
                IPCParam.OnServiceLost onServiceLost;
                LogUtil.e("IDMClient", "onServiceLost", new Object[0]);
                if (bArr == null) {
                    LogUtil.e("IDMClient", "onServiceLost param is null", new Object[0]);
                    return;
                }
                String str2 = "";
                IDMService iDMService = null;
                try {
                    onServiceLost = IPCParam.OnServiceLost.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    onServiceLost = null;
                }
                if (onServiceLost != null) {
                    str2 = onServiceLost.getServiceId();
                }
                if (!str2.isEmpty()) {
                    iDMService = (IDMService) IDMClient.this.f.get(str2);
                }
                if (iDMService != null) {
                    IDMClient.this.f.remove(iDMService.getUUID());
                    IDMClient.this.a.onServiceLost(iDMService);
                    IDMClient.this.a(iDMService.getUUID());
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onResponse(byte[] bArr) {
                IPCParam.OnResponse onResponse;
                Call call;
                LogUtil.e("IDMClient", "onResponse", new Object[0]);
                if (bArr == null) {
                    LogUtil.e("IDMClient", "onResponse param is null", new Object[0]);
                    return;
                }
                IDMServiceProto.IDMResponse iDMResponse = null;
                try {
                    onResponse = IPCParam.OnResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    onResponse = null;
                }
                if (onResponse != null) {
                    iDMResponse = onResponse.getIdmResponse();
                }
                if (iDMResponse != null) {
                    String requestId = iDMResponse.getRequestId();
                    synchronized (IDMClient.this.d) {
                        call = (Call) IDMClient.this.d.get(requestId);
                    }
                    if (call != null) {
                        if (iDMResponse.getCode() == ResponseCode.RequestCode.REQUEST_SUCCEED.getCode()) {
                            try {
                                call.c.setDone(call.a.parseResponse(iDMResponse.getResponse().toByteArray()));
                            } catch (RmiException e2) {
                                LogUtil.e("IDMClient", e2.getMessage(), e2);
                                call.c.setFailed(e2.getResponseCode(), e2.getMessage());
                            }
                        } else {
                            call.c.setFailed(iDMResponse.getCode(), iDMResponse.getMsg());
                        }
                    }
                } else {
                    LogUtil.e("IDMClient", "onResponse responseParam is null", new Object[0]);
                }
                IDMClient.this.b();
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onEvent(byte[] bArr) {
                IPCParam.OnEvent onEvent;
                try {
                    onEvent = IPCParam.OnEvent.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    onEvent = null;
                }
                if (onEvent == null) {
                    LogUtil.e("IDMClient", "onEvent eventParam is null", new Object[0]);
                    return;
                }
                IDMServiceProto.IDMEvent idmEvent = onEvent.getIdmEvent();
                if (idmEvent != null) {
                    int eid = idmEvent.getEid();
                    IDMService.Event event = (IDMService.Event) IDMClient.this.e.get(IDMClient.this.generateEventKey(idmEvent.getUuid(), eid));
                    if (event != null) {
                        event.onEvent(idmEvent.getEvent().toByteArray());
                    } else {
                        LogUtil.e("IDMClient", "Event Not Found", new Object[0]);
                    }
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onServiceConnectStatus(byte[] bArr) {
                IPCParam.OnServiceConnectStatus onServiceConnectStatus;
                LogUtil.d("IDMClient", "onServiceConnectStatus", new Object[0]);
                try {
                    onServiceConnectStatus = IPCParam.OnServiceConnectStatus.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    onServiceConnectStatus = null;
                }
                if (onServiceConnectStatus == null) {
                    LogUtil.e("IDMClient", "onServiceConnectStatus param is null", new Object[0]);
                    return;
                }
                int status = onServiceConnectStatus.getStatus();
                LogUtil.d("IDMClient", "onServiceConnectStatus : status = " + status, new Object[0]);
                if (!IDMClient.this.a.onServiceConnectStatus(status, onServiceConnectStatus.getServiceId(), EndPoint.buildFromProto(onServiceConnectStatus.getEndpoint()), ConnParam.buildFromProto(onServiceConnectStatus.getConnParam())) && status == ResponseCode.ConnectCode.CONN_STAT_TO_BE_CONFIRM.getCode()) {
                    IDMClient.this.acceptConnection(onServiceConnectStatus.getServiceId());
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onInviteConnection(byte[] bArr) {
                IPCParam.OnInviteConnection onInviteConnection;
                LogUtil.d("IDMClient", "onInviteConnection", new Object[0]);
                try {
                    onInviteConnection = IPCParam.OnInviteConnection.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    onInviteConnection = null;
                }
                if (onInviteConnection == null) {
                    LogUtil.e("IDMClient", "onInviteConnection param is null", new Object[0]);
                    return;
                }
                IDMClient.this.a.onInviteConnection(onInviteConnection.getCode(), onInviteConnection.getInviteStr());
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onInvitationAccepted(byte[] bArr) {
                IPCParam.OnInvitationAccepted onInvitationAccepted;
                LogUtil.d("IDMClient", "onInvitationAccepted", new Object[0]);
                try {
                    onInvitationAccepted = IPCParam.OnInvitationAccepted.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    onInvitationAccepted = null;
                }
                if (onInvitationAccepted == null) {
                    LogUtil.e("IDMClient", "onInviteConnection param is null", new Object[0]);
                    return;
                }
                IDMServiceProto.IDMService idmService = onInvitationAccepted.getIdmService();
                if (idmService == null) {
                    LogUtil.e("IDMClient", "service param is null", new Object[0]);
                    return;
                }
                IDMService createIDMService = IDMClient.this.f.containsKey(idmService.getUuid()) ? (IDMService) IDMClient.this.f.get(idmService.getUuid()) : IDMClient.this.b.createIDMService(IDMClient.this, idmService);
                if (createIDMService != null) {
                    IDMClient.this.f.put(createIDMService.getUUID(), createIDMService);
                    IDMClient.this.a.onInvitationAccepted(createIDMService);
                }
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onDiscoveryResult(byte[] bArr) {
                IPCParam.OnDiscoveryResult onDiscoveryResult;
                LogUtil.d("IDMClient", "onDiscoveryResult", new Object[0]);
                try {
                    onDiscoveryResult = IPCParam.OnDiscoveryResult.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    onDiscoveryResult = null;
                }
                if (onDiscoveryResult == null) {
                    LogUtil.e("IDMClient", "onDiscoveryResult param is null", new Object[0]);
                    return;
                }
                IDMClient.this.a.onDiscoveryResult(onDiscoveryResult.getStatus());
            }

            @Override // com.xiaomi.mi_connect_service.IIDMClientCallback
            public void onAccountChanged(byte[] bArr) {
                IPCParam.ClientOnAccountChanged clientOnAccountChanged;
                LogUtil.d("IDMClient", "onAccountChanged", new Object[0]);
                try {
                    clientOnAccountChanged = IPCParam.ClientOnAccountChanged.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IDMClient", e.getMessage(), e);
                    clientOnAccountChanged = null;
                }
                if (clientOnAccountChanged == null) {
                    LogUtil.e("IDMClient", "onAccountChanged: param is null", new Object[0]);
                    return;
                }
                String newIdHash = clientOnAccountChanged.getNewIdHash();
                if (newIdHash == null || newIdHash.isEmpty()) {
                    LogUtil.e("IDMClient", "newIdHash is empty!", new Object[0]);
                    return;
                }
                IDMServiceProto.OnAccountChangeResult.SubChangeType subChangeType = clientOnAccountChanged.getSubChangeType();
                if (subChangeType == null) {
                    LogUtil.e("IDMClient", "subChangeType is null", new Object[0]);
                } else {
                    IDMClient.this.a.onAccountChanged(newIdHash, subChangeType.name());
                }
            }
        };
        this.b = iDMServiceFactoryBase;
        this.c = 0;
        this.d = new ConcurrentHashMap<>();
        this.e = new ConcurrentHashMap<>();
        this.f = new ConcurrentHashMap<>();
    }

    private String a() {
        String valueOf;
        synchronized (IDM.class) {
            int i = this.c;
            this.c = i + 1;
            valueOf = String.valueOf(i);
        }
        return valueOf;
    }

    public int registerIDM(@NonNull IDMClientCallback iDMClientCallback, IdentifyParam identifyParam) {
        if (!serviceAvailable()) {
            return -1;
        }
        try {
            this.a = iDMClientCallback;
            IPCParam.RegisterIDMClient.Builder newBuilder = IPCParam.RegisterIDMClient.newBuilder();
            newBuilder.setSdkVersion(BuildConfig.VERSION_CODE);
            if (identifyParam != null) {
                newBuilder.setIdentify(identifyParam.toProto());
            }
            return this.mService.registerIDMClient(getClientId(), newBuilder.build().toByteArray(), this.g).equals(getClientId()) ? 0 : -1;
        } catch (RemoteException e) {
            LogUtil.e("IDMClient", e.toString(), e);
            return -1;
        }
    }

    public IDMService connectService(@NonNull IDMServiceProto.IDMService iDMService) {
        return connectService(new CSParamBuilder(iDMService));
    }

    public IDMService connectService(@NonNull CSParamBuilder cSParamBuilder) {
        LogUtil.d("IDMClient", "connectService serviceId = " + cSParamBuilder.f.getUuid(), new Object[0]);
        if (!serviceAvailable()) {
            return null;
        }
        try {
            this.mService.connectService(getClientId(), IPCParam.ConnectService.newBuilder().setIdmService(cSParamBuilder.f).setCommType(cSParamBuilder.a).setCommDataType(cSParamBuilder.b).setConnLevel(cSParamBuilder.c).setVerifySameAccount(cSParamBuilder.d).setPrivateData(ByteString.copyFrom(cSParamBuilder.e)).build().toByteArray());
        } catch (RemoteException e) {
            LogUtil.e("IDMClient", e.getMessage(), e);
        }
        IDMService iDMService = this.f.get(cSParamBuilder.f.getUuid());
        return iDMService != null ? iDMService : this.b.createIDMService(this, cSParamBuilder.f);
    }

    /* loaded from: classes3.dex */
    public static final class CSParamBuilder {
        int a;
        int b;
        int c;
        boolean d;
        byte[] e;
        IDMServiceProto.IDMService f;

        private CSParamBuilder() {
            this.a = 8;
            this.b = 4;
            this.c = 0;
            this.d = false;
            this.e = new byte[0];
        }

        public CSParamBuilder(@NonNull IDMServiceProto.IDMService iDMService) {
            this();
            this.f = iDMService;
        }

        public CSParamBuilder commType(int i) {
            this.a = i;
            return this;
        }

        public CSParamBuilder commDataType(int i) {
            this.b = i;
            return this;
        }

        public CSParamBuilder connLevel(int i) {
            this.c = i;
            return this;
        }

        public CSParamBuilder verifySameAccount(boolean z) {
            this.d = z;
            return this;
        }

        public CSParamBuilder privateData(@NonNull byte[] bArr) {
            this.e = bArr;
            return this;
        }
    }

    public void disconnectService(@NonNull String str) {
        disconnectService(str, 0);
    }

    public void disconnectService(@NonNull String str, int i) {
        LogUtil.d("IDMClient", "disconnectService serviceId = " + str, new Object[0]);
        if (serviceAvailable()) {
            try {
                this.mService.disconnectService(getClientId(), IPCParam.DisconnectService.newBuilder().setServiceId(str).setConnLevel(i).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.getMessage(), e);
            }
        }
    }

    public void acceptConnection(@NonNull String str) {
        LogUtil.d("IDMClient", "acceptConnection serviceId = " + str, new Object[0]);
        if (serviceAvailable()) {
            try {
                this.mService.clientAcceptConnection(getClientId(), IPCParam.ClientAcceptConnection.newBuilder().setServiceId(str).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.getMessage(), e);
            }
        }
    }

    public void rejectConnection(@NonNull String str) {
        LogUtil.d("IDMClient", "rejectConnection serviceId = " + str, new Object[0]);
        if (serviceAvailable()) {
            try {
                this.mService.clientRejectConnection(getClientId(), IPCParam.ClientRejectConnection.newBuilder().setServiceId(str).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.getMessage(), e);
            }
        }
    }

    public void inviteConnection(String str) {
        LogUtil.d("IDMClient", "invite Connection, service type = [" + str + "]", new Object[0]);
        if (serviceAvailable()) {
            try {
                this.mService.inviteConnection(getClientId(), IPCParam.InviteConnection.newBuilder().setServiceType(str).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.getMessage(), e);
            }
        }
    }

    public void abortInvitation(String str) {
        LogUtil.d("IDMClient", "abort InvitationCode, service type = [" + str + "]", new Object[0]);
        if (serviceAvailable()) {
            try {
                this.mService.abortInvitation(getClientId(), IPCParam.AbortInvitation.newBuilder().setServiceType(str).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.getMessage(), e);
            }
        }
    }

    @Override // com.xiaomi.idm.api.IDMBinderBase
    protected void doDestroy() {
        if (serviceAvailable()) {
            try {
                this.mService.unregisterIDMClient(getClientId());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.toString(), e);
            }
            for (Call call : this.d.values()) {
                call.c.setFailed(ResponseCode.RequestCode.ERR_CLIENT_DESTROYED.getCode(), ResponseCode.RequestCode.ERR_CLIENT_DESTROYED.getMsg());
            }
            return;
        }
        LogUtil.e("IDMClient", "destroy called, but service unavailable", new Object[0]);
    }

    /* loaded from: classes3.dex */
    public static class ServiceFilter {
        private List<String> a = new ArrayList();
        private List<String> b = new ArrayList();

        public ServiceFilter addType(String str) {
            this.a.add(str);
            return this;
        }

        public ServiceFilter addUUID(String str) {
            this.b.add(str);
            return this;
        }
    }

    public void startDiscovery(@NonNull ServiceFilter serviceFilter, int i) {
        startDiscovery(new SDParamBuilder(serviceFilter).discType(i));
    }

    public void startDiscovery(@NonNull ServiceFilter serviceFilter) {
        startDiscovery(new SDParamBuilder(serviceFilter));
    }

    public void startDiscovery(@NonNull SDParamBuilder sDParamBuilder) {
        if (serviceAvailable()) {
            try {
                this.mService.startDiscoveryIDM(getClientId(), IPCParam.StartDiscovery.newBuilder().addAllServiceTypes(sDParamBuilder.c.a).addAllServiceUuids(sDParamBuilder.c.b).setDiscType(sDParamBuilder.a).setServiceSecurityType(sDParamBuilder.b).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.toString(), e);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static final class SDParamBuilder {
        int a = 3;
        int b = 0;
        ServiceFilter c;

        public SDParamBuilder(@NonNull ServiceFilter serviceFilter) {
            this.c = serviceFilter;
        }

        public SDParamBuilder discType(int i) {
            this.a = i;
            return this;
        }

        public SDParamBuilder serviceSecurityType(int i) {
            this.b = i;
            return this;
        }
    }

    public void stopDiscovery() {
        if (serviceAvailable()) {
            try {
                this.mService.stopDiscoveryIDM(getClientId(), null);
                this.f.clear();
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.toString(), e);
            }
        }
    }

    private byte[] a(IDMServiceProto.IDMRequest iDMRequest) {
        LogUtil.d("IDMClient", "doRequest", new Object[0]);
        if (!serviceAvailable()) {
            return null;
        }
        try {
            return this.mService.request(getClientId(), IPCParam.Request.newBuilder().setIdmRequest(iDMRequest).build().toByteArray());
        } catch (RemoteException e) {
            LogUtil.e("IDMClient", e.getMessage(), e);
            return null;
        }
    }

    public void b() {
        synchronized (this.d) {
            for (Map.Entry<String, Call> entry : this.d.entrySet()) {
                if (entry.getValue().c.isDone()) {
                    this.d.remove(entry.getKey());
                }
            }
        }
    }

    public <T> CallFuture<T> request(IDMService.Action<T> action) {
        int i;
        LogUtil.d("IDMClient", "request action: " + action.getAid() + StringUtils.SPACE + action.getClass(), new Object[0]);
        IDMServiceProto.IDMRequest build = IDMServiceProto.IDMRequest.newBuilder().setUuid(action.getServiceUUID()).setAid(action.getAid()).setRequestId(a()).setClientId(getClientId()).setRequest(ByteString.copyFrom(action.toBytes())).build();
        String requestId = build.getRequestId();
        Call call = new Call(action, build);
        synchronized (this.d) {
            this.d.put(requestId, call);
        }
        byte[] a = a(build);
        if (a == null) {
            LogUtil.e("IDMClient", "Response bytes null when do request", new Object[0]);
            i = ResponseCode.RequestCode.ERR_RESPONSE_NULL.getCode();
        } else {
            IDMServiceProto.IDMResponse iDMResponse = null;
            try {
                iDMResponse = IDMServiceProto.IDMResponse.parseFrom(a);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("IDMClient", e.getMessage(), e);
            }
            if (iDMResponse == null) {
                LogUtil.e("IDMClient", "Response parse error when do request", new Object[0]);
                i = ResponseCode.RequestCode.ERR_RESPONSE_PARSE.getCode();
            } else {
                i = iDMResponse.getCode();
            }
        }
        if (i < 0) {
            LogUtil.e("IDMClient", "Error when do request responseCode = " + i, new Object[0]);
            call.c.setFailed(i, ResponseCode.RequestCode.getResponseMsg(i));
        }
        b();
        return call.c;
    }

    public String generateEventKey(String str, int i) {
        return "serviceId:" + str + "eid:" + i;
    }

    public int setEventCallback(IDMService.Event event, boolean z) {
        if (z) {
            LogUtil.d("IDMClient", "subscribe event: " + event, new Object[0]);
        } else {
            LogUtil.d("IDMClient", "unsubscribe event: " + event, new Object[0]);
        }
        String uuid = event.getUUID();
        int eid = event.getEid();
        String generateEventKey = generateEventKey(uuid, eid);
        if (z) {
            this.e.put(generateEventKey, event);
        }
        int code = ResponseCode.SetEventCode.EVENT_LOCAL_SERVICE_NOT_AVAILABLE.getCode();
        if (serviceAvailable()) {
            try {
                code = this.mService.setEventCallback(getClientId(), IPCParam.SetEventCallback.newBuilder().setIdmEvent(IDMServiceProto.IDMEvent.newBuilder().setUuid(uuid).setEid(eid).setEnable(z).build()).build().toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDMClient", e.getMessage(), e);
            }
            if (code != ResponseCode.SetEventCode.EVENT_SUCCEED.getCode() || !z) {
                this.e.remove(generateEventKey);
            }
        }
        return code;
    }

    /* loaded from: classes3.dex */
    public static abstract class IDMClientCallback {
        protected abstract boolean onServiceConnectStatus(int i, String str, EndPoint endPoint, ConnParam connParam);

        protected abstract void onServiceFound(IDMService iDMService);

        protected void onServiceLost(IDMService iDMService) {
            LogUtil.d("IDMClient", "onServiceLost, service name = [%s]\nserviceId = [%s]", iDMService.getName(), iDMService.getUUID());
        }

        protected void onInviteConnection(int i, String str) {
            LogUtil.d("IDMClient", "onInviteConnection, code = [%d], inviteStr = [%s]", Integer.valueOf(i), str);
        }

        protected void onInvitationAccepted(IDMService iDMService) {
            LogUtil.d("IDMClient", "onInvitationAccepted, service name = [%s]\nserviceId = [%s]", iDMService.getName(), iDMService.getUUID());
        }

        protected void onDiscoveryResult(int i) {
            LogUtil.d("IDMClient", "onDiscoveryResult, status = [%d]", Integer.valueOf(i));
        }

        protected void onAccountChanged(String str, String str2) {
            LogUtil.d("IDMClient", "onMiIdentityChanged, newIdHash = [%s], subChangeType = [%s]", str, str2);
        }
    }

    public void a(String str) {
        for (Call call : this.d.values()) {
            if (call.b.getUuid().equals(str)) {
                call.c.setFailed(ResponseCode.RequestCode.ERR_SERVICE_LOST.getCode(), ResponseCode.RequestCode.ERR_SERVICE_LOST.getMsg());
            }
        }
        b();
    }

    /* loaded from: classes3.dex */
    public class Call<T> {
        IDMService.Action<T> a;
        IDMServiceProto.IDMRequest b;
        CallFuture<T> c = new CallFuture<>();

        Call(IDMService.Action<T> action, IDMServiceProto.IDMRequest iDMRequest) {
            IDMClient.this = r1;
            this.a = action;
            this.b = iDMRequest;
        }
    }
}
