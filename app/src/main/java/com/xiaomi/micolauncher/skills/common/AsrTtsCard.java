package com.xiaomi.micolauncher.skills.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.skill.SpeechHandler;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.skills.baike.controller.uievent.BaikeQueryEvent;
import com.xiaomi.micolauncher.skills.common.view.WakeupViewForBigScreen;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AsrTtsCard {
    @SuppressLint({"StaticFieldLeak"})
    private static AsrTtsCard a;
    private WakeupHelper b;
    private a c;
    private boolean d;
    private Context e;
    private boolean f;

    public boolean onBeginningOfSpeech() {
        return false;
    }

    public boolean onEndOfSpeech() {
        return false;
    }

    public boolean onNlpRequest(String str) {
        return true;
    }

    private AsrTtsCard(Context context) {
        a aVar;
        L.speech.i("create AsrTtsCard", "[TtsCard]:");
        this.e = context.getApplicationContext();
        this.b = new WakeupViewForBigScreen(context);
        if (!(a == null || (aVar = this.c) == null)) {
            aVar.removeCallbacksAndMessages(null);
        }
        WakeupHelper wakeupHelper = this.b;
        if (wakeupHelper != null) {
            wakeupHelper.autoDismiss(0);
        }
        this.c = new a();
    }

    public static void init(Context context) {
        a = new AsrTtsCard(context);
    }

    public static AsrTtsCard getInstance() {
        if (a == null) {
            init(MicoApplication.getGlobalContext());
        }
        return a;
    }

    /* loaded from: classes3.dex */
    public static final class a extends Handler {
        private int a;

        private a() {
            super(Looper.getMainLooper());
            this.a = 5;
        }

        private void a(Object obj) {
            boolean z;
            String speakingTts = SpeechManager.getInstance().getSpeakingTts();
            boolean z2 = AsrTtsCard.getInstance().f;
            WakeupHelper wakeupHelper = AsrTtsCard.getInstance().b;
            if (wakeupHelper == null) {
                L.speech.e("nlpHandle : wakeup view not set");
                return;
            }
            if (obj instanceof String) {
                speakingTts = (String) obj;
            }
            if (!TextUtils.isEmpty(speakingTts)) {
                if (z2) {
                    z = wakeupHelper.showNlp(speakingTts);
                } else {
                    z = wakeupHelper.aiSpeakerTts(speakingTts);
                }
                L.skill.i("%s wake up wakeupRequest=%s , showFullPage=%s", "[TtsCard]:", Boolean.valueOf(z2), Boolean.valueOf(z));
                if (z) {
                    EventBusRegistry.getEventBus().post(new BaikeQueryEvent(null, speakingTts, null, speakingTts));
                    wakeupHelper.autoDismiss(0);
                }
            } else if (z2) {
                wakeupHelper.autoDismiss(0);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i;
            int i2 = message.what;
            super.handleMessage(message);
            boolean z = false;
            L.speech.d("%s mLastEvent=%d, event=%d", "[TtsCard]:", Integer.valueOf(this.a), Integer.valueOf(message.what));
            WakeupHelper wakeupHelper = AsrTtsCard.getInstance().b;
            if (wakeupHelper == null) {
                L.skill.e("wakeup view not set");
                return;
            }
            switch (message.what) {
                case 0:
                    wakeupHelper.autoDismiss(100);
                    i = i2;
                    break;
                case 1:
                    if (message.obj == null || 100 != ((Integer) message.obj).intValue()) {
                        wakeupHelper.show(true);
                    } else {
                        wakeupHelper.show(true, true);
                    }
                    i = i2;
                    break;
                case 2:
                    if (message.obj instanceof String) {
                        String str = (String) message.obj;
                        if (message.arg1 > 0) {
                            z = true;
                        }
                        wakeupHelper.showAsr(str, z);
                    }
                    i = i2;
                    break;
                case 3:
                    wakeupHelper.waitNlp();
                    i = i2;
                    break;
                case 4:
                    a(message.obj);
                    i = i2;
                    break;
                case 5:
                    if (this.a != 6) {
                        wakeupHelper.autoDismiss(0);
                    }
                    i = i2;
                    break;
                case 6:
                    i = this.a;
                    if (i == 4 || i == 8) {
                        wakeupHelper.ttsStart();
                        i = i2;
                        break;
                    }
                    break;
                case 7:
                    if (this.a >= 4) {
                        wakeupHelper.ttsEnd();
                    }
                    AsrTtsCard.getInstance().f = true;
                    i = i2;
                    break;
                case 8:
                    wakeupHelper.stepToTtsAnimation();
                    i = i2;
                    break;
                case 9:
                    wakeupHelper.autoDismiss(0);
                    i = i2;
                    break;
                case 10:
                    L.speech.d("%s show fade asr text on invalid nlp", "[TtsCard]:");
                    wakeupHelper.showAsr(null, false, true);
                    i = i2;
                    break;
                case 11:
                    wakeupHelper.wakeUpCancel();
                    i = i2;
                    break;
                default:
                    i = i2;
                    break;
            }
            this.a = i;
        }

        int a() {
            return this.a;
        }
    }

    public void handleDone() {
        int a2 = this.c.a();
        L.speech.d("%s handleDone.status=%s", "[TtsCard]:", Integer.valueOf(a2));
        if (a2 == 2 || a2 == 3 || a2 == 7) {
            this.c.removeMessages(3);
            if (SpeechManager.getInstance().isDialogContinuous()) {
                L.speech.d("%s handleDone continueDialog=true do nothing", "[TtsCard]:");
            } else {
                this.c.sendEmptyMessageDelayed(0, 500L);
            }
        }
    }

    public void showTtsUi() {
        this.c.removeMessages(3);
        this.c.sendEmptyMessage(8);
    }

    public void onWakeupWithoutSound() {
        L.speech.d("%s onWakeupWithoutSound", "[TtsCard]:");
        this.c.removeCallbacksAndMessages(null);
        this.f = true;
        if (Screen.getInstance().isInteractive()) {
            this.c.sendEmptyMessage(1);
            return;
        }
        Screen.getInstance().onResume();
        this.c.sendEmptyMessageDelayed(1, 300L);
    }

    public void onWakeupCancel() {
        L.speech.d("%s onWakeupCancel", "[TtsCard]:");
        this.c.removeCallbacksAndMessages(null);
        if (Screen.getInstance().isInteractive()) {
            this.c.sendEmptyMessage(11);
        } else {
            this.c.sendEmptyMessageDelayed(11, 300L);
        }
        this.c.sendEmptyMessageDelayed(5, TimeUnit.SECONDS.toMillis(1L));
    }

    public SpeechHandler.WakeupHandleResult onWakeup(final boolean z, final boolean z2) {
        L.speech.d("%s onWakeup, isVoiceWakeup=%b, mute=%b", "[TtsCard]:", Boolean.valueOf(z), Boolean.valueOf(z2));
        this.c.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.common.-$$Lambda$AsrTtsCard$7R1aNWZVL4skFAdvSntsAglhGoo
            @Override // java.lang.Runnable
            public final void run() {
                AsrTtsCard.this.a(z, z2);
            }
        });
        this.f = true;
        if (!z) {
            this.c.obtainMessage(1, 100).sendToTarget();
        }
        return SpeechHandler.WakeupHandleResult.WP_HANDLE_RESULT_HANDLED;
    }

    public /* synthetic */ void a(boolean z, boolean z2) {
        WakeupHelper wakeupHelper = this.b;
        if (wakeupHelper != null) {
            wakeupHelper.playWakeup(!z, z2);
        }
    }

    public boolean onUnWakeup() {
        L.speech.d("%s onUnWakeup", "[TtsCard]:");
        this.c.removeCallbacksAndMessages(null);
        this.c.obtainMessage(5).sendToTarget();
        return true;
    }

    public SpeechHandler.WakeupHandleResult onDialogWakeup() {
        L.skill.i("%s onDialogWakeup", "[TtsCard]:");
        return onDialogWakeup(false);
    }

    public SpeechHandler.WakeupHandleResult onDialogWakeup(boolean z) {
        boolean hasMessages = this.c.hasMessages(10);
        L.skill.i("%s onDialogWakeup .queryInvalid=%s", "[TtsCard]:", Boolean.valueOf(z));
        this.c.removeCallbacksAndMessages(null);
        if (hasMessages) {
            this.c.obtainMessage(10).sendToTarget();
        }
        this.f = true;
        this.c.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.common.-$$Lambda$AsrTtsCard$NlBBi5hsQmOgo_SsPH7FPq-AeYs
            @Override // java.lang.Runnable
            public final void run() {
                AsrTtsCard.this.a();
            }
        });
        if (z) {
            a aVar = this.c;
            aVar.sendMessageDelayed(aVar.obtainMessage(1, 100), 3000L);
        } else {
            this.c.obtainMessage(1, 100).sendToTarget();
        }
        return SpeechHandler.WakeupHandleResult.WP_HANDLE_RESULT_HANDLED;
    }

    public /* synthetic */ void a() {
        WakeupHelper wakeupHelper = this.b;
        if (wakeupHelper != null) {
            wakeupHelper.finishWakeup();
        }
    }

    public boolean onNlpTtsRequest(String str) {
        L.skill.d("%s onNlpTtsRequest.nlp=%s", "[TtsCard]:", str);
        this.c.removeCallbacksAndMessages(null);
        this.c.obtainMessage(5).sendToTarget();
        this.f = false;
        return true;
    }

    public boolean onTtsRequest(String str) {
        L.skill.d("%s onTtsRequest.tts=%s", "[TtsCard]:", str);
        this.c.removeCallbacksAndMessages(null);
        this.f = false;
        this.c.obtainMessage(4, str).sendToTarget();
        return true;
    }

    public boolean onInvalidNlp() {
        L.speech.d("%s onInvalidNlp", "[TtsCard]:");
        this.c.removeMessages(2);
        this.c.obtainMessage(10).sendToTarget();
        return true;
    }

    public boolean onAsrResult(String str) {
        return onAsrResult(str, 500L, true);
    }

    public boolean onAsrResult(String str, long j, boolean z) {
        L.speech.d("%s onAsrResult=%s", "[TtsCard]:", str);
        if (TextUtils.isEmpty(str)) {
            this.c.obtainMessage(0, "").sendToTarget();
            return true;
        }
        this.c.removeMessages(1);
        this.c.removeMessages(2);
        this.c.obtainMessage(2, z ? 1 : 0, 0, str).sendToTarget();
        this.c.obtainMessage(2, 0, 0, str).sendToTarget();
        a aVar = this.c;
        aVar.sendMessageDelayed(aVar.obtainMessage(3), j);
        return true;
    }

    public boolean onAsrPartialResult(String str) {
        L.speech.d("%s onAsrPartialResult=%s", "[TtsCard]:", str);
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        this.c.removeMessages(1);
        this.c.removeMessages(2);
        this.c.obtainMessage(2, 0, 0, str).sendToTarget();
        return true;
    }

    public void onTtsContent(String str) {
        L.speech.d("%s onTtsContent", "[TtsCard]:");
        this.c.removeMessages(3);
        this.c.obtainMessage(4, str).sendToTarget();
    }

    public void onTtsPlayStart(int i) {
        L.speech.d("%s onTtsPlayStart", "[TtsCard]:");
        this.c.obtainMessage(6).sendToTarget();
    }

    public void onTtsPlayEnd() {
        L.speech.d("%s onTtsPlayEnd", "[TtsCard]:");
        this.c.obtainMessage(7).sendToTarget();
    }

    public void dismissForce() {
        a aVar = this.c;
        if (aVar != null) {
            aVar.obtainMessage(9, "").sendToTarget();
        }
    }

    public void hideTts() {
        if (this.c == null) {
            return;
        }
        if (SpeechManager.getInstance().isDialogMode()) {
            L.speech.d("%s hideTts, continueDialog=true do nothing", "[TtsCard]:");
        } else {
            this.c.obtainMessage(0, "").sendToTarget();
        }
    }

    public void onNlpRequestError(String str) {
        L.speech.i("onNlpRequestError,content=%s", str);
    }

    public void onTtsPlayError(String str) {
        L.speech.i("onTtsPlayError,content=%s", str);
    }

    public boolean getHalfWakeupViewFlag() {
        return this.d && StatUtils.getHalfWakeupViewFlag(this.e);
    }

    public void allowHalfWakeupView() {
        this.d = true;
    }

    public void forbidHalfWakeupView() {
        this.d = false;
    }
}
