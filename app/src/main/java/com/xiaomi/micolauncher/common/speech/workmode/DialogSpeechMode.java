package com.xiaomi.micolauncher.common.speech.workmode;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.ai.speaker.vpmclient.VpmClient;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class DialogSpeechMode extends BaseSpeechWorkMode {
    private static final int c = (int) TimeUnit.SECONDS.toMillis(30);
    private static final int d = (int) TimeUnit.MINUTES.toMillis(10);
    private static final int e = (int) TimeUnit.SECONDS.toMillis(7);
    private static final long f = TimeUnit.MINUTES.toMillis(1);
    private static final long g = TimeUnit.SECONDS.toMillis(1);
    private static final long h = TimeUnit.SECONDS.toMillis(1);
    private boolean j;
    private boolean k;
    private int l;
    private long n;
    private boolean o;
    private int p;
    private boolean r;
    private long i = c;
    private Handler m = new Handler(ThreadUtil.getLightWorkHandler().getLooper()) { // from class: com.xiaomi.micolauncher.common.speech.workmode.DialogSpeechMode.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            L.speech.d("%s handleMessage.msg.what=%s", "[DialogSpeechMode]:", Integer.valueOf(message.what));
            switch (message.what) {
                case 2:
                    SpeechManager.getInstance().dialogClose(true);
                    return;
                case 3:
                default:
                    return;
            }
        }
    };
    private boolean q = false;
    private String t = "";
    private final boolean u = SpeechConfig.dialogForceAllDomain();
    private boolean s = false;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum a {
        REASON_NONE,
        REASON_TIMEOUT,
        REASON_NO_ASR,
        REASON_EXIT_CMD,
        REASON_NO_NLP
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public boolean isDialog() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public String name() {
        return "Dialog";
    }

    public DialogSpeechMode(VpmClient vpmClient, BaseSpeechEngine baseSpeechEngine) {
        super(vpmClient, baseSpeechEngine);
        this.a.vpmSetWaitAsrTimeout(8000);
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void clearNewSession() {
        super.clearNewSession();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void release() {
        this.m.removeCallbacksAndMessages(null);
        super.release();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public boolean wakeupHandle() {
        super.wakeupHandle();
        this.j = false;
        if (!this.q) {
            this.p = VideoMediaSession.getInstance().getPlayStatus();
        }
        this.q = true;
        if (SpeechContextHelper.isAlarmRunningWhenPeak()) {
            SpeechContextHelper.clearCachedContext();
            PromptSoundPlayer.getInstance().play(R.raw.close_alarm);
            SpeechManager.getInstance().setUnWakeup();
            return false;
        }
        OperationManager.getInstance().onWakeup(true, !SpeechConfig.getWakeupSound());
        this.m.removeCallbacksAndMessages(null);
        this.l = 0;
        this.k = false;
        this.o = false;
        this.r = true;
        this.t = "";
        SpeechContextHelper.sceneType = null;
        L.speech.d("%s wakeupHandle", "[DialogSpeechMode]:");
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void setWakeup() {
        if (!this.k || SpeechConfig.showWakeupViewInSceneMode()) {
            OperationManager.getInstance().onDialogWakeup(this.s);
            this.s = false;
            return;
        }
        AsrTtsCard.getInstance().onNlpTtsRequest("");
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void setUnWakeup() {
        super.setUnWakeup();
        this.q = false;
        this.b.forceStop();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    protected void start() {
        a(g);
        int i = this.p;
        this.m.sendEmptyMessageDelayed(3, h);
        this.q = true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public boolean isDialogContinuous() {
        return this.j;
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public boolean supportOpenMicWhenPlaying() {
        return this.k;
    }

    private void a(boolean z, a aVar) {
        L.speech.d("%s setDialogStatus.dialogContinue=%b, reason=%s", "[DialogSpeechMode]:", Boolean.valueOf(z), aVar);
        if (!z) {
            switch (aVar) {
                case REASON_NO_ASR:
                    if (this.j) {
                        StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.no_asr);
                        StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.exit_timeout, String.valueOf(2));
                        break;
                    }
                    break;
                case REASON_TIMEOUT:
                    StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.exit_timeout, String.valueOf(1));
                    d();
                    OperationManager.getInstance().onUnWakeup();
                    break;
                case REASON_EXIT_CMD:
                    StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.exit_cmd);
                    d();
                    this.l++;
                    break;
            }
            if (this.l > 0) {
                StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.chat_count, String.valueOf(this.l));
            }
        }
        this.j = z;
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void asrResultHandle(String str) {
        if (this.u && !this.k) {
            enterScene(30, "");
        }
        this.r = TextUtils.isEmpty(str);
        if (this.r) {
            L.speech.i("%s, asrResultHandle asr empty, do nothing", "[DialogSpeechMode]:");
        } else {
            super.asrResultHandle(str);
        }
    }

    private void a(boolean z) {
        this.q = false;
        SpeechManager.getInstance().setWakeup();
        a(true, a.REASON_NONE);
        if (z) {
            StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.wakeup);
            c();
        }
        L.speech.i("%s, startNextQuery.restMillis=%d", "[DialogSpeechMode]:", Long.valueOf(f()));
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void onQueryResultInvalid() {
        L.speech.d("%s onQueryResultInvalid.dialogContinue=%b, .sceneType=%s", "[DialogSpeechMode]:", Boolean.valueOf(this.j), this.t);
        if (!this.r) {
            this.s = true;
            AsrTtsCard.getInstance().onInvalidNlp();
        }
        if (e()) {
            a(false);
        } else if (this.k) {
            a(false, a.REASON_TIMEOUT);
        }
        super.onQueryResultInvalid();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void onQueryResultNoTts() {
        L.speech.d("%s onQueryResultNoTts.dialogContinue=%b, .forceExit=%b, .sceneType=%s", "[DialogSpeechMode]:", Boolean.valueOf(this.j), Boolean.valueOf(this.o), this.t);
        if (!this.o) {
            this.l++;
            if (this.k) {
                a(true);
            } else {
                a(false, a.REASON_NONE);
            }
        }
        super.onQueryResultNoTts();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void ttsPlayFinishHandle() {
        L.speech.d("%s ttsPlayFinishHandle.dialogContinue=%b, .sceneType=%s", "[DialogSpeechMode]:", Boolean.valueOf(this.j), this.t);
        this.l++;
        if (this.j) {
            a(true);
        } else if (this.k) {
            AsrTtsCard.getInstance().onUnWakeup();
        }
        super.ttsPlayFinishHandle();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void dialogOpen() {
        StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.wakeup);
        a(true, a.REASON_NONE);
        this.o = false;
        super.dialogOpen();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void dialogClose(boolean z) {
        if (z) {
            a(false, a.REASON_TIMEOUT);
            OperationManager.getInstance().onUnWakeup();
        } else {
            a(false, a.REASON_EXIT_CMD);
            this.a.vpmSetUnWakeup();
        }
        this.o = true;
        super.dialogClose(z);
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void shortAudioFinish() {
        super.shortAudioFinish();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void onRequestError() {
        this.m.removeCallbacksAndMessages(null);
        super.onRequestError();
    }

    private void c() {
        L.speech.d("%s resetDialogTimer", "[DialogSpeechMode]:");
        this.n = System.currentTimeMillis();
        this.m.removeMessages(1);
        this.m.sendEmptyMessageDelayed(1, this.i);
    }

    private void d() {
        this.m.removeMessages(1);
        L.speech.d("%s exitPlayingScene", "[DialogSpeechMode]:");
    }

    private boolean e() {
        return this.m.hasMessages(1);
    }

    private long f() {
        return this.i - (System.currentTimeMillis() - this.n);
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void enterScene(int i, String str) {
        int millis = (int) TimeUnit.SECONDS.toMillis(i);
        if (millis < e || millis > d) {
            this.i = c;
        } else {
            this.i = millis;
        }
        this.k = true;
        this.t = str;
        a(true, a.REASON_NONE);
        c();
        super.enterScene(i, str);
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public String getSceneType() {
        return this.t;
    }
}
