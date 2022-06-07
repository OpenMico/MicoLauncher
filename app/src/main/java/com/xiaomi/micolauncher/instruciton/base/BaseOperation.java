package com.xiaomi.micolauncher.instruciton.base;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.api.common.InstructionDependence;
import com.xiaomi.ai.api.common.InstructionHeader;
import com.xiaomi.common.Optional;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.personalize.event.ThirdPartAppOpeEvent;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public abstract class BaseOperation<T extends Instruction> {
    boolean a;
    private BaseOperation b;
    private boolean d;
    private List<Instruction> f;
    protected T instruction;
    private OpState c = OpState.STATE_IDLE;
    private final Object e = new Object();
    protected Runnable openAppCmdCountDown = new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.base.-$$Lambda$BaseOperation$iLpTmjnNCOYvuhhENR4edATZ47g
        @Override // java.lang.Runnable
        public final void run() {
            BaseOperation.this.d();
        }
    };

    /* loaded from: classes3.dex */
    public enum OpState {
        STATE_IDLE,
        STATE_PROCESSING,
        STATE_SUCCESS,
        STATE_FAIL
    }

    protected List<String> dependenceInstruction() {
        return null;
    }

    protected void onCancel() {
    }

    protected void onCreateOp() {
    }

    protected void onPreCreateOp() {
    }

    protected abstract OpState onProcess(boolean z);

    protected boolean supportAsync() {
        return false;
    }

    public BaseOperation(T t) {
        this.instruction = t;
        onPreCreateOp();
        a();
        onCreateOp();
    }

    private void a() {
        OperationManager.getInstance().putToHashMap(this);
        Optional<InstructionDependence> dependence = ((InstructionHeader) this.instruction.getHeader()).getDependence();
        if (dependence == null || !dependence.isPresent()) {
            L.ope.i("no dependence=%s", this);
            return;
        }
        String id = dependence.get().getId();
        if (!TextUtils.isEmpty(id)) {
            this.b = OperationManager.getInstance().getFromHashMap(id);
            if (this.b != null) {
                L.ope.i("Operation=%s,DependenceOpe=%s,dependenceId=%s", this, this.b, id);
                this.b.setHasDependenceByOtherIns(true);
            }
        }
    }

    public boolean consumeInstruction(Instruction instruction) {
        List<String> dependenceInstruction = dependenceInstruction();
        if (!ContainerUtil.hasData(dependenceInstruction)) {
            return false;
        }
        if (this.f == null) {
            this.f = new ArrayList();
        }
        for (String str : dependenceInstruction) {
            if (str.equals(instruction.getFullName())) {
                this.f.add(instruction);
                return true;
            }
        }
        return false;
    }

    protected Instruction loadDependentInstrByName(String str) {
        return InstructionUtil.getIntention(this.f, str);
    }

    public T getInstruction() {
        return this.instruction;
    }

    public OpState getState() {
        return this.c;
    }

    public void setState(OpState opState) {
        this.c = opState;
    }

    public List<Instruction> getDependentIns() {
        return this.f;
    }

    public BaseOperation getDependOpe() {
        return this.b;
    }

    public void setHasDependenceByOtherIns(boolean z) {
        this.a = z;
    }

    public final void process() {
        boolean z = true;
        L.ope.i("process %s", getOpName());
        synchronized (this.e) {
            if (this.b == null) {
                b();
            } else {
                OpState state = this.b.getState();
                L.ope.i("dependent ope is processing,dependOpe=%s", this.b);
                if (state != OpState.STATE_PROCESSING) {
                    if (state != OpState.STATE_SUCCESS) {
                        z = false;
                    }
                    if (this.instruction.checkDependence(this.b.getInstruction().getId(), String.valueOf(z))) {
                        b();
                    } else {
                        notifyProcessDone(OpState.STATE_FAIL);
                    }
                }
            }
        }
    }

    public void notifyProcessDone(OpState opState) {
        L.ope.i("notifyProcessDone=%s", getOpName());
        c();
        synchronized (this.e) {
            this.c = opState;
            if (!(this.c == OpState.STATE_IDLE || this.c == OpState.STATE_PROCESSING)) {
                OperationManager.getInstance().a(this, opState);
            }
        }
    }

    private void a(OpState opState) {
        L.ope.i("notifyProcess=%s", getOpName());
        synchronized (this.e) {
            this.c = opState;
            if (opState == OpState.STATE_PROCESSING) {
                OperationManager.getInstance().a(this);
            }
        }
    }

    private void b() {
        if (this.c == OpState.STATE_IDLE) {
            this.c = OpState.STATE_PROCESSING;
            try {
                this.c = onProcess(this.a);
            } catch (Exception e) {
                L.ope.e("processAfterHandleDepend", e);
                this.c = OpState.STATE_FAIL;
            }
            L.ope.i("instruction=%s,op=%s,OpState=%s,dependence=%s,supportAsync=%s", fullName(), this, this.c, Boolean.valueOf(this.a), Boolean.valueOf(supportAsync()));
            if (this.c == OpState.STATE_PROCESSING && !supportAsync()) {
                throw new IllegalStateException("not support async operation");
            } else if (this.c == OpState.STATE_SUCCESS || this.c == OpState.STATE_FAIL) {
                notifyProcessDone(this.c);
            } else if (this.a) {
                a(this.c);
            }
        } else {
            L.ope.i("not idle to process");
        }
    }

    public String getOpName() {
        return getClass().getSimpleName();
    }

    public String fullName() {
        T t = this.instruction;
        return t != null ? t.getFullName() : "";
    }

    public void setDependIns(Instruction instruction) {
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.add(instruction);
    }

    public String getId() {
        T t = this.instruction;
        return t != null ? t.getId() : "";
    }

    public String getDialogId() {
        return InstructionUtil.getDialogId(this.instruction);
    }

    public boolean isCancelled() {
        return this.d;
    }

    public void setCancelled(boolean z) {
        this.d = z;
    }

    public final void cancel() {
        this.c = OpState.STATE_FAIL;
        c();
        onCancel();
        L.ope.i("cancel currentop:%s", this);
    }

    public boolean isFinished() {
        return isCancelled() || this.c == OpState.STATE_SUCCESS || this.c == OpState.STATE_FAIL;
    }

    protected void register() {
        L.ope.i("%s register", getOpName());
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    private void c() {
        L.ope.i("%s unregister", getOpName());
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    public String toString() {
        return getOpName() + " id: " + getId() + " mState: " + this.c;
    }

    protected Context getContext() {
        return OperationManager.getInstance().getContext();
    }

    protected String getAsrQuery() {
        String dialogId = getDialogId();
        Instruction loadInstruction = OperationManager.getInstance().loadInstruction(dialogId, AIApiConstants.SpeechRecognizer.RecognizeResult);
        String dialogId2 = InstructionUtil.getDialogId(loadInstruction);
        if (TextUtils.isEmpty(dialogId) || !dialogId.equals(dialogId2)) {
            return null;
        }
        List<SpeechRecognizer.RecognizeResultItem> results = ((SpeechRecognizer.RecognizeResult) loadInstruction.getPayload()).getResults();
        if (ContainerUtil.isEmpty(results)) {
            return null;
        }
        return results.get(0).getText();
    }

    protected void setSpeakDependence() {
        Instruction loadInstruction;
        if (!this.instruction.getDependenceId().isPresent() && (loadInstruction = OperationManager.getInstance().loadInstruction(getDialogId(), AIApiConstants.SpeechSynthesizer.Speak)) != null) {
            InstructionDependence instructionDependence = new InstructionDependence();
            instructionDependence.setId(loadInstruction.getId());
            instructionDependence.setPredicate(Boolean.TRUE.toString());
            this.instruction.setDependence(instructionDependence);
        }
    }

    protected boolean setOpenAppCmdCountDown() {
        ThreadUtil.getWorkHandler().removeCallbacks(this.openAppCmdCountDown);
        long currentCmdDuration = PersonalizeExecution.getInstance().getCurrentCmdDuration();
        if (currentCmdDuration <= 0) {
            return false;
        }
        ThreadUtil.getWorkHandler().postDelayed(this.openAppCmdCountDown, TimeUnit.SECONDS.toMillis(currentCmdDuration));
        return true;
    }

    public /* synthetic */ void d() {
        ThirdPartyAppProxy.getInstance().stop(getContext());
        ThirdPartyAppProxy.getInstance().quit(getContext());
        EventBusRegistry.getEventBus().post(new ThirdPartAppOpeEvent(true));
    }
}
