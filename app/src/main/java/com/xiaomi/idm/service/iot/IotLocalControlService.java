package com.xiaomi.idm.service.iot;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.idm.utils.ResponseHelper;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class IotLocalControlService extends IDMService.BuiltinService {
    public static final String SERVICE_TYPE = "urn:aiot-spec-v3:service:iot-local-control:00000001:1";

    public abstract IotLocalControlServiceProto.IotResponse exeScenes(String str, String str2, String str3) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse getDeviceInformations(String str, String str2, String str3) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse getDeviceProperties(String str, String str2, String str3) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse getDevices(String str, String str2, boolean z) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse getHomeFastCommands(String str, String str2) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse getHomes(String str, String str2) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse getSceneAlias(String str, String str2, String str3) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse getScenes(String str, String str2) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse invokeAction(String str, String str2, String str3) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse setDeviceProperties(String str, String str2, String str3, boolean z) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse setToken(String str, String str2) throws RmiException;

    public abstract IotLocalControlServiceProto.IotResponse stop(String str, String str2) throws RmiException;

    protected IotLocalControlService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public IotLocalControlService(String str, String str2) {
        super(str, str2, "urn:aiot-spec-v3:service:iot-local-control:00000001:1");
    }

    public IotLocalControlService(String str, String str2, String str3) {
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
                    action = new Actions.GetDevices(this, byteArray);
                    break;
                case 2:
                    action = new Actions.GetHomes(this, byteArray);
                    break;
                case 3:
                    action = new Actions.GetScenes(this, byteArray);
                    break;
                case 4:
                    action = new Actions.GetDeviceInformations(this, byteArray);
                    break;
                case 5:
                    action = new Actions.GetHomeFastCommands(this, byteArray);
                    break;
                case 6:
                    action = new Actions.GetDeviceProperties(this, byteArray);
                    break;
                case 7:
                    action = new Actions.SetDeviceProperties(this, byteArray);
                    break;
                case 8:
                    action = new Actions.ExeScenes(this, byteArray);
                    break;
                case 9:
                    action = new Actions.SetToken(this, byteArray);
                    break;
                case 10:
                    action = new Actions.Stop(this, byteArray);
                    break;
                case 11:
                    action = new Actions.GetSceneAlias(this, byteArray);
                    break;
                case 12:
                    action = new Actions.InvokeAction(this, byteArray);
                    break;
                default:
                    action = null;
                    break;
            }
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("IotLocalControlService", e.getMessage(), e);
            action = null;
        }
        if (action != null) {
            return ResponseHelper.buildResponse(iDMRequest, action.invoke());
        }
        return ResponseHelper.buildResponse(ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getCode(), ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND.getMsg() + " for uuid: " + getUUID() + " aid: " + aid, iDMRequest, null);
    }

    /* loaded from: classes3.dex */
    public static class Stub extends IotLocalControlService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> getDevicesAsync(String str, String str2, boolean z) {
            return this.a.request(new Actions.GetDevices(this, str, str2, z));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse getDevices(String str, String str2, boolean z) throws RmiException {
            try {
                return getDevicesAsync(str, str2, z).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> getHomesAsync(String str, String str2) {
            return this.a.request(new Actions.GetHomes(this, str, str2));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse getHomes(String str, String str2) throws RmiException {
            try {
                return getHomesAsync(str, str2).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> getScenesAsync(String str, String str2) {
            return this.a.request(new Actions.GetScenes(this, str, str2));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse getScenes(String str, String str2) throws RmiException {
            try {
                return getScenesAsync(str, str2).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> getDeviceInformationsAsync(String str, String str2, String str3) {
            return this.a.request(new Actions.GetDeviceInformations(this, str, str2, str3));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse getDeviceInformations(String str, String str2, String str3) throws RmiException {
            try {
                return getDeviceInformationsAsync(str, str2, str3).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> getHomeFastCommandsAsync(String str, String str2) {
            return this.a.request(new Actions.GetHomeFastCommands(this, str, str2));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse getHomeFastCommands(String str, String str2) throws RmiException {
            try {
                return getHomeFastCommandsAsync(str, str2).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> getDevicePropertiesAsync(String str, String str2, String str3) {
            return this.a.request(new Actions.GetDeviceProperties(this, str, str2, str3));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse getDeviceProperties(String str, String str2, String str3) throws RmiException {
            try {
                return getDevicePropertiesAsync(str, str2, str3).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> setDevicePropertiesAsync(String str, String str2, String str3, boolean z) {
            return this.a.request(new Actions.SetDeviceProperties(this, str, str2, str3, z));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse setDeviceProperties(String str, String str2, String str3, boolean z) throws RmiException {
            try {
                return setDevicePropertiesAsync(str, str2, str3, z).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> exeScenesAsync(String str, String str2, String str3) {
            return this.a.request(new Actions.ExeScenes(this, str, str2, str3));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse exeScenes(String str, String str2, String str3) throws RmiException {
            try {
                return exeScenesAsync(str, str2, str3).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> setTokenAsync(String str, String str2) {
            return this.a.request(new Actions.SetToken(this, str, str2));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse setToken(String str, String str2) throws RmiException {
            try {
                return setTokenAsync(str, str2).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> stopAsync(String str, String str2) {
            return this.a.request(new Actions.Stop(this, str, str2));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse stop(String str, String str2) throws RmiException {
            try {
                return stopAsync(str, str2).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> getSceneAliasAsync(String str, String str2, String str3) {
            return this.a.request(new Actions.GetSceneAlias(this, str, str2, str3));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse getSceneAlias(String str, String str2, String str3) throws RmiException {
            try {
                return getSceneAliasAsync(str, str2, str3).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<IotLocalControlServiceProto.IotResponse> invokeActionAsync(String str, String str2, String str3) {
            return this.a.request(new Actions.InvokeAction(this, str, str2, str3));
        }

        @Override // com.xiaomi.idm.service.iot.IotLocalControlService
        public IotLocalControlServiceProto.IotResponse invokeAction(String str, String str2, String str3) throws RmiException {
            try {
                return invokeActionAsync(str, str2, str3).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends IotLocalControlService {
        public Skeleton(String str, String str2) {
            super(str, str2, "urn:aiot-spec-v3:service:iot-local-control:00000001:1");
        }

        public Skeleton(String str, String str2, String str3) {
            super(str, str2, str3);
        }
    }

    /* loaded from: classes3.dex */
    public static class Actions {
        public static final int AID_EXESCENES = 8;
        public static final int AID_GETDEVICEINFORMATIONS = 4;
        public static final int AID_GETDEVICEPROPERTIES = 6;
        public static final int AID_GETDEVICES = 1;
        public static final int AID_GETHOMEFASTCOMMANDS = 5;
        public static final int AID_GETHOMES = 2;
        public static final int AID_GETSCENEALIAS = 11;
        public static final int AID_GETSCENES = 3;
        public static final int AID_INVOKEACTION = 12;
        public static final int AID_SETDEVICEPROPERTIES = 7;
        public static final int AID_SETTOKEN = 9;
        public static final int AID_STOP = 10;

        /* loaded from: classes3.dex */
        public static class GetDevices extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.GetDevices a;

            GetDevices(IotLocalControlService iotLocalControlService, String str, String str2, boolean z) {
                super(1, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetDevices.newBuilder().setAid(1).setServiceToken(str).setAppId(str2).setIsLocal(z).build();
            }

            GetDevices(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(1, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetDevices.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).getDevices(this.a.getServiceToken(), this.a.getAppId(), this.a.getIsLocal());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.GetDevices getDevices = this.a;
                if (getDevices == null) {
                    return null;
                }
                return getDevices.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetHomes extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.GetHomes a;

            GetHomes(IotLocalControlService iotLocalControlService, String str, String str2) {
                super(2, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetHomes.newBuilder().setAid(2).setServiceToken(str).setAppId(str2).build();
            }

            GetHomes(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(2, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetHomes.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).getHomes(this.a.getServiceToken(), this.a.getAppId());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.GetHomes getHomes = this.a;
                if (getHomes == null) {
                    return null;
                }
                return getHomes.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetScenes extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.GetScenes a;

            GetScenes(IotLocalControlService iotLocalControlService, String str, String str2) {
                super(3, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetScenes.newBuilder().setAid(3).setServiceToken(str).setAppId(str2).build();
            }

            GetScenes(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(3, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetScenes.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).getScenes(this.a.getServiceToken(), this.a.getAppId());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.GetScenes getScenes = this.a;
                if (getScenes == null) {
                    return null;
                }
                return getScenes.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetDeviceInformations extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.GetDeviceInformations a;

            GetDeviceInformations(IotLocalControlService iotLocalControlService, String str, String str2, String str3) {
                super(4, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetDeviceInformations.newBuilder().setAid(4).setServiceToken(str).setAppId(str2).setDeviceId(str3).build();
            }

            GetDeviceInformations(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(4, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetDeviceInformations.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).getDeviceInformations(this.a.getServiceToken(), this.a.getAppId(), this.a.getDeviceId());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.GetDeviceInformations getDeviceInformations = this.a;
                if (getDeviceInformations == null) {
                    return null;
                }
                return getDeviceInformations.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetHomeFastCommands extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.GetHomeFastCommands a;

            GetHomeFastCommands(IotLocalControlService iotLocalControlService, String str, String str2) {
                super(5, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetHomeFastCommands.newBuilder().setAid(5).setServiceToken(str).setAppId(str2).build();
            }

            GetHomeFastCommands(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(5, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetHomeFastCommands.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).getHomeFastCommands(this.a.getServiceToken(), this.a.getAppId());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.GetHomeFastCommands getHomeFastCommands = this.a;
                if (getHomeFastCommands == null) {
                    return null;
                }
                return getHomeFastCommands.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetDeviceProperties extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.GetDeviceProperties a;

            GetDeviceProperties(IotLocalControlService iotLocalControlService, String str, String str2, String str3) {
                super(6, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetDeviceProperties.newBuilder().setAid(6).setServiceToken(str).setAppId(str2).setPropertyId(str3).build();
            }

            GetDeviceProperties(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(6, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetDeviceProperties.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).getDeviceProperties(this.a.getServiceToken(), this.a.getAppId(), this.a.getPropertyId());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.GetDeviceProperties getDeviceProperties = this.a;
                if (getDeviceProperties == null) {
                    return null;
                }
                return getDeviceProperties.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class SetDeviceProperties extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.SetDeviceProperties a;

            SetDeviceProperties(IotLocalControlService iotLocalControlService, String str, String str2, String str3, boolean z) {
                super(7, iotLocalControlService);
                this.a = IotLocalControlServiceProto.SetDeviceProperties.newBuilder().setAid(7).setServiceToken(str).setAppId(str2).setPropertyBody(str3).setIsSort(z).build();
            }

            SetDeviceProperties(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(7, iotLocalControlService);
                this.a = IotLocalControlServiceProto.SetDeviceProperties.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).setDeviceProperties(this.a.getServiceToken(), this.a.getAppId(), this.a.getPropertyBody(), this.a.getIsSort());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.SetDeviceProperties setDeviceProperties = this.a;
                if (setDeviceProperties == null) {
                    return null;
                }
                return setDeviceProperties.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class ExeScenes extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.ExeScenes a;

            ExeScenes(IotLocalControlService iotLocalControlService, String str, String str2, String str3) {
                super(8, iotLocalControlService);
                this.a = IotLocalControlServiceProto.ExeScenes.newBuilder().setAid(8).setServiceToken(str).setAppId(str2).setSceneId(str3).build();
            }

            ExeScenes(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(8, iotLocalControlService);
                this.a = IotLocalControlServiceProto.ExeScenes.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).exeScenes(this.a.getServiceToken(), this.a.getAppId(), this.a.getSceneId());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.ExeScenes exeScenes = this.a;
                if (exeScenes == null) {
                    return null;
                }
                return exeScenes.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class SetToken extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.SetToken a;

            SetToken(IotLocalControlService iotLocalControlService, String str, String str2) {
                super(9, iotLocalControlService);
                this.a = IotLocalControlServiceProto.SetToken.newBuilder().setAid(9).setAppId(str).setServiceToken(str2).build();
            }

            SetToken(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(9, iotLocalControlService);
                this.a = IotLocalControlServiceProto.SetToken.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).setToken(this.a.getAppId(), this.a.getServiceToken());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.SetToken setToken = this.a;
                if (setToken == null) {
                    return null;
                }
                return setToken.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class Stop extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.Stop a;

            Stop(IotLocalControlService iotLocalControlService, String str, String str2) {
                super(10, iotLocalControlService);
                this.a = IotLocalControlServiceProto.Stop.newBuilder().setAid(10).setAppId(str).setServiceToken(str2).build();
            }

            Stop(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(10, iotLocalControlService);
                this.a = IotLocalControlServiceProto.Stop.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).stop(this.a.getAppId(), this.a.getServiceToken());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.Stop stop = this.a;
                if (stop == null) {
                    return null;
                }
                return stop.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class GetSceneAlias extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.GetSceneAlias a;

            GetSceneAlias(IotLocalControlService iotLocalControlService, String str, String str2, String str3) {
                super(11, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetSceneAlias.newBuilder().setAid(11).setAppId(str).setServiceToken(str2).setSceneName(str3).build();
            }

            GetSceneAlias(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(11, iotLocalControlService);
                this.a = IotLocalControlServiceProto.GetSceneAlias.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).getSceneAlias(this.a.getAppId(), this.a.getServiceToken(), this.a.getSceneName());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.GetSceneAlias getSceneAlias = this.a;
                if (getSceneAlias == null) {
                    return null;
                }
                return getSceneAlias.toByteArray();
            }
        }

        /* loaded from: classes3.dex */
        public static class InvokeAction extends IDMService.Action<IotLocalControlServiceProto.IotResponse> {
            IotLocalControlServiceProto.InvokeAction a;

            InvokeAction(IotLocalControlService iotLocalControlService, String str, String str2, String str3) {
                super(12, iotLocalControlService);
                this.a = IotLocalControlServiceProto.InvokeAction.newBuilder().setAid(12).setAppId(str).setServiceToken(str2).setActionBody(str3).build();
            }

            InvokeAction(IotLocalControlService iotLocalControlService, byte[] bArr) throws InvalidProtocolBufferException {
                super(12, iotLocalControlService);
                this.a = IotLocalControlServiceProto.InvokeAction.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public IotLocalControlServiceProto.IotResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return IotLocalControlServiceProto.IotResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                IotLocalControlServiceProto.IotResponse iotResponse;
                try {
                    iotResponse = ((IotLocalControlService) this.service).invokeAction(this.a.getAppId(), this.a.getServiceToken(), this.a.getActionBody());
                } catch (RmiException e) {
                    LogUtil.e("IotLocalControlService", e.getMessage(), e);
                    iotResponse = null;
                }
                if (iotResponse == null) {
                    return null;
                }
                return iotResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                IotLocalControlServiceProto.InvokeAction invokeAction = this.a;
                if (invokeAction == null) {
                    return null;
                }
                return invokeAction.toByteArray();
            }
        }
    }
}
