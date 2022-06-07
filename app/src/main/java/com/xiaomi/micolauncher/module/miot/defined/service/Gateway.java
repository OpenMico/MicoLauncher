package com.xiaomi.micolauncher.module.miot.defined.service;

import android.util.Log;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.action.GatewayDisable;
import com.xiaomi.micolauncher.module.miot.defined.action.GatewayEnable;
import com.xiaomi.micolauncher.module.miot.defined.action.GatewayStatus;
import com.xiaomi.micolauncher.module.miot.defined.property.GatewayStatusResponse;
import com.xiaomi.micolauncher.module.miot.defined.property.MeshSwitch;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class Gateway extends MicoServiceOperable {
    public static final String TAG = "[Gateway:] ";
    public static final ServiceType TYPE = SpeakerDefined.Service.Player.toServiceType();
    private PropertyGetter a;
    private PropertySetter b;
    private ActionHandler c;

    /* loaded from: classes3.dex */
    public interface ActionHandler {
        void GatewayEnable(String str);
    }

    /* loaded from: classes3.dex */
    public interface PropertyGetter {
    }

    /* loaded from: classes3.dex */
    public interface PropertySetter {
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    protected int instanceID() {
        return 0;
    }

    public Gateway(boolean z) {
        super(TYPE);
        super.addProperty(new MeshSwitch());
        super.addProperty(new GatewayStatusResponse());
        super.addAction(new GatewayEnable());
        super.addAction(new GatewayDisable());
        super.addAction(new GatewayStatus());
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        Log.i(TAG, "onGet");
        if (this.a == null) {
            return super.onGet(property);
        }
        SpeakerDefined.Property valueOf = SpeakerDefined.Property.valueOf(property.getDefinition().getType());
        MiotError miotError = MiotError.OK;
        int i = AnonymousClass1.a[valueOf.ordinal()];
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onSet(Property property) {
        Log.i(TAG, "onSet");
        if (this.b == null) {
            return super.onSet(property);
        }
        SpeakerDefined.Property valueOf = SpeakerDefined.Property.valueOf(property.getDefinition().getType());
        MiotError miotError = MiotError.OK;
        int i = AnonymousClass1.a[valueOf.ordinal()];
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    public MiotError executeAction(ActionInfo actionInfo) {
        if (this.c == null) {
            return super.onAction(actionInfo);
        }
        a();
        SpeakerDefined.Action valueOf = SpeakerDefined.Action.valueOf(actionInfo.getType());
        L.base.d("%s executeAction instanceID=%d, action=%s", TAG, Integer.valueOf(getInstanceID()), valueOf);
        switch (valueOf) {
            case bt_gateway_enable:
                L.mesh.d("%s executeAction=bt_gateway_enable, then initMiotMeshManager", TAG);
                MiotProvisionManagerWrapper.getInstance().initMiotMeshManager(false);
                return MiotError.OK;
            case bt_gateway_disable:
                Log.d(TAG, "bt_gateway_disable !!");
                MiotProvisionManagerWrapper.getInstance().quitMesh();
                return MiotError.OK;
            case bt_gateway_status:
                if (MiotProvisionManagerWrapper.isMeshEnable()) {
                    actionInfo.getResult(GatewayStatusResponse.TYPE).setValue("enable");
                } else {
                    actionInfo.getResult(GatewayStatusResponse.TYPE).setValue("disable");
                }
                return MiotError.OK;
            default:
                return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.defined.service.Gateway$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            try {
                b[SpeakerDefined.Action.bt_gateway_enable.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[SpeakerDefined.Action.bt_gateway_disable.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[SpeakerDefined.Action.bt_gateway_status.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            a = new int[SpeakerDefined.Property.values().length];
        }
    }

    private void a() {
        EventBusRegistry.getEventBus().post(new AlarmVoiceCloseEvent());
    }

    public void setHandler(ActionHandler actionHandler, PropertyGetter propertyGetter, PropertySetter propertySetter) {
        this.c = actionHandler;
        this.a = propertyGetter;
        this.b = propertySetter;
    }
}
