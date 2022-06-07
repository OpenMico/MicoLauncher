package com.xiaomi.micolauncher.module.miot.defined.service;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.action.DirectivesIntelligent;
import com.xiaomi.micolauncher.module.miot.defined.action.Fm;
import com.xiaomi.micolauncher.module.miot.defined.action.Tts;
import com.xiaomi.micolauncher.module.miot.defined.action.WakeUp;
import com.xiaomi.micolauncher.module.miot.defined.property.SilentExecution;
import com.xiaomi.micolauncher.module.miot.defined.property.Text;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.miot.typedef.data.value.Vbool;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class IntelligentSpeaker extends MicoServiceOperable {
    public static final String TAG = "[IntelligentSpeaker:] ";
    public static final ServiceType TYPE = SpeakerDefined.Service.IntelligentSpeaker.toServiceType();
    private PropertyGetter a;
    private PropertySetter b;
    private ActionHandler c;

    /* loaded from: classes3.dex */
    public interface ActionHandler {
        void excute();
    }

    /* loaded from: classes3.dex */
    public interface PropertyGetter {
    }

    /* loaded from: classes3.dex */
    public interface PropertySetter {
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    protected int instanceID() {
        return 7;
    }

    public IntelligentSpeaker() {
        super(TYPE);
        super.addProperty(new Text());
        super.addProperty(new SilentExecution());
        super.addAction(new Fm());
        super.addAction(new WakeUp());
        super.addAction(new Tts());
        super.addAction(new DirectivesIntelligent());
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        L.miot.d("IntelligentSpeaker onGet sid=%s pid=%s toString=%s", Integer.valueOf(property.getServiceInstanceID()), Integer.valueOf(property.getInstanceID()), property.getDefinition().getType().toString());
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
        boolean z;
        if (this.c == null) {
            return super.onAction(actionInfo);
        }
        a();
        SpeakerDefined.Action valueOf = SpeakerDefined.Action.valueOf(actionInfo.getType());
        L.miot.d("%s onAction action=%s", TAG, valueOf);
        switch (valueOf) {
            case Fm:
                L.miot.d("%s !! FM !!", TAG);
                L.miot.d("%s get playRecommendMusic !!");
                SchemaManager.handleSchema(MicoApplication.getGlobalContext(), HomepageSchemaHandler.PATH_INDIVIDUAL_RADIO);
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_fm, "1");
                break;
            case Tts:
                L.miot.d("%s !! Tts !!", TAG);
                String value = ((Vstring) actionInfo.getArgumentValue(Text.TYPE)).getValue();
                if (TextUtils.isEmpty(value)) {
                    L.miot.d("%s !! text is null !!", TAG);
                } else {
                    L.miot.d("%s !! text: ", TAG);
                    SpeechManager.getInstance().ttsRequest(value);
                }
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_tts, "1");
                break;
            case WakeUp:
                L.miot.d("%s WakeUp !! ", TAG);
                if (Mic.getInstance().isMicMute()) {
                    L.miot.d("%s isMicMute !! ", TAG);
                    break;
                } else {
                    SpeechManager.getInstance().setWakeup();
                    break;
                }
            case Directives:
                L.miot.d("%s Directives !! ", TAG);
                String value2 = ((Vstring) actionInfo.getArgumentValue(Text.TYPE)).getValue();
                if (actionInfo.getArgumentValue(SilentExecution.TYPE) == null) {
                    L.miot.d("%s getArgumentValue TtsReply.TYPE is null !! ", TAG);
                    z = false;
                } else {
                    L.miot.d("%s getArgumentValue TtsReply.TYPE is not null !! ", TAG);
                    z = ((Vbool) actionInfo.getArgumentValue(SilentExecution.TYPE)).getValue();
                }
                L.miot.d("%s directText: %s result: %s!! ", TAG, value2, Boolean.valueOf(z));
                if (!z) {
                    SpeechManager.getInstance().nlpTtsRequest(value2);
                    break;
                } else {
                    SpeechManager.getInstance().nlpRequest(value2);
                    break;
                }
            default:
                return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
        return MiotError.OK;
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.defined.service.IntelligentSpeaker$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            try {
                b[SpeakerDefined.Action.Fm.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[SpeakerDefined.Action.Tts.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[SpeakerDefined.Action.WakeUp.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[SpeakerDefined.Action.Directives.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
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
