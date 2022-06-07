package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class TencentVideoAppCommandProcessor extends BaseAppCommandProcessor {
    public static final String PACKAGE_NAME_TENCENT_VIDEO = "com.tencent.qqlive.audiobox";
    private IDeviceVoiceServiceAidl a;
    private String c;
    private Context d;
    private volatile boolean b = false;
    private final ServiceConnection e = new ServiceConnection() { // from class: com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TencentVideoAppCommandProcessor.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TencentVideoAppCommandProcessor.this.a = IDeviceVoiceServiceAidl.Stub.asInterface(iBinder);
            TencentVideoAppCommandProcessor.this.b = true;
            if (TencentVideoAppCommandProcessor.this.a != null && TencentVideoAppCommandProcessor.this.c != null) {
                try {
                    L.video.i("onServiceConnected,TencentVideoAppCommandProcessor callAsrEvent:%s", TencentVideoAppCommandProcessor.this.c);
                    TencentVideoAppCommandProcessor.this.a.callAsrEvent(TencentVideoAppCommandProcessor.this.c);
                } catch (RemoteException | NullPointerException e) {
                    L.video.e("TencentVideoAppCommandProcessor,onServiceConnected", e);
                }
                TencentVideoAppCommandProcessor.this.c = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            L.video.i("onServiceDisconnected,ComponentName: %s", componentName);
            TencentVideoAppCommandProcessor.this.a = null;
            TencentVideoAppCommandProcessor.this.b = false;
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            L.video.i("onBindingDied %s", componentName);
            TencentVideoAppCommandProcessor.this.a = null;
            TencentVideoAppCommandProcessor.this.b = false;
        }
    };

    private String a() {
        return "com.ktcp.aiagent";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return "com.tencent.qqlive.audiobox";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean shouldCountTimeOnChildMode() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stop(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportBackward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportCommunicate() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportForward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportGesture() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    public static String getUrl(String str) {
        return (str == null || str.length() != 15) ? String.format("txvideo://v.qq.com/videodetail/VideoDetailActivity?lid=%s", str) : String.format("txvideo://v.qq.com/videodetail/VideoDetailActivity?cid=%s", str);
    }

    public static String getUrl(String str, String str2) {
        return String.format(Locale.ENGLISH, "txvideo://v.qq.com/videodetail/VideoDetailActivity?vid=%s&cid=%s", str2, str);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startApp(Context context, ThirdPartyAppProxy.OnFillIntentExtras onFillIntentExtras) {
        launchTencentVideo(context, "");
        a(context);
    }

    public static void launchTencentVideo(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("com.ktcp.aiagent.voice.trigger");
        intent.setPackage("com.ktcp.aiagent");
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("trigger_asr_text", str);
        }
        intent.setFlags(268435456);
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
        L.video.i("TencentVideoAppCommandProcessor,startVideo,startArgs:%s", startArgs);
        if (startArgs != null) {
            ActivityLifeCycleManager.startActivityQuietly(startArgs.getUri());
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSoundboxControlAction(PlaybackControllerInfo playbackControllerInfo) {
        switch (playbackControllerInfo.playbackControl) {
            case PLAY:
            case CONTINUE_PLAYING:
            case PAUSE:
            case STOP:
            case PREV:
            case NEXT:
            case SEEK:
            case REWIND:
            case FAST_FORWARD:
            case SKIP_START:
                return true;
            case SET_PROPERTY:
                return playbackControllerInfo.setPropertyName == PlaybackControllerInfo.SetProperty.RESOLUTION ? true : true;
            default:
                return false;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void forward(Context context, long j) {
        communicate(context, context.getString(R.string.tencent_asr_forward));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void backward(Context context, long j) {
        communicate(context, context.getString(R.string.tencent_asr_backward));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void pause(Context context) {
        communicate(context, context.getString(R.string.tencent_asr_pause));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void play(Context context) {
        communicate(context, context.getString(R.string.tencent_asr_play));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void skipStart(Context context) {
        communicate(context, context.getString(R.string.tencent_asr_skip_start));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void seek(Context context, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.tencent_asr_seek));
        long millis = j / TimeUnit.HOURS.toMillis(1L);
        if (millis > 0) {
            j -= TimeUnit.HOURS.toMillis(1L) * millis;
            sb.append(millis);
            sb.append(context.getString(R.string.tencent_asr_seek_hour));
        }
        long millis2 = j / TimeUnit.MINUTES.toMillis(1L);
        if (millis2 > 0) {
            j -= TimeUnit.MINUTES.toMillis(1L) * millis2;
            sb.append(millis2);
            sb.append(context.getString(R.string.tencent_asr_seek_minute));
        }
        long millis3 = j / TimeUnit.SECONDS.toMillis(1L);
        if (millis3 > 0) {
            TimeUnit.SECONDS.toMillis(1L);
            sb.append(millis3);
            sb.append(context.getString(R.string.tencent_asr_seek_second));
        }
        communicate(context, sb.toString());
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void quit(Context context) {
        L.video.i("quit package %s, Context %s", getPackage(), context);
        b();
        ActivityUtil.forceStopPackage(context, getPackage());
        ActivityUtil.forceStopPackage(context, a());
    }

    private void a(Context context) {
        Intent intent = new Intent("com.ktcp.voicedevice.ACTION_MICO_BIND_SERVICE");
        intent.setPackage(context.getPackageName());
        this.d = context.getApplicationContext();
        L.video.i("bindVoiceDeviceService bindResult: %s, bindContext %s", Boolean.valueOf(this.d.bindService(intent, this.e, 1)), this.d);
    }

    private void b() {
        if (this.b && this.d != null) {
            L.video.i("unbindVoiceDeviceService,bindServiceContext:%s", this.d);
            this.d.unbindService(this.e);
            this.b = false;
            this.d = null;
            this.a = null;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void communicate(Context context, String str) {
        IDeviceVoiceServiceAidl iDeviceVoiceServiceAidl = this.a;
        if (iDeviceVoiceServiceAidl != null) {
            try {
                iDeviceVoiceServiceAidl.callAsrEvent(str);
            } catch (RemoteException e) {
                L.video.e("TencentVideoAppCommandProcessor,communicate", e);
            }
            L.video.i("communicate callAsrEvent:%s", str);
            return;
        }
        L.video.i("TencentVideoAppCommandProcessor deviceVoiceService is null,bindVoiceDeviceService,asrText:%s", str);
        a(context);
        this.c = str;
    }
}
