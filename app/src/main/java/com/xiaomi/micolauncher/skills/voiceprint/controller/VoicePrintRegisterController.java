package com.xiaomi.micolauncher.skills.voiceprint.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRefreshTipEvent;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterModel;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterProgressEvent;
import com.xiaomi.micolauncher.skills.voiceprint.view.VoicePrintRegisterActivity;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VoicePrintRegisterController implements MultiAudioPlayer.PlayerListener {
    public static final String PARAMS_CNT = "cnt";
    public static final String PARAMS_NICK_NAME = "nick_name";
    public static final String PARAMS_RESULT = "result";
    public static final String PARAMS_TOTAL_CNT = "total_cnt";
    public static final String PARAMS_TTS = "tts";
    private static final long a = TimeUnit.SECONDS.toMillis(20);
    private static final long b = TimeUnit.SECONDS.toMillis(2);
    private Context d;
    private VoicePrintRegisterModel e;
    private MultiAudioPlayer g;
    private int c = 0;
    private FakePlayer f = new FakePlayer(AudioSource.AUDIO_SOURCE_UI) { // from class: com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController.1
        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
        public void postStop(AudioSource audioSource) {
            L.skill.d("%s postStop", "[VoicePrint]:");
            VoicePrintRegisterController.this.a(true);
            SpeechManager.getInstance().setNewSession();
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void suspend(AudioSource audioSource) {
            L.skill.d("%s suspend", "[VoicePrint]:");
            VoicePrintRegisterController.this.g.stop();
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void background(AudioSource audioSource) {
            L.skill.d("%s background", "[VoicePrint]:");
            VoicePrintRegisterController.this.g.stop();
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void resume(AudioSource audioSource) {
            super.resume(audioSource);
            L.skill.d("%s resume", "[VoicePrint]:");
            if (VoicePrintRegisterController.this.h == a.REGISTER_STEP_RESULT) {
                VoicePrintRegisterController.this.a(true);
                SpeechManager.getInstance().setNewSession();
            }
        }
    };
    private a h = a.REGISTER_STEP_IDLE;
    private Handler i = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.skills.voiceprint.controller.-$$Lambda$VoicePrintRegisterController$0PBSv_NZyK6-8Fp2Yb-XCuOldww
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = VoicePrintRegisterController.this.a(message);
            return a2;
        }
    });

    /* loaded from: classes3.dex */
    public enum a {
        REGISTER_STEP_IDLE,
        REGISTER_STEP_START,
        REGISTER_STEP_NICK_NAME_CONFIRM,
        REGISTER_STEP_NICK_NAME_CONFIRM_AGAIN,
        REGISTER_STEP_PROGRESS_START,
        REGISTER_STEP_PROGRESS,
        REGISTER_STEP_RESULT,
        REGISTER_STEP_END
    }

    public VoicePrintRegisterController(Context context, @NonNull VoicePrintRegisterModel voicePrintRegisterModel) {
        this.d = context;
        this.e = voicePrintRegisterModel;
        this.e.resetRegisterStatus();
        this.g = new MultiAudioPlayer(context, BasePlayer.StreamType.STREAM_TYPE_MUSIC);
        this.g.setListener(this);
    }

    public /* synthetic */ boolean a(Message message) {
        L.skill.d("%s handleMessage.msg=%s", "[VoicePrint]:", Integer.valueOf(message.what));
        switch (message.what) {
            case 1:
                if (!this.e.isRegisteringIdle()) {
                    SpeechManager.getInstance().setNewSession();
                    a(true);
                    break;
                }
                break;
            case 2:
                EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_END, new ArrayMap(1)));
                break;
        }
        return false;
    }

    public void registerStart(Bundle bundle) {
        this.e.setRegistering();
        L.speech.i("currentStep=%s", this.h.name());
        if (a.REGISTER_STEP_IDLE == this.h) {
            this.f.start();
            Intent intent = new Intent(this.d, VoicePrintRegisterActivity.class);
            if (bundle != null) {
                intent.putExtra(PARAMS_TTS, bundle.getString(PARAMS_TTS));
            }
            if (!c() || !intent.hasExtra(PARAMS_TTS)) {
                ActivityLifeCycleManager.startActivityQuietly(intent);
            } else {
                EventBusRegistry.getEventBus().post(new VoicePrintRefreshTipEvent(intent.getStringExtra(PARAMS_TTS)));
            }
        } else {
            EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_START, new ArrayMap(1)));
        }
        this.h = a.REGISTER_STEP_START;
        this.i.removeMessages(1);
        this.i.removeMessages(2);
        this.i.sendEmptyMessageDelayed(1, a);
    }

    public void registerStartFake(Bundle bundle) {
        if (VoicePrintRegisterModel.getInstance().getCurrentStep() == DomainConfig.VoicePrint.Action.UNKNOWN) {
            this.h = a.REGISTER_STEP_IDLE;
        }
        registerStart(bundle);
        this.h = a.REGISTER_STEP_PROGRESS_START;
        SpeechManager.getInstance().enterVoicePrintMode();
    }

    private void a() {
        if (this.h == a.REGISTER_STEP_START) {
            ArrayMap arrayMap = new ArrayMap(1);
            arrayMap.put(PARAMS_TTS, this.e.getNickNameConfirmTts());
            EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_SHOW_XATX_PAGE, arrayMap));
            this.h = a.REGISTER_STEP_NICK_NAME_CONFIRM;
            this.i.removeMessages(1);
            this.i.sendEmptyMessageDelayed(1, a);
            this.g.playTts(this.e.getNickNameConfirmTts());
            SpeechManager.getInstance().setUnWakeup();
        }
    }

    public void registerNickNameConfirmAgain() {
        if (this.h == a.REGISTER_STEP_NICK_NAME_CONFIRM || this.h == a.REGISTER_STEP_NICK_NAME_CONFIRM_AGAIN) {
            ArrayMap arrayMap = new ArrayMap(1);
            arrayMap.put(PARAMS_TTS, this.e.getNickNameConfirmTts());
            EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_SHOW_XATX_PAGE, arrayMap));
            this.h = a.REGISTER_STEP_NICK_NAME_CONFIRM_AGAIN;
            this.i.removeMessages(1);
            this.i.sendEmptyMessageDelayed(1, a);
        }
    }

    public void registerProgressStart() {
        ArrayMap arrayMap = new ArrayMap(3);
        arrayMap.put(PARAMS_TOTAL_CNT, String.valueOf(this.e.getTotalStep()));
        arrayMap.put(PARAMS_CNT, String.valueOf(this.e.getStep()));
        arrayMap.put(PARAMS_TTS, this.e.getStepTts());
        EventBusRegistry.getEventBus().postSticky(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_SHOW_XATX_PAGE, arrayMap));
        this.h = a.REGISTER_STEP_PROGRESS;
        this.i.removeMessages(1);
        this.i.sendEmptyMessageDelayed(1, a);
    }

    public void registerProgress() {
        ArrayMap arrayMap = new ArrayMap(3);
        arrayMap.put(PARAMS_TOTAL_CNT, String.valueOf(this.e.getTotalStep()));
        arrayMap.put(PARAMS_CNT, String.valueOf(this.e.getStep()));
        arrayMap.put(PARAMS_TTS, this.e.getStepTts());
        EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_SPEAK_XATX, arrayMap));
        if (a.REGISTER_STEP_NICK_NAME_CONFIRM == this.h || a.REGISTER_STEP_NICK_NAME_CONFIRM_AGAIN == this.h) {
            SpeechManager.getInstance().enterVoicePrintMode();
        }
        this.h = a.REGISTER_STEP_PROGRESS;
        this.i.removeMessages(1);
        this.i.sendEmptyMessageDelayed(1, a);
    }

    public void registerResult(boolean z, String str) {
        L.speech.d("%s registerResult .result=%s", "[VoicePrint]:", Boolean.valueOf(z));
        ArrayMap arrayMap = new ArrayMap(3);
        arrayMap.put("result", String.valueOf(z));
        arrayMap.put(PARAMS_NICK_NAME, this.e.getNickName());
        arrayMap.put(PARAMS_TTS, str);
        EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_RESULT, arrayMap));
        this.h = a.REGISTER_STEP_RESULT;
        this.i.removeMessages(1);
        this.i.sendEmptyMessageDelayed(1, a);
        if (z) {
            this.e.setRegisterSuccess();
        } else {
            this.e.setRegisterFail();
        }
        SpeechManager.getInstance().enterNormalMode(false);
    }

    public void a(boolean z) {
        L.skill.d("%s registerEnd, rightNow=%b", "[VoicePrint]:", Boolean.valueOf(z));
        if (!(this.h == a.REGISTER_STEP_END || this.h == a.REGISTER_STEP_IDLE)) {
            this.g.stop();
            this.f.stop();
            SpeechManager.getInstance().enterNormalMode();
        }
        this.h = a.REGISTER_STEP_END;
        this.i.removeMessages(1);
        if (z) {
            this.i.sendEmptyMessage(2);
        } else {
            this.i.sendEmptyMessageDelayed(2, b);
        }
        this.e.setRegisterEnd();
    }

    public void registerEnd() {
        a(false);
    }

    public void manualFinish() {
        L.skill.d("%s manualFinish", "[VoicePrint]:");
        this.h = a.REGISTER_STEP_END;
        this.g.stop();
        this.f.stop();
        this.i.removeCallbacksAndMessages(null);
        SpeechManager.getInstance().setUnWakeup();
        SpeechManager.getInstance().setNewSession();
        SpeechManager.getInstance().enterNormalMode();
        this.e.setRegisterEnd();
    }

    public boolean onWakeup() {
        if (a.REGISTER_STEP_START != this.h && a.REGISTER_STEP_PROGRESS_START != this.h) {
            return false;
        }
        SpeechManager.getInstance().setUnWakeup();
        return true;
    }

    private void b() {
        L.skill.d("%s stepOperation.step=%s", "[VoicePrint]:", this.h);
        switch (this.h) {
            case REGISTER_STEP_START:
                int i = this.c;
                if (i <= 0 || i > 2) {
                    this.c = 0;
                    a();
                    return;
                }
                this.g.playTts(this.d.getString(R.string.skill_voice_print_home_tip));
                return;
            case REGISTER_STEP_RESULT:
            case REGISTER_STEP_PROGRESS_START:
                registerProgressStart();
                return;
            case REGISTER_STEP_PROGRESS:
                int i2 = this.c;
                if (i2 <= 0 || i2 > 2) {
                    this.c = 0;
                    SpeechManager.getInstance().setWakeup();
                    return;
                }
                this.g.playTts(this.e.getStepTts());
                return;
            case REGISTER_STEP_NICK_NAME_CONFIRM:
                int i3 = this.c;
                if (i3 <= 0 || i3 > 2) {
                    this.c = 0;
                    SpeechManager.getInstance().setWakeup();
                    return;
                }
                this.g.playTts(this.e.getNickNameConfirmTts());
                return;
            default:
                return;
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
    public void onComplete() {
        this.c = 0;
        b();
    }

    @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
    public void onError(int i, Exception exc) {
        this.c++;
        b();
    }

    public boolean isInProgress() {
        return this.h == a.REGISTER_STEP_PROGRESS;
    }

    private boolean c() {
        Activity topActivity = ActivityLifeCycleManager.getInstance().getTopActivity();
        if (topActivity != null) {
            return topActivity instanceof VoicePrintRegisterActivity;
        }
        return false;
    }
}
