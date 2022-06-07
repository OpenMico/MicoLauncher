package com.xiaomi.idm.service.iot;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.api.proto.RMIBasicDataType;
import com.xiaomi.idm.service.iot.proto.TestServiceProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.idm.utils.ResponseHelper;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class TestService extends IDMService.BuiltinService {
    public static final String SERVICE_TYPE = "TestService";
    public static final String SERVICE_UUID = "10000000-0000-1000-8000-00805f9b34fc";

    public abstract int getAPlusB(int i, int i2) throws RmiException;

    public abstract TestServiceProto.GetSomeStringRes getSomeString(String str, String str2, String str3) throws RmiException;

    public abstract long getTimestamp() throws RmiException;

    public abstract void triggerClick() throws RmiException;

    protected TestService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public TestService(String str) {
        super("10000000-0000-1000-8000-00805f9b34fc", str, SERVICE_TYPE);
    }

    public TestService(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    @Override // com.xiaomi.idm.api.IDMService
    public IDMServiceProto.IDMResponse request(IDMServiceProto.IDMRequest iDMRequest) {
        IDMService.Action action;
        int aid = iDMRequest.getAid();
        byte[] byteArray = iDMRequest.getRequest().toByteArray();
        if (byteArray == null) {
            return null;
        }
        try {
            switch (aid) {
                case 1:
                    action = new Actions.GetSomeString(this, byteArray);
                    break;
                case 2:
                    action = new Actions.GetAPlusB(this, byteArray);
                    break;
                case 3:
                    action = new Actions.GetTimestamp(this, byteArray);
                    break;
                case 4:
                    action = new Actions.TriggerClick(this, byteArray);
                    break;
                default:
                    action = null;
                    break;
            }
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e(SERVICE_TYPE, e.getMessage(), e);
            action = null;
        }
        if (action != null) {
            return ResponseHelper.buildResponse(iDMRequest, action.invoke());
        }
        return ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getCode(), ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getMsg() + " for uuid: " + getUUID() + " aid: " + aid, iDMRequest, null);
    }

    /* loaded from: classes3.dex */
    public static class Stub extends TestService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        public CallFuture<TestServiceProto.GetSomeStringRes> getSomeStringAsync(String str, String str2, String str3) {
            return this.a.request(new Actions.GetSomeString(this, str, str2, str3));
        }

        @Override // com.xiaomi.idm.service.iot.TestService
        public TestServiceProto.GetSomeStringRes getSomeString(String str, String str2, String str3) throws RmiException {
            try {
                return getSomeStringAsync(str, str2, str3).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<Integer> getAPlusBAsync(int i, int i2) {
            return this.a.request(new Actions.GetAPlusB(this, i, i2));
        }

        @Override // com.xiaomi.idm.service.iot.TestService
        public int getAPlusB(int i, int i2) throws RmiException {
            try {
                return getAPlusBAsync(i, i2).get().intValue();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<Long> getTimestampAsync() {
            return this.a.request(new Actions.GetTimestamp(this));
        }

        @Override // com.xiaomi.idm.service.iot.TestService
        public long getTimestamp() throws RmiException {
            try {
                return getTimestampAsync().get().longValue();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<Object> triggerClickAsync() {
            return this.a.request(new Actions.TriggerClick(this));
        }

        @Override // com.xiaomi.idm.service.iot.TestService
        public void triggerClick() throws RmiException {
            try {
                triggerClickAsync().get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public void subscribeMyTestEvent(Events.MyTestEvent.Callback callback) {
            this.a.setEventCallback(new Events.MyTestEvent(this, callback), true);
        }

        public void unsubscribeMyTestEvent(Events.MyTestEvent.Callback callback) {
            this.a.setEventCallback(new Events.MyTestEvent(this, callback), false);
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends TestService {
        private boolean a;

        public Skeleton(String str) {
            super(str);
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

        public void notifyMyTestEvent(int i, String str) {
            if (this.a) {
                notifyEvent(1, TestServiceProto.MyTestEvent.newBuilder().setParam(i).setParamStr(str).build().toByteArray());
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Actions {
        public static final int AID_GETAPLUSB = 2;
        public static final int AID_GETSOMESTRING = 1;
        public static final int AID_GETTIMESTAMP = 3;
        public static final int AID_TRIGGERCLICK = 4;

        /* loaded from: classes3.dex */
        public static class GetSomeString extends IDMService.Action<TestServiceProto.GetSomeStringRes> {
            TestServiceProto.GetSomeString a;

            GetSomeString(TestService testService, String str, String str2, String str3) {
                super(1, testService);
                this.a = TestServiceProto.GetSomeString.newBuilder().setAid(1).setParam1(str).setParam2(str2).setParam3(str3).build();
            }

            GetSomeString(TestService testService, byte[] bArr) throws InvalidProtocolBufferException {
                super(1, testService);
                this.a = TestServiceProto.GetSomeString.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public TestServiceProto.GetSomeStringRes parseResponse(byte[] bArr) throws RmiException {
                try {
                    return TestServiceProto.GetSomeStringRes.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                TestServiceProto.GetSomeStringRes getSomeStringRes;
                try {
                    getSomeStringRes = ((TestService) this.service).getSomeString(this.a.getParam1(), this.a.getParam2(), this.a.getParam3());
                } catch (RmiException e) {
                    LogUtil.e(TestService.SERVICE_TYPE, e.getMessage(), e);
                    getSomeStringRes = null;
                }
                if (getSomeStringRes == null) {
                    return null;
                }
                return getSomeStringRes.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                TestServiceProto.GetSomeString getSomeString = this.a;
                if (getSomeString == null) {
                    return null;
                }
                return getSomeString.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetAPlusB extends IDMService.Action<Integer> {
            TestServiceProto.GetAPlusB a;

            GetAPlusB(TestService testService, int i, int i2) {
                super(2, testService);
                this.a = TestServiceProto.GetAPlusB.newBuilder().setAid(2).setA(i).setB(i2).build();
            }

            GetAPlusB(TestService testService, byte[] bArr) throws InvalidProtocolBufferException {
                super(2, testService);
                this.a = TestServiceProto.GetAPlusB.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public Integer parseResponse(byte[] bArr) throws RmiException {
                try {
                    return Integer.valueOf(RMIBasicDataType.Integer.parseFrom(bArr).getV());
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                RMIBasicDataType.Integer integer;
                try {
                    integer = RMIBasicDataType.Integer.newBuilder().setV(((TestService) this.service).getAPlusB(this.a.getA(), this.a.getB())).build();
                } catch (RmiException e) {
                    LogUtil.e(TestService.SERVICE_TYPE, e.getMessage(), e);
                    integer = null;
                }
                if (integer == null) {
                    return null;
                }
                return integer.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                TestServiceProto.GetAPlusB getAPlusB = this.a;
                if (getAPlusB == null) {
                    return null;
                }
                return getAPlusB.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetTimestamp extends IDMService.Action<Long> {
            TestServiceProto.GetTimestamp a;

            GetTimestamp(TestService testService) {
                super(3, testService);
                this.a = TestServiceProto.GetTimestamp.newBuilder().setAid(3).build();
            }

            GetTimestamp(TestService testService, byte[] bArr) throws InvalidProtocolBufferException {
                super(3, testService);
                this.a = TestServiceProto.GetTimestamp.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public Long parseResponse(byte[] bArr) throws RmiException {
                try {
                    return Long.valueOf(RMIBasicDataType.Long.parseFrom(bArr).getV());
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                RMIBasicDataType.Long r1;
                try {
                    r1 = RMIBasicDataType.Long.newBuilder().setV(((TestService) this.service).getTimestamp()).build();
                } catch (RmiException e) {
                    LogUtil.e(TestService.SERVICE_TYPE, e.getMessage(), e);
                    r1 = null;
                }
                if (r1 == null) {
                    return null;
                }
                return r1.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                TestServiceProto.GetTimestamp getTimestamp = this.a;
                if (getTimestamp == null) {
                    return null;
                }
                return getTimestamp.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class TriggerClick extends IDMService.Action<Object> {
            TestServiceProto.TriggerClick a;

            TriggerClick(TestService testService) {
                super(4, testService);
                this.a = TestServiceProto.TriggerClick.newBuilder().setAid(4).build();
            }

            TriggerClick(TestService testService, byte[] bArr) throws InvalidProtocolBufferException {
                super(4, testService);
                this.a = TestServiceProto.TriggerClick.parseFrom(bArr);
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public Object parseResponse(byte[] bArr) throws RmiException {
                return new Object();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                try {
                    ((TestService) this.service).triggerClick();
                } catch (RmiException e) {
                    LogUtil.e(TestService.SERVICE_TYPE, e.getMessage(), e);
                }
                return new byte[0];
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                TestServiceProto.TriggerClick triggerClick = this.a;
                if (triggerClick == null) {
                    return null;
                }
                return triggerClick.toByteArray();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Events {
        public static final int EID_MYTESTEVENT = 1;

        /* loaded from: classes3.dex */
        public static class MyTestEvent extends IDMService.Event {
            Callback a;

            /* loaded from: classes3.dex */
            public interface Callback {
                void onMyTestEvent(int i, String str);
            }

            MyTestEvent(IDMService iDMService, Callback callback) {
                super(iDMService, 1);
                this.a = callback;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.idm.api.IDMService.Event
            public void onEvent(byte[] bArr) {
                try {
                    TestServiceProto.MyTestEvent parseFrom = TestServiceProto.MyTestEvent.parseFrom(bArr);
                    this.a.onMyTestEvent(parseFrom.getParam(), parseFrom.getParamStr());
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e(TestService.SERVICE_TYPE, e.getMessage(), e);
                }
            }
        }
    }
}
