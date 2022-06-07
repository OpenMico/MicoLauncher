package com.xiaomi.micolauncher.instruciton.base;

import android.content.Context;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.NlpRequestErrorEvent;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.event.TtsPlayErrorEvent;
import com.xiaomi.micolauncher.common.skill.SpeechHandler;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterModel;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class OperationManager {
    private static OperationManager b;
    private OperationsQueue a = new OperationsQueue();
    private Context c;
    private BaseOperation d;
    private HashMap<String, String> e;

    private boolean b(String str) {
        return false;
    }

    private boolean c(String str) {
        return false;
    }

    public void onNlpStart(String str) {
    }

    public void setOriginDialogId(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            HashMap<String, String> hashMap = this.e;
            if (hashMap != null) {
                hashMap.clear();
                this.e = null;
                return;
            }
            return;
        }
        this.e = new HashMap<>(1);
        this.e.put(str, str2);
    }

    public String getOriginDialogId(String str) {
        HashMap<String, String> hashMap = this.e;
        if (hashMap != null) {
            return hashMap.get(str);
        }
        return null;
    }

    public void clearOriginDialogIdMap() {
        HashMap<String, String> hashMap = this.e;
        if (hashMap != null) {
            hashMap.clear();
            this.e = null;
        }
    }

    public static OperationManager getInstance() {
        OperationManager operationManager;
        synchronized (OperationManager.class) {
            if (b != null) {
                operationManager = b;
            } else {
                throw new IllegalStateException("OperationManager need to call init first");
            }
        }
        return operationManager;
    }

    private OperationManager(Context context) {
        this.c = context;
    }

    public static void init(Context context) {
        synchronized (OperationManager.class) {
            if (b == null) {
                b = new OperationManager(context);
                AsrTtsCard.init(context);
            }
        }
    }

    public void process(String str) {
        L.ope.i("process id=%s", str);
        List<Instruction> instructions = getInstructions(str);
        if (ContainerUtil.hasData(instructions)) {
            process(instructions);
        } else {
            L.ope.i("instructions is empty dialogId=%s", str);
        }
    }

    public void process(List<Instruction> list) {
        LinkedList<BaseOperation> parse = OperationFactory.getInstance().parse(list);
        if (ContainerUtil.hasData(parse)) {
            if (a(list)) {
                L.ope.i("interruptOperation");
                this.a.cancelProcessAll();
                this.a.addAll(parse);
            } else {
                this.a.addFirstAll(parse);
            }
            L.ope.i("process operations");
            a();
            clearOpConcurrentHashMap();
        }
    }

    public void addInstructions(String str, List<Instruction> list) {
        this.a.addInstructions(str, list);
    }

    public List<Instruction> getInstructions(String str) {
        return this.a.getInstructions(str);
    }

    public List<Instruction> removeInstructions(String str) {
        return this.a.getInstructions(str);
    }

    public void removeOperations(String str) {
        this.a.removeOperations(str);
    }

    public Instruction loadInstruction(String str, String str2) {
        return InstructionUtil.getIntention(this.a.getInstructions(str), str2);
    }

    private void a() {
        BaseOperation a = this.a.a();
        if (a == null) {
            L.ope.i("operationsQueue is empty");
            this.a.removeAllInstructions();
            return;
        }
        BaseOperation dependOpe = a.getDependOpe();
        if (dependOpe == null) {
            b();
            a.process();
        } else if (dependOpe.isFinished()) {
            if (!a.getInstruction().checkDependence(dependOpe.getInstruction().getId(), String.valueOf(dependOpe.getState() == BaseOperation.OpState.STATE_SUCCESS))) {
                b().setState(BaseOperation.OpState.STATE_FAIL);
                Logger logger = L.ope;
                logger.i("after remove baseOperation: " + a);
                a();
                return;
            }
            b();
            a.process();
        } else {
            Logger logger2 = L.ope;
            logger2.i("is not finish: " + dependOpe.getOpName());
        }
    }

    public void putToHashMap(BaseOperation baseOperation) {
        this.a.getOpConcurrentHashMap().put(baseOperation.getId(), baseOperation);
    }

    public BaseOperation getFromHashMap(String str) {
        return this.a.getOpConcurrentHashMap().get(str);
    }

    public void clearOpConcurrentHashMap() {
        this.a.getOpConcurrentHashMap().clear();
    }

    public BaseOperation nextOperation() {
        return this.a.a();
    }

    private BaseOperation b() {
        this.d = this.a.b();
        return this.d;
    }

    public BaseOperation currentOperation() {
        return this.d;
    }

    public void a(BaseOperation baseOperation, BaseOperation.OpState opState) {
        L.ope.i("notifyOpDone Operation=%s", baseOperation);
        baseOperation.getDialogId();
        a();
    }

    public void a(BaseOperation baseOperation) {
        L.ope.i("notifyOperating Operation=%s", baseOperation);
        a();
    }

    public void dumpInstructions(List<Instruction> list) {
        for (int i = 0; i < list.size(); i++) {
            L.ope.i("dump=%s", list.get(i).getFullName());
        }
    }

    public Context getContext() {
        return this.c;
    }

    public void onWakeup(boolean z, boolean z2) {
        switch (a(z, z2)) {
            case WP_HANDLE_RESULT_NO_HANDLED:
                AsrTtsCard.getInstance().onWakeup(z, z2);
                return;
            case WP_HANDLE_RESULT_ONLY_SHOW_UI:
                AsrTtsCard.getInstance().onWakeup(false, z2);
                return;
            default:
                return;
        }
    }

    private SpeechHandler.WakeupHandleResult a(boolean z, boolean z2) {
        if (VoipModel.getInstance().isVoipConnected()) {
            return SpeechHandler.WakeupHandleResult.WP_HANDLE_RESULT_ONLY_SHOW_UI;
        }
        if (!z || !VoicePrintRegisterModel.getInstance().isRegistering()) {
            return SpeechHandler.WakeupHandleResult.WP_HANDLE_RESULT_NO_HANDLED;
        }
        return SpeechHandler.WakeupHandleResult.WP_HANDLE_RESULT_HANDLED;
    }

    public void onDialogWakeup(boolean z) {
        AsrTtsCard.getInstance().onDialogWakeup(z);
    }

    public void onUnWakeup() {
        AsrTtsCard.getInstance().onUnWakeup();
    }

    public void onTtsRequest(String str) {
        AsrTtsCard.getInstance().onTtsRequest(str);
    }

    public void onNlpRequestError(String str) {
        AsrTtsCard.getInstance().onNlpRequestError(str);
        EventBusRegistry.getEventBus().post(new NlpRequestErrorEvent(str));
    }

    public void onNlpTtsRequest(String str) {
        AsrTtsCard.getInstance().onNlpTtsRequest(str);
    }

    public void onNlpEnd(String str) {
        SpeechHandler.HandleResult a = a(str);
        L.ope.d("onNlpEnd id=%s HandleResult=%s", str, a);
        switch (a) {
            case HANDLE_RESULT_NO_TTS:
            case HANDLE_RESULT_HANDLED:
                AsrTtsCard.getInstance().handleDone();
                return;
            default:
                return;
        }
    }

    private SpeechHandler.HandleResult a(String str) {
        return SpeechHandler.HandleResult.HANDLE_RESULT_NO_HANDLED;
    }

    public void onTtsPlayStart(int i) {
        AsrTtsCard.getInstance().onTtsPlayStart(i);
    }

    public void onTtsPlayEnd(String str, String str2, boolean z) {
        AsrTtsCard.getInstance().onTtsPlayEnd();
        TtsPlayEndEvent ttsPlayEndEvent = new TtsPlayEndEvent();
        ttsPlayEndEvent.query = str;
        ttsPlayEndEvent.dialogId = str2;
        ttsPlayEndEvent.interrupt = z;
        EventBusRegistry.getEventBus().post(ttsPlayEndEvent);
        L.ope.i("OperationManager onTtsPlayEnd");
    }

    public void onTtsPlayError(String str, String str2) {
        AsrTtsCard.getInstance().onTtsPlayError(str);
        TtsPlayErrorEvent ttsPlayErrorEvent = new TtsPlayErrorEvent();
        ttsPlayErrorEvent.query = str;
        ttsPlayErrorEvent.dialogId = str2;
        EventBusRegistry.getEventBus().post(ttsPlayErrorEvent);
    }

    public void onBeginningOfSpeech() {
        AsrTtsCard.getInstance().onBeginningOfSpeech();
    }

    public void onEndOfSpeech() {
        AsrTtsCard.getInstance().onEndOfSpeech();
    }

    public void onAsrResult(String str) {
        if (!b(str)) {
            AsrTtsCard.getInstance().onAsrResult(str);
        }
    }

    public void onAsrPartialResult(String str) {
        if (!c(str)) {
            AsrTtsCard.getInstance().onAsrPartialResult(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(java.util.List<com.xiaomi.ai.api.common.Instruction> r4) {
        /*
            r3 = this;
            com.xiaomi.micolauncher.instruciton.base.OperationsQueue r0 = r3.a
            boolean r0 = r0.c()
            r1 = 1
            if (r0 == 0) goto L_0x000a
            return r1
        L_0x000a:
            java.util.Iterator r4 = r4.iterator()
        L_0x000e:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x003f
            java.lang.Object r0 = r4.next()
            com.xiaomi.ai.api.common.Instruction r0 = (com.xiaomi.ai.api.common.Instruction) r0
            java.lang.String r0 = r0.getFullName()
            java.lang.String r2 = "AudioPlayer.Play"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L_0x003e
            java.lang.String r2 = "VideoPlayer.PlayList"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L_0x003e
            java.lang.String r2 = "VideoPlayer.Play"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L_0x003e
            java.lang.String r2 = "VideoPlayer.LaunchPlayApp"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x000e
        L_0x003e:
            return r1
        L_0x003f:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.instruciton.base.OperationManager.a(java.util.List):boolean");
    }

    public boolean isPhoneRunning() {
        boolean isVoipActive = VoipModel.getInstance().isVoipActive();
        L.ope.i("isVoipActive:%s", Boolean.valueOf(isVoipActive));
        return isVoipActive;
    }

    public boolean hasSpeakInstruction(String str) {
        return InstructionUtil.getIntention(getInstructions(str), AIApiConstants.SpeechSynthesizer.Speak) != null;
    }
}
