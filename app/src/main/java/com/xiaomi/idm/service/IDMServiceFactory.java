package com.xiaomi.idm.service;

import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.handoff.HandoffService;
import com.xiaomi.idm.service.iot.IPCameraService;
import com.xiaomi.idm.service.iot.InputMethodService;
import com.xiaomi.idm.service.iot.IotLocalControlService;
import com.xiaomi.idm.service.iot.LightService;
import com.xiaomi.idm.service.iot.MotionSensorService;
import com.xiaomi.idm.service.iot.PropertyService;
import com.xiaomi.idm.service.iot.TestService;

/* loaded from: classes3.dex */
public class IDMServiceFactory {
    public static IDMService createIDMService(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
        IDMService iDMService2 = null;
        if (iDMService == null) {
            return null;
        }
        String type = iDMService.getType();
        if (TestService.SERVICE_TYPE.equals(type)) {
            iDMService2 = new TestService.Stub(iDMClient, iDMService);
        } else if ("urn:aiot-spec-v3:service:iot-local-control:00000001:1".equals(type)) {
            iDMService2 = new IotLocalControlService.Stub(iDMClient, iDMService);
        } else if ("urn:aiot-spec-v3:service:ip-camera:00000001:1".equals(type)) {
            iDMService2 = new IPCameraService.Stub(iDMClient, iDMService);
        } else if (IDMService.TYPE_HANDOFF.equals(type)) {
            iDMService2 = new HandoffService.Stub(iDMClient, iDMService);
        } else if ("urn:aiot-spec-v3:service:input:00000001:1".equals(type)) {
            iDMService2 = new InputMethodService.Stub(iDMClient, iDMService);
        } else if ("urn:aiot-spec-v3:service:light:00000001:1".equals(type)) {
            iDMService2 = new LightService.Stub(iDMClient, iDMService);
        } else if ("urn:aiot-spec-v3:service:motionsensor:00000001:1".equals(type)) {
            iDMService2 = new MotionSensorService.Stub(iDMClient, iDMService);
        }
        return (iDMService2 != null || iDMService.getSuperType() == null || !iDMService.getSuperType().equals(PropertyService.SUPERTYPE)) ? iDMService2 : new PropertyService.Stub(iDMClient, iDMService);
    }
}
