package com.xiaomi.idm.service.test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.test.localetestservice.proto.ActionsProto;
import com.xiaomi.idm.service.test.localetestservice.proto.DataProto;
import com.xiaomi.idm.service.test.localetestservice.proto.EventsProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.idm.utils.ResponseHelper;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class TestBuiltinService extends IDMService.BuiltinService {
    public static final String SERVICE_TYPE = "TestBuiltinService";

    public abstract DataProto.Response getSomeString(String str) throws RmiException;

    public TestBuiltinService() {
        super(UUID.randomUUID().toString(), TestBuiltinService.class.getName(), SERVICE_TYPE);
    }

    protected TestBuiltinService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    @Override // com.xiaomi.idm.api.IDMService
    public IDMServiceProto.IDMResponse request(IDMServiceProto.IDMRequest iDMRequest) {
        Actions.GetSomeString getSomeString;
        int aid = iDMRequest.getAid();
        byte[] byteArray = iDMRequest.getRequest().toByteArray();
        if (byteArray == null) {
            return null;
        }
        if (aid != 1) {
            getSomeString = null;
        } else {
            try {
                getSomeString = new Actions.GetSomeString(this, byteArray);
            } catch (InvalidProtocolBufferException e) {
                LogUtil.e(SERVICE_TYPE, e.getMessage(), e);
                getSomeString = null;
            }
        }
        if (getSomeString != null) {
            return ResponseHelper.buildResponse(iDMRequest, getSomeString.invoke());
        }
        return ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getCode(), ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getMsg() + " for uuid: " + getUUID() + " aid: " + aid, iDMRequest, null);
    }

    /* loaded from: classes3.dex */
    public static class Stub extends TestBuiltinService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        @Override // com.xiaomi.idm.service.test.TestBuiltinService
        public DataProto.Response getSomeString(String str) throws RmiException {
            try {
                return getSomeStringAsync(str).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<DataProto.Response> getSomeStringAsync(String str) {
            return this.a.request(new Actions.GetSomeString(this, str));
        }

        public void subscribeSomeEvent(Events.SomeEvent.Callback callback) {
            this.a.setEventCallback(new Events.SomeEvent(this, callback), true);
        }

        public void unsubscribeSomeEvent(Events.SomeEvent.Callback callback) {
            this.a.setEventCallback(new Events.SomeEvent(this, callback), false);
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends TestBuiltinService {
        private boolean a;

        @Override // com.xiaomi.idm.api.IDMService, com.xiaomi.idm.api.IIDMService
        public int enableEvent(int i, boolean z) {
            if (i == -1) {
                this.a = z;
            } else if (i != 1) {
                return -1;
            } else {
                this.a = z;
            }
            return 0;
        }

        protected void notifySomeEvent(int i) {
            if (this.a) {
                notifyEvent(1, EventsProto.SomeEvent.newBuilder().setParam(i).build().toByteArray());
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Actions {
        public static final int AID_GETSOMESTRING = 1;

        /* loaded from: classes3.dex */
        public static class GetSomeString extends IDMService.Action<DataProto.Response> {
            ActionsProto.GetSomeString a;

            public GetSomeString(TestBuiltinService testBuiltinService, String str) {
                super(1, testBuiltinService);
                this.a = ActionsProto.GetSomeString.newBuilder().setAid(getAid()).setParam(str).build();
            }

            public GetSomeString(TestBuiltinService testBuiltinService, byte[] bArr) throws InvalidProtocolBufferException {
                super(1, testBuiltinService);
                this.a = ActionsProto.GetSomeString.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public DataProto.Response parseResponse(byte[] bArr) throws RmiException {
                try {
                    return DataProto.Response.parseFrom(bArr);
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("GetSomeString", e.getMessage(), e);
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                DataProto.Response response;
                try {
                    response = ((TestBuiltinService) this.service).getSomeString(this.a.getParam());
                } catch (RmiException e) {
                    LogUtil.e("GetSomeString", e.getMessage(), e);
                    response = null;
                }
                if (response == null) {
                    return null;
                }
                return response.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                ActionsProto.GetSomeString getSomeString = this.a;
                if (getSomeString == null) {
                    return null;
                }
                return getSomeString.toByteArray();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Events {
        public static final int EID_SOMEEVENT = 1;

        /* loaded from: classes3.dex */
        public static class SomeEvent extends IDMService.Event {
            Callback a;

            /* loaded from: classes3.dex */
            public interface Callback {
                void onSome(int i);
            }

            SomeEvent(IDMService iDMService, Callback callback) {
                super(iDMService, 1);
                this.a = callback;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.idm.api.IDMService.Event
            public void onEvent(byte[] bArr) {
                try {
                    this.a.onSome(EventsProto.SomeEvent.parseFrom(bArr).getParam());
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e(TestBuiltinService.SERVICE_TYPE, e.getMessage(), e);
                }
            }
        }
    }
}
