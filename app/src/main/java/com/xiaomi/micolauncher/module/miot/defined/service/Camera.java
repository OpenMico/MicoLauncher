package com.xiaomi.micolauncher.module.miot.defined.service;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.DoorBellStartEvent;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenSendBroadcast;
import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.action.Doorbell;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellDid;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellState;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellTimeStamp;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellTraceId;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.CameraEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.miot.BinderParcel;
import com.xiaomi.miot.support.ICameraStatus;
import com.xiaomi.miot.typedef.data.value.Vstring;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ServiceType;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class Camera extends MicoServiceOperable {
    private static final ServiceType a = SpeakerDefined.Service.Camera.toServiceType();
    private String e;
    private String f;
    private BasePlayer g;
    private PropertyGetter j;
    private PropertySetter k;
    private ActionHandler l;
    private String b = "";
    private volatile boolean c = false;
    private volatile boolean d = false;
    private ICameraStatus h = new ICameraStatus.Stub() { // from class: com.xiaomi.micolauncher.module.miot.defined.service.Camera.1
        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onPreviewStart(String str) {
            L.base.d("%s [preview start]: %s", "[Doorbell:] ", str);
            if (SpeechManager.getInstance().isRunning()) {
                Camera.this.d = true;
                L.base.d("%s isQuerySet: %s", "[Doorbell:] ", true);
                SpeechManager.getInstance().stop();
            }
            L.base.i("%s is running: %s", "[Doorbell:] ", Boolean.valueOf(SpeechManager.getInstance().isRunning()));
        }

        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onPreviewEnd(String str) {
            L.base.d("%s [preview stop]: %s", "[Doorbell:] ", str);
            if (!SpeechManager.getInstance().isRunning() && Camera.this.d) {
                Camera.this.d = false;
                L.base.d("%s isQuerySet: %s", "[Doorbell:] ", false);
                SpeechManager.getInstance().start();
            }
        }

        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onCallStart(String str) {
            L.base.d("%s [call start]", "[Doorbell:] ");
            if (Mic.getInstance().isMicMute()) {
                Camera.this.c = true;
                L.mic.d("%s isMicSet: %s", "[Doorbell:] ", true);
                Mic.getInstance().setMicMute(false);
            }
        }

        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onCallEnd(String str) {
            L.base.d("%s [call stop]: %s", "[Doorbell:] ", str);
            if (!Mic.getInstance().isMicMute() && Camera.this.c) {
                Camera.this.c = false;
                L.mic.d("%s isMicSet: %s", "[Doorbell:] ", false);
                Mic.getInstance().setMicMute(true);
            }
        }
    };
    private BasePlayer.PlayerListener i = new BasePlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.module.miot.defined.service.Camera.2
        @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
        public void onPrepared(BasePlayer basePlayer) {
            L.base.d("%s onPrepared !!", "[Doorbell:] ");
            if (Camera.this.g != null) {
                Camera.this.g.start();
            }
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
        public void onComplete(BasePlayer basePlayer) {
            L.base.d("%s onComplete !!", "[Doorbell:] ");
            Camera camera = Camera.this;
            if (camera.a(camera.e)) {
                L.base.e("%s doorbellSceneProcess failed!", "[Doorbell:] ");
            }
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
        public void onError(BasePlayer basePlayer, int i, int i2) {
            L.base.d("%s onError !!", "[Doorbell:] ");
            Camera camera = Camera.this;
            if (camera.a(camera.e)) {
                L.base.e("%s doorbellSceneProcess failed!", "[Doorbell:] ");
            }
        }
    };

    /* loaded from: classes3.dex */
    public interface ActionHandler {
        void doorBell(String str);
    }

    /* loaded from: classes3.dex */
    public interface PropertyGetter {
    }

    /* loaded from: classes3.dex */
    public interface PropertySetter {
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    protected int instanceID() {
        return 1;
    }

    public Camera() {
        super(a);
        super.addProperty(new DoorbellDid());
        super.addProperty(new DoorbellState());
        super.addProperty(new DoorbellTimeStamp());
        super.addProperty(new DoorbellTraceId());
        super.addAction(new Doorbell());
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        L.base.d("%s onGet", "[Doorbell:] ");
        if (this.j == null) {
            return super.onGet(property);
        }
        int i = AnonymousClass3.a[SpeakerDefined.Property.valueOf(property.getDefinition().getType()).ordinal()];
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onSet(Property property) {
        L.base.d("%s onGet", "[Doorbell:] ");
        if (this.k == null) {
            return super.onSet(property);
        }
        int i = AnonymousClass3.a[SpeakerDefined.Property.valueOf(property.getDefinition().getType()).ordinal()];
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    private void a() {
        L.base.d("%s playSound !!", "[Doorbell:] ");
        if (this.g == null) {
            this.g = new BasePlayer(BasePlayer.StreamType.STREAM_TYPE_ALARM);
        }
        this.g.setDataSource(R.raw.camera_doorbell, MicoApplication.getGlobalContext());
        this.g.setListener(this.i);
        this.g.prepareAsync();
        a(this.e, this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str) {
        if (VoipModel.getInstance().isVoipActive()) {
            CameraEvent cameraEvent = new CameraEvent();
            cameraEvent.did = str;
            cameraEvent.result = true;
            EventBusRegistry.getEventBus().post(cameraEvent);
            L.base.d("%s isVoip Running !", "[Doorbell:] ");
            return true;
        }
        LockScreenSendBroadcast.sendCloseLockScreenEventBroadcast(MicoApplication.getGlobalContext());
        Screen.getInstance().systemUiDismiss(0);
        Screen.getInstance().onResume();
        ActivityUtil.stopQiyiVideo(MicoApplication.getGlobalContext());
        if (!RomUpdateAdapter.getInstance().isUpdateOngoing()) {
            return false;
        }
        L.base.d("%s ROM update is ongoing.", "[Doorbell:] ");
        return true;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.service.MicoServiceOperable
    public MiotError executeAction(ActionInfo actionInfo) {
        if (this.l == null) {
            return super.onAction(actionInfo);
        }
        SpeakerDefined.Action valueOf = SpeakerDefined.Action.valueOf(actionInfo.getType());
        L.base.d("%s onAction %d", "[Doorbell:] ", Integer.valueOf(getInstanceID()));
        if (AnonymousClass3.b[valueOf.ordinal()] != 1) {
            return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
        L.base.d("%s Doorbell", "[Doorbell:] ");
        String value = ((Vstring) actionInfo.getArgumentValue(DoorbellDid.TYPE)).getValue();
        String value2 = ((Vstring) actionInfo.getArgumentValue(DoorbellState.TYPE)).getValue();
        String value3 = ((Vstring) actionInfo.getArgumentValue(DoorbellTimeStamp.TYPE)).getValue();
        String value4 = ((Vstring) actionInfo.getArgumentValue(DoorbellTraceId.TYPE)).getValue();
        L.base.i("%s did: %s, state: %s, timeStamp: %s, traceId: %s", "[Doorbell:] ", value, value2, value3, value4);
        this.e = value;
        this.f = value4;
        if (TextUtils.isEmpty(this.b)) {
            L.base.d("%s lastTimeStamp is null !", "[Doorbell:] ");
            this.b = value3;
        } else if (this.b.equals(value3)) {
            L.base.d("时间戳相同，过滤掉该门铃事件!", "[Doorbell:] ");
            return MiotError.OK;
        } else {
            L.base.d("%s 时间戳不相同!", "[Doorbell:] ");
            this.b = value3;
        }
        if (TextUtils.isEmpty(value2)) {
            L.base.i("%s doorBell state is null !", "[Doorbell:] ");
            a();
        } else if (value2.equals("0") || value2.equals("1")) {
            a();
        } else if (value2.equals("2")) {
            L.base.i("%s state = 2 ,doorBell 不播放提示音 ", "[Doorbell:] ");
        }
        return MiotError.OK;
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.defined.service.Camera$3  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b = new int[SpeakerDefined.Action.values().length];

        static {
            try {
                b[SpeakerDefined.Action.Doorbell.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            a = new int[SpeakerDefined.Property.values().length];
        }
    }

    public void setHandler(ActionHandler actionHandler, PropertyGetter propertyGetter, PropertySetter propertySetter) {
        this.l = actionHandler;
        this.j = propertyGetter;
        this.k = propertySetter;
    }

    private void a(String str, String str2) {
        Intent intent = new Intent();
        L.base.i("%s startDoorbellActivity", "[Doorbell:] ");
        intent.setComponent(new ComponentName("com.xiaomi.smarthome", "com.xiaomi.smarthome.camera.page.CameraP2pActivity"));
        intent.putExtra("did", str);
        intent.putExtra("traceId", str2);
        intent.putExtra("timestamp", this.b);
        ICameraStatus iCameraStatus = this.h;
        if (iCameraStatus != null) {
            intent.putExtra("status_call_back", new BinderParcel(iCameraStatus.asBinder()));
        }
        intent.setFlags(268435456);
        if (intent.resolveActivity(MicoApplication.getGlobalContext().getPackageManager()) != null) {
            L.base.i("%s start smartHome activity", "[Doorbell:] ");
            MicoApplication.getGlobalContext().startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDoorBellStartEvent(DoorBellStartEvent doorBellStartEvent) {
        L.base.d("%s onDoorBellStartEvent", "[Doorbell:] ");
        if (!doorBellStartEvent.action) {
            Intent intent = new Intent("com.xiaomi.mico.action.smartmiot.camera.close");
            intent.setPackage("com.xiaomi.smarthome");
            MicoApplication.getGlobalContext().sendBroadcast(intent);
        } else if (!a(this.e) && !TextUtils.isEmpty(this.e)) {
            a(this.e, this.f);
        }
    }
}
