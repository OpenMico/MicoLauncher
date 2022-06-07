package com.xiaomi.micolauncher.common.quickapp;

import android.app.Activity;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.common.WakeupUiEvent;
import java.util.HashMap;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.hapjs.features.channel.vui.IWorker;
import org.hapjs.features.channel.vui.VuiBridgeCallback;
import org.hapjs.features.channel.vui.VuiBridgeManager;
import org.hapjs.features.channel.vui.VuiCallback;
import org.hapjs.features.channel.vui.VuiResult;
import org.hapjs.features.channel.vui.data.Location;

/* loaded from: classes3.dex */
public class Worker implements IWorker {
    private MultiAudioPlayer b;
    private HashMap<Long, VuiCallback<Location>> a = new HashMap<>();
    private volatile boolean c = false;

    @Override // org.hapjs.features.channel.vui.IWorker
    public void connect(int i, final VuiCallback vuiCallback) {
        new Thread(new Runnable() { // from class: com.xiaomi.micolauncher.common.quickapp.Worker.1
            @Override // java.lang.Runnable
            public void run() {
                L.quickapp.i("VUI:Worker 【connect】");
                Worker.this.c = true;
                vuiCallback.callback(new VuiResult(0, null));
            }
        }).start();
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void tts(final String str, final VuiCallback vuiCallback) {
        Logger logger = L.quickapp;
        logger.d("VUI:Worker", "【text】: " + str);
        MultiAudioPlayer multiAudioPlayer = this.b;
        if (multiAudioPlayer != null) {
            multiAudioPlayer.stop();
            this.b = null;
        }
        if (this.b == null) {
            this.b = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_MUSIC);
            this.b.setListener(new MultiAudioPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.common.quickapp.Worker.2
                @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
                public void onComplete() {
                    L.quickapp.i("VUI:Worker 【tts onComplete】");
                    vuiCallback.callback(new VuiResult(0, null));
                }

                @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
                public void onError(int i, Exception exc) {
                    L.quickapp.i("VUI:Worker【tts onError】");
                    vuiCallback.callback(new VuiResult(3003, null));
                    L.player.e("VUI:Worker failed to play tts %s", str);
                }
            });
            L.quickapp.i("VUI:Worker【tts】 %s", str);
            this.b.playTts(str, true);
        }
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void stopTts(VuiCallback vuiCallback) {
        MultiAudioPlayer multiAudioPlayer = this.b;
        if (multiAudioPlayer != null) {
            multiAudioPlayer.stop();
            this.b = null;
        }
        L.quickapp.i("VUI:Worker 【stopTts】");
        vuiCallback.callback(new VuiResult(0, null));
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void openMic(VuiCallback vuiCallback) {
        SpeechManager.getInstance().setWakeup();
        L.quickapp.i("VUI:Worker 【openMic】");
        vuiCallback.callback(new VuiResult(0, null));
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void closeMic(VuiCallback vuiCallback) {
        SpeechManager.getInstance().setUnWakeup();
        L.quickapp.i("VUI:Worker 【closeMic】");
        vuiCallback.callback(new VuiResult(0, null));
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void query(String str, VuiCallback vuiCallback) {
        L.quickapp.i("VUI:Worker 【query】 %s", str);
        if (TextUtils.equals("退出", str) || TextUtils.equals("__EXIT__", str)) {
            SpeechManager.getInstance().nlpRequest("退出");
            this.c = false;
            L.quickapp.i("VUI:Worker 【setNewSession】");
            SpeechManager.getInstance().setNewSession();
        } else {
            SpeechManager.getInstance().nlpRequest(str);
        }
        vuiCallback.callback(new VuiResult(0, null));
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void pause(String str, VuiCallback vuiCallback) {
        L.quickapp.i("VUI:Worker 【pause】 %s", str);
        vuiCallback.callback(new VuiResult(0, null));
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void resume(String str, VuiCallback vuiCallback) {
        L.quickapp.i("VUI:Worker 【resume】 %s", str);
        vuiCallback.callback(new VuiResult(0, null));
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void registerLocationUpdate(long j, VuiCallback<Location> vuiCallback) {
        this.a.put(Long.valueOf(j), vuiCallback);
        L.quickapp.i("VUI:Worker 【registerLocationUpdate】 %s", Long.valueOf(j));
        for (int i = 0; i < 1; i++) {
            Location location = new Location();
            location.setLatitude("90");
            location.setLongitude("90");
            vuiCallback.callback(new VuiResult<>(0, location));
        }
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public void unregisterLocationUpdate(long j, VuiCallback vuiCallback) {
        this.a.remove(Long.valueOf(j));
        L.quickapp.i("VUI:Worker 【unregisterLocationUpdate】 %s", Long.valueOf(j));
        vuiCallback.callback(new VuiResult(0, null));
    }

    @Override // org.hapjs.features.channel.vui.IWorker
    public boolean onAccept(String str, String str2) {
        L.quickapp.i("VUI:Worker 【onAccept】 %s %s", str, str2);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onWakeupUiEvent(WakeupUiEvent wakeupUiEvent) {
        if (!VuiBridgeManager.getInstance().isChannelConnected("com.xiaomi.aiot.soundbox_template.mi")) {
            this.c = false;
        }
        Activity topActivity = ActivityLifeCycleManager.getInstance().getTopActivity();
        if (topActivity != null && (topActivity instanceof BaseActivity) && ((BaseActivity) topActivity).isMicoActivityResumed()) {
            this.c = false;
        }
        if (this.c) {
            StringBuilder sb = new StringBuilder("{\"type\":\"event\",");
            sb.append("\"data\":{\"WakeupUiEvent\":");
            switch (wakeupUiEvent.getEvent()) {
                case 0:
                    sb.append("\"WAKEUP_UI_SHOW\"");
                    break;
                case 1:
                    sb.append("\"WAKEUP_UI_ASR\"");
                    break;
                case 2:
                    sb.append("\"WAKEUP_UI_NLP\"");
                    break;
                case 3:
                    sb.append("\"WAKEUP_UI_DISMISS\"");
                    break;
            }
            sb.append("}}");
            String sb2 = sb.toString();
            L.quickapp.i("onWakeupUiEvent 【%s】", sb2);
            VuiBridgeManager.getInstance().sendRawData("com.xiaomi.aiot.soundbox_template.mi", sb2, "/Pages/Home", new VuiBridgeCallback<JsonNode>() { // from class: com.xiaomi.micolauncher.common.quickapp.Worker.3
                /* renamed from: a */
                public void onSuccess(JsonNode jsonNode) {
                    L.quickapp.i("【WakeupUiEvent onSuccess】: %s", jsonNode.toString());
                }

                @Override // org.hapjs.features.channel.vui.VuiBridgeCallback
                public void onFail(int i, String str) {
                    L.quickapp.i("【WakeupUiEvent onFail】: %s,%s", Integer.valueOf(i), str);
                }
            });
            if (wakeupUiEvent.getEvent() != 0) {
                wakeupUiEvent.getEvent();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onQuickAppQuitEvent(QuickAppQuitEvent quickAppQuitEvent) {
        if (quickAppQuitEvent.quit) {
            this.c = false;
        }
    }
}
