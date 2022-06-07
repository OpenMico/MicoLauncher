package com.xiaomi.micolauncher.common.track;

import android.content.Context;
import android.os.Build;
import com.xiaomi.ai.android.helper.TrackHelper;
import com.xiaomi.ai.api.TrackLogV3;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.common.HardwareConfig;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEngineHelper;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.update.VersionUtil;
import com.xiaomi.smarthome.setting.ServerSetting;
import io.reactivex.android.schedulers.AndroidSchedulers;

/* loaded from: classes3.dex */
public class TrackLog {
    private volatile TrackHelper a;
    private HardwareConfig b;
    private String c;
    private String d;
    private TrackLogV3.ScreenBoxEventContext e;
    private Context f;

    /* loaded from: classes3.dex */
    public static class a {
        public static final TrackLog a = new TrackLog();
    }

    public static TrackLog shareInstance() {
        return a.a;
    }

    private TrackLog() {
    }

    public void init(Context context) {
        this.f = context;
        this.b = Hardware.current(context);
        this.c = MibrainConfig.getMibrainConfig(context).clientId;
        this.d = SpeechEngineHelper.getDeviceId(context);
        this.a = new TrackHelper(context, SpeechManager.getInstance().getAuthorizationValueBlocked(), c());
        this.a.setOnErrorListener($$Lambda$TrackLog$E4O95n3NfnW5qiUymZaACdONVs.INSTANCE);
        this.e = d();
    }

    public static /* synthetic */ void a(AivsError aivsError) {
        L.base.e("trackHelper error call back %s", aivsError);
        int errorCode = aivsError.getErrorCode();
        if (errorCode == 40110018 || errorCode == 401) {
            ApiHelper.getAuthorizationValue().subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe($$Lambda$TrackLog$WQzDsp8qWjKum41h7XgHazu6bE.INSTANCE, $$Lambda$TrackLog$Sh9Ak4difmRrPqBlbU_cyt0CrzY.INSTANCE);
        }
    }

    public static /* synthetic */ void a(String str) throws Exception {
        shareInstance().updateAuthToken(str);
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.base.e("get auth value error", th);
    }

    public void updateAuthToken(String str) {
        if (this.a != null) {
            this.a.setAuthorization(str);
        } else {
            L.base.e("update auth init TrackHelper first");
        }
    }

    private AivsConfig c() {
        AivsConfig aivsConfig = new AivsConfig();
        aivsConfig.putInt(AivsConfig.ENV, SpeechEngineHelper.getSpeechEnv());
        aivsConfig.putLong(AivsConfig.GeneralTrack.MAX_LOCAL_TRACK_LENGTH, 614400L);
        return aivsConfig;
    }

    private TrackLogV3.ScreenBoxEventContext d() {
        if (this.e == null) {
            this.e = new TrackLogV3.ScreenBoxEventContext();
            this.e.setDeviceId(this.d);
            this.e.setSerialNumber(Constants.getSn());
            this.e.setHardware(this.b.getName());
            this.e.setRomVersion(SystemSetting.getRomVersion());
            String romChannel = SystemSetting.getRomChannel();
            if ("release".equalsIgnoreCase(romChannel)) {
                this.e.setChannelVersion(VersionUtil.VERSION_STABLE);
            } else if (VersionUtil.VERSION_STABLE.equalsIgnoreCase(romChannel)) {
                this.e.setChannelVersion("dev");
            } else {
                this.e.setChannelVersion("beta");
            }
            this.e.setOsVersion(Build.VERSION.RELEASE);
            this.e.setDistrictSetting(ServerSetting.SERVER_CN);
            this.e.setAppId(this.c);
            this.e.setUid(TokenManager.getInstance().getUserId());
            this.e.setNetworkStatus(TrackLogV3.TrackNetworkStatus.WIFI);
        }
        return this.e;
    }

    public TrackLogV3.ScreenBoxEventContext a() {
        if (this.e == null) {
            L.base.e("init event context first");
        }
        e();
        return this.e;
    }

    private void e() {
        if (this.e != null) {
            if (ChildModeManager.getManager().isChildMode()) {
                this.e.setUserMode(TrackLogV3.TrackUserMode.KID);
            } else {
                this.e.setUserMode(TrackLogV3.TrackUserMode.NORMAL);
            }
        }
    }

    public TrackHelper b() {
        if (this.a == null) {
            L.base.e("init TrackHelper first");
        }
        return this.a;
    }
}
