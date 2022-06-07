package com.xiaomi.micolauncher.module.miot.defined.service;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.action.AlarmStop;
import com.xiaomi.micolauncher.module.miot.defined.action.Directives;
import com.xiaomi.micolauncher.module.miot.defined.action.Fm;
import com.xiaomi.micolauncher.module.miot.defined.action.Mute;
import com.xiaomi.micolauncher.module.miot.defined.action.Next;
import com.xiaomi.micolauncher.module.miot.defined.action.Pause;
import com.xiaomi.micolauncher.module.miot.defined.action.Play;
import com.xiaomi.micolauncher.module.miot.defined.action.Prev;
import com.xiaomi.micolauncher.module.miot.defined.action.Tts;
import com.xiaomi.micolauncher.module.miot.defined.action.TvSwitchOn;
import com.xiaomi.micolauncher.module.miot.defined.action.UnMute;
import com.xiaomi.micolauncher.module.miot.defined.action.WakeUp;
import com.xiaomi.micolauncher.module.miot.defined.property.PlayerState;
import com.xiaomi.micolauncher.module.miot.defined.property.Text;
import com.xiaomi.micolauncher.module.miot.defined.property.TtsReply;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.micolauncher.skills.mitv.MiTvManager;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.miot.typedef.data.value.Vint;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class Player extends MicoServiceOperable {
    public static final String TAG = "[Player:] ";
    public static final ServiceType TYPE = SpeakerDefined.Service.Player.toServiceType();
    private PropertyGetter a;
    private PropertySetter b;
    private ActionHandler c;

    /* loaded from: classes3.dex */
    public interface ActionHandler {
        void play(String str);
    }

    /* loaded from: classes3.dex */
    public interface PropertyGetter {
        int getPlayerState();
    }

    /* loaded from: classes3.dex */
    public interface PropertySetter {
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    protected int instanceID() {
        return 3;
    }

    public Player(boolean z) {
        super(TYPE);
        super.addProperty(new PlayerState());
        super.addProperty(new TtsReply());
        super.addAction(new Play());
        super.addAction(new Pause());
        super.addAction(new Prev());
        super.addAction(new Next());
        super.addAction(new Mute());
        super.addAction(new UnMute());
        if (!Hardware.isSupportSpec()) {
            super.addProperty(new Text());
            super.addAction(new Fm());
            super.addAction(new AlarmStop());
            super.addAction(new Tts());
            super.addAction(new TvSwitchOn());
            super.addAction(new WakeUp());
            super.addAction(new Directives());
        }
    }

    public PlayerState playerState() {
        Property property = super.getProperty(PlayerState.TYPE);
        if (property == null || !(property instanceof PlayerState)) {
            return null;
        }
        return (PlayerState) property;
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        L.miot.i("Spec:Player onGet sid=%s pid=%s toString=%s", Integer.valueOf(property.getServiceInstanceID()), Integer.valueOf(property.getInstanceID()), property.getDefinition().getType().toString());
        if (this.a == null) {
            return super.onGet(property);
        }
        SpeakerDefined.Property valueOf = SpeakerDefined.Property.valueOf(property.getDefinition().getType());
        MiotError miotError = MiotError.OK;
        if (AnonymousClass1.a[valueOf.ordinal()] != 1) {
            return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
        Log.d(TAG, "!!! PlayerState !!!");
        property.setValue(Integer.valueOf(this.a.getPlayerState()));
        return miotError;
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
        Log.i(TAG, "!! onAction");
        if (this.c == null) {
            return super.onAction(actionInfo);
        }
        a();
        SpeakerDefined.Action valueOf = SpeakerDefined.Action.valueOf(actionInfo.getType());
        int i = 0;
        L.miot.d("%s onAction action=%s", TAG, valueOf);
        switch (valueOf) {
            case Play:
                Log.d(TAG, "!! Play !!");
                PlayerApi.playOrPlayRecommend();
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_music, "1");
                return MiotError.OK;
            case Pause:
                Log.d(TAG, "!! Pause !!");
                PlayerApi.pause();
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_pause, "1");
                return MiotError.OK;
            case Prev:
                Log.d(TAG, "!! Prev !!");
                PlayerApi.prev();
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_prev, "1");
                return MiotError.OK;
            case Next:
                Log.d(TAG, "!! Next !!");
                PlayerApi.next();
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_next, "1");
                return MiotError.OK;
            case Mute:
                L.mic.d("[Player:] set mute true");
                Mic.getInstance().setMicMute(true);
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.mic_off, "1");
                return MiotError.OK;
            case UnMute:
                L.mic.d("[Player:] set mute false");
                Mic.getInstance().setMicMute(false);
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.mic_on, "1");
                return MiotError.OK;
            case Fm:
                Log.d(TAG, "!! FM !!");
                Log.d(TAG, "get playRecommendMusic !!");
                SchemaManager.handleSchema(MicoApplication.getGlobalContext(), HomepageSchemaHandler.PATH_INDIVIDUAL_RADIO);
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_fm, "1");
                return MiotError.OK;
            case AlarmStop:
                Log.d(TAG, "!! Alarmstop !!");
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.alarm_stop, "1");
                return MiotError.OK;
            case Tts:
                Log.d(TAG, "!! Tts !!");
                String value = ((Vstring) actionInfo.getArgumentValue(Text.TYPE)).getValue();
                if (TextUtils.isEmpty(value)) {
                    Log.d(TAG, "!! text is null !!");
                } else {
                    Log.d(TAG, "!! text: " + value);
                    SpeechManager.getInstance().ttsRequest(value);
                }
                StatPoints.recordPoint(StatPoints.Event.miio, StatPoints.MiotPluginAutoMation.play_tts, "1");
                return MiotError.OK;
            case TvSwitchOn:
                Log.d(TAG, "!! TvSwitchOn !!");
                MiTvManager.getInstance().wakeUpMiTv(null);
                return MiotError.OK;
            case WakeUp:
                Log.d(TAG, "WakeUp !!");
                if (!Mic.getInstance().isMicMute()) {
                    SpeechManager.getInstance().setWakeup();
                    Screen.getInstance().systemUiDismiss(0);
                } else {
                    Log.d(TAG, "isMicMute !");
                }
                return MiotError.OK;
            case Directives:
                Log.d(TAG, "Directives !!");
                String value2 = ((Vstring) actionInfo.getArgumentValue(Text.TYPE)).getValue();
                if (actionInfo.getArgumentValue(TtsReply.TYPE) == null) {
                    Log.d(TAG, "getArgumentValue TtsReply.TYPE is null !!");
                } else {
                    Log.d(TAG, "getArgumentValue TtsReply.TYPE is not null !!");
                    i = ((Vint) actionInfo.getArgumentValue(TtsReply.TYPE)).getValue();
                }
                Log.d(TAG, "directText: " + value2 + " ttsReply: " + i);
                if (i == 0) {
                    SpeechManager.getInstance().nlpRequest(value2);
                } else if (i == 1) {
                    SpeechManager.getInstance().nlpTtsRequest(value2);
                }
                return MiotError.OK;
            default:
                return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.defined.service.Player$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            try {
                b[SpeakerDefined.Action.Play.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[SpeakerDefined.Action.Pause.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[SpeakerDefined.Action.Prev.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[SpeakerDefined.Action.Next.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                b[SpeakerDefined.Action.Mute.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                b[SpeakerDefined.Action.UnMute.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                b[SpeakerDefined.Action.Fm.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                b[SpeakerDefined.Action.AlarmStop.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                b[SpeakerDefined.Action.Tts.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                b[SpeakerDefined.Action.TvSwitchOn.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                b[SpeakerDefined.Action.WakeUp.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                b[SpeakerDefined.Action.Directives.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            a = new int[SpeakerDefined.Property.values().length];
            try {
                a[SpeakerDefined.Property.PlayerState.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
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
