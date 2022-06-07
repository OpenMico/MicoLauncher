package com.xiaomi.idm.service.handoff;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.handoff.proto.HandoffServiceProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.idm.utils.ResponseHelper;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class HandoffService extends IDMService.BuiltinService {
    public abstract HandoffServiceProto.AllHandoffData requestHandoffData() throws RmiException;

    protected HandoffService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public HandoffService(String str, String str2) {
        super(str, str2, IDMService.TYPE_HANDOFF);
    }

    public HandoffService(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    @Override // com.xiaomi.idm.api.IDMService
    public IDMServiceProto.IDMResponse request(IDMServiceProto.IDMRequest iDMRequest) {
        Actions.RequestHandoffData requestHandoffData;
        int aid = iDMRequest.getAid();
        byte[] byteArray = iDMRequest.getRequest().toByteArray();
        if (byteArray == null) {
            return null;
        }
        if (aid != 1) {
            requestHandoffData = null;
        } else {
            try {
                requestHandoffData = new Actions.RequestHandoffData(this, byteArray);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e("HandoffService", e.getMessage(), e);
                requestHandoffData = null;
            }
        }
        if (requestHandoffData != null) {
            return ResponseHelper.buildResponse(iDMRequest, requestHandoffData.invoke());
        }
        return ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getCode(), ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getMsg() + " for uuid: " + getUUID() + " aid: " + aid, iDMRequest, null);
    }

    /* loaded from: classes3.dex */
    public static class Stub extends HandoffService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        public CallFuture<HandoffServiceProto.AllHandoffData> requestHandoffDataAsync() {
            return this.a.request(new Actions.RequestHandoffData(this));
        }

        @Override // com.xiaomi.idm.service.handoff.HandoffService
        public HandoffServiceProto.AllHandoffData requestHandoffData() throws RmiException {
            try {
                return requestHandoffDataAsync().get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public void subscribeHandoffEvent(Events.HandoffEvent.Callback callback) {
            this.a.setEventCallback(new Events.HandoffEvent(this, callback), true);
        }

        public void unsubscribeHandoffEvent(Events.HandoffEvent.Callback callback) {
            this.a.setEventCallback(new Events.HandoffEvent(this, callback), false);
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends HandoffService {
        private boolean a;

        public Skeleton(String str, String str2) {
            super(str, str2, IDMService.TYPE_HANDOFF);
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

        public void notifyHandoffEvent(String str, String str2, String str3, byte[] bArr) {
            if (this.a) {
                notifyEvent(1, HandoffServiceProto.HandoffEvent.newBuilder().setIdHash(str).setAppKey(str2).setKey(str3).setValue(ByteString.copyFrom(bArr)).build().toByteArray());
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Actions {
        public static final int AID_REQUESTHANDOFFDATA = 1;

        /* loaded from: classes3.dex */
        public static class RequestHandoffData extends IDMService.Action<HandoffServiceProto.AllHandoffData> {
            HandoffServiceProto.RequestHandoffData a;

            RequestHandoffData(HandoffService handoffService) {
                super(1, handoffService);
                this.a = HandoffServiceProto.RequestHandoffData.newBuilder().setAid(1).build();
            }

            RequestHandoffData(HandoffService handoffService, byte[] bArr) throws InvalidProtocolBufferException {
                super(1, handoffService);
                this.a = HandoffServiceProto.RequestHandoffData.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public HandoffServiceProto.AllHandoffData parseResponse(byte[] bArr) throws RmiException {
                try {
                    return HandoffServiceProto.AllHandoffData.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                HandoffServiceProto.AllHandoffData allHandoffData;
                try {
                    allHandoffData = ((HandoffService) this.service).requestHandoffData();
                } catch (RmiException e) {
                    LogUtil.e("HandoffService", e.getMessage(), e);
                    allHandoffData = null;
                }
                if (allHandoffData == null) {
                    return null;
                }
                return allHandoffData.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                HandoffServiceProto.RequestHandoffData requestHandoffData = this.a;
                if (requestHandoffData == null) {
                    return null;
                }
                return requestHandoffData.toByteArray();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Events {
        public static final int EID_HANDOFFEVENT = 1;

        /* loaded from: classes3.dex */
        public static class HandoffEvent extends IDMService.Event {
            Callback a;

            /* loaded from: classes3.dex */
            public interface Callback {
                void onHandoffEvent(String str, String str2, String str3, byte[] bArr);
            }

            HandoffEvent(IDMService iDMService, Callback callback) {
                super(iDMService, 1);
                this.a = callback;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.idm.api.IDMService.Event
            public void onEvent(byte[] bArr) {
                try {
                    HandoffServiceProto.HandoffEvent parseFrom = HandoffServiceProto.HandoffEvent.parseFrom(bArr);
                    this.a.onHandoffEvent(parseFrom.getIdHash(), parseFrom.getAppKey(), parseFrom.getKey(), parseFrom.getValue().toByteArray());
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("HandoffService", e.getMessage(), e);
                }
            }
        }
    }
}
