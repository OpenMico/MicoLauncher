package com.xiaomi.idm.service.iot;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.iot.proto.PropertyServiceProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.idm.utils.ResponseHelper;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class PropertyService extends IDMService.BuiltinService {
    public static final String SUPERTYPE = "property";

    public abstract PropertyServiceProto.PropertyResponse getProperty(String str) throws RmiException;

    public abstract PropertyServiceProto.PropertyResponse getPropertyCommands(Map<String, String> map) throws RmiException;

    public abstract PropertyServiceProto.PropertyResponse setProperty(String str, boolean z) throws RmiException;

    public abstract PropertyServiceProto.PropertyResponse setPropertyCommands(Map<String, String> map) throws RmiException;

    public PropertyService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public PropertyService(String str, String str2, String str3) {
        super(str, str2, str3, SUPERTYPE);
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
                    action = new Actions.SetProperty(this, byteArray);
                    break;
                case 2:
                    action = new Actions.GetProperty(this, byteArray);
                    break;
                case 3:
                    action = new Actions.SetPropertyCommands(this, byteArray);
                    break;
                case 4:
                    action = new Actions.GetPropertyCommands(this, byteArray);
                    break;
                default:
                    action = null;
                    break;
            }
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("PropertyService", e.getMessage(), e);
            action = null;
        }
        if (action != null) {
            return ResponseHelper.buildResponse(iDMRequest, action.invoke());
        }
        return ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getCode(), ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getMsg() + " for uuid: " + getUUID() + " aid: " + aid, iDMRequest, null);
    }

    /* loaded from: classes3.dex */
    public static class Stub extends PropertyService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> setPropertyAsync(String str, boolean z) {
            return this.a.request(new Actions.SetProperty(this, str, z));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse setProperty(String str, boolean z) throws RmiException {
            try {
                return setPropertyAsync(str, z).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> getPropertyAsync(String str) {
            return this.a.request(new Actions.GetProperty(this, str));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse getProperty(String str) throws RmiException {
            try {
                return getPropertyAsync(str).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> setPropertyCommandsAsync(Map<String, String> map) {
            return this.a.request(new Actions.SetPropertyCommands(this, map));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse setPropertyCommands(Map<String, String> map) throws RmiException {
            try {
                return setPropertyCommandsAsync(map).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> getPropertyCommandsAsync(Map<String, String> map) {
            return this.a.request(new Actions.GetPropertyCommands(this, map));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse getPropertyCommands(Map<String, String> map) throws RmiException {
            try {
                return getPropertyCommandsAsync(map).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public void subscribePropertyEvent(Events.PropertyEvent.Callback callback) {
            this.a.setEventCallback(new Events.PropertyEvent(this, callback), true);
        }

        public void unsubscribePropertyEvent(Events.PropertyEvent.Callback callback) {
            this.a.setEventCallback(new Events.PropertyEvent(this, callback), false);
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends PropertyService {
        private boolean a;

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

        public void notifyPropertyEvent(int i, String str) {
            if (this.a) {
                notifyEvent(1, PropertyServiceProto.PropertyEvent.newBuilder().setParam(i).setParamStr(str).build().toByteArray());
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Actions {
        public static final int AID_GETPROPERTY = 2;
        public static final int AID_GETPROPERTYCOMMANDS = 4;
        public static final int AID_SETPROPERTY = 1;
        public static final int AID_SETPROPERTYCOMMANDS = 3;

        /* loaded from: classes3.dex */
        public static class SetProperty extends IDMService.Action<PropertyServiceProto.PropertyResponse> {
            PropertyServiceProto.SetProperty a;

            public SetProperty(PropertyService propertyService, String str, boolean z) {
                super(1, propertyService);
                this.a = PropertyServiceProto.SetProperty.newBuilder().setAid(1).setParamJson(str).setIsSort(z).build();
            }

            SetProperty(PropertyService propertyService, byte[] bArr) throws InvalidProtocolBufferException {
                super(1, propertyService);
                this.a = PropertyServiceProto.SetProperty.parseFrom(bArr);
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public PropertyServiceProto.PropertyResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return PropertyServiceProto.PropertyResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                PropertyServiceProto.PropertyResponse propertyResponse;
                try {
                    propertyResponse = ((PropertyService) this.service).setProperty(this.a.getParamJson(), this.a.getIsSort());
                } catch (RmiException e) {
                    LogUtil.e("PropertyService", e.getMessage(), e);
                    propertyResponse = null;
                }
                if (propertyResponse == null) {
                    return null;
                }
                return propertyResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                PropertyServiceProto.SetProperty setProperty = this.a;
                if (setProperty == null) {
                    return null;
                }
                return setProperty.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetProperty extends IDMService.Action<PropertyServiceProto.PropertyResponse> {
            PropertyServiceProto.GetProperty a;

            public GetProperty(PropertyService propertyService, String str) {
                super(2, propertyService);
                this.a = PropertyServiceProto.GetProperty.newBuilder().setAid(2).setParamJson(str).build();
            }

            GetProperty(PropertyService propertyService, byte[] bArr) throws InvalidProtocolBufferException {
                super(2, propertyService);
                this.a = PropertyServiceProto.GetProperty.parseFrom(bArr);
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public PropertyServiceProto.PropertyResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return PropertyServiceProto.PropertyResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                PropertyServiceProto.PropertyResponse propertyResponse;
                try {
                    propertyResponse = ((PropertyService) this.service).getProperty(this.a.getParamJson());
                } catch (RmiException e) {
                    LogUtil.e("PropertyService", e.getMessage(), e);
                    propertyResponse = null;
                }
                if (propertyResponse == null) {
                    return null;
                }
                return propertyResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                PropertyServiceProto.GetProperty getProperty = this.a;
                if (getProperty == null) {
                    return null;
                }
                return getProperty.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class SetPropertyCommands extends IDMService.Action<PropertyServiceProto.PropertyResponse> {
            PropertyServiceProto.SetPropertyCommands a;

            public SetPropertyCommands(PropertyService propertyService, Map<String, String> map) {
                super(3, propertyService);
                this.a = PropertyServiceProto.SetPropertyCommands.newBuilder().setAid(3).putAllPropertyMap(map).build();
            }

            public SetPropertyCommands(PropertyService propertyService, byte[] bArr) throws InvalidProtocolBufferException {
                super(3, propertyService);
                this.a = PropertyServiceProto.SetPropertyCommands.parseFrom(bArr);
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public PropertyServiceProto.PropertyResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return PropertyServiceProto.PropertyResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                PropertyServiceProto.PropertyResponse propertyResponse;
                try {
                    propertyResponse = ((PropertyService) this.service).setPropertyCommands(this.a.getPropertyMap());
                } catch (RmiException e) {
                    LogUtil.e("PropertyService", e.getMessage(), e);
                    propertyResponse = null;
                }
                if (propertyResponse == null) {
                    return null;
                }
                return propertyResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                PropertyServiceProto.SetPropertyCommands setPropertyCommands = this.a;
                if (setPropertyCommands == null) {
                    return null;
                }
                return setPropertyCommands.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetPropertyCommands extends IDMService.Action<PropertyServiceProto.PropertyResponse> {
            PropertyServiceProto.GetPropertyCommands a;

            public GetPropertyCommands(PropertyService propertyService, Map<String, String> map) {
                super(4, propertyService);
                this.a = PropertyServiceProto.GetPropertyCommands.newBuilder().setAid(4).putAllPropertyMap(map).build();
            }

            GetPropertyCommands(PropertyService propertyService, byte[] bArr) throws InvalidProtocolBufferException {
                super(4, propertyService);
                this.a = PropertyServiceProto.GetPropertyCommands.parseFrom(bArr);
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public PropertyServiceProto.PropertyResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return PropertyServiceProto.PropertyResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                PropertyServiceProto.PropertyResponse propertyResponse;
                try {
                    propertyResponse = ((PropertyService) this.service).getPropertyCommands(this.a.getPropertyMap());
                } catch (RmiException e) {
                    LogUtil.e("PropertyService", e.getMessage(), e);
                    propertyResponse = null;
                }
                if (propertyResponse == null) {
                    return null;
                }
                return propertyResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                PropertyServiceProto.GetPropertyCommands getPropertyCommands = this.a;
                if (getPropertyCommands == null) {
                    return null;
                }
                return getPropertyCommands.toByteArray();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Events {
        public static final int EID_PROPERTYEVENT = 1;

        /* loaded from: classes3.dex */
        public static class PropertyEvent extends IDMService.Event {
            Callback a;

            /* loaded from: classes3.dex */
            public interface Callback {
                void onPropertyEvent(int i, String str);
            }

            PropertyEvent(IDMService iDMService, Callback callback) {
                super(iDMService, 1);
                this.a = callback;
            }

            @Override // com.xiaomi.idm.api.IDMService.Event
            public void onEvent(byte[] bArr) {
                try {
                    PropertyServiceProto.PropertyEvent parseFrom = PropertyServiceProto.PropertyEvent.parseFrom(bArr);
                    this.a.onPropertyEvent(parseFrom.getParam(), parseFrom.getParamStr());
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("PropertyService", e.getMessage(), e);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class PropertyCommand {

        /* loaded from: classes3.dex */
        public static class PropertyBuilder {
            private HashMap<String, String> a = new HashMap<>();

            public PropertyBuilder addProperty(String str, String str2) {
                this.a.put(str, str2);
                return this;
            }

            public HashMap<String, String> build() {
                return this.a;
            }
        }
    }
}
