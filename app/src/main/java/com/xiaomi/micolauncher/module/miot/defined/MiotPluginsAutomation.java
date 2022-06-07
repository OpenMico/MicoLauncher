package com.xiaomi.micolauncher.module.miot.defined;

import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.VolumeLimitInPowerSaveModeEvent;
import com.xiaomi.micolauncher.module.miot.MiotDevice;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.miot.defined.service.Camera;
import com.xiaomi.micolauncher.module.miot.defined.service.Clock;
import com.xiaomi.micolauncher.module.miot.defined.service.Gateway;
import com.xiaomi.micolauncher.module.miot.defined.service.IntelligentSpeaker;
import com.xiaomi.micolauncher.module.miot.defined.service.Microphone;
import com.xiaomi.micolauncher.module.miot.defined.service.Player;
import com.xiaomi.micolauncher.module.miot.defined.service.Speaker;
import com.xiaomi.micolauncher.module.miot.defined.service.TvSwitch;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;

/* loaded from: classes3.dex */
public class MiotPluginsAutomation {
    private static final MiotPluginsAutomation a = new MiotPluginsAutomation();
    private Microphone b = new Microphone(false);
    private Speaker c = new Speaker(false);
    private Player d = new Player(false);
    private Camera e = new Camera();
    private Gateway f = new Gateway(false);
    private Clock g = new Clock();
    private IntelligentSpeaker h = new IntelligentSpeaker();
    private TvSwitch i = new TvSwitch();

    public static MiotPluginsAutomation getInstance() {
        return a;
    }

    public Microphone getMicrophone() {
        return this.b;
    }

    public Speaker getSpeaker() {
        return this.c;
    }

    public Player getPlayer() {
        return this.d;
    }

    public Camera getCamera() {
        return this.e;
    }

    public Gateway getGateway() {
        return this.f;
    }

    public Clock getClock() {
        return this.g;
    }

    public TvSwitch getTvSwitch() {
        return this.i;
    }

    public IntelligentSpeaker getIntelligentSpeaker() {
        return this.h;
    }

    public void registerProperty() {
        L.miot.i("%s registerProperty !!", "[MiotPluginsAutomation]: ");
        MiotDevice device = MiotDeviceManager.getInstance().getDevice();
        this.b.setHandler(new Microphone.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.-$$Lambda$MiotPluginsAutomation$h1ovExVFRFVeS1U-LO-zS_VlcZY
            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Microphone.PropertyGetter
            public final boolean getMicrophoneMute() {
                boolean e;
                e = MiotPluginsAutomation.this.e();
                return e;
            }
        }, new Microphone.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.-$$Lambda$MiotPluginsAutomation$ef_ACrjjOUg8LoTbtj9BWfs6IoY
            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Microphone.PropertySetter
            public final void setMicrophoneMute(boolean z) {
                MiotPluginsAutomation.this.a(z);
            }
        });
        this.c.setHandler(new Speaker.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.1
            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertyGetter
            public int getSpeakerVolume() {
                L.miot.i("%s Get getSpeakerVolume !!", "[MiotPluginsAutomation]: ");
                return MiotPluginsAutomation.this.c.speakerVolume().getValue();
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertyGetter
            public int getSpeakerRate() {
                L.miot.i("%s Get getSpeakerRate !!", "[MiotPluginsAutomation]: ");
                return MiotPluginsAutomation.this.c.speakerRate().getValue();
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertyGetter
            public boolean getSpeakerMute() {
                L.miot.i("%s Get getSpeakerMute !!", "[MiotPluginsAutomation]: ");
                return MiotPluginsAutomation.this.c.speakerMute().getValue();
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertyGetter
            public String getUp() {
                L.miot.i("%s Get getUp !!", "[MiotPluginsAutomation]: ");
                return MiotPluginsAutomation.this.c.up().getValue();
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertyGetter
            public String getDown() {
                L.miot.i("%s Get getUp !!", "[MiotPluginsAutomation]: ");
                return MiotPluginsAutomation.this.c.down().getValue();
            }
        }, new Speaker.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.7
            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertySetter
            public void setSpeakerVolume(int i) {
                L.miot.i("%s Set SetSpeakerVolume: %s", "[MiotPluginsAutomation]: ", Integer.valueOf(i));
                MiotPluginsAutomation.this.c.speakerVolume().setValue(i);
                if (SystemVolume.volumeTooBigForPowerSaveMode(i)) {
                    EventBusRegistry.getEventBus().post(new VolumeLimitInPowerSaveModeEvent());
                }
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertySetter
            public void setSpeakerRate(int i) {
                L.miot.i("%s Set SpeakerRate: rate=%s", "[MiotPluginsAutomation]: ", Integer.valueOf(i));
                MiotPluginsAutomation.this.c.speakerRate().setValue(i);
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertySetter
            public void setSpeakerMute(boolean z) {
                L.miot.i("%s Set setSpeakerMute: mute=%s", "[MiotPluginsAutomation]: ", Boolean.valueOf(z));
                MiotPluginsAutomation.this.c.speakerMute().setValue(z);
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertySetter
            public void setUp(String str) {
                L.miot.i("%s Set setUp !!", "[MiotPluginsAutomation]: ");
            }

            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Speaker.PropertySetter
            public void setDown(String str) {
                L.miot.i("%s Set setDown !!", "[MiotPluginsAutomation]: ");
            }
        });
        this.d.setHandler($$Lambda$MiotPluginsAutomation$8X3RbOFc0w2C6lp1TOzvGHssKnU.INSTANCE, new Player.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.-$$Lambda$MiotPluginsAutomation$-MfLTIsBlgaU5Dsr4YBLESIs1D0
            @Override // com.xiaomi.micolauncher.module.miot.defined.service.Player.PropertyGetter
            public final int getPlayerState() {
                int d;
                d = MiotPluginsAutomation.this.d();
                return d;
            }
        }, new Player.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.8
        });
        this.f.setHandler($$Lambda$MiotPluginsAutomation$0FtsOaRBMOq2bh3K3qECK6yX6Vs.INSTANCE, new Gateway.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.9
        }, new Gateway.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.10
        });
        this.e.setHandler($$Lambda$MiotPluginsAutomation$XLmobR8YOiU9mVjVihYPi_NNpw.INSTANCE, new Camera.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.11
        }, new Camera.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.12
        });
        this.g.setHandler($$Lambda$MiotPluginsAutomation$wXysLcIzpYD__vv_n_KL13p4cd4.INSTANCE, new Clock.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.13
        }, new Clock.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.14
        });
        this.h.setHandler($$Lambda$MiotPluginsAutomation$tLm3th79x4FYbSF0Of_NGBHR1lc.INSTANCE, new IntelligentSpeaker.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.2
        }, new IntelligentSpeaker.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.3
        });
        this.i.setHandler($$Lambda$MiotPluginsAutomation$6aV9OG9RCRzgd4OjBJPN0TzUv6E.INSTANCE, new TvSwitch.PropertyGetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.4
        }, new TvSwitch.PropertySetter() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.5
        });
        try {
            device.register(new CompletedListener() { // from class: com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.6
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    L.miot.d("%s register onSucceed: %s", "[MiotPluginsAutomation]: ", str);
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    L.miot.e("%s register onFailed %s", "[MiotPluginsAutomation]: ", miotError);
                }
            });
        } catch (MiotException e) {
            L.miot.e("%s device register failed: %s", "[MiotPluginsAutomation]: ", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean e() {
        L.miot.i("%s Get MicrophoneMute !!", "[MiotPluginsAutomation]: ");
        return this.b.microphonemute().getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z) {
        this.b.microphonemute().setValue(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(String str) {
        L.miot.i("%s ACTION play !!", "[MiotPluginsAutomation]: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int d() {
        return this.d.playerState().getPlayerStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(String str) {
        L.miot.i("%s ACTION Gateway !!", "[MiotPluginsAutomation]: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str) {
        L.miot.i("%s ACTION doorBell !!", "[MiotPluginsAutomation]: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c() {
        L.miot.i("%s ACTION clock !!", "[MiotPluginsAutomation]: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b() {
        L.miot.i("%s ACTION IntelligentSpeaker !!", "[MiotPluginsAutomation]: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a() {
        L.miot.i("%s ACTION TvSwitch !!", "[MiotPluginsAutomation]: ");
    }
}
