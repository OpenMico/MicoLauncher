package com.xiaomi.micolauncher.module.miot.defined.service;

import android.util.Log;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.property.Down;
import com.xiaomi.micolauncher.module.miot.defined.property.SpeakerMute;
import com.xiaomi.micolauncher.module.miot.defined.property.SpeakerRate;
import com.xiaomi.micolauncher.module.miot.defined.property.SpeakerVolume;
import com.xiaomi.micolauncher.module.miot.defined.property.Up;
import com.xiaomi.miot.typedef.data.value.Vbool;
import com.xiaomi.miot.typedef.data.value.Vint;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class Speaker extends MicoServiceOperable {
    public static final String TAG = "[Speaker]";
    public static final ServiceType TYPE = SpeakerDefined.Service.Speaker.toServiceType();
    private PropertyGetter a;
    private PropertySetter b;

    /* loaded from: classes3.dex */
    public interface PropertyGetter {
        String getDown();

        boolean getSpeakerMute();

        int getSpeakerRate();

        int getSpeakerVolume();

        String getUp();
    }

    /* loaded from: classes3.dex */
    public interface PropertySetter {
        void setDown(String str);

        void setSpeakerMute(boolean z);

        void setSpeakerRate(int i);

        void setSpeakerVolume(int i);

        void setUp(String str);
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    protected int instanceID() {
        return 2;
    }

    public Speaker(boolean z) {
        super(TYPE);
        super.addProperty(new SpeakerVolume());
        super.addProperty(new SpeakerRate());
        super.addProperty(new SpeakerMute());
        super.addProperty(new Up());
        super.addProperty(new Down());
    }

    public SpeakerVolume speakerVolume() {
        Property property = super.getProperty(SpeakerVolume.TYPE);
        if (property == null || !(property instanceof SpeakerVolume)) {
            return null;
        }
        return (SpeakerVolume) property;
    }

    public SpeakerRate speakerRate() {
        Property property = super.getProperty(SpeakerRate.TYPE);
        if (property == null || !(property instanceof SpeakerRate)) {
            return null;
        }
        return (SpeakerRate) property;
    }

    public Up up() {
        Property property = super.getProperty(Up.TYPE);
        if (property == null || !(property instanceof Up)) {
            return null;
        }
        return (Up) property;
    }

    public Down down() {
        Property property = super.getProperty(Down.TYPE);
        if (property == null || !(property instanceof Down)) {
            return null;
        }
        return (Down) property;
    }

    public SpeakerMute speakerMute() {
        Property property = super.getProperty(SpeakerMute.TYPE);
        if (property == null || !(property instanceof SpeakerMute)) {
            return null;
        }
        return (SpeakerMute) property;
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        Log.i(TAG, "!! onGet");
        if (this.a == null) {
            return super.onGet(property);
        }
        SpeakerDefined.Property valueOf = SpeakerDefined.Property.valueOf(property.getDefinition().getType());
        MiotError miotError = MiotError.OK;
        switch (valueOf) {
            case SpeakerVolume:
                Log.d(TAG, "!!! SpeakerVolume !!!");
                property.setValue(Integer.valueOf(this.a.getSpeakerVolume()));
                return miotError;
            case SpeakerRate:
                Log.d(TAG, "!!! SpeakerRate !!!");
                property.setValue(Integer.valueOf(this.a.getSpeakerRate()));
                return miotError;
            case SpeakerMute:
                Log.d(TAG, "!!! SpeakerMute !!!");
                property.setValue(Boolean.valueOf(this.a.getSpeakerMute()));
                return miotError;
            case Up:
                Log.d(TAG, "!!! Up !!!");
                property.setValue(this.a.getUp());
                return miotError;
            case Down:
                Log.d(TAG, "!!! Down !!!");
                property.setValue(this.a.getDown());
                return miotError;
            default:
                return MiotError.IOT_RESOURCE_NOT_EXIST;
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
        switch (valueOf) {
            case SpeakerVolume:
                Log.d(TAG, "!!! SpeakerVolume !!!");
                this.b.setSpeakerVolume(((Vint) property.getCurrentValue()).getValue());
                return miotError;
            case SpeakerRate:
                Log.d(TAG, "!!! SpeakerRate !!!");
                this.b.setSpeakerRate(((Vint) property.getCurrentValue()).getValue());
                return miotError;
            case SpeakerMute:
                Log.d(TAG, "!!! SpeakerMute !!!");
                this.b.setSpeakerMute(((Vbool) property.getCurrentValue()).getValue());
                return miotError;
            case Up:
                Log.d(TAG, "!!! Up !!!");
                this.b.setUp(((Vstring) property.getCurrentValue()).getValue());
                return miotError;
            case Down:
                Log.d(TAG, "!!! Down !!!");
                this.b.setDown(((Vstring) property.getCurrentValue()).getValue());
                return miotError;
            default:
                return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
    }

    public void setHandler(PropertyGetter propertyGetter, PropertySetter propertySetter) {
        this.a = propertyGetter;
        this.b = propertySetter;
    }
}
