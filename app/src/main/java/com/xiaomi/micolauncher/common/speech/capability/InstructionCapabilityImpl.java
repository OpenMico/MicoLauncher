package com.xiaomi.micolauncher.common.speech.capability;

import android.os.Handler;
import android.text.TextUtils;
import com.xiaomi.ai.android.capability.InstructionCapability;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Scene;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.MultiDialogSwitcherEvent;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationFactory;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.module.setting.utils.FullDuplexSpeechHelper;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes3.dex */
public final class InstructionCapabilityImpl extends InstructionCapability {
    private static volatile InstructionCapabilityImpl a;
    private final Handler b;
    private String e;
    private String f;
    private String g;
    private String h;
    private final ArrayList<Instruction> c = new ArrayList<>();
    private final ArrayList<Instruction> d = new ArrayList<>();
    private final AtomicBoolean k = new AtomicBoolean(false);
    private final AtomicReferenceArray<SpeechRecognizer.AudioProcessType> l = new AtomicReferenceArray<>(1);
    private int j = 0;
    private boolean i = false;

    public static InstructionCapabilityImpl getInstance() {
        if (a == null) {
            synchronized (InstructionCapabilityImpl.class) {
                if (a == null) {
                    throw new IllegalStateException("you need to call init first");
                }
            }
        }
        return a;
    }

    public static InstructionCapabilityImpl init(Handler handler) {
        if (a == null) {
            synchronized (InstructionCapabilityImpl.class) {
                if (a == null) {
                    a = new InstructionCapabilityImpl(handler);
                }
            }
            return a;
        }
        throw new IllegalStateException("init method should be called only once");
    }

    private InstructionCapabilityImpl(Handler handler) {
        this.b = handler;
    }

    public SpeechRecognizer.AudioProcessType getAudioProcessType() {
        return this.l.getAndSet(0, null);
    }

    public void setSpeechDialogId(String str) {
        this.f = this.g;
        this.g = str;
    }

    public String getSpeechDialogId() {
        return this.g;
    }

    public String getPreviousDialogId() {
        return this.f;
    }

    @Override // com.xiaomi.ai.android.capability.InstructionCapability
    public boolean process(Instruction instruction) {
        L.capability.i("instruction=%s", instruction.toString());
        this.d.add(instruction);
        if (!a(instruction)) {
            this.c.add(instruction);
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean a(Instruction instruction) {
        char c;
        String fullName = instruction.getFullName();
        String dialogId = InstructionUtil.getDialogId(instruction);
        switch (fullName.hashCode()) {
            case -1658608874:
                if (fullName.equals(AIApiConstants.Dialog.EnterTemporaryContinuousDialog)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1625763229:
                if (fullName.equals(AIApiConstants.Dialog.TurnOffContinuousDialog)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1049170997:
                if (fullName.equals(AIApiConstants.Nlp.EventACK)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -788476173:
                if (fullName.equals(AIApiConstants.System.TruncationNotification)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -580464805:
                if (fullName.equals(AIApiConstants.Dialog.ExitContinuousDialog)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -529211283:
                if (fullName.equals(AIApiConstants.Nlp.FinishAnswer)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -349709590:
                if (fullName.equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -291973127:
                if (fullName.equals(AIApiConstants.Dialog.TurnOnContinuousDialog)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 274747385:
                if (fullName.equals(AIApiConstants.Dialog.Finish)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 295840438:
                if (fullName.equals(AIApiConstants.Scene.Enter)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 978198135:
                if (fullName.equals(AIApiConstants.SpeechSynthesizer.FinishSpeakStream)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 986531076:
                if (fullName.equals(AIApiConstants.Nlp.StartAnswer)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1327948931:
                if (fullName.equals(AIApiConstants.SpeechRecognizer.RecognizeResult)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1602877600:
                if (fullName.equals(AIApiConstants.General.Push)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1631722339:
                if (fullName.equals(AIApiConstants.SpeechRecognizer.ExpectSpeech)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1963775772:
                if (fullName.equals(AIApiConstants.SpeechRecognizer.StopCapture)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return b(instruction);
            case 1:
                QueryLatency.getInstance().setBrainEndPacketMs();
                this.b.sendEmptyMessage(9);
                return true;
            case 2:
                SpeechRecognizer.ExpectSpeech expectSpeech = (SpeechRecognizer.ExpectSpeech) instruction.getPayload();
                if (expectSpeech.getPostBack().isPresent()) {
                    SpeechRecognizer.AudioProcessType enable = expectSpeech.getPostBack().get().getAudioProcess().getEnable();
                    L.capability.i("InstructionCapabilityImpl preProcess audioProcessType=%s", enable);
                    this.l.set(0, enable);
                }
                if (expectSpeech.isRenewSessionAfterTimeout().isPresent() && expectSpeech.isRenewSessionAfterTimeout().get().booleanValue()) {
                    SpeechManager.getInstance().setNewSessionWhenEmptyAsr();
                }
                if (InstructionUtil.hasDependence(instruction)) {
                    return false;
                }
                this.b.sendEmptyMessage(4002);
                return true;
            case 3:
                f(instruction);
                return true;
            case 4:
                g(instruction);
                return false;
            case 5:
                FullDuplexSpeechHelper.enable(3);
                EventBusRegistry.getEventBus().post(new MultiDialogSwitcherEvent());
                return true;
            case 6:
                a();
                return true;
            case 7:
                FullDuplexSpeechHelper.disable(3);
                this.b.removeMessages(3001);
                this.b.sendEmptyMessage(3001);
                return true;
            case '\b':
                SpeechManager.getInstance().exitContinuousDialog();
                return true;
            case '\t':
                Scene.Enter enter = (Scene.Enter) instruction.getPayload();
                Scene.SceneType type = enter.getType();
                int i = 30;
                if (enter.getMicDuration().isPresent()) {
                    i = enter.getMicDuration().get().intValue();
                }
                SpeechContextHelper.sceneType = type;
                SpeechManager.getInstance().enterScene(i, type.toString());
                return true;
            case '\n':
                this.i = true;
                c(instruction);
                return true;
            case 11:
                d(instruction);
                return true;
            case '\f':
                return true;
            case '\r':
                if (dialogId.equals(this.g)) {
                    b();
                }
                if (dialogId.equals(this.h)) {
                    c();
                }
                e(instruction);
                this.c.clear();
                this.d.clear();
                this.i = false;
                this.j = 0;
                return true;
            case 14:
                ArrayList arrayList = new ArrayList();
                arrayList.add(instruction);
                OperationManager.getInstance().addInstructions(dialogId, arrayList);
                this.b.obtainMessage(2004, dialogId).sendToTarget();
                return true;
            case 15:
                this.b.removeMessages(8);
                this.b.sendEmptyMessage(8);
                return true;
            default:
                if (this.i && dialogId.equals(this.g) && OperationFactory.getInstance().containsNamespace(instruction.getNamespace())) {
                    this.j++;
                    L.capability.i("InstructionCapabilityImpl preProcess validInstruction=%s", fullName);
                }
                return false;
        }
    }

    private void a() {
        this.b.removeMessages(3000);
        this.b.sendEmptyMessage(3000);
    }

    private boolean b(Instruction instruction) {
        SpeechRecognizer.RecognizeResult recognizeResult = (SpeechRecognizer.RecognizeResult) instruction.getPayload();
        List<SpeechRecognizer.RecognizeResultItem> results = recognizeResult.getResults();
        if (results.size() <= 0) {
            return true;
        }
        SpeechRecognizer.RecognizeResultItem recognizeResultItem = results.get(0);
        if (recognizeResult.isFinal()) {
            this.b.obtainMessage(5, recognizeResultItem.getText()).sendToTarget();
            this.b.obtainMessage(7, instruction).sendToTarget();
            QueryLatency.getInstance().setAsrFinalResult(recognizeResultItem.getText(), recognizeResultItem.getBeginOffset(), recognizeResultItem.getEndOffset());
            this.c.clear();
            return false;
        }
        QueryLatency.getInstance().compareSetAsrFirstPartialMs();
        if (!TextUtils.isEmpty(recognizeResultItem.getText())) {
            QueryLatency.getInstance().compareSetAsrResultPartialMs();
            this.b.obtainMessage(4, recognizeResultItem.getText()).sendToTarget();
        }
        this.b.obtainMessage(6, instruction).sendToTarget();
        return true;
    }

    private void c(Instruction instruction) {
        this.e = null;
        this.g = InstructionUtil.getDialogId(instruction);
        this.h = this.g;
        QueryLatency.getInstance().setNlpTimeStamp(this.g);
        this.b.obtainMessage(2003, this.g).sendToTarget();
    }

    private void d(Instruction instruction) {
        QueryLatency.getInstance().setNlpFinishMs();
    }

    private void e(Instruction instruction) {
        String dialogId = InstructionUtil.getDialogId(instruction);
        ArrayList<Instruction> arrayList = new ArrayList<>(this.c);
        a(dialogId, arrayList);
        OperationManager.getInstance().addInstructions(dialogId, arrayList);
        QueryLatency.getInstance().setDialogFinishMs();
        this.b.obtainMessage(2004, dialogId).sendToTarget();
    }

    private void a(String str, ArrayList<Instruction> arrayList) {
        ArrayList arrayList2 = new ArrayList(this.d);
        if (TextUtils.equals(str, PersonalizeExecution.getInstance().getEventId())) {
            if (!PersonalizeExecution.getInstance().findExpectSpeech(arrayList2)) {
                L.capability.d("the same dialogId=%s and cmd Response has not open mic instruction", str);
                PersonalizeExecution.getInstance().filterAndSetDependence(arrayList);
                boolean isProcessIngCmd = PersonalizeExecution.getInstance().isProcessIngCmd();
                L.capability.d("本轮same dialogId cmd指令集是否依赖完毕=%s", Boolean.valueOf(!isProcessIngCmd));
                this.k.set(isProcessIngCmd);
            }
        } else if (this.k.getAndSet(false) && !PersonalizeExecution.getInstance().findSelectInstruction(arrayList2)) {
            L.capability.d("清除了本轮的cmd指令集 dialogId=%s", str);
            PersonalizeExecution.getInstance().clearCmdList();
        } else if (PersonalizeExecution.getInstance().isProcessIngCmd() && !PersonalizeExecution.getInstance().findExpectSpeech(arrayList2)) {
            L.capability.d("the different dialogId=%s and cmd Response has not open mic instruction", str);
            PersonalizeExecution.getInstance().filterAndSetDependence(arrayList);
            boolean isProcessIngCmd2 = PersonalizeExecution.getInstance().isProcessIngCmd();
            L.capability.d("本轮different dialogId cmd指令集是否依赖完毕=%s", Boolean.valueOf(!isProcessIngCmd2));
            this.k.set(isProcessIngCmd2);
        }
    }

    private void f(Instruction instruction) {
        QueryLatency.getInstance().setSpeechFinishSpeakStreamMs();
    }

    private void g(Instruction instruction) {
        String dialogId = InstructionUtil.getDialogId(instruction);
        if (a(dialogId)) {
            SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) instruction.getPayload();
            String text = speak.getText();
            L.monitor.d("TtsContent.dialog_id=%s.content=%s", dialogId, text);
            this.b.obtainMessage(1007, text).sendToTarget();
            if (speak.getUrl().isPresent()) {
                QueryLatency.getInstance().setSpeechSpeakWithUrlMs();
                this.b.obtainMessage(1003, speak.getUrl().get()).sendToTarget();
                return;
            }
            QueryLatency.getInstance().setSpeechSpeakWithStreamMs();
            return;
        }
        L.speech.d("not first speak instruction");
    }

    private boolean a(String str) {
        if (this.e != null || !str.equals(this.g)) {
            return false;
        }
        this.e = str;
        return true;
    }

    private void b() {
        boolean isDialogMode = SpeechManager.getInstance().isDialogMode();
        boolean d = d();
        if (!isDialogMode) {
            return;
        }
        if (this.j <= 0) {
            SpeechManager.getInstance().onQueryResultInvalid();
        } else if (!d) {
            SpeechManager.getInstance().onQueryResultNoTts();
        }
    }

    private void c() {
        boolean d = d();
        boolean e = e();
        if (!d || !e) {
            AsrTtsCard.getInstance().handleDone();
        }
    }

    private boolean d() {
        return InstructionUtil.getIntention(this.c, AIApiConstants.SpeechSynthesizer.Speak) != null;
    }

    private boolean e() {
        return InstructionUtil.getIntention(this.c, AIApiConstants.Template.Toast) != null;
    }
}
