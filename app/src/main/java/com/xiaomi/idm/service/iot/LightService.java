package com.xiaomi.idm.service.iot;

import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.iot.PropertyService;
import com.xiaomi.idm.service.iot.proto.PropertyServiceProto;
import com.xiaomi.idm.task.CallFuture;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class LightService extends PropertyService {
    public static final String SERVICE_TYPE = "urn:aiot-spec-v3:service:light:00000001:1";

    /* loaded from: classes3.dex */
    public static class LightPropertyCommand extends PropertyService.PropertyCommand {
        public static final String BRIGHTNESS = "brightness";
        public static final String COLOR = "color";
        public static final String COLORTEMPERATURE = "colortemperature";
        public static final String FLEXSWITCH = "flexswitch";
        public static final String FLOW = "flow";
        public static final String GETLIGHTPROPERTY = "getlightproperty";
        public static final String LIGHTCONTROLS = "lightcontrols";
        public static final String LIGHT_SERVICE_DESC = "light";
        public static final String MODE = "mode";
        public static final String ON = "on";
        public static final String SATURABILITY = "saturability";
    }

    protected LightService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public LightService(String str, String str2) {
        super(str, str2, "urn:aiot-spec-v3:service:light:00000001:1");
    }

    public LightService(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    /* loaded from: classes3.dex */
    public static class Stub extends LightService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> setPropertyAsync(String str, boolean z) {
            return this.a.request(new PropertyService.Actions.SetProperty(this, str, z));
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
            return this.a.request(new PropertyService.Actions.GetProperty(this, str));
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
            return this.a.request(new PropertyService.Actions.SetPropertyCommands(this, map));
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
            return this.a.request(new PropertyService.Actions.GetPropertyCommands(this, map));
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
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends LightService {
        public Skeleton(String str, String str2) {
            super(str, str2, "urn:aiot-spec-v3:service:light:00000001:1");
        }

        public Skeleton(String str, String str2, String str3) {
            super(str, str2, str3);
        }
    }
}
