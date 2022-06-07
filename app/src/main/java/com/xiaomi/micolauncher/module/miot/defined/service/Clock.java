package com.xiaomi.micolauncher.module.miot.defined.service;

import android.util.Log;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.action.AlarmStop;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class Clock extends MicoServiceOperable {
    public static final String TAG = "[Clock:] ";
    public static final ServiceType TYPE = SpeakerDefined.Service.Clock.toServiceType();
    private PropertyGetter a;
    private PropertySetter b;
    private ActionHandler c;

    /* renamed from: com.xiaomi.micolauncher.module.miot.defined.service.Clock$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[SpeakerDefined.Property.values().length];
    }

    /* loaded from: classes3.dex */
    public interface ActionHandler {
        void stopAlarm();
    }

    /* loaded from: classes3.dex */
    public interface PropertyGetter {
    }

    /* loaded from: classes3.dex */
    public interface PropertySetter {
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    protected int instanceID() {
        return 6;
    }

    public Clock() {
        super(TYPE);
        super.addAction(new AlarmStop());
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        L.miot.i("%s onGet", TAG);
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
        SpeakerDefined.Action valueOf = SpeakerDefined.Action.valueOf(actionInfo.getType());
        L.miot.d("%s onAction action=%s", TAG, valueOf);
        if (valueOf != SpeakerDefined.Action.AlarmStop) {
            return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
        a();
        StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.alarm_stop, "1");
        return MiotError.OK;
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
