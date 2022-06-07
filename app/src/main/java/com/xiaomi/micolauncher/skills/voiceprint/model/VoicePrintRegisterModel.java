package com.xiaomi.micolauncher.skills.voiceprint.model;

import android.content.Context;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterProgressEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class VoicePrintRegisterModel {
    private static volatile VoicePrintRegisterModel f;
    private String c;
    private int d;
    private int e;
    private VoicePrintRegisterController j;
    private DomainConfig.VoicePrint.Action k;
    private String a = "";
    private String b = "";
    private RegisterStatus g = RegisterStatus.REGISTER_IDLE;
    private RegisterType i = RegisterType.REGISTER_TYPE_VOICE_RECOGNIZE;
    private AtomicBoolean h = new AtomicBoolean(false);

    /* loaded from: classes3.dex */
    public enum RegisterStatus {
        REGISTER_IDLE,
        REGISTER_ING,
        REGISTER_FAIL,
        REGISTER_SUCCESS
    }

    /* loaded from: classes3.dex */
    public enum RegisterType {
        REGISTER_TYPE_UNKNOWN,
        REGISTER_TYPE_VOICE_RECOGNIZE,
        REGISTER_TYPE_VOICE_PAYMENT
    }

    public static VoicePrintRegisterModel getInstance() {
        if (f == null) {
            synchronized (VoicePrintRegisterModel.class) {
                if (f == null) {
                    f = new VoicePrintRegisterModel();
                }
            }
        }
        return f;
    }

    private VoicePrintRegisterModel() {
    }

    public void setNickName(String str) {
        this.a = str;
    }

    public String getNickName() {
        return this.a;
    }

    public String getNickNameConfirmTts() {
        return this.b;
    }

    public void setNickNameConfirmTts(String str) {
        this.b = str;
    }

    public void setProgressStepInfo(int i, int i2, String str) {
        this.e = i;
        this.d = i2;
        this.c = str;
    }

    public int getTotalStep() {
        return this.e;
    }

    public int getStep() {
        return this.d;
    }

    public String getStepTts() {
        return this.c;
    }

    public String getRegisterStatus() {
        return this.g.name();
    }

    public void resetRegisterStatus() {
        this.g = RegisterStatus.REGISTER_IDLE;
    }

    public void setRegisterFail() {
        this.g = RegisterStatus.REGISTER_FAIL;
    }

    public void setRegisterSuccess() {
        this.g = RegisterStatus.REGISTER_SUCCESS;
    }

    public void setRegisterEnd() {
        if (this.g == RegisterStatus.REGISTER_ING) {
            this.g = RegisterStatus.REGISTER_FAIL;
        }
        this.i = RegisterType.REGISTER_TYPE_VOICE_RECOGNIZE;
    }

    public void setRegistering() {
        if (!this.h.get()) {
            this.g = RegisterStatus.REGISTER_ING;
        }
    }

    public boolean isRegistering() {
        return this.g == RegisterStatus.REGISTER_ING;
    }

    public boolean isRegisteringIdle() {
        return this.g == RegisterStatus.REGISTER_IDLE;
    }

    public void clearCancel() {
        this.h.set(false);
    }

    public boolean isCanceled() {
        return this.h.get();
    }

    public RegisterType getRegisterType() {
        return this.i;
    }

    public void startRegisterRecognize(Context context) {
        this.g = RegisterStatus.REGISTER_IDLE;
        this.i = RegisterType.REGISTER_TYPE_VOICE_RECOGNIZE;
        SpeechManager.getInstance().nlpTtsRequest(context.getString(R.string.skill_voice_print_register), true);
        clearCancel();
    }

    public void startRegisterPayment(Context context) {
        this.g = RegisterStatus.REGISTER_IDLE;
        this.i = RegisterType.REGISTER_TYPE_VOICE_PAYMENT;
        SpeechManager.getInstance().nlpTtsRequest(context.getString(R.string.skill_voice_print_register_payment), true);
        clearCancel();
    }

    public void cancelRegisterRecognize(final Context context) {
        this.h.set(true);
        EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_CANCEL, null));
        ThreadUtil.getWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voiceprint.model.-$$Lambda$VoicePrintRegisterModel$XqnVSzwVkgQbX0moj5pa9dvAnBQ
            @Override // java.lang.Runnable
            public final void run() {
                VoicePrintRegisterModel.this.b(context);
            }
        }, 500L);
    }

    public /* synthetic */ void b(Context context) {
        setRegisterFail();
        SpeechManager.getInstance().stopTts();
        SpeechManager.getInstance().ttsRequest(context.getString(R.string.skill_voice_print_recognize_exit_tts), true);
    }

    public void cancelRegisterPayment(final Context context) {
        this.h.set(true);
        EventBusRegistry.getEventBus().post(new VoicePrintRegisterProgressEvent(VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_CANCEL, null));
        ThreadUtil.getWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voiceprint.model.-$$Lambda$VoicePrintRegisterModel$Tpb0iDz6DBiWwM0KuJHg4IU0IU0
            @Override // java.lang.Runnable
            public final void run() {
                VoicePrintRegisterModel.this.a(context);
            }
        }, 500L);
    }

    public /* synthetic */ void a(Context context) {
        setRegisterFail();
        SpeechManager.getInstance().stopTts();
        SpeechManager.getInstance().ttsRequest(context.getString(R.string.skill_voice_print_payment_exit_tts), true);
    }

    public VoicePrintRegisterController getController() {
        if (this.j == null) {
            this.j = new VoicePrintRegisterController(OperationManager.getInstance().getContext(), this);
        }
        return this.j;
    }

    public void setController(VoicePrintRegisterController voicePrintRegisterController) {
        this.j = voicePrintRegisterController;
    }

    public DomainConfig.VoicePrint.Action getCurrentStep() {
        if (this.k == null) {
            this.k = DomainConfig.VoicePrint.Action.UNKNOWN;
        }
        return this.k;
    }

    public void setCurrentStep(DomainConfig.VoicePrint.Action action) {
        this.k = action;
    }
}
