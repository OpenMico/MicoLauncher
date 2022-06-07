package com.xiaomi.idm.service.iot;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.iot.proto.IPCameraServiceProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.idm.utils.ResponseHelper;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class IPCameraService extends IDMService.BuiltinService {
    public static final String SERVICE_TYPE = "urn:aiot-spec-v3:service:ip-camera:00000001:1";

    public abstract IPCameraServiceProto.IPCResponse getIpcSkeletonInfo(String str, String str2) throws RmiException;

    protected IPCameraService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public IPCameraService(String str, String str2) {
        super(str, str2, "urn:aiot-spec-v3:service:ip-camera:00000001:1");
    }

    public IPCameraService(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    @Override // com.xiaomi.idm.api.IDMService
    public IDMServiceProto.IDMResponse request(IDMServiceProto.IDMRequest iDMRequest) {
        Actions.GetIpcSkeletonInfo getIpcSkeletonInfo;
        int aid = iDMRequest.getAid();
        byte[] byteArray = iDMRequest.getRequest().toByteArray();
        if (byteArray == null) {
            return null;
        }
        if (aid != 1) {
            getIpcSkeletonInfo = null;
        } else {
            try {
                getIpcSkeletonInfo = new Actions.GetIpcSkeletonInfo(this, byteArray);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("IPCameraService", e.getMessage(), e);
                getIpcSkeletonInfo = null;
            }
        }
        if (getIpcSkeletonInfo != null) {
            return ResponseHelper.buildResponse(iDMRequest, getIpcSkeletonInfo.invoke());
        }
        return ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getCode(), ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getMsg() + " for uuid: " + getUUID() + " aid: " + aid, iDMRequest, null);
    }

    /* loaded from: classes3.dex */
    public static class Stub extends IPCameraService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        public CallFuture<IPCameraServiceProto.IPCResponse> getIpcSkeletonInfoAsync(String str, String str2) {
            return this.a.request(new Actions.GetIpcSkeletonInfo(this, str, str2));
        }

        @Override // com.xiaomi.idm.service.iot.IPCameraService
        public IPCameraServiceProto.IPCResponse getIpcSkeletonInfo(String str, String str2) throws RmiException {
            try {
                return getIpcSkeletonInfoAsync(str, str2).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public void subscribeSkeletonEvent(Events.SkeletonEvent.Callback callback) {
            this.a.setEventCallback(new Events.SkeletonEvent(this, callback), true);
        }

        public void unsubscribeSkeletonEvent(Events.SkeletonEvent.Callback callback) {
            this.a.setEventCallback(new Events.SkeletonEvent(this, callback), false);
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends IPCameraService {
        private boolean a;

        public Skeleton(String str, String str2) {
            super(str, str2, "urn:aiot-spec-v3:service:ip-camera:00000001:1");
        }

        public Skeleton(String str, String str2, String str3) {
            super(str, str2, str3);
        }

        @Override // com.xiaomi.idm.api.IDMService, com.xiaomi.idm.api.IIDMService
        public int enableEvent(int i, boolean z) {
            if (i == -1) {
                this.mEventEnable |= z;
                this.a = z;
            } else if (i != 1) {
                return -1;
            } else {
                this.a = z;
                this.mEventEnable |= z;
            }
            return 0;
        }

        public void notifySkeletonEvent(byte[] bArr) {
            if (this.a) {
                notifyEvent(1, IPCameraServiceProto.SkeletonEvent.newBuilder().setBytes(ByteString.copyFrom(bArr)).build().toByteArray());
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Actions {
        public static final int AID_GETIPCSKELETONINFO = 1;

        /* loaded from: classes3.dex */
        public static class GetIpcSkeletonInfo extends IDMService.Action<IPCameraServiceProto.IPCResponse> {
            IPCameraServiceProto.GetIpcSkeletonInfo a;

            GetIpcSkeletonInfo(IPCameraService iPCameraService, String str, String str2) {
                super(1, iPCameraService);
                this.a = IPCameraServiceProto.GetIpcSkeletonInfo.newBuilder().setAid(1).setAppId(str).setServiceToken(str2).build();
            }

            GetIpcSkeletonInfo(IPCameraService iPCameraService, byte[] bArr) throws InvalidProtocolBufferException {
                super(1, iPCameraService);
                this.a = IPCameraServiceProto.GetIpcSkeletonInfo.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IPCameraServiceProto.IPCResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IPCameraServiceProto.IPCResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IPCameraServiceProto.IPCResponse iPCResponse;
                try {
                    iPCResponse = ((IPCameraService) this.service).getIpcSkeletonInfo(this.a.getAppId(), this.a.getServiceToken());
                } catch (RmiException e) {
                    LogUtil.e("IPCameraService", e.getMessage(), e);
                    iPCResponse = null;
                }
                if (iPCResponse == null) {
                    return null;
                }
                return iPCResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IPCameraServiceProto.GetIpcSkeletonInfo getIpcSkeletonInfo = this.a;
                if (getIpcSkeletonInfo == null) {
                    return null;
                }
                return getIpcSkeletonInfo.toByteArray();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Events {
        public static final int EID_SKELETONEVENT = 1;

        /* loaded from: classes3.dex */
        public static class SkeletonEvent extends IDMService.Event {
            Callback a;

            /* loaded from: classes3.dex */
            public interface Callback {
                void onSkeletonEvent(byte[] bArr);
            }

            SkeletonEvent(IDMService iDMService, Callback callback) {
                super(iDMService, 1);
                this.a = callback;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.idm.api.IDMService.Event
            public void onEvent(byte[] bArr) {
                try {
                    this.a.onSkeletonEvent(IPCameraServiceProto.SkeletonEvent.parseFrom(bArr).getBytes().toByteArray());
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("IPCameraService", e.getMessage(), e);
                }
            }
        }
    }
}
