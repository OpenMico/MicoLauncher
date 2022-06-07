package com.xiaomi.micolauncher.module.miot.defined.service;

import android.util.Log;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.property.MicrophoneMute;
import com.xiaomi.miot.typedef.data.value.Vbool;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class Microphone extends MicoServiceOperable {
    public static final String TAG = "[Microphone]: ";
    public static final ServiceType TYPE = SpeakerDefined.Service.Microphone.toServiceType();
    private PropertyGetter a;
    private PropertySetter b;

    /* loaded from: classes3.dex */
    public interface PropertyGetter {
        boolean getMicrophoneMute();
    }

    /* loaded from: classes3.dex */
    public interface PropertySetter {
        void setMicrophoneMute(boolean z);
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    protected int instanceID() {
        return 4;
    }

    public Microphone(boolean z) {
        super(TYPE);
        super.addProperty(new MicrophoneMute());
    }

    public MicrophoneMute microphonemute() {
        Property property = super.getProperty(MicrophoneMute.TYPE);
        if (property == null || !(property instanceof MicrophoneMute)) {
            return null;
        }
        return (MicrophoneMute) property;
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        Log.i(TAG, "!! onGet");
        if (this.a == null) {
            return super.onGet(property);
        }
        SpeakerDefined.Property valueOf = SpeakerDefined.Property.valueOf(property.getDefinition().getType());
        MiotError miotError = MiotError.OK;
        if (AnonymousClass1.a[valueOf.ordinal()] != 1) {
            return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
        Log.d(TAG, "!!! MicrophoneMute !!!");
        property.setValue(Boolean.valueOf(this.a.getMicrophoneMute()));
        return miotError;
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.defined.service.Microphone$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[SpeakerDefined.Property.values().length];

        static {
            try {
                a[SpeakerDefined.Property.MicrophoneMute.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onSet(Property property) {
        Log.i(TAG, "!! onSet !!");
        if (this.b == null) {
            return super.onSet(property);
        }
        SpeakerDefined.Property valueOf = SpeakerDefined.Property.valueOf(property.getDefinition().getType());
        MiotError miotError = MiotError.OK;
        if (AnonymousClass1.a[valueOf.ordinal()] != 1) {
            return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
        Log.d(TAG, "!!! MicrophoneMute !!!");
        this.b.setMicrophoneMute(((Vbool) property.getCurrentValue()).getValue());
        return miotError;
    }

    public void setHandler(PropertyGetter propertyGetter, PropertySetter propertySetter) {
        this.a = propertyGetter;
        this.b = propertySetter;
    }
}
